package com.proenca.twitteranalyser.response;

import java.util.Set;

public class TwitterAnalyserResponse {

  Set<UserResponse> userResponses;

  public TwitterAnalyserResponse() {
  }

  public TwitterAnalyserResponse(
      Set<UserResponse> userResponses) {
    this.userResponses = userResponses;
  }

  public Set<UserResponse> getUserResponses() {
    return userResponses;
  }

  public void setUserResponses(
      Set<UserResponse> userResponses) {
    this.userResponses = userResponses;
  }
}
