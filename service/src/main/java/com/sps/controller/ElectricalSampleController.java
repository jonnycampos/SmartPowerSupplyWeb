package com.sps.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.FileCopyUtils;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ElectricalSampleController {

    @Autowired
    private ElectricalSampleService electricalSampleService = new ElectricalSampleService();

    
	Logger logger = LoggerFactory.getLogger(ElectricalSampleService.class);

    
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
    
    
    @CrossOrigin
    @PostMapping(value="/electricaldata/export")
    public void exportTimeSeries(@RequestBody ElectricalDateConfig electricalDateConfig, HttpServletResponse response) {
    	File file = electricalSampleService.exportTimeSeries(electricalDateConfig);
    	if (file.exists()) {
			//get the mimetype
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				//unknown mimetype so set the mimetype to application/octet-stream
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			InputStream inputStream;
			try {
				inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			} catch (Exception e) {
				logger.error("Error returning file", e);
			}

    	}
 
    	
    }
    

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String healthCheckResponse() {
        return "Nothing here, used for health check. Try /electricaldata instead.";
    }

    
}