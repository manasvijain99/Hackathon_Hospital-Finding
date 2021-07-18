package testCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import baseClass.pageBaseClass;
import pageClasses.corporateWellnessPage;
import pageClasses.diagnosticsPage;
import pageClasses.homePage;
import pageClasses.searchPage;

public class correctTestCase extends pageBaseClass{
	
	homePage homePage;
	searchPage searchPage;
	diagnosticsPage diagnosticsPage;
	corporateWellnessPage corporateWellnessPage;
	
	@Parameters("browser")
	@Test(groups = "testOne")
	public void submitForm(String browser) throws IOException {
		//Took data from Excel sheet using Apache Poi
		File src = new File(System.getProperty("user.dir") + "\\Excel\\data.xlsx");
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		//Used to format data from number to string
		DataFormatter formatter = new DataFormatter();
		
		
		//fetch data from excel sheet
		String name = formatter.formatCellValue(sheet.getRow(2).getCell(0));
		String organizationName = formatter.formatCellValue(sheet.getRow(2).getCell(1));
		String email = formatter.formatCellValue(sheet.getRow(2).getCell(2));
		String phone = formatter.formatCellValue(sheet.getRow(2).getCell(3));
		String size = formatter.formatCellValue(sheet.getRow(2).getCell(4));
		
		//Closed the workbook
		workbook.close();
		
		//Extent report initialization
		logger = report.createTest("Search for hospitals Test 1");
		pageBaseClass pageBaseClass = new pageBaseClass();
		pageBaseClass.invokeBrowser(browser);
		homePage = pageBaseClass.OpenApplication();
		searchPage = homePage.seachHospitals();
		diagnosticsPage = searchPage.hosptialFilters();
		corporateWellnessPage = diagnosticsPage.majorCitiesSelection();
		corporateWellnessPage.submitForm(name, organizationName, email, phone, size);
	}
}
