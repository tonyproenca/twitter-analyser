package com.proenca.twitteranalyser.service;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

import com.proenca.twitteranalyser.config.TestConfiguration;
import com.proenca.twitteranalyser.domain.Tweet;
import com.proenca.twitteranalyser.response.TopFollowersResponse;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import twitter4j.Query;
import twitter4j.Twitter;
import twitter4j.TwitterException;

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

  @Autowired
  private Twitter twitter;

  @Before
  public void init() {
    initMocks(this);
  }

  @Test
  public void testGetAnalyserResponseBatch() throws TwitterException {

    given(tagParameterService.findAll())
        .willReturn(asList("#azure", "#aws"));

    List<TopFollowersResponse> result = fixture.getTwitterAnalyserResponseBatch();

    assertThat(result).isNotNull();
    assertThat(result).size().isEqualTo(2);
    assertThat(result).extracting(TopFollowersResponse::getTag).contains("#azure", "#aws");
    then(tagParameterService).should().findAll();
    then(tweetService).should(times(200)).saveTweet(any(Tweet.class));
    then(twitter).should(times(2)).search(any(Query.class));
  }

}
