package com.proenca.twitteranalyser.service;

import com.proenca.twitteranalyser.domain.Tweet;
import com.proenca.twitteranalyser.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetServiceImpl implements TweetService {

  @Autowired
  private TweetRepository tweetRepository;

  @Override
  public void saveTweet(Tweet tweet) {
    tweetRepository.save(tweet);
  }
}
