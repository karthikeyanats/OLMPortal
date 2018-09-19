<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Content Approve</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/flowplayer/skin/minimalist.css">
<style type="text/css">
#tree li {
	margin: 0px 0;
	list-style-type: none;
	position: relative;
	padding: 15px 5px 0px 5px;
}

#tree li::before {
	content: '';
	position: absolute;
	top: 0;
	width: 1px;
	height: 100%;
	right: auto;
	left: -20px;
	border-left: 1px solid #ccc;
	bottom: 50px;
}

#tree li::after {
	content: '';
	position: absolute;
	top: 30px;
	width: 25px;
	height: 20px;
	right: auto;
	left: -20px;
	border-top: 1px solid #ccc;
}

#tree li a {
	display: inline-block;
	border: 1px solid #ccc;
	padding: 5px 10px;
	text-decoration: none;
	color: #666;
	font-family: arial, verdana, tahoma;
	font-size: 11px;
	border-radius: 5px;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
}
/*Remove connectors before root*/
#tree>ul>li::before,#tree>ul>li::after {
	border: 0;
}
/*Remove connectors after last child*/
#tree li:last-child::before {
	height: 30px;
}
/*Time for some hover effects*/

/*We will apply the hover effect the the lineage of the element also*/
#tree li a:hover,#tree li a:hover+ul li a {
	background: #c8e4f8;
	color: #000;
	border: 1px solid #94a0b4;
}
/*Connector styles on hover*/
#tree li a:hover+ul li::after,#tree li a:hover+ul li::before,#tree li a:hover+ul::before,#tree li a:hover+ul ul::before
	{
	border-color: #94a0b4;
}
</style>

<script id="courseinfotmpl" type="text/x-handlebars-template">
{{#each .}}
<div class="col-md-12 whitebg boxshadow">
<div class="row centertext lightgraybg pad5">
		<h5>Course Basic Information</h5>
	</div>
	<div class="row">
		 <img
			class="courselogo col-md-8 col-md-offset-2 pad0	boxshadow height140 hidden-xs martopbottom10"
			src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo">
	</div>
	<div class="row pad5">
		<div class="form-horizontal marleft15">
			<div class="form-group">
				<label class="control-label col-md-4 righttext pad0">Course : </label>
				<div class="col-md-8 lefttext">{{coursetitle}}
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-4 righttext pad0">About Course: </label>
				<div class="col-md-8 lefttext"><font class="breakword shorttext">{{coursedescription}}</font>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-4 righttext pad0">Category : </label>
				<div class="col-md-8 lefttext">{{categoryname}}
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-4 righttext pad0">Sub-Category : </label>
				<div class="col-md-8 lefttext">{{subcategoryname}}
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-4 righttext pad0">Medium : </label>
				<div class="col-md-8 lefttext">{{mediumname}}
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-4 righttext pad0">Price : </label>
				<div class="col-md-8 lefttext">
					<span class="pricebtn" price="{{price}}" 
						priceicon ="<s:text name="label.price"></s:text>">{{price}}</span>					
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-4 righttext pad0">Instructional Level : </label>
				<div class="col-md-8 lefttext">{{instructoinallevel}}
				</div>
			</div>
			<div class="form-group">
				<div class="col-md-8 col-md-offset-2 lefttext"><a class="btn btn-success 
					promovideolink" videolink="{{coursepromevideopath}}">View Promo Video</a>
				</div>
			</div>
		</div>
		
	</div>	
</div>
{{/each}}
</script>

<script id="subjectpartstmpl" type="text/x-handlebars-template">
<div id="tree">
	<ul class="padleft0">{{#each .}}
		<li class="pad0 padbottom10"><a class="twittbg white size17"><b>{{math @index '+'1}} . {{sectiontitle}}</b></a>
			<ul>{{#each lectures}} 
				<li><a class="leclink" contentid="{{contentid}}" lectureid="{{lectureid}}" sectiontitle="{{sectiontitle}}" 
					contentpath="{{contentpath}}" lecturetitle="{{lecturetitle}}">{{lecturetitle}}</a></li>{{/each}}	
			</ul>
		</li>
		{{/each}}	
	</ul>
</div>
</script>
<!-- Pdf Content Template Start -->
<script id="pdfcontenttmpl" type="text/x-handlebars-template"> 
<div class="row ">
  <div class="col-md-12 ">
     <object class="pdfcontent width100" style="height: 400px;"
			data="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{contentpath}}">
		Unable to view the content
	</object>
</div>
</div>
<p class="help-inline" style="color:red;"><b> Note : </b> To open external link in the pdf, use 'CTRL + LEFT CLICK'</p>  
</script>
<!-- Pdf Content Template End -->

<!-- Video Content Template Start -->
<script id="videocontenttmpl" type="text/x-handlebars-template">
<div class="col-md-12 flowplayer width100" data-swf="<%=request.getContextPath()%>/assets/flowplayer/igplayer.swf" 
	data-ratio="0.5" style="width: 95%; height: 400px;">
	<video data-setup='{"customControlsOnMobile": true}'>
		<source type="video/flv" src="<%=request.getContextPath()%>/PlayFile?r1=serverpath&r2={{contentpath}}">
		</source>
	</video>
</div>
</script>
<!-- Video Content Template End -->

<!-- Promo Video Template Start -->
<script id="promovideocontenttmpl" type="text/x-handlebars-template">
<div class="col-md-12 promoflowplayer width100" data-swf="<%=request.getContextPath()%>/assets/flowplayer/igplayer.swf" 
	data-ratio="0.5" style="width: 95%; height: 400px;">
	<video data-setup='{"customControlsOnMobile": true}'>
		<source type="video/flv" src="<%=request.getContextPath()%>/PlayFile?r1=serverpath&r2={{contentpath}}">
		</source>
	</video>
</div>
</script>
<!-- Video Content Template End -->


<!-- SWF Content Template Start -->
<script id="swfcontentmpl" type="text/x-handlebars-template">
<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"  width="100%" 
	codebase="http://download.macromedia.com/pub/shockwave/
	cabs/flash/swflash.cab#version=6,0,40,0" id="mymoviename" > 
		<param name="movie" value="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{contentpath}}" /> 
		<param name="quality" value="high" /> 
		<param name="bgcolor" value="#fff" /> 
		<param name="wmode" value="transparent"> 
		<PARAM NAME="SCALE" VALUE="exactfit">
		<embed class="swfembed" src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{contentpath}}" height=500
			width="100%" SCALE="exactfit" wmode="transparent" quality="high" bgcolor="#ffffff" name="mymoviename" align="" type="application/x-shockwave-flash"> 
		</embed> 
</object> 
</script>
<!-- SWF Content Template End -->
<script id="learningLectureInformationtmpl"
	type="text/x-handlebars-template">


<div class="col-md-12">
<h3>Keywords</h3>
{{#if  keywords}}
{{#each keywords}}
	<span class="badge badge-success pad612 marbottom10 breakword">{{description}}</span>
{{/each}}
{{else}}
	<h5><i class="fa fa-angle-double-right marright7"></i>No keywords Found.</h5>
{{/if}}
</div>
<div class="col-md-12">
<h3>Summary</h3>
{{#if  summary}}
{{#each summary}}
	<h5 class="justify"><i class="fa fa-angle-double-right marright7"></i>{{description}}</h5>
{{/each}}
{{else}}
	<h5><i class="fa fa-angle-double-right marright7"></i>No Summary Found.</h5>
{{/if}}
</div>
</script>
<script id="learningLectureQuiztmpl" type="text/x-handlebars-template">
<h4 class="cornflowerblue">Objective Type Questions </h4>
<ol class="scrollofquestion padleft10 pad0">
	{{#if objList}}
	{{#each objList}} 
	<div class="col-md-12">
		<li><p>{{{question}}}</p></li>
		<ol class="martopbottom10">
		{{#each answers}}
			<li style="{{answer answer}}">{{{answers}}}</li>
		{{/each}}
		</ol>
	</div>
	{{/each}}
	{{else}}
	<div class="col-md-12">
		<p>No Objective Type Questions Found</p>
	</div>
	{{/if}}
</ol>

<h4 class="cornflowerblue">True/False Type Questions </h4>
<ol class="scrollofquestion padleft10 pad0">
	{{#if trurFalseList}}
	{{#each trurFalseList}} 
	<div class="col-md-12">
		<li><p>{{{question}}}</p></li>
		<ol class="martopbottom10">
		{{#each answers}}
			<li style="{{answer}}">{{{answers}}}</li>
		{{/each}}
		</ol>
	</div>
	{{/each}}
	{{else}}
	<div class="col-md-12">
		<p>No True/False Type Questions Found</p>
	</div>
	{{/if}}
</ol>

<h4 class="cornflowerblue">Fill in the blanks Type Questions </h4>
<ol class="scrollofquestion padleft10 pad0">
	{{#if fillintheBlankList}}
	{{#each fillintheBlankList}} 
	<div class="col-md-12">
		<li><p>{{{question}}}</p></li>
		<ol class="martopbottom10">
		{{#each answers}}
			<li style="{{answer}}">{{{answers}}}</li>
		{{/each}}
		</ol>
	</div>
	{{/each}}
{{else}}
	<div class="col-md-12">
		<p>No Fill in the blanks Type Questions Found</p>
	</div>
	{{/if}}
</ol>

<h4 class="cornflowerblue">Match the Following Type Questions </h4>
<ol class="scrollofquestion padleft10 pad0">{{#if matchValuesList}}
	{{#each matchValuesList}}
	<div class="col-md-12">
		<li>
			<h5> Match The Following</h5>
			<div class="col-md-6">
				<h6><u>{{index}}Question</u></h6>
			</div>
			<div class="col-md-6 padright0">
				<h6><u>Answer</u></h6>
			</div>
			<div class="col-md-12 pad0">
				<ol id="questiondiv">
					{{{match-question question answers}}}
				</ol>
			</div>
		</li>
	</div>{{/each}}
{{else}}
	<div class="col-md-12">
		<p>No Match the Following Type Questions Found</p>
	</div>
	{{/if}}
</ol>
</script>

<script id="learningLectureActivitytmpl"
	type="text/x-handlebars-template">
<div class="col-md-12">
<h3>Activities</h3>
{{#if  .}}
{{#each .}}
	<h5><i class="fa fa-angle-double-right marright7"></i>{{description}}</h5>
{{/each}}
{{else}}
	<h5><i class="fa fa-angle-double-right marright7"></i>No Activity Found.</h5>
{{/if}}
</div>
</script>

<script id="learningLectureBoxItemtmpl"
	type="text/x-handlebars-template">
<div class="col-md-12">
<h3>Box Items</h3>
{{#if  .}}
{{#each .}}
	<h5><i class="fa fa-angle-double-right marright7"></i>{{description}}</h5>
{{/each}}
{{else}}
	<h5><i class="fa fa-angle-double-right marright7"></i>No Box Item Found.</h5>
{{/if}}
</div>
</script>

<script id="learningLectureDiagramtmpl"
	type="text/x-handlebars-template">
<div class="col-md-12">
<h3>Diagrams</h3>
{{#if  .}}
<div id="cars" class="carousel slide cars">
<div class="carousel-inner">
   	{{#each .}}
		<div class="item boxshadow pad10">
			<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{description}}&filetype=photo" 
				class="width100 height400 boxshadow"/>
		</div>   
	{{/each}} 
</div>
<a class="carousel-control left" href="#cars" data-slide="prev"><i class="fa fa-chevron-left martop7"></i></a>
<a class="carousel-control right" href="#cars" data-slide="next"><i class="fa fa-chevron-right martop7"></i></a>
</div>
{{else}}
	<h5><i class="fa fa-angle-double-right marright7"></i>No Diagrams Found.</h5>
{{/if}}
</div>
</script>

<script id="learningLectureTabletmpl" type="text/x-handlebars-template">
<div class="col-md-12">
<h3>Tables</h3>
{{#if  .}}
<div id="cars" class="carousel slide cars">
<div class="carousel-inner">
   	{{#each .}}
		<div class="item boxshadow pad10">
			<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{description}}&filetype=photo" 
				class="width100 height400 boxshadow"/>
		</div>   
	{{/each}} 
</div>
<a class="carousel-control left" href="#cars" data-slide="prev"><i class="fa fa-chevron-left martop7"></i></a>
<a class="carousel-control right" href="#cars" data-slide="next"><i class="fa fa-chevron-right martop7"></i></a>
</div>
{{else}}
	<h5><i class="fa fa-angle-double-right marright7"></i>No Tables Found.</h5>
{{/if}}
</div>
</script>

<script id="learningLectureMaptmpl" type="text/x-handlebars-template">
<div class="col-md-12">
<h3>Maps</h3>
{{#if  .}}
<div id="cars" class="carousel slide cars">
<div class="carousel-inner">
   	{{#each .}}
		<div class="item boxshadow pad10">
			<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{description}}&filetype=photo" 
				class="width100 height400 boxshadow"/>
		</div>   
	{{/each}} 
</div>
<a class="carousel-control left" href="#cars" data-slide="prev"><i class="fa fa-chevron-left martop7"></i></a>
<a class="carousel-control right" href="#cars" data-slide="next"><i class="fa fa-chevron-right martop7"></i></a>
</div>
{{else}}
	<h5><i class="fa fa-angle-double-right marright7"></i>No Maps Found.</h5>
{{/if}}
</div>
</script>

<script id="learningLectureReferencetmpl"
	type="text/x-handlebars-template">
<div class="col-md-12">
<h3>References</h3>
{{#if .}}
<div id="cars" class="carousel slide cars">
	<div class="carousel-inner">
    	{{#each .}}
			<div class="item">
				<object class="pdfcontent width100 height400" data="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{description}}">
					Unable to view the content
				</object>
			</div>   
		{{/each}} 
	</div>
  	<a class="carousel-control left" href="#cars" data-slide="prev"><i class="fa fa-chevron-left martop7"></i></a>
  	<a class="carousel-control right" href="#cars" data-slide="next"><i class="fa fa-chevron-right martop7"></i></a>
</div>
{{else}}
	<h5><i class="fa fa-angle-double-right marright7"></i>No References Found.</h5>
{{/if}}
</div>
</script>

<script id="learningLectureWeblinkstmpl"
	type="text/x-handlebars-template">
<div class="col-md-12">
<h3>Web links</h3>
{{#if  .}}
{{#each .}}
	<h5><i class="fa fa-angle-double-right marright7"></i><a href="http://{{description}}" target="_blank"> {{description}}</a></h5>
{{/each}}
{{else}}
	<h5><i class="fa fa-angle-double-right marright7"></i>No Web-links Found.</h5>
{{/if}}
</div>
</script>

<script id="learningLectureProjectIdeastmpl"
	type="text/x-handlebars-template">
<div class="col-md-12">
<h3>Project Ideas</h3>
{{#if  .}}
{{#each .}}
	<h5><i class="fa fa-angle-double-right marright7"></i>{{description}}</h5>
{{/each}}
{{else}}
	<h5><i class="fa fa-angle-double-right marright7"></i>No Web-links Found.</h5>
{{/if}}
</div>
</script>

<script id="commonapprovesuccesstmpl" type="text/x-handlebars-template">
<div class="alert alert-success" role="alert">
      <strong>Success!</strong> <font class="commonapprovealertmsg"></font></a>.
    </div>
</script>

<script id="commonapprovefailuretmpl" type="text/x-handlebars-template">
<div class="alert alert-danger" role="alert">
      <strong>Failure!</strong> <font class="commonapprovealertmsg"></font></a>.
    </div>
</script>
</head>
<body>
	<div id="approvealertholder"></div>
	<div class="container-fluid ">
		<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
				<a href="<%=request.getContextPath()%>/app/dashboard">Dashboard
				</a> / Content Approve<span class="courseappholder pull-right"></span>
			</div>
		</div>
	</div>

	<div class="container-fluid ">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-4 leftpane min530 pad0"></div>
				<div class="col-md-8 rightpane min450 padright0">
					<div class="col-md-12 lightgraybg boxshadow lefttext">
						<h5 class="lineheight30">
							<a class="slidetoggledrop marright7"><i
								class="fa fa-align-justify"></i></a><font class="contenttitle">Section
								/ Lecture</font> <span class="appholder pull-right"></span>
						</h5>
						<div class="dropdownlecturestable whitebg padtop10"
							style="display: none;"></div>
					</div>
					<div class="container-fluid dropbox" style="display: none;">
						<div class="row dropbox titlecontainer form-horizontal pad5">
							<div class="col-md-12">
								<div class="col-sm-8 martop7">
									<textarea class="form-control sendcommomtextarea" type="text"
										placeholder="Enter Notification"></textarea>
									<div
										class="alert alert-success commouseralert sent lineheight20 mar0"
										style="display: none">
										<font class="commonusermsg">Please dont leave it blank.</font>
									</div>
									<div
										class="alert alert-danger commouseralert errorinsend lineheight20 mar0"
										style="display: none">
										<font class="commonusermsg">Please dont leave it blank.</font>
									</div>
								</div>
								<div class="col-sm-4 lefttext martop10">

									<a class="btn btn-primary sendcommonbtn marright7"> Send
										Notification </a> <a class="btn btn-danger closebtn"> Cancel </a>
								</div>
							</div>
						</div>
					</div>
					<div
						class="col-md-12 lightgraybg boxshadow lefttext padtopbottom10"
						id="contenttable" style="height: 450px;"></div>

				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row martop30">
			<div class="col-md-12 whitebg">
				<ul class="nav nav-tabs pad0 marbottom10 informationview">
					<li class="borderbottoma informationtablink tlink" al="info"><a
						class="learningtablinksec"> Information </a></li>
					<li class="borderbottoma quiztablink tlink" al="quiz"><a
						class="learningtablinksec"> Quiz </a></li>
					<li class="borderbottoma activitytablink tlink" al="activity"><a
						class="learningtablinksec"> Activity </a></li>
					<li class="borderbottoma boxitemtablink tlink" al="box"><a
						class="learningtablinksec"> Box Item </a></li>
					<li class="borderbottoma diagramtablink tlink" al="diagram"><a
						class="learningtablinksec"> Diagram </a></li>
					<li class="borderbottoma tabletablink tlink" al="table"><a
						class="learningtablinksec"> Table </a></li>
					<li class="borderbottoma maptablink tlink" al="map"><a
						class="learningtablinksec"> Map </a></li>
					<li class="borderbottoma referencetablink tlink" al="reference"><a
						class="learningtablinksec"> Reference </a></li>
					<li class="borderbottoma weblinkstablink tlink" al="weblink"><a
						class="learningtablinksec"> Weblinks </a></li>
					<li class="borderbottoma projectideastablink tlink"
						al="projectidea"><a class="learningtablinksec"> Project
							Ideas </a></li>
				</ul>
				<div class="tab-content">
					<div id="approvebottomtab" class="tab-content"
						style="min-height: 250px;"></div>
				</div>
			</div>
		</div>
	</div>

	<div id="usercommonmodal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 id="userModalLabel" class="modal-title">User Details</h4>
				</div>
				<div class="modal-body pad20 min450" id="usercommonmodalbody"></div>
			</div>
		</div>
	</div>

	<input type="hidden" name="courseid"
		value="<%=request.getParameter("courseid")%>">

	<input type="hidden" name="currentlectureid" value="">
	<input type="hidden" name="currentlecture" value="">
	<input type="hidden" name="currentsection" value="">
	<input type="hidden" name="currentcontentpath" value="">
	<input type="hidden" name="currentcontentid" value="">
	<input type="hidden" name="authorpersonid" value="">

	<input type="hidden" name="coursetitle" value="">

	<form name="contentapproveform" method="post"></form>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/flowplayer/iGplayer.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/admin/contentapprove.js"></script>
</body>
</html>