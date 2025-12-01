package UserStory_Dashboard.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdminDashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By pageHeader = By.cssSelector(".page-header h1");
    private By dashboardWidgets = By.cssSelector(".row .mb-3");
    private By totalOrders = By.xpath("//div[contains(text(), 'Total Orders') or contains(text(), 'Orders')]");
    private By totalSales = By.xpath("//div[contains(text(), 'Total Sales') or contains(text(), 'Sales')]");
    private By totalCustomers = By.xpath("//div[contains(text(), 'Total Customers') or contains(text(), 'Customers')]");
    private By onlineCustomers = By.xpath("//div[contains(text(), 'People Online') or contains(text(), 'Online')]");
    private By recentOrdersTable = By.cssSelector("table.table");
    private By salesChart = By.cssSelector("canvas, .chart");

    public AdminDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean isDashboardDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getWidgetCount() {
        try {
            List<WebElement> widgets = driver.findElements(dashboardWidgets);
            return widgets.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean hasMetrics() {
        return driver.findElements(totalOrders).size() > 0 ||
               driver.findElements(totalSales).size() > 0 ||
               driver.findElements(totalCustomers).size() > 0;
    }

    public boolean hasRecentOrders() {
        return driver.findElements(recentOrdersTable).size() > 0;
    }

    public boolean hasSalesStatistics() {
        return driver.findElements(salesChart).size() > 0;
    }

    public boolean hasOnlineCustomers() {
        return driver.findElements(onlineCustomers).size() > 0;
    }
}
