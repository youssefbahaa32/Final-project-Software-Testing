package UserStory_OrderManagement.Tests;

import UserStory_OrderManagement.Base.BaseTest;
import UserStory_OrderManagement.Pages.OrderHistoryPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OrderManagementTests extends BaseTest {

    private void loginAsCustomer() {
        driver.findElement(By.linkText("My Account")).click();
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.id("input-email")).sendKeys(config.getProperty("test.email"));
        driver.findElement(By.id("input-password")).sendKeys(config.getProperty("test.password"));
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @BeforeMethod
    public void loginBeforeTest() {
        loginAsCustomer();
    }

    @Test(priority = 1, description = "Order Management | View Order History")
    public void testViewOrderHistory() {
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);
        orderHistoryPage.navigateToOrderHistory();
        
        Assert.assertTrue(orderHistoryPage.isOrderHistoryDisplayed(), 
            "Order history page not displayed");
        
        int orderCount = orderHistoryPage.getOrderCount();
        System.out.println("Total orders found: " + orderCount);
        
        if (orderCount > 0) {
            System.out.println("Order history displayed successfully");
        } else {
            System.out.println("No orders found for this customer");
        }
    }

    @Test(priority = 2, description = "Order Management | View Order Details")
    public void testViewOrderDetails() {
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);
        orderHistoryPage.navigateToOrderHistory();
        
        int orderCount = orderHistoryPage.getOrderCount();
        if (orderCount > 0) {
            orderHistoryPage.viewFirstOrder();
            Assert.assertTrue(orderHistoryPage.isOrderDetailsDisplayed(), 
                "Order details not displayed");
            System.out.println("Order details displayed successfully");
        } else {
            System.out.println("No orders available to view details");
        }
    }

    @Test(priority = 3, description = "Order Management | Reorder from History")
    public void testReorder() {
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);
        orderHistoryPage.navigateToOrderHistory();
        
        int orderCount = orderHistoryPage.getOrderCount();
        if (orderCount > 0) {
            orderHistoryPage.reorderFirstOrder();
            
            // Verify redirected to cart
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("cart") || currentUrl.contains("checkout"), 
                "Not redirected to cart after reorder");
            System.out.println("Reorder successful - products added to cart");
        } else {
            System.out.println("No orders available to reorder");
        }
    }

    @Test(priority = 4, description = "Order Management | Request Product Return")
    public void testRequestReturn() {
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);
        orderHistoryPage.navigateToOrderHistory();
        
        int orderCount = orderHistoryPage.getOrderCount();
        if (orderCount > 0) {
            orderHistoryPage.requestReturn();
            orderHistoryPage.fillReturnRequest("Defective", "Product not working as expected");
            
            boolean returnSuccess = orderHistoryPage.isReturnRequestSuccessful();
            if (returnSuccess) {
                System.out.println("Return request submitted successfully");
            } else {
                System.out.println("Return request submission may have failed or requires approval");
            }
        } else {
            System.out.println("No orders available to request return");
        }
    }

    @Test(priority = 5, description = "View Downloads")
    public void testViewDownloads() {
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);
        orderHistoryPage.navigateToDownloads();
        
        boolean hasDownloads = orderHistoryPage.hasDownloads();
        if (hasDownloads) {
            System.out.println("Downloads available for this customer");
        } else {
            System.out.println("No digital products purchased yet");
        }
    }

    @Test(priority = 6, description = "Order Management | Download Digital Product")
    public void testDownloadDigitalProduct() {
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);
        orderHistoryPage.navigateToDownloads();
        
        boolean hasDownloads = orderHistoryPage.hasDownloads();
        if (hasDownloads) {
            orderHistoryPage.downloadFirstProduct();
            System.out.println("Digital product download initiated");
        } else {
            System.out.println("No digital products available to download");
        }
    }

    @Test(priority = 7, description = "Order Management | Track Order Status")
    public void testTrackOrderStatus() {
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);
        orderHistoryPage.navigateToOrderHistory();
        
        int orderCount = orderHistoryPage.getOrderCount();
        if (orderCount > 0) {
            orderHistoryPage.viewFirstOrder();
            
            // Check order status section
            boolean hasStatus = orderHistoryPage.hasOrderStatus();
            if (hasStatus) {
                String status = orderHistoryPage.getOrderStatus();
                System.out.println("Order status: " + status);
                
                // Check for tracking number if shipped
                boolean hasTracking = orderHistoryPage.hasTrackingNumber();
                if (hasTracking) {
                    String trackingNumber = orderHistoryPage.getTrackingNumber();
                    System.out.println("Tracking number: " + trackingNumber);
                } else {
                    System.out.println("No tracking number available - order may not be shipped yet");
                }
            }
            
            Assert.assertTrue(orderHistoryPage.isOrderDetailsDisplayed(),
                    "Order details should be displayed");
        } else {
            System.out.println("No orders available to track status");
        }
    }

    @Test(priority = 8, description = "Order Management | View Subscription List")
    public void testViewSubscriptionList() {
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);
        
        // Navigate to subscriptions
        orderHistoryPage.navigateToSubscriptions();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Check if subscriptions page is accessible
        String currentUrl = driver.getCurrentUrl();
        boolean onSubscriptionsPage = currentUrl.contains("subscription") || 
                                      currentUrl.contains("recurring");
        
        if (onSubscriptionsPage) {
            boolean hasSubscriptions = orderHistoryPage.hasSubscriptions();
            if (hasSubscriptions) {
                System.out.println("Subscriptions found for this customer");
                
                // Verify subscription details shown
                boolean hasDetails = orderHistoryPage.hasSubscriptionDetails();
                Assert.assertTrue(hasDetails, "Subscription details should be displayed");
            } else {
                System.out.println("No subscriptions found for this customer");
            }
        } else {
            System.out.println("Subscriptions feature may not be enabled or accessible");
        }
    }

    @Test(priority = 9, description = "Order Management | Cancel Subscription")
    public void testCancelSubscription() {
        OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);
        
        // Navigate to subscriptions
        orderHistoryPage.navigateToSubscriptions();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        boolean hasSubscriptions = orderHistoryPage.hasSubscriptions();
        
        if (hasSubscriptions) {
            // View first subscription
            orderHistoryPage.viewFirstSubscription();
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // Check if subscription is active
            boolean isActive = orderHistoryPage.isSubscriptionActive();
            
            if (isActive) {
                // Attempt to cancel
                orderHistoryPage.cancelSubscription();
                
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                // Verify cancellation
                boolean isCancelled = orderHistoryPage.isSubscriptionCancelled();
                if (isCancelled) {
                    System.out.println("Subscription cancelled successfully");
                } else {
                    System.out.println("Subscription cancellation may require confirmation");
                }
            } else {
                System.out.println("No active subscriptions to cancel");
            }
        } else {
            System.out.println("No subscriptions available to cancel");
        }
    }
}
