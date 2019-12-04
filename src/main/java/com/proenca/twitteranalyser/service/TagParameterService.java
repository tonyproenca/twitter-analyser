package com.proenca.twitteranalyser.service;

import com.proenca.twitteranalyser.domain.dto.TagParameterDto;
import com.proenca.twitteranalyser.request.PostTagRequest;
import java.util.List;

public interface TagParameterService {

  void createTag(PostTagRequest request);

  void deleteTag(PostTagRequest request);

  List<TagParameterDto> findAll();
}
