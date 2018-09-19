package com.igrandee.product.ecloud.action.pages.admin;

import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class NewExcelParser
{

    private static Logger logger = Logger.getLogger(NewExcelParser.class.getName());
    private static final int MAX_ROW_COUNT = 10000;

	public static String parseAndDumpToDB(String fileName, InputStream stream, boolean isDataMigration) throws Exception
	{
	        return parseAndDumpToDB(fileName, stream, isDataMigration, null, true, null);
	}


	public static String parseAndDumpToDB(String fileName, InputStream stream, boolean isDataMigration, String module, boolean dumpData) throws Exception
	{
		return parseAndDumpToDB(fileName, stream, isDataMigration, module, dumpData, null);
	}

	public static String parseAndDumpToDB(String fileName, InputStream stream, boolean isDataMigration, String module, boolean dumpData, Long userId) throws Exception
	{
		return parseAndDumpToDB(fileName, stream, isDataMigration, module, dumpData, null,null);
	}

	public static String parseAndDumpToDB(String fileName, InputStream stream, boolean isDataMigration, String module, boolean dumpData, Long userId,HashMap entityMapString) throws Exception
	{
		Date startTime = new Date();
	 
		if (fileName.endsWith(".xls") || fileName.endsWith(".XLS"))
		{
			String str = parseXLSAndDumpToDB(stream, isDataMigration, module, dumpData, userId,entityMapString);
			Date endTime = new Date();
			logger.info("Starting import for file = " + fileName + " at " + startTime.getTime());
			logger.info("Ending import for file = " + fileName + " at " + endTime.getTime());
			logger.info("Import took = " + (endTime.getTime() - startTime.getTime()));
			return str;
		}

 		throw new Exception();
	}

	private static String parseXLSAndDumpToDB(InputStream stream, boolean isDataMigration) throws Exception
	{
		return parseXLSAndDumpToDB(stream, isDataMigration, null, true, null,null);
	}


	private static String parseXLSAndDumpToDB(InputStream stream, boolean isDataMigration, String module, boolean dumpData, Long userId,HashMap entityMapString) throws Exception
	{
		String tableName = "";
 		boolean isOwnerMapped=false;
		int ownerheader=-1;
		if(module.equals("Contacts") && entityMapString!=null)
		{
			ArrayList colmaps = (ArrayList)entityMapString.get("Contacts");
			for(int i=0;i<colmaps.size();i++)
			{
				String[] mapping = (String[])colmaps.get(i);
				if(mapping[0].equals("CrmSalesEntity") && mapping[1].equals("SMOWNERID"))
				{
					isOwnerMapped=true;
					ownerheader = Integer.parseInt(mapping[2]);
					break;
				}
			}
		}
		HSSFWorkbook workBook = getWorkBook(stream);
		Vector headers = getXLSHeaders(workBook);
		logger.log(Level.FINER, "Headers in the file are: " + headers);
  		logger.log(Level.FINER, "Temp table created for XLS with name: " + tableName);
		if (dumpData)
		{
 		long count = 0;
 		int sheets = workBook.getNumberOfSheets();
 		for (int i = 0; i < sheets; i++)
		{
			HSSFSheet sheet = workBook.getSheetAt(i);
			int rows = sheet.getPhysicalNumberOfRows();
			if (rows <= 0)
			{
				continue;
			}

			if (!isDataMigration && rows > MAX_ROW_COUNT + 1)
			{
 				throw new Exception();
			}

			if (module != null && module.equals("Contacts"))
			{
				String licenseType ="Free"; //LicenseUtil.getLicenseType();
				if (licenseType != null && licenseType.equals("Free"))
				{
					long totalContacts =0; //CommonDBService.getTotalNoOfContacts();
					if ((rows - 1) > (50 - totalContacts))
					{
						rows = 50 - (int) totalContacts + 1;
					}
				}
			}
  			for (int r = 1; r < rows; r++)
			{

				System.out.println("inside commented for loop");
				
			  }
 				count++;
				if (count > 1000)
				{
 					count = 0;
 					Thread.yield();
					System.gc();
				}
			}
 		}
		return tableName;
	}

	public static Vector getXLSHeaders(HSSFWorkbook workBook) throws Exception
	{
		Vector headerVec = new Vector();
		int sheets = workBook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++)
		{
			Vector headers = getXLSHeaders(workBook, i);
			for (int j = 0; j < headers.size(); j++)
			{
				Object header = headers.get(j);
				if (!headerVec.contains(header))
				{
					headerVec.add(header);
				}
			}
		}
		return headerVec;
	}

	public static Vector getXLSHeaders(HSSFWorkbook workBook, int numOfSheet) throws Exception
	{
		Vector headers = new Vector();
		HSSFSheet sheet = workBook.getSheetAt(numOfSheet);
		int rows = sheet.getPhysicalNumberOfRows();
		if (rows > 0)
		{
			HSSFRow row = sheet.getRow(0);
			int cells = row.getPhysicalNumberOfCells();
			for (short cell = 0; cell < cells; cell++)
			{
				HSSFCell cellObj = row.getCell(cell);
				Object value = getValueOfCell(cellObj,"CHAR");
				if (value != null)
				{
					value = value.toString().trim();
				}
				if (value != null && !headers.contains(value))
				{
					headers.add(value);
				}
			}
		}

		return headers;
	}

	public static String getXLSHeader(HSSFSheet sheet, short column) throws Exception
	{
		HSSFRow row = sheet.getRow(0);
		HSSFCell cell = row.getCell(column);
		String header =(String) getValueOfCell(cell,"CHAR");
		if (header != null)
		{
			header = header.trim();
		}
		return header;
	}

	public static Object getStrValueOfCell(HSSFCell cell,String dataType) throws Exception
	{
 		String subVal=null;
		if (cell != null)
		{
			switch (cell.getCellType())
			{
				case HSSFCell.CELL_TYPE_NUMERIC:
					subVal = "" + cell.getNumericCellValue();
					if (subVal != null && subVal.trim().length()!=0)
					{
						subVal = NumberFormat.getNumberInstance().parse(subVal).toString();
					}
					break;
				case HSSFCell.CELL_TYPE_STRING:
					subVal = ""+cell.getStringCellValue();
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					subVal = ""+cell.getBooleanCellValue();
					break;
				default:
				subVal=null;
			}
			if(subVal==null || subVal.trim().length()==0)
			{
				return null;
			}

		}
		return subVal;
	}

	public static Object getValueOfCell(HSSFCell cell,String dataType) throws Exception
	{
		Object value = null;
		String subVal=null;
		if (cell != null)
		{
			switch (cell.getCellType())
			{
				case HSSFCell.CELL_TYPE_NUMERIC:
					subVal = "" + cell.getNumericCellValue();
					if (subVal != null && subVal.trim().length()!=0)
					{
						subVal = NumberFormat.getNumberInstance().parse(subVal).toString();
					}
					break;
				case HSSFCell.CELL_TYPE_STRING:
					subVal = ""+cell.getStringCellValue();
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					subVal = ""+cell.getBooleanCellValue();
					break;
				default:
					subVal=null;
			}
			if(subVal==null || subVal.trim().length()==0)
			{
				return null;
			}
			if (dataType.equals("BIGINT"))
			{
				value =new Long(subVal);
			}
			else if (dataType.equals("INTEGER"))
			{
				value = new Integer(subVal);
			}
			else if (dataType.equals("FLOAT"))
			{
				value =new Float(subVal);
			}
			else if (dataType.equals("BOOLEAN"))
			{
				value=subVal;
			}
			else if (dataType.equals("DOUBLE"))
			{
				value = new Double(subVal);
			}
			else if (dataType.equals("CHAR"))
			{
				value = new String(subVal);
			}
			else if (dataType.equals("DATE"))
			{
				double d = cell.getNumericCellValue();
				value = HSSFDateUtil.getJavaDate(d);
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				value=df.format(value);
			}
			else if (dataType.equals("DATETIME"))
			{
				double d = cell.getNumericCellValue();
				value = HSSFDateUtil.getJavaDate(d);
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm");
				value=df.format(value);
			}
		}
		return value;
	}
	
	public static Object getValueOfXCell(XSSFCell cell,String dataType) throws Exception
	{
		Object value = null;
		String subVal=null;
		if (cell != null)
		{
			switch (cell.getCellType())
			{
				case XSSFCell.CELL_TYPE_NUMERIC:
					subVal = "" + cell.getNumericCellValue();
					if (subVal != null && subVal.trim().length()!=0)
					{
						subVal = NumberFormat.getNumberInstance().parse(subVal).toString();
					}
					break;
				case XSSFCell.CELL_TYPE_STRING:
					subVal = ""+cell.getStringCellValue();
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					subVal = ""+cell.getBooleanCellValue();
					break;
				default:
					subVal=null;
			}
			if(subVal==null || subVal.trim().length()==0)
			{
				return null;
			}
			if (dataType.equals("BIGINT"))
			{
				value =new Long(subVal);
			}
			else if (dataType.equals("INTEGER"))
			{
				value = new Integer(subVal);
			}
			else if (dataType.equals("FLOAT"))
			{
				value =new Float(subVal);
			}
			else if (dataType.equals("BOOLEAN"))
			{
				value=subVal;
			}
			else if (dataType.equals("DOUBLE"))
			{
				value = new Double(subVal);
			}
			else if (dataType.equals("CHAR"))
			{
				value = new String(subVal);
			}
			else if (dataType.equals("DATE")){
				double d = cell.getNumericCellValue();
				value = HSSFDateUtil.getJavaDate(d);
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				value=df.format(value);
			}
			else if (dataType.equals("DATETIME"))
			{
				double d = cell.getNumericCellValue();
				value = HSSFDateUtil.getJavaDate(d);
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm");
				value=df.format(value);
			}
		}
		return value;
	}

	public static HSSFWorkbook getWorkBook(InputStream stream) throws Exception
	{
		POIFSFileSystem fs = new POIFSFileSystem(stream);
		HSSFWorkbook workBook = new HSSFWorkbook(fs);
		return workBook;
	}

    private static Vector fillVector(Vector vec, Object val)
    {
        for (int i = 0; i < vec.capacity(); i++)
        {
            vec.add(val);
        }
        return vec;
    } 
}
