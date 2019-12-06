package com.proenca.twitteranalyser.response;

import java.util.Objects;

public class UserResponse {

  private Long id;
  private String name;
  private String email;
  private String screenName;
  private Integer followersCount;
  private String userUrl;
  private String tweetMessage;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public Integer getFollowersCount() {
    return followersCount;
  }

  public void setFollowersCount(Integer followersCount) {
    this.followersCount = followersCount;
  }

  public String getUserUrl() {
    return userUrl;
  }

  public void setUserUrl(String userUrl) {
    this.userUrl = userUrl;
  }

  public String getTweetMessage() {
    return tweetMessage;
  }

  public void setTweetMessage(String tweetMessage) {
    this.tweetMessage = tweetMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserResponse that = (UserResponse) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        Objects.equals(email, that.email) &&
        Objects.equals(screenName, that.screenName) &&
        Objects.equals(followersCount, that.followersCount) &&
        Objects.equals(userUrl, that.userUrl) &&
        Objects.equals(tweetMessage, that.tweetMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, screenName, followersCount, userUrl, tweetMessage);
  }

  @Override
  public String toString() {
    return "UserResponse{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", screenName='" + screenName + '\'' +
        ", followersCount=" + followersCount +
        ", userUrl='" + userUrl + '\'' +
        ", tweetMessage='" + tweetMessage + '\'' +
        '}';
  }
}
