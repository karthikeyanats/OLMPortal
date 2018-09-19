<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css"> 
#loginmodal {
	width: 350px;
	left: 57%;
}
</style>
</head> 

<body class="body">
 	<div class="row-fluid">
		<div class="span10 offset1"
			style="background-color: white; margin-top: 10px;margin-bottom: 10px; -moz-box-shadow: 0px 0px 2px 2px #ccc; -webkit-box-shadow: 0px 0px 2px 2px #ccc; box-shadow: 0px 0px 2px 2px #c;">
			<s:actionerror theme="bootstrap" />
			<s:actionmessage theme="bootstrap" />
			<s:fielderror theme="bootstrap" />
			<form class="form-horizontal" action="saveorganization" method="post"> 
				<div class="heading">
					<h2 style="color: #007FBF; font-size: 22px; padding-left: 10px;"
						class="form-heading">Master Registration</h2>
					<hr>
				</div>
				<div class="control-group">
					<label class="control-label" for="">Organization Name</label>
					<div class="controls">
						<input type="text" name="orgname" id="" class="span7" required
							placeholder="E.g. Hegde">
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="">Street1</label>
					<div class="controls">
						<input type="text" name="street1" id="" class="span7" required
							placeholder="E.g. kalkurichi">
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="">Street2</label>
					<div class="controls">
						<input type="text" name="street2" id="" class="span7" required
							placeholder="E.g. Nagar">
					</div>
				</div>
				
				 
				<div class="control-group">
					<label class="control-label" for="">City</label>
					<div class="controls">
						<input type="text" name="city" id="" class="span7" required
							placeholder="E.g. India">
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="">Country</label>
					<div class="controls">
						<input type="text" name="country" id="" class="span7" required
							placeholder="E.g. Ashwin">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="">Email</label>
					<div class="controls">
						<input type="text" name="email" id="" class="span7" required
							placeholder="E.g. gk@gmail.com">
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="">Phone no</label>
					<div class="controls">
						<input type="text" name="phone" id="" class="span7" required
							placeholder="E.g. 9949898989">
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="">Url</label>
					<div class="controls">
						<input type="text" name="url" id="" class="span7" required
							placeholder="E.g. www.igrandee.com">
					</div>
				</div>
				
				<div class="heading">
					<h2 style="color: #007FBF; font-size: 22px; padding-left: 10px;"
						class="form-heading">Personal Details</h2> 
					<hr>
				</div> 
				
				<div class="control-group">
					<label class="control-label" for="">First Name</label>
					<div class="controls">
						<input type="text" name="firstname" id="" class="span7" required
							placeholder="E.g. ashwinhegde">
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="">Last Name</label>
					<div class="controls">
						<input type="text" name="lastname" id="" class="span7" required
							placeholder="E.g. ashwinhegde">
					</div>
				</div>				
				 
				<div class="control-group">
					<label class="control-label" for="">Email</label>
					<div class="controls">
						<input type="text" name="useremail" id="" class="span7" required
							placeholder="E.g. gk@gmail.com">
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="">Contact Number</label>
					<div class="controls">
						<input type="text" name="usercontactno" id="" class="span7" required
							placeholder="E.g. 993939898">
					</div>
				</div>
				
 				<div class="control-group">
					<label class="control-label" for="">Username</label>
					<div class="controls">
						<input type="text" name="username" id="" class="span7" required
							placeholder="E.g. ashwinhegde">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="">Password</label>
					<div class="controls">
						<input type="password" name="password" id="" class="span7" required
							placeholder="Min. 8 Characters">
					</div>
				</div>
 			 	<div class="control-group">
					<div class="controls">
 						<button type="submit" class="btn btn-success">Sign Up</button>
 					</div>
				</div>
 				    
 			</form>
		</div>
	</div>


 
</body>
</html>
