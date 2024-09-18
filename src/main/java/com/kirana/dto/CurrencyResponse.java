package com.kirana.dto;

import lombok.Data;
import java.util.Map;

@Data
public class CurrencyResponse {
    private String base;
    private Map<String, Double> rates;
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public Map<String, Double> getRates() {
		return rates;
	}
	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}
    
    
}
