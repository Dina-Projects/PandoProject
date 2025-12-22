package Pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class IndentCreationPage {
    WebDriver driver;

    public IndentCreationPage(WebDriver driver){
        this.driver = driver;
    }

    public boolean createIndent(String id){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='app-side-menu']/span"))).click();
        driver.findElement(By.xpath("//*[@class='sidemenu-slideout-menu-item'][4]")).click();

        //adding source details
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='btn header_pbtn']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='card-list list-plain']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@placeholder='Search by DEPOT Name, city or ID']"))).sendKeys("Dummydepot"+id);
        Actions action = new Actions(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000));
        action.moveToElement(driver.findElement(By.xpath("//*[contains(text(),'Dummydepot"+id+"')]/ancestor::td/preceding-sibling::td//child::input"))).click().perform();
        driver.findElement(By.xpath("//span[contains(text(),'Add')]")).click();

        //change to Open
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='el-input el-input--small el-input--suffix']/span/span/i"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'No Freight')]/ancestor::*/li[2]/span"))).click();

        //adding consignee details
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Add')]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Consignee') and @class='el-tabs__item is-top']"))).click();
        driver.findElement(By.xpath("//*[@placeholder='Search by Consignee Name, City or ID']")).sendKeys("consignee"+id);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='pane-2']/div/div[2]/div[3]/table/tbody/tr/td[1]/div/label/span/span"))).click();
        driver.findElement(By.xpath("//span[text()='Add']")).click();

        //adding delivery, vehicle, base fright details
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Delivery Type']/parent::*/div//i"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='FTL']"))).click();
        driver.findElement(By.xpath("//*[text()='Vehicle type']/parent::*/div//i")).click();
        driver.findElement(By.xpath("//*[contains(text(),'Vehicle type')]/parent::li/div/div/div/input[@placeholder='Select']")).sendKeys("vehicle"+id);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='vehicle"+id+"']"))).click();
        driver.findElement(By.xpath("//*[@placeholder='INR']")).clear();
        driver.findElement(By.xpath("//*[@placeholder='INR']")).sendKeys("22");
        driver.findElement(By.xpath("//div[contains(text(),'TRANSPORTER - Select a Transporter')]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@placeholder='Search']"))).sendKeys("Transporter"+id);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'transporter"+id+"')]/ancestor::label/span[1]"))).click();
        driver.findElement(By.xpath("//span[text()='Indent']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Confirmation')]/ancestor::div[@role='dialog']/div[3]/span/button[2]"))).click();

        //validate success message
        boolean indentStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@role='alert']/div/span"))).isDisplayed();
        return indentStatus;
    }
}
