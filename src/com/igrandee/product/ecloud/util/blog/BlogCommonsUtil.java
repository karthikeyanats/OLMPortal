/*
 * The contents of this file are subject to the terms
 * of the i-Grandee Software Technologies 
 * Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 * 
 * You can obtain a copy of the license at
 * http://www.igrandee.com/licenses
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * Copyright (c) 2014 i-Grandee Software Technologies. All rights reserved.
 
 * Redistribution and use in source and binary forms, with or without
 * modification, are strictly prohibited, And permitted only when the 
 * following conditions are met,
 * 
 * 	 - On acquired the legal permission from i-Grandee corporate office and 
 * 	   following the below listed commitments.
 * 
 * 	 - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Igrandee or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *     
 */
/*
 * ecloud-blogutil 
 * com.igrandee.products.ecloud.blog.ecloud.blogutil.util
 * BlogCommonsUtil.java
 * Created Jul 23, 2014 1:18:23 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.util.blog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sun.jersey.core.header.FormDataContentDisposition;

/**
 * @author balajichander_r
 * 
 */
@Component
@Scope("prototype")
public class BlogCommonsUtil {
	private static final Logger blogCommonsUtilLogger = Logger
			.getLogger(BlogCommonsUtil.class);

	static ResourceBundle rb = ResourceBundle.getBundle("serversetup");
	private static final String SERVER_PATH = rb.getString("serverpath");

	/**
	 * RETURNS THE CURRENT TIME STAMP
	 * 
	 * @return Timestamp
	 * @author balajichander_r
	 */
	public Timestamp getCurrentTimStamp() {
		blogCommonsUtilLogger.info("GENERATING CURRENT TIME STAMP....");
		Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
		blogCommonsUtilLogger.info("GENERATED  TIME STAMP...." + timestamp);
		return timestamp;
	}

	/**
	 * 
	 * @param uploadedInputStream
	 * @param filedetail
	 * @return uploadedFileLocation
	 * @author balajichander_r
	 */
	public String uploadFile(InputStream uploadedInputStream, FormDataContentDisposition filedetail, String rootContextFolderName) {
		String uploadedFileLocation = null;
		try {
			String servpath = SERVER_PATH;
			String fileString = rootContextFolderName+"/";
			fileString = fileString.replaceAll(" ", "");
			String fileuploadFolder = getNewFileUploadLocation(servpath, fileString);
			File path = new File(fileuploadFolder);
			if (!path.exists()) {
				path.mkdirs();
			}
			String newFileName = renameFileWithTimeStampsIncluded(filedetail.getFileName(), getCalenderStringWithRegex("-", true));
			uploadedFileLocation = fileuploadFolder + newFileName; 
			uploadedFileLocation = writeToFile(uploadedInputStream, uploadedFileLocation);  
		} catch (Exception e) {
			blogCommonsUtilLogger.error(e);
		}
		return uploadedFileLocation;
	}
	
	/**
	 * Rename Orginal file name
	 * @param uploadedInputStream
	 * @return uploadedFileLocation
	 * @author Selvarajan_j
	 */
	public String renamefile(String rootContextFolderName) {
		String uploadedFileLocation = null;
		try {
			String servpath = SERVER_PATH;
			String fileString = rootContextFolderName+"/";
			fileString = fileString.replaceAll(" ", "");
			String fileuploadFolder = getNewFileUploadLocation(servpath, fileString);
			File path = new File(fileuploadFolder);
			if (!path.exists()) {
				path.mkdirs();
			}
			String newFileName = renameFileWithTimeStampsIncluded("drawingBoard.png", getCalenderStringWithRegex("-", true));
			uploadedFileLocation = fileuploadFolder + newFileName; 
		} catch (Exception e) {
			blogCommonsUtilLogger.error(e);
		}
		return uploadedFileLocation;
	}

	/**
	 * 
	 * @param uploadedInputStream
	 * @param uploadedFileLocation
	 * @author balajichander_r
	 */
	private String writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		OutputStream out = null;String message = null;
		try {
			
				out = new FileOutputStream(new File(uploadedFileLocation));
				int read = 0;
				byte[] bytes = new byte[1024];
				out = new FileOutputStream(new File(uploadedFileLocation));
				while ((read = uploadedInputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
				message = uploadedFileLocation;
			
		} catch (IOException e) {
			blogCommonsUtilLogger.error(e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					blogCommonsUtilLogger.error(e);
				}
			}
		}
		return message;
	}

	/**
	 * 
	 * @param fileName
	 * @param appendTimeStamp
	 * @return
	 * @author balajichander_r
	 */
	private String renameFileWithTimeStampsIncluded(String fileName, String appendTimeStamp) {
		String returnFileName = null;
		try {
			String extension = fileName.split("\\.")[1];
			String slicedFileName = fileName.split("\\.")[0];
			blogCommonsUtilLogger.warn(extension);
			blogCommonsUtilLogger.warn(slicedFileName);
			returnFileName = slicedFileName + "-" + appendTimeStamp + "." + extension;
		} catch (Exception e) {
			blogCommonsUtilLogger.error(e);
		}
		blogCommonsUtilLogger.info("renamed file name  : " + returnFileName);
		return returnFileName;
	}

	/**
	 * 
	 * @param pattern
	 * @return returnString
	 * @author balajichander_r
	 */
	@SuppressWarnings("static-access")
	private String getCalenderStringWithRegex(String pattern, Boolean isTimeStampNeeded) {
		String returnString = null;
		try {

			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(calendar.YEAR);
			int month = calendar.get(calendar.MONTH) + 1;
			int date = calendar.get(calendar.DATE);
			String time = new SimpleDateFormat("HH-mm-ss-SSS").format(calendar.getTime());
			blogCommonsUtilLogger.info("BOOLEAN " + isTimeStampNeeded);
			if (isTimeStampNeeded) {
				blogCommonsUtilLogger.info("RENAMING FILE ... ");
				returnString = year + pattern + month + pattern + date + pattern + time;
			} else {
				blogCommonsUtilLogger.info("GENEARATING FILE  PATH ... ");
				returnString = year + pattern + month + pattern + date + pattern;
			}
		} catch (Exception e) {
			blogCommonsUtilLogger.error(e);
		}
		return returnString;
	}

	/**
	 * 
	 * @param serverPath
	 * @param fileString
	 * @return returnString
	 * @author balajichander_r
	 */
	private String getNewFileUploadLocation(String serverPath, String fileString) {
		String returnString = null;
		try {
			Long timestamp = getCurrentTimStamp().getTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(timestamp);
			returnString = serverPath + fileString
					+ getCalenderStringWithRegex("/", false);
			blogCommonsUtilLogger.info("file upload location  " + returnString);
		} catch (Exception e) {
			blogCommonsUtilLogger.error(e);
		}
		return returnString;
	}

	/**
	 * @param actualFilepath
	 * @return returnObsoluteFilePath
	 * @author balajichander_r
	 */
	public String getObsoluteFilePath(String actualFilepath) {
		String returnObsoluteFilePath = null;
		try {
			returnObsoluteFilePath = actualFilepath.replaceAll(SERVER_PATH, "");
			blogCommonsUtilLogger.info(returnObsoluteFilePath);
		} catch (Exception e) {
			blogCommonsUtilLogger.error(e);
		}
		return returnObsoluteFilePath;
	}

}
