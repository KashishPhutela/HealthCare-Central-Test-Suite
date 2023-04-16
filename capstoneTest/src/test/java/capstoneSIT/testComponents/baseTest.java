package capstoneSIT.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import capstoneSIT.pageObjects.login;

public class baseTest {

	public WebDriver driver;
	ChromeOptions options;
	
	public login l;
	
	public WebDriver initializeDriver() throws IOException {
		options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
	//	options.addArguments("disable-blink-features=AutomationControlled");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		return driver;
	}
	
	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException{
		
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+ "//reports//" + testCaseName +".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir")+ "//reports//" + testCaseName +".png";
		
	}

	@BeforeMethod
	public login launchApplication() throws IOException {
		driver = initializeDriver();
		l = new login(driver);
		l.goTo();
		return l;
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	
}
