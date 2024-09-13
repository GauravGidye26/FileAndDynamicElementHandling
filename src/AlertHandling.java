import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class AlertHandling {

    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;
    private WebDriverWait wait;

    @BeforeSuite
    public void setUp() {
        ExtentSparkReporter spark = new ExtentSparkReporter("HTML_Reports/AlertHandling.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        System.setProperty("webdriver.chrome.driver", "C:/Users/gauravg/Documents/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        test = extent.createTest("Alert Handling Test").assignAuthor("Gaurav").assignCategory("Functional Test Case").assignDevice("Windows");
        test.info("Browser launched and maximized");
    }

    @Test
    public void handleAlertTest() throws IOException {
        try {
            driver.get("https://letcode.in/waits");
            test.info("Navigated to the waits page");

            WebElement simpleAlertButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Simple Alert']")));
            simpleAlertButton.click();
            test.info("Clicked on Simple Alert button");

            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            
            test.info("Alert is present");

            Thread.sleep(2000);

            alert.accept();
            test.pass("Alert accepted successfully");

            test.addScreenCaptureFromPath(captureScreenshot(driver));

        } catch (Exception e) {
            test.fail("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
        test.info("Browser closed");

        extent.flush();
    }

    private String captureScreenshot(WebDriver driver) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFilePath = new File("src/../Images/Screenshot" + System.currentTimeMillis() + ".png");
        String absolutePathLocation = destFilePath.getAbsolutePath();
        FileUtils.copyFile(srcFile, destFilePath);
        return absolutePathLocation;
    }
}

