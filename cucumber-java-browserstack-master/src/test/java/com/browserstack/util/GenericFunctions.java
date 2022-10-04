package com.browserstack.util;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.browserstack.stepdefs.StackDemoSteps;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/*import pages.Home;
import runner.TestRunner;
import steps.Hook;
import steps.MainPage;*/

import java.util.List;

public class GenericFunctions

{
    private WebDriver driver;

    public static void clickName(WebDriver driver,String name)
    {
        WebDriverWait wait= new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(name)));
        driver.findElement(By.name(name)).click();
    }

    public static void clickId(WebDriver driver,String id)
    {
        WebDriverWait wait= new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        driver.findElement(By.id(id)).click();
    }

    public static void clickXpath(WebDriver driver,String xpath) throws InterruptedException {
        WebDriverWait wait= new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        Thread.sleep(1000);
        driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(1000);
    }

    public static void clickText(WebDriver driver,String text)
    {
        WebDriverWait wait= new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(text)));
        driver.findElement(By.linkText(text)).click();
    }

    public static void scrollToElement(WebDriver driver, String value, String loc) throws InterruptedException {
        if(loc.equals("id")) {
            WebElement element = driver.findElement(By.id(value));
            Actions actions = new Actions(driver);
            actions.moveToElement(element);
            actions.perform();
        } else if (loc.equals("name")) {
            WebElement element = driver.findElement(By.name(value));
            Actions actions = new Actions(driver);
            actions.moveToElement(element);
            actions.perform();

        }
        else {

            try
            {
                WebElement element = driver.findElement(By.xpath(value));
                Actions actions = new Actions(driver);
                actions.moveToElement(element);
                actions.perform();
/*
                WebElement element = driver.findElement(By.xpath(value));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView();", element);
                Thread.sleep(2000);*/
              /*  Actions actions = new Actions(driver);
                actions.keyDown(Keys.CONTROL).sendKeys(Keys.PAGE_DOWN).perform();*/

            }

            catch(MoveTargetOutOfBoundsException e)
            {
                WebElement element = driver.findElement(By.xpath(value));
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView();", element);
                Thread.sleep(2000);

            }


        }

    }

    public static void checkNextQuestion(WebDriver driver)
    {
        try {
            Thread.sleep(4000);

            String NoQues= driver.findElement(By.xpath("//app-question-box/div/div[1]/div[2]")).getText();
            String[]array= NoQues.split("of",2);
            String[]array1=array[1].split(" ",3);
            int lengQues= Integer.parseInt(array1[1]);

            for(int i=1;i<=lengQues;i++)
            {


                List<WebElement> txt = driver.findElements(By.xpath("//*/app-question-box/div/div[2]/div[2]/div"));
                int len = txt.size();


                int counter = 1;
                for (WebElement ele : txt) {
                    if (counter == 1) {
                        String text = driver.findElement(By.xpath("//*/app-question-box/div/div[2]/div[2]/div[" + counter + "]/label")).getText();
                        driver.findElement(By.xpath("//*/app-question-box/div/div[2]/div[2]/div[" + counter + "]/label/input[" + counter + "]")).click();
                        Thread.sleep(2000);
                        driver.findElement(By.xpath("//*/div[4]/div[1]/div[1]/button[1]")).click();
                        Thread.sleep(2000);
                        String text1 = driver.findElement(By.xpath("//*/app-question-box/div/div[2]/div[2]/div[" + counter + "]/label/span[3]")).getText();
                        String question = driver.findElement(By.xpath("//app-question-box/div/div[2]/div[1]/p")).getText();
                        if (text1.contains("incorrect!")) {
                            StackDemoSteps.test.log(Status.FAIL, "Question is : " + "'" + question + "'");
                            StackDemoSteps.test.log(Status.FAIL, "Answer : " + "'" + text + "'" + " is : " + "'" + text1 + "'");
                        } else {
                            StackDemoSteps.test.log(Status.PASS, "Question is : " + "'" + question + "'");
                            StackDemoSteps.test.log(Status.PASS, "Answer : " + "'" + text + "'" + " is : " + "'" + text1 + "'");
                        }
                    }
                    try
                    {
                        if(i==lengQues)
                        {
                            if (driver.findElement(By.xpath("//*/div[4]/div[1]/div[1]/button[1]")).getText().contains("Next Question"))
                            {
                                driver.findElement(By.xpath("//*/div[4]/div[1]/div[1]/button[1]")).click();
                                Thread.sleep(4000);
                                //  checkNextQuestion(driver);
                            }
                            break;
                        }
                        else
                        {
                            if (driver.findElement(By.xpath("//*/div[4]/div[1]/div[1]/button[1]")).getText().contains("Next Question"))
                            {
                                driver.findElement(By.xpath("//*/div[4]/div[1]/div[1]/button[1]")).click();
                                Thread.sleep(4000);
                                //  checkNextQuestion(driver);
                            }
                        }

                    } catch (Exception e)
                    {
                        StackDemoSteps.test.log(Status.INFO, new Throwable(e.getMessage()));
                        return;
                        // break;
                    }
                    counter = counter + 1;
                }
            }// End loop for all questions
        } // Try
        catch (Exception e)
        {
            StackDemoSteps.test.log(Status.INFO,new Throwable(e.getMessage()));
            return;
        }


    }// checkNextQuestion- End function sample testing


} // End of Class sample test
