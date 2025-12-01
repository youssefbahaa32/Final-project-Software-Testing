package UserStory_Localization.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LocalizationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By currencyDropdown = By.id("form-currency");
    private By currencyButton = By.cssSelector("button[data-bs-toggle='dropdown']");
    private By currencyOptions = By.cssSelector(".dropdown-menu.dropdown-menu-end a");
    
    private By languageDropdown = By.id("form-language");
    private By languageButton = By.cssSelector("button[data-bs-toggle='dropdown']");
    private By languageOptions = By.cssSelector(".dropdown-menu a");
    
    private By productPrices = By.cssSelector(".price");
    private By pageContent = By.tagName("body");
    
    public LocalizationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void switchCurrency(String currency) {
        try {
            // Try to find currency dropdown
            WebElement currencyBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.dropdown-toggle")));
            currencyBtn.click();
            
            // Select currency from dropdown
            List<WebElement> currencies = driver.findElements(By.cssSelector(".dropdown-menu a"));
            for (WebElement curr : currencies) {
                if (curr.getText().contains(currency)) {
                    curr.click();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Currency selector not found or not clickable");
        }
    }

    public String getCurrentCurrency() {
        try {
            WebElement currencyBtn = driver.findElement(By.cssSelector("button.dropdown-toggle"));
            return currencyBtn.getText().trim();
        } catch (Exception e) {
            return "USD"; // Default
        }
    }

    public boolean arePricesInCurrency(String currencySymbol) {
        try {
            List<WebElement> prices = driver.findElements(productPrices);
            if (prices.isEmpty()) {
                return false;
            }
            
            for (WebElement price : prices) {
                if (price.getText().contains(currencySymbol)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void switchLanguage(String language) {
        try {
            // Try to find language dropdown
            WebElement langBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.dropdown-toggle")));
            langBtn.click();
            
            // Select language from dropdown
            List<WebElement> languages = driver.findElements(By.cssSelector(".dropdown-menu a"));
            for (WebElement lang : languages) {
                if (lang.getText().contains(language)) {
                    lang.click();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Language selector not found or not clickable");
        }
    }

    public String getCurrentLanguage() {
        try {
            WebElement langBtn = driver.findElement(By.cssSelector("button.dropdown-toggle"));
            return langBtn.getText().trim();
        } catch (Exception e) {
            return "English"; // Default
        }
    }

    public boolean isPageInLanguage(String expectedText) {
        try {
            String pageText = driver.findElement(pageContent).getText();
            return pageText.contains(expectedText);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCurrencyPersisted() {
        String currentCurrency = getCurrentCurrency();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.presenceOfElementLocated(pageContent));
        String currencyAfterRefresh = getCurrentCurrency();
        return currentCurrency.equals(currencyAfterRefresh);
    }

    public boolean isLanguagePersisted() {
        String currentLanguage = getCurrentLanguage();
        driver.navigate().refresh();
        wait.until(ExpectedConditions.presenceOfElementLocated(pageContent));
        String languageAfterRefresh = getCurrentLanguage();
        return currentLanguage.equals(languageAfterRefresh);
    }
}
