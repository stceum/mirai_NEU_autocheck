package me.stceum.neu_autocheck;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.util.Base64;
import java.time.Duration;
import java.io.UnsupportedEncodingException;

public class NEUAutoCheck {
    private String web_url = "https://ehall.neu.edu.cn/infoplus/form/JKXXSB/start";
    boolean show = false;
    private String info = "";

    public String check(String acc, String key_base64, String web_url, boolean show) {
        info += acc;
        info += " ";
        WebDriver driver;
        if (!show) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            driver = new FirefoxDriver(options);
        } else {
            driver = new FirefoxDriver();
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            String key = new String(Base64.getDecoder().decode(key_base64), "utf-8");
            driver.get(web_url);
//            System.out.println("Opening web...");
            // input the id and the passwd
            driver.findElement(By.xpath("/html/body/div[2]/div/form/div/div[1]/input[1]")).sendKeys(acc);
            driver.findElement(By.xpath("/html/body/div[2]/div/form/div/div[1]/input[2]")).sendKeys(key);
            driver.findElement(By.xpath("/html/body/div[2]/div/form/div/div[1]/span/input")).click();
            //click click click
            wait.until(presenceOfElementLocated(By.xpath("/html/body/div/main/div/form/div[1]/table/tbody/tr/td[1]/div/div/div/label[1]/span[1]/span"))).click();
            wait.until(presenceOfElementLocated(By.xpath("/html/body/div/main/div/form/div[3]/div[2]/table/tbody/tr[1]/td/div/div/div/label[1]/span[1]/span"))).click();
            wait.until(presenceOfElementLocated(By.xpath("/html/body/div/main/div/form/div[4]/div[2]/table/tbody/tr[1]/td/div/div/div/label[1]/span[1]/span"))).click();
            wait.until(presenceOfElementLocated(By.xpath("/html/body/div/main/div/form/div[6]/button/span"))).click();
            // temperature click click click
            for (int i = 1; i < 4; i++) {
                driver.get("https://e-report.neu.edu.cn/inspection/items/" + Integer.toString(i) + "/records/create");
                wait.until(presenceOfElementLocated(By.xpath("/html/body/div[2]/form/div[5]/input"))).click();
            }
            info += ("All done!");
//        } catch (UnsupportedEncodingException e) {
        } catch (Exception e) {
            info += ("Error :" + e.getMessage());
            return info;
        } finally {
            driver.quit();
        }
        return info;
    }

    public String check(String acc, String key_base64, boolean show) {
        return check(acc, key_base64, web_url, show);
    }

    public String check(String acc, String key_base64) {
        return check(acc, key_base64, web_url, show);
    }
}
