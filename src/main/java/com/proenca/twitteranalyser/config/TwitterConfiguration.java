package com.proenca.twitteranalyser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class TwitterConfiguration {

  @Value("${twitter.consumer.key}")
  private String oAuthConsumerKey;

  @Value("${twitter.consumer.secret}")
  private String oAuthConsumerSecret;

  @Value("${twitter.access.token}")
  private String oAuthAccessToken;

  @Value("${twitter.access.token.secret}")
  private String oAuthAccessTokenSecret;

  @Bean
  public Twitter twitter() {
    ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
    configurationBuilder.setDebugEnabled(true)
        .setOAuthConsumerKey(oAuthConsumerKey)
        .setOAuthConsumerSecret(oAuthConsumerSecret)
        .setOAuthAccessToken(oAuthAccessToken)
        .setOAuthAccessTokenSecret(oAuthAccessTokenSecret);

    return new TwitterFactory(configurationBuilder.build()).getInstance();
  }
}
