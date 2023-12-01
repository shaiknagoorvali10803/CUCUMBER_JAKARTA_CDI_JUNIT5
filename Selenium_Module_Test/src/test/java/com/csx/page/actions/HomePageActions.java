package com.csx.page.actions;

import com.csx.stepDefinitions.ScenarioContext;
import com.csx.test.util.ScreenshotUtils;
import com.csx.utils.AppConfigHolder;
import com.csx.test.util.WebDriverProvider;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

@Singleton
public class HomePageActions {
    @Inject
    ScreenshotUtils screenshotUtils;
    @Inject
    WebDriverProvider driverProvider;
    WebDriverWait wait;

    @Inject
    HomePageObjects pageObjects;
    String googleurl = AppConfigHolder.getInstance().googleurl();

    @PostConstruct
    private void init(){
        PageFactory.initElements(this.driverProvider.getInstance(), this.pageObjects);
    }

    public void goTo(){
        this.driverProvider.getInstance().get(googleurl);
    }

    public void search(final String keyword) throws InterruptedException {
        pageObjects.searchBox.sendKeys(keyword);
        screenshotUtils.insertScreenshot("screenshot");
        pageObjects.searchBox.sendKeys(Keys.TAB);
        pageObjects.searchBtns
                .stream()
                .filter(e -> e.isDisplayed() && e.isEnabled())
                .findFirst()
                .ifPresent(WebElement::click);
        Thread.sleep(3000);
        screenshotUtils.insertScreenshot("screenshot");
    }

}
