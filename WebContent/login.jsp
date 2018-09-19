<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/x-handlebars-template" id="logintmpl">
{{#each .}}
<legend>
  <div class="row-fluid">
	  <div class="span4">
	        <img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{logopath}}&filetype=logo" alt="" style="padding: 10px;" width="50">
	  </div>				  
	  <div class="span8">
	      <div style="padding:10px;" id="orgname">{{orgname}}
	      </div>
	   </div>
 </div>
</legend>
{{/each}}
</script>
<script type="text/x-handlebars-template" id="validationtmpl">
<div class="alert alert-danger alert-dismissible" role="alert" style="margin-top:10px;">
   <button type="button" class="close" data-dismiss="alert">
	  <span aria-hidden="true">×</span>
  </button>
  <strong>Warning! </strong> {{message}}
</div>
</script>
<script type="text/javascript">
window.onload = function () {
	$('[name=userlogin]').attr('action','loginpage');
	$('[name=userlogin]').submit();	
};
</script>
</head>
<body>
	<div id="home" class="home">
		<div class="row-fluid" style="min-height: 500px;">
			<div class="span4"></div>
			<div class="span4 form_login"
				style="margin-top: 90px; padding: 15px; min-height: 250px;display:none;">
				<form name="userlogin" action="" method="post"
					class="form-vertical offset1" style="margin-top: 15px;">
					<fieldset>
                        <div id="userlogin"></div>
						<div class="row-fluid">
							<div class="span4">
								<label class="control-label" for=""><b>Full Name</b>
								      <span style="color: rgb(255, 10, 10);padding: 8px;">*</span>
								</label>
							</div>
							<div class="span8">
								<input type="text" name="firstname" value="">
							</div>
						</div>
						<div class="row-fluid">
							<div class="span4">
								<label class="control-label" for=""><b>Email Id</b>
								     <span style="color: rgb(255, 10, 10);padding:8px;margin-left:15px;">*</span>
								</label>
							</div>
							<div class="span8">
							  <input type="text"  name="username" value=<%=request.getParameter("emailid")%> > 		
							</div>
						</div>
						<div class="row-fluid">
							<div class="span4">
								<label class="control-label" for=""><b>Password</b>
								   <span style="color: rgb(255, 10, 10);padding: 8px;">*</span>
								</label>
							</div>
							<div class="span8">
								<input type="password" name="password" value="">
							</div>
						</div>
						<div class="row-fluid">
							<div class="span4">
								<label class="control-label" for=""><b>Confirm Password</b>
								    <span style="color: rgb(255, 10, 10);padding: 8px;">*</span>
								</label>
							</div>
							<div class="span8">
								<input type="password" name="confirmpassword" value="">
							</div>
						</div>
						<div id="errormessage"></div> 
						<input type="submit" value="Sign up"
							class="btn btn-flat btn-primary userlogin" style="margin-left: 120px;margin-top: 20px;">
					</fieldset>					
					   <input type="hidden" name="organizationid"  value="<%=request.getParameter("organizationid")%>">
					   <input type="hidden" name="orgpersonid"  value="<%=request.getParameter("orgpersonid")%>">
					    <input type="hidden" name="emailid"  value="<%=request.getParameter("emailid")%>">
					    <input type="hidden" name="personid"  value="<%=request.getParameter("personid")%>">
					    <input type="hidden" name="loginid"  value="<%=request.getParameter("loginid")%>">
					    <input type="hidden" name="invitationid"  value="<%=request.getParameter("invitationid")%>">
					    <input type="hidden" name="courseid"  value="<%=request.getParameter("courseid")%>">
<%-- 					    <input type="hidden" name="invitationid"  value="<%=request.getParameter("courseinvitationid")%>">					    
 --%>					  <!--  <input type="hidden" name="targetUrl" value="/app/mycourses"> -->
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript"  src="<%=request.getContextPath()%>/assets/lib/js/jquery.js"></script>
 </body>
</html>