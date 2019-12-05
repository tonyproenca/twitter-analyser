package com.proenca.twitteranalyser.controller;

import com.proenca.twitteranalyser.domain.dto.TagParameterDto;
import com.proenca.twitteranalyser.request.PostTagRequest;
import com.proenca.twitteranalyser.response.TopFollowersResponse;
import com.proenca.twitteranalyser.response.TwitterAnalyserResponse;
import com.proenca.twitteranalyser.service.TagParameterService;
import com.proenca.twitteranalyser.service.TwitterAnalyserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/twitter/analyser")
public class TwitterAnalyserController {

  @Autowired
  private TwitterAnalyserService twitterAnalyserService;

  @Autowired
  private TagParameterService tagParameterService;


  @GetMapping(path = "/top/followed/by/tags")
  public ResponseEntity<List<TopFollowersResponse>> getTopFiveMostFollowedUsersFromLastTweetsFromTag() {
    return new ResponseEntity<>(
        twitterAnalyserService.getTwitterAnalyserResponseBatch(),
        HttpStatus.OK
    );
  }

  @PostMapping(path = "/tag", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> postTag(@RequestBody @Valid PostTagRequest request) {
    tagParameterService.createTag(request);
    return new ResponseEntity<>(
        new TwitterAnalyserResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.name()),
        HttpStatus.CREATED
    );
  }

  @DeleteMapping(path = "/tag")
  public ResponseEntity<Object> deleteTag(@RequestBody @Valid PostTagRequest request) {
    tagParameterService.deleteTag(request);
    return new ResponseEntity<>(
        new TwitterAnalyserResponse(HttpStatus.OK.value(), HttpStatus.OK.name()),
        HttpStatus.OK
    );
  }

  @GetMapping(path = "/tags")
  public ResponseEntity<List<String>> findAllTags() {
    List<String> result = tagParameterService
        .findAll()
        .stream()
        .map(TagParameterDto::getTag)
        .collect(Collectors.toList());
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
