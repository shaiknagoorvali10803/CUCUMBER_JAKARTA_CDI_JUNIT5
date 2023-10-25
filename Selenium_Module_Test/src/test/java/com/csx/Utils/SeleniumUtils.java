package com.csx.Utils;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import org.joda.time.Days;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


import static org.junit.jupiter.api.Assertions.fail;


public  class SeleniumUtils {
    private  static final Logger LOGGER = LoggerFactory.getLogger(SeleniumUtils.class);
    private static  int defaultMaxTime = 60;
    private static  int maxSyncTime = defaultMaxTime;
    private static  boolean isCustomWait = false;

    private   static final String SPINNER_XPATH = "//app-block-ui/div/p-blockui";
    public static   final String ERROR_MSG = "Some error has occurred while performing operation::{}";
    public static   final String IS_ENTERED = " is entered";
    public static   final String AND_PASSWORD = " and Password: ";
    public static   final String USERNAME = "Username: ";
    public static   final int DRIVER_WAIT_TIME_IN_SECS = 30;
    private   static final String DROPDOWN_ITEM_SELECTOR_IN_OVERLAY = "//ul/li[*]/span[text()='%s']";
    private   static final String DROPDOWN_PARTIAL_MATCH_ITEM_SELECTOR_IN_OVERLAY = "//ul/li[*]/span[contains(text(),'%s')]";
    private   static final String DROPDOWN_OVERLAY = "//ul";
    private   static final String DROPDOWN_CLICKABLE_LABEL = "div/label";
    private   static final String DEFAULT_RACFID = "Q119M";
    private   static final String DEFAULT_RACFID_PASSWORD = "csx123";
    private   static final String DEFAULT_SXLOGON_STAGING_URL = "https://sxlogon-staging.csx.com";
    private   static final String LOGINPAGE_USERNAME_TEXTBOX_OBJECT_ID = "username";
    private   static final String LOGINPAGE_PASSWORD_TEXTBOX_OBJECT_ID = "password";
    private   static final String LOGINPAGE_LOGIN_BUTTON_OBJECT_XPATH = "//*[@id=\"okta-login-section\"]/div[7]/input";
    public static final String BEFORE_WAIT_FOR_ELEMENT_IN_BUTTON_CLICK = "BeforeWaitForElement in buttonClick ::";
    public static final String MM_DD_YYYY_WITH_SLASH = "MM/dd/yyyy";

    /**
     * ---------------------------Maximize
     * Window------------------------------------------
     */
    public static   void maximizeWindow(final WebDriver driver) {
        driver.manage().window().maximize();
    }

    /**
     * ---------------------------Resize window for
     * Mobile------------------------------------------
     */
    public static   void resizeWindowForMobile(final WebDriver driver) {
        driver.manage().window().setSize(new Dimension(50, 750));
    }

    /**
     * ---------------------------Resize window for
     * Tablet------------------------------------------
     */
    public static   void resizeWindowForTablet(final WebDriver driver) {
        driver.manage().window().setSize(new Dimension(700, 750));
    }

    /**
     * -------------------------------Simple Date
     * Format--------------------------------------
     */
    public static   String todayDate() {
        return new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date());
    }

    /**
     * ------------------------------------------------------------------------------------------------------------------
     * Click on the WebElement With ID and handling WebDriverWait to handle
     * NoSuchElementException Passing Element as String
     */
    public static   void clickElementByID(final WebDriver driver, final String elementID, final String elementName) {
        final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementID)));
        wait.until(ExpectedConditions.elementToBeClickable(By.id(elementID)));
        driver.findElement(By.id(elementID)).click();
    }

    /**
     * ------------------------------------------------------------------------------------------------------------------
     * Click on the WebElement With XPATH and handling WebDriverWait to handle
     * NoSuchElementException Passing Element as String
     */
    public static   void clickElementbyXPath(final WebDriver driver, final String elementID, final String elementName) {
        try {
            final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementID)));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementID)));
            driver.findElement(By.xpath(elementID)).click();
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
    }

    /**
     * ------------------------------------------------------------------------------------------------------------------
     * Click on the WebElement With XPATH and handling WebDriverWait to handle
     * NoSuchElementException Passing Element as WebElement
     */
    public static   void clickElementbyWebElement(final WebDriver driver, final WebElement elementID) {
        try {
            final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
            wait.until(ExpectedConditions.visibilityOf(elementID));
            wait.until(ExpectedConditions.elementToBeClickable(elementID));
            elementID.sendKeys("");
            elementID.click();
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        } catch (final Exception e) {
            // If clickElementbyWebElement method failed to click on the element with
            // sendKyes
            // then this catch block will be executed with calling
            // clickElementbyWebElementWithOutSendKeys method
            clickElementbyWebElementWithOutSendKeys(driver, elementID);
        }
    }

    /**
     * ------------------------------------------------------------------------------------------------------------------
     * Click on the WebElement With XPATH and handling WebDriverWait to handle
     * NoSuchElementException Passing Element as WebElement This method doesn't use
     * sendKeys
     */
    public static   void clickElementbyWebElementWithOutSendKeys(final WebDriver driver, final WebElement elementID) {
        try {
            final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
            wait.until(ExpectedConditions.visibilityOf(elementID));
            wait.until(ExpectedConditions.elementToBeClickable(elementID));
            elementID.click();
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
    }

    /**
     * ---------------------------------------------------------------------------------------------------
     * Submit Element by XPATH
     */
    public static   void submitElementbyXPath(final WebDriver driver, final String elementID, final String elementName) {
        final JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath(elementID)));
    }

    /**
     * ----------------------------------------------------------------------------------------------------
     * Scroll till Web Element
     */
    public static   void scrollToWebElement(final WebDriver driver, final WebElement webelement) {
        final JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", webelement);
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * Validate Element
     */
    public static   void validateElement(final WebDriver driver, final String xpath, final String testCaseName,
                                       final String expectedResult) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            final WebElement element = driver.findElement(By.xpath(xpath));
            Assertions.assertEquals(expectedResult.trim(), element.getText().trim());
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * Set value to Element
     */
    public static   void setValueToElementByXpath(final WebDriver driver, final String xpath, final String inputValue) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            final WebElement element = driver.findElement(By.xpath(xpath));
            element.clear();
            element.sendKeys(inputValue);
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
    }

    public static   void setValueToElementByXpath(final WebDriver driver, final WebElement element,
                                                final String inputValue) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            driverWait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(inputValue);
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
    }

    /**
     * ---------------------------------------------------------------------------------------------------------
     * Set Value to WebElement using XPATH and handling WebDriverWait to handle
     * NoSuchElementException
     */
    public static   void setValueToElement(final WebDriver driver, final WebElement webElement, final String inputValue) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            driverWait.until(ExpectedConditions.visibilityOf(webElement));
            webElement.clear();
            webElement.sendKeys(inputValue);
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get value by element
     */
    public static   String getValueByElement(final WebDriver driver, final WebElement webElement) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOf(webElement)).isDisplayed()) {
                return webElement.getText().trim();
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return null;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * Is element not empty-
     */
    public static   boolean isElementNotEmpty(final WebDriver driver, final WebElement webElement,
                                            final String elementName) {
        return StringUtils.isNotEmpty(getValueByElement(driver, webElement));
    }

    /**
     * ----------------------------------------------------------------------------
     * Get Text Value of the Input Element using XPATH and handling WebDriverWait to
     * handle NoSuchElementException //Handled the webelement from -
     * ShipmentEnquiryObjectRepo and Actions class
     */
    public static   String getInputElementValue(final WebDriver driver, final WebElement webElement) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOf(webElement)).isDisplayed()) {
                return webElement.getAttribute("value").trim();
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return null;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get Enable Disable by element
     */
    public static   Boolean getEnableDisableByElement(final WebDriver driver, final WebElement webElement) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOf(webElement)) != null) {
                return webElement.isEnabled();
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return false;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * is Disable by element
     */
    public static   Boolean isDisableByElement(final WebDriver driver, final WebElement webElement) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOf(webElement)) != null) {
                final String attribute = webElement.getAttribute("disabled");
                return attribute != null && attribute.equalsIgnoreCase("true");
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return false;
    }

    /**
     * ----------------------------------------------------------------------------------------------
     * this method checks if the input's enabled status has changed by overriding
     * the apply method and applying the condition that we are looking for pass
     * testIsEnabled true if checking whether the input has become enabled pass
     * testIsEnabled false if checking whether the input has become disabled
     */
    public static   Boolean isEnabledDisabledWaitingForChange(final WebDriver driver, final WebElement webElement,
                                                            final Boolean testIsEnabled) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        Boolean isEnabled = null;
        try {
            isEnabled = driverWait.until(driver1 -> {
                final Boolean isElementEnabled = webElement.isEnabled();
                return testIsEnabled ? isElementEnabled : !isElementEnabled;
            });
        } catch (final TimeoutException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return isEnabled;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * is Spinner Enabled
     */
    public static   Boolean isSpinnerEnabled(final WebDriver driver, final WebElement webElement) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOf(webElement)) != null) {
                final String webElementCSS = getElementsCSS(driver, webElement, "webelemnt CSS");
                if (webElementCSS != null && !webElementCSS.isEmpty()) {
                    return webElementCSS.contains("loadingSpinner");
                } else {
                    return false;
                }
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return false;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * Is disabled by element CSS
     */
    public static   Boolean isDisabledByElementCSS(final WebDriver driver, final WebElement webElement) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOf(webElement)) != null) {
                final String webElementCSS = getElementsCSS(driver, webElement, "webelemnt CSS");
                if (webElementCSS != null && !webElementCSS.isEmpty()) {
                    return webElementCSS.contains("ui-state-disabled");
                } else {
                    return false;
                }
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return false;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * Mouse over Element
     */
    public static   void mouseOverElement(final WebDriver driver, final WebElement webElement) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            driverWait.until(ExpectedConditions.visibilityOf(webElement));
            final Actions actObj = new Actions(driver);
            actObj.moveToElement(webElement).build().perform();
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * Check & Uncheck box
     */
    public static   void clickCheckedUnCheckedElement(final WebDriver driver, final WebElement elementID) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            driverWait.until(ExpectedConditions.visibilityOf(elementID));
            driverWait.until(ExpectedConditions.elementToBeClickable(elementID));
            final boolean chk = elementID.isEnabled();
            if (chk == true) {
                elementID.click();
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * Mouse over element select
     */
    public static   void mouseOverElementSelect(final WebDriver driver, final WebElement webElement) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            driverWait.until(ExpectedConditions.visibilityOf(webElement)).isSelected();
            final Actions actObj = new Actions(driver);
            actObj.moveToElement(webElement).build().perform();
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
    }

    /**
     * Navigate to URL
     */
    public static   void navigateToURL(final WebDriver driver, final String url) {
        driver.get(url);
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * Reload page
     */
    public static   void reloadPage(final WebDriver driver) {
        driver.navigate().refresh();
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * Navigate to URL in new tab
     */
    public static   void navigateToURLInNewTab(final WebDriver driver, final String url) {
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
        final ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        driver.get(url);
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * Is element present
     */
    public static   boolean isElementPresent(final WebDriver driver, final String xPath) {
        try {
            return driver.findElements(By.xpath(xPath)).size() > 0;
        } catch (final NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
            return false;
        }
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * Is Anchor present
     */
    public static   boolean isAnchorPresent(final WebDriver driver, final String text) {
        return isElementPresent(driver, "//a[contains(text(),'" + text + "')]");
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get CSS classes by element
     */
    public static   String getCssClassesByElement(final WebDriver driver, final WebElement webElement,
                                                final String elementName) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOf(webElement)).isDisplayed()) {
                return webElement.getAttribute("class").trim();
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return null;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * Get CSS value by element
     */
    public static   String getCSSValueByElement(final WebDriver driver, final WebElement webElement,
                                              final String cssAttributeName, final String elementName) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOf(webElement)).isDisplayed()) {
                return webElement.getCssValue(cssAttributeName);
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return null;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get data table size by xpath
     */
    public static   int getDataTableSizeByXpath(final WebDriver driver, final String tableXPath,
                                              final String testCaseName) {
        final List<WebElement> rows = driver.findElements(By.xpath(tableXPath + "/tbody/tr"));
        return rows.size();
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get mobile view card size by xpath
     */
    public static   int getMobileViewCardsSizeByXpath(final WebDriver driver, final String cardXPath,
                                                    final String testCaseName) {
        final List<WebElement> rows = driver.findElements(By.xpath(cardXPath));
        return rows.size();
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * To switch to a different window
     */
    public static   void switchToOtherWindow(final WebDriver driver, final int windowNumber) {
        final List<String> browserTabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(windowNumber));
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * is element displayed
     */
    public static   boolean isElementDisplayed(final WebElement webElement) {
        try {
            return webElement.isDisplayed();
        } catch (final NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
            return false;
        }
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * is element not displayed
     */
    public static   boolean isElementNotDisplayed(final WebElement webElement) {
        try {
            return !webElement.isDisplayed();
        } catch (final NoSuchElementException ele) {
            return true;
        }
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get element's CSS
     */
    public static   String getElementsCSS(final WebDriver driver, final WebElement webElement, final String elementName) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOf(webElement)).isDisplayed()) {
                return webElement.getAttribute("class");
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return null;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get web element from string path
     */
    public static   WebElement getWebElementFromStringPath(final WebDriver driver, final String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * Xpath eists
     */
    public static   boolean xPathExists(final WebDriver driver, final String xpath) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return true;
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
            return false;
        }
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get value by element no log
     */
    public static   String getValueByElementNoLog(final WebDriver driver, final WebElement webElement,
                                                final String elementName) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOf(webElement)).isDisplayed()) {
                return webElement.getText().trim();
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return null;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * check visibility by element
     */
    public static   Boolean checkVisibilityByElement(final WebDriver driver, final WebElement webElement,
                                                   final String elementName) {
        Boolean isVisible = false;
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOf(webElement)) != null) {
                isVisible = true;
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return isVisible;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * is element enabled
     */
    public static   Boolean isElementEnabled(final WebDriver driver, final WebElement webElement,
                                           final String elementName) {
        Boolean isEnabled = false;
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOf(webElement)) != null) {
                isEnabled = webElement.isEnabled();
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return isEnabled;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * is element not present
     */
    public static   Boolean isElementNotPresent(final WebDriver driver, final String xPath) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            return driverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xPath)));
        } catch (final Exception ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
            return true;
        }
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * is element not present with wait
     */
    public static   Boolean isElementNotPresentWithWait(final WebDriver driver, final String xPath,
                                                      final int waitTimeinSec) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeinSec));
        try {
            return driverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xPath)));
        } catch (final Exception ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
            return true;
        }
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * is button enabled
     */
    public static   Boolean isButtonEnabled(final WebDriver driver, final WebElement webElement,
                                          final String elementName) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOf(webElement)).isDisplayed()) {
                return webElement.isEnabled();
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return null;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get value by xpath
     */
    public static   String getValueByXpath(final WebDriver driver, final String xPath) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath))).isDisplayed()) {
                final WebElement webElement = driver.findElement(By.xpath(xPath));
                return webElement.getText().trim();
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return null;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * is form control input field filled and valid
     */
    public static   boolean isFormControlInputFieldFilledAndValid(final WebDriver driver, final WebElement webElement) {
        final String fieldCSS = getElementsCSS(driver, webElement, "tsFailedPaymentOopsImage");
        return fieldCSS.contains("ui-state-filled") && !fieldCSS.contains("ng-invalid");
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * This method checks the value in list and return boolean reusult.
     */
    public static   boolean checkValueInList(final WebDriver driver, final List<WebElement> webElement,
                                           final String expectedValueInList, final String elementName) {
        final Boolean isValueExist = false;
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.visibilityOfAllElements(webElement)) != null) {
                final List<WebElement> elements = webElement;
                for (int i = 0; i < elements.size(); i++) {
                    final String listValue = elements.get(i).getText().trim();
                    if (listValue.contains(expectedValueInList)) {
                        return true;
                    }
                }
                return isValueExist;
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return isValueExist;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get URL of new tab
     */
    public static   String getUrlOfNewTab(final WebDriver driver, final int driverWaitTimeInSecs) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(driverWaitTimeInSecs));
        // get window handlers as list
        final List<String> browserTabs = new ArrayList<>(driver.getWindowHandles());
        // Ship to new tab and check if correct page opened
        String shipmentInstructionsTitle = null;
        driver.switchTo().window(browserTabs.get(2));
        if (driverWait.until(ExpectedConditions.titleContains("Main Page"))) {
            shipmentInstructionsTitle = driver.getTitle();
        }
        driver.switchTo().window(browserTabs.get(0));
        return shipmentInstructionsTitle;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * is text there
     */
    public static   String isTextThere(final WebDriver driver, final WebElement element, final Boolean testIsEnabled,
                                     final String elementName, final String attributeName) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            driverWait.until(ExpectedConditions.visibilityOf(element));
            driverWait.until(ExpectedConditions.elementToBeClickable(element));
            Thread.sleep(3000);
            return driverWait.until(drivers -> {
                if (element.getAttribute(attributeName).length() > 0) {
                    element.click();
                    return element.getAttribute(attributeName);
                }
                return null;
            });
        } catch (TimeoutException | InterruptedException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return null;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get element value by ID
     */
    public static   String getElementValueById(final WebDriver driver, final String elementId, final String elementName) {
        try {
            final JavascriptExecutor js = (JavascriptExecutor) driver;
            return (String) js.executeScript("return document.getElementById(" + "'" + elementId + "'" + ").value");
        } catch (final TimeoutException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return null;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * select value in PrimeNgDropdown
     */
    public static   void selectValueInPrimeNgDropDown(final WebDriver driver, final WebElement dropDownElementOrParent,
                                                    final String value) {
        final WebElement dropDownElement = findLabelPrimeNgDropdown(driver, dropDownElementOrParent);
        final WebElement element = dropDownElement
                .findElement(By.xpath(String.format(DROPDOWN_ITEM_SELECTOR_IN_OVERLAY, value)));
        clickElementbyWebElement(driver, element);
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * select partial match value in PrimeNgDropdown
     */
    public static   void selectPartialMatchValueInPrimeNgDropDown(final WebDriver driver,
                                                                final WebElement dropDownElementOrParent, final String value) {
        final WebElement dropDownElement = findLabelPrimeNgDropdown(driver, dropDownElementOrParent);
        final WebElement element = dropDownElement
                .findElement(By.xpath(String.format(DROPDOWN_PARTIAL_MATCH_ITEM_SELECTOR_IN_OVERLAY, value)));
        clickElementbyWebElement(driver, element);
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * find Label PrimeNgDropdown
     */
    private static   WebElement findLabelPrimeNgDropdown(final WebDriver driver,
                                                       final WebElement dropDownElementOrParent) {
        WebElement dropDownElement = null;
        if (dropDownElementOrParent != null && "p-dropdown".equalsIgnoreCase(dropDownElementOrParent.getTagName())) {
            dropDownElement = dropDownElementOrParent;
        } else if (dropDownElementOrParent != null) {
            dropDownElement = dropDownElementOrParent.findElement(By.tagName("p-dropdown"));
        } else {
            dropDownElement = driver.findElement(By.tagName("p-dropdown"));
        }
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        driverWait.until(ExpectedConditions.visibilityOf(dropDownElement));
        driverWait.until(ExpectedConditions
                .elementToBeClickable(dropDownElement.findElement(By.xpath(DROPDOWN_CLICKABLE_LABEL))));
        clickElementbyWebElement(driver, dropDownElement.findElement(By.xpath(DROPDOWN_CLICKABLE_LABEL)));
        try {
            Thread.sleep(300);
        } catch (final InterruptedException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        driverWait.until(ExpectedConditions.visibilityOf(dropDownElement.findElement(By.xpath(DROPDOWN_OVERLAY))));
        return dropDownElement;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get PrimeNgDropdown value
     */
    public static   String getPrimeNgDropDownValue(final WebDriver driver, final int driverWaitTimeInSecs,
                                                 final WebElement dropDownElementOrParent, final String elementName, final String dropDownLabel) {
        WebElement dropDownElement = null;
        if (dropDownElementOrParent != null && "p-dropdown".equalsIgnoreCase(dropDownElementOrParent.getTagName())) {
            dropDownElement = dropDownElementOrParent;
        } else if (dropDownElementOrParent != null) {
            dropDownElement = dropDownElementOrParent.findElement(By.tagName("p-dropdown"));
        } else {
            dropDownElement = driver.findElement(By.tagName("p-dropdown"));
        }
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(driverWaitTimeInSecs));
        driverWait.until(ExpectedConditions.visibilityOf(dropDownElement));
        return getValueByElement(driver, dropDownElement.findElement(By.xpath(dropDownLabel)));
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * is PrimeNg check box checked
     */
    public static   boolean isPrimeNgCheckboxChecked(final WebDriver driver, final WebElement parentElement) {
        WebElement checkBox = null;
        if (parentElement != null) {
            checkBox = parentElement.findElement(By.tagName("p-checkbox"));
        } else {
            checkBox = driver.findElement(By.tagName("p-checkbox"));
        }
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        driverWait.until(ExpectedConditions.visibilityOf(checkBox));
        return checkBox.findElement(By.xpath("div/div[2]")).getAttribute("class").contains("ui-state-active");
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * high lighter method
     */
    public static   void highLighterMethod(final WebDriver driver, final WebElement element) {
        final JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * Remove high lighter
     */
    public static   void removeHighLighter(final WebDriver driver, final WebElement element) {
        final JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: white;');", element);
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get place holder text
     */
    public static   String getPlaceHolderText(final WebElement element) {
        return element.getAttribute("placeholder");
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get current screen URL
     */
    public static   String getCurrentScreenUrl(final WebDriver driver) {
        final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[contains(text(),' Equipment ')]")));
        return driver.getCurrentUrl();

    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * focus out of text area
     */
    public static   void focusOutOfTextArea(final WebElement webElement) {
        final WebElement destWebElement = webElement;
        destWebElement.sendKeys(Keys.TAB);
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * is spinner loaded
     */
    public static   Boolean isSpinnerLoaded(final WebDriver driver, final WebElement webElement,
                                          final String elementName) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            if (driverWait.until(ExpectedConditions.invisibilityOf(webElement)) != null) {
                final String webElementCSS = getElementsCSS(driver, webElement, "webelemnt CSS");
                if (webElementCSS != null && !webElementCSS.isEmpty()) {
                    return webElementCSS.contains("loadingSpinner");
                } else {
                    return false;
                }
            }
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return false;
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * is angular spinner loaded
     */
    public static   void waitForApiCallInAngular(final WebDriver driver) {
        final Wait<WebDriver> wait = new FluentWait<>(driver).pollingEvery(Duration.ofMillis(200))
                .withTimeout(Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS)).ignoring(NoSuchElementException.class);
        // making sure that spinner is present
        wait.until(d -> ExpectedConditions.visibilityOf(d.findElement(By.xpath(SPINNER_XPATH))));

        // we have to wait until spinner goes away
        final Wait<WebDriver> wait1 = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        wait1.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath(SPINNER_XPATH))));
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * get hidden element value by xpath
     */
    public static   String getHiddenElementValueByXPath(final WebDriver driver, final String xPath,
                                                      final String elementName) {
        try {
            final JavascriptExecutor js = (JavascriptExecutor) driver;
            final WebElement hiddenDiv = driver.findElement(By.xpath(xPath));
            String value = hiddenDiv.getText();
            final String script = "return arguments[0].innerHTML";
            return value = (String) ((JavascriptExecutor) driver).executeScript(script, hiddenDiv);
        } catch (final TimeoutException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
        return null;
    }

    public static   void sxoLogon(final WebDriver driver, final String url, final String racfId, final String pwd) {
        navigateToURL(driver, url);
        LOGGER.info("Launched sxo url: " + url);
        driver.findElement(By.id(LOGINPAGE_USERNAME_TEXTBOX_OBJECT_ID)).sendKeys(racfId);
        driver.findElement(By.id(LOGINPAGE_PASSWORD_TEXTBOX_OBJECT_ID)).sendKeys(pwd);
        LOGGER.info(USERNAME + racfId + AND_PASSWORD + pwd + IS_ENTERED);
        driver.findElement(By.xpath(LOGINPAGE_LOGIN_BUTTON_OBJECT_XPATH)).click();
        LOGGER.info("Login button is clicked");
        try {
            Thread.sleep(5000);
        } catch (final InterruptedException e) {
            LOGGER.info("Logging Failed");
        }
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     * SXO LOGON Method with Default Staging URL and Credentials
     */
    public static   void sxoLogon(final WebDriver driver) {
        navigateToURL(driver, DEFAULT_SXLOGON_STAGING_URL);
        LOGGER.info("Launched sxo url: " + DEFAULT_SXLOGON_STAGING_URL);
        driver.findElement(By.id(LOGINPAGE_USERNAME_TEXTBOX_OBJECT_ID)).sendKeys(DEFAULT_RACFID);
        driver.findElement(By.id(LOGINPAGE_PASSWORD_TEXTBOX_OBJECT_ID)).sendKeys(DEFAULT_RACFID_PASSWORD);
        LOGGER.info(USERNAME + DEFAULT_RACFID + AND_PASSWORD + DEFAULT_RACFID_PASSWORD + IS_ENTERED);
        driver.findElement(By.xpath(LOGINPAGE_LOGIN_BUTTON_OBJECT_XPATH)).click();
        LOGGER.info("Login button is clicked");
        try {
            Thread.sleep(5000);
        } catch (final InterruptedException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
    }

    /**
     * -------------------------------------------------------------------------------------------------------
     *
     * @param driver
     * @param url
     *
     *               SXO LOGON Method with URL Passing. This method used default
     *               Racf ID and Pwd.
     */
    public static   void sxoLogon(final WebDriver driver, final String url) {
        navigateToURL(driver, url);
        LOGGER.info("Launched sxo url:{} ", url);
        driver.findElement(By.id(LOGINPAGE_USERNAME_TEXTBOX_OBJECT_ID)).sendKeys(DEFAULT_RACFID);
        driver.findElement(By.id(LOGINPAGE_PASSWORD_TEXTBOX_OBJECT_ID)).sendKeys(DEFAULT_RACFID_PASSWORD);
        LOGGER.info(USERNAME + DEFAULT_RACFID + AND_PASSWORD + DEFAULT_RACFID_PASSWORD + IS_ENTERED);
        driver.findElement(By.xpath(LOGINPAGE_LOGIN_BUTTON_OBJECT_XPATH)).click();
        LOGGER.info("Login button is clicked");
        try {
            Thread.sleep(5000);
        } catch (final InterruptedException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
    }

    /**
     * ----This Method is for Sorting of Ascending&Descending Order---------------
     */

    public static   void verifyAscendingAndDescending(final WebDriver driver, final String XPATH,
                                                    final String elementName) {
        try {
            final JavascriptExecutor js = (JavascriptExecutor) driver;
            final List<WebElement> AllAscendingAndDescending = driver.findElements(By.xpath(XPATH));
            for (final WebElement AscAndDsc : AllAscendingAndDescending) {
                final String value = AscAndDsc.getText();
                final String script = "return arguments[0].innerHTML";
                LOGGER.info(elementName + ":" + value);
            }
        } catch (final TimeoutException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
    }

    public static   void clearTextField(final WebDriver driver, final WebElement webElement, final String inputValue) {
        final WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME_IN_SECS));
        try {
            driverWait.until(ExpectedConditions.visibilityOf(webElement));
            webElement.sendKeys(Keys.CONTROL, Keys.chord("a"));
            webElement.sendKeys(Keys.BACK_SPACE);
        } catch (TimeoutException | NoSuchElementException ele) {
            LOGGER.error(ERROR_MSG, ele);
            fail();
        }
    }

    /**
     * -------------------------------Add days to current
     * date--------------------------------------
     */
    public static   Date addDays(final int daysCount) {
        Date date = new Date();
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, daysCount);
        date = cal.getTime();
        return date;
    }

   public static  String takeScreenshot(WebDriver driver,String screenshotName) {
        String destination = null;
        String imgPath = null;
        int maxRetryCount = 5;
        int retryCounter = 0;
        while (driver instanceof TakesScreenshot) {
            String dateName = new SimpleDateFormat(CommonConstants.YYYY_MM_DD_HH_MM_SS).format(new Date());
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                imgPath = "\\TestsScreenshots\\" + screenshotName + dateName + ".png";
                destination = System.getProperty("user.dir") + imgPath;
                File finalDestination = new File(destination);
                FileUtils.copyFile(source, finalDestination);
                LOGGER.info("Screenshot destination : " + destination);
                return imgPath;
            } catch (IOException e) {
                LOGGER.error("takeScreenshot Exception : " + e.getMessage());
                if (++retryCounter > maxRetryCount) {
                    Assertions.assertTrue(false, "Exception while taking screenshot : " + e.getMessage());
                    break;
                }
            }
        }
        return destination;
    }

    public static   void screenshotLocatorWrite(WebDriver driver, WebElement element, String imageName){
        try {
            Screenshot screenshot = new AShot().takeScreenshot(driver, element);
            ImageIO.write(screenshot.getImage(), "PNG",
                    new File(System.getProperty("user.dir") + "/images/" + imageName + ".png"));
        }catch (IOException e){
            System.out.println("OPTA: Not able to take screenshot locator - " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static   boolean compareImages(WebDriver driver, String savedImage, WebElement locator){
        try {
            //get the saved image, the expected one
            BufferedImage expectedSatellite =
                    ImageIO.read(new File(System.getProperty("user.dir")+"/images/"+ savedImage +".png"));

            //take the new screenshot
            Screenshot screenshot = new AShot().takeScreenshot(driver, locator);
            BufferedImage actualSatellite = screenshot.getImage();

            //compare both images
            ImageDiffer imgDiff = new ImageDiffer();
            ImageDiff diff = imgDiff.makeDiff(expectedSatellite,actualSatellite);

            return diff.hasDiff();

        }catch (IOException e){
            System.out.println("OPTA: Not able to compare images - " + e.getMessage());
            e.printStackTrace();

            return true;
        }
    }

    public static   String getDownloadedDocumentName(String downloadDir, String fileExtension)
    {
        String downloadedFileName = null;
        boolean valid = true;
        boolean found = false;

        //default timeout in seconds
        long timeOut = 60;
        try
        {
            //set the path
            Path downloadFolderPath = Paths.get(downloadDir);
            //create a listener
            WatchService watchService = FileSystems.getDefault().newWatchService();
            // add the listener to the path
            downloadFolderPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            long startTime = System.currentTimeMillis();
            do
            {
                WatchKey watchKey;
                watchKey = watchService.poll(timeOut, TimeUnit.SECONDS);
                long currentTime = (System.currentTimeMillis()-startTime)/1000;
                //check if 60 seconds already passed
                if(currentTime>timeOut)
                {
                    System.out.println("OPTA: Download operation timed out... Expected file was not downloaded");
                    return downloadedFileName;
                }

                for (WatchEvent<?> event : watchKey.pollEvents())
                {
                    WatchEvent.Kind <?> kind = event.kind();
                    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE))
                    {
                        String fileName = event.context().toString();
                        //Checks if the file created is a PDF
                        if(fileName.endsWith(fileExtension))
                        {
                            downloadedFileName = fileName;
                            found = true;
                            break;
                        }
                    }
                }
                if(found)
                {
                    return downloadedFileName;
                }
                else
                {
                    currentTime = (System.currentTimeMillis()-startTime)/1000;
                    if(currentTime>timeOut)
                    {
                        System.out.println("Failed to download expected file");
                        return downloadedFileName;
                    }
                    valid = watchKey.reset();
                }
            } while (valid);
        }

        catch (InterruptedException e)
        {
            System.out.println("OPTA: Interrupted error - " + e.getMessage());
            e.printStackTrace();
        }
        catch (NullPointerException e)
        {
            System.out.println("OPTA: Download operation timed out.. Expected file was not downloaded");
        }
        catch (Exception e)
        {
            System.out.println("OPTA: Error occurred - " + e.getMessage());
            e.printStackTrace();
        }
        return downloadedFileName;
    }


    public static   String checkValueChanged(WebDriver driver, String expectedValue, String path, int waitTime) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String elementValue = (String) js
                .executeScript("return document.evaluate(\""+path+"\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.value;");

        while (!(elementValue.equals(expectedValue)) && (waitTime>0)) {
            Thread.sleep(1000);
            waitTime--;
            elementValue = (String) js
                    .executeScript("return document.evaluate(\""+path+"\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.value;");
        }
        return elementValue;
    }


    public static  void clickElementWithWait(WebDriver driver,String locator, int time){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        WebElement element=driver.findElement(By.xpath(locator));
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();

    }
    public static  void clickElementWithWait(WebDriver driver,WebElement element, int time){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();

    }

    private static  FluentWait webDriverFluentWait(WebDriver driver) {
        return new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(NoSuchElementException.class, NoSuchFrameException.class);
    }

    private static  FluentWait webDriverFluentWait(WebDriver driver,int time) {
        return new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(time))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class, NoSuchFrameException.class);
    }

    public static  WebElement waitForElement(WebDriver driver,final By byElement) {
        WebElement element = null;
        try {
            LOGGER.info("BeforeWaitForElement::" + byElement);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(CommonConstants.MAX_WAIT));
            wait.until(ExpectedConditions.elementToBeClickable(byElement));
            if (!isElementVisible(driver,byElement)) {
                scrollToView(driver,byElement);
            } else {
                element = driver.findElement(byElement);
            }
        } catch (WebDriverException e) {
            LOGGER.error("Exception in waitForElement::" + byElement + " " + e);
            throw new WebDriverException(e);
        }
        return element;
    }

    public static  void waitForElement(WebDriver driver,final By byElement,int time) {
        try {
            LOGGER.info("BeforeWaitForElement::" + byElement);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
            wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));

        } catch (WebDriverException e) {
            LOGGER.error("Exception in waitForElement::" + byElement + " " + e);
            throw new WebDriverException(e);
        }

    }

    private static  List<WebElement> waitForElements(WebDriver driver,By element, int timeout) {
        new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOfElementLocated(element));
        return driver.findElements(element);
    }

    public static  List<WebElement> waitForElements(WebDriver driver,By element) {
        return waitForElements(driver,element, CommonConstants.MAX_WAIT);
    }

    public static  void waitForElementDisplayed(WebDriver driver,By locator) {
        try {
            if (!driver.getWindowHandles().isEmpty()) {
                waitForElementLoading(driver,locator);
            }
        } catch (Exception e) {
            LOGGER.error("Locator not found and the reason for failure is " + e);
        }
    }

    private static  void waitForElementLoading(WebDriver driver,final By byElement) {
        try {
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(15))
                    .pollingEvery(Duration.ofMillis(5))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(TimeoutException.class);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(byElement));
            if (!element.isDisplayed()) {
                LOGGER.info("Element " + byElement + " is not displayed");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to find the element and the reason is " + e);
        }
    }

    public static  boolean isAlertPresent(WebDriver driver)
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert();
            return true;
        }
        catch (NoAlertPresentException e)
        {
            throw new NoAlertPresentException();
        }
    }

    public static  void waitUntilDomLoad(WebDriver driver) {
        LOGGER.info("Inside waitForElementLoading");
        FluentWait fluentWait = readyStateWait(driver);
        if (driver.getTitle().contains("/maintenix/")) {
            ExpectedCondition<Boolean> jQueryLoad;
            try {
                jQueryLoad = webDriver -> ((Long) ((JavascriptExecutor) driver)
                        .executeScript("return jQuery.active") == 0);
            } catch (Exception e) {
                jQueryLoad = webDriver -> (true);
                LOGGER.error("Failed to waitForElementLoading: " + e);
            }
            fluentWait.until(jQueryLoad);
        }
        try {
            ExpectedCondition<Boolean> docLoad = webDriver -> ((Boolean) ((JavascriptExecutor) driver)
                    .executeScript("return document.readyState").toString().equals("complete"));
            fluentWait.until(docLoad);
        } catch (Exception e) {
            LOGGER.error("Failed to waitForElementLoading " + e);
        }
        LOGGER.info("Dom load completed");
    }

    private static  FluentWait readyStateWait(WebDriver driver) {
        return new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(WebDriverException.class);
    }

    public static  boolean isElementPresent(WebDriver driver,By locator) {
        try {
            LOGGER.info("Before isElementPresent::" + locator);
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            LOGGER.info("Exception isElementPresent::" + locator + " " + e);
            return false;
        }
    }

    public static  boolean isElementVisible(WebDriver driver,By locator) {
        try {
            webDriverFluentWait(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            return true;
        } catch (Exception e) {
            LOGGER.info("Exception isElementVisible::" + locator + " " + e);
            return false;
        }
    }
    public static  boolean isElementVisible(WebDriver driver,WebElement element) {
        try {
            webDriverFluentWait(driver).until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            LOGGER.info("Exception isElementVisible::" + element + " " + e);
            return false;
        }
    }

    public static  boolean isElementVisible(WebDriver driver,By locator, int time) {
        try {
            webDriverFluentWait(driver,time).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            return true;
        } catch (Exception e) {
            LOGGER.info("Exception isElementVisible::" + locator + " " + e);
            return false;
        }
    }

    public static  void waitByTime(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            LOGGER.info("Fail in wait due to : " + e);
            Thread.currentThread().interrupt();
        }
    }

    public static  void clearAndEnterText(WebDriver driver,By locator, String text) {
        LOGGER.info("Before clearAndEnterText::" + locator + ", with text: " + text);
        WebElement webElementEnter = waitForElement(driver,locator);
        if (!isElementVisible(driver,locator)) {
            scrollToElement(driver,webElementEnter);
        }
        highlightElement(driver,locator);
        webElementEnter.click();
        webElementEnter.clear();
        String value = webElementEnter.getAttribute("value");
        if (!value.isEmpty()) {
            webElementEnter.sendKeys(Keys.CONTROL + "a");
            webElementEnter.sendKeys(Keys.DELETE);
        }
        Actions action = new Actions(driver);
        action.sendKeys(webElementEnter, text).build().perform();
    }

    public static  void clearAndEnterValue(WebDriver driver,By locator, String text) {
        LOGGER.info("Before clearAndEnterText::" + locator + ", with text: " + text);
        WebElement webElementEnter = driver.findElement(locator);
        if (!isElementVisible(driver,locator)) {
            scrollToElement(driver,webElementEnter);
        }
        webElementEnter.click();
        webElementEnter.clear();
        Actions action = new Actions(driver);
        LOGGER.info("entering with text: " + text);
        action.sendKeys(webElementEnter, text).build().perform();
    }

    public static  void enterText(WebDriver driver,By locator, String text) {
        LOGGER.info("Before enterText::" + locator + ", with text::" + text);
        if (!isElementVisible(driver,locator)) {
            scrollToView(driver,locator);
        }
        WebElement webElementEnter = waitForElement(driver,locator);
        highlightElement(driver,locator);
        webElementEnter.sendKeys(text);
    }

    public static  void enteringText(WebDriver driver,By locator, String text) {
        LOGGER.info("Before enterText::" + locator + ", with text::" + text);
        if (!isElementVisible(driver,locator)) {
            scrollToView(driver,locator);
        }
        WebElement webElementEnter = waitForElement(driver,locator);
        webElementEnter.sendKeys(text);
    }

    public static  void clickUsingJavaScript(WebDriver driver,By locator) {
        LOGGER.info("BeforeWaitForElement in clickUsingJavaScript::" + locator);
        try {
            WebElement elm = waitForElement(driver,locator);
            if (!isElementVisible(driver,locator)) {
                scrollToElement(driver,elm);
            }
            highlightElement(driver,locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click()", elm);
            waitTillLoadingCompletes(driver);
        } catch (Exception e) {
            LOGGER.info("Unable to highlight : " + e);
        }
    }
    public static  void clickUsingJavaScript(WebDriver driver,WebElement element) {
        LOGGER.info("BeforeWaitForElement in clickUsingJavaScript::" + element);
        try {
            if (!isElementVisible(driver,element)) {
                scrollToElement(driver,element);
            }
            highlightElement(driver,element);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click()", element);
            waitTillLoadingCompletes(driver);
        } catch (Exception e) {
            LOGGER.info("Unable to highlight : " + e);
        }
    }

    public static  void clickWithWait(WebDriver driver,By locator) {
        try {
            WebElement elm = waitForElement(driver,locator);
            LOGGER.info(BEFORE_WAIT_FOR_ELEMENT_IN_BUTTON_CLICK + locator);
            if (!isElementVisible(driver,locator)) {
                scrollToView(driver,locator);
            }
            waitForElementDisplayed(driver,locator);
            if (isElementVisible(driver,locator)) {
                elm.click();
            } else {
                waitUntilElementClickable(driver,locator);
                elm.click();
            }
            waitTillLoadingCompletes(driver);
        } catch (Exception e) {
            LOGGER.error(Level.FINEST + "Exception in clicking::" + e);
        }
    }

    public static  void buttonClick(WebDriver driver,By locator) {
        try {
            LOGGER.info(BEFORE_WAIT_FOR_ELEMENT_IN_BUTTON_CLICK + locator);
            if (!isElementVisible(driver,locator)) {
                scrollToView(driver,locator);
            }
            WebElement elm = waitForElement(driver,locator);
            waitUntilElementClickable(driver,locator);
            if (isElementVisible(driver,locator)) {
                highlightElement(driver,locator);
                elm.click();
            } else {
                waitUntilElementClickable(driver,locator);
                highlightElement(driver,locator);
                elm.click();
            }
            waitTillLoadingCompletes(driver);
        } catch (Exception e) {
            LOGGER.error("Exception in buttonClick::" + e);
        }
    }

    public static  void buttonClick(WebDriver driver,WebElement element) {
        try {
            LOGGER.info("BeforeWaitForElement in buttonClick::");
            webDriverFluentWait(driver).until(ExpectedConditions.elementToBeClickable(element));
            scrollToElement(driver,element);
            highlightElement(driver,element);
            waitByTime(1000);
            element.click();
            waitTillLoadingCompletes(driver);
        } catch (Exception e) {
            LOGGER.error("Exception in buttonClick ::" + e);
        }
    }

    public static  void doubleClick(WebDriver driver,By locator) {
        Actions actions = new Actions(driver);
        WebElement elementLocator = driver.findElement(locator);
        waitForElement(driver,locator);
        waitByTime(1000);
        if (!isElementVisible(driver,locator)) {
            scrollToView(driver,locator);
        }
        waitForElement(driver,locator);
        if (isElementVisible(driver,locator)) {
            actions.moveToElement(elementLocator).build();
            highlightElement(driver,locator);
            actions.doubleClick(elementLocator).build().perform();
        } else {
            waitUntilElementClickable(driver,locator);
            highlightElement(driver,locator);
            actions.moveToElement(elementLocator).build();
            actions.doubleClick(elementLocator).build().perform();
        }
        waitTillLoadingCompletes(driver);
    }

    public static  void singleClick(WebDriver driver,By locator) {
        Actions actions = new Actions(driver);
        WebElement elementLocator = driver.findElement(locator);
        waitForElement(driver,locator);
        waitByTime(1000);
        if (!isElementVisible(driver,locator)) {
            scrollToView(driver,locator);
        }
        waitForElement(driver,locator);
        if (isElementVisible(driver,locator)) {
            actions.moveToElement(elementLocator).build();
            highlightElement(driver,locator);
            actions.click(elementLocator).build().perform();
        } else {
            waitUntilElementClickable(driver,locator);
            highlightElement(driver,locator);
            actions.moveToElement(elementLocator).build();
            actions.click(elementLocator).build().perform();
        }
        waitTillLoadingCompletes(driver);
    }

    public static  void waitUntilElementClickable(WebDriver driver,By locator) {
        LOGGER.info("Before waitUntilElementVisible::" + locator);
        WebDriverWait expWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        expWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static  void selectOption(WebDriver driver,By locator, String opt) {
        LOGGER.info("Before selectOption::" + locator + ", with Select Option::" + opt);
        WebElement element = waitForElement(driver,locator);
        webDriverFluentWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(element);
        select.selectByVisibleText(opt);
    }

    public static  boolean waitUntilTextPresentInElement(WebDriver driver,By locator, String elementText, int seconds) {
        WebDriverWait expWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return expWait.until(ExpectedConditions.textToBePresentInElementLocated(locator, elementText));
    }

    public static  void scrollToView(WebDriver driver,By locator) {
        WebElement element = driver.findElement(locator);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static  void scrollToElement(WebDriver driver,WebElement webelement) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(true);", webelement);
        jse.executeScript("window.scrollBy(0,100)", "");
        LOGGER.info("ScrollToElement::" + webelement + "Done");
    }

    public static  void uncheckCheckbox(WebDriver driver,By locator) {
        waitForElement(driver,locator);
        if (Boolean.TRUE.equals(driver.findElement(locator).isSelected())) {
            highlightElement(driver,locator);
            clickWithWait(driver,locator);
        }
    }

    public static  void checkCheckbox(WebDriver driver,By locator) {
        waitForElement(driver,locator);
        if (Boolean.FALSE.equals(driver.findElement(locator).isSelected())) {
            highlightElement(driver,locator);
            clickWithWait(driver,locator);
        }
    }

    public static  void highlightElement(WebDriver driver,WebElement webElement) {
        try {
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='4px solid orange'", webElement);
            }
        } catch (Exception e) {
            LOGGER.error("Fail in highlightElement " + e);
        }
    }

    public static  void highlightElement(WebDriver driver,By locator) {
        highlightElement(driver,driver.findElement(locator));
    }

    public static  void clickWithMultipleTimes(WebDriver driver,By locator, int clicks) {
        try {
            WebElement elm = waitForElement(driver,locator);
            LOGGER.info(BEFORE_WAIT_FOR_ELEMENT_IN_BUTTON_CLICK + locator);
            if (!isElementVisible(driver,locator)) {
                scrollToView(driver,locator);
            }
            for (int i = 0; i < clicks; i++) {
                waitForElement(driver,locator);
                if (isElementVisible(driver,locator)) {
                    elm.click();
                } else {
                    waitUntilElementClickable(driver,locator);
                    elm.click();
                }
                waitTillLoadingCompletes(driver);
            }
            waitByTime(2000);
        } catch (Exception e) {
            LOGGER.error("Exception in clickWithMultipleTimes::" + e);
        }
    }

    public static  void waitTillLoadingCompletes(WebDriver driver) {
        waitTillLoadingCompletes(driver,By.xpath("//*[@class='uicLoaderOverlay uicLo-loading']"));
        waitTillLoadingCompletes(driver,By.xpath("//*[@class='loadingBox loading']"));
    }

    public static  void setMaxSyncTime(int newSyncTime) {
        maxSyncTime = newSyncTime;
    }

    private static  void waitTillLoadingCompletes(WebDriver driver,By loadingCircle) {
        try {
            long start = System.currentTimeMillis();
            int elapsedTime = 0;
            waitByTime(250);
            while (driver.findElement(loadingCircle).isDisplayed()) {
                LOGGER.info("Waiting for Loading Screens to go away");
                if (elapsedTime > maxSyncTime) {
                    break;
                }
                waitByTime(500);
                elapsedTime = Math.round((System.currentTimeMillis() - start) / 1000F);
            }
        } catch (Exception e) {
            if ((e.toString().contains("NoSuchElementException") || e.getMessage().contains("NoStale")))
                LOGGER.info("Loading screenshot is not present now");
            else {
                LOGGER.error(Level.FINEST + "Fail in waitTillLoadingCompletes " + e);
            }
        }
    }

    private static  boolean waitUntilLocatorPresent(WebDriver driver,By expectedLocator, String context) {
        boolean locatorFound = false;
        try {
            if (isCustomWait) {
                isCustomWait = false;
            } else {
                maxSyncTime = defaultMaxTime;
            }
            long start = System.currentTimeMillis();
            int elapsedTime = 0;
            while (elapsedTime < maxSyncTime) {
                if (isElementPresent(driver,expectedLocator)) {
                    locatorFound = true;
                    waitUntilElementClickable(driver,expectedLocator);
                    break;
                }
                elapsedTime = Math.round((System.currentTimeMillis() - start) / 1000F);
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred in + 'waitUntilLocatorPresent' for locator:: " + expectedLocator.toString() + " and context:: " + context + "Exception" + e);
        }
        return locatorFound;
    }

    public static  String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(CommonConstants.DD_MMM);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static  String getCurrentDateInDDMMMyy() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMMyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static  String getFutureDate(int value) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(CommonConstants.DD_MMM);
        LocalDateTime now = LocalDateTime.now();
        now = now.plusDays(value);
        return dtf.format(now);
    }

    public static  String getNewTravelDate(String departureDate, String typeOfFormat) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(typeOfFormat);
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(departureDate));
        c.add(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(c.getTime());
    }

    public static  String getDate(String depArrDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(MM_DD_YYYY_WITH_SLASH);
        SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMMyy");
        Date d = sdf.parse(depArrDate);
        return sdf1.format(d);
    }

    public static  String getCurrentDateInMMYY() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMyy");
        LocalDateTime now = LocalDateTime.now();
        now = now.plusYears(1);
        return dtf.format(now);
    }

    public static  String getCurrentYearInYY() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static  int dateConversion(String date) throws ParseException {
        String date1 = date.replace("/", "");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(CommonConstants.MM_DD_YYYY);
        Date newDate = new SimpleDateFormat(CommonConstants.MM_DD_YYYY).parse(date1);
        LocalDate now = LocalDate.now();
        Date currentDate = new SimpleDateFormat(CommonConstants.MM_DD_YYYY).parse(dtf.format(now));
        return Days.daysBetween(new org.joda.time.LocalDate(currentDate.getTime()), new org.joda.time.LocalDate(newDate.getTime())).getDays();
    }

    public static  String getUMNRDOB(int years) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(CommonConstants.DD_MMM_YY);
        LocalDateTime now = LocalDateTime.now();
        now = now.minusYears(years);
        return dtf.format(now);
    }

    public static  String getTimeATD() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("CST"));
        return format.format(new Date());
    }

    public static  String getTimeInATW() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("CST"));
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 1);
        return format.format(now.getTime());
    }

    public static  String getPreviousDayInddMMM() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(CommonConstants.DD_MMM);
        LocalDateTime now = LocalDateTime.now();
        now = now.minusDays(1);
        return dtf.format(now);
    }

    public static  String getDateInDdMmm(String depArrDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(MM_DD_YYYY_WITH_SLASH);
        SimpleDateFormat sdf1 = new SimpleDateFormat(CommonConstants.DD_MMM);
        Date d = sdf.parse(depArrDate);
        return sdf1.format(d);
    }

    public static  String getFutureDateMmDdYyyy(int value) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(MM_DD_YYYY_WITH_SLASH);
        LocalDateTime now = LocalDateTime.now();
        now = now.plusDays(value);
        return dtf.format(now);
    }

    public static  int getCurrentYear()  {
        Date d = new Date();
        int year = d.getYear();
        return year + 1900;
    }

}
