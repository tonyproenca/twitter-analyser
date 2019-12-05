package com.proenca.twitteranalyser.domain.dto;

import com.proenca.twitteranalyser.domain.TagParameter;

public class TagParameterDto {

  private Long uniqueId;
  private String tag;

  public static TagParameterDto createTagParameterDto(TagParameter tagParameter) {
    TagParameterDto tagParameterDto = new TagParameterDto();
    tagParameterDto.setTag(tagParameter.getTag());
    tagParameterDto.setUniqueId(tagParameter.getUniqueId());

    return tagParameterDto;
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

}
