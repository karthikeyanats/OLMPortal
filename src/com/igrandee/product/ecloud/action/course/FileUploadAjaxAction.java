package com.igrandee.product.ecloud.action.course;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.course.CourseService;
import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/course")
public class FileUploadAjaxAction extends MasterActionSupport {

	private static final long serialVersionUID = 3627723170634085314L;
	private static Logger FileUploadlogger = Logger.getLogger(FileUploadAjaxAction.class);
	private static ResourceBundle rb; 
	
	@InjectParam
	CourseService courseServiceobj;

	@POST
	@Path("/logoUpdate")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String LogoUpdate(
			@FormDataParam("cologo") InputStream uploadedInputStream,
			@FormDataParam("cologo") FormDataContentDisposition fileDetail,
			@FormDataParam("courseidval") String courseid,
			@FormDataParam("logosizeval") String logosizeval){
		try{
			rb = ResourceBundle.getBundle("serversetup");
			String serverPath = rb.getString("serverpath");
			long freeSpace = new File(serverPath).getFreeSpace() / (1024 * 1024);
			float fisize=Float.parseFloat(logosizeval);
			if(freeSpace>fisize){
				String contpath=uploadLogo(serverPath,uploadedInputStream,getLogoFolderPath(courseid),fileDetail.getFileName());
				if(!contpath.equalsIgnoreCase("null")){
					String updatedStatus=courseServiceobj.updateCourseLogo(courseid,contpath);
					if(updatedStatus.equalsIgnoreCase("1")){
						return "upsuccess";
					}else{
						return "upfailure";
					}
				}else{
					return "nopath";
				}				
			}else{
				return "nospace";
			}	
		}
		catch(Exception e){
			if(FileUploadlogger.isInfoEnabled())	{
				FileUploadlogger.error("error in LogoUpdate() in FileUploadAjaxAction",e);
			}
			return "error";
		}		
	}
	
	@POST
	@Path("/mailattach")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String mailAttach(
			@FormDataParam("mattachment") InputStream uploadedInputStream,
			@FormDataParam("mattachment") FormDataContentDisposition fileDetail,
			@FormDataParam("attachmentsizeval") String attachmentsizeval){
		try{
			rb = ResourceBundle.getBundle("serversetup");
			String serverPath = rb.getString("serverpath");
			
			long freeSpace = new File(serverPath).getFreeSpace() / (1024 * 1024);
			float fisize=Float.parseFloat(attachmentsizeval);
			if(freeSpace>fisize){
				String contpath=uploadAttachment(serverPath,uploadedInputStream,fileDetail.getFileName());
				if(!contpath.equalsIgnoreCase("null")){
					return contpath;
				}
				else{
					return "nopath";
				}				
			}
			else{
				return "nospace";
			}	
		}
		catch(Exception e){
			if(FileUploadlogger.isInfoEnabled())	{
				FileUploadlogger.error("error in mailAttach() in FileUploadAjaxAction",e);
			}
			return "error";
		}		
	}
	
	private String uploadAttachment(String serverPath,InputStream uploadedInputStream,String filename) {
		String retfilename=null;
		try {
			String locationFolder=serverPath+"attachments";
			StringUtils.deleteWhitespace(locationFolder);
			File f1 = new File(locationFolder);
			if (!f1.exists()) {
				f1.mkdirs();
			}		 
			String changedfilename=StringUtils.deleteWhitespace("/"+filename);
			File fileToCreate = new File(f1, changedfilename);
			if (!fileToCreate.exists()) {
				fileToCreate.createNewFile();
			}
			int read = 0;
			byte[] bytes = new byte[1024];
			OutputStream out = new FileOutputStream(fileToCreate);
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			retfilename=StringUtils.deleteWhitespace("attachments"+changedfilename);
			return retfilename;
		} 
		catch (Exception e) {
			if(FileUploadlogger.isInfoEnabled())	{
				FileUploadlogger.error("error in uploadAttachment() in FileUploadAjaxAction",e);
			}
			return "null";
		}				
	}
	
	private String uploadLogo(String serverPath,InputStream uploadedInputStream,String uploadedFileLocation,String filename) {
		String retfilename=null;
		try {
			String locationFolder=serverPath+uploadedFileLocation;
			StringUtils.deleteWhitespace(locationFolder);
			File f1 = new File(locationFolder);
			if (!f1.exists()) {
				f1.mkdirs();
			}		 
			String changedfilename=StringUtils.deleteWhitespace("/" + "logo_"+(new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss").format(new java.util.Date()))+"_"+filename);
			File fileToCreate = new File(f1, changedfilename);
			if (!fileToCreate.exists()) {
				fileToCreate.createNewFile();
			}
			int read = 0;
			byte[] bytes = new byte[1024];
			OutputStream out = new FileOutputStream(fileToCreate);
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			retfilename=StringUtils.deleteWhitespace(uploadedFileLocation+changedfilename);
			
			Thumbnails.of(fileToCreate).size(300,300).toFile(fileToCreate);
			
			return retfilename;
		} 
		catch (Exception e) {
			if(FileUploadlogger.isInfoEnabled())	{
				FileUploadlogger.error("error in uploadLogo() in FileUploadAjaxAction",e);
			}
			return "null";
		}				
	}
	
	private String getLogoFolderPath(String courseid){
		try{			
			List<?> contentFolder=courseServiceobj.LogoFolder(courseid);
			String category="";
			String course="";			
			for(int i=0;i<contentFolder.size();i++){
				Map FoldertitleMap=(Map) contentFolder.get(i);
				category=""+FoldertitleMap.get("coursecategoryid");				
				course=""+FoldertitleMap.get("courseid");				
			}
			String folderPath=getAttribute("organizationid")+"/"+getAttribute("orgpersonid")+"/"+category+"/"+course+"/logo";
			return folderPath;
		}
		catch(Exception e){
			if(FileUploadlogger.isInfoEnabled())	{
				FileUploadlogger.error("error in getLogoFolderPath() in FileUploadAjaxAction",e);
			}
			return "null";
		}
	}
}
