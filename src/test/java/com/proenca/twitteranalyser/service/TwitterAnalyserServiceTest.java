package com.proenca.twitteranalyser.service;

import com.proenca.twitteranalyser.config.TestConfiguration;
import com.proenca.twitteranalyser.response.TopFollowersResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;

import static com.proenca.twitteranalyser.mother.TagParameterMother.createTagParameterDto;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.MockitoAnnotations.initMocks;

@ContextConfiguration(classes = {TestConfiguration.class})
@TestPropertySource(locations = "/application.properties")
@RunWith(SpringRunner.class)
public class TwitterAnalyserServiceTest {

  @Autowired
  private TwitterAnalyserService fixture;

  @MockBean
  private TagParameterService tagParameterService;

  @MockBean
  private TweetService tweetService;

  @MockBean
  private Twitter twitter;

  @Before
  public void init() {
    initMocks(this);
  }

  @Test
  public void testGetAnalyserResponseBatch() throws TwitterException {

    given(tagParameterService.findAll())
        .willReturn(asList(createTagParameterDto("#azure"), createTagParameterDto("#aws")));

    List<TopFollowersResponse> result = fixture.getTwitterAnalyserResponseBatch();
    assertThat(result).isNotNull();
    assertThat(result).size().isEqualTo(2);
    assertThat(result).extracting(TopFollowersResponse::getTag).contains("#azure", "#aws");

    then(tagParameterService).should().findAll();
  }

}
