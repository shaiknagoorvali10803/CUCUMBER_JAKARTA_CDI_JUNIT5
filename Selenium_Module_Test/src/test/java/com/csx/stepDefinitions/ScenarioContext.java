package com.csx.stepDefinitions;

import io.cucumber.java.Scenario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
@Singleton
public class ScenarioContext {
	private Scenario scenario;
	private Map<String, Object> contextData;
	public Scenario getScenario() {
        return scenario;
    }
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }
	public Map<String, Object> getContextData() {
		return contextData;
	}
	public void setContextData(Map<String, Object> contextData) {
		this.contextData = contextData;
	}
    public void clearContextData() {
    	this.contextData = new HashMap<>();
    }
}
