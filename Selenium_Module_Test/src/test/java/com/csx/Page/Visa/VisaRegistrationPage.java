package com.csx.Page.Visa;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import com.csx.Page.Base;
import com.csx.Utils.ScenarioContext;
import com.csx.Utils.SeleniumUtils;
import com.csx.Utils.WebDriverProvider;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
public class VisaRegistrationPage extends Base {
    private static Map<Integer, ScenarioContext> contextMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(VisaRegistrationPage.class);

    @Inject
    WebDriverProvider driverProvider;
    private WebDriverWait wait;

    @FindBy(id ="first_4")
    private WebElement firstName;

    @FindBy(id ="last_4")
    private WebElement lastName;

    @FindBy(id = "input_46")
    private WebElement fromCountry;

    @FindBy(id = "input_47")
    private WebElement toCountry;

    @FindBy(id = "input_24_month")
    private WebElement month;

    @FindBy(id = "input_24_day")
    private WebElement day;

    @FindBy(id = "input_24_year")
    private WebElement year;

    @FindBy(id = "input_6")
    private WebElement email;

    @FindBy(id = "input_27_phone")
    private WebElement phone;

    @FindBy(id = "input_45")
    private WebElement comments;

    @FindBy(id = "submitBtn")
    private WebElement submit;

    @FindBy(id = "requestnumber")
    private WebElement requestNumber;

    @Inject
    ScenarioContext scenarioContext;
    @PostConstruct
    private void init(){
        wait= new WebDriverWait(driverProvider.getInstance(), Duration.ofSeconds(60));
        contextMap.put(driverProvider.getInstance().hashCode(),scenarioContext);
    }

   public void setNames(String firstName, String lastName){
        logger.info("Getting names : " + firstName);
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
    }

    public void setCountryFromAndTo(String countryFrom, String countryTo){
        new Select(this.fromCountry).selectByValue(countryFrom);
        new Select(this.toCountry).selectByValue(countryTo);
    }

    public void setBirthDate(LocalDate localDate){
        new Select(this.year).selectByVisibleText(String.valueOf(localDate.getYear()));
        new Select(this.day).selectByVisibleText(String.valueOf(localDate.getDayOfMonth()));
        new Select(this.month).selectByValue(localDate.getMonth().toString());
    }
    public void setContactDetails(String email, String phone){
        this.email.sendKeys(email);
        this.phone.sendKeys(phone);
    }

    public void setComments(String comments){
        this.comments.sendKeys(Objects.toString(comments, ""));
    }

    public void submit(){
        SeleniumUtils.clickUsingJavaScript(driverProvider.getInstance(),submit);
    }

    public String getConfirmationNumber(){
        this.wait.until((d) -> this.requestNumber.isDisplayed());
        return this.requestNumber.getText();
    }


}
