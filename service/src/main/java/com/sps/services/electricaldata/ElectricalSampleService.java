package com.sps.services.electricaldata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sps.configuration.DeviceSimulatorConfiguration;

@Configuration
@EnableConfigurationProperties(DeviceSimulatorConfiguration.class)
@Service
public class ElectricalSampleService {


	Logger logger = LoggerFactory.getLogger(ElectricalSampleService.class);
	
    @Autowired
    private ElectricalSampleRepository repository;
    
    @Autowired
    private DeviceSimulatorConfiguration simulatorConfig;
    


    public List<ElectricalSample> getAllElectricalSamples() {
    	return repository.findAll();
    }

	public ElectricalSample saveRandomElectricalSample() {
		logger.info("Saving...");
		ElectricalSample electricalSample = new ElectricalSample(true);
		logger.info("Simulator:" + simulatorConfig.getCoffeeTypes().get("capuccino").getCoffeeConfigurationRanges().get(0).getMaxVol());
		repository.save(electricalSample);
		return electricalSample;
	}
	
	
	public List<ElectricalSample> getElectricalSamples(LocalDateTime timeA, LocalDateTime timeB) {
		logger.info("Samples between " + timeA + " and " + timeB);
		List<ElectricalSample> samples = repository.findByTime(timeA, timeB);
		logger.info("Samples - " + samples);
		return samples;
	}

	
	
	public List<ElectricalSample> getLastElectricalSamples(Integer limit) {
		logger.info("Retrieving the last samples:" + limit);
    	//Pageable page = PageRequest.of(0,limit, Sort.by("time"));
		Pageable page = PageRequest.of(0,limit,Sort.by("_id").descending());
		Page<ElectricalSample> samples = repository.findAll(page);
		List<ElectricalSample> reversed = new ArrayList<>(samples.getContent());
		//Ok we have the last n items. But we need to reverse them to follow the normal logic
		Collections.reverse(reversed);
		return reversed;
	}
}
