<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reamedi Cloud || Create New Organization</title>


<script id="errortmpl" type="text/x-handlebars-template">


<span>{{message}}</span>
</script>



<style type="text/css">
hr {
    margin-top: 29px;
    margin-bottom: 20px;
    border: 0;
    border-top: 2px solid #14bdee;
}
.form-group {
    margin-bottom: 9px;
}
.well{

    height: 748px;
    background-color: white;
    border-radius: 0px;
}
.input-lg{
  border-radius: 1px;
      font-size: 15px;
}
.btn-lg{
  border-radius: 1px;
}
.form-control{
    border-radius: 1px;
      font-size: 15px;
      height: 41px;
}
.btn{

   border-radius: 0px;
   width: 334px;
   padding: 11px 12px;
}
.plusicon{

   padding: 9px 12px !important;
   color: #fff !important;
   background-color: #14bdee !important;
    border: 0px solid #ccc;
}
.min530{

    padding: 0px;
    background: url("./assets/app/images/whitebglogin.jpg");
	/* background-repeat: no-repeat; */
 }
 label {
	color: black;
}
a{font-size: 16px;}
</style>

</head>
<body>
	
	

<div class="row">
    <div class="well col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3"  style="margin-top: 30px;width: 800px;margin-left: 580px;">
		<form role="form" enctype="multipart/form-data" name="organizationform" method="post"
			id="organizationinfo"  style="margin-bottom: -22px;">
			<div style="background: #14bdee;padding: 1px;margin-top: -19px;width: 798px;margin-left: -19px;"><h3 align="center" style="color: #fff;">Create a Organization Form</h3></div>
			
			<!-- <hr class=""> -->
			<div class="row" style="margin-top: 26px;">
				<div class="col-xs-12 col-sm-6 col-md-6">
					<label>Organization Name</label>
					<div class="form-group">
                       <input id="orgname" name="organizationname" type="text"
									class="form-control trimvalue" maxlength="45"
									placeholder="E.g:aimjoro.com"  required="required"> <label
									class="orgname error orgnameerror" id="alertorgname"></label>
                    </div>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-6">
					<label>Organization Logo</label>
					<div class="form-group">
						  <input id="fileUpload" name="fileUpload" class="form-control input-file" type="file" style="display: inline;">
												  <label for="file" class="file error" id="alertLogo"  ></label>
								                  <p class="help-block error"><strong style="font-size: 12.5px;">Recommended Formats : png,jpg,jpeg and File Size is 1 MB.</strong></p>
								                  <div class="alert alert-danger imagesizealert" style="display: none;">The file Size specified is 1 MB.
										          You cannot upload files greater than 1MB</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-md-6">
					<label>Organization url</label>
					<div class="form-group">
                        <input id="textinput url" name="url" type="text" class="organizationurl form-control" maxlength="45" placeholder="E.g:www.aimjoro.com"  required="required">
						<label for ="file"  class="file error orgurl" id="errorurl"></label>
					</div>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-6">
					<label>Domain</label>
					<div class="form-group">
						 <input id="domain" name="domainurl" type="text" class=" form-control col-sm-4" maxlength="45"  required="required">
						 <label for ="file"  class="file error orgurl" id="errorurl"></label>
						<small id="emailHelp" class="form-text text-muted">Aimjoro.com</small>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-md-6">
					<label>Phone Number</label>
					<div class="form-group">
					<div class="input-group">
								<span class="input-group-addon" style="padding: 10px 16px;font-size: 14px;font-weight: 400;line-height: 1;color: #fff;text-align: center;
    background-color: #14bdee;
    border: 0px solid #14bdee;
    border-radius: 0px;"> <i class="glyphicon glyphicon-plus"></i>
												</span> 
                         <input type="text" required="required" maxlength="12" class="form-control  orgphonenumber" id=" textinput phoneno txtNumeric" name="contactno"><br>
                        <label for="file" class="file error orgcontact" id=""></label>
					</div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-6">
					<label>Size Of Your Team</label>
					<div class="form-group">
						<input id="textinput" name="sizeoforg" type="text" min="1" minlength="1" maxlength="4" class="form-control" placeholder="E.g:10"  required="required">
						<label for="file" class="file error organizationsize" id=""></label>
					</div>
				</div>
			</div>
			<label>Admin Name</label>
			<div class="form-group">
				<input id="fullname" name="orgfirstname" type="text" maxlength="45" class="form-control"  required="required">
				<label for="file" class="file error organizationsize" id=""></label>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-md-6">
					<label>Email Address</label>
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
						<input id="emailid" name="email" type="text" maxlength="45" class="form-control" placeholder="E.g:someone@example.com"  required="required">
						<label class="email error" id="alertemail"></label>
					</div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-6">
					<label>Password</label>
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
						<input type="password" maxlength="45" minlength="6" class="newpwdval form-control" name="orgpassword"  required="required" style="width: 281px;">
						<span class="input-group-addon showpass plusicon"><i class="fa fa-eye"></i></span>
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
			<%-- <div class="row">
				<div class="col-xs-4 col-sm-3 col-md-3">
					<span class="button-checkbox">
						<button type="button" class="btn" data-color="info" tabindex="7">I Agree</button>
                        <input type="checkbox" name="t_and_c" id="t_and_c" class="hidden" value="1">
					</span>
				</div>
				<div class="col-xs-8 col-sm-9 col-md-9">
					 By clicking <strong class="label label-primary">Register</strong>, you agree to the <a href="#" data-toggle="modal" data-target="#t_and_c_m">Terms and Conditions</a> set out by this site, including our Cookie Use.
				</div>
			</div> --%>
			
			<hr>
			<div class="row">
				<div class="col-xs-12 col-md-6"><input type="submit" style="height: auto;margin-top: 7px;font-size: 16px;" id="createorg" class="btn white btn-blue orgcreate" value="Create New Organization"></div>
				<div class="col-xs-12 col-md-6"><a href="<%=request.getContextPath()%>/suborganization" class="btn btn-primary btn-block btn-lg" style="margin-top: 7px;">Sign In</a></div>
			</div>
			
			<%-- <hr class="colorgraph">
			 <div class="row">
				<div class="col-xs-12 col-md-6"><input type="submit" style="height: auto;" id="createorg"
						class="btn white btn-blue orgcreate"
						value="Create New Organization"> <a
						href="<%=request.getContextPath()%>/suborganization"
						class="btn white btn-blue">Sign in</a></div>
				<div class="col-xs-12 col-md-6"><a href="#" class="btn btn-success btn-block btn-lg" style="margin-top: 7px;">Sign In</a></div>
			</div> --%> 
			<s:hidden name="parentorgid" value="%{orgid}"></s:hidden>
		</form>
	</div>
</div>

	<%-- <div class="container-fluid">
		<form class="well col-md-6 col-md-offset-3 forn form-horizontal"
			enctype="multipart/form-data" name="organizationform" method="post"
			id="organizationinfo" style="margin-top: 10px;background: white;padding: 0px;margin-left: 500px;">
			<fieldset>
				<legend class="centertext cornflowerblue padbottom10" style="background-color: #14bdee;"><h4 style="color: white;margin-top: 17px;font-size: 26px;">Create a New Organization</h4></legend>
				<div class="tab-content">
					<div class="tab-pane active" id="basicinfo">

						        <div class="col-sm-12 col-md-10  col-md-offset-1 ">
									   <div class="p-t-31 p-b-9">
						                     <span class="txt1">Organization Name<span style="color:red;font-size:18px;">*</span></span>
					                    </div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon"></span> 
												  <input id="orgname" name="organizationname" type="text" class="form-control trimvalue" maxlength="45" placeholder="E.g:aimjoro.com"  required="required">
												  <label class="orgname error orgnameerror" id="alertorgname"></label>
											</div>
										</div>
										<div class="p-t-31 p-b-9">
						                     <span class="txt1">Organization Logo<span style="color:red;font-size:18px;">*</span></span>
					                    </div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon"></span> 
												  <input id="fileUpload" name="fileUpload" class="form-control input-file" type="file" style="display: inline;">
												  <label for="file" class="file error" id="alertLogo"  ></label>
								                  <p class="help-block error"><strong>Recommended Formats : png,jpg,jpeg. Recommended file Size is 1 MB.</strong></p>
								                  <p class="help-block error"><strong>Recommended file Size is 1 MB.</strong></p>
								                  <div class="alert alert-danger imagesizealert" style="display: none;">The file Size specified is 1 MB.
										          You cannot upload files greater than 1MB</div>	
											</div>
										</div>
										<div class="p-t-31 p-b-9">
						                     <span class="txt1">Organization url<span style="color:red;font-size:18px;">*</span></span>
					                    </div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon"></span> 
												  <input id="textinput url" name="url" type="text" class="organizationurl form-control" maxlength="45" placeholder="E.g:www.aimjoro.com"  required="required" style="height: 42px;margin-left: -29px;">
												  <label for ="file"  class="file error orgurl" id="errorurl"></label>
											</div>
										</div>
										<div class="p-t-31 p-b-9">
						                     <span class="txt1">Domain<span style="color:red;font-size:18px;">*</span></span>
					                    </div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon"></span> 
												  <input id="domain" name="domainurl" type="text" class=" form-control col-sm-4" maxlength="45"  required="required" style="height: 42px;margin-left: -29px;">
												  <label for ="file"  class="file error orgurl" id="errorurl"></label>
											</div>
										</div>
										
										<div class="p-t-31 p-b-9">
						                     <span class="txt1">Phone Number<span style="color:red;font-size:18px;">*</span></span>
					                    </div>
										<div class="form-group">
											<div class="input-group">
											    <span class="input-group-addon" style="padding: 10px 16px;font-size: 14px;font-weight: 400;line-height: 1;color: #fff;text-align: center;background-color: #14bdee;border: 0px solid #14bdee;border-radius: 0px;"><i class="glyphicon glyphicon-plus"></i></span> 
												 <input type="text" required="required" maxlength="12" class="form-control  orgphonenumber" id=" textinput phoneno txtNumeric" name="contactno" style="height: 42px;width: 694px;">
											</div>
											<label for="file" class="file error orgcontact" id=""></label>
										</div>
										
										
										
										<div class="p-t-31 p-b-9">
						                     <span class="txt1">Size Of Your Team<span style="color:red;font-size:18px;">*</span></span>
					                    </div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon"> <i class=""></i>
												</span> 
												  <input id="textinput" name="sizeoforg" type="text" min="1" minlength="1" maxlength="4" class="form-control" placeholder="E.g:10"  required="required" style="height: 42px;margin-left: -29px;">
												  <label for="file" class="file error organizationsize" id=""></label>
											</div>
										</div>
										
										
										<div class="p-t-31 p-b-9">
						                     <span class="txt1">Admin Name<span style="color:red;font-size:18px;">*</span></span>
					                    </div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon"> <i class=""></i>
												</span> 
												  <input id="fullname" name="orgfirstname" type="text" maxlength="45" class="form-control"  required="required" style="height: 42px;margin-left: -29px;">
												  <label for="file" class="file error organizationsize" id=""></label>
											</div>
										</div>
										
										<div class="p-t-31 p-b-9">
						                     <span class="txt1">Email<span style="color:red;font-size:18px;">*</span></span>
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
												  <input id="emailid" name="email" type="text" maxlength="45" class="form-control" placeholder="E.g:someone@example.com"  required="required" style="height: 42px;width: 694px;">
												  
								                  <label class="email error" id="alertemail"></label>
											</div>
										</div>
										
										
										<div class="p-t-31 p-b-9">
						                     <span class="txt1">Password<span style="color:red;font-size:18px;">*</span></span>
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
												  <input type="password" maxlength="45" minlength="6" class="newpwdval form-control" name="orgpassword"  required="required" style="height: 42px;width: 694px;">
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
						
						
						 
						
						
						
						
						
						
						
						
						
						
						
						
						<div class="form-group">
							<label class="col-sm-4 righttext" for="textinput">Organization
								Name <span style="color: red;">*</span> :
							</label>
							<div class="col-sm-8">
								<div class="input-group">
								<input id="orgname" name="organizationname" type="text"
									class="form-control trimvalue" maxlength="45"
									placeholder="E.g:aimjoro.com"  required="required"> <label
									class="orgname error orgnameerror" id="alertorgname"></label>
							</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 righttext" for="filebutton">Organization
								Logo<span style="color: red;">*</span> :
							</label>
							<div class="col-sm-8">
								<input id="fileUpload" name="fileUpload" class="input-file"
									type="file" style="display: inline; margin-left: 12px;">
								<label for="file" class="file error" id="alertLogo"  ></label>
								<p class="help-block error"><strong>Recommended Formats :
											png,jpg,jpeg.</strong></p>
								<p class="help-block error"><strong>Recommended file Size is 1 MB.</strong></p>
								<div class="alert alert-danger imagesizealert"
										style="display: none;">The file Size specified is 1 MB.
										You cannot upload files greater than 1MB</div>			
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 righttext" for="textinput">Organization url <span
								style="color: red;">*</span> :
							</label>
							<div class="col-sm-8">
								<input id="textinput url" name="url" type="text" class="organizationurl form-control "
									maxlength="45" placeholder="E.g:www.aimjoro.com"  required="required"> <label
									for ="file"  class="file error orgurl" id="errorurl"></label>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-4 righttext" for="textinput">Domain<span
								style="color: red;">*</span> :
							</label>
							<div class="col-sm-8">
								<!-- <input type="hidden" id="textinput" name=""
									type="text" class=" domainurl">  --><input id="domain"
									name="domainurl" type="text"
									class=" form-control col-sm-4" maxlength="45"  required="required">
								<label for="file" class="file error orgdomain" id="alertdomain"></label>
								<span class="" for="textinput"><strong>.aimjoro.com</strong></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 righttext righttext" for="textinput">Phone
								Number<span style="color: red;">*</span> :

							</label>
							<div class="col-sm-8">
								<div class="input-group">
									<span class="input-group-addon plusicon"> + </span> <input
										type="text" required="required"
										maxlength="12" class="form-control  orgphonenumber"
										id=" textinput phoneno txtNumeric" name="contactno" style="border-radius: 4px;">
									<label for="file" class="file error orgcontact" id=""></label>
									
									<!-- <input id="phoneno" name="contactno" type="text" minlength="10"  maxlength="15"class="form-control"> -->
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 righttext" for="textinput">Size Of
								Your Team<span style="color: red;">*</span> :
							</label>
							<div class="col-sm-8">
								<input id="textinput" name="sizeoforg" type="text" min="1"
									minlength="1" maxlength="4" class="form-control"
									placeholder="E.g:10"  required="required"> <label for="file"
									class="file error organizationsize" id=""></label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 righttext " for="textinput" id="adminname">Admin
								Name<span style="color: red;">*</span> :
							</label>
							<div class="col-sm-8">
								<input id="fullname" name="orgfirstname" type="text"
									maxlength="45" class="form-control"  required="required"> <label for="file"
									class="file error orgfirstname" id="orgfirstname"></label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 righttext" for="textinput">Email<span
								style="color: red;">*</span> :
							</label>
							<div class="col-sm-8">
								<input id="emailid" name="email" type="text" maxlength="45"
									class="form-control" placeholder="E.g:someone@example.com"  required="required">
								<p class="helptext">This will be your username to login</p>
								<label class="email error" id="alertemail"></label>
							</div>
						</div>
						<!-- Text input-->
						<div class="form-group">
							<label class="col-sm-4 righttext" for="textinput">Password<span
								style="color: red;">*</span> :
							</label>
							<div class="col-sm-8">
								<div class="input-group">
									<input type="password" maxlength="45" minlength="6"
										class="newpwdval form-control" name="orgpassword"  required="required"> <span
										class="input-group-addon showpass plusicon"><i
										class="fa fa-eye"></i></span>
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
			</fieldset><hr>
			<!-- <hr> -->
			<div class="form-group">
				<label class="col-sm-4 righttext" for=""></label>
				<div class="col-md-4" style="margin-left: -120px;">
					<input type="submit" class="btn btn-lg btn-primary btn-block orgcreate" style="height: auto;padding: 6px;margin-top: -18px;font-size: 17px;"  value="Create New Organization">
				</div>	
				<div class="col-md-4">
					<!-- <input type="submit" style="height: auto;" id="createorg"
						class="btn white btn-blue orgcreate"
						value="Create New Organization"> --> 
						<a href="<%=request.getContextPath()%>/suborganization" class="btn btn-lg btn-primary btn-block" style="height: auto;padding: 6px;margin-top: -18px;font-size: 17px;">Sign in</a>
				</div>
			</div><br>
			<s:hidden name="parentorgid" value="%{orgid}"></s:hidden>
		</form>
	</div>
	</script>  --%>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/livevalidation/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/organization/organizationvalidation.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/organization/organizationlogin.js"></script>
</body>
</html>