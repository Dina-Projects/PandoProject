package Pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class VehicleCreationPage {
    WebDriver driver;

    public VehicleCreationPage(WebDriver driver){
        this.driver = driver;
    }

    public boolean createVehicle(String id){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String parentWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/main/div/div[5]/div[2]/div/h3/span"))).click();
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
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter Vehicle Name here']"))).sendKeys("vehicle"+id);
        driver.findElement(By.xpath("//input[@placeholder='Enter CFT here']")).sendKeys("904");
        driver.findElement(By.xpath("//input[@placeholder='Enter KG here']")).sendKeys("20");
        driver.findElement(By.xpath("/html/body/div[6]/div/div/section/div/div[2]/form/span[5]/div/div/div/div/input")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[7]/div[1]/div[1]/ul/li[1]"))).click();

        driver.findElement(By.xpath("/html/body/div[6]/div/div/section/div/div[2]/form/span[6]/div[1]/div/div/div/input")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[8]/div[1]/div[1]/ul/li[3]"))).click();

        driver.findElement(By.xpath("//span[contains(text(),'Create')]")).click();

        //validate success msg
        boolean vehicleStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Record Added!')]"))).isDisplayed();
        driver.close();
        driver.switchTo().window(parentWindow);
        return vehicleStatus;
    }
}
