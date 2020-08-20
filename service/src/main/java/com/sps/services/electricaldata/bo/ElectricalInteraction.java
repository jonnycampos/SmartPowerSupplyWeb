package com.sps.services.electricaldata.bo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;


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

	public ElectricalInteraction(LocalDateTime start, LocalDateTime end) {
		this.start = start;
		this.end = end;
		
		this.startFormatted = start.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
		this.endFormatted = start.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
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
	
	
	
	
}
