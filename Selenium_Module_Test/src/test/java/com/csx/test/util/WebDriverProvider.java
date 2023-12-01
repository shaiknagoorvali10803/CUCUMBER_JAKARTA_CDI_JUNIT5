package com.csx.test.util;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;


@Singleton
public class WebDriverProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverProvider.class);
    private static final String BUILD_TOOL_RUN = SeleniumUtil.setRemoteExecution(System.getProperty("buildToolRun"));
    private static final String HEADLESS = SeleniumUtil.setHeadlessProperty(System.getProperty("headless"));

    //private static final String SELENIUM_GRID_URL = "http://selenium.apps.ocpjaxd003.csx.com/";

    private static final String SELENIUM_GRID_URL = "http://192.168.56.1:4444";


    private static final String USER_DIR = "user.dir";

    private WebDriver instance;
    private BrowserType instanceBrowserType;


    @PostConstruct
    public void setUpBrowsers() {
        if (!BooleanUtils.toBoolean(System.getProperty("buildToolRun"))) {
            //ChromeDriverManager.chromedriver().setup();
            // InternetExplorerDriverManager.iedriver().setup();
            // FirefoxDriverManager.firefoxdriver().setup();
            // EdgeDriverManager.edgedriver().setup();
        }
    }

    public enum BrowserType {
        CHROME("Google Chrome"),
        FIRE_FOX("Firefox"),
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
            case FIRE_FOX:
                driver = generateFirefoxDriver(isHeadless);
                break;
            case EDGE:
                driver = generateEdgeDriver(isHeadless);
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
        //chromeOptions.setHeadless(BooleanUtils.toBoolean(headless));
        chromeOptions.addArguments("--proxy-server='direct://'");
        chromeOptions.addArguments("--proxy-bypass-list=*");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        // to launch chrome in incognito mode
        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("--incognito");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        // to set default download path
        String downloadFilepath = System.getProperty("user.dir");
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        if (BooleanUtils.toBoolean(HEADLESS)||BooleanUtils.toBoolean(headless)) {
            chromeOptions.addArguments("--headless");
        }
        if (BooleanUtils.toBoolean(BUILD_TOOL_RUN)) {
            try {
                chromeOptions.addArguments("--headless");
                chromeOptions.addArguments("--window-size=1920,1080");
                return new RemoteWebDriver(new URL(SELENIUM_GRID_URL), chromeOptions);
            } catch (MalformedURLException e) {
                LOGGER.info("Given remote web driver url is wrong");
            }
        }
        return new ChromeDriver(chromeOptions);
    }


    private static WebDriver generateEdgeDriver(boolean headless) {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--proxy-server='direct://'");
        edgeOptions.addArguments("--proxy-bypass-list=*");
        edgeOptions.addArguments("--ignore-certificate-errors");
        edgeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        edgeOptions.addArguments("--remote-allow-origins=*");
        edgeOptions.addArguments("start-maximized");
        edgeOptions.addArguments("-inprivate");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, edgeOptions);
        String downloadFilepath = System.getProperty("user.dir");
        HashMap<String, Object> edgePrefs = new HashMap<>();
        edgePrefs.put("profile.default_content_settings.popups", 0);
        edgePrefs.put("download.default_directory", downloadFilepath);
        edgeOptions.setExperimentalOption("prefs", edgePrefs);
        if (BooleanUtils.toBoolean(HEADLESS)||BooleanUtils.toBoolean(headless)) {
            edgeOptions.addArguments("--headless");
        }
        if (BooleanUtils.toBoolean(BUILD_TOOL_RUN)) {
            try {
                edgeOptions.addArguments("--headless");
                edgeOptions.addArguments("--window-size=1920,1080");
                return new RemoteWebDriver(new URL(SELENIUM_GRID_URL), edgeOptions);
            } catch (MalformedURLException e) {
                LOGGER.info("Given remote web driver url is wrong");
            }
        }
        return new EdgeDriver(edgeOptions);
    }

    private static WebDriver generateFirefoxDriver(boolean headless) {
        String downloadFilepath = System.getProperty("user.dir");
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(false);
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", downloadFilepath);
        profile.setPreference("browser.download.useDownloadDir", true);
        profile.setPreference("browser.download.viewableInternally.enabledTypes", "");
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/xlsx;application/msword;application/ms-doc;application/doc;application/pdf;text/plain;application/text;text/xml;application/xml");
        profile.setPreference("pdfjs.disabled", true);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setProfile(profile);
        firefoxOptions.addArguments("--disable-web-security");
        firefoxOptions.addArguments("--allow-running-insecure-content");
        firefoxOptions.addArguments("--whitelist-ip *");
        firefoxOptions.addArguments("--ignore-certificate-errors", "--ignore-ssl-errors");
        firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        firefoxOptions.addArguments("start-maximized");
        firefoxOptions.addArguments("--private");
        if (BooleanUtils.toBoolean(HEADLESS)||BooleanUtils.toBoolean(headless)) {
            firefoxOptions.addArguments("--headless");
        }
        if (BooleanUtils.toBoolean(BUILD_TOOL_RUN)) {
            try {
                firefoxOptions.addArguments("--headless");
                firefoxOptions.addArguments("--window-size=1920,1080");
                return new RemoteWebDriver(new URL(SELENIUM_GRID_URL), firefoxOptions);
            } catch (MalformedURLException e) {
                LOGGER.info("Given remote web driver url is wrong");
            }
        }
        return new FirefoxDriver(firefoxOptions);
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
