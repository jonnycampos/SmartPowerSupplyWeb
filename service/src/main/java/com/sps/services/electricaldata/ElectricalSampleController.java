package com.sps.services.electricaldata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ElectricalSampleController {

    @Autowired
    private ElectricalSampleService electricalSampleService = new ElectricalSampleService();


    
    
    private ApplicationContext context;
    
    

    
    @RequestMapping(value="/electricaldata", method=RequestMethod.GET)
    public List<ElectricalSample> getAllElectricalSamples(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        LocalDateTime b = LocalDateTime.parse("2020-08-12T10:18:01.897");
        LocalDateTime a = LocalDateTime.parse("2020-08-12T10:18:01.497");
        return electricalSampleService.getElectricalSamples(a, b);
    }

    
    @RequestMapping(value="/save", method=RequestMethod.GET)
    public ElectricalSample saveElectricalData(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return electricalSampleService.saveRandomElectricalSample();
    }
    
    
    

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String healthCheckResponse() {
        return "Nothing here, used for health check. Try /electricaldata instead.";
    }

    
}