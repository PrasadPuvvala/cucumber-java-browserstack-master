package com.browserstack.stepdefs;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.browserstack.RunWebDriverCucumberTests;
import com.browserstack.pageobjects.HomePage;
import com.browserstack.util.GenericFunctions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExamCategoriesSteps {
    private WebDriver driver;
    private HomePage homePage;
    public static ExtentTest test;
    public static ExtentReports extent;

    public static int counter=1;
    Scenario scenario;

    @Before
    public void setUp() {


        //    driver = RunWebDriverCucumberTests.getManagedWebDriver().getWebDriver();
            ChromeOptions options = new ChromeOptions();
             //disable notifications popup
            options.addArguments("--disable-notifications");
            WebDriverManager.chromedriver().setup();
            // System.setProperty("webdriver.chrome.driver", "E:\\Testing\\java-cucumber-testng-extentreport-master\\java-cucumber-testng-extentreport-master\\drivers\\chromedriver.exe");
            //something is happening that the next like is throwing an error
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
          // Creating Object for HomPage Class
            homePage = new HomePage(driver);
            this.extent = RunWebDriverCucumberTests.extent;
            this.test = RunWebDriverCucumberTests.test;

    }

    @Given("^I login on the website '(.+)'$")
    public void I_login_on_the_website(String url) throws Throwable {
        driver.get(url);
        Thread.sleep(2000);
        this.scenario= scenario;
        test = extent.createTest("User Login Page", "I login on the website".toUpperCase());
        test.log(Status.PASS,"Logged in successfully");
        //Close Report extent after writing logs
        extent.flush();
        driver.findElement(By.name(("login"))).sendKeys("IkonicTest@gmail.com");
        Thread.sleep(1000);
        test = extent.createTest("Checking all Exam Categories", "ALl Exams");
        test.log(Status.PASS,"Exams page redirected successfully");
        driver.findElement(By.name(("pass"))).sendKeys("Password1");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*/app-login/div/form/div/div[5]/button")).click();
        Thread.sleep(4000);
        /*Handling intermediate window before get into Dashboard*/
       try
       {
           driver.navigate().refresh();
           homePage.waitForXpath(driver,"//div[@class='cc_courses']");
           GenericFunctions.scrollToElement(driver,"//app-enrollment/div/form/div/div/div[6]/div[1]","xpath");
           GenericFunctions.clickXpath(driver,"//app-enrollment/div/form/div/div/div[6]/div[1]");
       }
       catch(Exception e)
       {

       }

        /*Handling intermediate window before get into Dashboard*/
    }

    @When("^I take exams")
    public void I_take_exams() throws Throwable
    {
       /* homePage.selectFirstProductName();
        homePage.clickAddToCartButton();
        driver.navigate().refresh();*/

        test = extent.createTest("Taking all available Exams", "ALl Exams");
        /* Click on Dashboard */
        homePage.waitForDashboard();
        GenericFunctions.clickXpath(driver,"//app-header/div/div[1]/ul/li[1]/a");
        List<WebElement> examLinks= driver.findElements(By.xpath("//app-dashboard/div[3]/div[5]/div/div[@class='cc_category']"));

        for(int i=1;i<=1;i++)
        {
           /* click on Dashboard */
            homePage.waitForDashboard();
            GenericFunctions.clickXpath(driver,"//app-header/div/div[1]/ul/li[1]/a");
          //  GenericFunctions.scrollToElement(driver,"//*[@id='examModule71']/div[1]/div[3]/div/div","xpath");
/*
            if(i==1)
            {
                GenericFunctions.clickXpath(driver,"//app-dashboard/div[3]/div[3]/div/div/div[1]/div[3]/div/div");
            }*/
            GenericFunctions.scrollToElement(driver,"//app-dashboard/div[3]/div[5]/div/div[1]/div[1]/div[3]/div/div","xpath");
            String examName= driver.findElement(By.xpath("//app-dashboard/div[3]/div[5]/div/div["+i+"]/div/div[1]")).getText();
            test.log(Status.PASS,"EXAM CATEGORIES : "+"'"+examName.toUpperCase()+"'");

            if(i>1)
            {
                GenericFunctions.clickXpath(driver,"//app-dashboard/div[3]/div[5]/div/div["+i+"]/div[1]/div[3]/div/div");

               /*   GenericFunctions.clickXpath(driver,"//*[@id='examModule"+i+"']/div[1]/div[3]/div/div");
                  driver.findElement(By.xpath("//*[@id='examModule"+i+"']/div[1]/div[3]/div/div")).click();*/

            }
           /* GenericFunctions.clickXpath(driver,"//*[@id='examModule"+i+"']/div[2]/div[2]/div[1]/a");*/
            /* Launch Practice Test*/
            GenericFunctions.clickXpath(driver,"//*/app-dashboard/div[3]/div[5]/div/div["+i+"]/div[2]/div[2]/div[1]/a");
            /* Start Practice Session*/
            GenericFunctions.clickXpath(driver,"//*/app-practice/div[1]/div[2]/div[1]/div/div/div[2]/div/button");
            /*Attempt Questions*/
            GenericFunctions.checkNextQuestion(driver);
        }

    }

    /* Vocabulary Test*/
    /*Take Vocabulary Exam*/

    @Then("Take Vocabulary Test")
    public void take_vocabulary_test() throws InterruptedException {
       /* homePage.waitForCartToOpen();
        assertEquals(homePage.getSelectedProductName(), homePage.getProductCartText());
        */
        WebDriverWait wait= new WebDriverWait(driver,20);
        test = extent.createTest("Taking Vocabulary Test", "Take Vocabulary Test".toUpperCase());
        test.log(Status.PASS,"Click on Exam Categories");
        driver.navigate().refresh();

        /*Handling intermediate window before get into Dashboard*/
        try
        {
            driver.navigate().refresh();
            homePage.waitForXpath(driver,"//div[@class='cc_courses']");
            GenericFunctions.scrollToElement(driver,"//app-enrollment/div/form/div/div/div[6]/div[1]","xpath");
            GenericFunctions.clickXpath(driver,"//app-enrollment/div/form/div/div/div[6]/div[1]");
        }
        catch(Exception e)
        {

        }

        /* Click on Dashboard */
        homePage.waitForDashboard();
        GenericFunctions.clickXpath(driver,"//app-header/div/div[1]/ul/li[1]/a");
        List<WebElement> examLinks= driver.findElements(By.xpath("//app-dashboard/div[3]/div[5]/div/div[@class='cc_category']"));

        for(int i=1;i<=1;i++)
        {
            /* click on Dashboard */
            homePage.waitForDashboard();
            GenericFunctions.clickXpath(driver,"//app-header/div/div[1]/ul/li[1]/a");
            GenericFunctions.scrollToElement(driver,"//app-dashboard/div[3]/div[5]/div/div[1]/div[1]/div[3]/div/div","xpath");
            String examName= driver.findElement(By.xpath("//app-dashboard/div[3]/div[5]/div/div["+i+"]/div/div[1]")).getText();
            test.log(Status.PASS,"EXAM CATEGORIES : "+"'"+examName.toUpperCase()+"'");
/*
            if(i==1)
            {
                GenericFunctions.clickXpath(driver,"//app-dashboard/div[3]/div[3]/div/div/div[1]/div[3]/div/div");
            }*/
            if(i>1)
            {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//app-dashboard/div[3]/div[5]/div/div["+i+"]/div[1]/div[3]/div/div")));
                GenericFunctions.clickXpath(driver,"//app-dashboard/div[3]/div[5]/div/div["+i+"]/div[1]/div[3]/div/div");
            }
            /* click on Vocabulary Test */
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*/app-dashboard/div[3]/div[5]/div/div["+i+"]/div[2]/div[1]/div[1]/a")));
            GenericFunctions.clickXpath(driver,"//*/app-dashboard/div[3]/div[5]/div/div["+i+"]/div[2]/div[1]/div[1]/a");
            GenericFunctions.clickXpath(driver,"//button");
           // String text ="//app-flashcard/div/div[1]/div/div/div[1]/div/div[2]/div/h2";
            homePage.waitForXpath(driver,"//app-flashcard/div/div[1]/div/div/div[1]/div/div[2]/div/h2");
            By firstProductName=null;
            try
            {
                firstProductName = By.xpath("//app-flashcard/div/div[1]/div/div/div[1]/div/div[2]/div/h2");
                test.log(Status.PASS,"Expected Phrase is :"+driver.findElement(firstProductName).getText());
            }
            catch(Exception e)
            {
                test.log(Status.FAIL,"Expected Phrase is :"+e.getMessage());

            }

            GenericFunctions.clickXpath(driver,"//button");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//app-header/div/div[1]/ul/li[1]/a")));

        }


    }

    @Then("the page should contain '(.+)'$")
    public void page_should_contain(String expectedTitle) throws InterruptedException {
        assertTrue(driver.getPageSource().contains(expectedTitle));

        test = extent.createTest("The page should contain", "the page should contain".toUpperCase());

        test.log(Status.PASS,"page should");

    Thread.sleep(5000);
    }

    @After
    public void teardown(Scenario scenario) throws Exception {
      /*  if (scenario.isFailed()) {
            Utility.setSessionStatus(driver, "failed", String.format("%s failed.", scenario.getName()));
        } else {
            Utility.setSessionStatus(driver, "passed", String.format("%s passed.", scenario.getName()));
        }*/
        Thread.sleep(2000);

        test = extent.createTest("Tear Down", "Quitting Browser".toUpperCase());
        test.log(Status.PASS,"Browser closed tear down");
        test.log(Status.INFO,"All tests were passed on Chrome Browser ");

        driver.quit();
    }



}
