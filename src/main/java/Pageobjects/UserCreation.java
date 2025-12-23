package Pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class UserCreation {
    WebDriver driver;

    public UserCreation(WebDriver driver) {
        this.driver = driver;
    }

    public boolean createUser(String id) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String parentWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/main/div/div[3]/div[2]/div/h3/span"))).click();
        //switch to users tab
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            if (!window.equalsIgnoreCase(driver.getWindowHandle())) {
                driver.switchTo().window(window);
                break;
            }
        }
        //adding details
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='btn header_pbtn']"))).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='nav-close el-link el-link--default']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='btn header_pbtn']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter User Name here']"))).sendKeys("user"+id);
        driver.findElement(By.xpath("//input[@placeholder='Enter EMAIL ID here']")).sendKeys("test"+id+"@abc.com");
        driver.findElement(By.xpath("/html/body/div[6]/div/div/section/div/div[2]/form/span[3]/div/div/div/div[1]/span/span/i")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[7]/div[1]/div[1]/ul/li[1]"))).click();
        driver.findElement(By.xpath("//*[@class='el-switch__label el-switch__label--left is-active']/following-sibling::span[1]")).click();

        //assigning permission
//        driver.findElement(By.xpath("/html/body/div[6]/div/div/section/div/div[2]/form/span[7]/div/div/div/div[1]/div/i")).click();
//        driver.findElement(By.xpath("/html/body/div[6]/div/div/section/div/div[2]/form/span[7]/div/div/div/div[2]/div/div[1]/div/input")).sendKeys("Dummydepot");
//        driver.findElement(By.xpath("//*[contains(text(),'Dummydepot')]")).click();
        driver.findElement(By.xpath("//span[contains(text(),'Create')]")).click();

        //validate success message
        boolean userStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Record Added!')]"))).isDisplayed();
        driver.close();
        driver.switchTo().window(parentWindow);
        return userStatus;
    }
}
