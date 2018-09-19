<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script type="text/javascript">
function authorinvitesignup(){
    document.authorinviteform.action="preview";
	document.authorinviteform.submit();

}
</script>
<body onload="authorinvitesignup()">
	<form name="authorinviteform" action="" method="post"
		class="form-vertical offset1" style="margin-top: 15px;">
		<input type="hidden" name="courseid"
			value="<%=request.getParameter("courseid")%>"> <input
			type="hidden" name="userstatus"
			value="<%=request.getParameter("userstatus")%>"> <input
			type="hidden" name="courseinvitationid"
			value="<%=request.getParameter("courseinvitationid")%>"> <input
			type="hidden" name="emailid"
			value="<%=request.getParameter("emailid")%>">
	</form>

</body>
</html>

