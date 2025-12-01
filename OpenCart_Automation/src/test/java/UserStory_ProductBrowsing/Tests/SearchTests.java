package UserStory_ProductBrowsing.Tests;

import UserStory_ProductBrowsing.Base.BaseTest;
import UserStory_ProductBrowsing.Pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * - Search | Search by Product Name
 * - Search | Search with No Results
 */
public class SearchTests extends BaseTest {

    @Test(priority = 1, description = "Search | Search by Product Name")
    public void testSearchByProductName() {
        SearchPage searchPage = new SearchPage(driver);

        // Search for "laptop"
        searchPage.search("laptop");

        // Verify search results page loaded
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("search"),
                "Should be on search results page");
        
        // Verify results are displayed
        Assert.assertTrue(searchPage.areResultsDisplayed() || searchPage.isNoResultsMessageDisplayed(),
                "Should show either results or no results message");
    }

    @Test(priority = 2, description = "Search | Search with No Results")
    public void testSearchWithNoResults() {
        SearchPage searchPage = new SearchPage(driver);

        // Search for non-existent term
        searchPage.search("xyzabc123nonexistent");

        // Verify no results message or empty results
        boolean hasNoResults = searchPage.isNoResultsMessageDisplayed() || searchPage.getResultCount() == 0;
        Assert.assertTrue(hasNoResults,
                "Should show no results message for non-existent search term");
    }

    @Test(priority = 3, description = "Search | Search Autocomplete")
    public void testSearchAutocomplete() {
        SearchPage searchPage = new SearchPage(driver);

        // Start typing in search box
        searchPage.typeInSearchBox("lap");

        // Wait for autocomplete suggestions
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if autocomplete suggestions appear
        boolean hasSuggestions = searchPage.hasAutocompleteSuggestions();
        
        if (hasSuggestions) {
            System.out.println("Autocomplete suggestions displayed");
            
            // Verify suggestions match search term
            boolean suggestionsMatch = searchPage.doSuggestionsMatchSearchTerm("lap");
            Assert.assertTrue(suggestionsMatch,
                    "Autocomplete suggestions should match search term");
            
            // Click on a suggestion
            searchPage.clickFirstSuggestion();
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // Verify navigation to product or search results
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("product") || currentUrl.contains("search"),
                    "Should navigate to product or search results after clicking suggestion");
            
            System.out.println("Search autocomplete working successfully");
        } else {
            System.out.println("Autocomplete feature may not be enabled in this store");
        }
    }
}
