package com.sps.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.sps.configuration.LabelingConfiguration;
import com.sps.repository.ElectricalSampleRepository;
import com.sps.repository.LabelInteractionRepository;
import com.sps.services.electricaldata.bo.ElectricalInteraction;
import com.sps.services.electricaldata.bo.ElectricalSample;

@Configuration
@EnableConfigurationProperties(LabelingConfiguration.class)
@Service
public class LabelingService {
	
    @Autowired
    private LabelingConfiguration labelingConfig;
	
    @Autowired
    private ElectricalSampleRepository repositorySample;
    
    @Autowired
    private LabelInteractionRepository repositoryLabel;
    
	Logger logger = LoggerFactory.getLogger(ElectricalSampleService.class);

    
    
    /**
     * Get label string list
     * @return
     */
	public List<String> getAvailableLabels() {
		logger.info("Retrieving name of labels");
		return labelingConfig.getLabels();
	}
	
	/**
	 * Save labeling from a particular period of samples
	 * - Labeling the samples
	 * - Adding a new record in the labeling table
	 * @param labelInfo
	 */
	public void performLabel(ElectricalInteraction labelInfo) {
		logger.info("Label from " + labelInfo.getStartFormatted() + " until " + labelInfo.getEndFormatted() + " - " + labelInfo.getLabel());
		//We will update the label in the samples database
		List<ElectricalSample> samples = repositorySample.findByTime(labelInfo.getStart(), labelInfo.getEnd());
		for (ElectricalSample sample : samples) {
			sample.setLabel(labelInfo.getLabel());
			repositorySample.save(sample);
		}

		//In another collection we will insert the label 
		ElectricalInteraction labeledSample = new ElectricalInteraction(labelInfo.getStart(), labelInfo.getEnd());
		labeledSample.setLabel(labelInfo.getLabel());
		repositoryLabel.save(labeledSample);
		logger.info("Labeled Done!!" );
	}
}
