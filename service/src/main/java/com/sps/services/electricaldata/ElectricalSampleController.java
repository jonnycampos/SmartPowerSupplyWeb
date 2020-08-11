package com.sps.services.electricaldata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ElectricalSampleController {

    @Autowired
    private ElectricalSampleService electricalSampleService = new ElectricalSampleService();


    
    
    private ApplicationContext context;
    
    

    
    @RequestMapping(value="/electricaldata", method=RequestMethod.GET)
    public ElectricalSampleList getAllElectricalSamples(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        return electricalSampleService.getAllElectricalSamples();
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