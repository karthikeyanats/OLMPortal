<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="container">
		<div class="row-fluid pagestart">
			<div class="span12 contentbackgound">
				 <form class="well span6 offset3 forn form-horizontal" enctype="multipart/form-data" name="organizationform" method="post" id="organizationinfo" novalidate="novalidate">
					<fieldset>

						<legend class="textcenter" style="background-color: rgb(15, 84, 116) !important; color: white;">Create
							a New Organization</legend>

						<div class="tab-content">
							<div class="tab-pane active" id="basicinfo">
								<div class="control-group">
									<label class="control-label" for="textinput">Organization
										Name <span style="color: red;">*</span>
									</label> 
									
									<div class="controls">
									<input id="orgname" name="organizationname" type="text" maxlength="45" style="margin-left: 12px;">										
									<label for="file" class="orgname error" id="alertorgname"></label>							
                               </div>
							  </div>					
								
								<div class="control-group">
									<label class="control-label" for="filebutton">Organization
										Logo<span style="color: red;">*</span>
									</label> 
									<div class="controls">
									
									<input id="fileUpload" name="fileUpload" class="input-file" type="file" style="display: inline; margin-left: 12px;">
										 <label for="file" class="file error" id="alertLogo"></label>
                                 </div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="textinput">Full Name<span style="color: red;">*</span>
									</label>
									
								<div class="controls">	
									 <input id="fullname" name="orgfirstname" type="text" maxlength="45" style="margin-left: 12px;">
										
                               </div>
								</div>

								<!-- Text input-->
								
									<div class="control-group">
									<label class="control-label" for="textinput">Email<span style="color: red;">*</span>
									</label>
									
								<div class="controls">	
									 <input id="emailid" name="email" type="text" maxlength="45" style="margin-left: 12px;" placeholder="E.g:rams@igrandee.com">
									<label for="file" class="email error" id="alertemail"></label>		
                               </div>
								</div>
								
							
								

								<!-- Text input-->
								<div class="control-group">
									<label class="control-label" for="textinput">Password<span style="color: red;">*</span></label> 
									<div class="controls">	
										<input id="textpassword" name="orgpassword" type="password" maxlength="45" style="margin-left: 12px;">
								   </div>

								</div>
								<div class="control-group">
									<label class="control-label" for="textinput">Domain<span style="color: red;">*</span></label> 
									<div class="controls">
									 <input type="hidden" id="textinput" name="organizationurl" class=" domainurl">
									 <input id="domain" name="domain" type="text" class=" organizationurl" maxlength="45" style="margin-left: 12px;">
									 <span class="" for="textinput"><strong>.remedicloud.com</strong></span>
									</div>
								</div>
								
								
								
								
								
								<div class="control-group">
									<label class="control-label" for="textinput">Phone Number<span style="color: red;">*</span>
									</label>
									
								<div class="controls">	
									 <input id="phoneno" name="phonenumber" type="text" maxlength="10" style="margin-left: 12px;">
                               </div>
								</div>
								
								
							

								<div class="control-group">
									<label class="control-label" for="textinput">Size Of
										Your Team</label>
									<div class="controls"> <input id="textinput" name="sizeoforg" maxlength="4" type="text" style="margin-left: 13px;">
	                                </div>
								</div>
								
							</div>
						</div>
						
					</fieldset>
					<div class="control-group" style="border-top: 1px solid rgb(224, 219, 219); padding-top: 10px; margin-bottom: 5px;">
						<label class="control-label" for=""></label>
						<div class="controls">
							<input type="submit" style="height: auto;" id="createorg" class="btn btn-flat btn-blue orgcreate" value="Create New Organization">
						</div>
					</div>
					
					<input type="hidden" name="parentorgid" value="97" id="parentorgid">

				</form>
			</div>
		</div>
	</div>



</body>
</html>