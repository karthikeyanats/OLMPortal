package com.igrandee.product.ecloud.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import com.igrandee.product.ecloud.model.Courselecture;
import com.igrandee.product.ecloud.model.Coursesection;
import com.igrandee.product.ecloud.model.Lecturecontent;
import com.igrandee.product.ecloud.model.Metainfo;
import com.igrandee.product.ecloud.model.Note;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.service.course.CourseLectureService;
import com.igrandee.product.ecloud.service.course.LectureContentService;
import com.igrandee.product.ecloud.service.course.MetainfoService;
import com.igrandee.product.ecloud.service.course.NotesService;
import com.igrandee.product.ecloud.util.blog.BlogCommonsUtil;
import com.lowagie.text.pdf.PdfReader;
import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

@Path("/file")
public class UploadFileService extends MasterActionSupport{

	private static final long serialVersionUID = 1L;

	static final BlogCommonsUtil BLOG_COMMONS_UTIL = new BlogCommonsUtil();

	@InjectParam
	LectureContentService lectureContentService;

	@InjectParam
	CourseLectureService CourseLectureServiceobj;

	@InjectParam
	MetainfoService MetainfoServiceobj;

	@InjectParam
	NotesService NotesServiceobj;

	public static ResourceBundle resourceBundle;

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)

	public String uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("courselectureidhidden") String courselectureid,
			@FormDataParam("filesizeval") String filesizeval,
			@FormDataParam("enstatus") String enstatus){
		try{			
			resourceBundle = ResourceBundle.getBundle("serversetup");
			String serverPath = resourceBundle.getString("serverpath");
			long freeSpace = new File(serverPath).getFreeSpace() / (1024 * 1024);
			float fisize=Float.parseFloat(filesizeval);
			if(freeSpace>fisize){
				String contPath=null;
				String duration=null;
				FilenameUtils.getExtension(fileDetail.getFileName());				
				String folderpath=getContentFolderPath(courselectureid);
				contPath=uploadContent(uploadedInputStream,folderpath,fileDetail.getFileName());
				String fename=fileDetail.getFileName();
				String ext=FilenameUtils.getExtension(fename);
				if(ext.equalsIgnoreCase("pdf")){
					String locationFolder=serverPath+contPath;
					PdfReader pdfReader=new PdfReader(locationFolder);
					if(pdfReader.isTampered()){
						contPath=null;
						duration="0 pages";
					}else{
						Integer pages = pdfReader.getNumberOfPages();
						duration=pages.toString()+" pages";
					}

				}
				else if(ext.equalsIgnoreCase("flv")){
					duration=null;
				}
				else{
					duration=null;
				}
				/*}*/
				if(contPath!=null){
					Courselecture courselecture = new Courselecture();
					courselecture.setCourselectureid(Integer.parseInt(courselectureid));
					Lecturecontent lecturecontent = new Lecturecontent();
					lecturecontent.setDuration(duration);
					lecturecontent.setContentstatus("A");

					lecturecontent.setContentpath(contPath);				
					lecturecontent.setContenttype(contPath.substring(contPath.lastIndexOf(".")+1));
					lecturecontent.setContent(null);
					lecturecontent.setCourselecture(courselecture);
					String lc=lectureContentService.save(lecturecontent).toString();
					return lc;	
				}
				else{
					return "error";
				}
			}
			else{
				return "nospace";
			}			
		}
		catch(Exception e){
			e.printStackTrace();
			return "exception";
		}		
	}
	private String getContentFolderPath(String courselectureid){
		try{
			List<?> contentFolder=CourseLectureServiceobj.loadLecturePath(Integer.parseInt(courselectureid));
			String category="";
			String course="";
			String section="";
			String lecture="";
			for(int i=0;i<contentFolder.size();i++){
				@SuppressWarnings("rawtypes")
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

	public String uploadContent(InputStream uploadedInputStream,String uploadedFileLocation,String filename) {
		String retfilename=null;
		try {
			resourceBundle = ResourceBundle.getBundle("serversetup");
			String serverPath = resourceBundle.getString("serverpath");
			String locationFolder=serverPath+uploadedFileLocation;
			StringUtils.deleteWhitespace(locationFolder);
			File f1 = new File(locationFolder);
			if (!f1.exists()) {
				f1.mkdirs();
			}		
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			String changedfilename=StringUtils.deleteWhitespace("/file_"+(calendar.getTimeInMillis())+"."+FilenameUtils.getExtension(filename));
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
			return retfilename;	
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}				
	}

	@SuppressWarnings("deprecation")
	@POST
	@Path("/addNewLecture" )
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String addNewLecture(
			@FormDataParam("coursereference") InputStream uploadedInputStream,
			@FormDataParam("coursereference") FormDataContentDisposition fileDetail,
			@FormDataParam("hidcoursesectionid") String coursesectionid,
			@FormDataParam("courselecturetitle") String courselecturetitle,
			@FormDataParam("coursekeywords") String keywords,
			@FormDataParam("coursesummary") String summary,
			@FormDataParam("courseweblinks") String weblinks,

			@FormDataParam("coursediagram") InputStream uploadeddiagram,
			@FormDataParam("coursediagram") FormDataContentDisposition diagramfileDetail,

			@FormDataParam("coursetable") InputStream uploadedtable,
			@FormDataParam("coursetable") FormDataContentDisposition tablefileDetail,

			@FormDataParam("coursemap") InputStream uploadedmap,
			@FormDataParam("coursemap") FormDataContentDisposition mapfileDetail,

			@FormDataParam("courseboxiem") String boxitem,
			@FormDataParam("courseprojidea") String projectideas,
			@FormDataParam("courseactivity") String activity){
		try	{
			Courselecture courselectureobj=new Courselecture();
			Coursesection coursesectionobj=new Coursesection();
			Metainfo metainfoobj=new Metainfo();
			Courselecture clobj=new Courselecture();
			coursesectionobj.setCoursesectionid(Integer.parseInt(coursesectionid));
			courselectureobj.setCourselecturestatus("A");
			courselectureobj.setCourselecturetitle(java.net.URLDecoder.decode(courselecturetitle));
			courselectureobj.setCoursesection(coursesectionobj);
			String newlecture=CourseLectureServiceobj.save(courselectureobj).toString();
			clobj.setCourselectureid(Integer.parseInt(newlecture));
			metainfoobj.setCourselecture(clobj);
			metainfoobj.setMetainfostatus("A");
			MetainfoServiceobj.save(metainfoobj).toString();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return "success";

	}


	@POST
	@Path("/notesScratchPad")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFileUploadedUri(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("file") List<FormDataBodyPart> files,
			@FormDataParam("drawobject") String drawobject,
			@FormDataParam("courselectureid") String courselectureid){
		try {
			String foldertobouploaded = getContentFolderPath(courselectureid);
			foldertobouploaded = foldertobouploaded+"/"+getAttribute("orgpersonid")+"/scratchpad/";
			String actualFilePath =saveimage(drawobject,foldertobouploaded);
			Note Noteobj =  new Note();
			Courselecture Courselectureobj = new Courselecture();
			Orgperson Orgpersonobj = new Orgperson();

			Noteobj.setNotesdescription("null");
			Noteobj.setCreateddate(new Timestamp(new Date().getTime()));
			Noteobj.setNotespath(actualFilePath);
			Noteobj.setNotesstatus("R");
			Noteobj.setNotetype("S");
			Courselectureobj.setCourselectureid(Integer.parseInt(courselectureid));
			Orgpersonobj.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			Noteobj.setOrgperson(Orgpersonobj);
			Noteobj.setCourselecture(Courselectureobj);
			String savedNotesResult=NotesServiceobj.save(Noteobj).toString();
			if(savedNotesResult!=null){
				String notesid = NotesServiceobj.saveNotePath(Integer.parseInt(savedNotesResult),actualFilePath);
				if(notesid.equalsIgnoreCase("updated")){
					return Response.status(Status.OK).entity(Status.OK).build();
				}else{
					return Response.status(Status.OK).entity(Status.PRECONDITION_FAILED).build();
				}
			}else{
				return Response.status(Status.OK).entity(Status.CONFLICT).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.OK).entity(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	
	private String saveimage(String drawObject,String foldertobouploaded){
		String dir=null;
		try{
			resourceBundle = ResourceBundle.getBundle("serversetup");
			String serverPath = resourceBundle.getString("serverpath");
			String locationFolder=serverPath+foldertobouploaded;
			StringUtils.deleteWhitespace(locationFolder);
			File f1 = new File(locationFolder);
			if (!f1.exists()) {
				f1.mkdirs();
			}	

			byte[] decodedBytes = DatatypeConverter.parseBase64Binary(drawObject);
			BufferedImage bfi = ImageIO.read(new ByteArrayInputStream(decodedBytes));    

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			String changedfilename=StringUtils.deleteWhitespace("scratch_"+(calendar.getTimeInMillis())+".png");

			File outputfile = new File(locationFolder+changedfilename);

			if (!outputfile.exists()) {
				outputfile.createNewFile();
			}
			ImageIO.write(bfi , "png", outputfile);
			bfi.flush();

			dir=foldertobouploaded + changedfilename;
			
		}catch(Exception e)
		{  e.printStackTrace();        
		}
		return dir;
	}

}