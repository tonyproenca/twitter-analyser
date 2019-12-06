package com.proenca.twitteranalyser.config;

import static org.mockito.Mockito.spy;

import com.proenca.twitteranalyser.service.TwitterAnalyserService;
import com.proenca.twitteranalyser.service.TwitterAnalyserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TestConfiguration {

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

        return spy(new TwitterFactory(configurationBuilder.build()).getInstance());
    }

    @Bean
    public TwitterAnalyserService twitterAnalyserService() {
        return new TwitterAnalyserServiceImpl();
    }

}
