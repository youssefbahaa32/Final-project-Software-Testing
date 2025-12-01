package UserStory_ProductBrowsing.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    private By searchInput = By.name("search");
    private By searchButton = By.xpath("//button[@type='submit' and contains(@class, 'btn-light')]");
    private By productResults = By.xpath("//div[contains(@class, 'product-thumb')]");
    private By noResultsMessage = By.xpath("//p[contains(text(), 'no product') or contains(text(), 'No product')]");
    private By resultCount = By.xpath("//div[@id='content']//p");

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    public void enterSearchTerm(String searchTerm) {
        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        searchField.clear();
        searchField.sendKeys(searchTerm);
    }

    public void clickSearch() {
        WebElement searchBtn = wait.until(ExpectedConditions.presenceOfElementLocated(searchButton));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", searchBtn);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        try {
            searchBtn.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", searchBtn);
        }
    }

    public void search(String searchTerm) {
        enterSearchTerm(searchTerm);
        clickSearch();
    }

    public boolean areResultsDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(productResults));
            return driver.findElements(productResults).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNoResultsMessageDisplayed() {
        try {
            return driver.findElements(noResultsMessage).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public int getResultCount() {
        return driver.findElements(productResults).size();
    }

    public void typeInSearchBox(String text) {
        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        searchField.clear();
        searchField.sendKeys(text);
    }

    public boolean hasAutocompleteSuggestions() {
        try {
            // Wait a moment for autocomplete to appear
            Thread.sleep(1000);
            return driver.findElements(By.cssSelector(".autocomplete-suggestions, .dropdown-menu, [class*='autocomplete']")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean doSuggestionsMatchSearchTerm(String searchTerm) {
        try {
            String pageSource = driver.getPageSource().toLowerCase();
            return pageSource.contains(searchTerm.toLowerCase());
        } catch (Exception e) {
            return false;
        }
    }

    public void clickFirstSuggestion() {
        try {
            WebElement firstSuggestion = driver.findElement(By.cssSelector(".autocomplete-suggestions li:first-child, .dropdown-menu li:first-child"));
            firstSuggestion.click();
        } catch (Exception e) {
            System.out.println("Autocomplete suggestion not found: " + e.getMessage());
        }
    }
}
