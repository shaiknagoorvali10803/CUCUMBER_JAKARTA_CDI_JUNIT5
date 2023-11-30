package com.csx.page.actions;

import com.csx.test.util.WebDriverProvider;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
@ApplicationScoped
public class HRMDashboardPageActions {
    @Inject
    WebDriverProvider driverProvider;
    private WebDriverWait driverWait;
    @Inject
    HRMDashboardPageObjects pageObjects;

    @PostConstruct
    private void setup() {
        PageFactory.initElements(driverProvider.getInstance(), pageObjects);
        driverWait = new WebDriverWait(driverProvider.getInstance(), Duration.ofSeconds(60));
    }

    public String verifyassignleave_link() throws InterruptedException {
        driverWait.until(ExpectedConditions.visibilityOf(pageObjects.assignleave_link)).isDisplayed();
        pageObjects.assignleave_link.click();
        Thread.sleep(2000);
        return pageObjects.assignleave_menu.getText();
    }

    public String verifyleavelist_link() throws InterruptedException {
        driverWait.until(ExpectedConditions.visibilityOf(pageObjects.leavelist_link)).isDisplayed();
        pageObjects.leavelist_link.click();
        Thread.sleep(2000);
        return pageObjects.leavelist_menu.getText();
    }

    public String verifyUserAccess() throws InterruptedException {
        Thread.sleep(2000);
        driverWait.until(ExpectedConditions.visibilityOf(pageObjects.dashboard_menu)).isDisplayed();
        Thread.sleep(2000);
        return pageObjects.dashboard_menu.getText();
    }
}
