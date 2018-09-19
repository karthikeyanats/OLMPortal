package com.igrandee.product.ecloud.action.course;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.course.CoursecertificateService;

public class CourseCertificateAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;
	private static Logger CourseCertificateActionlogger = Logger.getLogger(CourseCertificateAction.class);

	@Inject
	CoursecertificateService CoursecertificateServiceobj;
	private String courseenrollmentid;
	
	
	@Action(value="/loadCertificateDetails")
	public void loadCertificateDetails(){
		try	{
			List<?> certificateDetails=CoursecertificateServiceobj.getCertificateDetails(Integer.parseInt(courseenrollmentid));
			checkobj().toJsonResponse(certificateDetails);
		}
		catch(Exception e){
			if(CourseCertificateActionlogger.isInfoEnabled()){
				CourseCertificateActionlogger.error("error in loadCertificateDetails() in CourseCertificateAction",e);
			}
		}
	}

	public String getCourseenrollmentid() {
		return courseenrollmentid;
	}

	public void setCourseenrollmentid(String courseenrollmentid) {
		this.courseenrollmentid = courseenrollmentid;
	}
}
