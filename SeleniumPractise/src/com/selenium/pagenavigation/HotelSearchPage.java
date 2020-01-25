package com.selenium.pagenavigation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.selenium.utility.CommonFunctions;

public class HotelSearchPage {

	WebDriver driver;

	/* to be used in hotelSearch method */

	By hotelLink = By.xpath("//a[text()='Hotels']");
	By hotelLocation = By.xpath("//input[@id='Tags']");
	By checkInDateBox = By.xpath("(//input[@title='Check-in date'])");
	By checkOutDateBox = By.xpath("(//input[@title='Check-out date'])");
	By datePicker1 = By.xpath("(//i[@class='calendarIcon datePicker'])[1]");
	By datePicker2 = By.xpath("(//i[@class='calendarIcon datePicker'])[2]");
	By datePickerCurrentMonth = By.xpath("(//span[@class='ui-datepicker-month'])[1]") ;
	By datePickerCurrentYear = By.xpath("(//span[@class='ui-datepicker-year'])[1]") ;
	By datePickerNextMonthArrow = By.xpath("//a[@class='nextMonth ']");
	By selectTravellers = By.id("travellersOnhome");
	By hotelSearchbtn = By.id("SearchHotelsButton");
	
	
	public String setDriverHotelSearch(WebDriver driver) {
		this.driver = driver;
		return ("WebDriver set up successfully");
	}

	public void hotelSearch(String Location, String fromDate, String toDate, String travellers)
			throws InterruptedException, IOException {

		System.out.println("in hotel search");
		// data manupulations

		System.out.println(fromDate + " " + toDate);

		Map<String, String> dateMap = new LinkedHashMap<String, String>();
		dateMap.put("01", "January");
		dateMap.put("02", "February");
		dateMap.put("03", "March");
		dateMap.put("04", "April");
		dateMap.put("05", "May");
		dateMap.put("06", "June");
		dateMap.put("07", "July");
		dateMap.put("08", "August");
		dateMap.put("09", "September");
		dateMap.put("10", "October");
		dateMap.put("11", "November");
		dateMap.put("12", "December");

		String fromDay = fromDate.split("/")[0];
		if (fromDay.startsWith("0")) {
			fromDay = fromDay.replace("0", "");
		}

		String fromMonth = fromDate.split("/")[1];
		fromMonth = dateMap.get(fromMonth);
		String fromYear = fromDate.split("/")[2];

		System.out.println(fromDay + " " + fromMonth + " " + fromYear + " from date");

		String toDay = toDate.split("/")[0];
		if (toDay.startsWith("0")) {
			toDay = toDay.replace("0", "");
		}
		String toMonth = toDate.split("/")[1];
		toMonth = dateMap.get(toMonth);
		String toYear = toDate.split("/")[2];
		System.out.println(toDay + " " + toMonth + " " + toYear + " to date");

		Thread.sleep(3000);
		Thread.sleep(3000);
		// select the hotel icon
		driver.findElement(hotelLink).click();

		Thread.sleep(6000);

		// select Location

		WebElement locationBox = driver.findElement(hotelLocation);
		locationBox.clear();
		Thread.sleep(1000);
		locationBox.sendKeys(Location);
		Thread.sleep(5000);
		locationBox.sendKeys(Keys.ENTER);

		Thread.sleep(5000);

		// clear previous from date
		driver.findElement(checkInDateBox).click();
		Thread.sleep(3000);
		driver.findElement(checkInDateBox).sendKeys(Keys.BACK_SPACE);
		Thread.sleep(3000);

		// select from date

		driver.findElement(datePicker1).click();
		Thread.sleep(2000);

		String month = driver.findElement(datePickerCurrentMonth).getText();
		String year = driver.findElement(datePickerCurrentYear).getText();

		while (!(month.equalsIgnoreCase(fromMonth) && year.equalsIgnoreCase(fromYear))) {
			driver.findElement(datePickerNextMonthArrow).click();

			System.out.println(month + year);
			month = driver.findElement(datePickerCurrentMonth).getText();
			year = driver.findElement(datePickerCurrentYear).getText();
			Thread.sleep(2000);
		}

		// customized x=path to get the given date
		
		driver.findElement(By.xpath("(//a[@class='ui-state-default ' and text()=" + fromDay + "])[1]")).click();
		Thread.sleep(2000);

		// clear previous to date
		driver.findElement(checkOutDateBox).click();
		Thread.sleep(1000);
		driver.findElement(checkOutDateBox).sendKeys(Keys.BACK_SPACE);
		Thread.sleep(1000);

		// select to date

		driver.findElement(datePicker2).click();
		Thread.sleep(2000);

		month = driver.findElement(datePickerCurrentMonth).getText();
		year = driver.findElement(datePickerCurrentYear).getText();

		while (!(month.equalsIgnoreCase(toMonth) && year.equalsIgnoreCase(toYear))) {
			driver.findElement(datePickerNextMonthArrow).click();

			System.out.println(month + year);
			month = driver.findElement(datePickerCurrentMonth).getText();
			year = driver.findElement(datePickerCurrentYear).getText();
			Thread.sleep(2000);
		}

		// customized x=path to get the given date
		
		driver.findElement(By.xpath("(//a[@class='ui-state-default ' and text()=" + toDay + "])[1]")).click();
		Thread.sleep(2000);

		// select no of rooms and adult travellers

		Select travellerDropdown = new Select(driver.findElement(selectTravellers));
		// travellerDropdown.selectByValue(travellers);
		travellerDropdown.selectByVisibleText(travellers);
		Thread.sleep(2000);

		// select search button

		driver.findElement(hotelSearchbtn).click();
		WebElement element = CommonFunctions.myExplicitWait("(//input[@id='showNearByBox'])[1]", driver);
		Thread.sleep(3000);
		element.click();
		Thread.sleep(3000);

		HotelSearchDetailsPage.iterateHotels(driver);

	}

	public static String getTextNode(WebElement e)

	{

		String text = e.getText().toString().trim();

		ArrayList<WebElement> children = (ArrayList<WebElement>) e.findElements(By.xpath("./*"));

		for (WebElement child : children)

		{

			text = text.replaceFirst(child.getText(), "").trim();

		}

		return text;

	}
}
