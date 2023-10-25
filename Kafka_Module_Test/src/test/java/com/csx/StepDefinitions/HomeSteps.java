package com.csx.StepDefinitions;



import com.csx.Page.Homepage.HomePage;
import com.csx.Utils.WebDriverProvider;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
@ApplicationScoped
public class HomeSteps {
    @Inject
    private HomePage homePage;
    @Inject
    protected WebDriverProvider driverProvider;

    protected WebDriverWait wait;

    @PostConstruct
    private void init(){
        PageFactory.initElements(this.driverProvider.getInstance(), this);
    }

    @Given("I am Google Page")
    public void launchSite() {
        this.homePage.goTo();
         }

    @When("Search for the Word {string}")
    public void enterKeyword(String keyword) {
        this.homePage.search(keyword);
    }

}
