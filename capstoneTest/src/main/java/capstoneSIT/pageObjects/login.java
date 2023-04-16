package capstoneSIT.pageObjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class login  {
	
	WebDriver driver;
	
	public login(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(css="[name='userId")
	WebElement idElement;
	
	@FindBy(css="[name='password']")
	WebElement passwordElement;
	
	@FindBy(xpath="//button[@type='submit']")
	WebElement loginButton;
	
	public void goTo() {
		//driver.get("https://alpha-coderz.onrender.com/");
		driver.get("https://alphacoderzcapstone.onrender.com/");
	}
	
	public WebDriver loginApplication(String userId, String password) {
		idElement.sendKeys(userId);
		passwordElement.sendKeys(password);
		loginButton.click();
		return driver;
	}
	public String getUrl() {
		String currentUrl = driver.getCurrentUrl();
		return currentUrl;
	}
}
