package com.csx.Utils;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;


@ApplicationScoped
public class WebDriverProvider {
  private static final String BUILD_TOOL_RUN = "buildToolRun";

  private static final String SELENIUM_GRID_URL = "http://selenium.apps.ocpjaxd003.csx.com/";
  private static final String USER_DIR = "user.dir";

  private WebDriver instance;
  private BrowserType instanceBrowserType;

  @PostConstruct
  public void setUpBrowsers() {
    if (!BooleanUtils.toBoolean(System.getProperty(BUILD_TOOL_RUN))) {
      //ChromeDriverManager.chromedriver().setup();
      // InternetExplorerDriverManager.iedriver().setup();
      // FirefoxDriverManager.firefoxdriver().setup();
      // EdgeDriverManager.edgedriver().setup();
    }
  }

  public enum BrowserType {
    CHROME("Google Chrome"),
    INTERNET_EXPLORER("Internet Explorer"),
    FIRE_FOX("Firefox"),
    SAFARI("Safari"),
    EDGE("edge");

    private String displayName;

    private BrowserType(String displayName) {
      this.displayName = displayName;
    }

    @Override
    public String toString() {
      return displayName;
    }
  }

  public WebDriver getInstance() {
    if (instance == null) {
      generateWebDriver(BrowserType.CHROME);
    }
    return instance;
  }

  public void generateWebDriver(BrowserType browserType) {
    generateWebDriver(browserType, null);
  }

  public void generateWebDriver(BrowserType browserType, final Boolean headless) {
    WebDriver driver = null;
    boolean isHeadless = Optional.ofNullable(headless).isPresent() ? headless : isHeadlessRun();
    BrowserType bt = getBrowserTypeUsingSystemVar();
    if (Optional.ofNullable(bt).isPresent()) {
      browserType = bt;
    }

    switch (browserType) {
      case CHROME:
        driver = generateChromeWebDriver(isHeadless);
        break;
      case INTERNET_EXPLORER:
        driver = generateInternetExplorerDriver();
        break;
      case EDGE:
        driver= generateEdgeDriver();
        break;
      default:
        driver = generateChromeWebDriver(isHeadless);
        break;
    }

    instance = driver;
    instanceBrowserType = browserType;
  }

  private boolean isHeadlessRun() {
    return BooleanUtils.toBoolean(System.getProperty("headless"));
  }

  private BrowserType getBrowserTypeUsingSystemVar() {
    String browserValue = System.getProperty("browser");
    if (StringUtils.isNotBlank(browserValue)) {
      switch (browserValue) {
        case "chrome":
          return BrowserType.CHROME;
        case "firefox":
          return BrowserType.FIRE_FOX;
        case "ie":
          return BrowserType.INTERNET_EXPLORER;
        case "safari":
          return BrowserType.SAFARI;
        case "edge":
          return BrowserType.EDGE;
        default:
          return null;
      }
    }

    return null;
  }

  private static WebDriver generateChromeWebDriver(boolean headless) {
    ChromeOptions chromeOptions = new ChromeOptions();
    //chromeOptions.setHeadless(headless);
    // chromeOptions.addArguments("--whitelist-ip *");
    chromeOptions.addArguments("--proxy-server='direct://'");
    chromeOptions.addArguments("--proxy-bypass-list=*");
    chromeOptions.addArguments("--ignore-certificate-errors");
    chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

    // to launch chrome in incognito mode
    chromeOptions.addArguments("start-maximized");
    // chromeOptions.addArguments("--incognito");
    DesiredCapabilities capabilities =  new DesiredCapabilities();
    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

    // to set default download path
    String downloadFilepath = System.getProperty("user.dir");
    HashMap<String, Object> chromePrefs = new HashMap<>();
    chromePrefs.put("profile.default_content_settings.popups", 0);
    chromePrefs.put("download.default_directory", downloadFilepath);
    chromeOptions.setExperimentalOption("prefs", chromePrefs);

    if (BooleanUtils.toBoolean(System.getProperty(BUILD_TOOL_RUN))) {
      try {
        return new RemoteWebDriver(new URL(SELENIUM_GRID_URL), chromeOptions);
      } catch (MalformedURLException e) {
      }
    }

    return new ChromeDriver(chromeOptions);
  }

  private static WebDriver generateInternetExplorerDriver() {
    InternetExplorerOptions options = new InternetExplorerOptions();
    options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
    options.setCapability("requireWindowFocus", true);
    options.setCapability("ignoreZoomSetting", true);
    if (BooleanUtils.toBoolean(System.getProperty(BUILD_TOOL_RUN))) {
      try {
        return new RemoteWebDriver(new URL(SELENIUM_GRID_URL), options);
      } catch (MalformedURLException e) {

      }
    }
    return new InternetExplorerDriver(options);
  }

  private static WebDriver generateSafariDriver() {

    System.setProperty("webdriver.safari.driver", System.getProperty(USER_DIR) + "/drivers/mac/safaridriver");
    return new SafariDriver();
  }
  private static WebDriver generateEdgeDriver() {
    EdgeOptions chromeOptions = new EdgeOptions();
    chromeOptions.addArguments("--proxy-server='direct://'");
    chromeOptions.addArguments("--proxy-bypass-list=*");
    chromeOptions.addArguments("--ignore-certificate-errors");
    chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

    // to launch chrome in incognito mode
    chromeOptions.addArguments("start-maximized");
    // chromeOptions.addArguments("--incognito");
    DesiredCapabilities capabilities =  new DesiredCapabilities();
    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
    // to set default download path
    String downloadFilepath = System.getProperty("user.dir");
    HashMap<String, Object> chromePrefs = new HashMap<>();
    chromePrefs.put("profile.default_content_settings.popups", 0);
    chromePrefs.put("download.default_directory", downloadFilepath);
    chromeOptions.setExperimentalOption("prefs", chromePrefs);
    chromeOptions.setCapability("InPrivate", true);
    if (BooleanUtils.toBoolean(System.getProperty(BUILD_TOOL_RUN))) {
      try {
        return new RemoteWebDriver(new URL(SELENIUM_GRID_URL), chromeOptions);
      } catch (MalformedURLException e) {
      }
    }
    return new EdgeDriver(chromeOptions);
  }

  public BrowserType getInstanceBrowserType() {

    // default it to Chrome
    BrowserType result = BrowserType.CHROME;

    if (instance != null) {
      result = instanceBrowserType;
    }

    return result;
  }
}
