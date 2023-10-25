package com.csx.Page.HRM;


import com.csx.Utils.WebDriverProvider;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

@ApplicationScoped
public class LoginPage  {
	
	  @Inject
	  WebDriverProvider driverProvider;
	  private WebDriverWait driverWait;
	
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
		
		
	
	@PostConstruct
	private void setup() {
	PageFactory.initElements(driverProvider.getInstance(), this);
	driverWait = new WebDriverWait(driverProvider.getInstance(), Duration.ofSeconds(30));
	}
			
	public void enterCredentials(String username, String password) {
		driverWait.until(ExpectedConditions.visibilityOf(txtUsername)).isDisplayed();
		driverWait.until(ExpectedConditions.visibilityOf(txtPassword)).isDisplayed();
		txtUsername.sendKeys(username);
		txtPassword.sendKeys(password);
		
	}
	public void clickOnLoginButton() {
		btnLogin.click();
	}
	public String getInvalidCredentialsMessage() {
		driverWait.until(ExpectedConditions.visibilityOf(lblInvalidCredentials)).isDisplayed();
		return lblInvalidCredentials.getText();
	}
	
	public void logout() throws InterruptedException {
		driverWait.until(ExpectedConditions.visibilityOf(lblWelcome)).isDisplayed();
		lblWelcome.click();
		Thread.sleep(2000);
		lnkLogout.click();
		
		}
	public boolean isHRMLogoDisplayed() {
		driverWait.until(ExpectedConditions.visibilityOf(imgLogo));
		return imgLogo.isDisplayed();
	}
	
	public boolean isLoginFailedErrorDisplayed() {
		driverWait.until(ExpectedConditions.visibilityOf(lblInvalidCredentials));
		return lblInvalidCredentials.isDisplayed();
	}
	
	public void verifyLoginAsValidUser() {
		//DriverFactory.getInstance().getDriver().get("https://opensource-demo.orangehrmlive.com/");
		txtUsername.sendKeys("Admin");
		txtPassword.sendKeys("admin123");	
		btnLogin.click();
	}
	
	public void verifyLoginAsInvalidUser() {
		//DriverFactory.getInstance().getDriver().get("https://opensource-demo.orangehrmlive.com/");
		txtUsername.sendKeys("Admin");
		txtPassword.sendKeys("Admin");	
		btnLogin.click();
		
	}
	
	

}
