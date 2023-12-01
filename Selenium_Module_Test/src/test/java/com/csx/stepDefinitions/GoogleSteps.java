package com.csx.stepDefinitions;


import com.csx.page.actions.GooglePageActions;
import com.csx.test.util.ScreenshotUtils;
import com.csx.test.util.SeleniumUtil;
import com.csx.test.util.WebDriverProvider;
import com.google.common.util.concurrent.Uninterruptibles;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.time.Duration;

public class GoogleSteps {
    @Inject
    protected WebDriverProvider driverProvider;
    @Inject
    private GooglePageActions googlePage;
    @Inject
    ScenarioContext scenarioContext;

    @Inject
    ScreenshotUtils screenshotUtils;

    Scenario scenario;

    @PostConstruct
    private void init() {
        PageFactory.initElements(this.driverProvider.getInstance(), this.googlePage);
        scenario = scenarioContext.getScenario();
    }

    @Given("I am on the google site")
    public void launchSite() throws InterruptedException {
        this.googlePage.goTo();
    }

    @When("I enter {string} as a keyword")
    public void enterKeyword(String keyword) {
        this.googlePage.search(keyword);
    }

    @Then("I should see search results page")
    public void clickSearch() throws IOException {
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(4));
        Assertions.assertTrue(this.googlePage.isAt());
        screenshotUtils.insertScreenshot("screenshot");

    }

    @Then("I should see at least {int} results")
    public void verifyResults(int count) throws InterruptedException, IOException {
        Assertions.assertTrue(this.googlePage.getCount() >= count);
        SeleniumUtil.clickElementByJS(driverProvider.getInstance(), "//a[normalize-space()='Images']");
        screenshotUtils.insertScreenshot("screenshot");
        Thread.sleep(3000);
        System.out.println("Current Thread Number " + Thread.currentThread().getThreadGroup() + "thread number" + Thread.currentThread().getId());
        driverProvider.getInstance().findElement(By.xpath("//a[normalize-space()='Videos']")).click();
        screenshotUtils.insertScreenshot("screenshot");
        Thread.sleep(3000);
    }
}
