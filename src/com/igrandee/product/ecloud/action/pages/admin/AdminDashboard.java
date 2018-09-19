package com.igrandee.product.ecloud.action.pages.admin;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.service.admin.AdminDashBoardService;

/**
 * @author seenivasagan_p
 * Action for the admin dashboard
 */
@Namespace("/app")
public class AdminDashboard extends MasterActionSupport{

	private static Logger AdminDashboardlogger = Logger.getLogger(AdminDashboard.class);

	private static final long serialVersionUID = -478540349852201578L;
	
	@Inject
	AdminDashBoardService AdminDashBoardServiceobj;
	/**
	 * @author seenivasagan_p
	 * Method to Redirect to user preview
	 * @return success string
	 */
	@Action(value="/dashboard",results={@Result(name="SUCCESS",location="app/admin/admin-dashboard.jsp")})
	public String dashboard()	{
		try	{
		}
		catch(Exception e){
			if(AdminDashboardlogger.isInfoEnabled()){
				AdminDashboardlogger.error("error in AdminDashboard() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	
	/**
	 * @author seenivasagan_p
	 * Method to get the published courses for dashboard
	 */
	@Action("/loadCoursesList")
	public void loadCoursesList(){
		try{
			List<?> coursesCount=AdminDashBoardServiceobj.getAllCoursesList(Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(coursesCount);
		}
		catch(Exception e)	{
			if(AdminDashboardlogger.isInfoEnabled()){
				AdminDashboardlogger.info("error in getDashBoardData in AdminDashboard" ,e);
			}	
		}
	}	

	/**
	 * @author seenivasagan_p
	 * Method to get the published courses for dashboard
	 */
	@Action("/loadEnrolledPersonsList")
	public void loadEnrolledPersonsList(){
		try{
			List<?> enrolledUsersCount=AdminDashBoardServiceobj.getAllEnrollmentList(Long.parseLong(getAttribute("organizationid")));
			checkobj().toJsonResponse(enrolledUsersCount);
		}
		catch(Exception e)	{
			if(AdminDashboardlogger.isInfoEnabled()){
				AdminDashboardlogger.info("error in getEnrolledPersonsList in AdminDashboard" ,e);
			}	
		}
	}
	
	/**
	 * @author seenivasagan_p
	 * Method to get the published courses for dashboard
	 */
	@Action("/loadCertificateRequestList")
	public void loadCertificateRequestList(){
		try{
			List<?> certificateRequestCount=AdminDashBoardServiceobj.getPendingCertificatesList(Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(certificateRequestCount);
		}
		catch(Exception e)	{
			if(AdminDashboardlogger.isInfoEnabled()){
				AdminDashboardlogger.info("error in getEnrolledPersonsList in AdminDashboard" ,e);
			}	
		}
	}	
	
	/**
	 * @author seenivasagan_p
	 * Method to load top categories with comparing enrolled users
	 */
	@Action("/loadCategorywiseUsersList")
	public void loadCategorywiseUsersList(){
		try{
			List<?> categorywiseUsersList=AdminDashBoardServiceobj.getCategorywiseUsers(Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(categorywiseUsersList);
		}
		catch(Exception e)	{
			if(AdminDashboardlogger.isInfoEnabled()){
				AdminDashboardlogger.info("error in loadCategorywiseUsersList in AdminDashboard" ,e);
			}	
		}
	}
	
	/**
	 * @author seenivasagan_p
	 * Method to load top cousres with comparing enrolled users
	 */
	@Action("/loadCoursewiseUsersList")
	public void loadCoursewiseUsersList(){
		try{
			List<?> coursewiseUsersList=AdminDashBoardServiceobj.getCoursewiseUsers(Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(coursewiseUsersList);
		}
		catch(Exception e)	{
			if(AdminDashboardlogger.isInfoEnabled()){
				AdminDashboardlogger.info("error in loadCoursewiseUsersList in AdminDashboard" ,e);
			}	
		}
	}
	
	/**
	 * @author seenivasagan_p
	 * Method to load weekly payment amount
	 */
	@Action("/loadWeeklyPayments")
	public void loadWeeklyPayments(){
		try{
			List<?> weeklyPaymentAmount=AdminDashBoardServiceobj.getWeeklyPayments(Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(weeklyPaymentAmount);
		}
		catch(Exception e)	{
			if(AdminDashboardlogger.isInfoEnabled()){
				AdminDashboardlogger.info("error in loadWeeklyPayments in AdminDashboard" ,e);
			}	
		}
	}
	
	/**
	 * @author seenivasagan_p
	 * Method to load monthly payment amount
	 */
	@Action("/loadMonthlyPayments")
	public void loadMonthlyPayments(){
		try{
			List<?> monthlyPaymentAmount=AdminDashBoardServiceobj.getMonthlyPayments(Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(monthlyPaymentAmount);
		}
		catch(Exception e)	{
			if(AdminDashboardlogger.isInfoEnabled()){
				AdminDashboardlogger.info("error in loadMonthlyPayments in AdminDashboard" ,e);
			}	
		}
	}
	
	/**
	 * @author seenivasagan_p
	 * Method to load yearly payment amount
	 */
	
	@Action("/loadYearlyPayments")
	public void loadYearlyPayments(){
		try{
			List<?> yearlyPaymentAmount=AdminDashBoardServiceobj.getYearlyPayments(Integer.parseInt(getAttribute("orgpersonid")));
			checkobj().toJsonResponse(yearlyPaymentAmount);
		}
		catch(Exception e)	{
			if(AdminDashboardlogger.isInfoEnabled()){
				AdminDashboardlogger.info("error in loadYearlyPayments in AdminDashboard" ,e);
			}	
		}
	}
	/**
	 * @author srithar_m
	 * 
	 * @return success string
	 */
	
	
	@Action(value="/userdashboard",results={@Result(name="SUCCESS",location="app/organization/organization-dashboard.jsp")})
	public String organizationuserdashboard()	{
		try	{
		}
		catch(Exception e){
			if(AdminDashboardlogger.isInfoEnabled()){
				AdminDashboardlogger.error("error in AdminDashboard() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
	
	
	@Action(value="/trainerdashboard",results={@Result(name="SUCCESS",location="app/organization/trainer-dashboard.jsp")})
	public String trainerdashboard()	{
		try	{
		}
		catch(Exception e){
			if(AdminDashboardlogger.isInfoEnabled()){
				AdminDashboardlogger.error("error in AdminDashboard() in RedirectAction",e);
			}
		}
		return "SUCCESS";
	}
}
