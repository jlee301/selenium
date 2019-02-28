package youtube;

import selenium.SeleniumBaseTest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPageSearchTest extends SeleniumBaseTest {
  @Test
  public void testProblemCase1() {
    driver.get("http://www.youtube.com");
    
    // Wait for search bar to load
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.ytd-searchbox")));
    
    // Need to hover over search for the search box to be interactive
    actions = new Actions(driver);
    actions.moveToElement(element, 20, 0).perform();
    
    element = driver.findElement(By.cssSelector("input.ytd-searchbox"));
    element.clear();
    element.sendKeys("jimmy garoppolo");
    element = driver.findElement(By.id("search-icon-legacy"));
    element.click();
    
    // Wait filter button to load
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ytd-toggle-button-renderer.ytd-search-sub-menu-renderer")));    
    element.click();

    // Wait until filter menu expands and upload date option is visible
    element = (new WebDriverWait(driver, 10L))
        .until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Upload date")));
    element.click();
    
    // Wait until filter menu collapses and upload date option is no longer visible
    (new WebDriverWait(driver, 10L))
      .until(ExpectedConditions.invisibilityOf(element));
    
    // Verify the videos are sorted by upload date (newest first)
    List<WebElement> list = driver.findElements(By.id("dismissable"));
    int prev = -1;
    String unit = "";
    
    for(int i = 0; i < list.size(); i++) {
      element = list.get(i);
      if(element.isDisplayed()) {
        String temp = element.findElement(By.cssSelector("span:nth-child(2)")).getText();
        if(temp.isEmpty())
          continue;
        
        String[] data = temp.split(" ");
        int currVal = Integer.valueOf(data[0]);
        String currUnit = data[1];
        // 1 minute ago
        // data[0] = 1
        // data[1] = minute
        // data[2] = ago
        if(unit.equals(currUnit)) {
          if(prev > currVal)
            Assert.fail("Previous video: " + prev + " " + unit + " / Current video: " + currVal + " " + unit);
        }
        else {
          // minute != minutes != day != days != month != months != year != years
          unit = currUnit;
        }
        prev = currVal;
      }
    }
  }
}
