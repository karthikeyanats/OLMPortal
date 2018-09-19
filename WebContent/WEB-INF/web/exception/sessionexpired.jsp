<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page language="java" import="java.util.ResourceBundle"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/lib/js/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){	
	location.href="<%=request.getContextPath()%>/sessionexpired";
});
</script>
</head>
<body>
</body>
</html>