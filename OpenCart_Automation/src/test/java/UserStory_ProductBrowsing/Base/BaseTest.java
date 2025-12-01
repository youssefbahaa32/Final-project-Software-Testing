package UserStory_ProductBrowsing.Base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl;
    protected Properties config;

    public BaseTest() {
        config = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            config.load(fis);
            baseUrl = config.getProperty("base.url", "http://localhost/opencart/");
        } catch (IOException e) {
            baseUrl = "http://localhost/opencart/";
            System.out.println("Config file not found, using default URL: " + baseUrl);
        }
    }

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--incognito");
        
        driver = new ChromeDriver(options);
        
        int explicitWait = Integer.parseInt(config.getProperty("explicit.wait", "10"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        
        driver.get(baseUrl);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
