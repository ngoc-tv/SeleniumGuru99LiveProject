package Test;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	XSSFWorkbook workbook;
	XSSFSheet sheet;
	
	public ExcelUtils(String excelPath, String sheetName) {
		try {
			workbook = new XSSFWorkbook(excelPath);
			sheet = workbook.getSheet(sheetName);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int getRowCount() {
		int rowCount=0;
		try {
		    rowCount =	sheet.getPhysicalNumberOfRows();
		    System.out.println("number row:" + rowCount);
		} catch (Exception exp) {
			System.out.println(exp.getCause());
			System.out.println(exp.getMessage());
			exp.printStackTrace();
		}
		return rowCount;
	}
	
	public int getColCount() {
		int colCount=0;
		try {
		    colCount =	sheet.getRow(0).getPhysicalNumberOfCells();
		    System.out.println("number Col:" + colCount);
		} catch (Exception exp) {
			System.out.println(exp.getCause());
			System.out.println(exp.getMessage());
			exp.printStackTrace();
		}
		return colCount;
	}
	
	public String getCellDataString(int rowNum, int colNum) {
		String cellData = null;
		try {
		    cellData =	sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
		    //System.out.println("Cell Data ["+rowNum+"]"+"["+colNum+"]:" + cellData);
		} catch (Exception exp) {
			System.out.println(exp.getCause());
			System.out.println(exp.getMessage());
			exp.printStackTrace();
		}
		return cellData;
	}
	public double getCellDataNumber(int rowNum, int colNum) {
		double cellData = 0;
		try {
		    cellData =	sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
		    System.out.println("Cell Data ["+rowNum+"]"+"["+colNum+"]:" + cellData);
		} catch (Exception exp) {
			System.out.println(exp.getCause());
			System.out.println(exp.getMessage());
			exp.printStackTrace();
		}
		return cellData;
	}
	
}
