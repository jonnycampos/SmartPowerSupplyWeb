package com.sps.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.sps.configuration.CoffeeConfiguration;
import com.sps.configuration.CoffeeConfigurationRange;
import com.sps.configuration.DeviceSimulatorConfiguration;
import com.sps.repository.ElectricalSampleRepository;
import com.sps.services.electricaldata.bo.ElectricalSample;
import com.sps.services.simulator.bo.SimulatorConfig;

@Configuration
@EnableConfigurationProperties(DeviceSimulatorConfiguration.class)
@Service
public class SimulatorService {
	
	Logger logger = LoggerFactory.getLogger(ElectricalSampleService.class);
	
    @Autowired
    private DeviceSimulatorConfiguration simulatorConfig;

    @Autowired
    private ElectricalSampleRepository repository;
    
    /**
     * Generate a sample when the device is idle
     * @param time Time of the sample
     * @return Electrical Sample generated
     */
	public ElectricalSample simulateIdleSample(LocalDateTime time) {
		Integer amp = randomValue(simulatorConfig.getMinIdleAmp(), simulatorConfig.getMaxIdleAmp());
		Integer vol = randomValue(simulatorConfig.getMinIdleVol(), simulatorConfig.getMaxIdleVol());
		ElectricalSample electricalSample = new ElectricalSample(time, amp, vol);		
		repository.save(electricalSample);
		return electricalSample;
	}
	
	
    /**
     * Generate a sample when the device is idle
     * @param time Time of the sample
     * @return Electrical Sample generated
     */
	public void simulateIdle(Integer seconds, @Nullable LocalDateTime time) {
		Integer samplesPerSecond = simulatorConfig.getSamplesPerSecond();
		logger.info("Simulating idle device:" + seconds + " frecuency " + samplesPerSecond);
		LocalDateTime sampleTime;
		if (time == null) { 
			sampleTime = LocalDateTime.now();
		} else {
			sampleTime = time;
		}
		
		IntStream.range(0, seconds*samplesPerSecond).forEach(
				i -> 
				simulateIdleSample(sampleTime.plus(i*(1000 / samplesPerSecond), ChronoField.MILLI_OF_DAY.getBaseUnit())));
	}
	
	
	/**
	 * Generate the electrical samples for a coffee type
	 * @param coffeeType One of the coffee types configured in simulator.properties
	 */
	public void simulateCoffee(String coffeeType, @Nullable LocalDateTime time) {
		logger.info("Simulating coffee interaction:" + coffeeType);
		LocalDateTime sampleTime;
		if (time == null) { 
			sampleTime = LocalDateTime.now();
		} else {
			sampleTime = time;
		}
		CoffeeConfiguration coffeeConfiguration = simulatorConfig.getCoffeeTypes().get(coffeeType);
		if (coffeeConfiguration == null) {
			logger.error(coffeeType + " is not configured in the simulator");
		    return;
		}
		
		for (CoffeeConfigurationRange range: coffeeConfiguration.getCoffeeConfigurationRanges()) {
			simulateCoffeeRange(range, time);
			sampleTime = sampleTime.plusSeconds(range.getTime());
		}
	}
	
	
	/**
	 * Launch the simulator and store electrical samples for a list
	 * of actions. 
	 * @param simulatorConfigList
	 * @return Number of samples stored
	 */
	public Integer simulate(List<SimulatorConfig> simulatorConfigList) {
		LocalDateTime time = LocalDateTime.now();
		logger.info("Simulate this:" + simulatorConfigList);
		Integer totalSecondsSimulation = 0;
		for (SimulatorConfig config: simulatorConfigList) {
			//Simulator Type
			if (config.getType().equals("coffee")) {
				simulateCoffee(config.getCoffeeType(),time);
				time = time.plusSeconds(simulatorConfig.getCoffeeTypes().get(config.getCoffeeType()).getTotalSeconds());
				totalSecondsSimulation += simulatorConfig.getCoffeeTypes().get(config.getCoffeeType()).getTotalSeconds();
			} else if (config.getType().equals("idle")) {
				simulateIdle(config.getSeconds(),time);
				time = time.plusSeconds(config.getSeconds());
				totalSecondsSimulation += config.getSeconds();
			} else {
				logger.error(config.getType() + " is not type in the simulator");
			}
		}
		return simulatorConfig.getSamplesPerSecond() * totalSecondsSimulation;
	}
	
	
	
	private void simulateCoffeeRange(CoffeeConfigurationRange range, LocalDateTime time) {
		Integer seconds = range.getTime();
		Integer samplesPerSecond = simulatorConfig.getSamplesPerSecond();
		IntStream.range(0, seconds*samplesPerSecond).forEach(
				i -> 
				simulateCoffeeSample(range, time.plus(i*(1000 / samplesPerSecond), ChronoField.MILLI_OF_DAY.getBaseUnit())));		
	}


	private Object simulateCoffeeSample(CoffeeConfigurationRange range, LocalDateTime time) {
		Integer amp = randomValue(range.getMinAmp(), range.getMaxAmp());
		Integer vol = randomValue(range.getMinVol(), range.getMaxVol());
		ElectricalSample electricalSample = new ElectricalSample(time, amp, vol);		
		repository.save(electricalSample);
		return electricalSample;
	}


	/**
	 * Return a random value between two integers
	 * @param a
	 * @param b 
	 * @return A Random value between a and b
	 */
	private Integer randomValue(Integer a, Integer b) {
		//Random random = new Random(b - a);
		//return random.nextInt() + a;
		return (int)(Math.random() * (b-a+1))+ a;
	}
}
