package com.proenca.twitteranalyser.request;

import java.util.Objects;

public class PostTagRequest {

  private String tag;

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PostTagRequest that = (PostTagRequest) o;
    return Objects.equals(tag, that.tag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tag);
  }

  @Override
  public String toString() {
    return "PostTagRequest{" +
        "tag='" + tag + '\'' +
        '}';
  }
}
