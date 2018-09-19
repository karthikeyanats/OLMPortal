<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script id="reporttmpl" type="text/x-handlebars-template">
{{#each .}}
<li>
	<time class="cbp_tmtime" attime="{{getDate this}}" ></time>
	<div class="cbp_tmicon cbp_tmicon-phone" ></div>
	<div class="cbp_tmlabel boxshadow">
		{{#each this}}
		<div class="row eachString" coursetitle="{{coursetitle}}" enrolleddate="{{enrolleddate}}" 
		completeddate="{{completeddate}}" questionsasked="{{questionsasked}}" notes="{{notes}}" questionsanswered="{{questionsanswered}}">{{writeString this}}
		</div>		
		{{/each}}
	</div>
</li>
{{/each}}
</script>
<script type="text/javascript">
	Handlebars.registerHelper("getDate", function(obj) {
		return obj[0].formattedDate;
	});
	Handlebars.registerHelper("splittedDates", function(splittedDate) {
		var monthNames = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" ];
		var splitted = splittedDate.split("-");
		var months = splitted[0];
		var month = monthNames[months - 1];
		var date = splitted[1] + "-" + month + "-" + splitted[2];
		return date;
	});
	Handlebars
			.registerHelper(
					"writeString",
					function(obj) {

						if (obj.differentiator == "questionans") {
							return new Handlebars.SafeString(
									"<div class='col-md-12'><div class='col-md-2'><font class='splittedtime'><i class='fa fa-clock-o'></i>  "
											+ obj.splittedTime
											+ "</font></div><div class='col-md-10' style='margin-left: 0px;padding-top: 10px;'><span><i class='fa fa-question-circle' style='color: #492424 !important;'></i> <i class='fa fa-check-circle'  style='color: #492424 !important;'></i> You have answered for the question in  <b>"
											+ obj.courselecturetitle
											+ "</b><br><b> Question </b>: "
											+ obj.faqquestion
											+ " <br> <p style='overflow:hidden;text-overflow:ellipsis;'><b> Your Answer </b>: "
											+ obj.faqanswer
											+ "</p>"
											+ " </b></span></div></div>");
						} else if (obj.differentiator == "question") {
							return new Handlebars.SafeString(
									"<div class='col-md-12'><div class='col-md-2'><font class='splittedtime'><i class='fa fa-clock-o'></i>  "
											+ obj.splittedTime
											+ "</font></div><div class='col-md-10' style='margin-left: 0px;padding-top: 10px;'><span><i class='fa fa-question-circle' style='color: #492424 !important;'></i> You have asked the question in  <b>"
											+ obj.courselecturetitle
											+ "</b><br>"
											+ "<p style='overflow:hidden;text-overflow:ellipsis;'><b> Question </b>: "
											+ obj.faqquestion
											+ "</p>"
											+ "</span></div></div>");
						} else if (obj.differentiator == "lecturecompleted") {
							return new Handlebars.SafeString(
									"<div class='col-md-12'><div class='col-md-2'><font class='splittedtime'><i class='fa fa-clock-o'></i>   "
											+ obj.splittedTime
											+ "</font></div><div class='col-md-10' style='margin-left: 0px;padding-top: 10px;'><span><i class='fa fa-book' style='color: #492424 !important;'></i> <i class='fa fa-check-circle'  style='color: #492424 !important;'></i> You have completed a lecture in  <b>"
											+ obj.coursesectiontitle
											+ "</b><br><b> Lecture title : </b> "
											+ obj.courselecturetitle
											+ " <br> <b class='timespentclass' timespent='"+ obj.timespent+"'> Total Time Spent </b>: "
											+ obj.timespent
											+ " </b><br></span></div></div>");
						} else if (obj.differentiator == "note") {
							return new Handlebars.SafeString(
									"<div class='col-md-12'><div class='col-md-2'><font class='splittedtime'><i class='fa fa-clock-o'></i>  "
											+ obj.splittedTime
											+ "</font></div><div class='col-md-10' style='margin-left: 0px;padding-top: 10px;'><span><i class='fa fa-pencil-square-o' style='color: #492424 !important;'></i> You have taken note in lecture  <b>"
											+ obj.courselecturetitle
											+ "</b><br>"
											+ "<p style='overflow:hidden;text-overflow:ellipsis;'><b> Note : </b>"
											+ obj.notesdescription
											+ "</p>"
											+ "</span></div></div>");
							
						}else if (obj.differentiator == "scratch") {
							return new Handlebars.SafeString(
									"<div class='col-md-12'><div class='col-md-2'><font class='splittedtime'><i class='fa fa-clock-o'></i>  "
											+ obj.splittedTime
											+ "</font></div><div class='col-md-10' style='margin-left: 0px;padding-top: 10px;'>"
											+"<span><i class='fa fa-pencil-square-o' style='color: #492424 !important;'></i> You have taken note using scratch pad in lecture  <b>"
											+ obj.courselecturetitle
											+ "</b><br>"
											+ "<p style='overflow:hidden;text-overflow:ellipsis;'><b> File Name : </b>"
											+"<a target='_new' href="+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2="+obj.notespath+">"
											+ obj.actualFileName
											+ "</a></p>"
											+ "</span></div></div>");
							
						}else if (obj.differentiator == "lecturereview") {
							return new Handlebars.SafeString(
									"<div class='col-md-12'><div class='col-md-2'><font class='splittedtime'><i class='fa fa-clock-o'></i>  "
											+ obj.splittedTime
											+ "</font></div><div class='col-md-10' style='margin-left: 0px;padding-top: 10px;'><span><i class='fa fa-star' style='color: #492424 !important;'></i> You have given review for lecture  <b>"
											+ obj.courselecturetitle
											+ "</b> in the section <b>"
											+ obj.coursesectiontitle
											+ "</b><br><b> Review Type : </b> "
											+ obj.reviewtype
											+ "</b><br><b> Rank : </b><span class='ratingclass' rank="+ obj.rank+"></span> "
											+ "</span></div></div>");
						} else if (obj.differentiator == "coursereview") {
							return new Handlebars.SafeString(
									"<div class='col-md-12'><div class='col-md-2'><font class='splittedtime'><i class='fa fa-clock-o'></i>  "
											+ obj.splittedTime
											+ "</font></div><div class='col-md-10' style='margin-left: 0px;padding-top: 10px;'><span><i class='fa fa-star' style='color: #492424 !important;'></i> You have given review for the course   <b>"
											+ obj.coursetitle
											+ "</b><br><b> Rating : </b> <span class='ratingclass' rank="+ obj.courserating+"></span>"
											+ "</b><br><b> Rating Description : </b></br><p style='overflow:hidden;text-overflow:ellipsis;'>"
											+ obj.courseratingdescription
											+ "</p>" + "</span></div></div>");
						}
					});
</script>
</head>
<body>
	<div class="container-fluid titlecontainer">
		<div class="row">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/app/mycourses">My Courses
				</a> / <font class="crstitle">Javascript basics </font> - My Learning
				Path
			</div>
		</div>
	</div>
	<div class="container-fluid white pad0">
		<div class="row">
			<div class="col-md-3 marbottom10">
				<div class="container-fluid bg5 height140">
					<div class="row countsrow height100">
						<div class="col-md-12 centertext martop20">
							<h5>
								Enrolled on : <font class="enrolledDate">21-Nov-2014
									11:21:13</font>
							</h5>
							<h5>
								Completed on : <font class="completdDate">27-Nov-2014
									11:21:13</font>
							</h5>
						</div>
					</div>
					<div class="row opacitybg height40">
						<h4
							class="mar0 collectamount coursereq centertext white martop10 uppercase">
							Duration</h4>
					</div>
				</div>
			</div>

			<div class="col-md-3 marbottom10">
				<div class="container-fluid bg1 height140">
					<div class="row countsrow height100">
						<div class="col-md-12 centertext martop20">
							<h5 class="timespentholder martop20"></h5>
						</div>
					</div>
					<div class="row opacitybg height40">
						<h4
							class="mar0 collectamount coursereq centertext white martop10 uppercase">
							<i class="fa fa-clock-o marright7"></i> Time Spent
						</h4>
					</div>
				</div>
			</div>
			<div class="col-md-3 marbottom10">
				<div class="container-fluid ligtgreenbg height140">
					<div class="row height100">
						<div class="col-md-12 centertext">
							<div class="col-md-6 col-xs-6">
								<h3 class="notescount size40 martop30"></h3>
							</div>
							<div class="col-md-6 col-xs-6 martop20">
								<i class="fa fa-pencil-square-o size60"></i>
							</div>
						</div>
					</div>
					<div class="row opacitybg height40">
						<h4
							class="mar0 collectamount coursereq centertext white martop10 uppercase">
							Notes</h4>
					</div>
				</div>
			</div>

			<div class="col-md-3 marbottom10">
				<div class="container-fluid bg4 height140">
					<div class="row countsrow height100">
						<div class="col-md-12 centertext">
							<div class="col-md-6 col-xs-6">
								<h4 class="askedqnscount size40 martop20">0</h4>
								<h5>Asked</h5>
							</div>
							<div class="col-md-6 col-xs-6">
								<h4 class="answeredqnscount size40 martop20">0</h4>
								<h5>Answered</h5>
							</div>
						</div>
					</div>
					<div class="row opacitybg height40">
						<h4
							class="mar0 collectamount coursereq centertext white martop10 uppercase">
							Questions</h4>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<ul class="cbp_tmtimeline" id='timeline'></ul>
	</div>

	<form name="reportform" method="post"></form>
	<s:hidden name="courseenrollmentid" value="%{courseenrollmentid}"></s:hidden>
	<script
		src="<%=request.getContextPath()%>/assets/app/js/users/report.js"
		type="text/javascript"></script>
</body>
</html>