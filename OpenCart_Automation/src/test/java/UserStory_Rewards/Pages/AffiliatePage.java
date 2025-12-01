package UserStory_Rewards.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AffiliatePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By myAccountLink = By.linkText("My Account");
    private By registerAffiliateLink = By.linkText("Register for an affiliate account");
    private By affiliateDashboardLink = By.linkText("My Affiliate Account");
    
    // Registration Form
    private By companyField = By.id("input-company");
    private By websiteField = By.id("input-website");
    private By taxField = By.id("input-tax");
    private By paymentMethodRadio = By.name("payment_method");
    private By chequeNameField = By.id("input-cheque");
    private By agreeCheckbox = By.name("agree");
    private By continueButton = By.cssSelector("button[type='submit']");
    private By successMessage = By.cssSelector(".alert-success");
    
    // Dashboard
    private By trackingCode = By.cssSelector("code");
    private By totalClicks = By.xpath("//td[contains(text(),'Total Clicks')]/following-sibling::td");
    private By totalSales = By.xpath("//td[contains(text(),'Total Sales')]/following-sibling::td");
    private By totalCommission = By.xpath("//td[contains(text(),'Total Commission')]/following-sibling::td");
    
    public AffiliatePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void navigateToAffiliateRegistration() {
        wait.until(ExpectedConditions.elementToBeClickable(myAccountLink)).click();
        wait.until(ExpectedConditions.elementToBeClickable(registerAffiliateLink)).click();
    }

    public void fillAffiliateRegistration(String company, String website, String tax, 
                                         String paymentMethod, String chequeName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(companyField));
        driver.findElement(companyField).sendKeys(company);
        driver.findElement(websiteField).sendKeys(website);
        driver.findElement(taxField).sendKeys(tax);
        
        // Select payment method
        WebElement paymentRadio = driver.findElement(By.cssSelector("input[value='" + paymentMethod + "']"));
        paymentRadio.click();
        
        if (paymentMethod.equals("cheque")) {
            driver.findElement(chequeNameField).sendKeys(chequeName);
        }
        
        driver.findElement(agreeCheckbox).click();
        driver.findElement(continueButton).click();
    }

    public boolean isRegistrationSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return driver.findElement(successMessage).getText().contains("Success");
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToAffiliateDashboard() {
        wait.until(ExpectedConditions.elementToBeClickable(myAccountLink)).click();
        wait.until(ExpectedConditions.elementToBeClickable(affiliateDashboardLink)).click();
    }

    public boolean isDashboardDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(trackingCode));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTrackingCode() {
        return driver.findElement(trackingCode).getText();
    }

    public String getTotalClicks() {
        try {
            return driver.findElement(totalClicks).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public String getTotalSales() {
        try {
            return driver.findElement(totalSales).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public String getTotalCommission() {
        try {
            return driver.findElement(totalCommission).getText();
        } catch (Exception e) {
            return "$0.00";
        }
    }
}
