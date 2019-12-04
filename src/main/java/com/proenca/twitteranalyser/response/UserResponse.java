package com.proenca.twitteranalyser.response;

import twitter4j.User;

public class UserResponse {

  private Long id;
  private String name;
  private String email;
  private String screenName;
  private Integer followersCount;
  private String userUrl;

  public static UserResponse createUserResponseFromUser(User user) {

    UserResponse userResponse = new UserResponse();
    userResponse.setId(user.getId());
    userResponse.setName(user.getName());
    userResponse.setEmail(user.getEmail());
    userResponse.setFollowersCount(user.getFollowersCount());
    userResponse.setScreenName(user.getScreenName());
    userResponse.setUserUrl(user.getURL());

    return userResponse;
  }

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
}
