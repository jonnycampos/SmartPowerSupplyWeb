package com.sps.configuration;

import java.util.ArrayList;
import java.util.List;

public class CoffeeConfiguration {
	private String coffeeName;
	private List<CoffeeConfigurationRange> coffeeConfigurationRanges = new ArrayList<CoffeeConfigurationRange>();
	
	public String getCoffeeName() {
		return coffeeName;
	}
	public void setCoffeeName(String coffeeName) {
		this.coffeeName = coffeeName;
	}
	public List<CoffeeConfigurationRange> getCoffeeConfigurationRanges() {
		return coffeeConfigurationRanges;
	}
	public void setCoffeeConfigurationRanges(List<CoffeeConfigurationRange> coffeeConfigurationRanges) {
		this.coffeeConfigurationRanges = coffeeConfigurationRanges;
	}
	
	
}
