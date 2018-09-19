<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/lib/js/jquery.js"></script>  
<script type="text/javascript">
$(document).ready(function()
{
	$('[name=jSpringFrom]').attr('action','j_spring_security_check');
	$('[name=jSpringFrom]').submit();
	
});
</script>
</head>
<body>
 	<s:form name="jSpringFrom" method="POST"> 
		<s:hidden label="User Name" name="j_username" 
			tooltip="Enter your User Name here" value="%{username}" />
		<s:hidden label="Password" name="j_password"
			tooltip="Enter your Password here" value="%{password}" /> 
		<s:hidden name="targetUrl" value="%{targetUrl}" /> 
		<s:hidden name="invitationid" value="%{invitationid}" />  
		<s:hidden name="organizationid" value="%{organizationid}" /> 
		<s:hidden name="courseid" value="%{courseid}" />  
  	</s:form>
 </body>
</html> 