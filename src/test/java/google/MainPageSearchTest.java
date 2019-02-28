package google;

import selenium.SeleniumBaseTest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPageSearchTest extends SeleniumBaseTest {
  @Test
  public void testProblemCase1() {
    driver.get("http://www.google.com");
    element = driver.findElement(By.name("q"));
    element.sendKeys("jlee301");
    element = driver.findElement(By.name("btnK"));
    element.submit();
    
    // Wait for search result to be appear
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
    
    // Check all search result contains something that mentions "jlee301"
    List<WebElement> list = driver.findElements(By.className("g"));
    for(int i = 0; i < list.size(); i++)
      Assert.assertTrue(list.get(i).getText().toLowerCase().contains("jlee301"));
  }
}
