package com.selenium.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CVSFunctions {

	public static ArrayList<Object[]> ReadCSV(String CSV_Filepath) {

		ArrayList<Object[]> dataList = new ArrayList<Object[]>();

		try {

			String separator = "\",\"";
			String line = "";
			File file = new File(CSV_Filepath);
			FileReader reader = new FileReader(file);
			BufferedReader buff_reader = new BufferedReader(reader);

			while ((line = buff_reader.readLine()) != null) {
				
				if(line.startsWith("--")){  // condition for not executing a particular data set marked with --
					continue;
				}
				else 
				{
					Object[] row = line.split(separator);
					for (int i = 0; i < row.length; i++) {
						row[i] = ((String) row[i]).replace("\"", "");
					}
					dataList.add(row);
				}

			}
			System.out.println("data read from CSV file");
			buff_reader.close();

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;

	}

}
