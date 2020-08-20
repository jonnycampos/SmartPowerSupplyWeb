package com.sps;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.sps.repository.ElectricalSampleRepository;
import com.sps.repository.LabelInteractionRepository;


@SpringBootApplication(scanBasePackages={"com.sps.configuration","com.sps.repository","com.sps.services","com.sps.controller"})
@EnableMongoRepositories(basePackageClasses
	    = {
	        ElectricalSampleRepository.class, LabelInteractionRepository.class
	    })
public class ElectricalSampleApplication implements CommandLineRunner  {

    public static void main(String[] args) {
        SpringApplication.run(ElectricalSampleApplication.class, args);
    }



	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
}