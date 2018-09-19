<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/flowplayer/skin/minimalist.css">
<script id="scheduletemplate" type="text/x-handlebars-template">
<form id="livesession" method="post">
	<div id="righttable" class="marleft15" >
		<div class="row">
			<div class="col-md-12">
{{#if .}}
<div class="col-md-4"></div>
<div class="col-md-2 marbottom20"><a class="label" style="background-color:#449d44;"><i class="fa fa-play-circle"></i></a> - Live Session</div>
<div class="col-md-3 marbottom20"><a class="label" style="background-color:rgb(218, 154, 37);"><i class="fa fa-play-circle"></i></a> - Future Session</div>
<div class="col-md-3 marbottom20"><a class="label" style="background-color:rgb(73, 179, 199);"><i class="fa fa-play-circle"></i></a> - Completed Session</div>

<table class="table table-striped table-bordered whitebg list-search manageprofilecont">
<thead>
<tr>
<th>Date</th>
<th>Description</th>
<th>Start Time</th>
<th>End Time</th>
<th>Duration</th>
<th>Amount</th>
</tr>
</thead>
<tbody>
{{#each .}}
<tr>
<td class="schedule" schdate="{{scheduledate}}">{{{checkdate scheduledate livesessionid starttime endtime price courseid }}}<span class="scheduledate marleft15">{{schedulesate scheduledate}}</span></td>
<td class="width28 breakword">{{description}}</td>
<td>{{starttime}}</td>
<td>{{endtime}}</td>
<td class="duration" start="{{starttime}}" end="{{endtime}}"></td>
<td class="actiondetail">{{freeChecker price}}</td>
</tr>
{{/each}}
</tbody>
</table>
{{else}}
<div class="col-md-12">
	<h5 style="text-align:center;">No schedule</h5>		
</div>
{{/if}}
</div>
</form>
</script>


</head>
<body>
	<div class="container-fluid titlecontainer">
		<div class="row">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/"> Home </a> / <a
					href="<%=request.getContextPath()%>/browse"> Categories </a> / <font
					class="crstitle"></font>
			</div>
		</div>
	</div>
	<div class="container-fluid" id="infoholder"></div>

	<div class="container-fluid">
		<div class="row martop20">
			<div class="col-md-3 pad0">
				<div class="boxshadow marbottom10">
					<div class="greybgwhiteborder pad10">Course Summary</div>
					<div class="whitebg pad10">
						<h5 class="mar0 lineheight40">
							<i class="fa fa-list marright7"></i><font class="seccount">15</font>
							<font>Sections</font>
						</h5>
						<h5 class="mar0 lineheight40">
							<i class="fa fa-th marright7"></i><font class="leccount">15</font>
							<font>Lectures</font>
						</h5>
						<h5 class="mar0 lineheight40">
							<i class="fa fa-video-camera marright7"></i><font
								class="vidcount">25</font> <font>Videos</font>
						</h5>
						<h5 class="mar0 lineheight40">
							<i class="fa fa-file-pdf-o marright7"></i><font class="pdfcount">20</font>
							<font>Pdf's</font>
						</h5>
						<h5 class="mar0 lineheight40 fbcaret">
							<i class="fa fa-facebook marright7"></i> <font><a>Reputation</font>
							<i class="fa fa-caret-square-o-down"></i></a>
							<div id="frep"></div>
						</h5>
					</div>
				</div>

				<div class="col-md-12 pad0 whitebg boxshadow" id="authorholder"></div>
				<div class="col-md-12 pad0 martop10 border" id="morecourseholder"></div>
				<div class="col-md-12 pad0 martop10 border" id="relatedcourseholder"></div>

			</div>
			<div class="col-md-9 pad0 padleft20">
				<div id="curriculumholder"></div>
				<div class='blog-comment  martop10'>
					<ul class="nav nav-tabs" id="myTab">
						<li class="borderbottoma active"><a href="#reviewsholder"
							data-toggle="tab">Reviews</a></li>
						<li class="borderbottoma"><a href="#discussionholder"
							data-toggle="tab" class="discussionholder">Discussions</a></li>
						<li class="borderbottoma"><a href="#scheduleholder"
							data-toggle="tab" class="scheduleholder">Schedule</a></li>
					</ul>
					<div class="tab-content martop10">
						<div class="tab-pane active" id="reviewsholder">
							<div class="row whitebg marleft0 martop10 marbottom10 ">
								<div class="col-md-12">
									<div class="col-md-12 padtop10">
										<div class="col-md-6 centertext martop20">
											<h1 class="rating-num">4.0</h1>
											<div class="ratediv avgrate width100" data-score=''></div>
											<div class="martop10">
												<i class="fa fa-user marright7"></i><font class="totreviews">0</font>
												total
											</div>
										</div>
										<div class="col-md-6 pad0">
											<div class="row-fluid rating-desc">
												<div class="col-md-3 text-right">
													<i class="fa fa-star marright5"></i>5
												</div>
												<div class="col-md-9">
													<div class="progress">
														<div class="progress-bar fiveper" role="progressbar"
															aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
															style="">
															<span class="white fivestar"></span>
														</div>
													</div>
												</div>
												<div class="col-md-3 text-right">
													<i class="fa fa-star marright5"></i>4
												</div>
												<div class="col-md-9">
													<div class="progress">
														<div class="progress-bar fourper" role="progressbar"
															aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
															style="">
															<span class="white fourstar"></span>
														</div>
													</div>
												</div>
												<div class="col-md-3 text-right">
													<i class="fa fa-star marright5"></i>3
												</div>
												<div class="col-md-9">
													<div class="progress">
														<div class="progress-bar threeper" role="progressbar"
															aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
															style="">
															<span class="white threestar"></span>
														</div>
													</div>
												</div>
												<div class="col-md-3 text-right">
													<i class="fa fa-star marright5"></i>2
												</div>
												<div class="col-md-9">
													<div class="progress">
														<div class="progress-bar twoper" role="progressbar"
															aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
															style="">
															<span class="white twostar"></span>
														</div>
													</div>
												</div>
												<div class="col-md-3 text-right">
													<i class="fa fa-star marright5"></i>1
												</div>
												<div class="col-md-9">
													<div class="progress">
														<div class="progress-bar oneper" role="progressbar"
															aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
															style="">
															<span class="white onestar"></span>
														</div>
													</div>
												</div>
												<div class="col-md-3 text-right">
													<i class="fa fa-star marright5"></i>0
												</div>
												<div class="col-md-9">
													<div class="progress">
														<div class="white progress-bar zeroper" role="progressbar"
															aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
															style="">
															<span class="white zerostar"></span>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

								</div>
							</div>
							<div class="" id="reviewholder"></div>
						</div>
						<div class="tab-pane " id="scheduleholder"></div>
						<div class="tab-pane" id="discussionholder"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="answermodal" class="modal fade" aria-hidden="true"
		style="top: 20%">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 id="myModalLabel">Message</h4>
				</div>
				<div class="modal-body">
					<p>You must signup for the course to view the answers.</p>
				</div>

			</div>
		</div>
	</div>
	<div class="modal" id="signUpModal" style="display: none;" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header modal-bg modal-header-resp">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">X</button>
					<h4 class="modal-title modaltitle size20 centertext">Sign In
						for the course</h4>
				</div>
				<div class="modal-body modal-bg">
					<fieldset>
						<div class="alert alert-danger alert-dismissible authenalert"
							role="alert" style="display: none;">
							<span class="errormsg"> <strong>Warning!</strong> Please
								check your user name and password.
							</span> <span class="errormsg1"> <strong>Warning!</strong> Your
								Account has been deactivated.
							</span>
						</div>
						<center>
							<div class="row">
								<div class="center-block">
									<img class="profile-img"
										src="<%=request.getContextPath()%>/assets/app/images/index/user.png"
										alt="">
								</div>
							</div>
						</center>
						<div class="row">
							<div class="signupdetail">
								<form name="coursesignup" method="post" action="newSignUp"
									id="courseinfo">
									<div class="col-sm-12 col-md-10  col-md-offset-1 ">
										<div class="form-group">
											<div class="controls col-md-12 input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-user"></i></span> <input type="text"
													name="firstname" class="form-control"
													placeholder="Firstname" maxlength="45">
											</div>
											<div
												class="alert alert-danger alertfirstname martopbottom10 pad10"
												role="alert" style="display: none;"></div>
										</div>
										<div class="form-group">
											<div class="controls col-md-12 input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-user"></i></span> <input type="text"
													name="lastname" id="" class="form-control"
													placeholder="Lastname" maxlength="45">
											</div>
											<div
												class="alert alert-danger alertlastname martopbottom10 pad10"
												role="alert" style="display: none;"></div>
										</div>

										<div class="form-group">
											<div class="controls col-md-12 input-group">
												<span class="input-group-addon">@</span> <input type="email"
													value="" id="emailid" name="emailid"
													class="form-control signupusername"
													placeholder="Email Address" maxlength="100" maxlength="45">
											</div>
											<div
												class="alert alert-danger alertemail martopbottom10 pad10"
												role="alert" style="display: none;"></div>
										</div>
										<div class="form-group">
											<div class="alert alert-danger martopbottom10 pad10"
												role="alert" id="alertBoxMsg" style="display: none;">
												<strong>Message!</strong>
												<command id="checkmsgval" class="check"></command>
											</div>
										</div>
										<div class="form-group">
											<div class="controls col-md-12 input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-lock"></i></span> <input
													type="password" name="password" id="userpassword"
													class="form-control newpwdval" required
													placeholder="Password" maxlength="45">
											</div>
											<div
												class="alert alert-danger alertpassword martopbottom10 pad10"
												role="alert" style="display: none;"></div>
												<div class="alert alert-danger alertwrongpassword" role="alert"
										style="display: none;"></div>
										</div>
										<div class="control-group progcntrl"
											style="margin-bottom: 0px; display: none;">
											<div class="controls">
												<div class="span6">
													<span id="strengthmsg"></span>
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
										<div class="alert alert-success " hidden="true" id="alertBox">
											<strong>Message!</strong>
											<command id="checkmsg" class="check"></command>
										</div>
										<div class="form-group">
											<div class="controls">
												<input type="button"
													class="btn btn-lg btn-block signupbtn sigup-btn btn-blue white"
													id="signupbtn" value="Sign Up"">
											</div>
										</div>

										<s:hidden name="targetUrl" value="/app/coursepayment" />
										<s:hidden name="courseid" value="%{courseid}"></s:hidden>
										<s:hidden name="personid" value="%{personid}"></s:hidden>
										<s:hidden name="orgpersonid" value="%{orgpersonid}"></s:hidden>
										<s:hidden name="coursetitle" value="%{coursetitle}"></s:hidden>
										<s:hidden name="price" value="%{price}"></s:hidden>
										<s:hidden name="priceid" value="%{priceid}"></s:hidden>
										<s:hidden name="courseinvitationid"
											value="%{courseinvitationid}"></s:hidden>
										<input type="hidden" name="organizationid" value="" />
									</div>
								</form>
							</div>
							<div class="signindetail" style="display: none;">
								<form name="coursesignup" method="post"
									action="j_spring_security_check" id="courseinfo">
									<div class="col-sm-12 col-md-10  col-md-offset-1 ">
										<div class="form-group">
											<div class="controls col-md-12 input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-user"></i></span> <input type="text"
													class="form-control textbox-bottom form-control jusernamefield"
													placeholder="Username" required id="username"
													name="j_username">
											</div>
										</div>
										<div class="form-group">
											<div class="controls col-md-12 input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-lock"></i></span> <input
													type="password" class="form-control textbox-bottom span10"
													placeholder="Password" required type="password"
													name="j_password">
											</div>
										</div>
										<div class="form-group">
											<div class="controls">
												<input type="submit"
													class="btn btn-lg btn-block sigup-btn btn-blue white"
													id="sign-in" value="Login"">
											</div>
										</div>
										<s:hidden name="targetUrl" value="/app/coursepayment" />
										<s:hidden name="courseid" value="%{courseid}"></s:hidden>
										<s:hidden name="personid" value="%{personid}"></s:hidden>
										<s:hidden name="orgpersonid" value="%{orgpersonid}"></s:hidden>
										<s:hidden name="coursetitle" value="%{coursetitle}"></s:hidden>
										<s:hidden name="price" value="%{price}"></s:hidden>
										<s:hidden name="priceid" value="%{priceid}"></s:hidden>
										<s:hidden name="courseinvitationid"
											value="%{courseinvitationid}"></s:hidden>
										<s:hidden name="formname" value="coursepreview"></s:hidden>
										<input type="hidden" name="organizationid" value="" />
									</div>
								</form>
							</div>
						</div>
					</fieldset>
					<!-- </div> -->
					<!-- </div> -->
					<div class="modal-footer modal-footers modal-footers-resp">
						<p class="centertext modal-title">
							<span class="signupspan" style="display: none;">Don't have
								an account! <a class="signupa">Sign Up Here</a>
							</span> <span class="loginspan">Already Have an account! <a
								class="signina">Login Here</a>
							</span>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal" id="termsModal"
		style="width: 750px; left: 45%; display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<form name="coursesignupform" method="post" action="newSignUp">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">X</button>
						<h3>Terms of Service</h3>
					</div>
					<div class="modal-body">
						<div class="row-fluid">
							<h4>Services</h4>
							<p style="font-weight: 400;">The Company may at its sole
								discretion modify the features of the Services from time to time
								without prior notice. The Company will provide the Services in
								accordance with this Agreement. The Company may at its sole
								discretion modify the features of the Services from time to time
								without prior notice.The Company will provide the Services in
								accordance with this Agreement.</p>
						</div>

						<div class="row-fluid">
							<h4>Sensitive Data</h4>

							<p style="font-weight: 400;">The Company will not share,
								disclose, sell, lease, modify, delete or distribute any Data
								provided by You in any manner. The Company will also not view
								the Data provided by You except when given permission by You in
								writing for the purpose of support. The entire sensitive data
								clause herein shall survive termination of this agreement
								indefinitely.</p>

						</div>

						<div class="row-fluid">
							<p style="font-weight: 400;">No reselling or use outside of
								permitted terms</p>
						</div>

						<div class="row-fluid">
							<p style="font-weight: 400;">Other than using the Services as
								permitted under the terms and conditions of this Agreement or
								other written agreements between You and the Company, You may
								not resell, distribute, make any commercial use of, or use on a
								time-share or service bureau basis.</p>
						</div>

					</div>
				</form>
			</div>
		</div>
	</div>
	<form name="newuserSignupform" method="post"
		action="newsignuplogin.jsp">
		<input type="hidden" name="newjusername" value=""> <input
			type="hidden" name="newjpassword" value=""> <input
			type="hidden" name="newtargeturl" value="/app/coursepayment">
		<input type="hidden" name="neworganid" value="">
		<s:hidden name="courseid" value="%{courseid}"></s:hidden>
		<s:hidden name="personid" value="%{personid}"></s:hidden>
		<s:hidden name="orgpersonid" value="%{orgpersonid}"></s:hidden>
		<s:hidden name="coursetitle" value="%{coursetitle}"></s:hidden>
		<s:hidden name="price" value="%{price}"></s:hidden>
		<s:hidden name="priceid" value="%{priceid}"></s:hidden>
		<s:hidden name="courseinvitationid" value="%{courseinvitationid}"></s:hidden>
	</form>

	<s:hidden name="userstatus" id="userstatus" value="%{userstatus}"></s:hidden>
	<s:hidden name="courseid" value="%{courseid}"></s:hidden>
	<s:hidden name="orgpersonid" value="%{orgpersonid}"></s:hidden>
	<s:hidden name="useremailid" value="%{emailid}"></s:hidden>
	<s:hidden name="personid" value="%{personid}"></s:hidden>
	<s:hidden name="courseinvitationid" value="%{courseinvitationid}"></s:hidden>
	<s:hidden name="count" value="%{count}"></s:hidden>
	<s:hidden name="coursesubscribers" value="%{coursesubscribers}"></s:hidden>
	<span class="courseid" style="display: none"></span>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/livevalidation/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/flowplayer/iGplayer.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/guest/guestpreviewcourse.js"></script>

	<input type="hidden" class="error"
		value=<%=request.getParameter("error")%>>

	<span id="metaURI" style="display: none"><%=request.getHeader("host")%></span>
	<span id="priceicon" style="display: none"><s:text
			name='label.price'></s:text></span>
</body>
</html>