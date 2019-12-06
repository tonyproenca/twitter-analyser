package com.proenca.twitteranalyser.mother;

import com.proenca.twitteranalyser.domain.TagParameter;

public class TagParameterMother {

  private TagParameterMother() { }

  public static TagParameter createTagParameterDto(String tag) {
    var tagParameter = new TagParameter();
    tagParameter.setUniqueId(1L);
    tagParameter.setTag(tag);

    return tagParameter;
  }

}
