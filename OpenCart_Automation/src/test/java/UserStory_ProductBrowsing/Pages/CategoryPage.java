package UserStory_ProductBrowsing.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CategoryPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    private By productItems = By.xpath("//div[contains(@class, 'product-thumb')]");
    private By productNames = By.xpath("//div[@class='description']//h4/a");
    private By productPrices = By.xpath("//span[@class='price-new']");
    private By addToCartButtons = By.xpath("//button[@title and contains(@formaction, 'cart')]");
    private By sortDropdown = By.id("input-sort");
    private By paginationLinks = By.xpath("//ul[@class='pagination']//a");
    private By nextPageLink = By.xpath("//ul[@class='pagination']//a[contains(text(), '>')]");

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean areProductsDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productItems));
            return driver.findElements(productItems).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public int getProductCount() {
        return driver.findElements(productItems).size();
    }

    public boolean hasAddToCartButtons() {
        return driver.findElements(addToCartButtons).size() > 0;
    }

    public void selectSort(String sortOption) {
        try {
            WebElement sortElement = wait.until(ExpectedConditions.presenceOfElementLocated(sortDropdown));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", sortElement);
            wait.until(ExpectedConditions.elementToBeClickable(sortDropdown));
            
            // Try using Select first (for native select elements)
            try {
                Select sort = new Select(sortElement);
                sort.selectByVisibleText(sortOption);
            } catch (Exception e) {
                // If Select fails, use click approach for custom dropdowns
                sortElement.click();
                Thread.sleep(500);
                By optionLocator = By.xpath("//option[contains(text(), '" + sortOption + "')]");
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                option.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<WebElement> getProductPrices() {
        return driver.findElements(productPrices);
    }

    public boolean isPaginationDisplayed() {
        return driver.findElements(paginationLinks).size() > 0;
    }

    public void clickNextPage() {
        WebElement nextPage = wait.until(ExpectedConditions.presenceOfElementLocated(nextPageLink));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", nextPage);
        wait.until(ExpectedConditions.elementToBeClickable(nextPageLink));
        try {
            nextPage.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", nextPage);
        }
    }
}
