package com.csx.stepDefinitions;


import com.csx.page.actions.GooglePageActions;
import com.csx.page.actions.VisaPageActions;
import com.csx.test.util.WebDriverProvider;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;

public class VisaSteps {

    @Inject
    WebDriverProvider driverProvider;

    @Inject
    VisaPageActions visaRegistrationPage;

    @Inject
    GooglePageActions googlePage;
    @Inject
    ScenarioContext scenarioContext;

    Scenario scenario;

    @PostConstruct
    private void init() {
        PageFactory.initElements(this.driverProvider.getInstance(), this);
        scenario = scenarioContext.getScenario();
    }

    @Given("I am on VISA registration form")
    public void launchSite() {
        this.driverProvider.getInstance().navigate().to("https://vins-udemy.s3.amazonaws.com/sb/visa/udemy-visa.html");
    }

    @When("I select my from country {string} and to country {string}")
    public void selectCountry(String from, String to) {
        this.visaRegistrationPage.setCountryFromAndTo(from, to);
    }

    @And("I enter my dob as {string}")
    public void enterDob(String dob) {
        this.visaRegistrationPage.setBirthDate(LocalDate.parse(dob));
    }

    @And("I enter my name as {string} and {string}")
    public void enterNames(String fn, String ln) {
        this.visaRegistrationPage.setNames(fn, ln);
    }

    @And("I enter my contact details as {string} and {string}")
    public void enterContactDetails(String email, String phone) {
        this.visaRegistrationPage.setContactDetails(email, phone);
    }

    @And("I enter the comment {string}")
    public void enterComment(String comment) {
        this.visaRegistrationPage.setComments(comment);
    }

    @And("I submit the form")
    public void submit() {
        this.visaRegistrationPage.submit();
    }

    @Then("I should see get the confirmation number")
    public void verifyConfirmationNumber() throws InterruptedException {
        boolean isEmpty = StringUtils.isEmpty(this.visaRegistrationPage.getConfirmationNumber().trim());
        Assertions.assertFalse(isEmpty);
        Thread.sleep(2000);
    }

}
