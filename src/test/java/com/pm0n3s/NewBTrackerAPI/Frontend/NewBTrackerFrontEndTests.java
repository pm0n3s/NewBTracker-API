package com.pm0n3s.NewBTrackerAPI.Frontend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

@Test
public class NewBTrackerFrontEndTests {

	WebDriver driver;
	JavascriptExecutor js;

	String user;
	String password = "pass";

	String URL = "http://localhost:8080/NewBornTracker";

	@BeforeClass
	public void init() {
		user = "pat";
	}

	@BeforeTest
	public void setup() {
		driver = WebDriverManager.edgedriver().create();
		js = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
	}

	@AfterTest
	public void cleanup() {
		driver.quit();
	}

	// Login Tests

	@Test
	public void givenValidInput_whenRegisterButtonClicked_userIsCreatedAndLoggedIn() {
		driver.get(URL);

		WebElement start = driver.findElement(By.id("start"));

		new Actions(driver).moveToElement(start).click().perform();

		sleep(500l);

		WebElement unameField = driver.findElement(By.id("suname"));
		WebElement pwordField = driver.findElement(By.id("spword"));
		WebElement submit = driver.findElement(By.id("ssubmit"));

		unameField.sendKeys(user);
		pwordField.sendKeys(password);

		sleep(500l);

		new Actions(driver).moveToElement(submit).click().perform();

		sleep(1000l);

		String header = driver.findElement(By.id("header")).getText();
		assertEquals(header, "welcome back " + user + "!");

		sleep(500l);

	}

	@Test
	public void givenNoEnteredPassword_whenRegisterButtonClicked_nothingShouldHappen() {
		driver.get(URL + "/login");

		WebElement unameField = driver.findElement(By.id("suname"));
		WebElement submit = driver.findElement(By.id("ssubmit"));

		unameField.sendKeys(user);

		sleep(500l);

		new Actions(driver).moveToElement(submit).click().perform();

		sleep(500l);

		WebElement form = driver.findElement(By.id("signup"));
		assertTrue(form.isEnabled());

		sleep(500l);
	}

	@Test
	public void givenNoEnteredPassword_whenLoginButtonClicked_FlashMessageShouldShow() {
		driver.get(URL + "/login");

		WebElement unameField = driver.findElement(By.id("luname"));
		WebElement submit = driver.findElement(By.id("lsubmit"));

		unameField.sendKeys(user);

		sleep(500l);

		new Actions(driver).moveToElement(submit).click().perform();

		sleep(500l);

		String flashMessage = driver.findElement(By.id("loginError")).getText();
		assertEquals(flashMessage, "Username or Password not correct");

		sleep(500l);

	}

	@Test(dependsOnMethods = { "givenValidInput_whenRegisterButtonClicked_userIsCreatedAndLoggedIn" })
	public void givenValidLogin_whenLoginButtonClicked_userIsLoggedIn() {
		driver.get(URL + "/login");

		WebElement unameField = driver.findElement(By.id("luname"));
		WebElement pwordField = driver.findElement(By.id("lpword"));
		WebElement submit = driver.findElement(By.id("lsubmit"));

		unameField.sendKeys(user);
		pwordField.sendKeys(password);

		sleep(500l);

		new Actions(driver).moveToElement(submit).click().perform();

		sleep(500l);

		String header = driver.findElement(By.id("header")).getText();
		assertEquals(header, "welcome back " + user + "!");

		sleep(500l);

	}

	// CRUD TESTS
	@Test(dependsOnMethods = { "givenValidLogin_whenLoginButtonClicked_userIsLoggedIn" })
	public void givenValidEventData_whenAddButtonClicked_EventShouldBeSaved() {
		driver.get(URL + "/login");

		WebElement unameField = driver.findElement(By.id("luname"));
		WebElement pwordField = driver.findElement(By.id("lpword"));
		WebElement submit = driver.findElement(By.id("lsubmit"));

		unameField.sendKeys(user);
		pwordField.sendKeys(password);

		sleep(500l);

		new Actions(driver).moveToElement(submit).click().perform();

		sleep(500l);

		WebElement changeTime = driver.findElement(By.id("changeTime"));
		Select changeType = new Select(driver.findElement(By.id("changeType")));
		WebElement changeNotes = driver.findElement(By.id("changeNotes"));
		WebElement changeSubmit = driver.findElement(By.id("changeSubmit"));

		changeTime.sendKeys("08-30-2022 13:30");
		changeType.selectByVisibleText("PEE");
		changeNotes.sendKeys("Test Notes");

		js.executeScript("arguments[0].scrollIntoView()", changeSubmit);

		sleep(500l);

		new Actions(driver).moveToElement(changeSubmit).click().perform();

		WebElement foundNotes = driver.findElement(By.id("changeNotes3"));

		assertEquals("notes: Test Notes", foundNotes.getText());

		sleep(500l);
	}

	@Test(dependsOnMethods = { "givenValidLogin_whenLoginButtonClicked_userIsLoggedIn" })
	public void givenNoRequiredInput_addEventShouldBeDisabled() {
		driver.get(URL + "/login");

		WebElement unameField = driver.findElement(By.id("luname"));
		WebElement pwordField = driver.findElement(By.id("lpword"));
		WebElement submit = driver.findElement(By.id("lsubmit"));

		unameField.sendKeys(user);
		pwordField.sendKeys(password);

		sleep(500l);

		new Actions(driver).moveToElement(submit).click().perform();

		sleep(500l);

		WebElement changeNotes = driver.findElement(By.id("changeNotes"));
		WebElement changeSubmit = driver.findElement(By.id("changeSubmit"));

		changeNotes.sendKeys("Test Notes");

		assertFalse(changeSubmit.isEnabled());
	}

	@Test(dependsOnMethods = { "givenValidEventData_whenAddButtonClicked_EventShouldBeSaved" })
	public void givenValidUpdateData_whenUpdateClicked_EventShouldSaveUpdatedData() {
		driver.get(URL + "/login");

		WebElement unameField = driver.findElement(By.id("luname"));
		WebElement pwordField = driver.findElement(By.id("lpword"));
		WebElement submit = driver.findElement(By.id("lsubmit"));

		unameField.sendKeys(user);
		pwordField.sendKeys(password);

		sleep(500l);

		new Actions(driver).moveToElement(submit).click().perform();

		sleep(500l);

		WebElement editEvent = driver.findElement(By.id("changeEdit3"));

		new Actions(driver).moveToElement(editEvent).click().perform();

		WebElement changeNotes = driver.findElement(By.id("changeNotes"));
		WebElement changeSubmit = driver.findElement(By.id("changeSubmit"));

		changeNotes.clear();
		changeNotes.sendKeys("New Test Notes");

		js.executeScript("arguments[0].scrollIntoView()", changeSubmit);

		sleep(500l);

		new Actions(driver).moveToElement(changeSubmit).click().perform();

		sleep(500l);

		WebElement foundNotes = driver.findElement(By.id("changeNotes3"));

		assertEquals("notes: New Test Notes", foundNotes.getText());

		sleep(500l);
	}

	@Test(dependsOnMethods = { "givenValidEventData_whenAddButtonClicked_EventShouldBeSaved" })
	public void givenNoRequiredInput_whenUpdateIsClicked_nothingShouldHappen() {
		driver.get(URL + "/login");

		WebElement unameField = driver.findElement(By.id("luname"));
		WebElement pwordField = driver.findElement(By.id("lpword"));
		WebElement submit = driver.findElement(By.id("lsubmit"));

		unameField.sendKeys(user);
		pwordField.sendKeys(password);

		sleep(500l);

		new Actions(driver).moveToElement(submit).click().perform();

		sleep(500l);

		WebElement editEvent = driver.findElement(By.id("changeEdit3"));

		new Actions(driver).moveToElement(editEvent).click().perform();

		WebElement changeTime = driver.findElement(By.id("changeTime"));
		WebElement changeSubmit = driver.findElement(By.id("changeSubmit"));

		changeTime.clear();

		js.executeScript("arguments[0].scrollIntoView()", changeSubmit);

		sleep(500l);

		new Actions(driver).moveToElement(changeSubmit).click().perform();

		sleep(500l);

		assertFalse(changeSubmit.isEnabled());
	}

	@Test(dependsOnMethods = { "givenValidUpdateData_whenUpdateClicked_EventShouldSaveUpdatedData",
			"givenNoRequiredInput_whenUpdateIsClicked_nothingShouldHappen" })
	public void givenValidData_whenDeleteIsClicked_DataShouldBeDeleted() {
		driver.get(URL + "/login");

		WebElement unameField = driver.findElement(By.id("luname"));
		WebElement pwordField = driver.findElement(By.id("lpword"));
		WebElement submit = driver.findElement(By.id("lsubmit"));

		unameField.sendKeys(user);
		pwordField.sendKeys(password);

		sleep(500l);

		new Actions(driver).moveToElement(submit).click().perform();

		sleep(500l);

		WebElement deleteEvent = driver.findElement(By.id("changeDelete3"));

		new Actions(driver).moveToElement(deleteEvent).click().perform();

		sleep(500l);

		WebElement changes = driver.findElement(By.id("changes"));

		sleep(500l);

		System.out.println(changes.getText());

		assertEquals("", changes.getText());

	}

	private void sleep(Long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
