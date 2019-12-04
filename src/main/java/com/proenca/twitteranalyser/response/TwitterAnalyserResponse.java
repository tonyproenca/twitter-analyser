package com.proenca.twitteranalyser.response;

import java.util.List;

public class TwitterAnalyserResponse {

  List<UserResponse> userResponses;

  public List<UserResponse> getUserResponses() {
    return userResponses;
  }

  public void setUserResponses(
      List<UserResponse> userResponses) {
    this.userResponses = userResponses;
  }
}
