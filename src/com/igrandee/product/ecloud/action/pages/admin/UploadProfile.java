package com.igrandee.product.ecloud.action.pages.admin;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.log4j.Logger;

import com.igrandee.product.ecloud.util.blog.BlogCommonsUtil;
import com.sun.jersey.core.header.ContentDisposition;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;



@Path("/userprofile")
public class UploadProfile {
	private final BlogCommonsUtil BLOG_COMMONS_UTIL =new BlogCommonsUtil();
	private static final Logger fileUPloadLogger = Logger.getLogger(UploadProfile.class);
	@POST
	@Path("/singlefile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response singleUpload(@Context UriInfo uriInfo, @FormDataParam("file") List<FormDataBodyPart> file){
		String obsoluteFilePath = null;
		try {
			String actualFilepath =null;
			for (int i = 0; i < file.size() ; i++) {
				
				FormDataBodyPart this_formDataBodyPartFile = file.get(i);
				ContentDisposition this_contentDispositionHeader  = this_formDataBodyPartFile.getContentDisposition();
				InputStream this_fileInputStream = this_formDataBodyPartFile.getValueAs(InputStream.class);
 				actualFilepath =  this.uploadMedia(this_fileInputStream, (FormDataContentDisposition) this_contentDispositionHeader);
				obsoluteFilePath = BLOG_COMMONS_UTIL.getObsoluteFilePath(actualFilepath);
				Thumbnails.of(new File(actualFilepath)).size(300,300).toFile(new File(actualFilepath));
			} 
		} catch (Exception e) {
			fileUPloadLogger.error("Mrddsyr "+e);
			e.printStackTrace();
		}
		return Response.ok().status(Status.OK).entity(obsoluteFilePath).build();
	}
	public String uploadMedia(InputStream inputMedia, FormDataContentDisposition fileDetail){
		String  returnURL = null;
		 try {
			  
			 returnURL = BLOG_COMMONS_UTIL.uploadFile(inputMedia,fileDetail, "/profile"); 
		} catch (Exception e) {
			if(fileUPloadLogger.isInfoEnabled()){ 
				fileUPloadLogger.error("error in singleUpload() in UploadProfile ",e);  
			}
		}
		return returnURL;
	}
}
