package com.csx.page.actions;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
@Singleton
public class HRMLoginPageObjects {
    @FindBy(name="username")
    WebElement txtUsername;

    @FindBy(name="password")
    WebElement txtPassword;

    @FindBy(xpath="//button[normalize-space()='Login']")
    WebElement btnLogin;

    @FindBy(xpath = "//p[text()='Invalid credentials']")
    WebElement lblInvalidCredentials;

    @FindBy(xpath = "//p[@class='oxd-userdropdown-name']")
    WebElement lblWelcome;


    @FindBy(xpath="//li/a[text()='Logout']")
    WebElement lnkLogout;

    @FindBy(xpath="//div[@class='orangehrm-login-branding']//img")
    WebElement imgLogo;
}
