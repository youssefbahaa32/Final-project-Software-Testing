package UserStory_Dashboard.Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected String baseUrl;
    protected String adminUrl;
    protected String adminUsername;
    protected String adminPassword;

    @BeforeMethod
    public void setUp() {
        // Load configuration
        Properties config = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            config.load(fis);
            baseUrl = config.getProperty("base.url");
            adminUrl = config.getProperty("admin.url", baseUrl + "admin/");
            adminUsername = config.getProperty("admin.username", "admin");
            adminPassword = config.getProperty("admin.password", "admin");
        } catch (IOException e) {
            e.printStackTrace();
            baseUrl = "http://localhost/opencart/";
            adminUrl = baseUrl + "admin/";
            adminUsername = "admin";
            adminPassword = "admin";
        }

        // Setup ChromeDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
