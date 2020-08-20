package com.sps.services.electricaldata.bo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sps.services.ElectricalSampleService;

public class ElectricalDateConfig {

	Logger logger = LoggerFactory.getLogger(ElectricalSampleService.class);
	
	LocalDateTime initialTime;
	
	LocalDateTime endTime;
	
	private String initialTimeStr;
	
	private String endTimeStr;

	public LocalDateTime getInitialTime() {
		return initialTime;
	}

	public void setInitialTime(LocalDateTime initialTime) {
		this.initialTime = initialTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getInitialTimeStr() {
		return initialTimeStr;
	}

	public void setInitialTimeStr(String initialTimeStr) {
		this.initialTime = fromStrToDate(initialTimeStr);
		this.initialTimeStr = initialTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTime = fromStrToDate(endTimeStr);
		this.endTimeStr = endTimeStr;
	}
	
	
	private LocalDateTime fromStrToDate(String dateTime) {

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
			return LocalDateTime.parse(dateTime, formatter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			return null;
		}
		
	}
	
	
	
}
