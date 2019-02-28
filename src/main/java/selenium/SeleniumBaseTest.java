package selenium;

import org.junit.After;
import org.junit.Before;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import junitx.util.PropertyManager;

public class SeleniumBaseTest {
  protected Actions actions;
  protected WebDriver driver;
  protected WebElement element;
  
  @Before
  public void setUp() throws Exception {
    DesiredCapabilities dc = new DesiredCapabilities();
    dc.setBrowserName(PropertyManager.getProperty("BROWSER", "firefox"));

    String OS = PropertyManager.getProperty("PLATFORM", "ANY");
    if(OS.equals("MAC"))
      dc.setPlatform(Platform.MAC);
    else if(OS.equals("WINDOWS"))
      dc.setPlatform(Platform.WINDOWS);
    else if(OS.equals("LINUX"))
      dc.setPlatform(Platform.LINUX);
    else
      dc.setPlatform(Platform.ANY);
    
    driver = new RemoteWebDriver(new URL(PropertyManager.getProperty("GRID_URL", "http://localhost:4444/wd/hub")), dc);
    driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);  
  }
  
  @After
  public void tearDown() {
    driver.quit();
  }
}
