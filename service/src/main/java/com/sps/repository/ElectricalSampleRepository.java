package com.sps.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.sps.services.electricaldata.bo.ElectricalSample;



@Repository
public interface ElectricalSampleRepository extends MongoRepository<ElectricalSample, String> {

    @Query("{'time' : {$gte : ?0, $lte : ?1}}")
    public List<ElectricalSample> findByTime(LocalDateTime start, LocalDateTime end);


    
}

