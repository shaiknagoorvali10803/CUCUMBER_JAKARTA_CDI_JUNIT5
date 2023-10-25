package com.csx.StepDefinitions;


import com.csx.Page.Google.GooglePage;
import com.csx.Page.Visa.VisaRegistrationPage;
import com.csx.Utils.ScenarioContext;
import com.csx.Utils.WebDriverProvider;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class VisaSteps {

    @Inject
    protected WebDriverProvider driverProvider;

    protected WebDriverWait wait;

    @Inject
    private VisaRegistrationPage visaRegistrationPage;

    @Inject
    private GooglePage googlePage;
    @Inject
    ScenarioContext scenarioContext;
    private static Map<Integer, ScenarioContext> contextMap = new HashMap<>();

    @PostConstruct
    private void init(){
        PageFactory.initElements(this.driverProvider.getInstance(), this);
        contextMap.put(driverProvider.getInstance().hashCode(),scenarioContext);
    }
    @Given("I am on VISA registration form")
    public void launchSite() {
        this.driverProvider.getInstance().navigate().to("https://vins-udemy.s3.amazonaws.com/sb/visa/udemy-visa.html");
        System.out.println("Current Thread Number "+ Thread.currentThread().getThreadGroup() +"thread number"+ Thread.currentThread().getId());
        //Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
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
        //Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        this.visaRegistrationPage.submit();
        System.out.println("hashcode driver "+driverProvider.getInstance().hashCode());
        }

    @Then("I should see get the confirmation number")
    public void verifyConfirmationNumber() throws InterruptedException {
        boolean isEmpty = StringUtils.isEmpty(this.visaRegistrationPage.getConfirmationNumber().trim());
        System.out.println("Current Thread Number "+ Thread.currentThread().getThreadGroup() +"thread number"+ Thread.currentThread().getId());
        Assertions.assertFalse(isEmpty);
        Thread.sleep(2000);
    }

   }
