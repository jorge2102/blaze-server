package com.blaze.server.repositories.mongo;

import com.blaze.server.models.Identification;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IdentificationRepository extends MongoRepository<Identification, String>{

}
