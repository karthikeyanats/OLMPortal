<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body> 		
		<div class="row-fluid" style="min-height: 500px;">
			<div class="span4"></div> 
			<div class="span4" style="background-color: white;margin-top: 90px;padding:15px;min-height:250px;
			-moz-box-shadow: 0px 0px 2px 2px #ccc;-webkit-box-shadow: 0px 0px 2px 2px #ccc;
			box-shadow: 0px 0px 2px 2px #c;">
				<s:actionerror theme="bootstrap" />
				<s:actionmessage theme="bootstrap" />
				<s:fielderror theme="bootstrap" />
				<s:form action="j_spring_security_check" theme="bootstrap"
					cssClass="form-vertical offset2" cssStyle="margin-top:15px;">
					<s:textfield label="User Name" name="j_username"
						tooltip="Enter your User Name here" />
					<s:password label="Password" name="j_password"
						tooltip="Enter your Password here" />
					<s:hidden value="/app/mycourses" name="targetUrl"></s:hidden> 
 					<s:submit value="Sign In" cssClass="btn btn-flat btn-primary" />  
					 <a href="signUp" class="btn btn-primary btn-flat">Sign Up</a>  
				</s:form> 
			</div>
		</div>
 </body>
</html>