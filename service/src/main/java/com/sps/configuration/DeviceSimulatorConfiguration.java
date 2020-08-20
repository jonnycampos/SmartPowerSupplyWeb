package com.sps.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:simulator.properties")
@ConfigurationProperties("device")
@Primary
public class DeviceSimulatorConfiguration {

	Map<String,CoffeeConfiguration> coffeeTypes = new HashMap<String,CoffeeConfiguration>();
	Integer minIdleVol;
	Integer maxIdleVol;
	Integer minIdleAmp;
	Integer maxIdleAmp;
	
	private Integer samplesPerSecond;
	
	
	public Map<String, CoffeeConfiguration> getCoffeeTypes() {
		return coffeeTypes;
	}

	public void setCoffeeTypes(Map<String, CoffeeConfiguration> coffeeTypes) {
		this.coffeeTypes = coffeeTypes;
	}

	public Integer getMinIdleVol() {
		return minIdleVol;
	}

	public void setMinIdleVol(Integer minIdleVol) {
		this.minIdleVol = minIdleVol;
	}

	public Integer getMaxIdleVol() {
		return maxIdleVol;
	}

	public void setMaxIdleVol(Integer maxIdleVol) {
		this.maxIdleVol = maxIdleVol;
	}

	public Integer getMinIdleAmp() {
		return minIdleAmp;
	}

	public void setMinIdleAmp(Integer minIdleAmp) {
		this.minIdleAmp = minIdleAmp;
	}

	public Integer getMaxIdleAmp() {
		return maxIdleAmp;
	}

	public void setMaxIdleAmp(Integer maxIdleAmp) {
		this.maxIdleAmp = maxIdleAmp;
	}

	public Integer getSamplesPerSecond() {
		return samplesPerSecond;
	}

	public void setSamplesPerSecond(Integer samplesPerSecond) {
		this.samplesPerSecond = samplesPerSecond;
	}


	

}
