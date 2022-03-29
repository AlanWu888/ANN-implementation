package skelton_ai;

import java.io.File;  
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class excel_handler {
	static String DIR_TRAIN = "C:\\Users\\alanw\\OneDrive\\Desktop\\AI coursework\\Spreadsheets\\training data\\training.xlsx";
	static String DIR_VALID = "C:\\Users\\alanw\\OneDrive\\Desktop\\AI coursework\\Spreadsheets\\validation data\\validation.xlsx";
	static String DIR_TEST = "C:\\Users\\alanw\\OneDrive\\Desktop\\AI coursework\\Spreadsheets\\test data\\test.xlsx";
	static int MIN = 33970;
	static int MAX_FLOW = 300;
	
	public static void main(String[] args) {
		//getData(DIR_TRAIN);
		getData(DIR_VALID);
		//getData(DIR_TEST);
		// System.out.println();
		// System.out.println(getData(DIR_TRAIN).get(0).get(0));
	}
	
	private static void getData(String fileDir) {
		try {  
			File file = new File(fileDir);   //creating a new file instance  
			FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0);     	//creating a Sheet object to retrieve object  
			Iterator<Row> itr = sheet.iterator();    	//iterating over excel file  
			int counter = 0;
			while (itr.hasNext()) {  
				Row row = itr.next();  
				//String key = String.valueOf(row.getCell(0).getNumericCellValue());  			// generate id for day (show as int)
				Double key = new Double(normaliseKey(row.getCell(0).getNumericCellValue()));
				Double value = new Double(normaliseData(row.getCell(1).getNumericCellValue()));				// read flow of skelton that day
				System.out.println("{{" + key + "," + value + "},{" + value + "}},");
				counter++;
				
			}  
		} catch(Exception e) {  
			e.printStackTrace();  
		}
	}
	
	public static void getOuts(double[] result, int setLength) {
		double[][] skeltonResults = new double[setLength][3];
		for (int i=0; i<setLength; i++) {
			skeltonResults[i][0] = deNormaliseKey(data_sets.TRAINING_DATA[0][0][0]);	// gets the key
			skeltonResults[i][1] = deNormaliseData(result[i]);
		}
		System.out.println(Arrays.deepToString(skeltonResults));
	}
	
	public static void writeErrors(List<Double> rmse) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("errors");
		sheet.setColumnWidth(0, 8560);
		sheet.setColumnWidth(1, 8560);
		
		Row header = sheet.createRow(0);
		
		// create table worksheet headers
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("epoch");	
		headerCell = header.createCell(1);
		headerCell.setCellValue("error");
		
		// fill rows
		for(int i=0; i<rmse.size()-1; i++) {
			Row row = sheet.createRow(i+1);
			Cell cell = row.createCell(0);
			cell.setCellValue(i);
			
			cell = row.createCell(1);
			cell.setCellValue(rmse.get(i+1));
		}
		
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + "errors.xlsx";
		try {
			FileOutputStream outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);
			workbook.close();
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static void writeTest(double[] result) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("test");
		sheet.setColumnWidth(0, 8560);
		sheet.setColumnWidth(1, 8560);
		
		Row header = sheet.createRow(0);
		
		// create table worksheet headers
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Date");	
		headerCell = header.createCell(1);
		headerCell.setCellValue("Skelton");
		
		// fill rows
		for(int i=0; i<result.length-1; i++) {
			Row row = sheet.createRow(i+1);
			Cell cell = row.createCell(0);
			cell.setCellValue(deNormaliseKey(data_sets.TEST_DATA[i][0][0]));
			
			cell = row.createCell(1);
			cell.setCellValue(deNormaliseData(result[i+1]));
		}
		
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + "test.xlsx";

		try {
			FileOutputStream outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);
			workbook.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeValidation(double[] result) {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("validation");
		sheet.setColumnWidth(0, 8560);
		sheet.setColumnWidth(1, 8560);
		
		Row header = sheet.createRow(0);
		
		// create table worksheet headers
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("Date");	
		headerCell = header.createCell(1);
		headerCell.setCellValue("Skelton");
		
		// fill rows
		for(int i=0; i<result.length-1; i++) {
			Row row = sheet.createRow(i+1);
			Cell cell = row.createCell(0);
			cell.setCellValue(deNormaliseKey(data_sets.VALIDATION_DATA[i][0][0]));
			
			cell = row.createCell(1);
			cell.setCellValue(deNormaliseData(result[i+1]));
		}
		
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + "validation.xlsx";

		try {
			FileOutputStream outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);
			workbook.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeResults(double[] result) {
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
		for(int i=0; i<result.length-1; i++) {
			Row row = sheet.createRow(i+1);
			Cell cell = row.createCell(0);
			cell.setCellValue(deNormaliseKey(data_sets.TRAINING_DATA[i][0][0]));
			
			cell = row.createCell(1);
			cell.setCellValue(deNormaliseData(result[i+1]));
		}
		
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + "results.xlsx";

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

