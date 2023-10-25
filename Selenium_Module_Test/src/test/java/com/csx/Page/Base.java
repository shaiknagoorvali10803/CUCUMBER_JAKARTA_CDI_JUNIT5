package com.csx.Page;


import com.csx.Utils.WebDriverProvider;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

@ApplicationScoped
public abstract class Base {

    @Inject
    WebDriverProvider driverProvider;
    private WebDriverWait driverWait;

    @PostConstruct
    private void init(){
        PageFactory.initElements(this.driverProvider.getInstance(), this);
    }

}
