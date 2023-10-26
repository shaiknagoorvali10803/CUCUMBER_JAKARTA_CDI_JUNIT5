package com.csx.StepDefinitions;


import com.csx.Page.Google.GooglePage;
import com.csx.Utils.ScenarioContext;
import com.csx.Utils.SeleniumUtils;
import com.csx.Utils.WebDriverProvider;
import com.google.common.util.concurrent.Uninterruptibles;
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
    private GooglePage googlePage;
    @Inject
    ScenarioContext scenarioContext;

    @PostConstruct
    private void init( ){
        PageFactory.initElements(this.driverProvider.getInstance(), this);
       }

   @Given("I am on the google site")
    public void launchSite() {
        this.googlePage.goTo();
        }
    @When("I enter {string} as a keyword")
    public void enterKeyword(String keyword) {
        this.googlePage.search(keyword);
        }

    @Then("I should see search results page")
    public void clickSearch() throws IOException {
        Assertions.assertTrue(this.googlePage.isAt());
       }
    @Then("I should see at least {int} results")
    public void verifyResults(int count) throws InterruptedException, IOException {
        Assertions.assertTrue(this.googlePage.getCount() >= count);
        SeleniumUtils.singleClick(driverProvider.getInstance(),By.xpath("//a[normalize-space()='Images']"));
        Thread.sleep(3000);
        driverProvider.getInstance().findElement(By.xpath("//a[normalize-space()='Videos']")).click();
        Thread.sleep(3000);
    }
 }
