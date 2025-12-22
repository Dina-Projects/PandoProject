package Pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class DepotCreationPage {
    WebDriver driver;

    public DepotCreationPage(WebDriver driver){
        this.driver = driver;
    }

    public boolean createDepot(String id){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String parentWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='app-side-menu']/span"))).click();
        driver.findElement(By.xpath("//*[@class='sidemenu-slideout-menu-item'][2]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/main/div/div[3]/div[3]/div/h3/span"))).click();
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
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter Depot Name']"))).sendKeys(Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter Depot Name']"))).sendKeys("Dummydepot"+id);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='el-input']/input[@placeholder='Enter Reference Id']"))).sendKeys(Keys.ENTER);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.findElement(By.xpath("//div[@class='el-input']/input[@placeholder='Enter Reference Id']")).sendKeys("ref"+id);
        driver.findElement(By.xpath("//div[@class='el-input']/input[@placeholder='Enter Depot Short Code']")).sendKeys("depo"+id);
        driver.findElement(By.xpath("//div[@class='el-input']/input[@placeholder='Enter GSTIN']")).sendKeys("depo321");
        driver.findElement(By.xpath("//div[@class='el-input']/input[@placeholder='Enter Address']")).sendKeys("depo321");
        driver.findElement(By.xpath("//div[@class='el-input']/input[@placeholder='Enter City']")).sendKeys("depo321");
        driver.findElement(By.xpath("//div[@class='el-input']/input[@placeholder='Enter State']")).sendKeys("depo321");
        driver.findElement(By.xpath("//div[@class='el-input']/input[@placeholder='Enter Pin Code']")).sendKeys("654321");
        driver.findElement(By.xpath("//*[@id='app']/main/div/div[2]/div[2]/div/div/div/div[2]/div[2]/form/span[3]/div/div/div/div/span/span/i")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'South')]/parent::*"))).click();

        //assign user

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Create')]"))).click();

        //validate success message
        boolean depotStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Depot added successfully')]"))).isDisplayed();
        driver.close();
        driver.switchTo().window(parentWindow);
        return depotStatus;
    }
}
