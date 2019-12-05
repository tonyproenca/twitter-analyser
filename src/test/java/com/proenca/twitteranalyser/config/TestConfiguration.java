package com.proenca.twitteranalyser.config;

import com.proenca.twitteranalyser.service.TwitterAnalyserService;
import com.proenca.twitteranalyser.service.TwitterAnalyserServiceImpl;
import org.springframework.context.annotation.Bean;

public class TestConfiguration {

    @Bean
    public TwitterAnalyserService twitterAnalyserService() {
        return new TwitterAnalyserServiceImpl();
    }
}
