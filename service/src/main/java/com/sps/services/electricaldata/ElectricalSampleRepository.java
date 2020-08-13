package com.sps.services.electricaldata;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface ElectricalSampleRepository extends MongoRepository<ElectricalSample, String> {

    @Query("{'time' : {$gt : ?0, $lt : ?1}}")
    public List<ElectricalSample> findByTime(LocalDateTime start, LocalDateTime end);


    
}

