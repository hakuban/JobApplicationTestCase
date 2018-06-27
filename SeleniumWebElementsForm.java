package com.cybertek.TestNG;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumWebElementsForm {
	WebDriver driver;
	Faker faker;


	@BeforeClass // runs once for all tests
	public void setUp() {
		//Setting up WebDriver in BeforeClass
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
		faker=new Faker();
	}
	
	
	@Test 
	public void SeleniumWebElementsForm() throws InterruptedException {
		driver.get("https://forms.zohopublic.com/murodil/form/SeleniumWebElements/formperma/eCecYgX4WMcmjxvXVq6UdhA2ABXIoqPAxnAF8H8CCJg");
		List<WebElement> inputBoxes=driver.findElements(By.cssSelector("input[type='text']"));
		List<WebElement> dropDowns=driver.findElements(By.xpath("//select"));	
		List<WebElement> checkBoxes=driver.findElements(By.cssSelector("input[type='checkbox']"));
		List<WebElement> radioButtons=driver.findElements(By.cssSelector("input[type='radio']"));
		List<WebElement> buttons=driver.findElements(By.xpath("//button"));
		
		assertEquals(inputBoxes.size(),2);
		assertEquals(dropDowns.size(),3);
		assertEquals(checkBoxes.size(),9);
		assertEquals(radioButtons.size(),9);
		assertEquals(buttons.size(),1);
		
		for(WebElement each: inputBoxes) {
			each.sendKeys(faker.name().firstName());
		}
		
		for(WebElement each: dropDowns) {
			Select select=new Select(each);
			select.selectByIndex(faker.number().numberBetween(1, 4));
		}
		
		for(WebElement each: checkBoxes) {
			each.click();
		}
		
		for(WebElement each: radioButtons) {
			each.click();
			Thread.sleep(1000);
		}
		
		for(WebElement each: buttons) {
			each.click();
		}
	
		
	}


}
