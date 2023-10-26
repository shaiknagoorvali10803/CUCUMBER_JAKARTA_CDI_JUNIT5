package com.csx.Page.Google;

import com.csx.Page.Base;
import com.csx.Utils.ScenarioContext;
import com.csx.Utils.WebDriverProvider;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@ApplicationScoped
public class GooglePage extends Base {

    @Inject
    ScenarioContext scenarioContext;
    private String url="https://www.google.com";

    @Inject
    WebDriverProvider driverProvider;
    private WebDriverWait wait;

    @FindBy(name = "q")
    private WebElement searchBox;
    @FindBy(css = "div.g")
    private List<WebElement> results;

    @FindBy(name = "btnK")
    private List<WebElement> searchBtns;
    @PostConstruct
    private void init(){
       wait= new WebDriverWait(driverProvider.getInstance(), Duration.ofSeconds(60));

    }
    public void goTo(){
        this.driverProvider.getInstance().navigate().to(url);
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
    public int getCount(){
        return this.results.size();

    }
    public boolean isAt() {
        return this.wait.until((d) -> this.searchBox.isDisplayed());
    }


}
