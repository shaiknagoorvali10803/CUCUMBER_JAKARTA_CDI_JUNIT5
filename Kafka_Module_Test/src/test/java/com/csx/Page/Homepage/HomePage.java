package com.csx.Page.Homepage;


import com.csx.Page.Base;
import com.csx.Utils.WebDriverProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.Value;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@ApplicationScoped
public class HomePage extends Base {
    @Inject
    WebDriverProvider driverProvider;
    private WebDriverWait wait;
    @FindBy(name = "q")
    private WebElement searchBox;
    @FindBy(name = "btnK")
    private List<WebElement> searchBtns;
    private String url="https://www.google.com";

    public void goTo(){
        this.driverProvider.getInstance().get(url);
    }

    public void search(final String keyword){
        this.searchBox.sendKeys(keyword);
        this.searchBox.sendKeys(Keys.TAB);
        this.searchBtns
                .stream()
                .filter(e -> e.isDisplayed() && e.isEnabled())
                .findFirst()
                .ifPresent(WebElement::click);
    }

   public boolean isAt() {
        return this.wait.until((d) -> this.searchBox.isDisplayed());
    }

}
