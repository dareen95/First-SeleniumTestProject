package Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.LoginPage;
import Pages.ProductsPage;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;
    ProductsPage productsPage;

    @BeforeMethod
    public void setup() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        driver.get(" ");

        // ربط الـ Pages بالـ Driver
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
    }

    @Test
    public void testProductJourney() throws InterruptedException {
        // 1. تسجيل الدخول باستخدام مستخدم قياسي
        System.out.println("الخطوة 1: تسجيل الدخول...");
        loginPage.login("standard_user", "secret_sauce");
        Thread.sleep(1000);

        // 2. الانتقال لصفحة تفاصيل المنتج بالضغط على العنوان
        System.out.println("الخطوة 2: الدخول إلى صفحة تفاصيل المنتج...");
        productsPage.openProductDetails(); // تأكد من وجود هذه الميثود في ProductsPage
        Thread.sleep(2000);

        // 3. استخراج البيانات وطباعتها في الـ Console
        System.out.println("الخطوة 3: استخراج تفاصيل المنتج وطباعتها...");
        productsPage.printProductDetails();

        // 4. الضغط على زر إضافة للسلة من داخل صفحة المنتج
        System.out.println("الخطوة 4: إضافة المنتج إلى السلة...");
        productsPage.addProductToCart();

        // 5. التحقق من وجود المنتج في العربة (Badge)
        String count = productsPage.getCartCount();
        System.out.println("تأكيد: عدد العناصر في العربة هو: " + count);
        Assert.assertEquals(count, "1", "خطأ: العربة لا تحتوي على المنتج!");

        Thread.sleep(3000); // مهلة لمشاهدة النتيجة النهائية قبل الإغلاق
    }

    @AfterMethod
    public void tearDown() {
        // 6. إغلاق المتصفح وتنظيف الذاكرة
        if (driver != null) {
            System.out.println("الخطوة الأخيرة: إغلاق المتصفح بنجاح.");
            driver.quit();
        }
    }
}