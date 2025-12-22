package Pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class ConsigneeCreationPage {
    WebDriver driver;

    public ConsigneeCreationPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean createConsignee(String id) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String parentWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/main/div/div[3]/div[5]/div/h3/span"))).click();
        //switch to depo tab
        Set<String> windows = driver.getWindowHandles();
        for(String window : windows){
            if(!window.equalsIgnoreCase(driver.getWindowHandle())){
                driver.switchTo().window(window);
                break;
            }
        }
        //adding details
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='btn header_pbtn']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter Consignee Name']"))).sendKeys("consignee"+id);
        driver.findElement(By.xpath("//input[@placeholder='Enter Reference Id']")).sendKeys("ref"+id);
        driver.findElement(By.xpath("//input[@placeholder='Enter State']")).sendKeys("test");
        driver.findElement(By.xpath("//input[@placeholder='Enter Region']")).sendKeys("test");
        driver.findElement(By.xpath("//input[@placeholder='Enter City']")).sendKeys("test");
        driver.findElement(By.xpath("//input[@placeholder='Enter Postal code']")).sendKeys("666777");
        driver.findElement(By.xpath("//input[@placeholder='Enter Representative Mobile']")).sendKeys("9876543210");

        driver.findElement(By.xpath("//span[contains(text(),'Save')]")).click();

        //validate success msg
        boolean consigneeStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Consignee Created Successfully')]"))).isDisplayed();
        driver.close();
        driver.switchTo().window(parentWindow);
        return consigneeStatus;
    }
}
