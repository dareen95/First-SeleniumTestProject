package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductsPage {
    WebDriver driver;
    WebDriverWait wait;

    // --- محددات العناصر (Locators) ---

    // من الصفحة الرئيسية
    private By backpackTitleLink = By.id("item_4_title_link");
    private By cartBadge = By.className("shopping_cart_badge");

    // من داخل صفحة تفاصيل المنتج (تختلف الأسماء قليلاً)
    private By detailProductName = By.className("inventory_details_name");
    private By detailProductDesc = By.className("inventory_details_desc");
    private By detailProductPrice = By.className("inventory_details_price");
    private By addToCartBtn = By.id("add-to-cart"); // الزر داخل صفحة التفاصيل غالباً له ID بسيط

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // 1. الضغط على اسم المنتج للانتقال لصفحته
    public void openProductDetails() {
        wait.until(ExpectedConditions.elementToBeClickable(backpackTitleLink)).click();
    }

    // 2. استخراج وطباعة التفاصيل من داخل صفحة المنتج
    public void printProductDetails() {
        // ننتظر ظهور العناصر في الصفحة الجديدة
        WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(detailProductName));
        String name = nameElement.getText();
        String desc = driver.findElement(detailProductDesc).getText();
        String price = driver.findElement(detailProductPrice).getText();

        System.out.println("--- تفاصيل المنتج من صفحة العرض ---");
        System.out.println("الاسم: " + name);
        System.out.println("الوصف: " + desc);
        System.out.println("السعر: " + price);
        System.out.println("---------------------------------");
    }

    // 3. الضغط على زر إضافة للسلة (Add to Cart)
    public void addProductToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
    }

    // 4. الحصول على عدد العناصر في السلة للتأكد
    public String getCartCount() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
        } catch (Exception e) {
            return "0";
        }
    }
}