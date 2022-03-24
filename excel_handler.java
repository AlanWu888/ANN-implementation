package xor_problem;

import java.io.File;  
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;

public class excel_handler {
	static int NUMB_OF_EPOCHS = 1000;
	static String DIR_TRAIN = "C:\\Users\\alanw\\OneDrive\\Desktop\\AI coursework\\Spreadsheets\\training data\\training.xlsx";
	static String DIR_VALID = "";
	static String DIR_TEST = "";
	static int MIN = 33970;
	static int MAX_FLOW = 300;
	
	public static void main(String[] args) {
		/*

		System.out.println(normaliseData(123));
		System.out.println(deNormaliseDate(normaliseData(123)));	// test normalise function
		
		System.out.println(data_sets.TRAINING_DATA[0][0][1]);		// gets the date - will have to denormalise this
		System.out.println(data_sets.TRAINING_DATA[2][0][1]);		// gets the date - will have to denormalise this
		*/
		
		getDataSet(DIR_TRAIN);
	}
	
	private static HashMap<Double, Double> getDataSet(String fileDir) {
		HashMap<Double, Double> dataSet = new HashMap<Double, Double>();
		try {  
			File file = new File("C:\\Users\\alanw\\OneDrive\\Desktop\\AI coursework\\Spreadsheets\\training data\\training.xlsx");   //creating a new file instance  
			FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(6);     	//creating a Sheet object to retrieve object  
			Iterator<Row> itr = sheet.iterator();    	//iterating over excel file  
			while (itr.hasNext()) {  
				Row row = itr.next();  
				//String key = String.valueOf(row.getCell(0).getNumericCellValue());  			// generate id for day (show as int)
				Double key = new Double(normaliseKey(row.getCell(0).getNumericCellValue()));
				Double value = new Double(normaliseData(row.getCell(1).getNumericCellValue()));				// read flow of skelton that day
				dataSet.put(key, value);
			}  
		} catch(Exception e) {  
			e.printStackTrace();  
		}
		//Print keys and values
		for (Double i : dataSet.keySet()) {
		  System.out.println("{{1," + i + "," + dataSet.get(i) + "},{" + dataSet.get(i) + "}},");
		}

		return dataSet;
	}
	
	public static void getOuts(double[] result, int setLength) {
		double[][] skeltonResults = new double[setLength][2];
		for (int i=0; i<setLength; i++) {
			skeltonResults[i][0] = deNormaliseKey(winter_sets.WINTER_TRAINING[0][0][1]);
			skeltonResults[i][1] = deNormaliseData(result[i]);
		}
		System.out.println(Arrays.deepToString(skeltonResults));
	}
	
	public static void writeResults(double[] result, int setLength) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("results");
		sheet.setColumnWidth(0, 8560);
		sheet.setColumnWidth(1, 8560);
		
		Row header = sheet.createRow(0);
		
		// create table worksheet headers
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Date");	
		headerCell = header.createCell(1);
		headerCell.setCellValue("Skelton");
		
		// fill rows
		for(int i=0; i<setLength-1; i++) {
			Row row = sheet.createRow(i+1);
			Cell cell = row.createCell(0);
			cell.setCellValue(deNormaliseKey(winter_sets.WINTER_TRAINING[i][0][1]));
			
			cell = row.createCell(1);
			cell.setCellValue(deNormaliseData(result[i+1]));
		}
		
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";
		try {
			FileOutputStream outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);
			workbook.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static double normaliseKey(double key) {
		double returnValue = ((key - MIN) / 2000) + 0.1;
		return returnValue;
	}
	
	private static double deNormaliseKey(double key) {
		double returnValue = ((key - 0.1) * 2000) + MIN;
		return returnValue;
	}
		
	private static double normaliseData(double flow) {
		double returnValue = ((flow/MAX_FLOW)*0.8)+0.1;	
		return returnValue;
	}
	
	private static double deNormaliseData(double flow) {
		double returnValue = ((flow - 0.1) / 0.8) * MAX_FLOW;
		return returnValue;
	}
}  

