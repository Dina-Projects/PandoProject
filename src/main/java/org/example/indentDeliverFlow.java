package org.example;

import Pageobjects.*;
import Utils.ExcelUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class indentDeliverFlow {
    String successMsg;
    String indentNum;
    String orderStatus;
    String id="9049";

    public void main() {
        try {
            LoginPage login = new LoginPage();
            if (login.loginMethod()) {
                System.out.println("Login successful");
                WebDriver driver = login.driver;
                DepotCreationPage depot = new DepotCreationPage(driver);
                UserCreation user = new UserCreation(driver);
                GateCreationPage gate = new GateCreationPage(driver);
                TransporterCreationPage transport = new TransporterCreationPage(driver);
                ConsigneeCreationPage consignee = new ConsigneeCreationPage(driver);
                VehicleCreationPage vehicle = new VehicleCreationPage(driver);
                IndentCreationPage indent = new IndentCreationPage(driver);

                if (depot.createDepot(id)) {
                    System.out.println("Depot created successfully");
                    if (user.createUser(id)) {
                        System.out.println("User created successfully");
                        if (gate.createGate(id)) {
                            System.out.println("Gate created successfully");
                            if (transport.createTransporter(id)) {
                                System.out.println("Transporter created successfully");
                                if (consignee.createConsignee(id)) {
                                    System.out.println("Consignee created successfully");
                                    if (vehicle.createVehicle(id)) {
                                        System.out.println("Vehicle created successfully");
                                        if (indent.createIndent(id)) {
                                            successMsg = driver.findElement(By.xpath("//*[@role='alert']/div/span")).getText();
                                            System.out.println(successMsg);
                                            indentNum = successMsg.split(" ")[4];

                                            //filter and search indent
                                            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='el-button el-button--default']"))).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='indentv2-outbound-sec-filter']/div[1]/div[1]/div/div/div[2]/div/div/span/span/i"))).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@placeholder='Delhi']"))).sendKeys("Dummydepot"+id);
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Dummydepot"+id+"')]"))).click();
                                            Actions actions = new Actions(driver);
                                            actions.moveToElement(driver.findElement(By.xpath("//*[contains(text(),'APPLY')]/parent::button"))).click().perform();

                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='tagblock_list' and @type='text']/parent::div"))).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@placeholder='Enter Indent ID']"))).sendKeys(indentNum);
                                            driver.findElement(By.xpath("//button[@class='btn section_pbtn' and contains(text(),'Search')]")).click();

                                            orderStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='order-status-button']"))).getText();
                                            System.out.println(orderStatus);

                                            //assign truck
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Assign Truck')]"))).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='assign-truck-vehicle-details']/div[2]/form/div[1]/div[2]/div/ul/li[1]/div/input"))).sendKeys("TN10");
                                            driver.findElement(By.xpath("//*[@id='assign-truck-vehicle-details']/div[2]/form/div[1]/div[2]/div/ul/li[2]/div/input")).sendKeys(id);
                                            driver.findElement(By.xpath("//*[@placeholder='99XXXXXXXX']")).sendKeys("9876543210");
                                            driver.findElement(By.xpath("//*[@placeholder='Enter Driver Name']")).sendKeys("driver"+id);
                                            driver.findElement(By.xpath("//*[@placeholder='Enter Vehicle Length']")).clear();
                                            driver.findElement(By.xpath("//*[@placeholder='Enter Vehicle Length']")).sendKeys("22");
                                            driver.findElement(By.xpath("//*[@placeholder='Enter Vehicle Width']")).clear();
                                            driver.findElement(By.xpath("//*[@placeholder='Enter Vehicle Width']")).sendKeys("33");
                                            driver.findElement(By.xpath("//*[@placeholder='Enter Vehicle height']")).clear();
                                            driver.findElement(By.xpath("//*[@placeholder='Enter Vehicle height']")).sendKeys("44");
                                            driver.findElement(By.xpath("//*[contains(text(),'Submit')]")).click();

                                            successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@role='alert']/div/span"))).getText();
                                            System.out.println(successMsg);
                                            driver.navigate().refresh();
                                            orderStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='order-status-button']"))).getText();
                                            System.out.println(orderStatus);

                                            //reported
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='truck-reported-text']"))).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Yes')]"))).click();
                                            successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@role='alert']/div/span"))).getText();
                                            System.out.println(successMsg);

                                            //Truckin
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'TRUCKIN')]"))).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='truckinpop']/child::h5/div/span"))).click();
                                            successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@role='alert']/div/span"))).getText();
                                            System.out.println(successMsg);
                                            driver.navigate().refresh();
                                            orderStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='order-status-button']"))).getText();
                                            System.out.println(orderStatus);

                                            //Material/LR number
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Document')]"))).click();
                                            //excel writing
                                            ExcelUtils.writeCsv(id);
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='upload-material']"))).sendKeys("C:/Users/durga/Downloads/indent-Material-PAND-816068-M-9-2025-12-20T13_56_19.677Z.xlsx");

                                            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000));
                                            actions.moveToElement(driver.findElement(By.xpath("//*[contains(text(),'Enter LR number')]"))).click().perform();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@placeholder='Enter LR number']"))).sendKeys("999888");
                                            driver.findElement(By.xpath("//*[@class='text-blue']")).click();
                                            successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@role='alert']/div/span"))).getText();
                                            System.out.println(successMsg);
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@role='navigation']/span[1]/span"))).click();
                                            orderStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='order-status-button']"))).getText();
                                            System.out.println(orderStatus);
                                            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

                                            //Truckout
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'TRUCKOUT')]"))).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@value='TRUCKOUT']/div[2]/div/div/h5/div/span"))).click();
                                            successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@role='alert']/div/span"))).getText();
                                            System.out.println(successMsg);
                                            driver.navigate().refresh();
                                            orderStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='order-status-button']"))).getText();
                                            System.out.println(orderStatus);
                                            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

                                            //Mark as Delivered date
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Mark as delivered')]"))).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@type='checkbox']/parent::span"))).click();
                                            driver.findElement(By.xpath("//*[contains(text(),'Reported At')]/parent::div/div/div/input")).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='el-button el-picker-panel__link-btn el-button--text el-button--mini']"))).click();
                                            driver.findElement(By.xpath("//*[contains(text(),'Unloading End')]/parent::div/div/div/input")).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='app']/following-sibling::div[2]/div[2]/button[2]"))).click();
                                            driver.findElement(By.xpath("//*[contains(text(),'POD DATE')]/parent::div/div/div/input")).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='app']/following-sibling::div[3]/div[2]/button[2]"))).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='mark-as-delivered-submit-button']"))).click();
                                            successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@role='alert']/div/span"))).getText();
                                            System.out.println(successMsg);
                                            driver.navigate().refresh();
                                            orderStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='order-status-button']"))).getText();
                                            System.out.println(orderStatus);
                                            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

                                            //delivered
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Document')]"))).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'APPROVE')]"))).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Approve')]"))).click();
                                            successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@role='alert']/div/span"))).getText();
                                            System.out.println(successMsg);

                                            //verification
                                            driver.findElement(By.xpath("//*[@class='el-button el-button--default']")).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='indentv2-outbound-sec-filter']/div[2]/div[1]/div/div/div[2]/span"))).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'All Statuses')]/parent::*"))).click();
                                            actions.moveToElement(driver.findElement(By.xpath("//*[contains(text(),'APPLY')]/parent::button"))).click().perform();
                                            orderStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='order-status-button']"))).getText();
                                            System.out.println(orderStatus);
                                            if (orderStatus.equalsIgnoreCase("DELIVERED")) {
                                                System.out.println("Order is delivered successfully");
                                            } else {
                                                System.out.println("Order is not delivered");
                                            }
                                            //logoff
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='app-side-menu']/span"))).click();
                                            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'LOG OUT')]"))).click();
                                            System.out.println("Logged out successfully");
                                            driver.quit();
                                        } else {
                                            System.out.println("Indent Not created");
                                        }
                                    } else {
                                        System.out.println("Vehicle creation failed");
                                    }
                                } else {
                                    System.out.println("Consignee creation failed");
                                }
                            } else {
                                System.out.println("Transporter created failed");
                            }
                        } else {
                            System.out.println("Gate creation failed");
                        }
                    } else {
                        System.out.println("user creation failed");
                    }
                } else {
                    System.out.println("Depot creation failed");
                }
            } else {
                System.out.println("Homepage not loaded");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
