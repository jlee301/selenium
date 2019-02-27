package google;

import selenium.SeleniumBaseTest;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPageSearchTest extends SeleniumBaseTest {
  @Test
  public void testProblemCase1() {
    driver.get("http://www.google.com");
    WebElement element = driver.findElement(By.name("q"));
    element.sendKeys("jlee301");
    element.submit();
    
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.presenceOfElementLocated(By.id("resultStats")));
  }
}
