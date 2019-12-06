package com.proenca.twitteranalyser.response;

import java.util.List;

public class TopFollowersResponse {

  private String tag;
  private List<UserResponse> userResponses;

  public TopFollowersResponse() {
  }

  public TopFollowersResponse(String tag,
      List<UserResponse> userResponses) {
    this.tag = tag;
    this.userResponses = userResponses;
  }


  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public List<UserResponse> getUserResponses() {
    return userResponses;
  }

  public void setUserResponses(
      List<UserResponse> userResponses) {
    this.userResponses = userResponses;
  }

}
