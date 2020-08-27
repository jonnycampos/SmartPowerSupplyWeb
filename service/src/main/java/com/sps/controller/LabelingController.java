package com.sps.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sps.services.ElectricalSampleService;
import com.sps.services.LabelingService;
import com.sps.services.electricaldata.bo.ElectricalDateConfig;
import com.sps.services.electricaldata.bo.ElectricalInteraction;

@RestController
public class LabelingController {
    
    @Autowired
    private LabelingService labelingService = new LabelingService();

	Logger logger = LoggerFactory.getLogger(ElectricalSampleService.class);

    
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
    

    @CrossOrigin
    @PostMapping(value="/labeling/interactionlabeled")
    public List<ElectricalInteraction> getInteractionsLabeled(HttpServletResponse response) {
    	return labelingService.getAllInteractionsLabeled();
    }

    @CrossOrigin
    @PostMapping(value="/labeling/export")
    public void exportInteractionsLabeled(@RequestBody List<String> idList,HttpServletResponse response) {
    	File file = labelingService.exportLabeledInteractions(idList);
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

    @CrossOrigin
    @PostMapping(value="/labeling/predict")
    public String predictLabel(@RequestBody ElectricalDateConfig electricalDateConfig,  HttpServletResponse response) {
    	return labelingService.predictLabel(electricalDateConfig);
    }
    

}
