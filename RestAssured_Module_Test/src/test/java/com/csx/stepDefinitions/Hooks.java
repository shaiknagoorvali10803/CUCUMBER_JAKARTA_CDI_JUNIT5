package com.csx.stepDefinitions;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.csx.test.util.FileHandlingUtil;
import com.csx.test.util.VideoRecorder;
import com.csx.utils.LoggingException;
import com.csx.test.util.WebDriverProvider;
import io.cucumber.java.After;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import jakarta.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hooks{
	public static final String LOCAL_VIDEO_RECORD_FLAG = "localVideoRecord";
	public static final String downloadPath = System.getProperty("user.dir");
	public static final String videoFileType = "avi";
	@Inject
	private WebDriverProvider driverProvider;

	@Inject
	private ScenarioContext scenarioContext;

	@Before("@Chrome and not (@Headless or @Firefox or @Edge)")
	public void chromeDriver(final Scenario scenario) throws Exception {
		driverProvider.generateWebDriver(WebDriverProvider.BrowserType.CHROME);
		scenarioContext.setScenario(scenario);
		localVideoRecord();
	}
	@Before("(@Chrome and @Headless) and not (@Firefox or @Edge)")
	public void chromeHeadless(final Scenario scenario) throws Exception {
		driverProvider.generateWebDriver(WebDriverProvider.BrowserType.CHROME, true);
		scenarioContext.setScenario(scenario);
		localVideoRecord();
	}

	@Before("@Edge and not (@Headless or @Firefox or @Chrome)")
	public void edgeBrowser(final Scenario scenario) throws Exception {
		driverProvider.generateWebDriver(WebDriverProvider.BrowserType.EDGE);
		scenarioContext.setScenario(scenario);
		localVideoRecord();
	}
	@Before("(@Edge and @Headless) and not (@Firefox or @Chrome)")
	public void edgeBrowserWithHeadless(final Scenario scenario) throws Exception {
		driverProvider.generateWebDriver(WebDriverProvider.BrowserType.EDGE, true);
		scenarioContext.setScenario(scenario);
		localVideoRecord();
	}

	@Before("@Firefox and not (@Headless or @Edge or @Chrome)")
	public void firefoxBrowser(final Scenario scenario) throws Exception {
		driverProvider.generateWebDriver(WebDriverProvider.BrowserType.FIRE_FOX);
		scenarioContext.setScenario(scenario);
		localVideoRecord();
	}
	@Before("(@Firefox and @Headless) and not (@Edge or @Chrome)")
	public void firefoxBrowserwithHeadless(final Scenario scenario) throws Exception {
		driverProvider.generateWebDriver(WebDriverProvider.BrowserType.FIRE_FOX, true);
		scenarioContext.setScenario(scenario);
		localVideoRecord();
	}

	@Before("not (@Chrome or @IE or @Safari or @Firefox or @Edge)")
	public void defaultBrowser(final Scenario scenario) throws Exception {
		driverProvider.generateWebDriver(WebDriverProvider.BrowserType.CHROME, true);
		scenarioContext.setScenario(scenario);
		localVideoRecord();
	}

	@After()
	public void afterMethod(final Scenario scenario) throws Exception {
		scenarioContext.clearContextData();
		if (scenario.isFailed()) {
			try {
				if (!BooleanUtils.toBoolean(System.getProperty("buildToolRun"))) {
					VideoRecorder.stopRecording();
				}
			} catch (ClassCastException | IOException |NullPointerException e) {
				throw new LoggingException(e);
			}
			scenario.attach(((TakesScreenshot) driverProvider.getInstance()).getScreenshotAs(OutputType.BYTES), "image/png", scenario.getName());
		}
		else {
			if (!BooleanUtils.toBoolean(System.getProperty("buildToolRun"))) {
				VideoRecorder.stopRecording();
				System.out.println("video recording ended");
				String fileName = FileHandlingUtil.getTheNewestFile(downloadPath, videoFileType);
				//FileHandlingUtil.deleteExistingFile(fileName);
			}
		}
		driverProvider.getInstance().quit();
	}

	private void localVideoRecord() throws Exception {
		if (!BooleanUtils.toBoolean(System.getProperty("buildToolRun"))) {
			VideoRecorder.startRecording();
		}
	}


}
