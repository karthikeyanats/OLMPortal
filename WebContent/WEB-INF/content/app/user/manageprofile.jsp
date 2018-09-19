<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link
	href="<%=request.getContextPath()%>/assets/plugins/tagsinput/css/bootstrap-tagsinput.css"
	type="text/css" rel="stylesheet">
<link
	href="<%=request.getContextPath()%>/assets/plugins/datepicker/css/bootstrap-datepicker.min.css"
	type="text/css" rel="stylesheet">
<script id="messageemptytmpl" type="text/x-handlebars-template">
<div class='alert alert-danger alert-dismissible manageprofilecont' role='alert' >  
<strong>Warning!</strong> Mandatory fields are required.</div>
</script>
<script id="personalinfotmpl" type="text/x-handlebars-template">
{{#each .}}

<div class="row"> 
	<div class="col-md-12 pad0"> 
		<div class="col-md-4">
			<h3 class="marbottom15 size20">
				<font class="nottitle"></font>Personal Information
			</h3>
		</div> 
		<div class="col-md-8 righttext martop20"> 
			<h6 class="error">* Mandatory</h6>	
		</div>  
	</div>
</div>
		
<div class="form-horizontal whitebg pad10 manageprofilecont" >
	<div class="form-group">
		<label class="col-sm-3 righttext control-label">First Name<font class="error">*</font></label>
		<div class="col-sm-4"> 
			<input type="text" required="required" contenteditable="true"
				class="form-control  firstNameval" value="{{firstName}}" maxlength="25">
			<div class="alert alert-danger martopbottom10 pad10 alert-danger alertfirstname error-masker" role="alert"
				style="display: none;">
				<a class="alert-link">Please Enter the First Name</a>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 righttext control-label">Last Name </label>
		<div class="col-sm-4">
			<input type="text" required="required" contenteditable="true"
				class="form-control  lastNameval" value="{{lastName}}" maxlength="25">
            <div class="alert alert-danger martopbottom10 pad10  alertlastname error-masker" role="alert" style="display: none;">
				<a class="alert-link"></a>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 righttext control-label">Edu. Qualification
<font class="error">*</font> </label>
		<div class="col-sm-4">
			<input type="text" required="required" contenteditable="true"
				class="form-control eduqualificationval" value="{{eduqualification}}"
				maxlength="25">
			<div class="alert alert-danger martopbottom10 pad10 alerteduqalification error-masker" role="alert"
				style="display: none;">
				<a class="alert-link">Please Enter the Educational Qualification</a>
			</div>
		</div>
	</div>
	
	<div class="form-group">
		<label class="col-sm-3 righttext control-label">Date Of Birth<font class="error">*</font></label>
		<div class="col-sm-4">
			<input type="text" readonly required="required"
				contenteditable="true" class="form-control  dobval" value="{{dob}}">
			<div class="alert alert-danger martopbottom10 pad10 alertdob error-masker" role="alert"
				style="display: none;">
				<a class="alert-link">Please Select Date of Birth</a>
			</div>
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-3 righttext control-label">Gender </label>
		<div class="col-sm-4">

			 <div class="btn-group lefttext">
        			<button type="button"  class="btn genderbtn malebtn white">Male</button>
        			<button type="button" class="btn genderbtn femalebtn white">Female</button>        
      			</div>
 			 
			<input type="hidden" required="required" class="genderval"
				sex="{{sex}}">
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 righttext control-label">Email Address<font class="error">*</font></label>
		<div class="col-sm-4">
			<input type="email" name="email" required="required"
				contenteditable="true" class="form-control  emailval" value="{{userid}}"
				maxlength="45">
			<div class="alert alert-danger martopbottom10 pad10 alertemail error-masker" role="alert"
				style="display: none;">
				<a class="alert-link">Please Enter the Email Address</a>
			</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 righttext control-label">Contact Number<font class="error">*</font> </label>
		<div class="col-sm-4">
			<input type="text" required="required" contenteditable="true"
				class="form-control  contactnumberval" value="{{number}}" maxlength="15"
				min="0" onkeyup="numberonly()" placeholder="Ex:1234">
			<div class="alert alert-danger martopbottom10 pad10 alertcontact error-masker" role="alert" style="display: none;">
				<a class="alert-link">Please Enter the Contact Number</a>
			</div>
		</div>
	</div> 
	<div class="form-group">
		<label class="col-sm-3 righttext control-label">Select Photo</label>
		<div class="col-sm-4">
			<input type="file" id="profile-media" name="file"
				value="{{personphotourl}}" required="required">

			<div class="pull-right">
				<img
					src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{personphotourl}}&filetype=photo"
					width="100" height="50">
				<input type="hidden" value="{{personphotourl}}"
					id="person-photo-url" />
			</div>
			<!-- <p>NOTE : EXISTING BANNER WOULD BE USED IF DO NOT CHANGE FILE.</p> -->
		</div>
	</div>


	<div class="form-group">
		<label class="col-sm-3 righttext control-label">
		</label>
		<div class="col-sm-4">
			<span class="btn white btn-flat btn-blue saveprofilebtn"
			personid="{{personid}}" phoneid="{{phoneid}}" emailid="{{emailid}}"
			contactinfoid="{{contactinfoid}}">Save</span>
 		</div>
	</div>
 	 
</div>
{{/each}}
</script>
<script id="account-info-template" type="text/x-handlebars-template">
{{#if .}}
 
	<h3 class="marbottom15 size20 rightpanetitle">Bank Account Information</h3>
	<div class="form-horizontal whitebg pad10 manageprofilecont">
	<div class="form-group">
			<label class="col-sm-3 righttext control-label">Account Number<span  style="color: red;margin-right: 10px;">*</span></label>
			<div class="col-sm-4">
				<input type="password" onkeyup="numberonly()" placeholder="Account Number" required="required" contenteditable="true" class="form-control account-info-hidden-number" id="account-info-hidden-number" value="{{accountNumber}}" maxlength="25">
				<div class="alert alert-danger martopbottom10 pad10 alert-error account-info-hidden-number-error error-masker" id="account-info-hidden-number-error">Please enter valid account number</div>
				<div class="alert alert-danger martopbottom10 pad10 alert-error account-info-hidden-number-error-special error-masker" id="account-info-hidden-number-error-special">Do Not enter Special characters</div>
			</div>
		</div>

		<div class="form-group">
			<label class="col-sm-3 righttext control-label">Confirm Account Number<span style="color: red;margin-right: 10px;">*</span></label>
			<div class="col-sm-4">
				<input type="text" onkeyup="numberonly()" placeholder="Account Number" required="required" contenteditable="true" class="form-control account-info-account-number" id="account-info-account-number" value="{{accountNumber}}" maxlength="25" onpaste="return false;" >
				<div class="alert alert-danger martopbottom10 pad10 alert-error account-info-account-number error-masker" id="account-info-account-number-error">Please enter valid account number</div>
				<div class="alert alert-danger martopbottom10 pad10 alert-error account-info-account-number-error-same error-masker" id="account-info-account-number-error-same">Please enter same account number</div>
				<div class="alert alert-danger martopbottom10 pad10 alert-error account-info-account-number-error-special error-masker" id="account-info-account-number-error-special">Do Not enter Special characters</div>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-3 righttext control-label">IFSC Code/Routing Code <span style="color: red;margin-right: 11px;">*</span></label>
			<div class="col-sm-4">
				<input type="text" placeholder="IFSC code" required="required" contenteditable="true" class="form-control account-info-ifsc-code" id="account-info-ifsc-code"  value="{{ifscCode}}" maxlength="25">
				<div class="alert alert-danger martopbottom10 pad10 alert-error account-info-account-ifsc error-masker" id="account-info-ifsc-code-error">Please enter valid IFSC code of the bank</div>
				<div class="alert alert-danger martopbottom10 pad10 alert-error account-info-account-ifsc error-masker" id="account-info-account-ifsc">Do Not enter Special characters</div>
			</div>
		</div>
		 <div class="form-group">
			<label class="col-sm-3 righttext control-label">Bank Name<span style="color: red;margin-right: 10px;">*</span></label>
			<div class="col-sm-4">
				<input type="text" placeholder="Bank Name" required="required" contenteditable="true" class=" form-control account-info-bank-name" id="account-info-bank-name" value="{{bankName}}" maxlength="25">
				<div class="alert alert-danger martopbottom10 pad10 alert-error account-info-bank-name-error error-masker" id="account-info-bank-name-error">Please enter bank name</div>
				<div class="alert alert-danger martopbottom10 pad10 alert-error account-info-bank-name-special error-masker" id="account-info-bank-name-special">Do Not enter Special characters</div>
			</div>
		</div>
		
		 <div class="form-group">
			<label class="col-sm-3 righttext control-label">Branch Name<span style="color: red;margin-right: 10px;">*</span></label>
			<div class="col-sm-4">
				<input type="text" placeholder="Branch Name" required="required" contenteditable="true" class="form-control account-info-branch-name" id="account-info-branch-name" value="{{branchName}}" maxlength="25">
				<div class="alert alert-danger martopbottom10 pad10 alert-error account-info-branch-name-error error-masker" id="account-info-branch-name-error">Please enter branch name of the bank</div>
				<div class="alert alert-danger martopbottom10 pad10 alert-error account-info-branch-name-special error-masker" id="account-info-branch-name-special">Do Not enter Special characters</div>
			</div>
		</div> 
		

		<div class="form-group">
			<label class="col-sm-3 righttext control-label">Branch Location<span style="color: red;margin-right: 10px;">*</span></label>
			<div class="col-sm-4">
				<input type="text" placeholder="Branch Location" required="required" contenteditable="true" class="form-control account-info-branch-location" id="account-info-branch-location" value="{{location}}" maxlength="25">
				<div class="alert alert-danger martopbottom10 pad10 alert-error account-info-account-number  error-masker" id="account-info-branch-location-error">Please enter location of the bank</div>
				<div class="alert alert-danger martopbottom10 pad10 alert-error account-info-branch-location-special error-masker" id="account-info-branch-location-special">Do Not enter Special characters</div>
 			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 righttext control-label">Country<span style="color: red;margin-right: 10px;">*</span></label>
			<div class="col-sm-4">
				<select id="account-info-country" name="country" class="form-control account-info-country">
				
				</select>
				<div class="alert alert-danger martopbottom10 pad10 alert-error account-info-country-error error-masker" id="account-info-country-error">Please select the country</div>
			</div> 
		</div>

		<div class="form-group">
			<label class="col-sm-3 righttext control-label">
			</label>
			<div class="col-sm-4"> 
				<input type="hidden" id="account-info-account-id" value="{{id}}" />
				<span class="btn btn-flat white btn-blue save-bank-account-information" >Save</span>
 			</div>
		</div>
 	</div>
{{/if}}
</script>
<script id="country-template" type="text/x-handlebars-template">
<option value="">----select option-----</option>
{{#each .}}
<option value="{{name}}">{{name}}</option>
{{/each}}
</script>


<script id="passwordinfotmpl" type="text/x-handlebars-template">
<h3 class="marbottom15 size20">Change Password</h3>
<div class="form-horizontal whitebg pad10 manageprofilecont">
<div class="form-group">
	<label class="col-sm-3 righttext control-label">Current password  <span style="color: red;margin-right: 10px;">*</span></label>
	<div class="col-sm-4">
		<input type="password" placeholder="Old Password" required="required" contenteditable="true"
			class="col-md-12 form-control  oldpwdval" value="" maxlength="25">
	</div>
</div> 

<div class="form-group">
	<label class="col-sm-3 righttext control-label">New Password <span style="color: red;margin-right: 10px;">*</span></label>
	<div class="col-sm-4">
		<div class="input-group">
 	 		<input type="password" placeholder="New Password" required="required" contenteditable="true"
				class=" form-control  newpwdval" value="" maxlength="25" minlength="6">
			<span class="input-group-addon ligtgreenbg showpass"><a class="white"><i class="fa fa-eye widheight14"></i></a></span>
		</div>
		<span id="strengthmsg"></span>
		<div class="progcntrl" style="margin-bottom: 0px;display:none;">
			<div class="progress" style="height:4px!important;">
  				<div class="progress-bar progress-bar-success one" style="background-color: #5eb95e!important;"></div>
  				<div class="progress-bar progress-bar-success two" style="background-color: #5eb95e!important;"></div>
  				<div class="progress-bar progress-bar-warning three" style="background-color: #faa732 !important;"></div>
  				<div class="progress-bar progress-bar-danger four" style="background-color: #dd514c!important;"></div>
  				<div class="progress-bar progress-bar-danger five" style="background-color: #dd514c!important;"></div>
			</div>
		</div>
     	<div class="alert martopbottom10 pad10 alert-danger page-alert" id="alert-3" style="display: none;">
        	<button type="button" class="close"><span aria-hidden="true" class="close" >×</span></button>
        	<strong>Please enter minimum 6 characters</strong>
    	</div>
   		<div class="alert alert-danger martopbottom10 pad10" id="password1err" style="display: none;">
        	Please enter minimum 6 characters
		</div>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-3 righttext control-label">Retype New Password  <span style="color: red;margin-right: 10px;">*</span></label>
	<div class="col-sm-4">
		<input type="password" placeholder="Retype New Password" required="required" contenteditable="true"
			class="form-control confirmpwdval" value="" maxlength="25" minlength="5">
		<div class="alert alert-danger martopbottom10 pad10 " id="password2err" style="display:none;"></div>	
	</div>
</div>

<div class="form-group">
	<label class="col-sm-3 righttext control-label"></label>
	<button class="btn white btn-blue passwordresetbtn" >
		Update
	</button>
	<div class="alert alert-danger controls resetupdatebtn" role="alert" style="display:none;width: 85%;margin-left: 14px;">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<a class="alert-link">Once you have change the password, please sign out and again login your new password</a>
	</div>
</div>


<div class="row-fluid" >
	
</div>

  
</div>
</script>
<script id="myloginsinfotmpl" type="text/x-handlebars-template">
<h3 class="rightpanetitle">My Login Informations</h3>
<table class="table table-striped table-borderd whitebg manageprofilecont">
	<thead>
		<tr> 
			<th>UserId</th>
			<th>ProviderId</th>
            <th>Actions</th>
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td style="text-align:justify;"><i class="{{icon}}" style="font-size:x-large;color:{{color}};"></i> {{userId}}</td>
			<td>{{providerId}}</td>
			<td><a providerid={{providerId}} class="loginremove">Remove</a></td>
		</tr>
		{{/each}} {{else}}
		<tr>
			<td colspan="4" style="text-align: center;color:red" class="categorydescription">No Other Logins</td>
		</tr>
		{{/if}}
	</tbody>
</table>
</script>
<script id="messageinfotmpl" type="text/x-handlebars-template"> 
<h3 class="marbottom15 size20">Inbox<a class="pull-right btn btn-success btn-flat marbottom10" id="sendmsgto">Send Message to</a></h3>
<div style='min450'>
 <table class="table table-striped table-borderd whitebg manageprofilecont paginationtab">
	<thead>
		<tr>
			<th class="width60per">Message</th>
			<th>From</th>
			<th>Date</th>
			<th class="center">Actions</th>
		</tr>
	</thead> 
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td>
               <p>{{message}}</p>
            </td>
			<td>{{fromUserFirstName}}</td>
			<td>{{messagedate}}</td> 
			<td class="center"><a class="btn btn-primary btn-userinfo messageuserbtn"
				personname="{{fromUserFirstName}}"
				orgpersonid="{{fromUserpersonid}}"><i class="fa fa-reply"></i></a> <a
				class="btn btn-primary btn-userinfo moreinfouserbtn deleteuserbtn"
				orgpersonid="{{personid}}" messageid="{{messageid}}"><i class="fa fa-remove"></i></a></td>
		</tr>
		{{/each}} {{else}} 
		<tr>
			<td colspan="4" class="categorydescription">No Messages</td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>

</script>

<script id="sentMsgTmpl" type="text/x-handlebars-template"> 
<div style='min450'>
<h3 class="marbottom15 size20">Sent</h3>
<table class="table table-striped table-borderd whitebg manageprofilecont paginationtab">
	<thead>
		<tr>
			<th class="width60per">Message</th>
			<th>To</th>
			<th>Date</th>
			<th class="center">Actions</th>
		</tr>
	</thead>
	<tbody>
		{{#if .}} {{#each .}}
		<tr>
			<td >
              <p>{{message}}</p>
            </td>
			<td>{{toUserFirstName}}</td>
			<td>{{messagedate}}</td> 
			<td class="center"> <a	class="btn btn-primary btn-userinfo moreinfouserbtn deleteuserbtn"
				orgpersonid="{{personid}}" messageid="{{messageid}}"><i class="fa fa-remove"></i></a></td>
		</tr>
		{{/each}} {{else}} 
		<tr>
			<td colspan="4" class="categorydescription">No Messages</td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>

</script>
<script id="professionalinfotmpl" type="text/x-handlebars-template">
<h3 class="marbottom15 size20 ">Professional Information</h3>
<div class="manageprofilecont whitebg form-horizontal padtopbottom10">
<div id="professinfoform">
<div class="form-group">
  <label class="col-sm-3 righttext control-label" for="organizationname">Organization Name<font style="color: red; margin-right: 5px;">*</font> : </label>
  <div class="col-sm-4">
    <input id="organizationname" class="professinfoerror  form-control" mandatory="true" len="200" maxlength="200" spchar="notallowed" errorid="orgerror" name="organizationname" type="text" placeholder="Organization Name" value="{{organization}}" class="input-xlarge">    
	<div class="alert alert-danger martopbottom10 pad10 error-maskpro" id="orgerror" style="display: none;"></div> 
 </div>

</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-sm-3 righttext control-label" for="designation">Designation<font style="color: red; margin-right: 5px;">*</font> : </label>
  <div class="col-sm-4">
    <input id="designation" class="professinfoerror form-control" len="200" maxlength="200" spchar="notallowed" mandatory="true" errorid="designerror" name="designation" type="text" placeholder="Designation" value="{{designation}}" class="input-xlarge">    
	<div class="alert alert-danger martopbottom10 pad10 error-maskpro" id="designerror" style="display: none;"></div>  
</div>

</div>

<!-- Textarea -->
<div class="form-group">
  <label class="col-sm-3 righttext control-label" for="aboutauthor">About Author<font style="color: red; margin-right: 5px;">*</font> : </label>
  <div class="col-sm-4">                     
    <textarea id="aboutauthor" class="professinfoerror form-control" len="300" maxlength="300" style="resize:none" mandatory="true" errorid="aboutauthorerr" name="aboutauthor" placeholder="about Author">{{aboutauthor}}</textarea>
<div class="alert alert-danger martopbottom10 pad10 error-maskpro" id="aboutauthorerr" style="display: none;"></div>  
</div>

</div>

<div class="form-group">
  <label class="col-sm-3 righttext control-label" for="awards">Awards : </label>
  <div class="col-sm-4">                     
 <input id="award" name="awards" class="professinfoerror form-control" len="300" maxlength="300" spchar="notallowed" mandatory="false" errorid="awarderr" type="text" placeholder="Awards" value="{{award}}" class="input-xlarge">
<div class="alert alert-danger martopbottom10 pad10 error-maskpro" id="awarderr" style="display: none;"></div> 
 </div>

</div>

<div class="form-group">
	<label class="col-sm-3 righttext control-label" for="websiteurl">Links : </label>
	<div class="col-sm-4">
		<div class="input-group">
 	 		<span class="input-group-addon white ligtgreenbg"><i class="fa fa-globe widheight14"></i></span> 
	 		<input class="input-xlarge professinfoerror form-control url" mandatory="false" 
				len="200" maxlength="200" errorid="orgurlerr" name="websiteurl" id="prependedInput" type="text" value="{{orgurl}}" placeholder="www.yourwebsites.com/..." >
		</div>
		<div class="alert alert-danger martopbottom10 pad10 error-maskpro" id="orgurlerr" style="display: none;"></div>        
  	</div>
	
</div>
 
<div class="form-group">
	<label class="col-sm-3 control-label"></label>
	<div class="col-sm-4">
		<div class="input-group">
  			<span class="input-group-addon white fbbg"><i class="fa fa-facebook widheight14"></i></span>
  			<input class="professinfoerror form-control url" mandatory="false" len="200" 
				maxlength="200" errorid="fburlerr" name="facebookurl" 
				type="text" value="{{facebook}}" placeholder="www.facebook.com/..." >
		</div>
		<div class="alert alert-danger martopbottom10 pad10 error-maskpro" id="fburlerr" style="display: none;"></div> 	
	</div>
	
</div>

<div class="form-group">
<label class="col-sm-3 control-label"></label>
	 <div class="col-sm-4">
 	<div class="input-group">
 	 	<span class="input-group-addon white twittbg"><i class="fa fa-twitter widheight14"></i></span> 
	 	<input class="input-xlarge professinfoerror col-md-12 form-control url" len="200"  mandatory="false" maxlength="200" errorid="twurlerr" name="twiiterurl" id="prependedInput" type="text" value="{{twitter}}" placeholder="www.twitter.com/..." >
	</div>
	<div class="alert alert-danger martopbottom10 pad10 error-maskpro" id="twurlerr" style="display: none;"></div>
</div>

</div>

<div class="form-group">
	<label class="col-sm-3 control-label"></label>
	<div class="col-sm-4">
 		<div class="input-group">
 	 		<span class="input-group-addon white linkinbg"><i class="fa fa-linkedin widheight14"></i></span> 
	 		<input class="input-xlarge professinfoerror col-md-12 form-control url" mandatory="false" len="200" maxlength="200" errorid="lnurlerr" name="linkedinurl" id="prependedInput" type="text" value="{{linkedin}}" placeholder="www.LinkedIN.com/..." >
		</div>
	<div class="alert alert-danger martopbottom10 pad10 error-maskpro" id="lnurlerr" style="display: none;"></div>	
	</div>	
</div>

<div class="form-group">
	<label class="col-sm-3 control-label"></label>
	<div class="col-sm-4">
	 	<a class="btn btn-blue saveprofo white">Save</a>
	</div>
</div>

</div>
<div id="authorprofessproftable"></div>
</div>
</script>

<script id="authorprofessproftmpl" type="text/x-handlebars-template">

<div class="col-md-12 min450 whitebg">
<div class="col-md-4 col-md-offset-3 centertext well">
<h4 class="centertext">Preview</h4>
		<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{photourl}}&filetype=photo" style="height: 120px;" width="120" height="120" border="0" class="img-circle"><br><br>
<span style="font-weight: bolder; color: rgb(34, 96, 206);"  class="breakword">{{authorname}}</span><br>
{{#if organization}}
<span style="font-weight: bolder; color: rgb(34, 96, 206);" class="breakword"><b>{{designation}},{{organization}}</b></span><br><br>
{{#if aboutauthor}}
<span style="font-weight: normal" class="breakword">{{aboutauthor}}</span><br><br>
{{/if}}
{{#if award}}
<span style="font-weight: bolder;color: rgb(185, 79, 104);" class="breakword">Awarded as {{award}}</span><br><br>
{{/if}}
<div class="social">
{{#if facebook}}
<a href="http://{{facebook}}" target="new" class="fbbg roundicon white breakword"><i class="fa fa-facebook"></i></a>
{{/if}}
{{#if twitter}}
<a href="http://{{twitter}}" target="new" class="twittbg roundicon white breakword"><i class="fa fa-twitter"></i></a>
{{/if}}
{{#if linkedin}}
<a href="http://{{linkedin}}" target="new" class="linkinbg roundicon white breakword"><i class="fa fa-linkedin"></i></a>
{{/if}}
{{#if orgurl}}
<a href="http://{{orgurl}}" target="new" class="ligtgreenbg roundicon white breakword"><i class="fa fa-globe"></i></a>
{{/if}}
{{/if}}
</div>
</div>
</div>

</script>

<script id="notificationtmpl" type="text/x-handlebars-template">
<h3 class="marbottom15 size20">Notification Entry</h3>
<div id="notifyvalidation"></div>
 <div class="form-horizontal whitebg pad10">
	<div class="form-group">
		<div class="col-md-8">
			<textarea class="col-md-12 form-control" placeholder="Enter your notification." rows="12" id="notificationdescmessage" maxlength="500"  style="resize: none; overflow: auto;"></textarea>
		</div>
		<div class="col-md-4">
			<a class="btn btn-blue white marbottom10 col-md-10 notificationbtnstyle notificationbtn" id="ALL">Send To All</a>
			<a class="btn btn-blue white marbottom10 col-md-10 notificationbtnstyle notificationbtn" id="LEARN">Send To Course Learning Users</a>
			<a class="btn btn-blue white marbottom10 col-md-10 notificationbtnstyle notificationbtn" id="PAID">Send To Paid Users</a>
			<a class="btn btn-blue white marbottom10 col-md-10 notificationbtnstyle notificationbtn" id="PENDING">Send To Payment pending users</a>
		</div>
	</div>
</div> 
</script>
<script id="otherNotificationTmpl" type="text/x-handlebars-template">
<div class="min450">
<div class="row"> 
	<div class="col-md-12 pad0" > 
		<div class="col-md-9">
			<h3 class="marbottom15 size20" >
				<font class="nottitle" ></font>&nbsp;Notifications
			</h3>
		</div> 
		<div class="col-md-3 righttext martop10" >
			<select class="form-control col-md-3 notificationtypeselect">
				<option value="registered">Registered Users</option>
				<option value="enrolled">Enrolled Users</option>
				<option value="paid">Paid Users</option>
				<option value="author">Authors</option>
				<option value="pendingpayment">Payment Pending Users</option>
			</select> 
			
		</div>  
	</div>
</div>
<table class="table table-striped table-borderd whitebg manageprofilecont paginationtab" >
	<thead>
		<tr> 
			<th>Notification</th>
			<th>No of Users</th>
			<th>Sent On</th>			
		</tr>
	</thead>
	<tbody>
{{#if othernotifications}} {{#each othernotifications}}
		<tr>
			<td style="text-align:justify;width:60%;">
                 <p>{{notificationdescription}}</p>
            </td>
			<td>{{orgpersonid}}</td>
			<td>{{notificationdate}}</td>			
		</tr>
		{{/each}} {{else}}
		<tr style="min450">
			<td colspan="3">
				<p class="center categorydescription" style="color:red">No Notifications</p></td>  
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>
<script id="myNotificationTmpl" type="text/x-handlebars-template">
<div style='min450'> 
<h3 class="marbottom15 size20">My Notification</h3>
<table class="table table-striped table-borderd whitebg manageprofilecont paginationtab">
	<thead>
		<tr>
		<th>S.No</th>	
		<th>Notification</th>
		<th>From</th>
		<th>Received Date</th>			
		</tr>
	</thead>
	<tbody>
{{#if .}} 
{{#each .}}
		<tr revstatus={{receivedstatus}}>
			<td>{{math @index "+" 1}}</td>
			<td style="text-align:justify;width:60%;">
               <p>{{notificationdescription}}</p>
            </td>
			<td >{{fromUserName}}</td>
			<td>{{notificationdate}}</td>			
		</tr>
		{{/each}} {{else}}
		<tr style="min450">
			<td colspan="3">
				<p class="center categorydescription" style="color:red">No Notifications</p>
			</td>
		</tr>
		{{/if}}
	</tbody>
</table>
</div>
<div class='row'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>

</script>
<script id="notificationdatanotfoundtmpl"
	type="text/x-handlebars-template">
<h5 class="errormessagetext">No Notification are Available Here....</h5>
</script>
<script id="nomessagestmpl" type="text/x-handlebars-template">
<h3 style="color:red;">No Messages Found</h3>
</script>
<script id="messagestmpl" type="text/x-handlebars-template">
{{#each .}}
<p class="btn btn-flat btn-gray messageval">{{message}}</p><br>
{{/each}}
</script>
<script id="validationtmpl" type="text/x-handlebars-template">
<div class='alert alert-danger alert-dismissible manageprofilecont' role='alert' >
   <strong>Warning!</strong> Empty Notifications are not Allowed.</div>
</script>
<script id="nodatampl" type="text/x-handlebars-template">
<div class='alert alert-danger alert-dismissible manageprofilecont' role='alert' >
  <strong>Warning!</strong> No students available</div>
</script>
<script id="planvalidationtmpl" type="text/x-handlebars-template">
<div class='alert alert-danger alert-dismissible manageprofilecont' role='alert' >
   <strong>Warning!</strong> Plan Name already exists.</div>
</script>
<script id="professprofilealerttmpl" type="text/x-handlebars-template">
<div class="alert alert-success alerter" >
<a href="#" class="close alertclose" data-dismiss="alert">
   &times;
</a>
<strong id="status" >{{message}}</strong> 
</div>
</script>
</head>
<body>

	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String roleName = userDetailsAdaptor.getUser().getProperty(
				"rolename");
		String orgstatus = userDetailsAdaptor.getUser().getProperty(
				"orgstatus");
		String personid = userDetailsAdaptor.getUser().getProperty(
				"personid");
		String bankinfo = request.getParameter("bankinfo");
		String courseid = request.getParameter("courseid");
	%>
	<input type="hidden" id="hidrole" value="<%=roleName%>">
	<input type="hidden" id="personid" value="<%=personid%>">
	<input type="hidden" id="orgstatus"
		value="<%=userDetailsAdaptor.getUser().getProperty("orgstatus")%>">

	<div class="container-fluid ">
		<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
				<a>Preferences</a>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-3 pad0 martop10">
					<ul class="pad0">
						<li class="leftsideliheader">
							<h3>
								Manage Profile
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#pinfodiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>
						</li>
						<div id="pinfodiv" class="collapse navbar-collapse pad0">
							<li class="leftsideli personalinfolink hover active">Personal
								Information</li>
							<li class="leftsideli profinfolink hover">Professional
								Information</li>
							<li class="leftsideli bank-info-link hover">Bank Account
								Information</li>
							<li class="leftsideli changepwdlink hover">Change Password</li>
							<%
								if (orgstatus.equalsIgnoreCase("M")
										&& roleName.equalsIgnoreCase("admin")) {
								} else {
							%>
							<li class="leftsideli myloginslink hover">Social Login
								Accounts</li>
							<%
								}
							%>
						</div>
					</ul>
					<ul class="pad0">
						<li class="leftsideliheader">
							<h3>
								Messages
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#msginfodiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>
						</li>
						<div id="msginfodiv" class="collapse navbar-collapse pad0">
							<li class="leftsideli inboxmessageslink hover"><span
								id="msghidden" style="display: none;"><%=request.getParameter("ref")%></span>Inbox</li>
							<li class="leftsideli sentmessageslink hover">Sent</li>
						</div>
					</ul>
					<ul class="pad0">
						<li class="leftsideliheader">
							<h3>
								Notifications
								<button type="button" class="navbar-toggle mar0 pad0"
									data-toggle="collapse" data-target="#notinfodiv"
									aria-expanded="false">
									<span class="sr-only">Toggle navigation</span> <i
										class="fa fa-bars"></i>
								</button>
							</h3>
						</li>
						<div id="notinfodiv" class="collapse navbar-collapse pad0">
							<li class="leftsideli hover mynotificationslink">My
								Notifications <span id="nothidden" style="display: none;"><%=request.getParameter("ref")%></span>
							</li>
							<!-- <li class="leftsideli hover notification">Notification Entry</li> -->
							<li class="leftsideli hover othernotlink">Sent
								Notifications</li>
						</div>
					</ul>
				</div>
				<div class="col-md-9 pad0 padleft10">
					<div id="professprofilealert"></div>
					<div id="personalinfotable"></div>
				</div>
			</div>
		</div>
	</div>
	<span class="rolefind" rolename="<%=roleName%>"
		bankinfo="<%=bankinfo%>" courseid="<%=courseid%>"></span>


	<div class="modal fade" id="successmessagemodel">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h3>Message</h3>
				</div>
				<div class="modal-body">
					<p class="categorydescription ">Information Saved</p>
				</div>
				<div class="modal-footer">
					<a class="btn btn-flat btn-blue" data-dismiss="modal"
						aria-hidden="true">Close</a>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="messageinfomodal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h3 class="size20 martop10">Message</h3>
				</div>
				<div class="modal-body">
					<p class="categorydescription msginfo"></p>
				</div>
			</div>
		</div>
	</div>
	<div class="popwindow" style="display: none;">
		<div class="row chathead pad5">
			<div class="col-md-9 chatername"></div>
			<div class="col-md-3 closechatwindowbtn">
				<i class="fa fa-remove"></i>
			</div>
		</div>
		<div class="row" id="messagetable"></div>
		<div class="row chatfooter">
			<div class="input-append"
				style="margin-bottom: 0px; border: 0px; border-top: 1px solid gray;">
				<input class="col-md-9 chatmessage" id="appendedInputButton"
					chatterpersonid="" type="text" placeholder="Enter Message" maxlength="500">
				<a class="col-md-3 btn sendmessagebtn btn-primary"
					chatterpersonid="">Send</a>
			</div>
		</div>
	</div>
	<div id="sendmsgmodal" class="modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 id="myModalLabel" class="modal-title">Message</h4>
				</div>
				<div class="modal-body">
					<p id="messageempty"></p>
					<form class="form-horizontal" action="" method="post" name="">
					<input type="hidden" name="personid" id="personid" value="">
						<div class="form-group">
							<label class="control-label col-md-5 righttext">To <font
								color="red">*</font></label>
							<div class="col-md-6" id="tagidss">
								<input type="text" class="form-control" style="display: none;" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-5 righttext">Message <font
								color="red">*</font></label>
							<div class="col-md-6">
								<textarea class="messagetosend form-control" id="messagetosend"
									placeholder="Enter your message here...." cols=""></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-flat closemodal" data-dismiss="modal"
						aria-hidden="true">Close</button>
					<button class="btn btn-blue btn-flat sendtouser" id="sendtouser">Save
					</button>
				</div>
			</div>
		</div>
	</div>


	<div id="successmodal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="myModalLabel">Information</h3>
				</div>
				<div class="modal-body">
					<p>Message Sent successfully</p>
				</div>
			</div>
		</div>
	</div>
	<form action="" name="banktocourseform" method="post">
		<input type="hidden" name="courseid" value="">
	</form>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/users/manageprofile.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/users/notifications.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/assets/plugins/tagsinput/js/bootstrap-tagsinput.js"
		type="text/javascript"></script>
	<script
		src="<%=request.getContextPath()%>/assets/plugins/tagsinput/js/typeahead.bundle.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/plugins/datepicker/js/bootstrap-datepicker.min.js"></script>

</body>
</html>