import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class FileHandling {

    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeSuite
    public void setUp() {
        ExtentSparkReporter spark = new ExtentSparkReporter("HTML_Reports/FileHandling.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        System.setProperty("webdriver.chrome.driver", "C:/Users/gauravg/Documents/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        test = extent.createTest("File Handling Test").assignAuthor("Gaurav").assignCategory("Functional Test Case").assignDevice("Windows");
        test.info("Browser launched and maximized");
    }

    @Test
    public void fileHandlingTest() throws IOException {
        String uploadFileName = "TestFile.txt";
        String uploadFilePath = "C:\\Users\\gauravg\\eclipse-workspace\\FileAndDynamicEleHandling\\SampleFile\\" + uploadFileName;
        String downloadDir = "C:\\Users\\gauravg\\Downloads";
        String expectedDownloadFileName = "sample.txt";

        try {
            driver.get("https://letcode.in/file");
            test.info("Navigated to the file handling page");

            WebElement uploadElement = driver.findElement(By.cssSelector("input[type='file']"));
            uploadElement.sendKeys(uploadFilePath);
            test.info("Uploaded file: " + uploadFileName);

            String uploadedFileName = uploadElement.getAttribute("value");
            if (uploadedFileName != null && uploadedFileName.contains(uploadFileName)) {
                test.pass("File uploaded successfully!");
            } else {
                throw new Exception("File upload failed: Uploaded file not found in the input field.");
            }

            WebElement downloadButton = driver.findElement(By.xpath("//button//a[@download='" + expectedDownloadFileName + "']"));
            downloadButton.click();
            test.info("Clicked download button for file: " + expectedDownloadFileName);

            Thread.sleep(5000);  

            File downloadFile = Paths.get(downloadDir, expectedDownloadFileName).toFile();
            if (downloadFile.exists()) {
                test.pass("File downloaded successfully!");
            } else {
                throw new Exception("File download failed: File not found in the download directory.");
            }

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
