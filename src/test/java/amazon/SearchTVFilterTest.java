package amazon;

import selenium.SeleniumBaseTest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchTVFilterTest extends SeleniumBaseTest {
  @Test
  public void testProblemCase1() throws InterruptedException {
    driver.get("http://www.amazon.com");
    element = driver.findElement(By.id("twotabsearchtextbox"));
    element.sendKeys("smart tv");
    element.submit();
    
    // click link TCL filter
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.visibilityOfElementLocated(By.id("p_89/TCL")));
    String link = element.findElement(By.linkText("TCL")).getAttribute("href");
    driver.get(link);
    
    // wait for search results to load
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.s-result-list.sg-row")));

    // Validate all search items are TCL brand
    List<WebElement> list = element.findElements(By.cssSelector("span.a-size-medium.a-color-base.a-text-normal"));
    for(WebElement e : list)
      Assert.assertTrue(e.getText().contains("TCL"));
  }
}
