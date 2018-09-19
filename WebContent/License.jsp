<%@page import="com.sun.xml.internal.txw2.Document"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/plugins/bootstrap-3/css/bootstrap.min.css">
	media="screen">
<!-- if (navigator.appVersion.indexOf("Win")!=-1) OSName="Windows";
if (navigator.appVersion.indexOf("Mac")!=-1) OSName="MacOS";
if (navigator.appVersion.indexOf("X11")!=-1) OSName="UNIX";
if (navigator.appVersion.indexOf("Linux")!=-1) OSName="Linux"; -->  

<!-- <script type="text/javascript">
	function redirect() {
		alert('os');

		if (navigator.appVersion.indexOf("Win") != -1) {
			//var person = prompt("Please enter Admin Password");
			window.open('<input type="text">');
			document.getElementById("hide").value = person;
		}
	}
</script> -->

<title>License Entry</title>

</head>
<body>

	<div class="col-md-12 well test14"
		style="width: 37%; position: absolute; top: 30%; left: 31%;">
		<div class="row test11 test12">
			<div class="col-md-4">
				<img src="ig.png" />
			</div>
			<div class="col-md-8">
				<label>Select Product Key</label>
			</div>
		</div>
		<!-- action="MyPageAction" -->
		<div class="row test12">
			<form id="form_id" name="myForm" method="post" enctype="multipart/form-data">
				<div class="col-md-12">
					<div class="row test11">
						<div class="col-md-4">
							<label class="lbl">Secret Key:</label>
						</div>
						<div class="col-md-8 test13">
							<input class="file" type="file" name="secret">
						</div>
					</div>
					<div class="row test11">
						<div class="col-md-4">
							<label class="lbl">License Key:</label>
						</div>
						<div class="col-md-8 test13">
							<input class="file" type="file" name="license">
						</div>
					</div>
					<%
						Object wel = request.getAttribute("welcme");
					%>
					<input type="hidden" name="welcome" value=<%=wel%>>
					<%
						String os = System.getProperty("os.name");
						if (os.startsWith("Linux")) {
					%>
					<!-- <div class="row test11">
						<div class="col-md-4">
							<label class="lbl">Admin Password:</label>
						</div>
						<div class="col-md-8 test13">
							<input type="password" name="pass" value="">
						</div>
					</div> -->
					<%
						}
					%>
					<div class="row">
						<div class="pull-right">
							<input type="submit" value="Submit" onclick="validateForm()" class="btn btn-success">
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>

	<script type="text/javascript">
		function validateForm() {
			var x=document.forms["myForm"]["secret"].value;
			var y=document.forms["myForm"]["license"].value;
			if (x == "")
				alert('Select Secret Key');
			else
			{
				if (y == "")
					alert('Select License Key');
				else
					document.getElementById('form_id').action = "MyPageAction";
			}
		}
	</script>

	<!-- <script src="js/jquery-2.0.3.min.js"></script>
	<script src="js/bootstrap.min.js"></script> -->
</body>
</html>