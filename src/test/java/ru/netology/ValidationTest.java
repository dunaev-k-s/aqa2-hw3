package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationTest {
    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldValidateName(){
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("123");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+75116239174");
        form.findElement(By.cssSelector("[data-test-id=agreement] [class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button")).click();
        String actual = form.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }

    @Test
    void shouldValidatePhone(){
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Третьяков Прокоп");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("121");
        form.findElement(By.cssSelector("[data-test-id=agreement] [class=checkbox__box]")).click();
        form.findElement(By.cssSelector("button")).click();
        String actual = form.findElement(By.cssSelector(".input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual);
    }

    @Test
    void shouldValidateCheckBox(){
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Третьяков Прокоп");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79012345678");
        form.findElement(By.cssSelector("button")).click();
        List<WebElement> actual = form.findElements(By.cssSelector(".checkbox.input_invalid"));

        assertEquals(1,actual.size());
    }
}
