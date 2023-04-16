package capstoneSIT.mainTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import capstoneSIT.abstractComponents.dbComponent;
import capstoneSIT.pageObjects.AssignedProjects;
import capstoneSIT.pageObjects.NewProject;
import capstoneSIT.pageObjects.NewReport;
import capstoneSIT.pageObjects.login;
import capstoneSIT.pageObjects.reviews;
import capstoneSIT.pageObjects.search;
import capstoneSIT.testComponents.baseTest;

public class App extends baseTest{
	
	@Test
	public void login() throws SQLException, IOException, InterruptedException  {
		
		dbComponent db = new dbComponent();
		Properties prop = db.loadProperties();
		String username = prop.getProperty("EngineerUserId");
		String password = prop.getProperty("EngineerPassword");
		
		l.loginApplication(username,password);
		reviews reviewObj = new reviews(driver);
		HashMap <String, String> reviewsByTypes = reviewObj.reviewCount();
		
		HashMap <String, String> dbReviews = db.getReviewCount();
		
		
	 for (Map.Entry<String, String> rev : reviewsByTypes.entrySet()) 
         {   
		 if(dbReviews.containsKey(rev.getKey())) {
			 String value = dbReviews.get(rev.getKey());
			 Assert.assertEquals(rev.getValue(), value);
			 System.out.println("Reviews Type : " +rev.getKey()+ " DB Count: " +value+ " Screen Count: " +rev.getValue());
		 }
		 }
	 
	 // validating search functionality 
	 
	 search s = new search(driver);
	 HashMap<String, String> searchResults = s.searchByCustomerName();
	 HashMap<String, String> dbSearchResults = db.getProjectDetails();
	 
	 for(Map.Entry<String, String> map: searchResults.entrySet()) {
		 String dbProjectNumber = dbSearchResults.get(map.getKey());
		 System.out.println(map.getKey()+ " & " +map.getValue()+ " & " +dbProjectNumber);
		 if(dbProjectNumber.equalsIgnoreCase(map.getValue())) {
			 Assert.assertTrue("Correct Search Results",true);
		 }
		 else {
			 Assert.assertFalse("Incorrect Search Results",false);
		 }
		 
	 }
	 
	HashMap<String, String> projectResults =  s.searchByProjectName();
	HashMap<String, String> dbProjectresults = db.getAssignedProjects();
	
	for(Map.Entry<String, String> mp: projectResults.entrySet()) {
		String dbValue = dbProjectresults.get(mp.getKey());
		
		 System.out.println("Project name on screen : " +mp.getKey());
		System.out.println("Project number on screen : " +mp.getValue());
		 if(dbValue.equalsIgnoreCase(mp.getValue())) {
			 Assert.assertTrue("Correct Search Results",true);
		 }
		 else {
			 Assert.assertFalse("Incorrect Search Results",false);
		 }
		 
	 }
	NewProject newProject = new NewProject(driver);
	newProject.createNewProject();
	
	String projectNum = db.getRecentlyCreatedProjectNumber();
	System.out.println("Project Number 1 : " +projectNum);
	NewReport report = new NewReport(driver);
	String reportId = report.createNewReport(projectNum);

	
	AssignedProjects assignedProj = new AssignedProjects(driver);
	ArrayList<String> financialDetails = assignedProj.validateFinancialDetails(projectNum);
	ArrayList<String> financialDetailsDB = db.getFinancialDetails(projectNum, reportId);
	
	if(financialDetails==null) {
		Assert.assertTrue("Financial Details not available.", false);
	}
	else if(financialDetails.equals(financialDetailsDB)) {
		Assert.assertTrue("Correct Financial Details.", true);
		System.out.println("Correct Financial Details");
	}
	else {
		Assert.assertTrue("Incorrect Financial Details.", false);
	}
	
	ArrayList<String> SupportingDocDetails = assignedProj.validateSupportingDocumentsDetails(projectNum);
	ArrayList<String> SupportingDocDetailsDB = db.getSupportingDocsDBDetails(projectNum, reportId);
	
	if(SupportingDocDetails==null) {
		Assert.assertTrue("Supporting Documents Details not available.", false);
	}
	else if(SupportingDocDetails.equals(SupportingDocDetailsDB)) {
		Assert.assertTrue("Correct Supporting Documents Details.", true);
		System.out.println("Correct Supporting Documents Details");
	}
	else {
		Assert.assertTrue("Incorrect Supporting Documents Details.", false);
	}
	
	}
	
	@Test
	public void loginFailed() {
		l.loginApplication("kG838804IK","root123");
		String currentUrl = l.getUrl();
		Assert.assertEquals("https://alphacoderzcapstone.onrender.com/",currentUrl);
		System.out.println("User is unable to login due to incorrect password");
	}
}
