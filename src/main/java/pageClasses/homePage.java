package pageClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import baseClass.pageBaseClass;

public class homePage extends pageBaseClass{

	public WebDriver driver;
	
	//Constructor
	public homePage(WebDriver driver) {
		this.driver = driver;
	}
	
	
	
	public searchPage seachHospitals() throws IOException {
		
		
		//Took data from Excel sheet using Apache Poi
		File src = new File(System.getProperty("user.dir") + "\\Excel\\data.xlsx");
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
				
		//Used to format data from number to string
		DataFormatter formatter = new DataFormatter();
		
		String cityName = formatter.formatCellValue(sheet.getRow(16).getCell(0));
		String categoryString = formatter.formatCellValue(sheet.getRow(16).getCell(1));
		
		//Closed the workbook
		workbook.close();
		
		WebElement locElement = driver.findElement(By.xpath(prop.getProperty("locationSearchBox_xpath")));
		locElement.clear();
		locElement.sendKeys(cityName);
		
		//Clicking city name
		for(int i=0;i<15;i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(driver.findElement(By.xpath(prop.getProperty("locationClick_xpath"))).getText().equalsIgnoreCase(cityName)) {
				driver.findElement(By.xpath(prop.getProperty("locationClick_xpath"))).click();
				break;
			}
			if(i==5 || i==10) {
				locElement.clear();
				locElement.sendKeys(cityName);
			}
		}
		
		screenshot("city.png", driver);
		reportPass("Found Bangalore City in Location List");
		
		WebElement hospitalType = driver.findElement(By.xpath(prop.getProperty("hospitalSearchBox_xpath")));
		hospitalType.sendKeys(categoryString);
		
		
		//Clicking hospital category
		for(int i=0;i<15;i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}if(driver.findElement(By.xpath(prop.getProperty("hospitalBoxClick_xpath"))).getText().equalsIgnoreCase(categoryString)) {
				driver.findElement(By.xpath(prop.getProperty("hospitalTextClick"))).click();
				break;
			}
			if(i==5 || i==10) {
				hospitalType.clear();
				hospitalType.sendKeys(categoryString);
			}
		}
		
		

		//Took ScreenShot
		screenshot("SearchBoxFilled.png",driver);
		
		//Extent Report
		reportPass("Hospital keyword found on category list");
		reportPass("Hospital search executed");
		return PageFactory.initElements(driver, searchPage.class);
	}
}
