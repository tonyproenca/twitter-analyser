package com.proenca.twitteranalyser.service;

import static com.proenca.twitteranalyser.domain.Tweet.createTweetFromStatus;
import static com.proenca.twitteranalyser.response.UserResponse.createUserResponseFromUser;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

import com.proenca.twitteranalyser.domain.Tweet;
import com.proenca.twitteranalyser.repository.TweetRepository;
import com.proenca.twitteranalyser.response.TwitterAnalyserResponse;
import com.proenca.twitteranalyser.response.UserResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Component
public class TwitterAnalyserServiceImpl implements TwitterAnalyserService {

  private static final Integer QUERY_COUNT = 100;

  @Autowired
  private TagParameterService tagParameterService;

  @Autowired
  private TweetRepository tweetRepository;

  @Value("${twitter.consumer.key}")
  private String oAuthConsumerKey;

  @Value("${twitter.consumer.secret}")
  private String oAuthConsumerSecret;

  @Value("${twitter.access.token}")
  private String oAuthAccessToken;

  @Value("${twitter.access.token.secret}")
  private String oAuthAccessTokenSecret;

  public TwitterAnalyserResponse getTwitterAnalyserResponseBatch() {

    List<QueryResult> queryResultList = tagParameterService
        .findAll()
        .parallelStream()
        .map(tagParameter ->
            consumeTwitter(tagParameter.getTag())).collect(
            toCollection(ArrayList::new));

    Set<UserResponse> userResponses = new HashSet<>();

    queryResultList.parallelStream().forEach(queryResult ->
        userResponses.addAll(collectDataFromTwitterResult(queryResult)));

    return new TwitterAnalyserResponse(userResponses);
  }

  private Set<UserResponse> collectDataFromTwitterResult(QueryResult queryResult) {

    Set<UserResponse> userList = new HashSet<>();
    List<Tweet> tweetList = new ArrayList<>();

    queryResult.getTweets()
        .parallelStream()
        .forEach(status -> {
          userList.add(createUserResponseFromUser(status.getUser()));
          tweetList.add(createTweetFromStatus(status));
        });

    tweetRepository.saveAll(tweetList);

    return userList
        .parallelStream()
        .sorted(comparing(UserResponse::getFollowersCount))
        .limit(5)
        .collect(toSet());

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

    QueryResult result = null;
    try {
      result = twitter.search(query);
    } catch (TwitterException e) {
      throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
          "A error occurred during Twitter query. Please, try again later");
    }
    return result;
  }
}
