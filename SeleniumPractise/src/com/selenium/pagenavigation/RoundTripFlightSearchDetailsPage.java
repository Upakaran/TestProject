package com.selenium.pagenavigation;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.selenium.utility.ExcelFunctions;

public class RoundTripFlightSearchDetailsPage {

	/* to be use in iterateNonStopFlights method */

	static By onwardDestinationLabel = By.xpath("(//div[@class='legHeaderWrap RTStickyWrapper'])[1]/div/strong");
	static By onwardDateLabel = By.xpath("(//div[@class='legHeaderWrap RTStickyWrapper'])[1]/div/span");
	static By returnDestinationLabel = By.xpath("(//div[@class='legHeaderWrap RTStickyWrapper'])[2]/div/strong");
	static By returnDateLabel = By.xpath("(//div[@class='legHeaderWrap RTStickyWrapper'])[2]/div/span");
	static By headingVendor = By.xpath("(//ul[@class='inline clearFix'])[1]/li[@class='vendor']/a");
	static By headingDepart = By.xpath("(//ul[@class='inline clearFix'])[1]/li[@class='depart']/a");
	static By headingArrive = By.xpath("(//ul[@class='inline clearFix'])[1]/li[@class='arrive disappear']/a");
	static By headingDuration = By.xpath("(//ul[@class='inline clearFix'])[1]/li[@class='duration']/a");
	static By headingPrice = By.xpath("(//ul[@class='inline clearFix'])[1]/li[@class='price']/a");
	static By onwardFlightDetailsGrid = By.xpath("((//ul[@class='listView flights'])[2])/li");
	static By returnFlightDetailsGrid = By.xpath("((//ul[@class='listView flights'])[3])/li");

	public static void iterateNonStopFlights(WebDriver driver) throws IOException, InterruptedException {

		String onwardDestination = driver.findElement(onwardDestinationLabel).getText().toString();

		onwardDestination = onwardDestination.replace("?", "->");

		String onwardDate = driver.findElement(onwardDateLabel).getText().toString();

		String returnDestination = driver.findElement(returnDestinationLabel).getText().toString();

		returnDestination = returnDestination.replace("?", "->");

		String returnDate = driver.findElement(returnDateLabel).getText().toString();

		// populate all the headers

		ArrayList<String[]> onwardDetailList = new ArrayList<String[]>();
		ArrayList<String[]> returnDetailList = new ArrayList<String[]>();
		String[] headerArray = new String[5];
		headerArray[0] = driver.findElement(headingVendor).getText().toString();
		headerArray[2] = "Arrive";
		headerArray[1] = driver.findElement(headingDepart).getText().toString();
	//	headerArray[2] = driver.findElement(headingArrive).getText().toString();
		headerArray[3] = driver.findElement(headingDuration).getText().toString();
		headerArray[4] = driver.findElement(headingPrice).getText().toString();

		// adding headers to onward and return details list
		onwardDetailList.add(headerArray);
		returnDetailList.add(headerArray);

		ArrayList<WebElement> onwardFlightList = (ArrayList<WebElement>) driver.findElements(onwardFlightDetailsGrid);

		String list1xPath = "((//ul[@class='listView flights'])[2])/li";

		int i = 1;
		for (WebElement webElement : onwardFlightList) {

			String[] eachDetail = new String[5];
			eachDetail[0] = webElement.findElement(By.xpath(list1xPath + "[" + i + "]" + "//span[@class='truncate']"))
					.getText().toString();
			eachDetail[1] = webElement.findElement(By.xpath(list1xPath + "[" + i + "]" + "//th[@class='depart']"))
					.getText().toString();
			eachDetail[2] = webElement.findElement(By.xpath(list1xPath + "[" + i + "]" + "//th[@class='arrive']"))
					.getText().toString();
			eachDetail[3] = webElement.findElement(By.xpath(list1xPath + "[" + i + "]" + "//th[@class='duration']"))
					.getText().toString();
			eachDetail[4] = webElement.findElement(By.xpath(list1xPath + "[" + i + "]" + "//th[@class='price ']"))
					.getText().toString();
			i++;
			onwardDetailList.add(eachDetail);
		}

		ArrayList<WebElement> returnFlightList = (ArrayList<WebElement>) driver.findElements(returnFlightDetailsGrid);

		String list2xPath = "((//ul[@class='listView flights'])[3])/li";

		int j = 1;
		for (WebElement webElement : returnFlightList) {

			String[] eachDetail = new String[5];
			eachDetail[0] = webElement.findElement(By.xpath(list2xPath + "[" + j + "]" + "//span[@class='truncate']"))
					.getText().toString();
			eachDetail[1] = webElement.findElement(By.xpath(list2xPath + "[" + j + "]" + "//th[@class='depart']"))
					.getText().toString();
			eachDetail[2] = webElement.findElement(By.xpath(list2xPath + "[" + j + "]" + "//th[@class='arrive']"))
					.getText().toString();
			eachDetail[3] = webElement.findElement(By.xpath(list2xPath + "[" + j + "]" + "//th[@class='duration']"))
					.getText().toString();
			eachDetail[4] = webElement.findElement(By.xpath(list2xPath + "[" + j + "]" + "//th[@class='price ']"))
					.getText().toString();
			j++;
			returnDetailList.add(eachDetail);

		}
		System.out.println(
				"onward Flight length " + onwardFlightList.size() + " onward detail list " + onwardDetailList.size());
		printFlightDetailData(onwardDetailList);
		System.out.println(
				"return flight length " + returnFlightList.size() + " return detail list " + returnDetailList.size());
		printFlightDetailData(returnDetailList);
		ExcelFunctions.createAndWriteFlightDetailsExcel(onwardDetailList, returnDetailList, onwardDate,
				onwardDestination, returnDate, returnDestination);

	}

	public static void printFlightDetailData(ArrayList<String[]> dataList) throws IOException {

		for (String[] objects : dataList) {

			for (int i = 0; i < objects.length; i++) {
				System.out.print(objects[i].toString() + " ");
			}
			System.out.println();
		}

	}

}
