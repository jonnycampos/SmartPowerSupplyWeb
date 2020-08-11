package com.sps.services.electricaldata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

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
    


    public ElectricalSampleList getAllElectricalSamples() {
    	return new ElectricalSampleList(repository.findAll());
    }

	public ElectricalSample saveRandomElectricalSample() {
		logger.info("Saving...");
		ElectricalSample electricalSample = new ElectricalSample(true);
		logger.info("Simulator:" + simulatorConfig.getCoffeeTypes().get("capuccino").getCoffeeConfigurationRanges().get(0).getMaxVol());
		repository.save(electricalSample);
		return electricalSample;
	}

}
