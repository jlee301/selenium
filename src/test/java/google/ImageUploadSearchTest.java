package google;

import selenium.SeleniumBaseTest;

import org.junit.Assert;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ImageUploadSearchTest extends SeleniumBaseTest {
  @Test
  public void testProblemCase1() {
    driver.get("http://www.google.com");
    element = driver.findElement(By.linkText("Images"));
    element.click();
    
    // Wait for image icon to load
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.visibilityOfElementLocated(By.className("FiqGxd")));
    element.click();
    
    // Wait for upload an image link to load
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Upload an image")));
    element.click();
    
    element = driver.findElement(By.id("qbfile"));
    element.sendKeys(System.getProperty("user.dir") + "/resources/twitch-512.png");
    element.submit();
    
    // Wait for results to load
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.visibilityOfElementLocated(By.id("resultStats")));
    
    Assert.assertTrue(driver.getPageSource().contains("twitch icon"));
  }
}
