package com.sps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RestController;

import com.sps.services.ElectricalSampleService;
import com.sps.services.electricaldata.bo.ElectricalDateConfig;
import com.sps.services.electricaldata.bo.ElectricalInteraction;
import com.sps.services.electricaldata.bo.ElectricalSample;
import com.sps.services.electricaldata.bo.ElectricalSampleList;
import com.sps.services.simulator.bo.SimulatorConfig;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ElectricalSampleController {

    @Autowired
    private ElectricalSampleService electricalSampleService = new ElectricalSampleService();

    
//    @RequestMapping(value="/electricaldata/all", method=RequestMethod.GET)
//    public List<ElectricalSample> getAllElectricalSamples(HttpServletResponse response) {
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        LocalDateTime b = LocalDateTime.parse("2020-08-12T10:18:01.897");
//        LocalDateTime a = LocalDateTime.parse("2020-08-12T10:18:01.497");
//        return electricalSampleService.getElectricalSamples(a, b);
//    }

    
    @RequestMapping(value="/save", method=RequestMethod.GET)
    public ElectricalSample saveElectricalData(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return electricalSampleService.saveRandomElectricalSample();
    }
    
    
    @CrossOrigin
    @PostMapping(value="/electricaldata/get")
    public ElectricalSampleList getElectricalData(@RequestBody ElectricalDateConfig electricalDateConfig, HttpServletResponse response) {
    	return electricalSampleService.getElectricalSamples(electricalDateConfig.getInitialTime(), electricalDateConfig.getEndTime());
    }
    
    

    

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String healthCheckResponse() {
        return "Nothing here, used for health check. Try /electricaldata instead.";
    }

    
}