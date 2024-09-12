import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AlertHandling {

    public static void main(String[] args) {
    	System.setProperty("webdriver.chrome.driver", "C:/Users/gauravg/Documents/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 

        try {
        	driver.get("https://letcode.in/waits");

        	WebElement simpleAlertButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Simple Alert']")));
            
//        	WebElement simpleAlertButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Simple Alert']")));

//        	WebElement simpleAlertButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Simple Alert']")));

            simpleAlertButton.click();

            wait.until(ExpectedConditions.alertIsPresent());

            Alert alert = driver.switchTo().alert();
            
            Thread.sleep(2000);

            alert.accept();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
