package com.csx.page.actions;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
@Singleton
public class HRMDashboardPageObjects {
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

}
