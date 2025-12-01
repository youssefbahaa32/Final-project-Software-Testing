package UserStory_AccountManagement.Base;

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
        options.addArguments("--incognito");  // Use incognito mode for fresh session each test
        
        driver = new ChromeDriver(options);
        
        // Set implicit wait
        int implicitWait = Integer.parseInt(config.getProperty("implicit.wait", "10"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        
        // Set page load timeout
        int pageLoadTimeout = Integer.parseInt(config.getProperty("page.load.timeout", "30"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        
        int explicitWait = Integer.parseInt(config.getProperty("explicit.wait", "10"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        
        driver.get(baseUrl);
        
        // Wait for page to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void navigateTo(String path) {
        driver.get(baseUrl + path);
    }

    protected String getConfigProperty(String key) {
        return config.getProperty(key);
    }
}
