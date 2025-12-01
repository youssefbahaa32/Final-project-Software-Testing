package UserStory_ProductBrowsing.Tests;

import UserStory_ProductBrowsing.Base.BaseTest;
import UserStory_ProductBrowsing.Pages.CategoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * - Category | Browse Products by Category
 * - Category | Sort Products
 * - Category | Pagination
 */
public class CategoryTests extends BaseTest {

    @Test(priority = 1, description = "Category | Browse Products by Category")
    public void testBrowseProductsByCategory() {
        // Navigate to a category (e.g., Desktops)
        driver.get(baseUrl + "index.php?route=product/category&path=20");

        CategoryPage categoryPage = new CategoryPage(driver);

        // Verify products are displayed
        Assert.assertTrue(categoryPage.areProductsDisplayed(),
                "Products should be displayed in category");
        
        // Verify product count > 0
        Assert.assertTrue(categoryPage.getProductCount() > 0,
                "Category should have products");
        
        // Verify Add to Cart buttons exist
        Assert.assertTrue(categoryPage.hasAddToCartButtons(),
                "Products should have Add to Cart buttons");
    }

    @Test(priority = 2, description = "Category | Sort Products")
    public void testSortProducts() {
        // Navigate to category
        driver.get(baseUrl + "index.php?route=product/category&path=20");

        CategoryPage categoryPage = new CategoryPage(driver);

        // Select sort option
        categoryPage.selectSort("Price (Low > High)");

        // Verify URL changed (sort applied)
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("sort=") || currentUrl.contains("order="),
                "URL should contain sort parameters after sorting");
    }

    @Test(priority = 3, description = "Category | Pagination")
    public void testPagination() {
        // Navigate to category with many products
        driver.get(baseUrl + "index.php?route=product/category&path=20");

        CategoryPage categoryPage = new CategoryPage(driver);

        // Check if pagination exists
        if (categoryPage.isPaginationDisplayed()) {
            int firstPageCount = categoryPage.getProductCount();
            
            // Click next page
            categoryPage.clickNextPage();

            // Verify page changed
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("page="),
                    "URL should contain page parameter after pagination");
            
            // Verify products still displayed
            Assert.assertTrue(categoryPage.areProductsDisplayed(),
                    "Products should be displayed on page 2");
        } else {
            System.out.println("No pagination available - category has few products");
        }
    }
}
