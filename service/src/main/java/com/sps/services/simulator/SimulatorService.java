package com.sps.services.simulator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Random;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.sps.configuration.CoffeeConfiguration;
import com.sps.configuration.CoffeeConfigurationRange;
import com.sps.configuration.DeviceSimulatorConfiguration;
import com.sps.services.electricaldata.ElectricalSample;
import com.sps.services.electricaldata.ElectricalSampleRepository;
import com.sps.services.electricaldata.ElectricalSampleService;

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
	public void simulateIdle(Integer seconds) {
		Integer samplesPerSecond = simulatorConfig.getSamplesPerSecond();
		logger.info("Simulating idle device:" + seconds + " frecuency " + samplesPerSecond);
		LocalDateTime time = LocalDateTime.now();
		
		IntStream.range(0, seconds*samplesPerSecond).forEach(
				i -> 
				simulateIdleSample(time.plus(i*(1000 / samplesPerSecond), ChronoField.MILLI_OF_DAY.getBaseUnit())));
	}
	
	
	/**
	 * Generate the electrical samples for a coffee type
	 * @param coffeeType One of the coffee types configured in simulator.properties
	 */
	public void simulateCoffee(String coffeeType) {
		LocalDateTime time = LocalDateTime.now();
		CoffeeConfiguration coffeeConfiguration = simulatorConfig.getCoffeeTypes().get(coffeeType);
		if (coffeeConfiguration == null) {
			logger.error(coffeeType + " is not configured in the simulator");
		    return;
		}
		
		for (CoffeeConfigurationRange range: coffeeConfiguration.getCoffeeConfigurationRanges()) {
			simulateCoffeeRange(range, time);
			time = time.plusSeconds(range.getTime());
		}
		
		
		
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
