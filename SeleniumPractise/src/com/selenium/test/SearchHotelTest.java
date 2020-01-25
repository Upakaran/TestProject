package com.selenium.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.selenium.pagenavigation.HotelSearchPage;
import com.selenium.utility.CommonFunctions;
import com.selenium.utility.ExcelFunctions;

public class SearchHotelTest extends HotelSearchPage {

	WebDriver driver;
	ExtentReports extent;
	String excelFilePath;
	String excelSheetName2;
	ExtentTest logger;
	CommonFunctions commonFunctions = new CommonFunctions();

	@Test(dataProvider = "getTestdata")
	public void Test2(String Location, String fromDate, String toDate, String travellers)
			throws InterruptedException, IOException {
		System.out.println("Test2");

		System.out.println(Location + " " + fromDate + " " + toDate + " " + travellers);

		logger = extent.createTest(Location + " " + fromDate + " " + toDate + " " + travellers);

		hotelSearch(Location, fromDate, toDate, travellers);

	}

	@Parameters({ "excelFilePath", "excelSheetName2" })
	@BeforeTest
	public void beforeTest(String excelFilePath, String excelSheetName2) throws InterruptedException {
		System.out.println("beforeTest2");
		this.excelFilePath = excelFilePath;
		this.excelSheetName2 = excelSheetName2;
		driver = commonFunctions.doSetUp();
		ExtentHtmlReporter reporter = new ExtentHtmlReporter(
				"test-output/ExtentReport_Output/SearchHotelTestReport.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		System.out.println(setDriverHotelSearch(driver));
	}

	@Parameters({ "MailId", "Password" })
	@BeforeMethod()
	public void beforeMethod(String myMailId, String myPassword) throws InterruptedException {
		System.out.println("beforeMethod2");
		commonFunctions.Login(myMailId, myPassword);
	}

	@AfterTest
	public void afterMethod() throws InterruptedException {
		System.out.println("AfterTest2");
		commonFunctions.exitBrowser();
		extent.flush();
	}

	@AfterMethod
	public void afterTest(ITestResult result) throws InterruptedException {
		System.out.println("AfterMethod2");
		if (result.getStatus() == ITestResult.SUCCESS)
			logger.log(Status.PASS, "hotel Search passed");
		else if (result.getStatus() == ITestResult.FAILURE)
			logger.log(Status.FAIL, "hotel Search failed");
		else if (result.getStatus() == ITestResult.SKIP)
			logger.log(Status.SKIP, "hotel Search skipped");
		commonFunctions.Logout();
	}

	@DataProvider
	public Iterator<Object[]> getTestdata() {
		System.out.println("DataProvider2");
		ArrayList<Object[]> dataList = ExcelFunctions.readExcel(excelFilePath, excelSheetName2);
		return dataList.iterator();
	}

}
