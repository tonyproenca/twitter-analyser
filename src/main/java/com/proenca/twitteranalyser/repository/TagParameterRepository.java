package com.proenca.twitteranalyser.repository;

import com.proenca.twitteranalyser.domain.TagParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagParameterRepository extends JpaRepository<TagParameter, Long> {

  void deleteByTag(String tag);
}
