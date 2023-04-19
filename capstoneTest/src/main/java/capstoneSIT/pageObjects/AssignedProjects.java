package capstoneSIT.pageObjects;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AssignedProjects {

	WebDriver driver;
	
	public AssignedProjects(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(xpath="//a[text()='Financials']")
	WebElement financialsTab;
	
	@FindBy(xpath="//table/tbody/tr/td[1]")
	WebElement recordName;
	
	@FindBy(xpath="//table/tbody/tr/td[2]")
	WebElement recordType;
	
	@FindBy(xpath="//table/tbody/tr/td[2]")
	WebElement recordTypeSupportingDocs;
	
	@FindBy(xpath="//table/tbody/tr/td[3]")
	WebElement projectNumber;
	
	@FindBy(xpath="//table/tbody/tr/td[4]")
	WebElement projectName;
	
	@FindBy(xpath="//table/tbody/tr/td[5]")
	WebElement projectNameSD;
	
	@FindBy(xpath="//table/tbody/tr/td[6]")
	WebElement responsibility;
	
	@FindBy(xpath="//table/tbody/tr/td[7]")
	WebElement reviewer;
	
	@FindBy(xpath="//a[text()='Supporting Documents']")
	WebElement SupportingDocsTab;
	
	
	public ArrayList<String> validateFinancialDetails(String projectId) throws InterruptedException {

		financialsTab.click();
		Thread.sleep(1000);
		String attachment = recordName.getText();
		String reportType = recordType.getText();
		String projectNo = projectNumber.getText();
		String projName = projectName.getText();
		String reviewerId = responsibility.getText();
		ArrayList<String> financialDetails = new ArrayList<>();
		financialDetails.add(attachment);
		financialDetails.add(projName);
		financialDetails.add(reviewerId);
		
		return financialDetails;
	}
	
	
	public ArrayList<String> validateSupportingDocumentsDetails(String projectId) throws InterruptedException {
		
		SupportingDocsTab.click();
		Thread.sleep(1000);
		String attachmentSD = recordTypeSupportingDocs.getText();
		String projNameSD = projectNameSD.getText();
		String reviewerIdSD = reviewer.getText();
		ArrayList<String> supportingDocs = new ArrayList<>();
		supportingDocs.add(attachmentSD);
		supportingDocs.add(projNameSD);
		supportingDocs.add(reviewerIdSD);
		return supportingDocs;
	}
}
