package com.selenium.pagenavigation;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.selenium.utility.CommonFunctions;

public class RoundTripFlightSearchPage {

	WebDriver driver;
	
	/* used in FlightSearch method */
	
	By RoundTripRadioBtn = By.xpath("//strong[text()='Round trip']");
	By SelectFromCity = By.xpath("(//input[@title='Any worldwide city or airport'])[1]");
	By SelectToCity = By.xpath("(//input[@title='Any worldwide city or airport'])[2]");
	By departDateBox = By.xpath("(//input[@title='Depart date'])");
	By returnDateBox = By.xpath("(//input[@title='Return date'])");
	By datePicker1 = By.xpath("(//i[@class='icon ir datePicker'])[1]");
	By datePicker2 = By.xpath("(//i[@class='icon ir datePicker'])[2]");
	By datePickerCurrentMonth = By.xpath("(//span[@class='ui-datepicker-month'])[1]");
	By datePickerCurrentYear = By.xpath("(//span[@class='ui-datepicker-year'])[1]");
	By datePickerNextMonthArrow = By.xpath("//a[@class='nextMonth ']");
	By selectAdults = By.id("Adults");
	By selectChildren =  By.id("Childrens");
	By selectInfants = By.id("Infants");
	By SearchBtn = By.id("SearchBtn");
	By nonStopFlighBtn = By.xpath("(//a[text()='Non-stop '])[2]");
	By nonStopCheckBox = By.xpath("((//div[@class='pt-3'])[1]//descendant::div[@class='flex flex-start p-relative flex-middle'])[1]//span");
	By modifySearchLink = By.xpath("(//a[@id='modifySearchLink'])[2]");
	
	public String setDriverRoundTripFlightSearch(WebDriver driver) {
		this.driver = driver;
		return ("WebDriver set up successfully");
	}

	
	public void FlightSearch(String fromCity, String toCity, String onwardDate, String returnDate, String Adult,
			String Children, String Infant) throws InterruptedException, IOException {
		// data manupulations
		
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

			String onwardDay = onwardDate.split("/")[0];
			if (onwardDay.startsWith("0")) {
				onwardDay = onwardDay.replace("0", "");
			}

			String onwardMonth = onwardDate.split("/")[1];
			onwardMonth = dateMap.get(onwardMonth);
			String onwardYear = onwardDate.split("/")[2];

			System.out.println(onwardDay + " " + onwardMonth + " " + onwardYear);

			String returnDay = returnDate.split("/")[0];
			if (returnDay.startsWith("0")) {
				returnDay = returnDay.replace("0", "");
			}
			String returnMonth = returnDate.split("/")[1];
			returnMonth = dateMap.get(returnMonth);
			String returnYear = returnDate.split("/")[2];
			System.out.println(returnDay + " " + returnMonth + " " + returnYear);

			// select round trip radio button
			driver.findElement(RoundTripRadioBtn).click();

			Thread.sleep(3000);

			// select from city
			WebElement fromTextbox = driver.findElement(SelectFromCity);
			fromTextbox.clear();
			Thread.sleep(2000);
			fromTextbox.sendKeys(fromCity);
			Thread.sleep(5000);
			fromTextbox.sendKeys(Keys.ENTER);
			Thread.sleep(1000);

			// select to city
			WebElement toTextbox = driver.findElement(SelectToCity);
			toTextbox.clear();
			Thread.sleep(2000);
			toTextbox.sendKeys(toCity);
			Thread.sleep(5000);
			toTextbox.sendKeys(Keys.ENTER);

			Thread.sleep(1000);

			// clear previous onward journey date
			driver.findElement(departDateBox).clear();
			Thread.sleep(1000);
			// select journey date

			driver.findElement(datePicker1).click();
			Thread.sleep(2000);

			String month = driver.findElement(datePickerCurrentMonth).getText();
			String year = driver.findElement(datePickerCurrentYear).getText();

			while (!(month.equalsIgnoreCase(onwardMonth) && year.equalsIgnoreCase(onwardYear))) {
				driver.findElement(datePickerNextMonthArrow).click();

				System.out.println(month + year);
				month = driver.findElement(datePickerCurrentMonth).getText();
				year = driver.findElement(datePickerCurrentYear).getText();
				Thread.sleep(2000);
			}

			//customized x-path to select the given date
			
			driver.findElement(By.xpath("(//a[@class='ui-state-default ' and text()=" + onwardDay + "])[1]")).click();
			Thread.sleep(2000);

			// clear previous return journey date
			driver.findElement(returnDateBox).clear();
			Thread.sleep(1000);
			// select return journey date

			driver.findElement(datePicker2).click();
			Thread.sleep(2000);

			month = driver.findElement(datePickerCurrentMonth).getText();
			year = driver.findElement(datePickerCurrentYear).getText();

			while (!(month.equalsIgnoreCase(returnMonth) && year.equalsIgnoreCase(returnYear))) {
				driver.findElement(datePickerNextMonthArrow).click();

				System.out.println(month + year);
				month = driver.findElement(datePickerCurrentMonth).getText();
				year = driver.findElement(datePickerCurrentYear).getText();
				Thread.sleep(2000);
			}

			//customized x-path to select the given date
			
			driver.findElement(By.xpath("(//a[@class='ui-state-default ' and text()=" + returnDay + "])[1]")).click();
			Thread.sleep(2000);

			// select no of adult passengers
			Select adultDropDown = new Select(driver.findElement(selectAdults));
			adultDropDown.selectByValue(Adult);
			Thread.sleep(2000);

			// select no of children passengers
			Select childDropDown = new Select(driver.findElement(selectChildren));
			childDropDown.selectByValue(Children);
			Thread.sleep(2000);

			// select no of infant passengers
			Select infantDropDown = new Select(driver.findElement(selectInfants));
			infantDropDown.selectByValue(Infant);
			Thread.sleep(2000);

			driver.findElement(SearchBtn).click();
			Thread.sleep(1000);
			
			WebElement element = null;
			try{
				 element = driver.findElement(modifySearchLink);
			}
			
			catch(NoSuchElementException e){
				e.getMessage();
			}
			
						
			if (element!=null){
					System.out.println("inside 1st condition for round trip flights search");
					WebElement element1 = CommonFunctions.myExplicitWait("(//a[text()='All flights'])[2]" , driver);
					Thread.sleep(3000);
					element1.click();
					Thread.sleep(3000);
					driver.findElement(nonStopFlighBtn).click();
					Thread.sleep(5000);
					RoundTripFlightSearchDetailsPage.iterateNonStopFlights(driver);
			}
			
			else {
					System.out.println("inside 2nd condition for round trip flights search");
					WebElement element2 = CommonFunctions.myExplicitWait("//label[@class='radio w-100p radio__light']//p[text()='All airlines']" , driver);
					Thread.sleep(3000);
					element2.click();
					Thread.sleep(3000);
					driver.findElement(nonStopCheckBox).click();
					Thread.sleep(5000);
					RoundTripFlightSearchDetailsPage.iterateNonStopFlights2(driver);
			}
	}
	
}
