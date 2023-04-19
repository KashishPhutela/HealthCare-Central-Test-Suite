package capstoneSIT.abstractComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.mysql.cj.util.StringUtils;

public class dbComponent {

	Connection conn;
	Statement st;
	ResultSet rs;
	
	public Statement initializeConnection() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://cap.mysql.database.azure.com:3306/capstone", "capstoneadmin","D@ily20242024");
		st = conn.createStatement();
		return st;
	}
	
	public Connection getConnection() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://cap.mysql.database.azure.com:3306/capstone", "capstoneadmin","D@ily20242024");
		return conn;
	}
	public HashMap<String, String> getReviewCount() throws SQLException {
		
		HashMap <String,String> dbReviews = new HashMap<>();
		Statement st = initializeConnection();
		rs =st.executeQuery("SELECT s.name,count(r.status_id) as status_id from report r LEFT JOIN status_type s on s.id = r.status_id group by r.status_id;");
		while(rs.next()) {
			dbReviews.put(rs.getString("name"),rs.getString("status_id"));
		}
		
		return dbReviews;
	}
	
	public HashMap<String, String> getProjectDetails() throws SQLException, IOException {
		HashMap<String, String> projectDetails = new HashMap<>();
		Properties prop = loadProperties();
		String createdBy = prop.getProperty("EngineerUserId");
		String transactingCust = prop.getProperty("transactingCustomer");
		conn=getConnection();
		PreparedStatement pst = conn.prepareStatement("Select project_name, Project_number from project_info where transacting_customer= ? and created_by =?");
		pst.setString(1, transactingCust);
		pst.setString(2, createdBy);
		rs = pst.executeQuery();
		while(rs.next()) {
			projectDetails.put(rs.getString("project_name"), rs.getString("project_number"));
		}
		return projectDetails;
	}

	public HashMap<String, String> getAssignedProjects() throws SQLException, IOException {
		HashMap<String, String> projectDetails = new HashMap<>();
		Properties prop = loadProperties();
		String createdBy = prop.getProperty("EngineerUserId");
		conn=getConnection();
		PreparedStatement pst = conn.prepareStatement("select project_name, Project_number from project_info where project_name like 'Global%' and created_by =?;");
		pst.setString(1, createdBy);
		rs = pst.executeQuery();
		while(rs.next()) {
			projectDetails.put(rs.getString("project_name"), rs.getString("project_number"));
		}
		return projectDetails;
	}
	
	public String getRecentlyCreatedProjectNumber() throws SQLException {
		String projectNum = null;
		
		Statement st = initializeConnection();
		rs = st.executeQuery("SELECT project_number FROM project_info where DATE(created_at) = curdate() order by created_at DESC limit 1;");
		while(rs.next()) {
			projectNum = rs.getString("project_number");
			
		}
		return projectNum;
	}
	

	
	public HashMap<String, String> getReviewerReviewDetails() throws SQLException, IOException {
		
		HashMap <String,String> reviewerReviews = new HashMap<>();
		Properties prop = loadProperties();
		String reviewerId= prop.getProperty("ReviewerUserId");
		conn=getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT s.name, count(r.status_id) as status_id from report r LEFT JOIN status_type s on s.id = r.status_id where reviewer_id =? group by r.status_id;");
		pst.setString(1, reviewerId);
		rs=pst.executeQuery();
		while(rs.next()) {
			reviewerReviews.put(rs.getString("name"), rs.getString("status_id"));
		}
		return reviewerReviews;
	}
	
	
	public HashMap<String, String> getReviewerSearchResults() throws SQLException, IOException {
		HashMap <String,String> dbSearchResults = new HashMap<>();
		Properties prop = loadProperties();
		String reviewerId= prop.getProperty("ReviewerUserId");
		String transactingCust = prop.getProperty("searchByCustomerCode");
		conn=getConnection();
		PreparedStatement pst = conn.prepareStatement("Select DISTINCT(p.project_number),p.project_name from project_info p LEFT JOIN report r ON p.project_number = r.project_number where r.reviewer_id =? and transacting_customer = ?;");
		pst.setString(1, reviewerId);
		pst.setString(2, transactingCust);
		rs = pst.executeQuery();
		while(rs.next()) {
			dbSearchResults.put(rs.getString("project_name"), rs.getString("project_number"));
		}
	return dbSearchResults;
	}
	
	public HashMap<String, String> getSearchResultsByProjectName() throws SQLException, IOException {
		HashMap<String, String> projectDetails = new HashMap<>();
		Properties prop = loadProperties();
		String projName = prop.getProperty("SearchByProjectName");
		conn=getConnection();
		PreparedStatement pst = conn.prepareStatement("Select p.project_name, p.project_number from project_info p LEFT JOIN report r ON p.project_number = r.project_number where r.reviewer_id ='XF691905O4' and project_name like ?;");
		pst.setString(1, projName + '%');
		rs = pst.executeQuery();
			while(rs.next()) {
			projectDetails.put(rs.getString("project_name"), rs.getString("project_number"));
		}
		return projectDetails;
	}
	
	public String getReportStatus(String reviewedReportNumber) throws IOException, SQLException {
		String reportStatus ="";
		Properties prop = loadProperties();
		String reviewerId = prop.getProperty("ReviewerUserId");
		conn=getConnection();
		PreparedStatement pst = conn.prepareStatement("select status_id from report where report_number = ? and reviewer_id= ?;");
		pst.setString(1, reviewedReportNumber);
		pst.setString(2, reviewerId);
		rs=pst.executeQuery();
		while(rs.next()) {
			reportStatus = rs.getString("status_id");
		}
		
		return reportStatus;
		
	}
	
	
	public ArrayList<String> getFinancialDetails(String projectNumber, String reportId) throws SQLException {
		ArrayList<String> projectDetails = new ArrayList<>();
		conn=getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT original_file_name FROM capstone.report_documents where report_id= ? and sub_type=2;");
		pst.setString(1, reportId);
		rs=pst.executeQuery();
		while(rs.next()) {
			projectDetails.add(rs.getString("original_file_name"));
		}
		PreparedStatement pst1 = conn.prepareStatement("select project_name, receiving_customer from project_info where project_number = ?;");
		pst1.setString(1, projectNumber);
		ResultSet rs1=pst1.executeQuery();
		
		while(rs1.next()) {
			projectDetails.add(rs1.getString("project_name"));
			projectDetails.add(rs1.getString("receiving_customer"));
		}
		return projectDetails;
	}
	
	public ArrayList<String> getSupportingDocsDBDetails(String projectNumber, String reportId) throws SQLException {
		ArrayList<String> sDDetails = new ArrayList<>();
		conn=getConnection();
		PreparedStatement pst = conn.prepareStatement("SELECT original_file_name FROM capstone.report_documents where report_id= ? and sub_type=3;");
		pst.setString(1, reportId);
		rs=pst.executeQuery();
		while(rs.next()) {
			sDDetails.add(rs.getString("original_file_name"));
		}
		PreparedStatement pst1 = conn.prepareStatement("select project_name, receiving_customer from project_info where project_number = ?;");
		pst1.setString(1, projectNumber);
		ResultSet rs1=pst1.executeQuery();
		
		while(rs1.next()) {
			sDDetails.add(rs1.getString("project_name"));
			sDDetails.add(rs1.getString("receiving_customer"));
		}
		return sDDetails;
	}
	public Properties loadProperties() throws IOException {
	Properties prop = new Properties();
	FileInputStream file = new FileInputStream(System.getProperty("user.dir") +"//constant.properties");
	prop.load(file);
	return prop;
	}
}
