package com.videohub.videohub.repository;

import com.videohub.videohub.domain.Rate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends MongoRepository<Rate, String> {
}
