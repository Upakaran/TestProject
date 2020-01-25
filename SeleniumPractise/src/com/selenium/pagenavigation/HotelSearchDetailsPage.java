package com.selenium.pagenavigation;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.selenium.utility.ExcelFunctions;

public class HotelSearchDetailsPage {

	static By nearByHotelsSearchBox = By.xpath("//input[@id='showNearByBox']");
	static By hotelDetailsGrid = By.xpath("//li[@class='listItem listUnit clearFix    ']");
	static By locationDetailsInfo = By.xpath("//div[@class='searchSummary']//strong");
	static By durationDetailsInfo = By.xpath("//div[@class='searchSummary']//small");

	public static void iterateHotels(WebDriver driver) throws InterruptedException, IOException {

		ArrayList<String[]> hotelDetailsList = new ArrayList<String[]>();

		WebDriverWait wait = new WebDriverWait(driver, 30);

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(nearByHotelsSearchBox));

		int i = 0;

		while (element.isDisplayed()) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			System.out.println("scrolled");
			i++;
			if (i > 400)
				break;
		}

		String hotelDetailsHeaderArray[] = { "Hotel Name", "Location", "Original Price", "Available Price",
				"Additional Taxes" };

		hotelDetailsList.add(hotelDetailsHeaderArray);

		// fetch all the li emelemts - all hotels in a list

		ArrayList<WebElement> hotelList = (ArrayList<WebElement>) driver.findElements(hotelDetailsGrid);

		System.out.println("hotelList size " + hotelList.size());

		String xpath = "(//li[@class='listItem listUnit clearFix    '])";

		// iterate through the hotel details fetched from webpage

		int j = 1;

		for (WebElement webElement : hotelList) {

			String hotelArray[] = new String[5];

			hotelArray[0] = webElement
					.findElement(By.xpath(xpath + "[" + j + "]//li[@class='info']//a[@class='hotelDetails']")).getText()
					.toString();

			hotelArray[1] = webElement
					.findElement(By.xpath(xpath + "[" + j + "]//li[@class='info']//small[@class='areaName truncate']"))
					.getText().toString().replace("map", "");
			try {
				hotelArray[2] = "";
				hotelArray[2] = webElement
						.findElement(By
								.xpath("(" + xpath + "[" + j + "]//li[@class='rate ']//small[@class='strikeOut'])[2]"))
						.getText().toString();
			} catch (org.openqa.selenium.NoSuchElementException e) {
				System.out.println(driver.getTitle());
			}

			try {
				hotelArray[3] = "";
				hotelArray[3] = webElement
						.findElement(By.xpath(
								"(" + xpath + "[" + j + "]//li[@class='rate ']//strong[@class='price-wrapper'])[2]"))
						.getText().toString();
				hotelArray[3] = hotelArray[3].replace(hotelArray[2], "");
			} catch (org.openqa.selenium.NoSuchElementException e) {
				System.out.println(driver.getTitle());
			}
			try {
				hotelArray[4] = "";
				hotelArray[4] = webElement
						.findElement(
								By.xpath(xpath + "[" + j + "]//li[@class='rate ']//small[@class='additionalTaxes']"))
						.getText().toString();
				hotelArray[4] = hotelArray[4].replace("+ Taxes", "");
			} catch (org.openqa.selenium.NoSuchElementException e) {
				System.out.println(driver.getTitle());
			}
			hotelDetailsList.add(hotelArray);

			j++;

		}

		printHotelDetails(hotelDetailsList);
		
		String locationDetails = driver.findElement(locationDetailsInfo).getText().toString();
		String durationDetails = driver.findElement(durationDetailsInfo).getText().toString();

		ExcelFunctions.createAndWriteHotelDetailsExcel(hotelDetailsList, locationDetails, durationDetails);
	}

	public static void printHotelDetails(ArrayList<String[]> hotelDetailsList) {

		for (String[] strings : hotelDetailsList) {

			for (String string : strings) {

				System.out.print(string.toString() + "  ");

			}

			System.out.println();

		}

	}
}
