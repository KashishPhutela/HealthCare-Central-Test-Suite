package capstoneSIT.pageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
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
	
	@FindBy(xpath = "//button[text()='Search']")
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
	
	@FindBy(xpath="//input[@placeholder='Customer Code']")
	WebElement custCodeSearchBox;
	
	
	
	
	public HashMap<String, String> searchByCustomerName() throws IOException {
		String formattedProjectName= null;
		String formattedProjectNumber = null;
		HashMap<String, String> searchResults = new HashMap<>();
		
		dbComponent db = new dbComponent();
		Properties prop = db.loadProperties();
		String custName = prop.getProperty("searchByCustomerName");
		customerName.sendKeys(custName);
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		searchButton.click();
		//waitForVisibility(modalDialog);
		for(int j=0;j<projectNames.size();j++) {
			String [] pNames = projectNames.get(j).getText().split(":");
			String [] pNums = projectNumbers.get(j).getText().split(":");
			formattedProjectName = pNames[1].trim();
			formattedProjectNumber = pNums[1].trim();
			searchResults.put(formattedProjectName, formattedProjectNumber);
			}
		return searchResults;
	}
	
	public HashMap<String, String> searchByCustomerCode() throws IOException{
		String formattedProjectName= null;
		String formattedProjectNumber = null;
		HashMap<String, String> searchResults = new HashMap<>();
		dbComponent db = new dbComponent();
		Properties prop = db.loadProperties();
		String custCode = prop.getProperty("searchByCustomerCode");
		custCodeSearchBox.sendKeys(custCode);
		searchButton.click();
		//waitForVisibility(modalDialog);
		for(int j=0;j<projectNames.size();j++) {
			String [] pNames = projectNames.get(j).getText().split(":");
			String [] pNums = projectNumbers.get(j).getText().split(":");
			formattedProjectName = pNames[1].trim();
			formattedProjectNumber = pNums[1].trim();
			searchResults.put(formattedProjectName, formattedProjectNumber);
			}
		return searchResults;
		
	}

	public HashMap<String, String> searchByProjectName() throws IOException {
		String formattedProjectNumber = null;
		String formattedProjectName= null;
		closeBtn.click();
		
		dbComponent db = new dbComponent();
		Properties prop = db.loadProperties();
		String projName = prop.getProperty("SearchByProjectName");
		projectName.sendKeys(projName);
		searchProject.click();
		HashMap<String, String> assignedProjects = new HashMap<>();
		//waitForVisibility(modalDialog);
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
