package com.csx.runner;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.csx.stepDefinitions")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "classpath:features")
@ConfigurationParameter(key= FILTER_TAGS_PROPERTY_NAME,value = "")
@ConfigurationParameter(key= EXECUTION_DRY_RUN_PROPERTY_NAME,value = "false")
@ConfigurationParameter(key= PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME,value = "true")
@ConfigurationParameter(key= EXECUTION_MODE_FEATURE_PROPERTY_NAME,value = "same_thread")

public class CucumberTest {
}
