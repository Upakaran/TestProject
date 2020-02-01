package com.selenium.test;

import org.testng.ITestResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.selenium.pagenavigation.RoundTripFlightSearchPage;
import com.selenium.utility.ExcelFunctions;
import com.selenium.utility.CommonFunctions;
import com.selenium.utility.CVSFunctions;

public class RoundTripFlightSearchTest extends RoundTripFlightSearchPage {

	String excelFilePath;
	String excelSheetName;
	String csvFilePath;
	ExtentReports extent;
	WebDriver driver;
	String exception;
	ExtentTest logger;

	CommonFunctions commonFunctions = new CommonFunctions();

	@Test(dataProvider = "getTestdata")
	public void Test1(String fromCity, String toCity, String onwardDate, String returnDate, String Adult,
			String Children, String Infant) throws InterruptedException, IOException {
		System.out.println("Test1");
		logger = extent.createTest(fromCity + "_" + toCity);
		FlightSearch(fromCity, toCity, onwardDate, returnDate, Adult, Children, Infant);

	}

	@Parameters({ "excelFilePath", "excelSheetName" , "csvFilePath"})
	@BeforeTest
	public void beforeTest( @Optional String excelFilePath, @Optional String excelSheetName, @Optional String csvFilePath) throws InterruptedException {
		System.out.println("beforeTest");
		this.excelFilePath = excelFilePath;
		this.excelSheetName = excelSheetName;
		this.csvFilePath = csvFilePath;
		driver = commonFunctions.doSetUp();
		ExtentHtmlReporter reporter = new ExtentHtmlReporter(
				"test-output/ExtentReport_Output/RoundTripFlightSearchTestReport.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		System.out.println(setDriverRoundTripFlightSearch(driver));
	}

	@Parameters({ "MailId", "Password" })
	@BeforeMethod()
	public void beforeMethod(String myMailId, String myPassword) throws InterruptedException {
		System.out.println("beforeMethod");
		commonFunctions.Login(myMailId, myPassword);
	}

	@AfterTest
	public void afterMethod() throws InterruptedException {
		System.out.println("AfterTest");
		commonFunctions.exitBrowser();
		extent.flush();
	}

	@AfterMethod
	public void afterTest(ITestResult result) throws InterruptedException {
		System.out.println("AfterMethod");
		if (result.getStatus() == ITestResult.SUCCESS)
			logger.log(Status.PASS, "flight Search passed");
		else if (result.getStatus() == ITestResult.FAILURE)
			logger.log(Status.FAIL, "flight Search failed");
		else if (result.getStatus() == ITestResult.SKIP)
			logger.log(Status.SKIP, "flight Search skipped");
		commonFunctions.Logout();
	}

	@DataProvider
	public Iterator<Object[]> getTestdata() {
		System.out.println("DataProvider");
		ArrayList<Object[]> dataList = null;
		if(this.csvFilePath == null){
			 dataList = ExcelFunctions.readExcel(excelFilePath, excelSheetName);
		}
		else if (this.csvFilePath != null){
		 dataList = CVSFunctions.ReadCSV(csvFilePath);
		}
		
		return dataList.iterator();
	}

}
