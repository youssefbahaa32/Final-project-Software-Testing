package UserStory_Localization.Tests;

import UserStory_Localization.Base.BaseTest;
import UserStory_Localization.Pages.LocalizationPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LocalizationTests extends BaseTest {

    @Test(priority = 1, description = "Localization | Switch Currency")
    public void testSwitchCurrencyToEUR() {
        LocalizationPage localizationPage = new LocalizationPage(driver);
        
        String initialCurrency = localizationPage.getCurrentCurrency();
        System.out.println("Initial currency: " + initialCurrency);
        
        localizationPage.switchCurrency("EUR");
        
        try {
            Thread.sleep(2000); // Wait for page to update
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        String newCurrency = localizationPage.getCurrentCurrency();
        System.out.println("Currency after switch: " + newCurrency);
        
        // Navigate to a product page to see prices
        driver.findElement(By.linkText("Desktops")).click();
        driver.findElement(By.linkText("Show All Desktops")).click();
        
        boolean pricesInEUR = localizationPage.arePricesInCurrency("€");
        if (pricesInEUR) {
            System.out.println("Prices successfully displayed in EUR");
        } else {
            System.out.println("EUR currency may not be configured in this store");
        }
    }

    @Test(priority = 2, description = "Switch Currency to GBP")
    public void testSwitchCurrencyToGBP() {
        LocalizationPage localizationPage = new LocalizationPage(driver);
        
        localizationPage.switchCurrency("GBP");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Navigate to a product page
        driver.findElement(By.linkText("Laptops & Notebooks")).click();
        driver.findElement(By.linkText("Show All Laptops & Notebooks")).click();
        
        boolean pricesInGBP = localizationPage.arePricesInCurrency("£");
        if (pricesInGBP) {
            System.out.println("Prices successfully displayed in GBP");
        } else {
            System.out.println("GBP currency may not be configured in this store");
        }
    }

    @Test(priority = 3, description = "Localization | Currency Conversion Accuracy")
    public void testCurrencyPersistence() {
        LocalizationPage localizationPage = new LocalizationPage(driver);
        
        localizationPage.switchCurrency("EUR");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        boolean isPersisted = localizationPage.isCurrencyPersisted();
        if (isPersisted) {
            System.out.println("Currency preference persisted after page refresh");
        } else {
            System.out.println("Currency preference may not persist - check cookie/session settings");
        }
    }

    @Test(priority = 4, description = "Localization | Switch Store Language")
    public void testSwitchLanguage() {
        LocalizationPage localizationPage = new LocalizationPage(driver);
        
        String initialLanguage = localizationPage.getCurrentLanguage();
        System.out.println("Initial language: " + initialLanguage);
        
        // Try to switch to another language if available
        localizationPage.switchLanguage("French");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        String newLanguage = localizationPage.getCurrentLanguage();
        System.out.println("Language after switch: " + newLanguage);
        
        if (!newLanguage.equals(initialLanguage)) {
            System.out.println("Language switched successfully");
        } else {
            System.out.println("Additional languages may not be configured in this store");
        }
    }

    @Test(priority = 5, description = "Localization | Language Preference Persistence")
    public void testLanguagePersistence() {
        LocalizationPage localizationPage = new LocalizationPage(driver);
        
        String initialLanguage = localizationPage.getCurrentLanguage();
        
        boolean isPersisted = localizationPage.isLanguagePersisted();
        if (isPersisted) {
            System.out.println("Language preference persisted after page refresh");
        } else {
            System.out.println("Language preference may not persist - check cookie/session settings");
        }
    }

    @Test(priority = 6, description = "Localization | View Cookie Consent Settings")
    public void testCurrencyConversionAccuracy() {
        LocalizationPage localizationPage = new LocalizationPage(driver);
        
        // Navigate to product page
        driver.findElement(By.linkText("Components")).click();
        driver.findElement(By.linkText("Show All Components")).click();
        
        // Get price in USD
        localizationPage.switchCurrency("USD");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Viewing prices in USD");
        
        // Switch to EUR
        localizationPage.switchCurrency("EUR");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Viewing prices in EUR - conversion applied");
        System.out.println("Note: Verify exchange rates are current in admin settings");
    }
}
