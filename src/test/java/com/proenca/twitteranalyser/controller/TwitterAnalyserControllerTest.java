package com.proenca.twitteranalyser.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proenca.twitteranalyser.domain.dto.TagParameterDto;
import com.proenca.twitteranalyser.request.PostTagRequest;
import com.proenca.twitteranalyser.response.TopFollowersResponse;
import com.proenca.twitteranalyser.service.TagParameterService;
import com.proenca.twitteranalyser.service.TwitterAnalyserService;
import java.util.Collections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ComponentScan(basePackages = "com.proenca.twitteranalyser.controller")
@WebMvcTest
public class TwitterAnalyserControllerTest {

  @InjectMocks
  private TwitterAnalyserController fixture;

  @MockBean
  private TwitterAnalyserService twitterAnalyserService;

  @MockBean
  private TagParameterService tagParameterService;

  private MockMvc mvc;

  @BeforeEach
  public void setUp() {
    initMocks(this);
    mvc = MockMvcBuilders.standaloneSetup(fixture).build();
  }

  @Test
  public void testGetTopFiveMostFollowedUsersFromLastTweetsFromTag() throws Exception {
    String url = "/twitter/analyser/top/followed/by/tags";

    given(twitterAnalyserService.getTwitterAnalyserResponseBatch())
        .willReturn(Collections.singletonList(new TopFollowersResponse()));

    mvc.perform(get(url)
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status()
            .isOk());

    then(twitterAnalyserService).should().getTwitterAnalyserResponseBatch();
  }

  @Test
  public void testPostTag() throws Exception {
    String url = "/twitter/analyser/tag";

    PostTagRequest request = new PostTagRequest();
    request.setTag("dummyTag");

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonValue = objectMapper.writeValueAsString(request);

    mvc.perform(post(url)
        .content(jsonValue)
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status()
            .isCreated());

    then(tagParameterService).should().createTag(any(PostTagRequest.class));
  }

  @Test
  public void testDeleteTag() throws Exception {
    String url = "/twitter/analyser/tag";

    PostTagRequest request = new PostTagRequest();
    request.setTag("dummyTag");

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonValue = objectMapper.writeValueAsString(request);

    mvc.perform(delete(url)
        .content(jsonValue)
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status()
            .isOk());

    then(tagParameterService).should().deleteTag(any(PostTagRequest.class));
  }

  @Test
  public void testGetAllTags() throws Exception {
    String url = "/twitter/analyser/tags";

    given(tagParameterService.findAll())
        .willReturn(Collections.singletonList(new TagParameterDto()));

    mvc.perform(get(url)
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status()
            .isOk());

    then(tagParameterService).should().findAll();

  }
}
