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
public class Dashboard {//Make this page POM later
	
	@Inject
	WebDriverProvider driverProvider;
	private WebDriverWait driverWait;
	
		@FindBy(xpath="//h6[normalize-space()='Dashboard']")
		WebElement dashboard_menu;
		
		@FindBy(xpath="//li/a[text()='Logout']")
		WebElement lnkLogout;
		
		@FindBy(xpath="//button[@title='Assign Leave']")
		WebElement assignleave_link;
		
		@FindBy(xpath="//a[text()='Assign Leave']")
		WebElement assignleave_menu;
		
		@FindBy(xpath="//button[@title='Leave List']")
		WebElement leavelist_link;
		
		@FindBy(xpath="//a[text()='Leave List']")
		WebElement leavelist_menu;
		
		//a[@id='menu_leave_assignLeave']
		
	@PostConstruct
	  private void setup() {
		    PageFactory.initElements(driverProvider.getInstance(), this);
		    driverWait = new WebDriverWait(driverProvider.getInstance(), Duration.ofSeconds(30));
		  }
	
	public String verifyassignleave_link() throws InterruptedException {
		driverWait.until(ExpectedConditions.visibilityOf(assignleave_link)).isDisplayed();
		assignleave_link.click();
		Thread.sleep(2000);
		return assignleave_menu.getText();
	}
	
	public String verifyleavelist_link() throws InterruptedException {
		driverWait.until(ExpectedConditions.visibilityOf(leavelist_link)).isDisplayed();
		leavelist_link.click();
		Thread.sleep(2000);
		return leavelist_menu.getText();
	}
	
	public String verifyUserAccess() throws InterruptedException {
		Thread.sleep(2000);
		driverWait.until(ExpectedConditions.visibilityOf(dashboard_menu)).isDisplayed();
		Thread.sleep(2000);
		return dashboard_menu.getText();
	}
	
	

}
