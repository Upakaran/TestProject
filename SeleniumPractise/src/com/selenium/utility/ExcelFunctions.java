package com.selenium.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFunctions {

	public static void printExcelData(ArrayList<Object[]> dataList) throws IOException {
		 
		for (Object[] objects : dataList) {
			System.out.println("array length"+objects.length);
			for (int i = 0; i < objects.length; i++) {
				System.out.print(objects[i].toString() + " ");
			}
			System.out.println();
		}

	}
	
	

	public static ArrayList<Object[]> readExcel(String excelPath, String excelSheetName) {

		ArrayList<Object[]> dataList = new ArrayList<Object[]>();

		try {
			File file = new File(excelPath);
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet(excelSheetName);

			int lastRow = sheet.getLastRowNum();

			XSSFRow row;
			XSSFCell cell;

			for (int i = 1; i <= lastRow; i++) {
				row = sheet.getRow(i);
				Object[] localArr = new Object[row.getLastCellNum()-1];
				System.out.println("last cell num "+row.getLastCellNum());
				System.err.println("physical no of cells"+ row.getPhysicalNumberOfCells());
				for (int j = 1; j < row.getLastCellNum(); j++) {
					cell = row.getCell(j);
					String value = cell.getStringCellValue();
					localArr[j-1] = value;
				}
				dataList.add(localArr);
			}
			ExcelFunctions.printExcelData(dataList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	public static void createAndWriteFlightDetailsExcel(ArrayList<String []> onwardDetailList , ArrayList<String []> returnDetailList, String onwardDate, String onwardDestination, String returnDate, String returnDestination) throws IOException{
		
		File file = new File("test-output/GeneratedExcelResults/CleartripFlightSearchResults_"+onwardDestination+"_"+returnDestination+".xlsx");
	//	File file = new File("F:/CleartripTest/CleartripFlightSearchResults_"+onwardDestination+"_"+returnDestination+".xlsx");
		if(file.exists()){
			file.delete();}
			FileOutputStream fos = new FileOutputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet1 = workbook.createSheet("Onward Journey");
			XSSFSheet sheet2 = workbook.createSheet("Return Journey");
			
			
			XSSFRow row;
			XSSFCell cell;
			
			sheet1.createRow(0).createCell(0).setCellValue(onwardDate);
			sheet1.getRow(0).createCell(1).setCellValue(onwardDestination);
			
			sheet2.createRow(0).createCell(0).setCellValue(returnDate);
			sheet2.getRow(0).createCell(1).setCellValue(returnDestination);
			
			for (String [] var : onwardDetailList) {
				
				row = sheet1.createRow(sheet1.getLastRowNum()+1);
				int i = 0;
				for (String string : var) {
					cell = row.createCell(i);
					cell.setCellValue(string);
					i++;
				}
			}
			
			for (String [] var : returnDetailList) {
				row = sheet2.createRow(sheet2.getLastRowNum()+1);
				int i = 0;
				for (String string : var) {
					cell = row.createCell(i);
					cell.setCellValue(string);
					i++;
				}
			}
			
			workbook.write(fos);
			workbook.close();
			fos.close();
		
		
	}
	
	
public static void createAndWriteHotelDetailsExcel(ArrayList<String []> hotelDetailsList ,  String locationDetails, String durationDetails) throws IOException{
	
	String locationDetailsMod = locationDetails.replace("(", "").replace(")", "").replace(" ", "_").toString().trim();
		
		File file = new File("test-output/GeneratedExcelResults/CleartripHotelSearchResults_"+locationDetailsMod+".xlsx");
	//	File file = new File("F:/CleartripTest/CleartripHotelSearchResults_"+locationDetailsMod+".xlsx");
		if(file.exists()){
			file.delete();}
			FileOutputStream fos = new FileOutputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet1 = workbook.createSheet("Hotel Details");
			
			
			XSSFRow row;
			XSSFCell cell;
			
			sheet1.createRow(0).createCell(0).setCellValue(locationDetails);
			sheet1.getRow(0).createCell(1).setCellValue(durationDetails);
			
			
			for (String [] var : hotelDetailsList) {
				
				row = sheet1.createRow(sheet1.getLastRowNum()+1);
				for (String string : var) {
					if(row.getLastCellNum()==-1)
					cell = row.createCell(row.getLastCellNum()+1);
					else
						cell = row.createCell(row.getLastCellNum());
					cell.setCellValue(string);
				}
			}
			
			workbook.write(fos);
			workbook.close();
			fos.close();
		
		
	}

}
