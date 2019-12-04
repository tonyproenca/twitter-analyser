package com.proenca.twitteranalyser.service;

import com.proenca.twitteranalyser.domain.TagParameter;
import com.proenca.twitteranalyser.domain.dto.TagParameterDto;
import com.proenca.twitteranalyser.repository.TagParameterRepository;
import com.proenca.twitteranalyser.request.PostTagRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagParameterServiceImpl implements TagParameterService {

  @Autowired
  private TagParameterRepository repository;

  @Override
  public void createTag(PostTagRequest request) {
    repository.save(new TagParameter(request.getTag()));
  }

  @Override
  public void deleteTag(PostTagRequest request) {
    repository.deleteByTag(request.getTag());
  }

  @Override
  public List<TagParameterDto> findAll() {
    return repository
        .findAll()
        .stream()
        .map(TagParameterDto::createTagParameterDto)
        .collect(Collectors.toList());
  }
}