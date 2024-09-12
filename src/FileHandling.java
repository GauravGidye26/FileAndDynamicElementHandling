import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.nio.file.Paths;

public class FileHandling {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:/Users/gauravg/Documents/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://letcode.in/file");
        
        String uploadFileName = "TestFile.txt";
        String uploadFilePath = "C:\\Users\\gauravg\\eclipse-workspace\\FileAndDynamicEleHandling\\SampleFile\\"+uploadFileName;
        String downloadDir = "C:\\Users\\gauravg\\Downloads";
        String expectedDownloadFileName = "sample.txt";

        try {
            WebElement uploadElement = driver.findElement(By.cssSelector("input[type='file']"));
            uploadElement.sendKeys(uploadFilePath);

            String uploadedFileName = uploadElement.getAttribute("value");
//          System.out.println(uploadedFileName);
            if (uploadedFileName != null && uploadedFileName.contains(uploadFileName)) {
                System.out.println("File uploaded successfully!");
            } else {
                throw new Exception("File upload failed: Uploaded file not found in the input field.");
            }
        } catch (Exception e) {
            System.err.println("Exception during file upload: " + e.getMessage());
        }

        try {
            WebElement downloadButton = driver.findElement(By.xpath("//button//a[@download='"+expectedDownloadFileName+"']"));
            downloadButton.click();
            
            Thread.sleep(5000);  

            File downloadFile = Paths.get(downloadDir, expectedDownloadFileName).toFile();
            if (downloadFile.exists()) {
                System.out.println("File downloaded successfully!");
            } else {
                throw new Exception("File download failed: File not found in the download directory.");
            }
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception during file download: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
