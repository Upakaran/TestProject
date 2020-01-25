package com.selenium.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonFunctions {
	
	WebDriver driver;
	String targetURL = "https://www.cleartrip.com/";
	
	/* used in login method*/
	
	By yourTrips = By.xpath("//span[text()='Your trips']");
	By signInBtn = By.xpath("//input[@title='Sign In']");
	By username = By.xpath("//input[@title='Your username']");
	By password = By.xpath("//input[@title='Your account password']");
	By signInBtnInFrame = By.xpath("//button[@id='signInButton']");
	
	/* used in logout method*/
	
	By SignOutHeader = By.xpath("//span[@class='span span19 truncate']");
	By SignoutBtn = By.xpath("//a[text()='Sign out']");
	By ClearTripLogo = By.xpath("(//span[@title='Cleartrip '])");
	
	public  WebDriver doSetUp() {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Kaushik\\Downloads\\chromedriver_win32\\chromedriver.exe");

		// Create object of HashMap Class
		Map<String, Object> prefs = new HashMap<String, Object>();

		// Set the notification setting it will override the default setting
		prefs.put("profile.default_content_setting_values.notifications", 2);

		prefs.put("credentials_enable_service", false);

		prefs.put("profile.password_manager_enabled", false);

		// Create object of ChromeOption class
		ChromeOptions options = new ChromeOptions();

		options.addArguments("disable-infobars");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-notifications");
		// Set the experimental option
		options.setExperimentalOption("prefs", prefs);
		options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation", "load-extension" });

		// pass the options object in Chrome driver
		driver = new ChromeDriver(options);

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		
		return driver;

	}

	public void Login(String myMailId, String myPassword) throws InterruptedException {

		// launch browser

		//driver.manage().deleteAllCookies();
		System.out.println("logging in " + myMailId + " " + myPassword);
		driver.get(targetURL);

		driver.findElement(yourTrips).click();
		Thread.sleep(2000);
		driver.findElement(signInBtn).click();
		Thread.sleep(2000);
		driver.switchTo().frame("modal_window");
		Thread.sleep(2000);
		driver.findElement(username).sendKeys(myMailId);
		Thread.sleep(2000);
		driver.findElement(password).sendKeys(myPassword);
		Thread.sleep(2000);
		driver.findElement(signInBtnInFrame).click();
		Thread.sleep(5000);
		Thread.sleep(2000);
		
		myExplicitWait("//a[text()='Hotels']" , driver);
	}

	public void Logout() throws InterruptedException {

		System.out.println("In logout");
		driver.findElement(SignOutHeader).click();
		Thread.sleep(5000);
		driver.findElement(SignoutBtn).click();
		Thread.sleep(5000);
		System.out.println("logout done");
		driver.findElement(ClearTripLogo).click();
		Thread.sleep(5000);
		driver.manage().deleteAllCookies();
		Thread.sleep(2000);

	}
	
	public void exitBrowser() throws InterruptedException {
		Thread.sleep(3000);
		driver.close();
	}

	public static WebElement myExplicitWait(String xpath , WebDriver driver) {
		System.out.println("in explicit wait");
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		return element;
	}
}
