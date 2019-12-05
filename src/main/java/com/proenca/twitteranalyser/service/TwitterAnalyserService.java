package com.proenca.twitteranalyser.service;

import com.proenca.twitteranalyser.response.TopFollowersResponse;
import java.util.List;

public interface TwitterAnalyserService {

  List<TopFollowersResponse> getTwitterAnalyserResponseBatch();

}
