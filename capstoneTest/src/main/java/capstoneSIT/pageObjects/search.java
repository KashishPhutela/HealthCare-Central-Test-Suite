package capstoneSIT.pageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import capstoneSIT.abstractComponents.*;

public class search extends Waits {
	
	WebDriver driver;
	
	public search(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@placeholder='Customer Name']")
	WebElement customerName;
	
	@FindBy(xpath = "//button[@class='btn customBtn mx-3']")
	WebElement searchButton;
	
	@FindBy(xpath="//div[@class='modal-dialog']")
	WebElement modalDialog;
	
	@FindBy(xpath="//div[@class='d-flex resultCs']/p[1]")
	WebElement result;
	
	@FindBy(xpath="//p[@class='mr-3']")
	List<WebElement> projectNames;
	
	@FindBy(xpath="//p[@class='mr-3']/following-sibling::p")
	List<WebElement> projectNumbers;
	
	@FindBy(xpath="//button[@class='btn btn-secondary']")
	WebElement closeBtn;
	
	@FindBy(xpath="//input[@placeholder='Project Name']")
	WebElement projectName;
	
	@FindBy(xpath="/html[1]/body[1]/div[1]/div[4]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/form[1]/div[1]/div[1]/div[1]/*[name()='svg'][1]/*[name()='path'][1]")
	WebElement searchProject;
	
	
	public Properties loadProperties() throws IOException {
	Properties prop = new Properties();
	FileInputStream file = new FileInputStream(System.getProperty("user.dir") +"//constant.properties");
	prop.load(file);
	return prop;
	}
	
	
	public HashMap<String, String> searchByCustomerName() throws IOException {
		String formattedProjectName= null;
		String formattedProjectNumber = null;
		HashMap<String, String> searchResults = new HashMap<>();
		
		Properties prop = loadProperties();
		String custName = prop.getProperty("customerName");
		customerName.sendKeys(custName);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		searchButton.click();
		waitForVisibility(modalDialog);
		for(int j=0;j<projectNames.size();j++) {
			String [] pNames = projectNames.get(j).getText().split(":");
			String [] pNums = projectNumbers.get(j).getText().split(":");
			formattedProjectName = pNames[1].trim();
			formattedProjectNumber = pNums[1].trim();
			searchResults.put(formattedProjectName, formattedProjectNumber);
			}
		return searchResults;
	}

	public HashMap<String, String> searchByProjectName() {
		String formattedProjectNumber = null;
		String formattedProjectName= null;
		closeBtn.click();
		projectName.sendKeys("Global Work");
		searchProject.click();
		HashMap<String, String> assignedProjects = new HashMap<>();
		waitForVisibility(modalDialog);
		for(int j=0;j<projectNames.size();j++) {
			String [] pNames = projectNames.get(j).getText().split(":");
			String [] pNums = projectNumbers.get(j).getText().split(":");
			formattedProjectName = pNames[1].trim();
			formattedProjectNumber = pNums[1].trim();
			System.out.println("formattedProjectName :" +formattedProjectName);
			System.out.println("formattedProjectNumber: " +formattedProjectNumber);
			assignedProjects.put(formattedProjectName, formattedProjectNumber);
			}
		return assignedProjects;
	}
}
