package pageClasses;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseClass.pageBaseClass;

public class corporateWellnessPage extends pageBaseClass{
	
	public WebDriver driver;
	//Constructor
	public corporateWellnessPage(WebDriver driver) {
		this.driver = driver;
		
	}
	

	
	public void submitForm(String name, String organizationName, String email, String phone, String size) throws IOException {
		
		//Entering data in form
		driver.findElement(By.id(prop.getProperty("name_id"))).sendKeys(name);
		driver.findElement(By.id(prop.getProperty("organisation_id"))).sendKeys(organizationName);
		driver.findElement(By.id(prop.getProperty("email_id"))).sendKeys(email);
		driver.findElement(By.id(prop.getProperty("phone_id"))).sendKeys(phone);
		
		Select select = new Select(driver.findElement(By.id(prop.getProperty("size_id"))));
		select.selectByVisibleText(size);
		
		//ScreenShot and Extent Report
		screenshot("FormFilled.png",driver);
		reportPass("Form filled");
		
		
		driver.findElement(By.xpath(prop.getProperty("submit_xpath"))).click();
		
		//Switching i-frame
		driver.switchTo().frame(2);
		
		//Explicit wait of 1 second
		WebDriverWait wait = new WebDriverWait(driver, 1);
		
		//Using this while loop because there is "Image CAPTCHA" in webSite which we have to Solve manually
		while(true) {
			//Handling alert
			try {
				if(wait.until(ExpectedConditions.alertIsPresent())!=null) {
					reportFail(driver.switchTo().alert().getText());
					System.out.println("****************************Alert********************************");
					System.out.println(driver.switchTo().alert().getText());
					driver.switchTo().alert().accept();
					break;
				}
			} catch (org.openqa.selenium.TimeoutException ex) {
				
				
			}
			//switch back iframe to default 
			driver.switchTo().defaultContent();
			if(driver.findElement(By.id("thankyou-section")).isDisplayed()) {
				reportPass("Form Submitted");
				screenshot("ThankYouPage.png",driver);
				System.out.println("Test Case Passed");
				break;
			}
			//switch to i-frame
			driver.switchTo().frame(2);
		}
		//Quit driver
		driver.quit();
	}
}
