package com.csx.page.actions;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class VisaPageObjects {
    private static final Logger logger = LoggerFactory.getLogger(VisaPageObjects.class);

    @FindBy(id ="first_4")
    WebElement firstName;

    @FindBy(id ="last_4")
    WebElement lastName;

    @FindBy(id = "input_46")
    WebElement fromCountry;

    @FindBy(id = "input_47")
    WebElement toCountry;

    @FindBy(id = "input_24_month")
    WebElement month;

    @FindBy(id = "input_24_day")
    WebElement day;

    @FindBy(id = "input_24_year")
    WebElement year;

    @FindBy(id = "input_6")
    WebElement email;

    @FindBy(id = "input_27_phone")
    WebElement phone;

    @FindBy(id = "input_45")
    WebElement comments;

    @FindBy(id = "submitBtn")
    WebElement submit;

    @FindBy(id = "requestnumber")
    WebElement requestNumber;


}
