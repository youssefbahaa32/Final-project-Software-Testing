package UserStory_User_Management.Base;

import UserStory_User_Management.Pages.AdminLoginPage;
import UserStory_User_Management.Pages.AdminDashboardPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected String adminUrl;
    protected String adminUsername;
    protected String adminPassword;
    protected AdminDashboardPage dashboardPage;

    @BeforeMethod
    public void setUp() {
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("src/test/resources/config.properties"));
            adminUrl = config.getProperty("admin.url", "http://localhost/opencart/admin/");
            adminUsername = config.getProperty("admin.username", "admin");
            adminPassword = config.getProperty("admin.password", "admin");
        } catch (Exception e) {
            adminUrl = "http://localhost/opencart/admin/";
            adminUsername = "admin";
            adminPassword = "admin";
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        
        driver.get(adminUrl);
        dashboardPage = new AdminLoginPage(driver).login(adminUsername, adminPassword);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
