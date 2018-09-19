<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page language="java" import="java.util.ResourceBundle"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<th>Action</th>
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
<td class="paymentbtn">{{{checkdates scheduledate livesessionid starttime endtime price courseid }}}</td>
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
<input type="hidden" name="coursetitle" class="coursetitle" value="">
<input type="hidden" name="livesessionid" class="livesessionid" value="">
<input type="hidden" name="price" class="price" value="">
<input type="hidden" name="courseid" class="courseid" value="">
</form>
</script>
</head>
<body>
	<%@ page
		import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
	<%
		UserDetailsAdaptor userDetailsAdaptor = null;
		userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String orgpersonid = userDetailsAdaptor.getUser().getProperty(
				"orgpersonid");
		String courseenrollmentid = request
				.getParameter("courseenrollmentid");
		String courseenrollmentstatus = request
				.getParameter("courseenrollmentstatus");
		String courseid = request
				.getParameter("courseid");
	%>
	<%
		ResourceBundle bundle = ResourceBundle.getBundle("serversetup");
		String remediliveclassroomurl=bundle.getString("remediliveclassroomurl");
	%>
	<span style="display: none;" class="hiddenclss"
		orgpersonid="<%=orgpersonid%>"></span>

	<div class="container-fluid titlecontainer">
		<div class="row">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/app/mycourses">My Courses </a> / <font
					class="crstitle"></font>
			</div>
		</div>
	</div>
	<div id="facebookalert"></div>
	<div class="container-fluid" id="infoholder"></div>

	<div class="container-fluid">
		<div class="row martop20">
			<div class="col-md-3 pad0 col-xs-4">
				<div class="boxshadow marbottom10 giftcls">
					<div class="pan-head giftcoursebtn pad10 centertext graybg pointer">
						<i class="fa fa-gift"></i> Gift Course
					</div>
				</div>
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
							<a><i class="fa fa-facebook marright7"></i>Reputation
							<i class="fa fa-caret-square-o-down carets"></i></a>
							<div id="frep"></div>
						</h5>
					</div>
				</div>
				<!-- <div class="panel panel-white profile-widget panel-shadow"> -->
					<!-- <div class="row martop20"> -->
						<div class="col-md-12 pad0 whitebg boxshadow marbottom10" id="authorholder"></div>
						<div class="col-md-12 pad0 martop10 border marbottom10" id="ratingcourses"></div>
					<!-- </div>
				</div> -->
			</div>
			<div class="col-md-9 pad0 padleft20 col-xs-8">
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
									<div class="col-md-8 padtop10">
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
														<div class="progress-bar zeroper" role="progressbar"
															aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
															style="">
															<span class="white zerostar"></span>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-4 centertext martop30">
										<h4 class="martop30">My Review</h4>
										<div class="ownreview" style="display: none">
											<a class="clicktoreview" style="cursor: pointer;">Click
												To Review</a>
										</div>
										<div class="ownrating" style="display: none">
											<div class="ratediv myrate width100" data-score=''></div>
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
	
	<form name="learnform" method="post">
		<input type="hidden" name="courseenrollmentid"
			value="<%=courseenrollmentid%>"> <input type="hidden"
			name="courseenrollmentstatus" value="<%=courseenrollmentstatus%>">

		<input type="hidden" name="courselectureid" value=""> <input
			type="hidden" name="courseid" value=""> <input type="hidden"
			name="coursetitle" value=""> <input type="hidden"
			name="courselogo" value=""> <input type="hidden"
			name="livesessionid" value="">
			<input type="hidden" name="price" value="">
			<input type="hidden" name="priceid" value="">
	</form>

	<div class="modal fade" id="answerModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Answer It</h4>
				</div>
				<div class="modal-body">
					<div class="form-horizontal">
						<div class="alert alert-success succansalert"
							style="display: none;">
							<font>Your Answer has been updated.</font>
						</div>
						<div class="alert alert-danger failcansalert"
							style="display: none;">
							<font>Something went wrong .Please Try again later</font>
						</div>
						<div class="form-group">
							<label class="col-sm-3 righttext" for="">Question: </label>
							<div class="col-sm-9">
								<label class="control-label qntxt span12"
									style="text-align: left; overflow: hidden; text-overflow: ellipsis;margin-left: 5px;"></label>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3 righttext " for="">Your
								Answer<font class='error'>*</font></label>
							<div class="col-sm-8" style="margin-left: 1%;">
								<textarea class="qnans col-sm-12 form-control"
									style="resize: none;"></textarea>
								<p class="help-block martop7">
									<font class="charremaining">5000</font> Characters Remaining
								</p>
								<div class="alert alert-danger answererror"
									style="display: none;">Please Fill the answer.</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 " for=""></label>
							<div class="col-sm-3">
								<button id="answerit" questionid="" name=""
									class="btn btn-success">Answer</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="ratingmodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Course Review</h4>
				</div>
				<div class="modal-body">
					<div class="form-horizontal">
						<div class="row">
							<div class="col-md-12">
								<div class="col-md-3"></div>
								<div class="col-md-6 pad0 padleft10 padbottom10">
									<div class="newratediv width100" style="display: inline;"></div>
								</div>
								<div class="col-md-3"></div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="col-md-3"></div>
								<div class="col-md-6">
									<textarea class="col-md-12 ratestextarea form-control"
										placeholder="Enter your reviews here.." rows="" cols="" maxlength=500></textarea>
										<p class="help-block"><span class="ratedestext">500</span> Characters Remaining</p>
								</div>
								<div class="col-md-3"></div>
							</div>
						</div>
						<div class="alert alert-danger martop10" id="mandatoryp"
							style="display: none;">
							<font class="reviewstatus">Your Review is Required</font>
						</div>
						<div class="alert alert-success martop10" id="ratesuccess"
							style="display: none;">
							<font>Your Review has been Successfully Saved</font>
						</div>
						<div class="row martop10">
							<div class="col-md-12">
								<div class="col-md-3"></div>
								<div class="col-md-6 centertext">
									<a class="btn btn-success rateitbtn">Rate It</a>
								</div>
								<div class="col-md-3"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>	
	<div class="modal fade" id="msgmodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Message</h4>
				</div>
				<div class="modal-body">
					<p>Your Subscription for the course is waiting for the approval from administrator. Please wait for a while. </p>
				</div>
			</div>
		</div>
	</div>
	<s:hidden name="courseid" value="%{courseid}" cssClass="clid"></s:hidden>
	<s:hidden name="sessionid" value="%{livesessionid}"></s:hidden>
	<s:hidden name="courseenrollmentid" value="%{courseenrollmentid}"></s:hidden>
	<s:hidden name="courseenrollmentstatus"
		value="%{courseenrollmentstatus}"></s:hidden>
	<input type="hidden" class="livesessionidss">
	<input name="courseids" value="<%=courseid%>" type="hidden">
	<input name="orgpersonid" value="<%=orgpersonid%>" type="hidden">
	<input name="remediliveclassroomurl" value="<%=remediliveclassroomurl%>" type="hidden">
	<span id="priceicon" style="display: none"><s:text
			name='label.price'></s:text></span>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/users/learningcoursepreview.js"></script>
</body>
</html>