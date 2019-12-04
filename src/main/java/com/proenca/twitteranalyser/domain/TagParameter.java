package com.proenca.twitteranalyser.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "TAG_PARAMETER")
public class TagParameter {

  @Id
  @Column(name = "TAG_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long uniqueId;

  @Column(name = "TAG", nullable = false, unique = true)
  private String tag;

  public TagParameter() {
  }

  public TagParameter(String tag) {
    this.tag = tag;
  }

  public Long getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(Long uniqueId) {
    this.uniqueId = uniqueId;
  }

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
    TagParameter tagParameter1 = (TagParameter) o;
    return Objects.equals(uniqueId, tagParameter1.uniqueId) &&
        Objects.equals(tag, tagParameter1.tag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uniqueId, tag);
  }
}
