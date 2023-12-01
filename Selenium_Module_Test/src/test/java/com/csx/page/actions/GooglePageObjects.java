package com.csx.page.actions;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
@Singleton
public class GooglePageObjects {

    @FindBy(name = "q")
    WebElement searchBox;
    @FindBy(css = "div.g")
    List<WebElement> results;

    @FindBy(name = "btnK")
    List<WebElement> searchBtns;



}
