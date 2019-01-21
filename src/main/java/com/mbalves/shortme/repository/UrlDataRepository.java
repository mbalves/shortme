package com.mbalves.shortme.repository;

import com.mbalves.shortme.domain.UrlData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UrlDataRepository extends MongoRepository<UrlData, String> {

    List<UrlData> findByIdShortUrl(String idShortUrl);
}
