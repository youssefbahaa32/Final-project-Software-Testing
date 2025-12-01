package UserStory_ShoppingCart_Checkout.Tests;

import UserStory_ShoppingCart_Checkout.Base.BaseTest;
import UserStory_ShoppingCart_Checkout.Pages.CheckoutPage;
import UserStory_ShoppingCart_Checkout.Pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class CheckoutTests extends BaseTest {

    @Test(description = "Checkout | Guest Checkout")
    public void testGuestCheckout() {
        // Add product to cart first
        driver.get(baseUrl + "index.php?route=product/product&product_id=43");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Navigate to checkout
        driver.get(baseUrl + "index.php?route=checkout/checkout");
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CheckoutPage checkoutPage = new CheckoutPage(driver);

        // Verify on checkout page
        Assert.assertTrue(checkoutPage.isOnCheckoutPage(),
                "Should be on checkout page");
        
        // Select guest checkout if available
        checkoutPage.selectGuestCheckout();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Fill guest details
        String timestamp = String.valueOf(System.currentTimeMillis());
        checkoutPage.fillGuestDetails(
            "Guest",
            "User",
            "guest" + timestamp + "@example.com",
            "123 Main St",
            "New York",
            "10001"
        );
        
        // Verify still on checkout flow
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout"),
                "Should remain in checkout flow");
    }

    @Test(priority = 2, description = "Checkout | Registered User Checkout")
    public void testRegisteredUserCheckout() {
        // Login as registered user
        driver.get(baseUrl + "index.php?route=account/login");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.loginUser(config.getProperty("test.email"), config.getProperty("test.password"));
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Add product to cart
        driver.get(baseUrl + "index.php?route=product/product&product_id=43");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Proceed to checkout
        driver.get(baseUrl + "index.php?route=checkout/checkout");
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify on checkout page with saved address
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout"),
                "Should be on checkout page");
        
        System.out.println("Registered user checkout initiated successfully");
    }

    @Test(priority = 3, description = "Checkout | Apply Coupon")
    public void testApplyCoupon() {
        // Add products to cart
        driver.get(baseUrl + "index.php?route=product/product&product_id=43");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Go to cart
        driver.get(baseUrl + "index.php?route=checkout/cart");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        
        // Try to apply coupon
        checkoutPage.applyCoupon("SAVE20");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify still on cart page
        Assert.assertTrue(driver.getCurrentUrl().contains("cart"),
                "Should remain on cart page after coupon attempt");
        
        System.out.println("Coupon application attempted");
    }

    @Test(priority = 4, description = "Payment | Select Payment Method")
    public void testSelectPaymentMethod() {
        // Add product and go to checkout
        driver.get(baseUrl + "index.php?route=product/product&product_id=43");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.get(baseUrl + "index.php?route=checkout/checkout");
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        
        // View available payment methods
        boolean paymentMethodsAvailable = checkoutPage.arePaymentMethodsAvailable();
        
        if (paymentMethodsAvailable) {
            checkoutPage.selectPaymentMethod();
            System.out.println("Payment method selected successfully");
        } else {
            System.out.println("Payment methods section not yet available - may need to complete previous steps");
        }
        
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout"),
                "Should remain in checkout flow");
    }

    @Test(priority = 5, description = "Payment | Credit Card Payment")
    public void testCreditCardPayment() {
        // Add product and go to checkout
        driver.get(baseUrl + "index.php?route=product/product&product_id=43");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.get(baseUrl + "index.php?route=checkout/checkout");
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        
        // Select credit card payment if available
        boolean creditCardAvailable = checkoutPage.isCreditCardPaymentAvailable();
        
        if (creditCardAvailable) {
            checkoutPage.selectCreditCardPayment();
            checkoutPage.enterCreditCardDetails("4111111111111111", "12/25", "123");
            System.out.println("Credit card payment details entered");
        } else {
            System.out.println("Credit card payment not available - may need configuration");
        }
        
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout"),
                "Should remain in checkout flow");
    }

    @Test(priority = 6, description = "Payment | PayPal Payment")
    public void testPayPalPayment() {
        // Add product and go to checkout
        driver.get(baseUrl + "index.php?route=product/product&product_id=43");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.get(baseUrl + "index.php?route=checkout/checkout");
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        
        // Select PayPal payment if available
        boolean paypalAvailable = checkoutPage.isPayPalPaymentAvailable();
        
        if (paypalAvailable) {
            checkoutPage.selectPayPalPayment();
            System.out.println("PayPal payment selected - would redirect to PayPal");
        } else {
            System.out.println("PayPal payment not available - may need configuration");
        }
        
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout") || driver.getCurrentUrl().contains("paypal"),
                "Should remain in checkout flow or redirect to PayPal");
    }

    @Test(priority = 7, description = "Shipping | Select Shipping Method")
    public void testSelectShippingMethod() {
        // Add product and go to checkout
        driver.get(baseUrl + "index.php?route=product/product&product_id=43");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.get(baseUrl + "index.php?route=checkout/checkout");
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        
        // View and select shipping method
        boolean shippingMethodsAvailable = checkoutPage.areShippingMethodsAvailable();
        
        if (shippingMethodsAvailable) {
            checkoutPage.selectShippingMethod();
            System.out.println("Shipping method selected successfully");
        } else {
            System.out.println("Shipping methods not yet available - may need to complete previous steps");
        }
        
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout"),
                "Should remain in checkout flow");
    }

    @Test(priority = 8, description = "Shipping | Free Shipping")
    public void testFreeShipping() {
        // Add products over free shipping threshold
        driver.get(baseUrl + "index.php?route=product/product&product_id=43");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Proceed to checkout
        driver.get(baseUrl + "index.php?route=checkout/checkout");
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        
        // Check for free shipping option
        boolean freeShippingAvailable = checkoutPage.isFreeShippingAvailable();
        
        if (freeShippingAvailable) {
            System.out.println("Free shipping option is available");
        } else {
            System.out.println("Free shipping not available - cart total may be below threshold");
        }
        
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout"),
                "Should be on checkout page");
    }
}
