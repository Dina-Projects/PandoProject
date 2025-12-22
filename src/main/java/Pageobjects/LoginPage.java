package Pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    public WebDriver driver;

    public boolean loginMethod() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.get("https://qa.pandostaging.in");
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'User name')]/preceding-sibling::*"))).sendKeys("arunkumarannadurai+01@pando.in");
        driver.findElement(By.xpath("//*[contains(text(),'Password')]/preceding-sibling::*")).sendKeys("Zymr@1234");
        driver.findElement(By.xpath("//*[contains(text(),'Log in')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'QA Pando - Outbound')]")));

        //verify dashboard
        boolean homepage = driver.findElement(By.xpath("//*[contains(text(),'QA Pando - Outbound')]")).isDisplayed();
        return homepage;
    }
}

