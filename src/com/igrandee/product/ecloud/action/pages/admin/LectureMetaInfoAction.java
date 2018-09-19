package com.igrandee.product.ecloud.action.pages.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Metainfo;
import com.igrandee.product.ecloud.model.Metainfodescription;
import com.igrandee.product.ecloud.service.Pages.admin.LectureMetaInfoService;
import com.igrandee.product.ecloud.viewmodel.metainfo.DescVM;
import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/metadesc")
public class LectureMetaInfoAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;

	@InjectParam
	LectureMetaInfoService lmis;

	@InjectParam
	Metainfo metainfo;

	@InjectParam
	Metainfodescription metadesc;

	public static ResourceBundle resourceBundle;

	@GET
	@Path("/{metainfoid}/{descriptiontype}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response descriptionContentByMetaid(@PathParam("metainfoid") String metainfoid,
			@PathParam("descriptiontype") String descriptiontype){
		try{
			List<?> descList=lmis.getMetaDescription(metainfoid, descriptiontype);
			if(descList.size()>0){
				return Response.status(Status.OK).entity(descList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/desc/{lectureid}/{descriptiontype}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response descriptionContentBylecture(@PathParam("lectureid") String lectureid,
			@PathParam("descriptiontype") String descriptiontype){
		try{
			List<?> descList=lmis.getMetaDescription(lectureid, descriptiontype);
			if(descList.size()>0){
				return Response.status(Status.OK).entity(descList).build();
			}else{
				return Response.status(Status.OK).entity(Status.NO_CONTENT).build();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cruds(DescVM descVM){
		try{
			String updatetype=descVM.getType();
			if(updatetype.equalsIgnoreCase("save")){
				metainfo.setMetainfoid(Integer.parseInt(descVM.getMetainfoid()));
				metadesc.setMetainfo(metainfo);
				metadesc.setDescription(descVM.getDescription().replaceAll("http://|https://", ""));
				metadesc.setOriginalfilename(null);
				metadesc.setDescriptiontype(descVM.getDescriptiontype());
				metadesc.setStatus(descVM.getStatus());
				lmis.save(metadesc);
				return Response.status(Status.OK).entity(Status.OK).build();
			}else{
				lmis.updateMetaDescription(descVM.getDescid(), descVM.getDescription());
				return Response.status(Status.OK).entity(Status.OK).build();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Path("/upload")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crudsupload(
			@FormDataParam("infofile") InputStream uploadedInputStream,
			@FormDataParam("infofile") FormDataContentDisposition fileDetail,
			@FormDataParam("metainfoid") String metainfoid,
			@FormDataParam("desctype") String desctype){
		try{
			String contPath=null;
			String filepath=getContentFolderPath(metainfoid);
			contPath=uploadContent(uploadedInputStream,filepath,fileDetail.getFileName(),desctype);
			if(contPath!=null){
				metainfo.setMetainfoid(Integer.parseInt(metainfoid));
				metadesc.setMetainfo(metainfo);
				metadesc.setOriginalfilename(fileDetail.getFileName());
				metadesc.setDescription(contPath);
				metadesc.setDescriptiontype(desctype);
				metadesc.setStatus("A");
				lmis.save(metadesc);
				return Response.status(Status.OK).entity(Status.OK).build();
			}else{
				return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
			}

		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}	

	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cruds(@PathParam("id") String id){
		try{
			lmis.deleteMetaDescription(id);
			return Response.status(Status.OK).entity(Status.OK).build();
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private String getContentFolderPath(String metainfoid){
		try{
			List<?> contentFolder=lmis.loadMataInfoPath(metainfoid);
			String category="";
			String course="";
			String section="";
			String lecture="";
			for(int i=0;i<contentFolder.size();i++){
				Map FoldertitleMap=(Map) contentFolder.get(i);
				category=""+FoldertitleMap.get("coursecategoryid");
				course=""+FoldertitleMap.get("courseid");
				section=""+FoldertitleMap.get("coursesectionid");
				lecture=""+FoldertitleMap.get("courselectureid");			
			}
			String folderPath=getAttribute("organizationid")+"/"+getAttribute("orgpersonid")+"/"+category+"/"+course+"/"+section+"/"+lecture;
			return folderPath;			
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	private String uploadContent(InputStream uploadedInputStream,String filepath,String filename,String desctype) {
		String retfilename=null;
		try {
			resourceBundle = ResourceBundle.getBundle("serversetup");
			String serverPath = resourceBundle.getString("serverpath");
			String locationFolder=serverPath+filepath+"/"+desctype;
			StringUtils.deleteWhitespace(locationFolder);
			
			File f1 = new File(locationFolder);
			if (!f1.exists()) {
				f1.mkdirs();
			}		 
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			String typeval=(desctype.substring(0, 3)).toUpperCase();
			String changedfilename=StringUtils.deleteWhitespace("/"+typeval+"_"+(calendar.getTimeInMillis())+"."+FilenameUtils.getExtension(filename));
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
			retfilename=StringUtils.deleteWhitespace(filepath+"/"+desctype+changedfilename);
			return retfilename;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}				
	}
}