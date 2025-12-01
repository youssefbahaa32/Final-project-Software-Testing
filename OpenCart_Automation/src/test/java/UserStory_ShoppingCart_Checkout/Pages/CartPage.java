package UserStory_ShoppingCart_Checkout.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    private By cartItems = By.xpath("//table[@class='table table-bordered']//tbody//tr");
    private By quantityInputs = By.xpath("//input[contains(@name, 'quantity')]");
    private By updateButtons = By.xpath("//button[@data-bs-toggle='tooltip' and contains(@title, 'Update')]");
    private By removeLinks = By.xpath("//a[@data-bs-toggle='tooltip' and contains(@title, 'Remove')]");
    private By cartTotal = By.xpath("//td[contains(text(), 'Total')]//following-sibling::td");
    private By emptyCartMessage = By.xpath("//p[contains(text(), 'shopping cart is empty') or contains(text(), 'no results')]");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    public int getCartItemCount() {
        try {
            return driver.findElements(cartItems).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void updateQuantity(int itemIndex, int newQuantity) {
        List<WebElement> inputs = driver.findElements(quantityInputs);
        if (itemIndex < inputs.size()) {
            WebElement input = inputs.get(itemIndex);
            input.clear();
            input.sendKeys(String.valueOf(newQuantity));
            
            // Click update button
            List<WebElement> updateBtns = driver.findElements(updateButtons);
            if (itemIndex < updateBtns.size()) {
                WebElement updateBtn = updateBtns.get(itemIndex);
                js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", updateBtn);
                wait.until(ExpectedConditions.elementToBeClickable(updateBtn));
                try {
                    updateBtn.click();
                } catch (Exception e) {
                    js.executeScript("arguments[0].click();", updateBtn);
                }
            }
        }
    }

    public void removeItem(int itemIndex) {
        List<WebElement> removeLinks = driver.findElements(this.removeLinks);
        if (itemIndex < removeLinks.size()) {
            WebElement removeLink = removeLinks.get(itemIndex);
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", removeLink);
            wait.until(ExpectedConditions.elementToBeClickable(removeLink));
            try {
                removeLink.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", removeLink);
            }
        }
    }

    public boolean isCartEmpty() {
        return driver.findElements(emptyCartMessage).size() > 0;
    }

    public String getCartTotal() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(cartTotal)).getText();
        } catch (Exception e) {
            return "";
        }
    }
}
