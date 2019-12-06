package com.proenca.twitteranalyser.service;

import static com.google.common.collect.Comparators.greatest;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;

import com.proenca.twitteranalyser.domain.Tweet;
import com.proenca.twitteranalyser.response.TopFollowersResponse;
import com.proenca.twitteranalyser.response.UserResponse;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

@Service
public class TwitterAnalyserServiceImpl implements TwitterAnalyserService {

  private static final Logger LOG = LoggerFactory.getLogger(TwitterAnalyserServiceImpl.class);
  private static final Integer QUERY_COUNT = 100;

  @Autowired
  private TagParameterService tagParameterService;

  @Autowired
  private TweetService tweetService;

  @Autowired
  private Twitter twitter;

  public List<TopFollowersResponse> getTwitterAnalyserResponseBatch() {

    List<QueryResult> queryResultList = tagParameterService
        .findAll()
        .parallelStream()
        .map(this::consumeTwitter)
        .collect(toList());

    List<TopFollowersResponse> topFollowersResponseList = new ArrayList<>();

    for (QueryResult queryResult : queryResultList) {
      topFollowersResponseList.add(new TopFollowersResponse(queryResult.getQuery(),
          collectDataFromTwitterResult(queryResult)));
    }

    return topFollowersResponseList;
  }

  private List<UserResponse> collectDataFromTwitterResult(QueryResult queryResult) {

    List<UserResponse> userList = new ArrayList<>();
    List<Tweet> tweetList = new ArrayList<>();

    for (Status status : queryResult.getTweets()) {
      userList.add(createUserResponseFromUser(status.getUser(), status.getText()));
      tweetList.add(createTweetFromStatus(status));
    }

    persistTweetsMessages(tweetList);

    return userList
        .stream()
        .collect(greatest(5, comparingLong(UserResponse::getFollowersCount)));
  }

  private void persistTweetsMessages(List<Tweet> tweetList) {
    for (Tweet tweet : tweetList) {
      try {
        tweetService.saveTweet(tweet);
      } catch (DataIntegrityViolationException e) {
        LOG.info(String
            .format("Message with ID %s already exists on database. Moving on.", tweet.getId()));
      }
    }
  }

  private QueryResult consumeTwitter(String hashTag) {
    Query query = new Query(hashTag);
    query.setCount(QUERY_COUNT);

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

  public static UserResponse createUserResponseFromUser(User user, String tweetMessage) {

    UserResponse userResponse = new UserResponse();
    userResponse.setId(user.getId());
    userResponse.setName(user.getName());
    userResponse.setEmail(user.getEmail());
    userResponse.setFollowersCount(user.getFollowersCount());
    userResponse.setScreenName(user.getScreenName());
    userResponse.setUserUrl(user.getURL());
    userResponse.setTweetMessage(tweetMessage);

    return userResponse;
  }

  public static Tweet createTweetFromStatus(Status status) {
    Tweet tweet = new Tweet();
    tweet.setTweetId(status.getId());
    tweet.setMessage(status.getText());
    tweet.setTwitterUserId(status.getUser().getId());
    tweet.setTwitterUserName(status.getUser().getName());
    return tweet;
  }
}
