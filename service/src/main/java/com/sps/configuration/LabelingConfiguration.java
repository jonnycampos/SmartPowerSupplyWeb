package com.sps.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:labeling.properties")
@ConfigurationProperties("labeling")
@Primary
public class LabelingConfiguration {

	//Sample rate to find an interaction 
	Integer samplesInteractionFound;
	Integer samplesInteractionDuring;
	
	//Electrical parameter to consider a sample as interaction
	Integer minRangeInteractionVol;
	Integer maxRangeInteractionVol;
	Integer minRangeInteractionAmp;
	Integer maxRangeInteractionAmp;
	
	//Labels
	ArrayList<String> labels;
	
	public Integer getSamplesInteractionFound() {
		return samplesInteractionFound;
	}
	public void setSamplesInteractionFound(Integer samplesInteractionFound) {
		this.samplesInteractionFound = samplesInteractionFound;
	}
	public Integer getSamplesInteractionDuring() {
		return samplesInteractionDuring;
	}
	public void setSamplesInteractionDuring(Integer samplesInteractionDuring) {
		this.samplesInteractionDuring = samplesInteractionDuring;
	}
	public Integer getMinRangeInteractionVol() {
		return minRangeInteractionVol;
	}
	public void setMinRangeInteractionVol(Integer minRangeInteractionVol) {
		this.minRangeInteractionVol = minRangeInteractionVol;
	}
	public Integer getMaxRangeInteractionVol() {
		return maxRangeInteractionVol;
	}
	public void setMaxRangeInteractionVol(Integer maxRangeInteractionVol) {
		this.maxRangeInteractionVol = maxRangeInteractionVol;
	}
	public Integer getMinRangeInteractionAmp() {
		return minRangeInteractionAmp;
	}
	public void setMinRangeInteractionAmp(Integer minRangeInteractionAmp) {
		this.minRangeInteractionAmp = minRangeInteractionAmp;
	}
	public Integer getMaxRangeInteractionAmp() {
		return maxRangeInteractionAmp;
	}
	public void setMaxRangeInteractionAmp(Integer maxRangeInteractionAmp) {
		this.maxRangeInteractionAmp = maxRangeInteractionAmp;
	}
	public ArrayList<String> getLabels() {
		return labels;
	}
	public void setLabels(ArrayList<String> labels) {
		this.labels = labels;
	}
	
	
	
	

}
