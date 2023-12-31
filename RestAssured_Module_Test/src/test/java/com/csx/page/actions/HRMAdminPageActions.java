package com.csx.page.actions;

import com.csx.test.util.WebDriverProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;
@ApplicationScoped
public class HRMAdminPageActions {
    @Inject
    WebDriverProvider driverProvider;
    private WebDriverWait driverWait;
    @Inject
    HRMAdminPageObjects pageObjects;

    private void setup() {
        PageFactory.initElements(driverProvider.getInstance(), pageObjects);
        driverWait = new WebDriverWait(driverProvider.getInstance(),Duration.ofSeconds(60));
    }

    private WebDriverWait wait = new WebDriverWait(driverProvider.getInstance(), Duration.ofSeconds(30));

    public void navigateToUserManagement() throws InterruptedException {
        pageObjects.admin_link.click();
        Thread.sleep(2000);
        Actions action = new Actions(driverProvider.getInstance());
        action.moveToElement(pageObjects.employmentStatus).perform();
        Thread.sleep(2000);
        pageObjects.users.click();
        Thread.sleep(2000);
    }

    public String randomNumber() {
        Random rand = new Random();
        return Integer.toString(rand.nextInt(1000));

    }
}
