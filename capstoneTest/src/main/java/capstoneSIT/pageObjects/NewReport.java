package capstoneSIT.pageObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import capstoneSIT.abstractComponents.ExcelData;
import capstoneSIT.abstractComponents.Waits;
import capstoneSIT.abstractComponents.dbComponent;

public class NewReport {

	WebDriver driver;
	String projectId ="";
	
	public NewReport(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//button[text()='New Report']")
	WebElement newReportBtn;
	
	@FindBy(id="reportname")
	WebElement reportName;
	
	@FindBy(id="dateIssued")
	WebElement dateIssued;
	
	@FindBy(id="tags")
	WebElement tags;
	
	@FindBy(id="receiving_customer")
	WebElement receivingCustomer;

	@FindBy(id="reviewer_id")
	WebElement reviewerId;
	
	@FindBy(id="projectNumber")
	WebElement projectNumber;
	
	@FindBy(id="productsCovered")
	WebElement productsCovered;
	
	@FindBy(id="models")
	WebElement model;
	
	@FindBy(xpath="//div[contains(text(),'Select')][1]")
	WebElement selectReportType;
	
	@FindBy(xpath="//div[contains(text(),'Select')][1]")
	WebElement selectCertificateType;
	
	@FindBy(xpath="//div[@class='dropdown__single-value css-1dimb5e-singleValue']")
	WebElement selectReport;
	
	@FindBy(xpath="//input[@name='report']")
	WebElement uploadReport;
	
	@FindBy(xpath="//input[@name='certificate']")
	WebElement uploadCert;
	
	@FindBy(xpath="//button[@type='submit']")
	WebElement submitButton;
	
	@FindBy(xpath="//div[text()='SUPPORTING DOCUMENTS']")
	WebElement selectOption1;
	
	@FindBy(xpath="//div[@class='dropdown__indicator dropdown__dropdown-indicator css-1xc3v61-indicatorContainer']")
	WebElement dropdownBtn;
	
	@FindBy(xpath="//p[text()='Certificate']/parent::label/div/div/div/div")
	WebElement dropdownbtncert;
	
	@FindBy(xpath="//div[text()='FINANCIAL']")
	WebElement selectOption2;
	
	@FindBy(xpath="//p[contains(text(),'Report created successfully with id')]")
	WebElement submissionAlert;
	
	@FindBy(xpath="//button[text()='Close']")
	WebElement closeBtn;
	
	
	public String createNewReport(String projectNo) throws IOException, InterruptedException {
		
		Waits wait = new Waits(driver);
		wait.waitForVisibility(newReportBtn);
		newReportBtn.click();
		ArrayList<String> data = ExcelData.getReportDataFromExcel();
		reportName.sendKeys(data.get(0));
		dateIssued.sendKeys(data.get(1));
		dateIssued.sendKeys(data.get(2));
		dateIssued.sendKeys(data.get(3));
		tags.sendKeys(data.get(4));
		receivingCustomer.sendKeys(data.get(5));
		reviewerId.sendKeys(data.get(6));
		projectNumber.sendKeys(projectNo);
		productsCovered.sendKeys(data.get(7));
		model.sendKeys(data.get(8));
		
		dropdownBtn.click();
		selectOption1.click();
		dropdownbtncert.click();
		selectOption2.click();

		uploadReport.sendKeys(System.getProperty("user.dir") + "\\attachments\\Book1.xlsx");
		Thread.sleep(1000);
		uploadCert.sendKeys(System.getProperty("user.dir") + "\\attachments\\Doc1.docx");
	
		submitButton.click();
		wait.waitForVisibility(submissionAlert);
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String[] reportSubmissionAlert= submissionAlert.getText().split("id");
		String reportId = reportSubmissionAlert[1].trim();
		closeBtn.click();
		return reportId;
		
	}
}
