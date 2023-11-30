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
public class HRMLoginPageActions {
    @Inject
    WebDriverProvider driverProvider;
    private WebDriverWait driverWait;
    @Inject
    HRMLoginPageObjects pageObjects;
    @PostConstruct
    private void setup() {
        PageFactory.initElements(driverProvider.getInstance(), pageObjects);
        driverWait = new WebDriverWait(driverProvider.getInstance(), Duration.ofSeconds(30));
    }

    public void enterCredentials(String username, String password) {
        driverWait.until(ExpectedConditions.visibilityOf(pageObjects.txtUsername)).isDisplayed();
        driverWait.until(ExpectedConditions.visibilityOf(pageObjects.txtPassword)).isDisplayed();
        pageObjects.txtUsername.sendKeys(username);
        pageObjects.txtPassword.sendKeys(password);

    }
    public void clickOnLoginButton() {
        pageObjects.btnLogin.click();
    }
    public String getInvalidCredentialsMessage() {
        driverWait.until(ExpectedConditions.visibilityOf(pageObjects.lblInvalidCredentials)).isDisplayed();
        return pageObjects.lblInvalidCredentials.getText();
    }

    public void logout() throws InterruptedException {
        driverWait.until(ExpectedConditions.visibilityOf(pageObjects.lblWelcome)).isDisplayed();
        pageObjects.lblWelcome.click();
        Thread.sleep(2000);
        pageObjects.lnkLogout.click();

    }
    public boolean isHRMLogoDisplayed() {
        driverWait.until(ExpectedConditions.visibilityOf(pageObjects.imgLogo));
        return pageObjects.imgLogo.isDisplayed();
    }

    public boolean isLoginFailedErrorDisplayed() {
        driverWait.until(ExpectedConditions.visibilityOf(pageObjects.lblInvalidCredentials));
        return pageObjects.lblInvalidCredentials.isDisplayed();
    }

    public void verifyLoginAsValidUser() {
        //DriverFactory.getInstance().getDriver().get("https://opensource-demo.orangehrmlive.com/");
        pageObjects.txtUsername.sendKeys("Admin");
        pageObjects.txtPassword.sendKeys("admin123");
        pageObjects.btnLogin.click();
    }

    public void verifyLoginAsInvalidUser() {
        //DriverFactory.getInstance().getDriver().get("https://opensource-demo.orangehrmlive.com/");
        pageObjects.txtUsername.sendKeys("Admin");
        pageObjects.txtPassword.sendKeys("Admin");
        pageObjects.btnLogin.click();

    }
}
