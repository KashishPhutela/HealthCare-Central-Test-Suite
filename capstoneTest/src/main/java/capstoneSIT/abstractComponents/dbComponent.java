package capstoneSIT.abstractComponents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class dbComponent {

	Connection conn;
	Statement st;
	ResultSet rs;
	
	public Statement initializeConnection() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://cap.mysql.database.azure.com:3306/capstone", "capstoneadmin","D@ily20242024");
		st = conn.createStatement();
		return st;
	}
	
	public HashMap<String, String> getReviewCount() throws SQLException {
		
		HashMap <String,String> dbReviews = new HashMap<>();
		conn = DriverManager.getConnection("jdbc:mysql://cap.mysql.database.azure.com:3306/capstone", "capstoneadmin","D@ily20242024");
		st = conn.createStatement();
		rs =st.executeQuery("SELECT s.name,count(r.status_id) as status_id from report r LEFT JOIN status_type s on s.id = r.status_id group by r.status_id;");
		while(rs.next()) {
			dbReviews.put(rs.getString("name"),rs.getString("status_id"));
		}
		
		return dbReviews;
	}
	
	public HashMap<String, String> getProjectDetails() throws SQLException {
		HashMap<String, String> projectDetails = new HashMap<>();
		Statement st = initializeConnection();
		rs = st.executeQuery("Select project_name, Project_number from project_info where transacting_customer='KVKB6689704K' and created_by ='kG838804IK'");
		while(rs.next()) {
			projectDetails.put(rs.getString("project_name"), rs.getString("project_number"));
		}
		return projectDetails;
	}

	public HashMap<String, String> getAssignedProjects() throws SQLException {
		HashMap<String, String> projectDetails = new HashMap<>();
		Statement st = initializeConnection();
		rs = st.executeQuery("select project_name, Project_number from project_info where project_name = 'Global Work' and created_by ='kG838804IK';");
		while(rs.next()) {
			projectDetails.put(rs.getString("project_name"), rs.getString("project_number"));
			System.out.println("dbProjectName: "+rs.getString("project_name"));
			System.out.println("dbProjectNumber: "+rs.getString("project_number"));
		}
		return projectDetails;
	}
}
