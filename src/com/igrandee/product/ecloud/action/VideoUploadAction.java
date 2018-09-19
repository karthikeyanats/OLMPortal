package com.igrandee.product.ecloud.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import com.igrandee.product.ecloud.service.course.CourseService;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/video")
public class VideoUploadAction extends MasterActionSupport{
	
	private static final long serialVersionUID = 1L;
	
	@InjectParam
	CourseService courseServiceobj;
	
	@Path("/{courseid}")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadVideo(@FormDataParam("file")InputStream inputStream,@FormDataParam("file")FormDataContentDisposition formDataContentDisposition,@PathParam("courseid")int courseid){
		ResourceBundle resourceBundle = ResourceBundle.getBundle("serversetup");
		String basepath = resourceBundle.getString("serverpath");
		String subpath=getLogoFolderPath(courseid)+ "/promovideo";
		String finalfile=StringUtils.deleteWhitespace("/file_"+(new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss").format(new java.util.Date()))+"_"+formDataContentDisposition.getFileName());
		String fileName=basepath+subpath+finalfile;
		int read=0;
		File folder=new File(basepath+subpath);
		byte[] bytes=new byte[1024];
		try {
			if(!folder.exists()){
			   folder.mkdirs();
			}
			OutputStream outputStream=new FileOutputStream(fileName);
			while((read=inputStream.read(bytes))!=-1){
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
			outputStream.close();
			return Response.ok().entity(subpath+finalfile).build();
		} catch (FileNotFoundException e) {
 			e.printStackTrace();
			return Response.status(Status.NOT_ACCEPTABLE).entity(Status.NOT_ACCEPTABLE).build();
		}
		catch(IOException e){
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
				
	}
	
	private String getLogoFolderPath(int courseid){
		try{			
			List<?> contentFolder=courseServiceobj.LogoFolder(""+courseid);
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
			e.printStackTrace();
			return "null";
		}
	}

}
