package pageClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import baseClass.pageBaseClass;


public class diagnosticsPage extends pageBaseClass{

	public WebDriver driver;
	
	//Constructor
	public diagnosticsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	
	public corporateWellnessPage majorCitiesSelection() throws IOException {
		
		//Fetching all major cities in list
		List<WebElement> topCitiesElements;
		try {
			topCitiesElements = driver.findElements(By.xpath(prop.getProperty("majoyCityList_xpath")));
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			topCitiesElements = driver.findElements(By.xpath(prop.getProperty("majoyCityList_xpath")));
		}
		
		//ScreenShot
		screenshot("MajorCities.png",driver);
		
		String[] topCitiesNameList = new String[topCitiesElements.size()];
		
		//Printing Major city names
		System.out.println("**********************major cities List***************************");
		for(int j=1;j<=topCitiesElements.size();j++) {
			topCitiesNameList[j-1] = driver.findElement(By.xpath(prop.getProperty("majorCityNames_xpath1")+j+prop.getProperty("majorCityNames_xpath2"))).getText();
			System.out.println(topCitiesNameList[j-1]);
		}
		try {
			driver.findElement(By.xpath(prop.getProperty("cityClick_xpath"))).click();
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			// TODO: handle exception
			driver.findElement(By.xpath(prop.getProperty("cityClick_xpath"))).click();
		}
		reportPass("All major cities printed on console");
		/********************For Providers********************************/
		try {
			driver.findElement(By.xpath(prop.getProperty("forProviders_xpath"))).click();
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			// TODO: handle exception
			driver.findElement(By.xpath(prop.getProperty("forProviders_xpath"))).click();
		}
		screenshot("ForProviders.png",driver);
		try {
			driver.findElement(By.xpath(prop.getProperty("corporateClick_xpath"))).click();
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			// TODO: handle exception
			driver.findElement(By.xpath(prop.getProperty("corporateClick_xpath"))).click();
		}
		
		
		//Change the browser tab
		ArrayList<String> newTb = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(newTb.get(1));
		
		reportPass("Corporate Wellness page executed");
		return PageFactory.initElements(driver, corporateWellnessPage.class);
	}
}
