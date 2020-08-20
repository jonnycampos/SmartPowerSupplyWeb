package com.sps.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sps.services.LabelingService;
import com.sps.services.electricaldata.bo.ElectricalInteraction;

@RestController
public class LabelingController {
    
    @Autowired
    private LabelingService labelingService = new LabelingService();

    @CrossOrigin
    @PostMapping(value="/labeling/labels")
    public List<String> getAvailableLabels(HttpServletResponse response) {
        return labelingService.getAvailableLabels();
    }
	
	
    @CrossOrigin
    @PostMapping(value="/labeling/addlabel")
    public void performLabel(@RequestBody ElectricalInteraction label, HttpServletResponse response) {
    	labelingService.performLabel(label);
    }
}
