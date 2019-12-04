package com.proenca.twitteranalyser.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import twitter4j.Status;

@Entity(name = "TWEET")
public class Tweet {

  @Id
  @Column(name = "TWEET_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "MESSAGE", nullable = false)
  private String message;

  @Column(name = "USER_UID", nullable = false)
  private Long twitterUserId;

  @Column(name = "TWITTER_USER_NAME", nullable = false)
  private String twitterUserName;

  public static Tweet createTweetFromStatus(Status status) {
    Tweet tweet = new Tweet();
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