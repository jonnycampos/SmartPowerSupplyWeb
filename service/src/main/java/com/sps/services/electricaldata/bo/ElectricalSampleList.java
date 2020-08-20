package com.sps.services.electricaldata.bo;

import java.util.List;

public class ElectricalSampleList {

    private List<ElectricalSample> electricalSamples;
    
    private List<ElectricalInteraction> electricalInteractions;


    public ElectricalSampleList() {}


	public List<ElectricalSample> getElectricalSamples() {
		return electricalSamples;
	}


	public void setElectricalSamples(List<ElectricalSample> electricalSamples) {
		this.electricalSamples = electricalSamples;
	}


	public List<ElectricalInteraction> getElectricalInteractions() {
		return electricalInteractions;
	}


	public void setElectricalInteractions(List<ElectricalInteraction> electricalInteractions) {
		this.electricalInteractions = electricalInteractions;
	}

    
}
