package com.sps.services.electricaldata.bo;

public class LikelyInInteractionResult {

	private Boolean isLikelyInIteraction;
	
	private ElectricalSample firstSample;
	
	private ElectricalSample lastSample;

	public Boolean getIsLikelyInIteraction() {
		return isLikelyInIteraction;
	}

	public void setIsLikelyInIteraction(Boolean isLikelyInIteraction) {
		this.isLikelyInIteraction = isLikelyInIteraction;
	}

	public ElectricalSample getFirstSample() {
		return firstSample;
	}

	public void setFirstSample(ElectricalSample firstSample) {
		this.firstSample = firstSample;
	}

	public ElectricalSample getLastSample() {
		return lastSample;
	}

	public void setLastSample(ElectricalSample lastSample) {
		this.lastSample = lastSample;
	}
	
	
	
	
}
