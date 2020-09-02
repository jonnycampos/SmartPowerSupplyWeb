package com.sps.services.electricaldata.bo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sps.util.SPSUtils;


@Document(collection = "electricalLabel")
public class ElectricalInteraction {
	
	@Id
	private String id;
	
	private LocalDateTime start;
	
	private LocalDateTime end;
	
	@ReadOnlyProperty
	private String startFormatted;
	
	@ReadOnlyProperty
	private String endFormatted;
	
	private String label;

	
	private Double meanV;
	private Double meanA;
	private Double medianV;
	private Double medianA;
	private Integer modeV;
	private Integer modeA;
	private Integer maxV;
	private Integer maxA;
	private Integer minV;
	private Integer minA;
	
	
	
	
	public ElectricalInteraction(LocalDateTime start, LocalDateTime end) {
		this.start = start;
		this.end = end;
		
		this.startFormatted = start.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS"));
		this.endFormatted = end.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS"));
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getStartFormatted() {
		return startFormatted;
	}

	public void setStartFormatted(String startFormatted) {
		this.startFormatted = startFormatted;
	}

	public String getEndFormatted() {
		return endFormatted;
	}

	public void setEndFormatted(String endFormatted) {
		this.endFormatted = endFormatted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getMeanV() {
		return meanV;
	}

	public void setMeanV(Double meanV) {
		this.meanV = meanV;
	}

	public Double getMeanA() {
		return meanA;
	}

	public void setMeanA(Double meanA) {
		this.meanA = meanA;
	}

	public Double getMedianV() {
		return medianV;
	}

	public void setMedianV(Double medianV) {
		this.medianV = medianV;
	}

	public Double getMedianA() {
		return medianA;
	}

	public void setMedianA(Double medianA) {
		this.medianA = medianA;
	}

	public Integer getModeV() {
		return modeV;
	}

	public void setModeV(Integer modeV) {
		this.modeV = modeV;
	}

	public Integer getModeA() {
		return modeA;
	}

	public void setModeA(Integer modeA) {
		this.modeA = modeA;
	}

	public Integer getMaxV() {
		return maxV;
	}

	public void setMaxV(Integer maxV) {
		this.maxV = maxV;
	}

	public Integer getMaxA() {
		return maxA;
	}

	public void setMaxA(Integer maxA) {
		this.maxA = maxA;
	}

	public Integer getMinV() {
		return minV;
	}

	public void setMinV(Integer minV) {
		this.minV = minV;
	}

	public Integer getMinA() {
		return minA;
	}

	public void setMinA(Integer minA) {
		this.minA = minA;
	}
	
	
	
	
}
