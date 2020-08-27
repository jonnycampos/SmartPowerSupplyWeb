package com.sps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.sps.services.ElectricalSampleService;
import com.sps.services.SimulatorService;
import com.sps.services.electricaldata.bo.ElectricalSample;
import com.sps.services.electricaldata.bo.ElectricalSampleList;
import com.sps.services.simulator.bo.SimulatorConfig;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

@RestController
public class SimulatorController {

    @Autowired
    private SimulatorService simulatorService= new SimulatorService();
    
    @Autowired
    private ElectricalSampleService electricalSampleService = new ElectricalSampleService();
  
    
    
    @RequestMapping(value="/simulate/idle/{seconds}", method=RequestMethod.GET)
    public void simulateIdle(HttpServletResponse response, 
    		  @PathVariable("seconds") int seconds) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        simulatorService.simulateIdle(seconds, null);
    }

    @CrossOrigin
    @PostMapping(value="/simulate/chain")
    public ElectricalSampleList simulateChain(@RequestBody List<SimulatorConfig> simulatorConfigList, HttpServletResponse response) {
        //Create simulation
    	Integer numberOfSamples = simulatorService.simulate(simulatorConfigList);
    	return electricalSampleService.getLastElectricalSamples(numberOfSamples);
    }
       
}