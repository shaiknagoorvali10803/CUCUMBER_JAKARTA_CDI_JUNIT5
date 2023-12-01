package com.csx.stepDefinitions;

import com.csx.page.actions.HomePageActions;
import com.csx.test.util.WebDriverProvider;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
@Singleton
public class HomeSteps {
    @Inject
    private HomePageActions homePage;
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
    public void enterKeyword(String keyword) throws InterruptedException {
        this.homePage.search(keyword);
    }

}
