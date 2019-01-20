package com.mbalves.shortme.repository;

import com.mbalves.shortme.domain.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url, String> {

    public Url findBy_id(String id);

}
