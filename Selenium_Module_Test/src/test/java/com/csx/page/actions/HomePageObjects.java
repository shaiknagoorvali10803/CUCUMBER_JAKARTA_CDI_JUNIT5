package com.csx.page.actions;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Singleton
public class HomePageObjects {

    @FindBy(name = "q")
    WebElement searchBox;
    @FindBy(name = "btnK")
    List<WebElement> searchBtns;

}
