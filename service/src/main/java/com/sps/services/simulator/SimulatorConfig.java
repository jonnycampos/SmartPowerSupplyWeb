package com.sps.services.simulator;

public class SimulatorConfig {

	private String type;
	private Integer seconds;
	private String coffeeType;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getSeconds() {
		return seconds;
	}
	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}
	public String getCoffeeType() {
		return coffeeType;
	}
	public void setCoffeeType(String coffeeType) {
		this.coffeeType = coffeeType;
	}
	
	@Override
	public String toString() {
		return "SimulatorConfig [type=" + type + ", seconds=" + seconds + ", coffeeType=" + coffeeType + "]";
	}
	
	
}
