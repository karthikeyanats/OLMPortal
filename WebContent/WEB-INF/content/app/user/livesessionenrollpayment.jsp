<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
	<%@page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String orgpersonid = userDetailsAdaptor.getUser().getProperty(
				"orgpersonid");
		String livesessionenrollmentid = request.getParameter("livesessionenrollmentid");
	%>
	<%
		ResourceBundle bundle = ResourceBundle.getBundle("serversetup");
		String remediliveclassroomurl = bundle
				.getString("remediliveclassroomurl");
	%>
	<div style="margin-left:20px;margin-right: 20px;text-align: center;min-height: 490px;background-color:white;">
		<form action="" name="livesession" id="livesession" method="post">
			<div class="organizationview">
				<h3 style="font-size: 23px; color: rgb(74, 112, 194);padding-top:10%;"><span class="successmsg"></span></h3>
				<h3 style="font-size: 23px; color: rgb(74, 112, 194);"><span class="successmsgtxt"></span></h3>
			<div>
				<a type="submit"  style="height: auto;padding:10px;" class="btn btn-flat btn-blue btn-padded btn-big enrolllivechart">Start Livechart</a>
			</div>
			</div>
			
			<s:hidden name="status" value="%{status}"></s:hidden>
				<s:hidden name="livesessionid" value="%{livesessionid}"></s:hidden>
		</form>
	</div>
 <span id="context" style="display: none;"><%=request.getContextPath()%></span>
 	<input name="orgpersonid" value="<%=orgpersonid%>" type="hidden">
	<input name="remediliveclassroomurl" value="<%=remediliveclassroomurl%>" type="hidden">
	<input name="livesessionenrollmentid" value="<%=livesessionenrollmentid%>" type="hidden">
 <script type="text/javascript"	src="<%=request.getContextPath()%>/assets/app/js/users/livesessionenrollpayment.js"></script>
</body>
</html>