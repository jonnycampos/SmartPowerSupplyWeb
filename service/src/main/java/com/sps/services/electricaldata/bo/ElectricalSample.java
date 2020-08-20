package com.sps.services.electricaldata.bo;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "electricalSample")
public class ElectricalSample {

	@Id
	private String id;
	private LocalDateTime time;
    private Integer ma;
    private Integer v;
    private String label;

    
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public ElectricalSample(Boolean isRandom) {
		if (isRandom) {
			this.time = LocalDateTime.now();
			this.ma = new Random().nextInt(1000);
			this.v = new Random().nextInt(1000);
		}
    }
	
	public ElectricalSample() {
    }

	public ElectricalSample(LocalDateTime time, Integer ma, Integer v) {
		this.time = time;
		this.ma = ma;
		this.v = v;
	}
	
	public ElectricalSample(Integer ma, Integer v) {
		this.time = LocalDateTime.now();
		this.ma = ma;
		this.v = v;
	}


	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Integer getMa() {
		return ma;
	}

	public void setMa(Integer ma) {
		this.ma = ma;
	}

	public Integer getV() {
		return v;
	}

	public void setV(Integer v) {
		this.v = v;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
    
    
    

}
