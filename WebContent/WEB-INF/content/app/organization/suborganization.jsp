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


<%-- <link href="<%=request.getContextPath()%>/assets/app/css/dashborad.css" type="text/css" rel="stylesheet"> --%>

<script type="text/x-handlebars-template" id="Orgcategorytmpl">
<option value="0">---Select Organization---</option>
{{#each .}}
	<option value="{{organizationid}}">{{organizationname}}</option>
{{/each}}
</script>
<script type="text/x-handlebars-template" id="validationtmpl">
<div class="alert alert-danger alert-dismissible" role="alert" style="margin-top:10px;">
   <strong >Warning! </strong> {{message}}
</div>
</script>

<style type="text/css">
.input-group-addon {
    padding: 6px 12px;
    font-size: 14px;
    font-weight: 400;
    line-height: 1;
    color: #555;
    text-align: center;
    background-color: #fff;
    border: 1px solid #fff;
    border-radius: 4px;
}
</style>
<style>
hr {
    margin-top: 8px;
    margin-bottom: 20px;
    border: 0;
    border-top: 2px solid #14bdee;
}
</style>
<style>
.course:hover {
  box-shadow: 0 0 11px rgba(33,33,33,.2); 
}
.modal-content{
  border-radius: 0px;
}
.btn-lg{
  border-radius: 0px;
}
.input-group .form-control:last-child{
   
   border-top-left-radius: 0px;
    /* border-bottom-left-radius: 60px; */
    border-bottom-left-radius: 0px;
    border-bottom-right-radius: 0px;
    border-top-right-radius: 0px;

}
.input-group .form-control{
    width: 100%;
}
:first-child{
       border-right: 0;
    border-top-left-radius: 0px;
    /* border-bottom-left-radius: 60px; */
    border-bottom-left-radius: 0px;
    border-bottom-right-radius: 0px;
    border-top-right-radius: 0px;
}
.input-group-addon {
    padding: 10px 16px;
    font-size: 14px;
    font-weight: 400;
    line-height: 1;
    color: #fff;
    text-align: center;
    background-color: #fff;
    border: 0px solid #fff;
    border-radius: 0px;
}

.input100{
    height: 50px;
    /* font-family: 'Poiret One', cursive; */
    font-size: 14px;
}

input[type=submit].btn-block{
      width: 100%;
}
.txt1{
    font-size: 14px;
    font-weight: 500;
    color: #212121;
    letter-spacing: 1px;
    text-transform: uppercase;
    margin-left: -13px;
}
.modal-body{
     margin-left: 10px;
}
.input-group-addon:last-child{

    border-left: 0;
    border-top-left-radius: 0px;
    /* border-bottom-left-radius: 60px; */
    border-bottom-left-radius: 0px;
    border-bottom-right-radius: 0px;
    border-top-right-radius: 0px;

}
.min530{

    padding: 0px;
    background: url("./assets/app/images/whitebglogin.jpg");
	/* background-repeat: no-repeat; */
 }
body {margin: 0; height: 100%;}

.well{
   margin-bottom: 189px;
}
a{font-size: 16px;}
</style>



</head>
<body>

<div class="container-fluid">
		<form class="well col-md-6 col-md-offset-3 forn form-horizontal"
			enctype="multipart/form-data" name="userlogin" method="post"
			id="organizationinfo" style="margin-top: 154px;background: white;padding: 0px;margin-left: 500px;">
			<fieldset>
				<legend class="centertext cornflowerblue padbottom10" style="background-color: #14bdee;"><h4 style="color: white;margin-top: 17px;font-size: 26px;">Login For Organization</h4></legend>
				<div class="tab-content">
					<div class="tab-pane active" id="basicinfo">
                               <div class="row martop20">
					            <div id="validationmessage" style="padding-left: 19px;padding-right: 19px;"></div>
				              </div>
						        <div class="col-sm-12 col-md-10  col-md-offset-1 ">
									   <div class="p-t-31 p-b-9">
						                     <span class="txt1">Login For Organization</span>
					                    </div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon"></span> 
												  
												  <select class="form-control" name="orgcategoryid" id="organcategory" style="height: 42px;margin-left: -29px;">
						                          </select>
											</div>
										</div>
										
										<div class="p-t-31 p-b-9">
						                     <span class="txt1">Email</span>
					                    </div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon" style="padding: 10px 16px;
    font-size: 14px;
    font-weight: 400;
    line-height: 1;
    color: #fff;
    text-align: center;
    background-color: #14bdee;
    border: 0px solid #14bdee;
    border-radius: 0px;"> <i class="glyphicon glyphicon-envelope"></i>
												</span> 
												  <!-- <input id="emailid" name="email" type="text" maxlength="45" class="form-control" placeholder="E.g:someone@example.com"  required="required" style="height: 42px;width: 703px;"> -->
												  
												  
												  <input type="email" name="j_username" id="username" value="" class="user-name form-control" placeholder="Your Email Address" style="height: 42px;width: 703px;">
												  
								            </div>
										</div>
										
										
										<div class="p-t-31 p-b-9">
						                     <span class="txt1">Password</span>
					                    </div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon" style="padding: 10px 16px;
    font-size: 14px;
    font-weight: 400;
    line-height: 1;
    color: #fff;
    text-align: center;
    background-color: #14bdee;
    border: 0px solid #14bdee;
    border-radius: 0px;"> <i class="glyphicon glyphicon-lock"></i>
												</span> 
												  <!-- <input type="password" maxlength="45" minlength="6" class="newpwdval form-control" name="orgpassword"  required="required" style="height: 42px;width: 703px;"> -->
												  
												  <input type="password" name="j_password" id="password" value="" class="user-name form-control" placeholder="Your Password" style="height: 42px;width: 703px;">
												  <label class="orgpassword error" id="orgpassword" style="display: none"></label>
											</div>
											<span id="strengthmsg" class="strmsg pull-right martop10"></span>
								<div class="progcntrl martop30" style="display: none;">
									<div class="progress" style="height: 4px !important;">
										<div class="progress-bar progress-bar-success one"
											style="background-color: #5eb95e !important;"></div>
										<div class="progress-bar progress-bar-success two"
											style="background-color: #5eb95e !important;"></div>
										<div class="progress-bar progress-bar-warning three"
											style="background-color: #faa732 !important;"></div>
										<div class="progress-bar progress-bar-danger four"
											style="background-color: #dd514c !important;"></div>
										<div class="progress-bar progress-bar-danger five"
											style="background-color: #dd514c !important;"></div>
									     </div>
										</div>
										
										</div>
									</div>
						</div>
				</div>
			</fieldset>
			<hr>
			<div class="form-group">
				<label class="col-sm-4 righttext" for=""></label>
				<div class="col-md-4" style="margin-left: -154px;">
					<!-- <input type="submit" class="btn btn-lg btn-primary btn-block orgcreate" style="height: auto;"  value="Create New Organization"> -->
					<button class="btn btn-lg btn-primary btn-block signin log-in" type="button" id="sign-in" value="Login">Sign in</button>
				</div>	
				<div class="col-md-4">
					<!-- <input type="submit" style="height: auto;" id="createorg"
						class="btn white btn-blue orgcreate"
						value="Create New Organization"> --> 
						<a href="<%=request.getContextPath()%>/createorganization" class="btn btn-lg btn-primary btn-block">Sign Up</a>
				</div>
			</div>
			<%-- <s:hidden name="parentorgid" value="%{orgid}"></s:hidden> --%>
			<input type="hidden" name="organizationid" value=""> <input
				type="hidden" name="targetUrl" value="/app/mycourses">
		</form>
	</div>
   
          
    
	<%-- <div class="container-fluid martop20">
		<form name="userlogin" action="j_spring_security_check" method="post"
			class="well white col-md-8 col-md-offset-2 form-horizontal martop20 ligtgreenbg padbottom20">
			<fieldset>
				<Legend class="centertext white padbottom10">Login For
					Organization</Legend>

				<div class="row martop20">
					<div id="validationmessage"></div>
				</div>

				<div class="form-group martop20 padbottom20">
					<label class="col-sm-5 righttext" for="textinput">Select
						Organization <span style="color: red;">*</span> :
					</label>
					<div class="col-sm-5">
						<select class="form-control" name="orgcategoryid"
							id="organcategory">
						</select>
					</div>
				</div>

				<div class="form-group padbottom20">
					<label class="col-sm-5 righttext" for="textinput">Email <span
						style="color: red;">*</span> :
					</label>
					<div class="col-sm-5">
						<input type="email" name="j_username" id="username" value=""
							class="user-name form-control" placeholder="Your Email Address">
					</div>
				</div>

				<div class="form-group padbottom20">
					<label class="col-sm-5 righttext" for="textinput">Password
						<span style="color: red;">*</span> :
					</label>
					<div class="col-sm-5">
						<input type="password" name="j_password" id="password" value=""
							class="user-name form-control" placeholder="Your Password">
					</div>
				</div>
				<hr>
				<div class="row martop20">
					<div class="col-md-12 col-md-offset-1">
						<div class="col-md-3">
							<a class="btn btn-primary orgfacebook"> <i
								class="fa fa-facebook"></i> | Facebook
							</a>
						</div>
						<div class="col-md-3">
							<a class="btn btn-danger orggoogle"> <i
								class="fa fa-google-plus"></i> | Google+
							</a>
						</div>
						<div class="col-md-2">
							<button class="btn btn-info signin log-in" type="button"
								id="sign-in" value="Login">Sign in</button>
						</div>
						<div class="col-md-3">
							<a class="btn signup cornbg white"
								href="<%=request.getContextPath()%>/createorganization" type=""
								id="" value="">Sign Up</a>
						</div>
					</div>
			</fieldset>
			<input type="hidden" name="organizationid" value=""> <input
				type="hidden" name="targetUrl" value="/app/mycourses">
		</form>
	</div> --%>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/lib/js/underscore.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/lib/js/handlebars.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/helper/helper-function.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/data/courseData.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/organization/suborganization.js"></script>
</body>
</html>