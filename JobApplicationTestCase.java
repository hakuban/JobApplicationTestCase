package com.cybertek.TestNG;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.Assert;

public class JobApplicationTestCase {
	WebDriver driver;
	String firstName;
	String lastName;
	int gender;
	String dateOfBirth;
	String email;
	String phoneNumber;
	String city;
	String state;
	String country;
	int annualSalary;
	List<String> technologies;
	int yearsOfExperience;
	String education;
	String github;
	List<String> certifications;
	String additionalSkills;
	Faker data = new Faker();
	Random random = new Random();
	String birthDay;
	String applicationID;
	String actualIP;
	List<String> emailInfo;

	@BeforeClass // runs once for all tests
	public void setUp() {
		//Setting up WebDriver in BeforeClass
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();

		//Navigating to homepage
		driver.get(
				"https://forms.zohopublic.com/murodil/form/JobApplicationForm/formperma/kOqgtfkv1dMJ4Df6k4_mekBNfNLIconAHvfdIk3CJSQ");
		firstName = data.name().firstName();
		lastName = data.name().lastName();
		gender = data.number().numberBetween(1, 3);
		dateOfBirth = data.date().birthday().toString();
		email = "hakuban@yahoo.com";
		phoneNumber = data.phoneNumber().cellPhone().replace(".", "");
		city = data.address().cityName();
		state = data.address().stateAbbr();
		country = data.address().country();
		annualSalary = data.number().numberBetween(60000, 150000);
		technologies = new ArrayList<String>();
		technologies.add("Java-" + data.number().numberBetween(1, 4));
		technologies.add("HTML-" + data.number().numberBetween(1, 4));
		technologies.add("Selenium WebDriver-" + data.number().numberBetween(1, 4));
		technologies.add("TestNG-" + data.number().numberBetween(1, 4));
		technologies.add("Git-" + data.number().numberBetween(1, 4));
		technologies.add("Maven-" + data.number().numberBetween(1, 4));
		technologies.add("JUnit-" + data.number().numberBetween(1, 4));
		technologies.add("Cucumber-" + data.number().numberBetween(1, 4));
		technologies.add("API Automation-" + data.number().numberBetween(1, 4));
		technologies.add("JDBC-" + data.number().numberBetween(1, 4));
		technologies.add("SQL-" + data.number().numberBetween(1, 4));

		yearsOfExperience = data.number().numberBetween(0, 11);
		education = data.number().numberBetween(1, 4) + "";
		github = "https://github.com/CybertekSchool/selenium-maven-automation.git";
		certifications = new ArrayList<String>();
		certifications.add("Java OCA");
		certifications.add("AWS");
		additionalSkills = data.job().keySkills();

	}

	@Test(priority = 1)
	public void submitFullApplication() throws InterruptedException {
		// FIRST PAGE ACTIONS
		driver.findElement(By.xpath("//input[@name='Name_First']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@name='Name_Last']")).sendKeys(lastName);
		setGender(gender);
		setDateOfBirth(dateOfBirth);
		driver.findElement(By.xpath("//input[@name='Email']")).clear();
		driver.findElement(By.xpath("//input[@name='Email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@name='countrycode']")).sendKeys(phoneNumber);
		driver.findElement(By.xpath("//input[@name='Address_City']")).sendKeys(city);
		driver.findElement(By.xpath("//input[@name='Address_Region']")).sendKeys(state);
		Select countryElem = new Select(driver.findElement(By.xpath("//select[@id='Address_Country']")));
		countryElem.selectByIndex(data.number().numberBetween(1, countryElem.getOptions().size()));
		driver.findElement(By.xpath("//input[@name='Number']")).sendKeys(String.valueOf(annualSalary) + Keys.TAB);
		verifySalaryCalculations(annualSalary);
		driver.findElement(By.xpath("//em[.=' Next ']")).click();

		// SECOND PAGE ACTIONS
		setSkillset(technologies);
		if (yearsOfExperience > 0) {
			driver.findElement(By.xpath("//a[@rating_value='" + yearsOfExperience + "']")).click();
		}
		Select educationList = new Select(driver.findElement(By.xpath("//select[@name='Dropdown']")));
		educationList.selectByIndex(data.number().numberBetween(1, educationList.getOptions().size()));
		driver.findElement(By.cssSelector("input[name='Website']")).sendKeys(github);
		setCertifications(certifications);
		driver.findElement(By.cssSelector("textarea[name='MultiLine']")).clear();
		driver.findElement(By.cssSelector("textarea[name='MultiLine']")).sendKeys(additionalSkills);
		driver.findElement(By.cssSelector(".fmSmtButton.submitColor.fmSmtButtom")).click();
		actualIP = driver.findElement(By.cssSelector("label[class='descFld'] div:nth-child(6)")).getText();
		applicationID=driver.findElement(By.cssSelector("label[class='descFld'] div:nth-child(8)")).getText().substring(16); 
		
	}

	@Test(priority = 2)
	public void IPaddressValidation() throws InterruptedException {
		driver.get("https://www.privateinternetaccess.com/pages/whats-my-ip/");
		Thread.sleep(2000);
		String expectedIP = driver.findElement(By.cssSelector("span[class='darktext']")).getText();
		Assert.assertTrue(actualIP.contains(expectedIP));
	}

	@Test(priority = 3)
	public void ConfirmationInfo() throws InterruptedException {

		// logging into my email and finding the confirmation email

		driver.get("https://login.yahoo.com/?.src=ym&.lang=en-US&.intl=us&.done=https%3A%2F%2Fmail.yahoo.com%2Fd");
		driver.findElement(By.cssSelector("input[id='login-username']")).sendKeys("hakuban");
		driver.findElement(By.cssSelector("input[id='login-signin']")).click();
		Thread.sleep(1000);
		driver.findElement(By.cssSelector("[id='login-passwd']")).sendKeys("Myhakuisagent007");
		driver.findElement(By.cssSelector("[id='login-signin']")).click();
		//Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"mail-search\"]/div/div/div[1]/ul/li/div/div/input[1]")).sendKeys("SDET Application #"+applicationID);
		driver.findElement(By.xpath("//button[@data-test-id='search-basic-btn']")).click();
		//driver.findElement(By.cssSelector("[class='D_F ab_C gl_C W_6D6F'")).click();
		//Thread.sleep(3000);
		driver.findElement(By.cssSelector("span[class='o_h G_e J_x en_N u_b']")).click();
		
	
		//extracting datas from confirmation email
		Thread.sleep(3000);
		List<WebElement> allInfo=driver.findElements(By.xpath("//*[@class='jb_0 X_6MGW N_6Fd5']/div/div//table/tbody/tr/td[3]"));
		emailInfo=new ArrayList<String>();
		for(WebElement each:allInfo) {
			if(!each.getText().isEmpty()) {
				emailInfo.add(each.getText());
			}
		}
		System.out.println("\n===========Info of confirmation Email=============");
		System.out.println(emailInfo);
		System.out.println("==================================================\n");
	}
	
	
	@Test(priority=4)
	public void fullNameValidation(){
		Assert.assertEquals(firstName+","+lastName,emailInfo.get(1));
		System.out.println("\nExpected fullname is: \""+firstName+","+lastName+"\" | Email fullname is: \""+emailInfo.get(1)+"\"");
	
		
	}
	
	@Test(priority=5)
	public void genderValidation(){
		String genderStr="";
		if (gender == 1) {
			genderStr="Male";
		} else {
			genderStr="Female";
		}
		Assert.assertEquals(genderStr,emailInfo.get(2));
		System.out.println("Expected gender is: \""+genderStr+"\" | Email gender is: \""+emailInfo.get(2)+"\"");
		
	}
	
	@Test(priority=6)
	public void dateOfBirthValidation(){
		Assert.assertEquals(birthDay, emailInfo.get(3));
		System.out.println("Expected birthday is: \""+birthDay+"\" | Email gender is: \""+emailInfo.get(3)+"\"");	
	}
	
	@Test(priority=7)
	public void phoneNumberValidation(){
		Assert.assertEquals(phoneNumber, emailInfo.get(5));
		System.out.println("Expected phoneNumber is: \""+phoneNumber+"\" | Email phoneNumber is: \""+emailInfo.get(5)+"\"\n");	
	}
	
	
	
	
	
	
	//-----------------Utility------------------

	public void setCertifications(List<String> list) {
		List<WebElement> text = driver.findElements(By.cssSelector("label[class='checkChoice']"));
		for (WebElement eachel : text) {
			for (String each : list) {
				if (eachel.getText().contains(each)) {
					eachel.click();
				}
			}
		}
	}

	public void setSkillset(List<String> tech) {

		for (String skill : tech) {
			String technology = skill.substring(0, skill.length() - 2);
			int rate = Integer.parseInt(skill.substring(skill.length() - 1));

			String level = "";

			switch (rate) {
			case 1:
				level = "Expert";
				break;
			case 2:
				level = "Proficient";
				break;
			case 3:
				level = "Beginner";
				break;
			default:
				fail(rate + " is not a valid level");
			}

			String xpath = "//input[@rowvalue='" + technology + "' and @columnvalue='" + level + "']";
			driver.findElement(By.xpath(xpath)).click();

		}

	}

	public void verifySalaryCalculations(int annual) {
		String monthly = driver.findElement(By.xpath("//input[@name='Formula']")).getAttribute("value");
		String weekly = driver.findElement(By.xpath("//input[@name='Formula1']")).getAttribute("value");
		String hourly = driver.findElement(By.xpath("//input[@name='Formula2']")).getAttribute("value");

		System.out.println(monthly);
		System.out.println(weekly);
		System.out.println(hourly);

		DecimalFormat formatter = new DecimalFormat("#.##");

		assertEquals(Double.parseDouble(monthly), Double.parseDouble(formatter.format((double) annual / 12.0)));
		assertEquals(Double.parseDouble(weekly), Double.parseDouble(formatter.format((double) annual / 52.0)));
		assertEquals(Double.parseDouble(hourly), Double.parseDouble(formatter.format((double) annual / 52.0 / 40.0)));
	}

	public void setDateOfBirth(String bday) {
		String[] pieces = bday.split(" ");
		birthDay = pieces[2] + "-" + pieces[1] + "-" + pieces[5];
		driver.findElement(By.xpath("//input[@id='Date-date']")).sendKeys(birthDay);
	}

	public void setGender(int n) {
		if (n == 1) {
			driver.findElement(By.xpath("//input[@value='Male']")).click();
		} else {
			driver.findElement(By.xpath("//input[@value='Female']")).click();
		}
	}

	
	public void fullNameEmptyTest() {
		// firstly assert that you are on the correct page
		assertEquals(driver.getTitle(), "SDET Job Application");

		driver.findElement(By.xpath("//input[@elname='first']")).clear();
		driver.findElement(By.xpath("//*[@elname='last']")).clear();

		driver.findElement(By.xpath("//em[.=' Next ']")).click();

		String nameError = driver.findElement(By.xpath("//p[@id='error-Name']")).getText();
		assertEquals(nameError, "Enter a value for this field.");
	}
}

