package com.sps.services.electricaldata;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ElectricalSampleRepository extends MongoRepository<ElectricalSample, String> {
}

