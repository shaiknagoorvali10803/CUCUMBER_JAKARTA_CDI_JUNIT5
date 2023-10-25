package com.csx.runner;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.csx.StepDefinitions")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "classpath:features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:")

public class CucumberTest {
}
