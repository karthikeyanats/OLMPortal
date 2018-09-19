package com.igrandee.product.ecloud.action.organization;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Board;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Coursecategory;
import com.igrandee.product.ecloud.model.Medium;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.Price;
import com.igrandee.product.ecloud.model.StandardLevel;
import com.igrandee.product.ecloud.service.course.CourseListService;

public class OrganizationCourseInsertAction extends MasterActionSupport {

	private static Logger organizationcourseinsertlogger = Logger.getLogger(OrganizationCourseInsertAction.class);

	private static final long serialVersionUID = 1L;

	@Inject
	CourseListService courseListService;

	@Autowired
	private Orgperson orgperson;

	@Autowired
	private Course course;

	@Autowired
	private Coursecategory coursecategory;

	@Autowired
	private Price priceobj;

	public static ResourceBundle resourceBundle;

	private String coursecategoryid;
	private String coursecategoryname;

	private String courseid;
	private String coursetitle;
	private String coursesubtitle;
	private String coursegoal;
	private String coursedescription;
	private String courselogo;
	private String coursepromevideopath;
	private String instructoinallevel;
	private String intendedaudience;
	private String coursestatus;

	private String priceid;
	private String price;
	private String pricestatus;

	private File coursechangelogo;
	private String coursechangelogoFileName;
	
	private Board board;
	private Medium medium;
	private StandardLevel standardlevel;
	private String boardid;
	private String mediumid;
	private String standardid;

	/**
	 * @author seenivasagan_p
	 * Method to create New Course and return its generated courseid
	 */
 	@Action(value = "/newOrgCourse",results = {@Result(name = "SUCCESS", type = "redirectAction", params = {"namespace", "/", "actionName", "orgmanagecourse" ,"courseid","${courseid}" })})
  	public String newCourseCreate(){ 
		try	{
			String personid = getAttribute("orgpersonid");
			course.setCoursetitle(coursetitle);
			course.setCoursestatus("D");
			course.setCoursedescription(coursedescription);
			priceobj.setPrice("Free");
			priceobj.setPricestatus("A");
			priceobj.setCourse(course);
			HashSet<Price> prices=new HashSet<Price>();
			prices.add(priceobj);
			course.setPrices(prices);
			java.util.Date date = new java.util.Date();
			course.setCoursecreateddate(new Timestamp(date.getTime()));
			orgperson.setOrgpersonid(Integer.parseInt(personid));
			course.setOrgperson(orgperson);
			courseid = courseListService.save(course).toString();
			setCourseid(courseid); 
		}
		catch(Exception e){
			if(organizationcourseinsertlogger.isInfoEnabled()){
				organizationcourseinsertlogger.error("error in newCourseCreate() in ManageCourseAction",e);
			}			
		}
		return "SUCCESS";
	}

	/**
	 * @author seenivasagan_p
	 * Class to save course's basic information
	 */
/*	@Action(value="/managecourseinfo")
	public void managecourseinfo(){
		try	{
			System.out.println("Update BAsic Info");
			course.setCourseid(Integer.parseInt(courseid));
			course.setCoursetitle(coursetitle);
			course.setCoursesubtitle(coursesubtitle);
			course.setCoursegoal(coursegoal);			
			course.setCoursestatus(coursestatus);
			course.setCoursedescription(coursedescription);
			course.setCourselogo(courselogo);
			course.setCoursepromevideopath(coursepromevideopath);
			course.setInstructoinallevel(instructoinallevel);
			course.setIntendedaudience(intendedaudience);
			coursecategory.setCoursecategoryid(Integer.parseInt(coursecategoryid));
			course.setCoursecategory(coursecategory);
			board=new Board();
			board.setBoardid(Integer.parseInt(boardid));
			course.setBoard(board);
			medium=new Medium();
			medium.setMediumid(Integer.parseInt(mediumid));
			course.setMedium(medium);
			standardlevel=new StandardLevel();
			standardlevel.setStandardlevelid(Integer.parseInt(standardid));
			course.setStandardlevel(standardlevel);
			if(priceid==null || priceid=="" || priceid.isEmpty() || priceid.equals("")){
			}
			else{
				priceobj.setPriceid(Integer.parseInt(priceid));	
			}
			if(price.equalsIgnoreCase("0")){
				priceobj.setPrice("Free");
			}
			else{
				priceobj.setPrice(price);	
			}	
			priceobj.setPricestatus("A");
			priceobj.setCourse(course);
			HashSet<Price> prices=new HashSet<Price>();
			prices.add(priceobj);
			String orgpersonid = getAttribute("orgpersonid");
			orgperson.setOrgpersonid(Integer.parseInt(orgpersonid));
			course.setOrgperson(orgperson);
			course.setPrices(prices);
			course.setCoursecategory(coursecategory);
			courseListService.update(course);
			checkobj().toJsonResponse("1");
		}
		catch(Exception e){
			if(organizationcourseinsertlogger.isInfoEnabled()){
				organizationcourseinsertlogger.error("error in managecourseinfo() in ManageCourseAction",e);
			}	
		}
	}*/

	@Action(value="/getCourseLogoImage")
	public void getCourseLogoImage(){
		try{
			List<?> courseLogoImage=courseListService.loadCourseLogo(Integer.parseInt(courseid));
			checkobj().toJsonResponse(courseLogoImage);
		}
		catch(Exception e){
			if(organizationcourseinsertlogger.isInfoEnabled()){
				organizationcourseinsertlogger.error("error in getCourseLogoImage() in ManageCourseAction",e);
			}
		}
	}

	@Action(value="/UploadCourseLogo")
	public void UploadCourseLogo(HttpServletRequest request, HttpServletResponse response){
		try{
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {
				// Create a factory for disk-based file items
				FileItemFactory factory = new DiskFileItemFactory();
				// Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload(factory);
				try {
					// Parse the request
					List /* FileItem */ items = upload.parseRequest(request);
					Iterator iterator = items.iterator();
					while (iterator.hasNext()) {
						FileItem item = (FileItem) iterator.next();
						if (!item.isFormField()) {
							String fileName = item.getName(); 
							resourceBundle = ResourceBundle.getBundle("serversetup");
							String serverPath = resourceBundle.getString("serverpath");
							File path = new File(serverPath + "/uploads");
							if (!path.exists()) {
								boolean status = path.mkdirs();
							}
							File uploadedFile = new File(path + "/" + fileName);
							item.write(uploadedFile);
						}
					}
				} catch (FileUploadException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		catch(Exception e){
			if(organizationcourseinsertlogger.isInfoEnabled()){
				organizationcourseinsertlogger.error("error in getCourseLogoImage() in ManageCourseAction",e);
			}
		}
	}

	@Action(value = "courseLogoUpdate", interceptorRefs = {
			@InterceptorRef("fileUpload"), @InterceptorRef("defaultStack") }, params = {
			"maximumSize",
			"10485760",
			"allowedTypes",
	"image/png,image/gif,image/jpeg,image/pjpeg,image/jpg" })
	public void courseLogoUpdate(){
		try{
			String uploadedFile=fileCreateUpload(coursechangelogo, coursechangelogoFileName);
			checkobj().toJsonResponse(uploadedFile);
		}
		catch(Exception e){
			if(organizationcourseinsertlogger.isInfoEnabled()){
				organizationcourseinsertlogger.error("error in courseLogoUpdate() in ManageCourseAction",e);
			}
		}
	}

	/**
	 * @param file
	 * @param UploadFileName
	 * @throws IOException
	 */
	public String fileCreateUpload(File file, String UploadFileName) throws IOException{
		String filename="";
		try{
			resourceBundle = ResourceBundle.getBundle("serversetup");
			String serverPath = resourceBundle.getString("serverpath");
			String msgpath = serverPath;
			int year = Calendar.getInstance().get(Calendar.YEAR);
			String FileExt = null;
			String current_date_time = null;
			java.util.Date date = new java.util.Date();
			current_date_time = date.getTime() + "";
			File f = new File(msgpath + "/" + year);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileExt = UploadFileName.substring(UploadFileName.lastIndexOf("."));
			String fileNamed = "media_" + current_date_time + FileExt;
			File fileToCreate = new File(f, fileNamed);
			FileUtils.copyFile(file, fileToCreate);
			filename= year + "/" + fileNamed;
		}
		catch(Exception e){
			if(organizationcourseinsertlogger.isInfoEnabled()){
				organizationcourseinsertlogger.error("error in fileCreateUpload() in ManageCourseAction",e);
			}
		}
		return filename;		
	}



	/*getters and setters*/
	public String getCourseid() {
		return courseid;
	}
	public void setCourseid(String courseid) {
		this.courseid = courseid;
	}
	public String getCoursetitle() {
		return coursetitle;
	}
	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
	}
	public String getCoursesubtitle() {
		return coursesubtitle;
	}
	public void setCoursesubtitle(String coursesubtitle) {
		this.coursesubtitle = coursesubtitle;
	}
	public String getCoursegoal() {
		return coursegoal;
	}
	public void setCoursegoal(String coursegoal) {
		this.coursegoal = coursegoal;
	}
	public String getCoursedescription() {
		return coursedescription;
	}
	public void setCoursedescription(String coursedescription) {
		this.coursedescription = coursedescription;
	}
	public String getCourselogo() {
		return courselogo;
	}
	public void setCourselogo(String courselogo) {
		this.courselogo = courselogo;
	}
	public String getCoursepromevideopath() {
		return coursepromevideopath;
	}
	public void setCoursepromevideopath(String coursepromevideopath) {
		this.coursepromevideopath = coursepromevideopath;
	}
	public String getCoursecategoryid() {
		return coursecategoryid;
	}
	public void setCoursecategoryid(String coursecategoryid) {
		this.coursecategoryid = coursecategoryid;
	}
	public String getCoursecategoryname() {
		return coursecategoryname;
	}
	public void setCoursecategoryname(String coursecategoryname) {
		this.coursecategoryname = coursecategoryname;
	}
	public String getPriceid() {
		return priceid;
	}
	public void setPriceid(String priceid) {
		this.priceid = priceid;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getInstructoinallevel() {
		return instructoinallevel;
	}
	public void setInstructoinallevel(String instructoinallevel) {
		this.instructoinallevel = instructoinallevel;
	}
	public String getIntendedaudience() {
		return intendedaudience;
	}
	public void setIntendedaudience(String intendedaudience) {
		this.intendedaudience = intendedaudience;
	}
	public File getCoursechangelogo() {
		return coursechangelogo;
	}
	public void setCoursechangelogo(File coursechangelogo) {
		this.coursechangelogo = coursechangelogo;
	}
	public String getCoursechangelogoFileName() {
		return coursechangelogoFileName;
	}
	public void setCoursechangelogoFileName(String coursechangelogoFileName) {
		this.coursechangelogoFileName = coursechangelogoFileName;
	}
	public String getCoursestatus() {
		return coursestatus;
	}
	public void setCoursestatus(String coursestatus) {
		this.coursestatus = coursestatus;
	}

	public String getPricestatus() {
		return pricestatus;
	}

	public void setPricestatus(String pricestatus) {
		this.pricestatus = pricestatus;
	}

	public String getBoardid() {
		return boardid;
	}

	public void setBoardid(String boardid) {
		this.boardid = boardid;
	}

	public String getMediumid() {
		return mediumid;
	}

	public void setMediumid(String mediumid) {
		this.mediumid = mediumid;
	}

	public String getStandardid() {
		return standardid;
	}

	public void setStandardid(String standardid) {
		this.standardid = standardid;
	}
	
}