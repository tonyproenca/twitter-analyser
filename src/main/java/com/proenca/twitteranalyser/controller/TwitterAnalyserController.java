package com.proenca.twitteranalyser.controller;

import com.proenca.twitteranalyser.request.PostTagRequest;
import com.proenca.twitteranalyser.response.TopFollowersResponse;
import com.proenca.twitteranalyser.service.TagParameterService;
import com.proenca.twitteranalyser.service.TwitterAnalyserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/twitter/analyser")
public class TwitterAnalyserController {

  @Autowired
  private TwitterAnalyserService twitterAnalyserService;

  @Autowired
  private TagParameterService tagParameterService;


  @GetMapping(path = "/tags/topFollowers")
  @ResponseStatus(HttpStatus.OK)
  public List<TopFollowersResponse> getTopFiveMostFollowedUsersFromLastTweetsFromTag() {
    return twitterAnalyserService.getTwitterAnalyserResponseBatch();
  }

  @PostMapping(path = "/tags", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void postTag(@RequestBody @Valid PostTagRequest request) {
    tagParameterService.createTag(request);
  }

  @DeleteMapping(path = "/tags/{tag}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteTag(@PathVariable String tag) {
    tagParameterService.deleteTag(tag);
  }

  @GetMapping(path = "/tags")
  @ResponseStatus(HttpStatus.OK)
  public List<String> findAllTags() {
    return tagParameterService.findAll();
  }

}
