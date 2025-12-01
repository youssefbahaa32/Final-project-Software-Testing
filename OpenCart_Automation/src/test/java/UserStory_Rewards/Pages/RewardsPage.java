package UserStory_Rewards.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RewardsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By myAccountLink = By.linkText("My Account");
    private By rewardPointsLink = By.linkText("Reward Points");
    private By pointsBalance = By.cssSelector("p");
    private By transactionTable = By.cssSelector(".table-responsive table");
    private By transactionRows = By.cssSelector(".table-responsive table tbody tr");
    
    // Checkout - Redeem Points
    private By redeemPointsField = By.id("input-reward");
    private By applyRewardButton = By.id("button-reward");
    private By rewardSuccessMessage = By.cssSelector(".alert-success");
    
    public RewardsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void navigateToRewardPoints() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(myAccountLink)).click();
            wait.until(ExpectedConditions.elementToBeClickable(rewardPointsLink)).click();
        } catch (Exception e) {
            // If link not found, try direct URL
            String currentUrl = driver.getCurrentUrl();
            String baseUrl = currentUrl.substring(0, currentUrl.indexOf("/index.php") + 10);
            driver.get(baseUrl + "?route=account/reward");
        }
    }

    public boolean isRewardPointsPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pointsBalance));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getPointsBalance() {
        WebElement balanceElement = driver.findElement(pointsBalance);
        String balanceText = balanceElement.getText();
        // Extract points from text like "Your total number of reward points is: 500"
        if (balanceText.contains(":")) {
            return balanceText.split(":")[1].trim();
        }
        return "0";
    }

    public int getTransactionCount() {
        try {
            List<WebElement> transactions = driver.findElements(transactionRows);
            return transactions.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void redeemPoints(String points) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(redeemPointsField));
        driver.findElement(redeemPointsField).sendKeys(points);
        driver.findElement(applyRewardButton).click();
    }

    public boolean isRewardApplied() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(rewardSuccessMessage));
            return driver.findElement(rewardSuccessMessage).getText().contains("Success");
        } catch (Exception e) {
            return false;
        }
    }
}
