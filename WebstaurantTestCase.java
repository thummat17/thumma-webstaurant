package Cucumber.Automation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

/**
 * Testcase for  WebstaurantStore
 */
public class WebstaurantTestCase 
{
    public static void main( String[] args )
    {
        WebDriver chromeDriver = new ChromeDriver();
        
        chromeDriver.manage().window().maximize();
        chromeDriver.get("https://www.webstaurantstore.com");
                
        // Search in search-box using "stainless work table"
        WebElement searchBoxElement = chromeDriver.findElement( By.cssSelector("input.typeahead") );
        searchBoxElement.click();
        searchBoxElement.sendKeys("stainless work table");
        
        // Click on Search button
        WebElement searchButtonElement = chromeDriver.findElement( By.cssSelector("button.bg-origin-box-border") );
        searchButtonElement.click();
        
        WebElement productListingElement = chromeDriver.findElement(By.id("product_listing"));
        List<WebElement> productElems = productListingElement.findElements(By.cssSelector("div.ag-item.gtm-product"));
        
        // Check all the product titles contains "Table" 
        for( WebElement productElement : productElems ) {
            Assert.assertTrue( productElement.getText().contains("Table" ) );
        }
        
        // Add the last of found items to cart
        WebElement lastProduct = productElems.get(productElems.size() - 1);
        WebElement lastProductAddToCart = lastProduct.findElement(By.cssSelector("div.add-to-cart input.btn.btn-cart"));
        lastProductAddToCart.click();
        
        // View cart
        WebElement viewCartContainer = chromeDriver.findElement(By.cssSelector("div.notification div.notification__action"));
        WebElement viewCart = viewCartContainer.findElement(By.cssSelector("a.btn.btn-primary"));
        viewCart.click();
        
        chromeDriver.manage().timeouts().pageLoadTimeout(10L, TimeUnit.SECONDS);
        
        // Verify cart after adding
        List<WebElement> checkCart = chromeDriver.findElements(By.cssSelector("div.cartItemWrapper.ag-item:not([style])"));
        Assert.assertEquals(1, checkCart.size());
        
        // Empty cart
        WebElement emptyCartContainer = chromeDriver.findElement(By.cssSelector("div.cartItems"));
        WebElement emptyCart = emptyCartContainer.findElement(By.linkText("Empty Cart"));
        emptyCart.click();
                
        // confirm empty cart
        WebElement confirmEmptyCart = chromeDriver.findElement(By.cssSelector("div.modal-content button.btn.btn-primary"));
        confirmEmptyCart.click();
        
        chromeDriver.manage().timeouts().pageLoadTimeout(10L, TimeUnit.SECONDS);
        
        // body click to unfocus any mouseover events
        WebElement bodyElem = chromeDriver.findElement(By.tagName("body"));
        bodyElem.click();
        
        chromeDriver.manage().timeouts().pageLoadTimeout(10L, TimeUnit.SECONDS);
        
        // Verify cart is empty after emptying
        WebElement checkCartIsEmpty = chromeDriver.findElement(By.cssSelector("div.container div.cartEmpty"));
        Assert.assertTrue(checkCartIsEmpty.isDisplayed());
        
        // quit chrome driver
        chromeDriver.quit();
    }
}
