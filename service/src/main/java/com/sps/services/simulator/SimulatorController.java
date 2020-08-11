package com.sps.services.simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletResponse;

@RestController
public class SimulatorController {

    @Autowired
    private SimulatorService simulatorService= new SimulatorService();
    
    
    @RequestMapping(value="/simulate/idle/{seconds}", method=RequestMethod.GET)
    public void getAllElectricalSamples(HttpServletResponse response, 
    		  @PathVariable("seconds") int seconds) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        
        simulatorService.simulateIdle(seconds);
    }

    
       
}