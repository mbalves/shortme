package com.mbalves.shortme.repository;

import com.mbalves.shortme.domain.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface UrlRepository extends MongoRepository<Url, String> {

    Url findBy_id(String id);

    Url findFirstByOrderByCreationDateDesc();

    Url findFirstByOrderByCreationDateAsc();

    @Query("{ 'creationDate': { $gt: ?0 }}")
    List<Url> findAllAfterCreationDate(Date creationDate);

}
