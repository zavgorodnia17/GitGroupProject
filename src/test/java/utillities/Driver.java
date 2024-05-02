package utillities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class Driver {

        static WebDriver driver;


        public static WebDriver getDriver(){

            //if driver object already exists , then we just return it
            if(driver!= null){
                return driver;
            }

            String browser = Config.getProperty("browser");
            switch (browser){
                case "chrome":
                    driver=new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "safari":
                    driver = new SafariDriver();
                    break;
                default:
                    driver = new ChromeDriver();
            }



            driver = new ChromeDriver();

            //these are implicit waits applied to the driver
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
            return driver;
        }
    }

