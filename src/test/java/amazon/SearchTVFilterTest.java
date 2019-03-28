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
  public void testProblemCase1() {
    driver.get("http://www.amazon.com");
    element = driver.findElement(By.id("twotabsearchtextbox"));
    element.sendKeys("smart tv");
    element.submit();
    
    // click TCL filter
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
    

    
    // also click Samsung filter
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.visibilityOfElementLocated(By.id("p_89/Samsung")));
    link = element.findElement(By.linkText("Samsung")).getAttribute("href");
    driver.get(link);

    // wait for search results to load
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.s-result-list.sg-row")));

    // Validate all search items are TCL or Samsung brand
    list = element.findElements(By.cssSelector("span.a-size-medium.a-color-base.a-text-normal"));
    int[] count = new int[2];
    for(WebElement e : list) {
      if(e.getText().contains("TCL"))
        count[0]++;
      else if(e.getText().contains("Samsung"))
        count[1]++;
      else
        Assert.fail("Result contained brand not TCL or Samsung.");
    }
    Assert.assertTrue(count[0] > 0);
    Assert.assertTrue(count[1] > 0);
    Assert.assertEquals(list.size(), count[0] + count[1]);
    
    
   
    // unclick TCL filter, only Samsung
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.visibilityOfElementLocated(By.id("p_89/TCL")));
    link = element.findElement(By.linkText("TCL")).getAttribute("href");
    driver.get(link);
    
    // wait for search results to load
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.s-result-list.sg-row")));

    // Validate all search items are Samsung brand
    list = element.findElements(By.cssSelector("span.a-size-medium.a-color-base.a-text-normal"));
    for(WebElement e : list)
      Assert.assertTrue(e.getText().contains("Samsung"));

    
    
    // see more filter
    element = driver.findElement(By.linkText("See more"));
    element.click();
    element = driver.findElement(By.partialLinkText("LG"));
    element.click();
    
    // wait for search results to load
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.s-result-list.sg-row")));

    // Validate all search items are Samsung or LG brand
    list = element.findElements(By.cssSelector("span.a-size-medium.a-color-base.a-text-normal"));
    count = new int[2];
    for(WebElement e : list) {
      if(e.getText().contains("LG"))
        count[0]++;
      else if(e.getText().contains("Samsung"))
        count[1]++;
      else
        Assert.fail("Result contained brand not LG or Samsung.");
    }
    Assert.assertTrue(count[0] > 0);
    Assert.assertTrue(count[1] > 0);
    Assert.assertEquals(list.size(), count[0] + count[1]);

  }
}
