<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ILS Education</title>
<link href="<%=request.getContextPath()%>/assets/app/css/datepicker.css"
type="text/css" rel="stylesheet">
<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/bootstrap-datepicker.min.js"></script>   
		 
<style type="text/css">
#loginmodal {
	width: 350px; 
	left: 57%;
}; 
p{
text-align: justify;
}
.titleh3{
padding-left: 0px !important;
margin-top: 0px;
}
.modal-body{
padding-top: 5px !important;
}
.termsli{
list-style: disc;
}
.termsli:hover{
background: none !important;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		$('#userpassword').change(function() {
			passwordCheckMtd();
		});
	});

	function passwordCheckMtd() {
		if ($('#userpassword').val().length < '8') {
			$('#alertBoxPassword').show();
			$('#alertBoxPassword').removeClass('alert-success');
			$('#alertBoxPassword').addClass('alert-danger');
			$('#alertBoxPasswordmsg').text(
					'Password Value Must not be less than 8 Charactors !	');
		} else {
			if($('#userpassword').val().length == '8'||$('#userpassword').val().length >= '8')
			{
				$('#alertBoxPassword').hide();		
			}
		}
	}
	
	function checkUsername(){  
	 	$.ajax({ 
	        type: "POST",
	        url: "checkUserName",
	        data:{username:$('#usernameId').val()}, 
	        success: function(data, textStatus, jqXHR){  
	        	if(data == "Exits"){
	        		$('#alertBox').show(); 
	        		$('#alertBox').removeClass("alert alert-success").addClass("alert alert-danger");
	        		$('#msg').text("The username has already been used.");
	         		$('#usernameId').val('');     
	         	} else {
	         		$('#alertBox').hide();  
	         	}
	        }, 
	        error: function(jqXHR, textStatus, errorThrown){
	        	console.log("ManageProfile Error : ");
	        }
		});
	}
</script>

</head>

<body class="body">
 	
		<div class="span12" style="padding: 10px;">
		<div class="row-fluid">
			<h3 class="coursepaymentsubtitle">You have
				Selected the Following Course</h3>
			<hr>
			<div class="row-fluid">
				<div class="span12">
					<div class="span2 offset1">

						<h5 class="coursepaymentvalue">Course Title </h5>
					</div>
					<div class="span6">
						<h5 class="coursepaymentvalue"> <s:property	value="coursetitle" />
						</h5>

					</div>
				</div> 
			</div>
			<div class="row-fluid">
				<div class="span12">
					<div class="span2 offset1">

						<h5 class="coursepaymentvalue">Course Category </h5>
					</div>
					<div class="span6">
						<h5 class="coursepaymentvalue">  <s:property	value="coursecategoryname" />
						</h5>

					</div>
				</div>
			</div>
			<div class="row-fluid">
				<div class="span12">
					<div class="span2 offset1">
						<h5 class="coursepaymentvalue"> Course Price  </h5>
					</div>
					<div class="span6">
						<h5 class="coursepaymentvalue">
						
						<s:if  test="price=='Free'">
						Free
						</s:if>
					
						<s:else>
    					$<s:property value="price"/>
						</s:else>
						
						</h5>

					</div>
				</div> 
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span10 offset1"
			style="margin-top: 10px;margin-bottom: 10px;">
			<s:actionerror theme="bootstrap" />
			<s:actionmessage theme="bootstrap" />
			<s:fielderror theme="bootstrap" />
			<form class="form-horizontal" action="signUpCreate" method="post">
				<div class=""> 
					<h2 style="color: #007FBF; font-size: 22px; padding-left: 10px;"
						class="form-heading">Sign Up</h2>
					<hr>
				</div>
				<div class="control-group">
					<label class="control-label" for="">First Name</label>
					<div class="controls">  
						<input type="text" name="firstname" id="" class="span7" required
							placeholder="E.g. Ashwin">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="">Last Name</label>
					<div class="controls">
						<input type="text" name="lastname" id="" class="span7" required
							placeholder="E.g. Hegde">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="">Email</label>
					<div class="controls">
						<input type="email" name="emailid" id="" class="span7" required
							placeholder="E.g. ashwinh@cybage.com"> 
					</div> 
				</div>
				<div class="control-group">   
					<label class="control-label" for="">Username</label>     
					<div class="controls">
						<input type="text" name="username" value="" id="usernameId" onblur="checkUsername()" class="span7" required placeholder="E.g. ashwinhegde">
					</div> 
				</div>
				<div class="alert alert-success" hidden="true" id="alertBox">
					<strong>Message!</strong>
					<command id="msg"></command> 
				</div>   
			    
			    <div class="alert alert-success" hidden="true" id="alertBoxPassword">
					<strong>Message!</strong>
					<command id="alertBoxPasswordmsg"></command> 
				</div> 
				
				<div class="control-group">
					<label class="control-label" for="">Password</label>
					<div class="controls">
						<input type="password" name="password" id="userpassword" class="span7" required
							placeholder="Min. 8 Characters">
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<label class="checkbox"> <input type="checkbox" required="required"> I
							agree all your <a href="#termsandconditionsmodal" data-toggle="modal">Terms of Services</a>
						</label>
						<button type="submit" class="btn btn-success">Sign Up</button>
						
					</div>
				</div>
				<div class="controls">
					<label>Already have an account? <a href="#loginmodal"
						data-toggle="modal" style="text-decoration: underline;"> Sign
							in.</a></label>
				</div>
				<s:if  test="price=='Free'">
					<s:hidden   name="targetUrl" value="/courseEnrollment" />
						</s:if>
					
						<s:else>
    					    <s:hidden   name="targetUrl" value="/coursepayment" /> 
						</s:else>
						
						</h5>   
			 
				
 				<s:hidden name="courseid" value="%{courseid}"></s:hidden>
				<s:hidden name="coursetitle" value="%{coursetitle}"></s:hidden>
				<s:hidden name="price" value="%{price}"></s:hidden>
				<s:hidden name="priceid" value="%{priceid}"></s:hidden>
			</form>
		</div>
	</div>



	<div class="modal hide" id="loginmodal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">x</button>
			<h3>Login to Learn this course</h3>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<s:form action="j_spring_security_check" theme="bootstrap"
						cssClass="loginformclass form-vertical">
						<s:textfield label="User Name" name="j_username"
							tooltip="Enter your User Name here" />
						<s:password label="Password" name="j_password"
							tooltip="Enter your Password here" />
							
 						 <s:hidden   name="targetUrl" value="/coursepayment" />  
 						<s:submit value="Sign In" cssClass="btn btn-primary" />						
						<s:hidden name="courseid" value="%{courseid}"></s:hidden>
						<s:hidden name="coursetitle" value="%{coursetitle}"></s:hidden>
						<s:hidden name="price" value="%{price}"></s:hidden>
						<s:hidden name="priceid" value="%{priceid}"></s:hidden>
					</s:form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal hide" id="forgotpassword">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">x</button>
			<h3>Forgot Password</h3>
		</div>
		<div class="alert alert-success" hidden="true" id="alertBoxforgetpasswd">
			<strong>Message!</strong>
			<command id="alertBoxforgetpasswdmsg"></command>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
				<div class="span12">
					<s:form theme="bootstrap" cssClass="well form-vertical">

						<s:textfield label="Username" name="forgetusername" id="forgotusernameId"
							requiredLabel="Please fill the email address"
							tooltip="Please enter the email associated with your account" />
						<a class="btn btn-success" style="" value="Send New Password"
							cssClass="btn-u" onclick="getForgetPassword()">Send New
							Password</a> or <a class="btn btn-success" style="" value="Login"
							cssClass="btn-u">Login</a>

					</s:form>
				</div>
			</div>
		</div>
	</div>
	<div id="termsandconditionsmodal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h2 id="myModalLabel" class="titleh3">Terms and Conditions</h2>
  </div>
  <div class="modal-body">

<h3 class="titleh3">Agreement Between User and Institute for Lean Systems</h3>
 
  <p >
ILS Website is comprised of various Web pages operated by Institute for Lean Systems.  
  </p>
 <p >
 Institute for Lean Systems Website (ILS) is offered to you conditioned on your acceptance 
without modification of the terms, conditions and notices contained herein. Your use of the ILS 
Website constitutes your agreement to all such terms, conditions and notices. 
 </p>

<h3 class="titleh3">Modification of These Terms of Use </h3>


<p>
ILS reserves the right to change the terms, conditions and notices under which the ILS Website 
is offered, including but not limited to the charges associated with the use of the ILS Website.
</p>
 <h3 class="titleh3">
 Links to Third Party Sites
 </h3>

 
<p>
The ILS Website may contain links to other Websites ("€oeLinked Sites"€•). The Linked Sites 
are not under the control of ILS and ILS is not responsible for the contents of any Linked Site, 
including without limitation any link contained in a Linked Site, or any changes or updates to a 
Linked Site. ILS is not responsible for webcasting or any other form of transmission received 
from any Linked Site. ILS is providing these links to you only as a convenience and the inclusion 
of any link does not imply endorsement by ILS of the site or any association with its operators.
</p>
 
<h3 class="titleh3">No Unlawful or Prohibited Use</h3>
 
<p>
As a condition of your use of the ILS Website, you warrant to ILS that you will not use the ILS 
Website for any purpose that is unlawful or prohibited by these terms, conditions and notices. 
You may not use the ILS Website in any manner which could damage, disable, overburden, or 
impair the ILS Website or interfere with any other partyâ€™s use and enjoyment of the ILS 
Website. You may not obtain or attempt to obtain any materials or information through any 
means not intentionally made available or provided for through the ILS Websites. 
</p>


<h3 class="titleh3">Use of Communication Services </h3>
<p>
The ILS Website may contain bulletin board services, chat areas, news groups, forums, 
communities, personal web pages, calendars and/or other message or communication facilities 
designed to enable you to communicate with the public at large or with a group (collectively, 
"€oeCommunication Services"€•), you agree to use the Communication Services only to post, 
send and receive messages and material that are proper and related to the particular 
Communication Service. By way of example, and not as a limitation, you agree that when using 
a Communication Service, you will not: 

</p>
<ul>
<li class="termsli">Defame, abuse, harass, stalk, threaten or otherwise violate the legal rights (such as 
rights of privacy and publicity) of others.</li>
<li class="termsli">Publish, post, upload, distribute or disseminate any inappropriate, profane, defamatory, 
infringing, obscene, indecent or unlawful topic, name, material or information. </li>
<li class="termsli">Upload files that contain software or other material protected by intellectual property 
laws (or by rights of privacy of publicity) unless you own or control the rights thereto or 
have received all necessary consents. </li>
<li class="termsli">Upload files that contain viruses, corrupted files, or any other similar software or 
programs that may damage the operation of another's computer. </li>
<li class="termsli">Advertise or offer to sell or buy any goods or services for any business purpose, unless 
such Communication Service specifically allows such messages.</li>
<li class="termsli">Conduct or forward surveys, contests, pyramid schemes or chain letters.</li>
<li class="termsli">Download any file posted by another user of a Communication Service that you know, or 
reasonably should know, cannot be legally distributed in such manner.</li>
<li class="termsli">Falsify or delete any author attributions, legal or other proper notices or proprietary 
designations or labels of the origin or source of software or other material contained in a 
file that is uploaded.</li>
<li class="termsli">Restrict or inhibit any other user from using and enjoying the Communication Services. 
</li>
<li class="termsli">Violate any code of conduct or other guidelines which may be applicable for any 
particular Communication Service. </li>
<li class="termsli">Harvest or otherwise collect information about others, including e-mail addresses without 
their consent.</li>
<li class="termsli">Violate any applicable laws or regulations.</li>
</ul>



<p>
ILS has no obligation to monitor the Communication Services. However, ILS reserves the right 
to review materials posted to a Communication Service and to remove any materials in its sole 
discretion. ILS reserves the right to terminate your access to any or all of the Communication 
Services at any time without notice for any reason whatsoever. 
</p>
 <p>
 ILS reserves the right at all times to disclose any information as necessary to satisfy any 
applicable law, regulation, legal process or governmental request, or to edit, refuse to post or to 
remove any information or materials, in whole or in part, in ILS'€™s sole discretion. 
 </p>
 
  <p>
 Always use caution when giving out any personally identifying information about yourself or your 
children in any Communication Service. ILS does not control or endorse the content, messages 
or information found in any Communication Service and, therefore, ILS specifically disclaims 
any liability with regard to the Communication Services and any actions resulting from your 
participation in any Communication Service. Managers and hosts are not authorized ILS 
spokespersons and their views do not necessarily reflect those of ILS. 
 </p>
 
  <p>
 Materials uploaded to a Communication Service may be subject to posted limitations on usage, 
reproduction and/or dissemination. You are responsible for adhering to such limitations if you 
download the materials. 
 </p>
<h3 class="titleh3">
Materials Provided to ILS or Posted at ILS Website
</h3>
<p>ILS does not claim ownership of the materials you provide to ILS (including feedback and 
suggestions) or post, upload, input or submit to any ILS Website or its associated services 
(collectively "€oeSubmissions"€•). However, by posting, uploading, inputting, providing or 
submitting your Submission you are granting ILS, its affiliated companies and necessary 
sublicenses permission to use your Submission in connection with the operation of their Internet 
businesses including, without limitation, the rights to: copy, distribute, transmit, publicly display, 
publicly perform, reproduce, edit, translate and reformat your Submission; and to publish your 
name in connection with your Submission.</p>
<p>No compensation will be paid with respect to the use of your Submission, as provided herein. 
ILS is under no obligation to post or use any Submission you may provide and may remove any 
Submission at any time in ILS'€™s sole discretion. </p>
<p>By posting, uploading, inputting, providing or submitting your Submission you warrant and 
represent that you own or otherwise control all of the rights to your Submission as described in 
this section including, without limitation, all the rights necessary for you to provide, post, upload, 
input or submit the Submissions.</p>

<h3 class="titleh3">Liability Disclaimer</h3>

<p>THE INFORMATION, SOFTWARE, PRODUCTS AND SERVICES INCLUDED IN OR 
AVAILABLE THROUGH THE ILS WEBSITE MAY INCLUDE INACCURACIES OR 
TYPOGRAPHICAL ERRORS. CHANGES ARE PERIODICALLY ADDED TO THE 
INFORMATION HEREIN. ILS AND/OR ITS SUPPLIERS MAY MAKE IMPROVEMENTS 
AND/OR CHANGES IN THE ILS WEBSITE AT ANY TIME. ADVICE RECEIVED VIA THE ILS 
WEBSITE SHOULD NOT BE RELIED UPON FOR PERSONAL, MEDICAL, LEGAL OR 
FINANCIAL DECISIONS AND YOU SHOULD CONSULT AN APPROPRIATE PROFESSIONAL 
FOR SPECIFIC ADVICE TAILORED TO YOUR SITUATION.</p>
<p>ILS AND/OR ITS SUPPLIERS MAKE NO REPRESENTATIONS ABOUT THE SUITABILITY, 
RELIABILITY, AVAILABILITY, TIMELINESS AND ACCURACY OF THE INFORMATION, 
SOFTWARE, PRODUCTS, SERVICES AND RELATED GRAPHICS CONTAINED ON THE ILS 
WEBSITE FOR ANY PURPOSE. TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE 
LAW, ALL SUCH INFORMATION, SOFTWARE, PRODUCTS, SERVICES AND RELATED 
GRAPHICS ARE PROVIDED "AS IS"€• WITHOUT WARRANTY OR CONDITION OF ANY 
KIND. ILS AND/OR ITS SUPPLIERS HEREBY DISCLAIM ALL WARRANTIES AND 
CONDITIONS WITH REGARD TO THIS INFORMATION, SOFTWARE, PRODUCTS, 
SERVICES AND RELATED GRAPHICS, INCLUDING ALL IMPLIED WARRANTIES OR 
CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, TITLE 
AND NON-INFRINGEMENT. </p>
<p>TO THE MAXIMUM EXTENT PERMITTED BY APPLICABLE LAW, IN NO EVENT SHALL ILS 
AND/OR ITS SUPPLIERS BE LIABLE FOR ANY DIRECT, INDIRECT, PUNITIVE, 
INCIDENTAL, SPECIAL, CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER 
INCLUDING, WITHOUT LIMITATION, DAMAGES FOR LOSS OF USE, DATA OR PROFITS,
ARISING OUT OF OR IN ANY WAY CONNECTED WITH THE USE OR PERFORMANCE OF 
THE ILS WEBSITE, WITH THE DELAY OR INABILITY TO USE THE ILS WEBSITE OR 
RELATED SERVICES, THE PROVISION OF OR FAILURE TO PROVIDE SERVICES, OR FOR 
ANY INFORMATION, SOFTWARE, PRODUCTS, SERVICES AND RELATED GRAPHICS 
OBTAINED THROUGH THE ILS WEBSITE, OR OTHERWISE ARISING OUT OF THE USE OF 
THE ILS WEBSITE, WHETHER BASED ON CONTRACT, TORT, NEGLIGENCE, STRICT 
LIABILITY OR OTHERWISE, EVEN IF ILS OR ANY OF ITS SUPPLIERS HAS BEEN ADVISED 
OF THE POSSIBILITY OF DAMAGES. BECAUSE SOME STATES/JURISDICTIONS DO NOT 
ALLOW THE EXCLUSION OR LIMITATION OF LIABILITY FOR CONSEQUENTIAL OR 
INCIDENTAL DAMAGES, THE ABOVE LIMITATION MAY NOT APPLY TO YOU. IF YOU ARE 
DISSATISFIED WITH ANY PORTION OF THE ILS WEBSITE OR WITH ANY OF THESE 
TERMS OF USE, YOUR SOLE AND EXCLUSIVE REMEDY IS TO DISCONTINUE USING THE 
ILS WEBSITE. </p>

<h3 class="titleh3">Termination / Access Restriction</h3>

 <p>ILS reserves the right, in its sole discretion, to terminate your access to the ILS Website and the 
related services or any portion thereof at any time, without notice. GENERAL To the maximum 
extent permitted by law, this agreement is governed by the laws of the State of California, 
U.S.A. and you hereby consent to the exclusive jurisdiction and venue of courts in San Luis 
Obispo County, California, U.S.A. in all disputes arising out of or relating to the use of the ILS 
Website.</p>
 <p>Use of the ILS Website is unauthorized in any jurisdiction that does not give effect to 
all provisions of these terms and conditions, including without limitation this paragraph. You 
agree that no joint venture, partnership, employment, or agency relationship exists between you 
and ILS as a result of this agreement or use of the ILS Website. </p>
 <p> ILS's performance of this 
agreement is subject to existing laws and legal process and nothing contained in this agreement 
is in derogation of ILS'€™s right to comply with governmental, court and law enforcement 
requests or requirements relating to your use of the ILS Website or information provided to or 
gathered by ILS with respect to such use. If any part of this agreement is determined to be 
invalid or unenforceable pursuant to applicable law including, but not limited to, the warranty 
disclaimers and liability limitations set forth above, then the invalid or unenforceable provision 
will be deemed superseded by a valid, enforceable provision that most closely matches the 
intent of the original provision and the remainder of the agreement shall continue in effect.</p>
 <p>Unless otherwise specified herein, this agreement constitutes the entire agreement between the 
user and ILS with respect to the ILS Website and it supersedes all prior or contemporaneous 
communications and proposals, whether electronic, oral or written, between the user and ILS 
with respect to the ILS Website. A printed version of this agreement and of any notice given in 
electronic form shall be admissible in judicial or administrative proceedings based upon or 
relating to this agreement to the same extent an d subject to the same conditions as other 
business documents and records originally generated and maintained in printed form. It is the 
express wish to the parties that this agreement and all related documents be drawn up in 
English.</p>

  <h3 class="titleh3">Copyright and Trademark Notices:</h3>
 
<p>All contents of the ILS Website are: Copyright Institute for Lean Systems and/or its suppliers. All
rights reserved.</p>

<h3 class="titleh3">Trademarks</h3>
<p>The names of actual companies and products mentioned herein may be the trademarks of their 
respective owners. </p>
<p>The example companies, organizations, products, people and events depicted herein are 
fictitious. No association with any real company, organization, product, person or event is 
intended or should be inferred. </p>
<p>Any rights not expressly granted herein are reserved.</p>
 
 <h3 class="titleh3">Notices and Procedure for Making Claims of Copyright Infringement</h3>
 <p>Pursuant to Title 17, United States Code, Section 512(c)(2), notifications of claimed copyright 
infringement under United States copyright law should be sent to Service Provider'€™s 
Designated Agent. ALL INQUIRIES NOT RELEVANT TO THE FOLLOWING PROCEDURE 
WILL RECEIVE NO RESPONSE. See Notice and Procedure for Making Claims of Copyright 
Infringement.</p>



  </div>
 
</div>
</body>
</html>
