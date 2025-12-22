package Pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class TransporterCreationPage {
    WebDriver driver;

    public TransporterCreationPage(WebDriver driver){
        this.driver = driver;
    }

    public boolean createTransporter(String id){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String parentWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/main/div/div[4]/div[1]/div/h3/span"))).click();
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
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter Transporter Name here']"))).sendKeys("transporter"+id);
        driver.findElement(By.xpath("//input[@placeholder='Enter Reference Id here']")).sendKeys("ref"+id);
        driver.findElement(By.xpath("//input[@placeholder='Enter GST Percentage here']")).sendKeys("22");
        driver.findElement(By.xpath("/html/body/div[6]/div/div/section/div/div/div[2]/form/span[8]/div/div/div/div/input")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[7]/div[1]/div[1]/ul/li[1]"))).click();

        driver.findElement(By.xpath("/html/body/div[6]/div/div/section/div/div/div[2]/form/span[10]/div/div/div/div[1]/input")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[8]/div[1]/div[1]/ul/li[1]"))).click();

        //add manager
        Actions actions = new Actions(driver);
        driver.findElement(By.xpath("/html/body/div[6]/div/div/section/div/div/div[2]/span[2]/div/div[1]/div[1]/div[2]/div/button[2]/span")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='text' and @placeholder='Enter']"))).isDisplayed();
        actions.moveToElement(driver.findElement(By.xpath("//input[@type='text' and @placeholder='Enter']"))).build().perform();
        driver.findElement(By.xpath("//input[@type='text' and @placeholder='Enter']")).sendKeys("manager"+id);
        driver.findElement(By.xpath("//input[@type='text' and @placeholder='Enter *']")).sendKeys("test"+id+"@abc.com");

        driver.findElement(By.xpath("//span[contains(text(),'Create')]")).click();

        //validate success msg
        boolean transporterStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Record Added!')]"))).isDisplayed();
        driver.close();
        driver.switchTo().window(parentWindow);
        return transporterStatus;
    }
}