package com.proenca.twitteranalyser.service;

import static java.util.stream.Collectors.toList;

import com.proenca.twitteranalyser.domain.TagParameter;
import com.proenca.twitteranalyser.repository.TagParameterRepository;
import com.proenca.twitteranalyser.request.PostTagRequest;
import java.util.List;
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
    String tag = validateTag(request.getTag());
    repository.save(new TagParameter(tag));
  }

  @Override
  public void deleteTag(String tag) {
    tag = validateTag(tag);
    repository.deleteByTag(tag);
  }

  @Override
  public List<String> findAll() {
    return repository
        .findAll()
        .stream()
        .map(TagParameter::getTag)
        .collect(toList());
  }

  private String validateTag(String tag) {
    return tag.startsWith("#") ? tag : "#".concat(tag);
  }
}