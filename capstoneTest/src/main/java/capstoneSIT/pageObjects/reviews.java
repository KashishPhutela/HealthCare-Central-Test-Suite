package capstoneSIT.pageObjects;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import capstoneSIT.abstractComponents.dbComponent;
import dev.failsafe.internal.util.Assert;



public class reviews {
	
	WebDriver driver;
	
	public reviews(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//div[@class='badge bg-primary']/preceding-sibling::div")
	List<WebElement> reviewTypes;
	
	@FindBy(xpath="//div[@class='badge bg-primary']")
	List<WebElement> reviewCount;
	
	public HashMap<String, String> reviewCount() {
		
		HashMap<String, String> reviewsByTypes = new HashMap<>();
		
		for(int i=0; i<reviewCount.size();i++) {
			reviewsByTypes.put(reviewTypes.get(i).getText(), reviewCount.get(i).getText());
		}
		return reviewsByTypes;
	}
	
}
