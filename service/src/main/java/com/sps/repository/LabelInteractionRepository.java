package com.sps.repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sps.services.electricaldata.bo.ElectricalInteraction;



@Repository
public interface LabelInteractionRepository extends MongoRepository<ElectricalInteraction, String> {

    
}

