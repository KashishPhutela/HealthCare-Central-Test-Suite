package capstoneSIT.mainTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.testng.annotations.Test;

import capstoneSIT.abstractComponents.dbComponent;
import capstoneSIT.pageObjects.ReviewerNotifications;
import capstoneSIT.pageObjects.reviews;
import capstoneSIT.pageObjects.search;
import capstoneSIT.testComponents.baseTest;


public class ReviewerMain extends baseTest {
	
	@Test
	public void testReviewer() throws SQLException, IOException, InterruptedException{
	dbComponent db = new dbComponent();
	Properties prop = db.loadProperties();
	String username = prop.getProperty("ReviewerUserId");
	String password = prop.getProperty("ReviewerPassword");
	
	l.loginApplication(username, password);
	reviews reviewObj = new reviews(driver);
	HashMap <String, String> pageReviews = reviewObj.reviewCount();
	
	HashMap <String, String> dbReviews = db.getReviewerReviewDetails();
	
	for (Map.Entry<String, String> rev : pageReviews.entrySet()) 
    {   
	 if(dbReviews.containsKey(rev.getKey())) {
		 String value = dbReviews.get(rev.getKey());
		 Assert.assertEquals(rev.getValue(), value);
		 System.out.println("Review Type " +rev.getKey()+ " database Count is " +value+ " and on Screen Count is " +rev.getValue());
	 }
	 }
	
	 search s = new search(driver);
	 HashMap<String, String> searchResults = s.searchByCustomerCode();
	 HashMap<String, String> dbSearchResults = db.getReviewerSearchResults();
	 
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
		HashMap<String, String> dbProjectresults = db.getSearchResultsByProjectName();
		
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
		ReviewerNotifications review = new ReviewerNotifications(driver);
		Thread.sleep(1000);
		String reviewedReportNumber = review.reviewReport();
		String reportStatus = db.getReportStatus(reviewedReportNumber);
		
		Assert.assertEquals("Report is successfully Approved","7",reportStatus);

	}
}
