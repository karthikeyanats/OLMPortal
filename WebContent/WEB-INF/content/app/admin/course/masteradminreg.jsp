<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="label.productname"></s:text></title>
<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/plugins/bootstrap-3/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/app/fonts/monstrerrat/font.css">
<style type="text/css">
body {
	background: rgb(240, 240, 240);
	overflow-x: hidden;
	font-family: "Montserrat", "Helvetica Neue", Helvetica, Arial,
		sans-serif;
}

hr {
	margin: 10px 0;
}

form {

	background-color: white;
	margin-top: 30px;
	margin-bottom: 10px;
	-moz-box-shadow: 0px 0px 2px 2px #ccc;
	-webkit-box-shadow: 0px 0px 2px 2px #ccc;
	box-shadow: 0px 0px 2px 2px #c;
}
</style>
</head>
<body>
	<form class="form-horizontal col-sm-offset-2 col-sm-8"
		action="saveMasterAdmin" method="post">
		<div class="heading">
			<h2 style="color: #007FBF; font-size: 22px; padding-left: 10px;"
				class="form-heading">Master Registration</h2>
			<hr>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="">Organization
				Name</label>
			<div class="col-sm-7">
				<input type="text" name="organizationname" id=""
					class="col-md-7 form-control" required placeholder="E.g. Hegde">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="">Street1</label>
			<div class="col-sm-7">
				<input type="text" name="street1" id=""
					class="col-md-7  form-control" required
					placeholder="E.g. kalkurichi">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="">Street2</label>
			<div class="col-sm-7">
				<input type="text" name="street2" id=""
					class="col-md-7  form-control" required placeholder="E.g. Nagar">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="">City</label>
			<div class="col-sm-7">
				<input type="text" name="city" id="" class="col-md-7  form-control"
					required placeholder="E.g. Madurai">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="">Country</label>
			<div class="col-sm-7">
				<input type="text" name="country" id=""
					class="col-md-7  form-control" required placeholder="E.g. India">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="">Email</label>
			<div class="col-sm-7">
				<input type="text" name="email" id="" class="col-md-7  form-control"
					required placeholder="E.g. gk@gmail.com">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="">Phone no</label>
			<div class="col-sm-7">
				<input type="text" name="contactno" id=""
					class="col-md-7  form-control" required
					placeholder="E.g. 9949898989">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="">Url</label>
			<div class="col-sm-7">
				<input type="text" name="organizationurl" id=""
					class="col-md-7  form-control" required
					placeholder="E.g. www.igrandee.com">
			</div>
		</div>
		<div class="heading">
			<h2 style="color: #007FBF; font-size: 22px; padding-left: 10px;"
				class="form-heading">Personal Details</h2>
			<hr>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="">First Name</label>
			<div class="col-sm-7">
				<input type="text" name="orgfirstname" id=""
					class="col-md-7  form-control" required
					placeholder="E.g. ashwinhegde">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="">Last Name</label>
			<div class="col-sm-7">
				<input type="text" name="orglastname" id=""
					class="col-md-7  form-control" required
					placeholder="E.g. ashwinhegde">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="">Email</label>
			<div class="col-sm-7">
				<input type="text" name="orgemail" id=""
					class="col-md-7  form-control" required
					placeholder="E.g. gk@gmail.com">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="">Contact Number</label>
			<div class="col-sm-7">
				<input type="text" name="orgcontactno" id=""
					class="col-md-7  form-control" required
					placeholder="E.g. 993939898">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="">Username</label>
			<div class="col-sm-7">
				<input type="text" name="orgusername" id=""
					class="col-md-7  form-control" required
					placeholder="E.g. ashwinhegde">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="">Password</label>
			<div class="col-sm-7">
				<input type="password" name="orgpassword" id=""
					class="col-md-7 form-control" required
					placeholder="Min. 8 Characters">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-7 text-center">
				<button type="submit" class="btn btn-success">Sign Up</button>
			</div>
		</div>
	</form>
</body>
</html>