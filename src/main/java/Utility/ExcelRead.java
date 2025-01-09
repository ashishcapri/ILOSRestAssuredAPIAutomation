package Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;

public class ExcelRead {

	public static List<Map<String, Object>> getExcelData(String filePath, String sheetName) throws Exception {
		InputStream is = null;
		List<Map<String, Object>> dateList = new ArrayList<Map<String, Object>>();
		try {
			is = new FileInputStream(filePath);
			Workbook workbook = getWorkbook(is);
			Sheet sheet = null;
			sheet = workbook.getSheet(sheetName);
			dateList = readExcelForSheet(sheet);

		} catch (Exception e) {
			System.err.println("exception caught while parsing excel sheet, msg: " + e.getMessage());
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dateList;

	}

	public static Workbook getWorkbook(InputStream inputStream) {
		Workbook workbook = null;
		Workbook wb;
		try {
			wb = WorkbookFactory.create(inputStream);
			return wb;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	/**
	 * Read excel for sheet.
	 *
	 * @param sheet the sheet
	 * @return the list
	 */
	@SuppressWarnings({ "incomplete-switch", "unchecked" })
	public static List<Map<String, Object>> readExcelForSheet(Sheet sheet) throws Exception {

		Iterator<Row> iterator = sheet.iterator();
		List<String> header = getHeaders(sheet);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int count = 0;
		JSONObject jsonObject = null;
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			int column = 0;
			jsonObject = new JSONObject();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				switch (cell.getCellType()) {
				case STRING:
					if (count != 0) {
						if (cell.getStringCellValue().equalsIgnoreCase("null")) {
							String nullValue = null;
							jsonObject.put(header.get(column), nullValue);
						} else {
							jsonObject.put(header.get(column), cell.getStringCellValue());
						}
					}

					break;
				case BOOLEAN:

					if (count != 0) {
						jsonObject.put(header.get(column), cell.getBooleanCellValue());

					}

					break;

				case NUMERIC:
					if (count != 0) {

						String stringCellValue = "" + (Double) cell.getNumericCellValue();
						try {
							DataFormatter df = new DataFormatter();
							stringCellValue = df.formatCellValue(cell);
						} catch (Exception e) {
							System.err.println("error durring formating");
						}
						jsonObject.put(header.get(column), stringCellValue);

					}
					break;
				case BLANK:
					if (count != 0) {
						System.out.println("----header" + column + "---" + header.get(column) + "-----Value-----"
								+ cell.getStringCellValue());
						jsonObject.put(header.get(column), "");
					}
					break;
				}

				column++;
			}
			map.put(sheet.getSheetName() + "rowNum" + nextRow.getRowNum(), jsonObject);
			if (count != 0) {
				list.add(map);
			}
			count++;
		}
		return list;
	}

	/**
	 * This method is used to read from HSSCell i.e. .xls file cell.
	 * 
	 * @param evaluator
	 * @param cell
	 */
	@SuppressWarnings("incomplete-switch")
	private static String getValueFromHSSCell(Cell cell) {
		String value = null;
		try {

			switch (cell.getCellType()) {
			case BOOLEAN:
				value = String.valueOf(cell.getBooleanCellValue());
				break;
			case NUMERIC:
				value = String.valueOf(cell.getNumericCellValue());
				break;
			case STRING:
				value = cell.getStringCellValue();
				break;
			case BLANK:
				break;
			case ERROR:
				System.err.println(String.valueOf(cell.getErrorCellValue()));
				break;
			case FORMULA:
				break;
			}

		} catch (Exception e) {
			System.err.println("error in reading of HSSF cell , msg:" + e.getMessage());
		}

		return value;
	}

	/**
	 * This method is used to read headers from workbook.
	 * 
	 * @param workbook
	 * @param sheetName
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static List<String> getHeaders(Sheet sheet) throws IOException, Exception {
		// String[] headers = null;
		List<String> listHeaders = new LinkedList<String>();
		try {

			Row row = sheet.getRow(0);
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (cell != null) {
					String val = getValueFromHSSCell(cell);
					listHeaders.add(val);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("exception caught while getting headers , msg: " + e.getMessage());
			throw new Exception("exception caught while getting headers , msg: " + e.getMessage());
		} finally {
		}

		return listHeaders;
	}
}