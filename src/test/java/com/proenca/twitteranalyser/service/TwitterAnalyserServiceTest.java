package com.proenca.twitteranalyser.service;

import static com.proenca.twitteranalyser.mother.TagParameterMother.createTagParameterDto;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.proenca.twitteranalyser.response.TopFollowersResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.proenca.twitteranalyser.service"})
public class TwitterAnalyserServiceTest {

  @Autowired
  private TwitterAnalyserService fixture;

  @Mock
  private TagParameterService tagParameterService;

  @Mock
  private TweetService tweetService;

  @BeforeEach
  private void setUp() {
    this.tagParameterService = Mockito.mock(TagParameterService.class);
    this.tweetService = Mockito.mock(TweetService.class);
  }

  @Test
  public void testGetAnalyserResponseBatch() {

    given(tagParameterService.findAll())
        .willReturn(asList(createTagParameterDto("#azure"), createTagParameterDto("#aws")));

    List<TopFollowersResponse> result = fixture.getTwitterAnalyserResponseBatch();

    assertThat(result).isNotNull();

    then(tagParameterService).should().findAll();
  }

}
