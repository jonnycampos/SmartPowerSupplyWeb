package com.sps.services.electricaldata;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(scanBasePackages={"com.sps.services"})
public class ElectricalSampleApplication implements CommandLineRunner  {

    public static void main(String[] args) {
        SpringApplication.run(ElectricalSampleApplication.class, args);
    }



	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
}