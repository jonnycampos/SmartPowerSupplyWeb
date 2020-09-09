package com.sps.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.sps.configuration.DeviceSimulatorConfiguration;
import com.sps.configuration.LabelingConfiguration;
import com.sps.repository.ElectricalSampleRepository;
import com.sps.repository.LabelInteractionRepository;
import com.sps.services.electricaldata.bo.ElectricalDateConfig;
import com.sps.services.electricaldata.bo.ElectricalInteraction;
import com.sps.services.electricaldata.bo.ElectricalSample;
import com.sps.util.SPSUtils;

@Configuration
@EnableConfigurationProperties({LabelingConfiguration.class,DeviceSimulatorConfiguration.class})
@Service
public class LabelingService {
	
    @Autowired
    private LabelingConfiguration labelingConfig;
    
    @Autowired
    private DeviceSimulatorConfiguration simulatorConfig;
	
    @Autowired
    private ElectricalSampleRepository repositorySample;
    
    @Autowired
    private LabelInteractionRepository repositoryLabel;
    
	Logger logger = LoggerFactory.getLogger(ElectricalSampleService.class);

    
    
    /**
     * Get label string list
     * @return
     */
	public List<String> getAvailableLabels() {
		logger.info("Retrieving name of labels");
		return labelingConfig.getLabels();
	}
	
	/**
	 * Save labeling from a particular period of samples
	 * - Labeling the samples
	 * - Adding a new record in the labeling table
	 * @param labelInfo
	 */
	public void performLabel(ElectricalInteraction labelInfo) {
		logger.info("Label from " + labelInfo.getStartFormatted() + " until " + labelInfo.getEndFormatted() + " - " + labelInfo.getLabel());
		//We will update the label in the samples database
		List<ElectricalSample> samples = repositorySample.findByTime(labelInfo.getStart(), labelInfo.getEnd());
		for (ElectricalSample sample : samples) {
			sample.setLabel(labelInfo.getLabel());
			repositorySample.save(sample);
		}
		
		//We do some calculations as well
		List<Integer> vList = samples.stream().map(ElectricalSample::getV).collect(Collectors.toList());
		List<Integer> aList = samples.stream().map(ElectricalSample::getMa).collect(Collectors.toList());

		
		
		//In another collection we will insert the label 
		ElectricalInteraction labeledSample = new ElectricalInteraction(labelInfo.getStart(), labelInfo.getEnd());
		labeledSample.setLabel(labelInfo.getLabel());
		
	    labeledSample.setMeanV(SPSUtils.mean(vList));
	    labeledSample.setMeanA(SPSUtils.mean(aList));
	    labeledSample.setMedianV(SPSUtils.median(vList));
	    labeledSample.setMedianA(SPSUtils.median(aList));
	    labeledSample.setModeV(SPSUtils.mode(vList));
	    labeledSample.setModeA(SPSUtils.mode(aList));
	    labeledSample.setMaxV(Collections.max(vList));
	    labeledSample.setMaxA(Collections.max(aList));
	    labeledSample.setMinV(Collections.min(vList));
	    labeledSample.setMinA(Collections.min(aList));
		
		repositoryLabel.save(labeledSample);
		logger.info("Labeled Done!!" );
	}
	
	
	/**
	 * Return all labeled interactions in the database
	 * @return List with start and end time, and with the labels
	 */
	public List<ElectricalInteraction> getAllInteractionsLabeled() {
		return repositoryLabel.findAll();
	}
	
	
	
	/**
	 * Export to csv data related to labels given during the input
	 * @param idList
	 * @return File with label data 
	 */
	public File exportLabeledInteractions(List<String> idList) {
		logger.info("Starting exporting labeled inderactions: " + idList);
		
		//Build the lines to write
		ArrayList<String> lines = new ArrayList<String>();
		lines.add(createHeader());
		for (String id:idList) {
			Optional<ElectricalInteraction> electricalInteracion = repositoryLabel.findById(id);
			if (electricalInteracion.isPresent()) {
				lines.add(createLine(electricalInteracion.get()));
			} else {
				logger.error("Line with id " + id + " does not exist");
			}
		}

		//Write the file
		File csvOutputFile = new File(labelingConfig.getExportFile());
	    
		try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
	    	lines.stream().forEach(pw::println);
		    logger.info("Ended writing the File");
	    	return csvOutputFile;
	    	
	    } catch (FileNotFoundException e) {
			logger.error("Error accesing the file " + labelingConfig.getExportFile(), e);
		}
	    
	    
	    return null;
	    
	}

	
	private String createHeader() {
		String SEPARATOR = ";";
		return String.join(SEPARATOR,
				"AmpPoints",
				"VolPoints",
				"NumberOfSamples",
				"MinA",
				"MinV",
			    "MaxA",
			    "MaxV",
			    "MeanA",
			    "MeanV",			    
			    "ModeA",
			    "ModeV",
			    "MedianA",
			    "MedianV",
				"Label");
	}

	/**
	 * Build the structure of one line to write in file
	 * @param labeledInteraction One labeled interaction
	 * @return String list:
	 * - Comma separated mA
	 * - Comma separated V
	 * - Milliseconds of the interaction
	 * - Label
	 */
	private String createLine(ElectricalInteraction labeledInteraction) {
		String SEPARATOR = ";";
		String listAmp = "";
		String listVol = "";
		List<ElectricalSample> samples = repositorySample.findByTime(labeledInteraction.getStart(), labeledInteraction.getEnd());
		
		listAmp = samples.stream().map(ElectricalSample::getMa).map(Object::toString).collect(Collectors.joining (","));
		listVol = samples.stream().map(ElectricalSample::getV).map(Object::toString).collect(Collectors.joining (","));
		return String.join(SEPARATOR,
				listAmp,
				listVol,
				String.valueOf(samples.size()),
				String.valueOf(labeledInteraction.getMinA()),
				String.valueOf(labeledInteraction.getMinV()),
			    String.valueOf(labeledInteraction.getMaxA()),
			    String.valueOf(labeledInteraction.getMaxV()),
			    String.valueOf(labeledInteraction.getMeanA()),
			    String.valueOf(labeledInteraction.getMeanV()),			    
			    String.valueOf(labeledInteraction.getModeA()),
			    String.valueOf(labeledInteraction.getModeV()),
			    String.valueOf(labeledInteraction.getMedianA()),
			    String.valueOf(labeledInteraction.getMedianV()),
				labeledInteraction.getLabel());
	}

	
	
	/**
	 * Call the classifier to predict what is happening during an interval of time
	 * @param electricalDateConfig With Start and End Date
	 * @return The Label predicted by the ML
	 */
	public String predictLabel(ElectricalDateConfig electricalDateConfig) {
		List<ElectricalSample> samples = repositorySample.findByTime(electricalDateConfig.getInitialTime(), electricalDateConfig.getEndTime());

		try {
        	//Build the json structure to send
            JSONObject obj = new JSONObject();
            // In this case, it's an array of arrays
            JSONArray dataItems = new JSONArray();
            JSONArray item1 = new JSONArray();
            item1.put(samples.stream().map(ElectricalSample::getMa).map(Object::toString).collect(Collectors.joining (",")));
            item1.put(samples.stream().map(ElectricalSample::getV).map(Object::toString).collect(Collectors.joining (",")));
        	item1.put(samples.size());
            dataItems.put(item1);
            obj.put("data", dataItems);
            logger.info("Classifier Call to " + labelingConfig.getClassifierURL()  + " using " + obj.toString());
        	HttpClient client = HttpClient.newBuilder().version(Version.HTTP_1_1).build();
        	HttpRequest request = HttpRequest.newBuilder(URI.create(labelingConfig.getClassifierURL()))
        			            .header("Content-Type", "application/json")
        			            .POST(HttpRequest.BodyPublishers.ofString(obj.toString())).build();
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            String responseParsed = response.body().replace("\\", "");
            responseParsed = responseParsed.substring(1, responseParsed.length()-1);
            //responseParsed = new JSONObject(responseParsed).getJSONArray("result").getString(0);
            logger.info("Classifier Call response " + responseParsed);
            return responseParsed;

        }
        catch (Exception e) {
        	logger.error("Error calling API",e);
        	return null;
        }

	}
}
