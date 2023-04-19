package capstoneSIT.pageObjects;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import capstoneSIT.abstractComponents.ExcelData;
import capstoneSIT.abstractComponents.Waits;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

public class NewProject {

    WebDriver driver;
    public NewProject(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath="//button[@class='btn btn-secondary']")
    WebElement closeBtn;

    @FindBy(xpath="//div[@class='FileLogo mx-2']//button[@id='dropdownMenuButton1']//*[name()='svg']")
    WebElement newProjectBtn;

    @FindBy(xpath="//a[text()='Create a Project Folder']")
    WebElement createProjectOptn;
    
    @FindBy(xpath="//input[@name='lab_name']")
    WebElement labName;
    
    @FindBy(xpath="//input[@name='project_type']")
    WebElement projectType;
    
    @FindBy(xpath="//input[@name='transacting_customer']")
    WebElement transactingCust;
    
    @FindBy(xpath="//input[@name='receiving_customer']")
    WebElement receivingCust;
    
    @FindBy(xpath="//input[@name='project_name']")
    WebElement projectName;
    
    @FindBy(xpath="//textarea[@name='description']")
    WebElement description;
    
    @FindBy(xpath="//input[@name='purchase_order_number']")
    WebElement purchaseNumber;
    
    @FindBy(xpath="//textarea[@name='product_covered']")
    WebElement productsCovered;
    
    @FindBy(xpath="//textarea[@name='modals']")
    WebElement models;
    
    @FindBy(xpath="//input[@name='client_ready']")
    WebElement dateClientReady;
    
    @FindBy(xpath="//input[@name='completion']")
    WebElement datePromisedComplete;
    
    @FindBy(xpath="//input[@name='start_date']")
    WebElement dateProjectStarts;
    
    @FindBy(xpath="//input[@name='end_date']")
    WebElement dateProjectEnds;
    
    @FindBy(xpath="//button[text()='Create Project Folder']")
    WebElement createProjectBtn;
    
    @FindBy(xpath="//div[@class='custom-modal-content']")
    WebElement popupModal;
    
    @FindBy(xpath="//button[text()='Proceed to your project']")
    WebElement projectCreatedBtn;
    
public void createNewProject() throws IOException, InterruptedException{
	
	closeBtn.click();
    newProjectBtn.click();
    createProjectOptn.click();
    ArrayList<String> data = ExcelData.getProjectData();
    System.out.println(data);
    labName.sendKeys(data.get(0));
    projectType.sendKeys(data.get(1));
    transactingCust.sendKeys(data.get(2));
    receivingCust.sendKeys(data.get(3));
    projectName.sendKeys(data.get(4));
    description.sendKeys(data.get(5));
    purchaseNumber.sendKeys(data.get(6));
    productsCovered.sendKeys(data.get(7));
    models.sendKeys(data.get(8));
    
    dateClientReady.sendKeys(data.get(9));
    dateClientReady.sendKeys(Keys.TAB);
    dateClientReady.sendKeys(data.get(10));
    dateClientReady.sendKeys(data.get(11));
    
    datePromisedComplete.sendKeys(data.get(12));
    datePromisedComplete.sendKeys(Keys.TAB);
    datePromisedComplete.sendKeys(data.get(13)); 
    datePromisedComplete.sendKeys(data.get(14));
    
    dateProjectStarts.sendKeys(data.get(9));
    dateProjectStarts.sendKeys(Keys.TAB);
    dateProjectStarts.sendKeys(data.get(10));
    dateProjectStarts.sendKeys(data.get(11));
    
    dateProjectEnds.sendKeys(data.get(12));
    dateProjectEnds.sendKeys(Keys.TAB);
    dateProjectEnds.sendKeys(data.get(13));
    dateProjectEnds.sendKeys(data.get(14));
    
    createProjectBtn.click();
    Waits wait = new Waits(driver);
    Thread.sleep(2000);
   // wait.waitForElementToBeClickable(projectCreatedBtn);
    projectCreatedBtn.click();

}
}
