package com.sps.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sps.services.ElectricalSampleService;
import com.sps.services.TuyaService;
import com.sps.services.electricaldata.bo.ElectricalDateConfig;
import com.sps.services.electricaldata.bo.ElectricalSampleList;

@RestController
public class TuyaController {

	
    @Autowired
    private TuyaService tuyaService = new TuyaService();

	
	Logger logger = LoggerFactory.getLogger(ElectricalSampleService.class);

	
    @CrossOrigin
    @PostMapping(value="/tuya/get")
    public ElectricalSampleList getElectricalData(@RequestBody ElectricalDateConfig electricalDateConfig, HttpServletResponse response) {
    	return tuyaService.getDataFromDevice(electricalDateConfig.getInitialTime(), electricalDateConfig.getEndTime());
    }
}
