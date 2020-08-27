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
	
	//Electrical parameter to consider a sample as idle
	Integer minRangeIdleVol;
	Integer maxRangeIdleVol;
	Integer minRangeIdleAmp;
	Integer maxRangeIdleAmp;
	
	//Number of consecutive idle samples to consider the device is idle again
	Integer consecutiveSamplesIdle;
	
	//URL of the classifier ML
	String classifierURL;
	
	//Labels
	ArrayList<String> labels;
	
	//File to save exports
	String exportFile;
	
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
	public String getExportFile() {
		return exportFile;
	}
	public void setExportFile(String exportFile) {
		this.exportFile = exportFile;
	}
	public Integer getMinRangeIdleVol() {
		return minRangeIdleVol;
	}
	public void setMinRangeIdleVol(Integer minRangeIdleVol) {
		this.minRangeIdleVol = minRangeIdleVol;
	}
	public Integer getMaxRangeIdleVol() {
		return maxRangeIdleVol;
	}
	public void setMaxRangeIdleVol(Integer maxRangeIdleVol) {
		this.maxRangeIdleVol = maxRangeIdleVol;
	}
	public Integer getMinRangeIdleAmp() {
		return minRangeIdleAmp;
	}
	public void setMinRangeIdleAmp(Integer minRangeIdleAmp) {
		this.minRangeIdleAmp = minRangeIdleAmp;
	}
	public Integer getMaxRangeIdleAmp() {
		return maxRangeIdleAmp;
	}
	public void setMaxRangeIdleAmp(Integer maxRangeIdleAmp) {
		this.maxRangeIdleAmp = maxRangeIdleAmp;
	}
	public Integer getConsecutiveSamplesIdle() {
		return consecutiveSamplesIdle;
	}
	public void setConsecutiveSamplesIdle(Integer consecutiveSamplesIdle) {
		this.consecutiveSamplesIdle = consecutiveSamplesIdle;
	}
	public String getClassifierURL() {
		return classifierURL;
	}
	public void setClassifierURL(String classifierURL) {
		this.classifierURL = classifierURL;
	}
	
	
	
	

}
