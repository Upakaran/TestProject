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
		headerArray[1] = driver.findElement(headingDepart).getText().toString();
		headerArray[2] = "Arrive";
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
	
	
	/* to be use in iterateNonStopFlights2 method */

	static By departureLabel = By.xpath("//input[@placeholder='Departure']");
	static By onwardDateLabel2 = By.xpath("(//div[@class='fs-2 c-inherit flex flex-nowrap'])[5]");
	static By arrivalLabel = By.xpath("//input[@placeholder='Arrival']");
	static By returnDateLabel2 = By.xpath("(//div[@class='fs-2 c-inherit flex flex-nowrap'])[6]");
	static By headingVendor2 = By.xpath("((//div[@class='col'])[2]//descendant::span)[1]");
	static By headingDepart2 = By.xpath("((//div[@class='col'])[2]//descendant::span)[2]");
	static By headingDuration2 = By.xpath("((//div[@class='col'])[2]//descendant::span)[3]");
	static By headingPrice2 = By.xpath("((//div[@class='col'])[2]//descendant::span)[4]");
	static By onwardFlightDetailsGrid2 = By.xpath("//div[@data-test-attrib='onward-view']/div/child::div");
	static By returnFlightDetailsGrid2 = By.xpath("//div[@data-test-attrib='return-view']/div/child::div");

	
	
	public static void iterateNonStopFlights2(WebDriver driver) throws IOException, InterruptedException {

		try{
	
		
		String onwardDestination =driver.findElement(departureLabel).getAttribute("value").toString().replaceAll("-", "").replaceAll(",", "").trim().replaceAll("( )+", " ");
		String returnDestination = driver.findElement(arrivalLabel).getAttribute("value").toString().replaceAll("-", "").replaceAll(",", "").trim().replaceAll("( )+", " ");
		
		System.out.println(onwardDestination+" "+returnDestination);
		
		String[] depNameArray = onwardDestination.split(" ");
		String onwardDestinationLocal = "";
		for (int i = 1 ; i < depNameArray.length -1; i++) {
			onwardDestinationLocal = onwardDestinationLocal + depNameArray[i]+" ";
		}
		onwardDestinationLocal = onwardDestinationLocal.trim();
		String[] arrNameArray = returnDestination.split(" ");
		String returnDestinationLocal = "";
		for (int i = 1 ; i < arrNameArray.length -1; i++) {
			returnDestinationLocal = returnDestinationLocal + arrNameArray[i]+" ";
		}
		returnDestinationLocal = returnDestinationLocal.trim();
		String onwardDestinationFinal = onwardDestinationLocal+"-"+returnDestinationLocal;
		String returnDestinationFinal = returnDestinationLocal+"-"+onwardDestinationLocal;
		
		
		System.out.println(onwardDestinationFinal+" "+returnDestinationFinal);

		String onwardDate = driver.findElement(onwardDateLabel2).getText().toString();
		String returnDate = driver.findElement(returnDateLabel2).getText().toString();

		// populate all the headers

		ArrayList<String[]> onwardDetailList = new ArrayList<String[]>();
		ArrayList<String[]> returnDetailList = new ArrayList<String[]>();
		String[] headerArray = new String[5];
		headerArray[0] = driver.findElement(headingVendor2).getText().toString();
		headerArray[1] = driver.findElement(headingDepart2).getText().toString();
		headerArray[2] = "Arrival";
		headerArray[3] = driver.findElement(headingDuration2).getText().toString();
		headerArray[4] = driver.findElement(headingPrice2).getText().toString();

		// adding headers to onward and return details list
		onwardDetailList.add(headerArray);
		returnDetailList.add(headerArray);

		ArrayList<WebElement> onwardFlightList = (ArrayList<WebElement>) driver.findElements(onwardFlightDetailsGrid2);

		String list1xPath = "(//div[@data-test-attrib='onward-view']/div/child::div";

		int i = 1;
		for (WebElement webElement : onwardFlightList) {

			String[] eachDetail = new String[5];
			eachDetail[0] = webElement.findElement(By.xpath(list1xPath + "[" + i + "]" + "//img)"))
					.getAttribute("alt").toString();
			eachDetail[1] = webElement.findElement(By.xpath(list1xPath + "[" + i + "]" + "//p)[1]"))
					.getText().toString();
			eachDetail[2] = webElement.findElement(By.xpath(list1xPath + "[" + i + "]" + "//p)[2]"))
					.getText().toString();
			eachDetail[3] = webElement.findElement(By.xpath(list1xPath + "[" + i + "]" + "//p)[3]"))
					.getText().toString();
			eachDetail[4] = webElement.findElement(By.xpath(list1xPath + "[" + i + "]" + "//p)[6]"))
					.getText().toString();
			i++;
			onwardDetailList.add(eachDetail);
		}

		ArrayList<WebElement> returnFlightList = (ArrayList<WebElement>) driver.findElements(returnFlightDetailsGrid2);

		String list2xPath = "(//div[@data-test-attrib='return-view']/div/child::div";

		int j = 1;
		for (WebElement webElement : returnFlightList) {

			String[] eachDetail = new String[5];
			eachDetail[0] = webElement.findElement(By.xpath(list2xPath + "[" + j + "]" + "//img)"))
					.getAttribute("alt").toString();
			eachDetail[1] = webElement.findElement(By.xpath(list2xPath + "[" + j + "]" + "//p)[1]"))
					.getText().toString();
			eachDetail[2] = webElement.findElement(By.xpath(list2xPath + "[" + j + "]" + "//p)[2]"))
					.getText().toString();
			eachDetail[3] = webElement.findElement(By.xpath(list2xPath + "[" + j + "]" + "//p)[3]"))
					.getText().toString();
			eachDetail[4] = webElement.findElement(By.xpath(list2xPath + "[" + j + "]" + "//p)[6]"))
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
				onwardDestinationFinal, returnDate, returnDestinationFinal);
		}
		
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
