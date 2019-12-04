package com.proenca.twitteranalyser.service;

import static java.util.stream.Collectors.toCollection;

import com.proenca.twitteranalyser.response.TwitterAnalyserResponse;
import com.proenca.twitteranalyser.response.UserResponse;
import java.util.ArrayList;
import java.util.List;
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

  @Value("twitter.consumer.key")
  private String oAuthConsumerKey;

  @Value("twitter.consumer.secret")
  private String oAuthConsumerSecret;

  @Value("twitter.access.token")
  private String oAuthAccessToken;

  @Value("twitter.access.token.secret")
  private String oAuthAccessTokenSecret;

  public TwitterAnalyserResponse getTwitterAnalyserResponseBatch() {

    List<QueryResult> queryResultList = tagParameterService
        .findAll()
        .parallelStream()
        .map(tagParameter ->
            consumeTwitter(tagParameter.getTag())).collect(
            toCollection(ArrayList::new));

    List<UserResponse> userResponses = new ArrayList<>();
    queryResultList.parallelStream().forEach(queryResult ->
      userResponses.addAll(collectDataFromTwitterResult(queryResult)));

    //from list of 5 most followed users for each hashtag, select the 5 final most followed,
    //sumarize and retrieve to controller

    return new TwitterAnalyserResponse();
  }

  private List<UserResponse> collectDataFromTwitterResult(QueryResult queryResult) {

    // run every tweet inside query result
    // add every message into a sub list of messages
    // sort top 5 most followed users
    // save batch all messages
    // retrieve 5 most followed users information

    return new ArrayList<>();
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
          "A error occurred during Twitter query. Please, try again later");
    }

    return result;
  }
}
