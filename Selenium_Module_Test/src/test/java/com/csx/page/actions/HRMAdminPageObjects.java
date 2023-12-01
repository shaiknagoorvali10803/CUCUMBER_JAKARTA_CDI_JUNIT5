package com.csx.page.actions;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
@Singleton
public class HRMAdminPageObjects {
    @FindBy(id = "menu_admin_viewAdminModule")
    WebElement admin_link;

    @FindBy(id = "menu_admin_employmentStatus")
    WebElement users;

    @FindBy(id = "menu_admin_Job")
    WebElement employmentStatus;

    @FindBy(id = "btnAdd")
    WebElement addbtn;

    @FindBy(id = "empStatus_name")
    WebElement empStatus_name;

    @FindBy(id = "btnSave")
    WebElement save_btn;

    @FindBy(xpath = "//*///div[@class='message success fadable']")
    WebElement message_save;
}
