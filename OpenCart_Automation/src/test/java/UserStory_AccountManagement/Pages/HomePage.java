package UserStory_AccountManagement.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomePage extends BasePage {

    private By[] registerLinkLocators = {
        By.linkText("Register"),
        By.partialLinkText("Register"),
        By.xpath("//a[contains(text(), 'Register')]"),
        By.xpath("//a[contains(@href, 'account/register')]"),
        By.cssSelector("a[href*='account/register']")
    };
    
    private By[] loginLinkLocators = {
        By.linkText("Login"),
        By.partialLinkText("Login"),
        By.xpath("//a[contains(text(), 'Login')]"),
        By.xpath("//a[contains(@href, 'account/login')]"),
        By.cssSelector("a[href*='account/login']")
    };
    
    private By[] logoutLinkLocators = {
        By.linkText("Logout"),
        By.partialLinkText("Logout"),
        By.xpath("//a[contains(text(), 'Logout')]"),
        By.xpath("//a[contains(@href, 'account/logout')]"),
        By.cssSelector("a[href*='account/logout']")
    };
    
    private By[] accountDropdownLocators = {
        By.xpath("//a[contains(@class, 'dropdown-toggle')]//i[contains(@class, 'fa-user')]"),
        By.xpath("//a[@data-bs-toggle='dropdown']//i[contains(@class, 'fa-user')]"),
        By.linkText("My Account"),
        By.partialLinkText("My Account"),
        By.xpath("//a[contains(text(), 'My Account')]")
    };

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private WebElement findElementWithMultipleStrategies(By[] locators) {
        for (By locator : locators) {
            try {
                List<WebElement> elements = driver.findElements(locator);
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    return elements.get(0);
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    public RegistrationPage navigateToRegistration() {
        try {
            WebElement registerLink = findElementWithMultipleStrategies(registerLinkLocators);
            
            if (registerLink != null) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true);", registerLink);
                Thread.sleep(500);
                js.executeScript("arguments[0].click();", registerLink);
                return new RegistrationPage(driver);
            }
            
            WebElement dropdown = findElementWithMultipleStrategies(accountDropdownLocators);
            if (dropdown != null) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", dropdown);
                Thread.sleep(1000);
                
                registerLink = findElementWithMultipleStrategies(registerLinkLocators);
                if (registerLink != null) {
                    js.executeScript("arguments[0].click();", registerLink);
                    return new RegistrationPage(driver);
                }
            }
            
            String currentUrl = driver.getCurrentUrl();
            String baseUrl = currentUrl.split("index.php")[0];
            driver.get(baseUrl + "index.php?route=account/register");
            return new RegistrationPage(driver);
            
        } catch (Exception e) {
            e.printStackTrace();
            String currentUrl = driver.getCurrentUrl();
            String baseUrl = currentUrl.split("index.php")[0];
            driver.get(baseUrl + "index.php?route=account/register");
            return new RegistrationPage(driver);
        }
    }

    public LoginPage navigateToLogin() {
        try {
            WebElement loginLink = findElementWithMultipleStrategies(loginLinkLocators);
            
            if (loginLink != null) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true);", loginLink);
                Thread.sleep(500);
                js.executeScript("arguments[0].click();", loginLink);
                return new LoginPage(driver);
            }
            
            WebElement dropdown = findElementWithMultipleStrategies(accountDropdownLocators);
            if (dropdown != null) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", dropdown);
                Thread.sleep(1000);
                
                loginLink = findElementWithMultipleStrategies(loginLinkLocators);
                if (loginLink != null) {
                    js.executeScript("arguments[0].click();", loginLink);
                    return new LoginPage(driver);
                }
            }
            
            String currentUrl = driver.getCurrentUrl();
            String baseUrl = currentUrl.split("index.php")[0];
            driver.get(baseUrl + "index.php?route=account/login");
            return new LoginPage(driver);
            
        } catch (Exception e) {
            e.printStackTrace();
            String currentUrl = driver.getCurrentUrl();
            String baseUrl = currentUrl.split("index.php")[0];
            driver.get(baseUrl + "index.php?route=account/login");
            return new LoginPage(driver);
        }
    }

    public void logout() {
        try {
            WebElement logoutLink = findElementWithMultipleStrategies(logoutLinkLocators);
            
            if (logoutLink != null) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", logoutLink);
                return;
            }
            
            WebElement dropdown = findElementWithMultipleStrategies(accountDropdownLocators);
            if (dropdown != null) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", dropdown);
                Thread.sleep(1000);
                
                logoutLink = findElementWithMultipleStrategies(logoutLinkLocators);
                if (logoutLink != null) {
                    js.executeScript("arguments[0].click();", logoutLink);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLogoutLinkDisplayed() {
        try {
            WebElement logoutLink = findElementWithMultipleStrategies(logoutLinkLocators);
            return logoutLink != null;
        } catch (Exception e) {
            return false;
        }
    }
}
