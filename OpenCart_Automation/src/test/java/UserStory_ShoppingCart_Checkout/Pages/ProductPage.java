package UserStory_ShoppingCart_Checkout.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    private By addToCartButton = By.id("button-cart");
    private By quantityInput = By.id("input-quantity");
    private By successAlert = By.xpath("//div[contains(@class, 'alert-success')]");
    private By cartTotal = By.id("header-cart");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAddToCart() {
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(addToCartButton));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", button);
    }

    public void setQuantity(int quantity) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInput));
        input.clear();
        input.sendKeys(String.valueOf(quantity));
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successAlert));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void addProductToCart() {
        clickAddToCart();
    }

    public boolean hasProductOptions() {
        try {
            return driver.findElements(By.cssSelector("select[name^='option'], input[name^='option']")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectProductOption(String optionName, String optionValue) {
        try {
            // Try to find select dropdown
            WebElement optionSelect = driver.findElement(By.xpath("//label[contains(text(), '" + optionName + "')]/following-sibling::select"));
            optionSelect.sendKeys(optionValue);
        } catch (Exception e) {
            try {
                // Try to find radio button
                WebElement optionRadio = driver.findElement(By.xpath("//label[contains(text(), '" + optionValue + "')]/input"));
                optionRadio.click();
            } catch (Exception ex) {
                System.out.println("Option not found: " + optionName + " = " + optionValue);
            }
        }
    }
}
