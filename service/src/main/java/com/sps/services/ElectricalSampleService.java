package com.sps.services;

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
import com.sps.configuration.LabelingConfiguration;
import com.sps.repository.ElectricalSampleRepository;
import com.sps.services.electricaldata.bo.ElectricalInteraction;
import com.sps.services.electricaldata.bo.ElectricalSample;
import com.sps.services.electricaldata.bo.ElectricalSampleList;
import com.sps.services.electricaldata.bo.LikelyInInteractionResult;

@Configuration
@EnableConfigurationProperties({LabelingConfiguration.class,
							   DeviceSimulatorConfiguration.class})
@Service
public class ElectricalSampleService {


	Logger logger = LoggerFactory.getLogger(ElectricalSampleService.class);
	
    @Autowired
    private ElectricalSampleRepository repositorySample;
    
    @Autowired
    private LabelingConfiguration labelingConfig;
    
    @Autowired
    private DeviceSimulatorConfiguration simulatorConfig;


    public List<ElectricalSample> getAllElectricalSamples() {
    	return repositorySample.findAll();
    }

	public ElectricalSample saveRandomElectricalSample() {
		logger.info("Saving...");
		ElectricalSample electricalSample = new ElectricalSample(true);
		logger.info("Simulator:" + simulatorConfig.getCoffeeTypes().get("capuccino").getCoffeeConfigurationRanges().get(0).getMaxVol());
		repositorySample.save(electricalSample);
		return electricalSample;
	}
	
	
	public ElectricalSampleList getElectricalSamples(LocalDateTime timeA, LocalDateTime timeB) {
		logger.info("Samples between " + timeA + " and " + timeB);
		List<ElectricalSample> samples = repositorySample.findByTime(timeA, timeB);
		logger.info("Samples - " + samples.size());
		//Samples analysis
		List<ElectricalInteraction> likelyElectricalInteractions = analyzeElectricalPatterns(samples);
		logger.info("After Analyzing there are likely- " + likelyElectricalInteractions.size() + " - Patterns to be labeled");
		ElectricalSampleList result = new ElectricalSampleList();
		result.setElectricalSamples(samples);
		result.setElectricalInteractions(likelyElectricalInteractions);
		return result;
	}

	
	/**
	 * Analyze a consecutive list of electrical samples to find device interactions
	 * @param samples List of samples
	 * @return List of possible patterns to be labeled. The rule to consider this pattern as not "idle"
	 * is based on a simple rule: During X seconds it must contains Y samples with amperage and voltage
	 * in a particular range. THIS RULE NEEDS TO BE REVISED WHEN REAL DATA IS AVAILABLE
	 */
	private List<ElectricalInteraction> analyzeElectricalPatterns(List<ElectricalSample> samples) {
		Boolean currentStatusIdle = true;
		int numberOfSamplesinBucket = labelingConfig.getSamplesInteractionDuring();
		LocalDateTime start = null;
		LocalDateTime end = null;
		List<ElectricalInteraction> interactions= new ArrayList<ElectricalInteraction>();
		
		int i = 0;
		while (i < samples.size()) {
			LikelyInInteractionResult likelyResult = null;
			
			// Get a sample to analyze
			if (i + numberOfSamplesinBucket <= samples.size()) {
				likelyResult = likelyInInteraction(samples.subList(i, i+numberOfSamplesinBucket));
			} else {
				likelyResult = likelyInInteraction(samples.subList(i, samples.size()));
			}
			
			logger.info("Analyzed Batch - Current status is idle - " + currentStatusIdle);
			
			//The device is idle so far
			if (currentStatusIdle) {
				if (likelyResult.getIsLikelyInIteraction()) {
					logger.info("Found Start of possible pattern:" + likelyResult.getFirstSample());
					currentStatusIdle = false;
					start = likelyResult.getFirstSample().getTime();
				}
			
				//The device was in the process of been working
			} else {
				//Idle again
				
				if (!likelyResult.getIsLikelyInIteraction()) {
					logger.info("Found End of possible pattern:" + likelyResult.getLastSample());
					currentStatusIdle = true;
					end = likelyResult.getLastSample().getTime();
					interactions.add(new ElectricalInteraction(start,end));
				}
			}
			i = i + numberOfSamplesinBucket;
		}
		
		return interactions;
	}

	
	/**
	 * If a batch of samples are not in "idle" status.
	 * If so, returns the first sample where the device changed its idle status
	 * @param samples A set of N samples consecutive
	 * @return True or false if there are enough not "idle" samples
	 * to be considered as part of an interaction.
	 * In any case also returns the first sample out of idle range and the last one
	 */
	private LikelyInInteractionResult likelyInInteraction(List<ElectricalSample> samples) {
		Integer countSamplesInRange = 0;
		ElectricalSample firstSample = null;
		ElectricalSample lastSample = samples.get(0);
		for (ElectricalSample sample : samples) {
			if (inLikelyLabeledRange(sample)) {
				countSamplesInRange++;
				if (firstSample == null) {
					firstSample = sample;
				}
				lastSample = sample;
			}
		}
		LikelyInInteractionResult result = new LikelyInInteractionResult();
		result.setFirstSample(firstSample);
		result.setLastSample(lastSample);
		result.setIsLikelyInIteraction(countSamplesInRange >= labelingConfig.getSamplesInteractionFound());
		return result;
	}
	
	
	/**
	 * Is a particular (likely) sample represents the device is working
	 * actively and not in idle status, just by checking the electrical
	 * parameters
	 * @param sample
	 * @return True if this sample needs to be (likely) labeled
	 */
	private boolean inLikelyLabeledRange(ElectricalSample sample) {
		return (sample.getMa() >= labelingConfig.getMinRangeInteractionAmp() &&
				sample.getMa() <= labelingConfig.getMaxRangeInteractionAmp() &&
				sample.getV() >= labelingConfig.getMinRangeInteractionVol() &&
				sample.getV() >= labelingConfig.getMinRangeInteractionVol());
	}

	
	/**
	 * Returns the last N samples
	 * @param limit Count of samples to return
	 * @return
	 */
	public ElectricalSampleList getLastElectricalSamples(Integer limit) {
		logger.info("Retrieving the last samples:" + limit);
		Pageable page = PageRequest.of(0,limit,Sort.by("_id").descending());
		Page<ElectricalSample> samples = repositorySample.findAll(page);
		List<ElectricalSample> reversed = new ArrayList<>(samples.getContent());
		//Ok we have the last n items. But we need to reverse them to follow the normal logic
		Collections.reverse(reversed);
		ElectricalSampleList result = new ElectricalSampleList();
		result.setElectricalSamples(reversed);
		result.setElectricalInteractions(new ArrayList<ElectricalInteraction>());
		return result;
	}

	
	

}
