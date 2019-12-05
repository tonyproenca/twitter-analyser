package com.proenca.twitteranalyser.service;

import com.proenca.twitteranalyser.domain.TagParameter;
import com.proenca.twitteranalyser.domain.dto.TagParameterDto;
import com.proenca.twitteranalyser.repository.TagParameterRepository;
import com.proenca.twitteranalyser.request.PostTagRequest;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = "transactionManager")
public class TagParameterServiceImpl implements TagParameterService {

  @Autowired
  private TagParameterRepository repository;

  @Override
  public void createTag(PostTagRequest request) {

    if (!request.getTag().startsWith("#")) {
      request.setTag("#" + request.getTag());
    }

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