package com.proenca.twitteranalyser.mother;

import com.proenca.twitteranalyser.domain.dto.TagParameterDto;

public class TagParameterMother {

  private TagParameterMother() { }

  public static TagParameterDto createTagParameterDto(String tag) {
    var tagParameterDto = new TagParameterDto();

    tagParameterDto.setUniqueId(1L);
    tagParameterDto.setTag(tag);

    return tagParameterDto;
  }

}
