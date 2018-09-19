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
<td class="schedule" schdate="{{scheduledate}}"><span class="scheduledate"></span>{{{checkdate scheduledate livesessionid starttime endtime price courseid }}}</td>
<td>{{description}}</td>
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
	<%
		String courseenrollmentstatus = request
				.getParameter("courseenrollmentstatus");
	%>
	<div class="container-fluid titlecontainer">
		<div class="row">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/app/browseu">Browse
					Courses </a> / <font class="crstitle"></font>
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
							<i class="fa fa-list marright7"></i><font class="seccount">0</font>
							<font>Sections</font>
						</h5>
						<h5 class="mar0 lineheight40">
							<i class="fa fa-th marright7"></i><font class="leccount">0</font>
							<font>Lectures</font>
						</h5>
						<h5 class="mar0 lineheight40">
							<i class="fa fa-video-camera marright7"></i><font
								class="vidcount">10</font> <font>Videos</font>
						</h5>
						<h5 class="mar0 lineheight40">
							<i class="fa fa-file-pdf-o marright7"></i><font class="pdfcount">20</font>
							<font>Pdf's</font>
						</h5>
						<h5 class="mar0 lineheight40 fbcaret">
							<i class="fa fa-facebook marright7"></i> <a>Reputation
								<i class="fa fa-caret-square-o-down carets"></i>
							</a>
							<div id="frep"></div>
						</h5>
					</div>
				</div>
				<div class="col-md-12 pad0 whitebg boxshadow marbottom10"
					id="authorholder"></div>
				<div class="col-md-12 pad0 martop10 border marbottom10"
					id="morecourseholder"></div>
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
							<div class="row whitebg marleft0 martop10 marbottom10 boxshadow">
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
														<div class="progress- twoper" role="progressbar"
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
	<div class="modal fade" id="answermodal">
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
					<p>You must signup for the course to view the answers.</p>
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

	<form name="previewuserform" method="post">
		<input type="hidden" name="coursetitle" value=""> <input
			type="hidden" name="courselogo" value="">
		<s:hidden name="courseid" value="%{courseid}"></s:hidden>
		<s:hidden name="price" value="%{price}"></s:hidden>
		<s:hidden name="priceid" value="%{priceid}"></s:hidden>
		<s:hidden name="courseenrollmentid" value="%{courseenrollmentid}"></s:hidden>
		<s:hidden name="wishlisted" value="%{wishlisted}"></s:hidden>
		<s:hidden name="socialmsg" value="%{socialmsg}"></s:hidden>
	</form>
	<span class="courseid" style="display: none;"></span>
	<input type="hidden" value="<%=courseenrollmentstatus%>"
		name="courseenrollmentstatus">
	<span id="priceicon" style="display: none"><s:text
			name='label.price'></s:text></span>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/flowplayer/iGplayer.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/users/upreview.js"
		type="text/javascript"></script>
</body>
</html>