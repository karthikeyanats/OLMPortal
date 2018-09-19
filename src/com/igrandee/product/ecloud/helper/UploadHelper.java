/**
 * 
 */
package com.igrandee.product.ecloud.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;


/**
 * @author thangadurai_m
 *
 */
public class UploadHelper {
	Logger logger = Logger.getLogger(UploadHelper.class);
	/**
	 * @author thangadurai_m
	 * @param uploadFile  ex : your upload file 
	 * @param uploadFileName ex : Upload file like this 'sample.png'
	 * @param resourcePath   ex : serversetup properties key name 
	 * @param manualPath	 ex : /productname/ or your custom name
	 * @return ex : Actual db file path like this "/productname/.../dynamically generate filename"
	 * @see <b>Note :<br></b> Required serversetup.properties file. Location like this our application resources folder.<br>
	 * 		<b>Should file name like this serversetup.properties</b> 
	 * 		 
	 */
	@SuppressWarnings("resource")
	public String singleFileUpload(File uploadFile, String uploadFileName, String resourcePath, String manualPath){
		ResourceBundle bundle = null;
		
		
		String dBFilePath  = "";
		String fileExt = "";
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			bundle = ResourceBundle.getBundle("serversetup");
			resourcePath = bundle.getString(resourcePath).trim();
			java.util.Date now = new java.util.Date();
 			String downloadPath = resourcePath+File.separator+manualPath+File.separator; 
			File f = new File(downloadPath);
			if(!f.exists()) {
				f.mkdirs();  
			}
			fileExt			= "doc_"+(new SimpleDateFormat("yyyyMMddHHmmss").format(now))+uploadFileName.substring(uploadFileName.lastIndexOf("."));
 			dBFilePath = manualPath+File.separator+fileExt;
 			File fileToCreate = new File(f, fileExt); 
			inputChannel = new FileInputStream(uploadFile).getChannel();
			outputChannel = new FileOutputStream(fileToCreate).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
 		} catch (IOException e) {
				if(logger.isDebugEnabled()){
					logger.error("ImageUploadHelper class singleFileUpload exception--->",e);
				}
		}
		finally{
			try {
				inputChannel.close();
				outputChannel.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				if(logger.isDebugEnabled()){
					logger.error("ImageUploadHelper class singleFileUpload finally exception--->",e);
				}
			}
		}
	
		return dBFilePath;
	}
}
