package UserStory_Sales.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderDetailPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private By orderDetailsSection = By.cssSelector(".table-responsive");
    private By orderStatusSelect = By.id("input-order-status");
    private By orderHistoryComment = By.id("input-comment");
    private By addHistoryButton = By.id("button-history");
    private By refundButton = By.cssSelector("button[title*='Refund']");
    private By refundAmountInput = By.id("input-refund-amount");
    private By confirmRefundButton = By.id("button-refund");
    private By successAlert = By.cssSelector(".alert-success");

    public OrderDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean isOrderDetailsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(orderDetailsSection));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void changeOrderStatus(String newStatus, String comment) {
        try {
            // Click on the status input to open dropdown
            WebElement statusInput = wait.until(ExpectedConditions.elementToBeClickable(orderStatusSelect));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", statusInput);
            statusInput.click();
            Thread.sleep(500);
            
            // Find and click the dropdown option
            By optionLocator = By.xpath("//li[contains(@class, 'dropdown-item') and contains(text(), '" + newStatus + "')]");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();
            Thread.sleep(500);
            
            // Add comment
            WebElement commentField = wait.until(ExpectedConditions.visibilityOfElementLocated(orderHistoryComment));
            commentField.clear();
            commentField.sendKeys(comment);
            
            // Click add history button
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(addHistoryButton));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addBtn);
            addBtn.click();
            
            // Wait for AJAX
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processRefund(String amount) {
        try {
            // Click refund button
            WebElement refundBtn = wait.until(ExpectedConditions.elementToBeClickable(refundButton));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", refundBtn);
            refundBtn.click();
            
            // Wait for refund dialog
            Thread.sleep(1000);
            
            // Enter refund amount
            WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(refundAmountInput));
            amountField.clear();
            amountField.sendKeys(amount);
            
            // Confirm refund
            WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(confirmRefundButton));
            confirmBtn.click();
            
            // Wait for processing
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("⚠ Refund feature may not be available or configured");
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successAlert));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
