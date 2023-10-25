package com.csx.StepDefinitions;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.csx.Utils.LoggingException;
import com.csx.Utils.ScenarioContext;
import com.csx.Utils.WebDriverProvider;
import io.cucumber.java.After;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hooks {
	@Inject
	private WebDriverProvider driverProvider;

	@Inject
	private ScenarioContext scenarioContext;

	@Before("@chrome")
	public void onlyChromeBrowser(final Scenario scenario) {
		driverProvider.generateWebDriver(WebDriverProvider.BrowserType.CHROME);
		scenarioContext.setScenario(scenario);
		System.out.println(scenarioContext.getScenario().getName());

	}
	/*
	@AfterStep
	public void TakeSceenShit(final Scenario scenario){
		final byte[] screenshot = ((TakesScreenshot) driverProvider.getInstance()).getScreenshotAs(OutputType.BYTES);
		scenario.attach(screenshot, "image/png", scenario.getName());
	}

	 */

	@After()
	public void afterMethod(final Scenario scenario) throws LoggingException {
		scenarioContext.clearContextData();
		System.out.println("execution entered in After Annotation");
		driverProvider.getInstance().quit();
	}

	private String captureScreenshot(final WebDriver driver, final Scenario scenario) throws IOException {
		final Date now = new Date();
		final String dateString = new SimpleDateFormat("dd-MMM-yyy").format(now);
		final String dateAndTimeString = new SimpleDateFormat("dd-MMM-yyyy hh-mm-ss a z").format(now);

		final TakesScreenshot screenShot = (TakesScreenshot) driver;
		final File source = screenShot.getScreenshotAs(OutputType.FILE);
		final String dest = ".." + File.separator + "target" + File.separator + "cucumber-html-reports" + File.separator
				+ dateString + File.separator + "Error_" + dateAndTimeString + ".png";
		final File destination = new File(dest);
		FileUtils.copyFile(source, destination);
		ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(destination.getAbsolutePath());
		return dest;
	}

}
