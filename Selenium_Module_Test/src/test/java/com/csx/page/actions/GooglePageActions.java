package com.csx.page.actions;

import com.csx.stepDefinitions.ScenarioContext;
import com.csx.test.util.ScreenshotUtils;
import com.csx.utils.AppConfigHolder;
import com.csx.test.util.WebDriverProvider;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Singleton
public class GooglePageActions {
    @Inject
    WebDriverProvider driverProvider;
    @Inject
    ScreenshotUtils screenshotUtils;
    @Inject
    GooglePageObjects pageObjects;

    WebDriverWait wait;
    String googleurl = AppConfigHolder.getInstance().googleurl();

    @PostConstruct
    private void init() {
        PageFactory.initElements(this.driverProvider.getInstance(), this.pageObjects);
        wait = new WebDriverWait(driverProvider.getInstance(), Duration.ofSeconds(60));
    }

    public void goTo() throws InterruptedException {
        driverProvider.getInstance().get(googleurl);
    }

    public void search(final String keyword) {
        pageObjects.searchBox.sendKeys(keyword);
        screenshotUtils.insertScreenshot("screenshot");
        pageObjects.searchBox.sendKeys(Keys.TAB);
        pageObjects.searchBtns
                .stream()
                .filter(e -> e.isDisplayed() && e.isEnabled())
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public int getCount() {
        return pageObjects.results.size();
    }

    public boolean isAt() {
        return this.wait.until((d) -> pageObjects.searchBox.isDisplayed());
    }
}
