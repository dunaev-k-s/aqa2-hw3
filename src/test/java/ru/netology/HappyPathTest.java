package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HappyPathTest {
    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldCreatedOrder(){
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Третьяков Прокоп");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+75116239174");
        form.findElement(By.cssSelector("[data-test-id=agreement] [class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actual);
    }
}
