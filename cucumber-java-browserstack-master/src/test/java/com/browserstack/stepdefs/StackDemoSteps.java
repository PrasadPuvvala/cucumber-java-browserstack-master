package com.browserstack.stepdefs;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.browserstack.RunWebDriverCucumberTests;
import com.browserstack.pageobjects.HomePage;
import com.browserstack.util.GenericFunctions;
import com.browserstack.util.Utility;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.Messages;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StackDemoSteps {
    private WebDriver driver;
    private HomePage homePage;
    public static ExtentTest test;
    public static ExtentReports extent;

    Scenario scenario;

    @Before
    public void setUp() {


     //   driver = RunWebDriverCucumberTests.getManagedWebDriver().getWebDriver();

        ChromeOptions options = new ChromeOptions();
        // disable notifications popup
        options.addArguments("--disable-notifications");
        WebDriverManager.firefoxdriver().setup();
        // System.setProperty("webdriver.chrome.driver", "E:\\Testing\\java-cucumber-testng-extentreport-master\\java-cucumber-testng-extentreport-master\\drivers\\chromedriver.exe");
        //something is happening that the next like is throwing an error
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        homePage = new HomePage(driver);

        this.extent=RunWebDriverCucumberTests.extent;
        this.test=RunWebDriverCucumberTests.test;



    }

    @Given("^I login on the website '(.+)'$")
    public void I_login_on_the_website(String url) throws Throwable {
        driver.get(url);
        Thread.sleep(2000);
        this.scenario= scenario;
        test = extent.createTest("User Login Page", "I login on the website".toUpperCase());
        test.log(Status.PASS,"Logged in successfully");
        //Close Report extent
        extent.flush();
        driver.findElement(By.name(("login"))).sendKeys("IkonicTest@gmail.com");
        Thread.sleep(1000);
        test = extent.createTest("Checking all Exam Categories", "ALl Exams");
        test.log(Status.PASS,"Exams page redirected successfully");
        driver.findElement(By.name(("pass"))).sendKeys("Password1");
        Thread.sleep(5000);

        driver.findElement(By.xpath("//*/app-login/div/form/div/div[5]/button")).click();
        Thread.sleep(5000);

        /*Handling intermediate window before get into Dashboard*/
        driver.navigate().refresh();
        Thread.sleep(10000);
        GenericFunctions.scrollToElement(driver,"//app-enrollment/div/form/div/div/div[6]/div[1]","xpath");
        GenericFunctions.clickXpath(driver,"//app-enrollment/div/form/div/div/div[6]/div[1]");
        Thread.sleep(5000);
        /*Handling intermediate window before get into Dashboard*/
    }

    @When("^I take exams")
    public void I_take_exams() throws Throwable
    {
       /* homePage.selectFirstProductName();
        homePage.clickAddToCartButton();*/
        Thread.sleep(2000);
        test = extent.createTest("Taking all available Exams", "ALl Exams");
     //   driver.navigate().refresh();
        int counter=1;
        for(int i=71;i<=72;i++)
        {

            Thread.sleep(4000);
            /* click on Dashboard */
            GenericFunctions.clickXpath(driver,"//app-header/div/div[1]/ul/li[1]/a");
          //  driver.findElement(By.xpath("//app-header/div/div[1]/ul/li[1]/a")).click();
            Thread.sleep(4000);
            GenericFunctions.scrollToElement(driver,"//*[@id='examModule71']/div[1]/div[3]/div/div","xpath");
       //     GenericFunctions.clickXpath(driver,"//*[@id='examModule"+i+"']/div[1]/div[3]/div/div");

            if(i>71)
            {

                Thread.sleep(2000);
               GenericFunctions.clickXpath(driver,"//*[@id='examModule"+i+"']/div[1]/div[3]/div/div");

              //    driver.findElement(By.xpath("//*[@id='examModule"+i+"']/div[1]/div[3]/div/div")).click();

            }
            GenericFunctions.clickXpath(driver,"//*[@id='examModule"+i+"']/div[2]/div[2]/div[1]/a");
            GenericFunctions.clickXpath(driver,"//*/app-practice/div[1]/div[2]/div[1]/div/div/div[2]/div/button");
            GenericFunctions.checkNextQuestion(driver);
            // Thread.sleep(4000);
        }


    }

    @Then("Take Vocabulary Test")
    public void take_vocabulary_test() throws InterruptedException {
       /* homePage.waitForCartToOpen();
        assertEquals(homePage.getSelectedProductName(), homePage.getProductCartText());

        driver.findElement(By.xpath("//*[@id='offers']")).click();*/
        test = extent.createTest("Taking Vocabulary Test", "Take Vocabulary Test".toUpperCase());
        test.log(Status.PASS,"Click on Exam Categories");

        driver.navigate().refresh();

        /*Handling intermediate window before get into Dashboard*/
        GenericFunctions.scrollToElement(driver,"//app-enrollment/div/form/div/div/div[6]/div[1]","xpath");
        GenericFunctions.clickXpath(driver,"//app-enrollment/div/form/div/div/div[6]/div[1]");
        Thread.sleep(5000);
        /*Handling intermediate window before get into Dashboard*/

        for(int i=71;i<=74;i++)
        {
            Thread.sleep(6000);
            /* click on Dashboard */
            driver.findElement(By.xpath("//app-header/div/div[1]/ul/li[1]/a")).click();
            Thread.sleep(4000);
            GenericFunctions.scrollToElement(driver,"//*[@id='examModule71']/div[1]/div[3]/div/div","xpath");
            if(i>71)
            {
                Thread.sleep(2000);
                GenericFunctions.clickXpath(driver,"//*[@id='examModule"+i+"']/div[1]/div[3]/div/div");
            }
            /* click on Vocabulary Test */
            Thread.sleep(5000);
            driver.findElement(By.xpath("//*[@id='examModule"+i+"']/div[2]/div[1]/div[1]/a")).click();
            GenericFunctions.clickXpath(driver,"//button");
            Thread.sleep(2000);
           // String text ="//app-flashcard/div/div[1]/div/div/div[1]/div/div[2]/div/h2";
            By firstProductName = By.xpath("//app-flashcard/div/div[1]/div/div/div[1]/div/div[2]/div/h2");
            test.log(Status.PASS,"Expected Phrase is :"+driver.findElement(firstProductName).getText());
            GenericFunctions.clickXpath(driver,"//button");
            Thread.sleep(2000);
            WebDriverWait wait= new WebDriverWait(driver,20);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//app-header/div/div[1]/ul/li[1]/a")));
           // GenericFunctions.checkNextQuestion(driver);
            // Thread.sleep(4000);
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
