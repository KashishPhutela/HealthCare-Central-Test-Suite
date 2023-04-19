package capstoneSIT.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReviewerNotifications {
	
	WebDriver driver;
	
	public ReviewerNotifications(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//table/tbody/tr/td[9]/span[text()='View']")
	WebElement viewReport;
	
	@FindBy(xpath="//button[text()='Close']")
	WebElement closePopup;
	
	@FindBy(xpath="//div[text()='Review Notifcations']/parent::div/child::div[2]/child::div")
	WebElement viewAllReports;
	
	@FindBy(xpath="(//*[name()='rect'])[2]")
	WebElement approveIcon;
	
	@FindBy(xpath="//input[@placeholder='*Recommendations']")
	WebElement recommendationsTextBox;
	
	@FindBy(xpath="//textarea[@placeholder='*Comments']")
	WebElement commentsTextBox;
	
	@FindBy(xpath="//button[text()='Approve Review']")
	WebElement approveBtn;
	
	@FindBy(xpath="//div[@class='ProjectNumber']/child::input")
	WebElement projectNumberLbl;
	
	@FindBy(xpath="//table/tbody/tr/td[1]")
	WebElement reportNumber;
	
	public String reviewReport() throws InterruptedException {
		closePopup.click();
		viewAllReports.click();
		String reviewedReportNumber = reportNumber.getText();
		System.out.println("Reviewed Report Number is : " +reviewedReportNumber);
		viewReport.click();
		Thread.sleep(2000);
		approveIcon.click();
		recommendationsTextBox.sendKeys("No Recommendations");
		commentsTextBox.sendKeys("Approved");
		approveBtn.click();
		String projectNumber = projectNumberLbl.getAttribute("placeholder");
		return reviewedReportNumber;
		
		
	}

}
