package com.proenca.twitteranalyser.service;

import static com.google.common.collect.Comparators.greatest;
import static com.proenca.twitteranalyser.domain.Tweet.createTweetFromStatus;
import static com.proenca.twitteranalyser.response.UserResponse.createUserResponseFromUser;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toCollection;

import com.proenca.twitteranalyser.domain.Tweet;
import com.proenca.twitteranalyser.response.TopFollowersResponse;
import com.proenca.twitteranalyser.response.UserResponse;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class TwitterAnalyserServiceImpl implements TwitterAnalyserService {

  private static final Logger LOG = LoggerFactory.getLogger(TwitterAnalyserServiceImpl.class);
  private static final Integer QUERY_COUNT = 100;

  @Autowired
  private TagParameterService tagParameterService;

  @Autowired
  private TweetService tweetService;

  @Value("${twitter.consumer.key}")
  private String oAuthConsumerKey;

  @Value("${twitter.consumer.secret}")
  private String oAuthConsumerSecret;

  @Value("${twitter.access.token}")
  private String oAuthAccessToken;

  @Value("${twitter.access.token.secret}")
  private String oAuthAccessTokenSecret;

  public List<TopFollowersResponse> getTwitterAnalyserResponseBatch() {

    List<QueryResult> queryResultList = tagParameterService
        .findAll()
        .parallelStream()
        .map(tagParameter -> consumeTwitter(tagParameter.getTag()))
        .collect(toCollection(ArrayList::new));

    List<UserResponse> userResponses = new ArrayList<>();

    List<TopFollowersResponse> topFollowersResponseList = new ArrayList<>();

    for (QueryResult queryResult : queryResultList) {
      userResponses.addAll(collectDataFromTwitterResult(queryResult));
      topFollowersResponseList.add(new TopFollowersResponse(queryResult.getQuery(), userResponses));
      userResponses = new ArrayList<>();
    }

    return topFollowersResponseList;
  }

  private List<UserResponse> collectDataFromTwitterResult(QueryResult queryResult) {

    List<UserResponse> userList = new ArrayList<>();
    List<Tweet> tweetList = new ArrayList<>();

    queryResult.getTweets()
        .parallelStream()
        .forEach(status -> {
          userList.add(createUserResponseFromUser(status.getUser(), status.getText()));
          tweetList.add(createTweetFromStatus(status));
        });

    persistTweetsMessages(tweetList);

    return userList.stream()
        .collect(greatest(5, comparingLong(UserResponse::getFollowersCount)));
  }

  private void persistTweetsMessages(List<Tweet> tweetList) {
    tweetList.forEach(tweet -> {
      try {
        tweetService.saveTweet(tweet);
      } catch (DataIntegrityViolationException e) {
        LOG.info(String
            .format("Message with ID %s already exists on database. Moving on.", tweet.getId()));
      }
    });
  }

  private QueryResult consumeTwitter(String hashTag) {

    ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
    configurationBuilder.setDebugEnabled(true)
        .setOAuthConsumerKey(oAuthConsumerKey)
        .setOAuthConsumerSecret(oAuthConsumerSecret)
        .setOAuthAccessToken(oAuthAccessToken)
        .setOAuthAccessTokenSecret(oAuthAccessTokenSecret);

    TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
    Twitter twitter = twitterFactory.getInstance();

    Query query = new Query(hashTag);
    query.setCount(QUERY_COUNT);

    return getTweets(query, twitter);
  }

  private QueryResult getTweets(Query query, Twitter twitter) {

    QueryResult result;
    try {
      result = twitter.search(query);
    } catch (TwitterException e) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
          "A error occurred during Twitter query. Please, try again later \n"
              + e.getErrorMessage());
    }
    return result;
  }
}
