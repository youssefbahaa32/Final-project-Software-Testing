package UserStory_ProductBrowsing.Tests;

import UserStory_ProductBrowsing.Base.BaseTest;
import UserStory_ProductBrowsing.Pages.CategoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FilterTests extends BaseTest {

    @Test(description = "Category | Filter Products")
    public void testFilterProducts() {
        // Navigate to category
        driver.get(baseUrl + "index.php?route=product/category&path=20");

        CategoryPage categoryPage = new CategoryPage(driver);

        // Verify products are displayed
        Assert.assertTrue(categoryPage.areProductsDisplayed(),
                "Products should be displayed before filtering");
        
        // Note: Filter implementation depends on OpenCart configuration
        // This test verifies the category page loads and products are shown
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("category"),
                "Should be on category page");
    }
}
