package com.proenca.twitteranalyser.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import twitter4j.Status;

@Entity(name = "TWEET")
public class Tweet {

  @Id
  @Column(name = "TWEET_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob
  @Column(name = "MESSAGE", nullable = false, length = 300)
  private String message;

  @Column(name = "TWITTER_TWEET_ID", nullable = false, unique = true)
  private Long tweetId;

  @Column(name = "USER_UID", nullable = false)
  private Long twitterUserId;

  @Column(name = "TWITTER_USER_NAME", nullable = false)
  private String twitterUserName;

  public static Tweet createTweetFromStatus(Status status) {
    Tweet tweet = new Tweet();
    tweet.setTweetId(status.getId());
    tweet.setMessage(status.getText());
    tweet.setTwitterUserId(status.getUser().getId());
    tweet.setTwitterUserName(status.getUser().getName());
    return tweet;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getTweetId() {
    return tweetId;
  }

  public void setTweetId(Long tweetId) {
    this.tweetId = tweetId;
  }

  public Long getTwitterUserId() {
    return twitterUserId;
  }

  public void setTwitterUserId(Long twitterUserId) {
    this.twitterUserId = twitterUserId;
  }

  public String getTwitterUserName() {
    return twitterUserName;
  }

  public void setTwitterUserName(String twitterUserName) {
    this.twitterUserName = twitterUserName;
  }
}