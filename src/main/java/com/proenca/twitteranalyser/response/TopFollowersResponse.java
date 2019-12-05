package com.proenca.twitteranalyser.response;

import java.util.List;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TopFollowersResponse that = (TopFollowersResponse) o;
    return Objects.equals(tag, that.tag) &&
        Objects.equals(userResponses, that.userResponses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tag, userResponses);
  }

  @Override
  public String toString() {
    return "TopFollowersResponse{" +
        "tag='" + tag + '\'' +
        ", userResponses=" + userResponses +
        '}';
  }
}
