package utillities;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class SeleniumUtils {

    /**
     * This method clicks on the given element , switches to the newly opened tab
     * and prins its url.
     *
     * @param driver  - is used to open web application
     * @param element - is clicked to open new tab
     */
    public static void switchToNewTab(WebDriver driver, WebElement element) {
        String mainWindow = driver.getWindowHandle();

        element.click();

        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(mainWindow)) {
                driver.switchTo().window(windowHandle);
            }

        }
        System.out.println("Currently , the driver is on : " + driver.getCurrentUrl());

    }

    /**
     * This method waits for element to be clickable
     * before clicking on it
     *
     * @param driver  - is used to open web application
     * @param element - is clicked to open new tab
     */

    public static void click(WebDriver driver, WebElement element) {

        try {
            FluentWait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(30))
                    .ignoring(ElementClickInterceptedException.class)
                    .ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., logging, reporting, retry logic)
        }
    }

    /**
     * This method dismisses alert , if alert is not there
     * it ignores the exception
     *
     * @param driver will take us to web application
     */

    public static void acceptAlert(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.alertIsPresent());
        try {
            driver.switchTo().alert().accept();

        } catch (NoAlertPresentException e) {
            System.out.println("Alert does not exist");
            e.printStackTrace();
        }


    }

    /**
     * This method dismisses alert , if alert is not there
     * it ignores the exception
     *
     * @param driver will take us to web application
     */

    public static void dismissAlert(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.alertIsPresent());
        try {
            driver.switchTo().alert().dismiss();

        } catch (NoAlertPresentException e) {
            System.out.println("Alert does not exist");
            e.printStackTrace();
        }
        /**
         * This method return true if element is present in the dom
         * @param driver to go to webpage
         * @param locator  to click on
         */


    }

    public static boolean isElementPresent(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException E) {
            return false;
        }
    }

    /**
     * This element will try to click on element multiple times
     * by ignoring StaleElement exception
     * @param driver to go to webpage
     * @param locator to click on
     * @param retries number of times to try
     * @throws InterruptedException
     */

    public static void clickWithRetries(WebDriver driver, By locator, int retries) throws InterruptedException {
        int numOfTrials = 0;
        while (numOfTrials < retries) {

            try {
                WebElement element = driver.findElement(locator);
                element.click();
                return; //ist is successful , exit the method
            } catch (StaleElementReferenceException e) {
                //its not successful , try again
                numOfTrials++;
                waitForSeconds(1);
            }catch (NoSuchElementException e){
                System.out.println("Wrong locator!");
                e.printStackTrace();
                return;
            }
        }
    }

    public static void waitForSeconds(int numberOfSeconds){
        try{
            Thread.sleep(numberOfSeconds * 1000);
        }catch (InterruptedException e){
    }
}

    /**
     * This methods waits for text to appear in the element
     * then verifies if it matches with expected text
     * @param driver takes to the page
     * @param numOfSec to wait for
     * @param element to verify
     * @param expectedText to verify
     */
    public static void waitForTextToAppearInElement(WebDriver driver , int numOfSec , WebElement element,String expectedText){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(numOfSec));
        wait.until(ExpectedConditions.textToBePresentInElement(element,expectedText));

        Assert.assertEquals(expectedText,element.getText());

    }

    public static void clearAndSendKeys(WebDriver driver , WebElement element , String textToSend){
        Actions actions = new Actions(driver);
        actions.keyDown(element,Keys.COMMAND).sendKeys("a");
        actions.keyUp(element,Keys.COMMAND);
        actions.keyDown(element,Keys.BACK_SPACE);
        actions.keyUp(element,Keys.BACK_SPACE);

        actions.build().perform();

        element.sendKeys(textToSend);
    }


}

