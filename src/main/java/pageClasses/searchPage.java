package pageClasses;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import baseClass.pageBaseClass;

public class searchPage extends pageBaseClass {
	public WebDriver driver;

	//Constructor
	public searchPage(WebDriver driver) {
		this.driver = driver;
	}

	
	public diagnosticsPage hosptialFilters() throws IOException {
		
		//Checking every filters on the webpage
		driver.findElement(By.xpath(prop.getProperty("accreditedCheckbox_xpath"))).click();

		try {
			driver.findElement(By.xpath(prop.getProperty("open_24X7Checkbox_xpath"))).click();
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			// TODO: handle exception
			driver.findElement(By.xpath(prop.getProperty("open_24X7Checkbox_xpath"))).click();
		}
		try {
			driver.findElement(By.xpath(prop.getProperty("all_filters_xpath"))).click();
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			// TODO: handle exception
			driver.findElement(By.xpath(prop.getProperty("all_filters_xpath"))).click();
		}
		try {
			driver.findElement(By.xpath(prop.getProperty("Has_Parking_checkbox_xpath"))).click();
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			// TODO: handle exception
			driver.findElement(By.xpath(prop.getProperty("Has_Parking_checkbox_xpath"))).click();
		}
		
		//ScreenShot and Extent Report
		screenshot("FiltersSeleted.png",driver);
		reportPass("Every Filters Executed");
		
		/****************************Printing All Hospitals having rating > 3.5**************************/
		
		System.out.println("****************Hospitals having rating greater than 3.5*********************");
		List<WebElement> hospitalsElements = driver.findElements(By.xpath(prop.getProperty("hospitalCard_xpath")));
		float rating;
		int n = hospitalsElements.size();
		for (int i = 1; i <= n-1; i++) {
			try {
				rating = Float.valueOf(driver.findElement(By.xpath(prop.getProperty("hospitalRating_xpath1") + i + prop.getProperty("hospitalRating_xpath2"))).getText());
			} catch (org.openqa.selenium.StaleElementReferenceException ex) {
				// TODO: handle exception
				rating = Float.valueOf(driver.findElement(By.xpath(prop.getProperty("hospitalRating_xpath1") + i + prop.getProperty("hospitalRating_xpath2"))).getText());

			}
			for(int trial = 0;trial<5;trial++) {
				try {
					if(!driver.findElement(By.xpath(prop.getProperty("hospitalRating_xpath1") + i + prop.getProperty("hospitalRating_xpath2"))).isDisplayed()) {
						continue;
					}
				} catch (org.openqa.selenium.StaleElementReferenceException ex) {
					// TODO: handle exception
					if(!driver.findElement(By.xpath(prop.getProperty("hospitalRating_xpath1") + i + prop.getProperty("hospitalRating_xpath2"))).isDisplayed()) {
						continue;
					}
				}
			}
			
			if (rating > 3.5) {
				String hospitalName;
				try {
					hospitalName = driver.findElement(By.xpath(prop.getProperty("hospitalName_xpath1") + i + prop.getProperty("hospitalName_xpath2"))).getText();
				} catch (org.openqa.selenium.StaleElementReferenceException ex) {
					// TODO: handle exception
					hospitalName = driver.findElement(By.xpath(prop.getProperty("hospitalName_xpath1") + i + prop.getProperty("hospitalName_xpath2"))).getText();
				}
				
				System.out.println(hospitalName);
			}
		}
		reportPass("All Hospitals having rating > 3.5 printed on console");
		
		//Click the diagnostics Button
		try {
			driver.findElement(By.xpath(prop.getProperty("diagnosticsButton_xpath"))).click();
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			// TODO: handle exception
			driver.findElement(By.xpath(prop.getProperty("diagnosticsButton_xpath"))).click();
		}
		
		//Extent report submission
		reportPass("Diagnostics Page Executed");
		return PageFactory.initElements(driver, diagnosticsPage.class);
	}
}
