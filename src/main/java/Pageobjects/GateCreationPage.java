package Pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class GateCreationPage {
    WebDriver driver;

    public GateCreationPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean createGate(String id) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String parentWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/main/div/div[3]/div[3]/div/h3/span"))).click();
        //switch to depo tab
        Set<String> windows = driver.getWindowHandles();
        for(String window : windows){
            if(!window.equalsIgnoreCase(driver.getWindowHandle())){
                driver.switchTo().window(window);
                break;
            }
        }
        //verify the depot record is created and Edit
        String createdRefId = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='app']/main/div/div[3]/div[1]/div[2]/div/div[3]/div/span/span"))).getText();
        System.out.println(createdRefId);
        if (createdRefId.equalsIgnoreCase("ref"+id)) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='app']/main/div/div[3]/div[1]/div[2]/div/div[10]/span"))).click();
            //Gate creation
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Add Gate')]"))).click();
            driver.findElement(By.xpath("//input[@placeholder='Enter Gate Name']")).sendKeys("Gate"+id);
            driver.findElement(By.xpath("//div[@class='el-input']/input[@placeholder='Enter Reference Id']")).sendKeys("ref"+id);
            driver.findElement(By.xpath("//*[contains(text(),'USE DEPOT ADDRESS')]/parent::div/div/span[2]")).click();
            //selecting user
//            driver.findElement(By.xpath("//input[@placeholder='Search user to add']")).sendKeys("user903");
//            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'sample')]"))).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Create')]"))).click();

            //validate success msg
            boolean gateStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Gate has been added')]"))).isDisplayed();
            driver.close();
            driver.switchTo().window(parentWindow);
            return gateStatus;
        } else {
            System.out.println("depot record not displayed");
            driver.close();
            driver.switchTo().window(parentWindow);
            return false;
        }
    }
}