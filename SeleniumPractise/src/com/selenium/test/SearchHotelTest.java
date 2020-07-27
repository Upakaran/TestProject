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
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.selenium.pagenavigation.HotelSearchPage;
import com.selenium.utility.CVSFunctions;
import com.selenium.utility.CommonFunctions;
import com.selenium.utility.ExcelFunctions;

public class SearchHotelTest extends HotelSearchPage {

	WebDriver driver;
	ExtentReports extent;
	String excelFilePath;
	String excelSheetName2;
	String csvFilePath;
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

	@Parameters({ "excelFilePath", "excelSheetName2" , "csvFilePath" })
	@BeforeTest
	public void beforeTest(@Optional String excelFilePath, @Optional String excelSheetName2, @Optional String csvFilePath) throws InterruptedException {
		System.out.println("beforeTest2");
		this.excelFilePath = excelFilePath;
		this.excelSheetName2 = excelSheetName2;
		this.csvFilePath = csvFilePath;
		driver = commonFunctions.doSetUp();
		ExtentHtmlReporter reporter = new ExtentHtmlReporter(
				"Report_Output/ExtentReport_Output/SearchHotelTestReport.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		System.out.println(setDriverHotelSearch(driver));
	}

	@Parameters({ "MailId", "Password" , "targetURL" })
	@BeforeMethod()
	public void beforeMethod(String myMailId, String myPassword, String targetURL) throws InterruptedException {
		System.out.println("beforeMethod2");
		commonFunctions.Login(myMailId, myPassword , targetURL);
	}

	@AfterTest
	public void afterMethod() throws InterruptedException {
		System.out.println("AfterTest2");
		commonFunctions.exitBrowser();
		extent.flush();
	}

	@AfterMethod
	public void afterTest(ITestResult result) throws Exception {
		System.out.println("AfterMethod2");
		if (result.getStatus() == ITestResult.SUCCESS){
			logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
		//	logger.log(Status.PASS, "flight Search passed");
		}
			
		else if (result.getStatus() == ITestResult.FAILURE){
		//	logger.log(Status.FAIL, "flight Search failed");
			logger.log(Status.FAIL, "Test Case Failed is "+result.getName());
		//	 logger.log(Status.FAIL, "Test Case Failed is "+result.getThrowable());
			String screenshotPath = commonFunctions.getScreenshot(driver, result.getName());
			System.out.println("screenshotPath "+screenshotPath);
		//	logger.log(Status.FAIL, (Markup) logger.addScreenCaptureFromPath(screenshotPath));
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));
			logger.fail(result.getThrowable());
			logger.fail("Snapshot below: " + logger.addScreenCaptureFromPath(screenshotPath));
		}	
		else if (result.getStatus() == ITestResult.SKIP)
		//	logger.log(Status.SKIP, "flight Search skipped");
		logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" Test Case SKIPPED", ExtentColor.GREY));
		commonFunctions.Logout();
	}

	@DataProvider
	public Iterator<Object[]> getTestdata() {
		System.out.println("DataProvider2");
		ArrayList<Object[]> dataList = null;
		if(this.csvFilePath == null){
			 dataList = ExcelFunctions.readExcel(excelFilePath, excelSheetName2);
		}
		else if (this.csvFilePath != null){
		 dataList = CVSFunctions.ReadCSV(csvFilePath);
		}
		return dataList.iterator();
	}

}
