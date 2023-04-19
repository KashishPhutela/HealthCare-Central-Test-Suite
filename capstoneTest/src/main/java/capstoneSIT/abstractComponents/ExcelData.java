package capstoneSIT.abstractComponents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelData {
	
	public static ArrayList<String> getProjectData() throws IOException {
		
		ArrayList<String> a = new ArrayList<String>();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") +"//CapstoneTestData.xlsx");
	    XSSFWorkbook workbook = new XSSFWorkbook(fis);
	    
	    if(workbook.getSheetName(0).equalsIgnoreCase("newProject")) {
	    	XSSFSheet sheet = workbook.getSheetAt(0);
	    	Iterator rowsIter = sheet.iterator();
	    	Row firstRow = (Row) rowsIter.next(); 
	    	Row data = (Row) rowsIter.next();   //got access to first row
	    	Iterator<Cell> cellIter = data.cellIterator();
	    	while(cellIter.hasNext()) {
	    		DataFormatter formatter = new DataFormatter();
	    		String val = formatter.formatCellValue(cellIter.next());
	    		 a.add(val);
	    	}
	    }
	    else {
	    	System.out.println("Incorrect Sheet Name. Please make sure that first sheet has New Project data");
	    }
	    return a;
}
	
	public static ArrayList<String> getReportDataFromExcel() throws IOException {
		
		ArrayList<String> list = new ArrayList<String>();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") +"//CapstoneTestData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
	
		if(workbook.getSheetName(1).equalsIgnoreCase("newReport")) {
			XSSFSheet sheet = workbook.getSheetAt(1);
			Iterator rowsIter = sheet.iterator();
			Row firstRow = (Row) rowsIter.next();
			Row secondRow = (Row) rowsIter.next();
			
			Iterator<Cell> cellIter = secondRow.cellIterator();
			
			while(cellIter.hasNext()) {
				DataFormatter formatter = new DataFormatter();
	    		String val = formatter.formatCellValue(cellIter.next());
				list.add(val);
				
			} //while closing
			
			
		} //if closing
		
		else {
			System.out.println("Incorrect Sheet Name. Please make sure that Second sheet has New Report data");
		}
		
		return list;
	}//method closing
}
