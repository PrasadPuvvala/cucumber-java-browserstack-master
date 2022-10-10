package com.browserstack;

import java.util.Iterator;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.browserstack.local.Local;
import com.browserstack.util.Utility;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.browserstack.webdriver.LazyInitWebDriverIterator;
import com.browserstack.webdriver.ManagedWebDriver;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

@CucumberOptions(
        features = {"src/test/resources/features/test"},
        glue = "com.browserstack.stepdefs",
        tags= "@smoke",
        plugin = {
                "pretty",
                "html:reports/tests/cucumber/html",
                "timeline:reports/tests/cucumber/timeline",
                "junit:reports/tests/cucumber/junit/cucumber.xml",
                "testng:reports/tests/cucumber/testng/cucumber.xml",
                "json:reports/tests/cucumber/json/cucumber.json",

        }
)
public class RunWebDriverCucumberTests {

    public static ExtentTest test;
    public static ExtentReports extent;
    private TestNGCucumberRunner testNGCucumberRunner;
    private Local local;
    private static final ThreadLocal<ManagedWebDriver> threadLocalWebDriver = new ThreadLocal<>();

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        System.out.println("Before CLass");

        ExtentHtmlReporter htmlReporter =  new ExtentHtmlReporter("E:/Testing/cucumber-java-browserstack-master/cucumber-java-browserstack-master/reports/extentReport.html");
        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        //configuration items to change the look and feel
        //add content, manage tests etc
        htmlReporter.config().setDocumentTitle("Simple Automation Report");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        test= extent.createTest("@Before Class Function-Website launched","@Before Class Executed");
        test.log(Status.PASS,"Test report starts logging result");

        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    private synchronized static void setThreadLocalWebDriver(ManagedWebDriver managedWebDriver) {
        threadLocalWebDriver.set(managedWebDriver);
    }

    public synchronized static ManagedWebDriver getManagedWebDriver() {
        return threadLocalWebDriver.get();
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
    public void feature(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper, ManagedWebDriver managedWebDriver) {
        if(Utility.isLocal(managedWebDriver) && local==null){
            local = new Local();
            Utility.startLocal(local, managedWebDriver);
        }
        managedWebDriver.setTestName(pickleWrapper.getPickle().getName());
        setThreadLocalWebDriver(managedWebDriver);
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider(name = "scenarios", parallel = true)
    public Iterator<Object[]> scenarios() {
        Object[][] scenarios = testNGCucumberRunner.provideScenarios();
        //Get Iterator of Object arrays consisting PickleWrapper, FeatureWrapper and ManagedWebDriver
        return new LazyInitWebDriverIterator(scenarios);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {

        System.out.println("After Class");
        test= extent.createTest("@After Class Function","Performing this at the last".toUpperCase());
        test.log(Status.PASS,"@After Class Executed");
        extent.flush();

        if(local != null){
            try {
                local.stop();
            } catch (Exception e) {
                throw new Error("Unable to stop BrowserStack Local.");
            }
        }
        if (testNGCucumberRunner == null) {
            return;
        }
        testNGCucumberRunner.finish();
    }

}
