package UserStory_ShoppingCart_Checkout.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    private By guestCheckoutRadio = By.id("input-guest");
    private By continueButton = By.id("button-register");
    private By firstNameInput = By.id("input-firstname");
    private By lastNameInput = By.id("input-lastname");
    private By emailInput = By.id("input-email");
    private By address1Input = By.id("input-shipping-address-1");
    private By cityInput = By.id("input-shipping-city");
    private By postcodeInput = By.id("input-shipping-postcode");
    private By countrySelect = By.id("input-shipping-country");
    private By zoneSelect = By.id("input-shipping-zone");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectGuestCheckout() {
        try {
            WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(guestCheckoutRadio));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", radio);
        } catch (Exception e) {
            System.out.println("Guest checkout option not found or already selected");
        }
    }

    public void fillGuestDetails(String firstName, String lastName, String email, 
                                 String address, String city, String postcode) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(firstName);
            driver.findElement(lastNameInput).sendKeys(lastName);
            driver.findElement(emailInput).sendKeys(email);
            driver.findElement(address1Input).sendKeys(address);
            driver.findElement(cityInput).sendKeys(city);
            driver.findElement(postcodeInput).sendKeys(postcode);
        } catch (Exception e) {
            System.out.println("Error filling guest details: " + e.getMessage());
        }
    }

    public boolean isOnCheckoutPage() {
        return driver.getCurrentUrl().contains("checkout");
    }

    public void loginUser(String email, String password) {
        try {
            driver.findElement(By.id("input-email")).sendKeys(email);
            driver.findElement(By.id("input-password")).sendKeys(password);
            driver.findElement(By.cssSelector("button[type='submit']")).click();
        } catch (Exception e) {
            System.out.println("Login form not found: " + e.getMessage());
        }
    }

    public void applyCoupon(String couponCode) {
        try {
            WebElement couponInput = driver.findElement(By.id("input-coupon"));
            couponInput.clear();
            couponInput.sendKeys(couponCode);
            driver.findElement(By.id("button-coupon")).click();
        } catch (Exception e) {
            System.out.println("Coupon field not found: " + e.getMessage());
        }
    }

    public boolean arePaymentMethodsAvailable() {
        try {
            return driver.findElements(By.cssSelector("input[name='payment_method']")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectPaymentMethod() {
        try {
            WebElement paymentMethod = driver.findElement(By.cssSelector("input[name='payment_method']"));
            if (!paymentMethod.isSelected()) {
                paymentMethod.click();
            }
        } catch (Exception e) {
            System.out.println("Payment method selection failed: " + e.getMessage());
        }
    }

    public boolean isCreditCardPaymentAvailable() {
        try {
            return driver.findElements(By.cssSelector("input[value*='credit']")).size() > 0 ||
                   driver.findElements(By.cssSelector("input[value*='card']")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectCreditCardPayment() {
        try {
            WebElement creditCard = driver.findElement(By.cssSelector("input[value*='credit'], input[value*='card']"));
            creditCard.click();
        } catch (Exception e) {
            System.out.println("Credit card payment not found: " + e.getMessage());
        }
    }

    public void enterCreditCardDetails(String cardNumber, String expiry, String cvv) {
        try {
            driver.findElement(By.id("input-card-number")).sendKeys(cardNumber);
            driver.findElement(By.id("input-card-expiry")).sendKeys(expiry);
            driver.findElement(By.id("input-card-cvv")).sendKeys(cvv);
        } catch (Exception e) {
            System.out.println("Credit card fields not found: " + e.getMessage());
        }
    }

    public boolean isPayPalPaymentAvailable() {
        try {
            return driver.findElements(By.cssSelector("input[value*='paypal']")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectPayPalPayment() {
        try {
            WebElement paypal = driver.findElement(By.cssSelector("input[value*='paypal']"));
            paypal.click();
        } catch (Exception e) {
            System.out.println("PayPal payment not found: " + e.getMessage());
        }
    }

    public boolean areShippingMethodsAvailable() {
        try {
            return driver.findElements(By.cssSelector("input[name='shipping_method']")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectShippingMethod() {
        try {
            WebElement shippingMethod = driver.findElement(By.cssSelector("input[name='shipping_method']"));
            if (!shippingMethod.isSelected()) {
                shippingMethod.click();
            }
        } catch (Exception e) {
            System.out.println("Shipping method selection failed: " + e.getMessage());
        }
    }

    public boolean isFreeShippingAvailable() {
        try {
            return driver.findElements(By.cssSelector("input[value*='free']")).size() > 0 ||
                   driver.getPageSource().toLowerCase().contains("free shipping");
        } catch (Exception e) {
            return false;
        }
    }
}
