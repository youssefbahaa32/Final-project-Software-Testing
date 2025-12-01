package UserStory_OrderManagement.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderHistoryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By[] myAccountLinkLocators = {
        By.linkText("My Account"),
        By.partialLinkText("My Account"),
        By.xpath("//a[contains(text(), 'My Account')]"),
        By.cssSelector("a[href*='account/account']")
    };
    
    private By[] orderHistoryLinkLocators = {
        By.linkText("Order History"),
        By.partialLinkText("Order History"),
        By.xpath("//a[contains(text(), 'Order History')]"),
        By.cssSelector("a[href*='account/order']")
    };
    
    private By ordersTable = By.cssSelector(".table-responsive table");
    private By orderRows = By.cssSelector(".table-responsive table tbody tr");
    private By viewOrderButton = By.cssSelector("a[data-bs-original-title='View']");
    private By reorderButton = By.cssSelector("a[data-bs-original-title='Reorder']");
    private By returnButton = By.cssSelector("a[data-bs-original-title='Return']");
    
    private By orderDetailsSection = By.cssSelector(".table-responsive");
    private By orderNumber = By.cssSelector("td:contains('Order ID')");
    private By orderStatus = By.cssSelector("td:contains('Status')");
    private By orderTotal = By.cssSelector("td:contains('Total')");
    
    private By[] returnsLinkLocators = {
        By.linkText("Returns"),
        By.partialLinkText("Returns"),
        By.linkText("Product Returns"),
        By.xpath("//a[contains(text(), 'Return')]"),
        By.cssSelector("a[href*='account/returns']")
    };
    private By returnProductDropdown = By.id("input-product");
    private By returnReasonRadio = By.name("return_reason_id");
    private By returnComments = By.id("input-comment");
    private By submitReturnButton = By.cssSelector("button[type='submit']");
    private By returnSuccessMessage = By.cssSelector(".alert-success");
    
    private By[] downloadsLinkLocators = {
        By.linkText("Downloads"),
        By.partialLinkText("Downloads"),
        By.linkText("Download"),
        By.xpath("//a[contains(text(), 'Download')]"),
        By.cssSelector("a[href*='account/download']")
    };
    private By downloadButton = By.cssSelector("a[data-bs-original-title='Download']");
    
    public OrderHistoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
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

    public void navigateToOrderHistory() {
        try {
            WebElement myAccountLink = findElementWithMultipleStrategies(myAccountLinkLocators);
            if (myAccountLink != null) {
                myAccountLink.click();
                Thread.sleep(1000);
            }
            
            WebElement orderHistoryLink = findElementWithMultipleStrategies(orderHistoryLinkLocators);
            if (orderHistoryLink != null) {
                orderHistoryLink.click();
            } else {
                String currentUrl = driver.getCurrentUrl();
                String baseUrl = currentUrl.split("index.php")[0];
                driver.get(baseUrl + "index.php?route=account/order");
            }
        } catch (Exception e) {
            String currentUrl = driver.getCurrentUrl();
            String baseUrl = currentUrl.split("index.php")[0];
            driver.get(baseUrl + "index.php?route=account/order");
        }
    }

    public boolean isOrderHistoryDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(ordersTable));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getOrderCount() {
        List<WebElement> orders = driver.findElements(orderRows);
        return orders.size();
    }

    public void viewFirstOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(viewOrderButton)).click();
    }

    public boolean isOrderDetailsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(orderDetailsSection));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void reorderFirstOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(reorderButton)).click();
    }

    public void requestReturn() {
        wait.until(ExpectedConditions.elementToBeClickable(returnButton)).click();
    }

    public void fillReturnRequest(String reason, String comments) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(returnReasonRadio));
        List<WebElement> reasonRadios = driver.findElements(returnReasonRadio);
        if (!reasonRadios.isEmpty()) {
            reasonRadios.get(0).click();
        }
        driver.findElement(returnComments).sendKeys(comments);
        driver.findElement(submitReturnButton).click();
    }

    public boolean isReturnRequestSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(returnSuccessMessage));
            return driver.findElement(returnSuccessMessage).getText().contains("Success");
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToDownloads() {
        try {
            WebElement myAccountLink = findElementWithMultipleStrategies(myAccountLinkLocators);
            if (myAccountLink != null) {
                myAccountLink.click();
                Thread.sleep(1000);
            }
            
            WebElement downloadsLink = findElementWithMultipleStrategies(downloadsLinkLocators);
            if (downloadsLink != null) {
                downloadsLink.click();
            } else {
                String currentUrl = driver.getCurrentUrl();
                String baseUrl = currentUrl.split("index.php")[0];
                driver.get(baseUrl + "index.php?route=account/download");
            }
        } catch (Exception e) {
            String currentUrl = driver.getCurrentUrl();
            String baseUrl = currentUrl.split("index.php")[0];
            driver.get(baseUrl + "index.php?route=account/download");
        }
    }

    public boolean hasDownloads() {
        try {
            List<WebElement> downloads = driver.findElements(downloadButton);
            return !downloads.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void downloadFirstProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(downloadButton)).click();
    }

    public boolean hasOrderStatus() {
        try {
            return driver.findElements(By.cssSelector(".order-status, .badge, [class*='status']")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public String getOrderStatus() {
        try {
            return driver.findElement(By.cssSelector(".order-status, .badge, [class*='status']")).getText();
        } catch (Exception e) {
            return "Status not found";
        }
    }

    public boolean hasTrackingNumber() {
        try {
            return driver.getPageSource().toLowerCase().contains("tracking") &&
                   driver.findElements(By.xpath("//*[contains(text(), 'TRACK') or contains(text(), 'Track')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTrackingNumber() {
        try {
            WebElement trackingElement = driver.findElement(By.xpath("//*[contains(@class, 'tracking') or contains(text(), 'Tracking')]"));
            return trackingElement.getText();
        } catch (Exception e) {
            return "No tracking number";
        }
    }

    public void navigateToSubscriptions() {
        try {
            driver.get(driver.getCurrentUrl().replace("account/account", "account/recurring"));
        } catch (Exception e) {
            System.out.println("Subscriptions navigation failed: " + e.getMessage());
        }
    }

    public boolean hasSubscriptions() {
        try {
            return driver.findElements(By.cssSelector("table tbody tr")).size() > 0 &&
                   !driver.getPageSource().contains("No results");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasSubscriptionDetails() {
        try {
            return driver.findElements(By.cssSelector(".subscription-details, .recurring-details")).size() > 0 ||
                   driver.getPageSource().contains("Subscription") ||
                   driver.getPageSource().contains("Recurring");
        } catch (Exception e) {
            return false;
        }
    }

    public void viewFirstSubscription() {
        try {
            WebElement viewButton = driver.findElement(By.cssSelector("table tbody tr:first-child a[href*='recurring']"));
            viewButton.click();
        } catch (Exception e) {
            System.out.println("View subscription button not found: " + e.getMessage());
        }
    }

    public boolean isSubscriptionActive() {
        try {
            String pageSource = driver.getPageSource().toLowerCase();
            return pageSource.contains("active") && !pageSource.contains("cancelled");
        } catch (Exception e) {
            return false;
        }
    }

    public void cancelSubscription() {
        try {
            WebElement cancelButton = driver.findElement(By.xpath("//button[contains(text(), 'Cancel') or contains(@value, 'cancel')]"));
            cancelButton.click();
            
            try {
                Thread.sleep(1000);
                driver.switchTo().alert().accept();
            } catch (Exception e) {
            }
        } catch (Exception e) {
            System.out.println("Cancel subscription button not found: " + e.getMessage());
        }
    }

    public boolean isSubscriptionCancelled() {
        try {
            Thread.sleep(2000);
            return driver.getPageSource().toLowerCase().contains("cancelled") ||
                   driver.getPageSource().toLowerCase().contains("canceled");
        } catch (Exception e) {
            return false;
        }
    }
}
