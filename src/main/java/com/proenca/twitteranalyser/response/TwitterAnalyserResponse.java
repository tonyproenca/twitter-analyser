package com.proenca.twitteranalyser.response;

import java.util.Objects;

public class TwitterAnalyserResponse {

  private int code;
  private String status;

  public TwitterAnalyserResponse(int code, String status) {
    this.code = code;
    this.status = status;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TwitterAnalyserResponse that = (TwitterAnalyserResponse) o;
    return code == that.code &&
        Objects.equals(status, that.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, status);
  }

  @Override
  public String toString() {
    return "TwitterAnalyserResponse{" +
        "code=" + code +
        ", status='" + status + '\'' +
        '}';
  }
}
