package capstoneSIT.mainTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import capstoneSIT.abstractComponents.dbComponent;
import capstoneSIT.pageObjects.login;
import capstoneSIT.pageObjects.reviews;
import capstoneSIT.pageObjects.search;
import capstoneSIT.testComponents.baseTest;

public class App extends baseTest{
	
	@Test
	public void login() throws SQLException, IOException {
		
		l.loginApplication("kG838804IK","root");
		reviews reviewObj = new reviews(driver);
		HashMap <String, String> reviewsByTypes = reviewObj.reviewCount();
		dbComponent db = new dbComponent();
		HashMap <String, String> dbReviews = db.getReviewCount();
		
		
	 for (Map.Entry<String, String> rev : reviewsByTypes.entrySet()) 
         {   
		 if(dbReviews.containsKey(rev.getKey())) {
			 String value = dbReviews.get(rev.getKey());
			 Assert.assertEquals(rev.getValue(), value);
			 System.out.println("Reviews Type : " +rev.getKey()+ " DB Count: " +value+ " Screen Count: " +rev.getValue());
		 }
		 }
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
		
		 System.out.println(mp.getKey()+ " & " +mp.getValue()+ " & " +dbValue);
		 if(dbValue.equalsIgnoreCase(mp.getValue())) {
			 Assert.assertTrue("Correct Search Results",true);
		 }
		 else {
			 Assert.assertFalse("Incorrect Search Results",false);
		 }
		 
	 }
	 
	}
	
//	@Test
//	public void loginFailed() {
//		l.loginApplication("kG838804IK","root123");
//		String currentUrl = l.getUrl();
//		Assert.assertEquals("https://alphacoderz.cyclic.app/",currentUrl);
//		System.out.println("User is unable to login due to incorrect password");
//	}
}
