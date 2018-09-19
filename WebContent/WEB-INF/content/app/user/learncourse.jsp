<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Education-Cloud :: Start Learning</title>

<!-- css dependencies Start -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/flowplayer/skin/minimalist.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/pagination/css/pagination.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/whiteboard/css/literallycanvas.css">
<style type="text/css">
.custompagin .pagination a {
	padding: 6px 12px !important;
	margin-right: 0px !important;
}
.item img,.item object{
width: 70% !important;
    margin-left: 15%;
}
.reviewstar img {
	width: 7% !important;
}
</style>
<!-- css dependencies End -->

<script id="lecturestmpl" type="text/x-handlebars-template">
<div id="tabs" class="col-md-12 pad0">
{{#each .}}
	<span class="coursetitleclass" courseid="{{courseid}}" coursetitle="{{coursetitle}}" style="display:none;"></span>
		{{#each lectures}}
    		<div curr="tabsc" courselectureid='{{courselectureid}}' coursesectionid="{{coursesectionid}}">
				<a class="lineheight40 lecturestats padleft10" courseenrollmentid="{{courseenrollmentid}}" courselectureid='{{courselectureid}}' compstatus='{{compstatus}}'>
					<i class='fa fa-circle'></i>
				</a>
				<font class="lecturetitle" coursesectionid="{{coursesectionid}}" courselectureid="{{courselectureid}}" courselecturetitle="{{courselecturetitle}}">{{coursesectiontitle}} / {{courselecturetitle}}</font>
				<span class="maximum" style="float:right;" title="maximize"><i class="glyphicon glyphicon-resize-full maxi"></i></span>
    			<span class="minimum" style="float:right;" title="minimize"><i class="glyphicon glyphicon-resize-small mini"></i></span>			
			</div>
  		{{/each}}
{{/each}}
</div>
</script>
<!-- Lecture List Template End -->

<!-- Pdf Content Template Start -->
<script id="pdfcontenttmpl" type="text/x-handlebars-template"> 
{{#each .}}
<div class="row ">
  <div class="col-md-12 ">
     <object class="pdfcontent width100 min500" data="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{contentpath}}">
		Unable to view the content
	</object>
</div>
</div>
<p class="help-inline" style="color:red;"><b> Note : </b> To open external link in the pdf, use 'CTRL + LEFT CLICK'</p>  
{{/each}}
</script>
<!-- Pdf Content Template End -->

<!-- Video Content Template Start -->
<script id="videocontenttmpl" type="text/x-handlebars-template">
{{#each .}}

	<div class="col-md-12 flowplayer width100 min500" data-swf="<%=request.getContextPath()%>/assets/flowplayer/igplayer.swf" data-ratio="0.5" style="width: 95%; height: 570px;">
      <span class="maximum" style="float:right;" title="maximize"><i class=" icon-resize-full maxi"></i></span>
      <span class="minimum" style="float:right;" title="minimize"><i class="  icon-resize-small mini"></i></span>
		<video data-setup='{"customControlsOnMobile": true}'>
			<source type="video/flv" src="<%=request.getContextPath()%>/PlayFile?r1=serverpath&r2={{contentpath}}">
			</source>
		</video>
	</div>
   
{{/each}}
</script>
<!-- Video Content Template End -->

<!-- SWF Content Template Start -->
<script id="swfcontentmpl" type="text/x-handlebars-template">
{{#each .}}
 <span class="maximum" style="float:right;" title="maximize"><i class=" icon-resize-full maxi"></i></span>
 <span class="minimum" style="float:right;" title="minimize"><i class=" icon-resize-small mini"></i></span>
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
{{/each}}
</script>
<!-- SWF Content Template End -->

<!-- No Content Template Start -->
<script id="nocontenttmpl" type="text/x-handlebars-template">
<div class="row">
	<img class="datanotfound" src="<%=request.getContextPath()%>/assets/app/images/contenttype/contentnotfound.png" />
</div>
</script>
<!-- No Content Template End -->

<script id="textnotestmpl" type="text/x-handlebars-template">
<div class="martop10">
	<div class="col-md-12">
		<textarea class="textnotestextarea form-control"
			placeholder="Enter Your Notes"></textarea>
		<span class="textnotescharremaining martop10" style="font-weight: bold;">2000</span>
		Characters remaining<br>
		<div class="alert alert-danger invaliderror"
			style="display: none;">Invalid Content</div>
		<div class="alert alert-danger emptynotealert"
			style="display: none;">Empty Note Not allowed</div>												
		<div class="alert alert-success notessuccess"
			style="display: none;">Successfully Saved.</div>
		<a class="btn btn-success col-md-12 savetextnotes martopbottom10">Save Note</a>												
	</div>
</div>
<div class="col-md-12">
	<h3 class="size20">
		<a>Your Notes</a> 
		<a class="publicnotelink marbottom10 btn btn-success pull-right">Public Notes</a>
	</h3>
</div>
{{#if .}}
<div class="col-md-12 paper"  style="min-height:250px;">
{{#each .}}
<div class="col-md-12 padleft0 martop3">
	<div class="col-md-1 pad0"><i class="fa fa-angle-double-right"></i></div>
	<div class="col-md-6 pad0"><p title="Click here to edit" class="noteslink" 
			style="line-height:25px;max-height: 20px;overflow: hidden;" notesid="{{notesid}}">
		<a>{{decode notesdescription}}</a></p></div>
	<div class="col-md-5 pad0 righttext" style="line-height:25px;"><i class="fa fa-calendar marright5"></i>{{createddate}}</div> 	
</div>
{{/each}}
</div>
{{else}}
<div class="col-md-12 padleft0 martop3 paper" style="min-height:250px;">
	<p class="centertext martop7">No Notes Taken yet</p> 	
</div>
{{/if}}
</script>

<script id="publictextnotestmpl" type="text/x-handlebars-template">
<h3 class="size20">
	<a>Public Notes</a> 
	<a class="privatenotelink btn btn-success pull-right marbottom10">Your Notes</a>
</h3>
{{#if .}}
<div class="col-md-12 paper min450">
{{#each .}}
<div class="row padleft0 martop3">
	<div class="col-md-1 centertext"><i class="fa fa-angle-double-right"></i></div>
	<div class="col-md-6"><p title="Click here to edit" class="publicnoteslink" 
			style="max-height: 20px;overflow: hidden;" notesid="{{notesid}}">
		<a>{{decode notesdescription}}</a></p></div>
	<div class="col-md-5 pad0 righttext"><i class="fa fa-user marright5"></i>{{firstName}} {{lastName}}</div> 	
</div>
{{/each}}
</div>
{{else}}
<div class="col-md-12 padleft0 martop3 paper min450">
	<h5 class="size20 centertext">No Public Notes Available</h5> 	
</div>
{{/if}}
</script>

<script id="textnotedescriptiontmpl" type="text/x-handlebars-template">
{{#each .}}
<div class="col-md-12 successmessgagediv" style="display: none;">
	<div class="alert alert-success successmessgage pad5"></div>
</div>
<div class="col-md-12 padtopbottom10">
	<div class="col-md-1">
		<a class="btn btn-default graybg white backtonotes" courselectureid="{{courselectureid}}">
			<i class="fa fa-backward"></i>
		</a>
	</div>
	<div class="col-md-7 centertext martop7">
		<span class="updateareanotesremaining marright5" style="font-weight: bold;"></span>Characters remaining
	</div>
	<div class="col-md-4 pull-right pad0 righttext">
		<a class="btn btn-default graybg white updatenotesdesc" courselectureid="{{courselectureid}}" notesid="{{notesid}}">
			<i class="fa fa-save"></i></a>
		<a class="btn btn-default graybg white sharenote" courselectureid="{{courselectureid}}" notesid="{{notesid}}">
			<i class="fa fa-share-alt"></i></a> 
		<a class="btn btn-default graybg white downloadnote" 
			href="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2=/{{notespath}}&r3=download" a="{{notespath}}">
			<i class="fa fa-download"></i></a> 
		<a class="btn btn-default graybg white deletenote" courselectureid="{{courselectureid}}" notesid="{{notesid}}">
			<i class="fa fa-trash"></i></a>
	</div>
</div>
<div class="col-md-12 paper min450">
	<textarea class="form-control scrollable notesdescvalue"
		placeholder="Type your note here">{{decode notesdescription}}</textarea>
</div>
{{/each}}
</script>

<script id="publictextnotedescriptiontmpl"
	type="text/x-handlebars-template">
{{#each .}}
<div class="col-md-12 successmessgagediv" style="display: none;">
	<div class="alert alert-success successmessgage pad5"></div>
</div>
<div class="col-md-12 pad0 padtopbottom10">
	<div class="col-md-1">
		<a class="btn btn-default graybg white backtonotes" courselectureid="{{courselectureid}}">
			<i class="fa fa-backward"></i>
		</a>
	</div>
	<div class="col-md-4 pull-right pad0 righttext">
		<a class="btn btn-default graybg white downloadnote" 
			href="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2=/{{notespath}}&r3=download" a="{{notespath}}">
			<i class="fa fa-download"></i></a>
	</div>
</div>
<div class="col-md-12 paper min450">
	<p class="scrollable breakword" style="line-height:30px;margin-left:30px;">{{decode notesdescription}}</p>
</div>
{{/each}}
</script>
<!-- Notes List Template End -->


<script id="scratchpadlisttmpl" type="text/x-handlebars-template">
<div class="martop10">
	<div class="col-md-12">
		<a class="btn btn-success col-md-12 newScratchPad martopbottom10">New Scratch Pad</a>												
	</div>
</div>
{{#if .}}
<div class="col-md-12 paper" style="padding-top:3px;">
{{#each .}}
<div class="row padleft0 scratchpadpagination">
	<div class="col-md-1 centertext"><i class="fa fa-angle-double-right"></i></div>
	<div class="col-md-6"><p title="Click here to view" class="publicnoteslink" 
			style="max-height: 20px;overflow: hidden;" notesid="{{notesid}}">
		<a class="changecustompath scratchbotespath" notespath="{{notespath}}" notesid="{{notesid}}"> -- </a></div>
	<div class="col-md-5 pad0 righttext"><i class="fa fa-calendar marright5"></i>{{createddate}}</div> 	
</div>
{{/each}}
</div>
{{else}}
<div class="col-md-12 paper" style="">
<h5 class="centertext">No Scratch pad images found</h5>
</div>
{{/if}}
<div class='row custompagin centertext'>
  <div class='col-md-12'>
    <div class="col-md-9 col-md-push-3 centertext pagination"></div>
  </div>
</div>
</script>

<script id="newscratchpadtmpl" type="text/x-handlebars-template">
<div class="col-md-12 martop10">
	<div class="alert alert-success newscratchalert successalert" style="display:none;"><b class="marright5">Success !</b>Image Saved. </div>
	<div class="alert alert-danger newscratchalert failurealert" style="display:none;"><b class="marright5">Failure !</b>Upload Failed. Try again later </div>
	<div class="alert alert-danger newscratchalert emptyalert" style="display:none;">
			<b class="marright5">Warning !</b>Please Draw Something. </div>	
	<div class="literally borderall1gray pad5 height400"></div>
	<div class="col-md-12 martopbottom10">
		<a class="btn btn-success col-md-5 col-md-offset-1 savescratchpad "><i class="fa fa-save marright5"></i>Save Image</a>
		<a class="btn btn-success col-md-5 col-md-offset-1 backtoscratchpadlist "><i class="fa fa-backward marright5"></i>Back</a>
	</div>
</div>
</script>


<script id="scratchPadDescriptiontmpl" type="text/x-handlebars-template">
<div class="col-md-12 pad0 padtopbottom10">
<div class="alert alert-success trashsuccessalert" style="display:none;"><b class="marright5">Success !</b>Successfully Deleted. </div>
	<div class="alert alert-danger trashfailurealert" style="display:none;"><b class="marright5">Failure !</b>Delete Failed. Try again later </div>
	<div class="col-md-1">
		<a class="btn btn-default graybg white backtoscratch" courselectureid="{{courselectureid}}">
			<i class="fa fa-backward"></i>
		</a>
	</div>
	<div class="col-md-9">
		<h5 class="centertext">{{fileName}}</h5>
	</div>
	<div class="col-md-2 pull-right pad0 righttext">
		<a class="btn btn-default graybg white trashscratchbtn" notesid="{{notesid}}">
			<i class="fa fa-trash"></i></a>
		<a class="btn btn-default graybg white" 
			href="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2=/{{imageFile}}&r3=download">
			<i class="fa fa-download"></i></a>
	</div>
</div>
<div class="col-md-12 pad0 boxshadow">
	<img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2=/{{imageFile}}" class="width100 borderall1gray pad5 height400"/>
</div>
</script>

<script id="modifiedLectureReviewstmpl"
	type="text/x-handlebars-template">
<div class="col-md-12 pad0 padtopbottom10">
	<div class="col-md-4 centertext">
		<h5 class="size20">My Review</h5>
		{{#if myReviews}}
			<h5 class="size20 martop30">You have rated as below </h5><br>
			<span class="reviewstar martop30"></span>
		{{else}}
		{{#each reviewtype}}
			<div class="col-md-12 pad0 padtopbottom10" >
				<span class="col-md-6 reviewtype righttext"> {{reviewtype}} : </span><input type="hidden" class="reviewid" value={{reviewid}}>
				<span class="col-md-6 newratediv ratingcourse lefttext" style="width:50% !important;"></span>
			</div>
		{{/each}}
		<div class="row" style="text-align:center;margin-bottom:10px;">
			<a class="btn btn-primary btn-success " id="totalrating"><i class="icon-save"></i> <b> Save </b></a>
		</div>
		{{/if}}
	</div>
	<div class="col-md-8 blog-comment">
		<h5 class="size20">Other's Reviews</h5>
		{{#if otherReviews}}
			<ul class='comments' style="margin-left: 0px;">
			{{#each otherReviews}}
				<li class='clearfix'><img class="avatar"
					src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{photo}}&filetype=photo" />
					<div class='post-comments lightgraybg'>
						<p class='meta' style="border-bottom: 1px solid white;">
							On {{rateddate}} <a class="capitalise"> {{firstName}} {{lastName}}</a> rated :
						</p>			
						 <span class="othersreviewstar martop30 ratingcourse" rating="{{averagerevieworiginal}}"></span>
					</div>
				</li>
			{{/each}}
			</ul>
		{{else}}
			<div class='lightgraybg padtop50' style="height:270px;">
				<p class='meta centertext size20 martop30 padtop50'> No Data Found </p>			
			</div>			
		{{/if}}
	</div>
</div>
</script>
<!-- Notes List Template End -->


<script id="discussionquestionstmpl" type="text/x-handlebars-template">
<div class="col-md-12 pad0 padtopbottom10">
	<div class="col-md-4 centertext">
		<h5 class="size20">Start Your Discussion</h5>
		<div class="form-horizontal" style="padding: 10px;">
			<div class="controls" style='margin-left: 0px;'>
				<textarea class="discussioninput form-control" rows="8"
					placeholder="Enter Your Question" maxlength="500"
					style="border: 1px solid #E4E4E4; resize: none; margin-bottom: 10px; background: rgb(255, 255, 255);"></textarea>
			</div>
			<div class="alert alert-danger discussionempty" style="display: none;"><b>Warning ! </b>Please Fill the question.</div>
			<div class="alert alert-success discussionsuccess" style="display: none;"><b>Success ! </b>Discussion Successfully Saved.</div>
			<div class="alert alert-danger discussionfailure" style="display: none;"><b>Error ! </b>Error in saving the discussion. Please Try again later. </div>
			<div class="controls">
				<font class="pull-left"><span class="discussionremaining">500</span> Characters Remaining </font><button id="questionit"
					class="btn btn-primary pull-right submitdiscussion">Ask</button>
			</div>
		</div>
	</div>
	<div class="col-md-8 blog-comment">
		<h5 class="size20">Discussions</h5>
		<ul class='comments' style="margin-left: 0px;">
			{{#if .}} {{#each .}}
			<li class='clearfix'><img class="avatar"
				src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{photo}}&filetype=photo" />
				<div class='post-comments lightgraybg'>
					<p class='meta' style="border-bottom: 1px solid white;">
						On {{askeddate}}<a> {{askedperson}} </a> asked : <a><i
							class='pull-right'><small
								class='discussionanswerlink' questionid="{{FAQid}}" style="cursor: pointer; border-bottom: 0;">Answers</small></i></a>
					</p>
					<p style='text-overflow: ellipsis; overflow: hidden;'>
						{{decode FAQquestion}}
					</p>
				</div></li> {{/each}} {{else}}
			<div class='lightgraybg padtop50' style="height: 270px;">
				<p class='meta centertext size20 martop30 padtop50'>No Data
					Found</p>
			</div>
			{{/if}}
		</ul>
	</div>
</div>
</script>

<script id="discussionanswerstmpl" type="text/x-handlebars-template">
<div class="col-md-12 pad0 padtopbottom10">
	<div class="col-md-4">
		<font><b class="cornflowerblue">Question </b>: {{questions.FAQquestion}}</font>
		<div class="form-horizontal" style="padding: 10px;">
			<div class="controls" style='margin-left: 0px;'>
				<textarea class="discussionanswerinput form-control" rows="8"
					placeholder="Enter Your Answer" maxlength="500"
					style="border: 1px solid #E4E4E4; resize: none; margin-bottom: 10px; 
					background: rgb(255, 255, 255);"></textarea>
			</div>
			<div class="alert alert-danger discussionswernempty" style="display: none;"><b>Warning ! </b>Please Fill the answer.</div>
			<div class="alert alert-success discussionanswersuccess" style="display: none;"><b>Success ! </b>Answer Successfully Saved.</div>
			<div class="alert alert-danger discussionanswerfailure" style="display: none;"><b>Error ! </b>Error in saving the answer. Please Try again later. </div>
			<div class="controls">
				<font class="pull-left"><span class="discussionanswerremaining">500</span> Characters Remaining </font><button 
					class="btn btn-primary pull-right submitdiscussionanswer">Submit</button>
			</div>
		</div>
	</div>
	<div class="col-md-8 blog-comment">
		<h5 class="size20">Answers <a class="btn btn-success backtodiscussions pull-right">Back To Discussions</a></h5>
		<ul class='comments padtop10' style="margin-left: 0px;">
			{{#if answers}} {{#each answers}}
			<li class='clearfix'><img class="avatar"
				src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{photo}}&filetype=photo" />
				<div class='post-comments lightgraybg'>
					<p class='meta' style="border-bottom: 1px solid white;">
						On {{answereddate}}<a> {{answeredperson}} </a> answered :</p>
					<p style='text-overflow: ellipsis; overflow: hidden;'>
						{{#correctanswer faqanswerstatus faqanswerid faqanswer}}{{/correctanswer}} {{faqanswer}} 
					</p>
				</div></li> {{/each}} {{else}}
			<div class='lightgraybg padtop50' style="height: 270px;">
				<p class='meta centertext size20 martop30 padtop50'>No Data
					Found</p>
			</div>
			{{/if}}
		</ul>
	</div>
</div>
</script>


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
<h3>Activities</h3>
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
<h3>Course Log Book</h3>
{{#if  .}}
<div id="cars" class="carousel slide cars">
<div class="carousel-inner">
   	{{#each .}}
		<div class="item boxshadow pad10">
			<%-- <img src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{description}}" 
				class="width100 height400 boxshadow"/>  --%>
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
	<h5><i class="fa fa-angle-double-right marright7"></i>No Project Ideas Found.</h5>
{{/if}}
</div>
</script>

<script id="subjectpartstmpl" type="text/x-handlebars-template">
<div class="tree">
<ul style="margin-left:5px;">
{{#each .}}
<li style="padding-left: 10px;">
	<span class="titles"><i class="fa fa-minus-sign"></i>{{coursesectiontitle}}</span>
    <ul class="lecclass" style="padding-top: 13px;">
		{{#each lectures}}       
    	<li style="padding-bottom: 17px;lineheight:30px;">
			<span class="hover grandchildtitles" coursesectionid="{{coursesectionid}}" courselectureid="{{courselectureid}}" style="border: 1px solid #F3DCDC !important;">{{courselecturetitle}}</span> 
		</li>
		{{/each}}
	</ul>
</li>
{{/each}}
</ul>
</div>
</script>

<!-- QUESTIONS TEMPLATE START -->
<script id="questionstmpl" type="text/x-handlebars-template">

{{#each .}}
{{#ifEquals [0].qtypeid}}
	<b><ol start="{{[0].sno}}"><li class="qst-chk " qstid="{{[0].questionid}}"> Match The Following </li></ol></b>
	<center><span style="color: crimson;"><strong >Note: </strong>If you have judge the answer <strong>Drag</strong> and <strong> Drop</strong> may be you will get correct answer</span></center>
		<div>
			<div class="row"><h5 class="col-md-6"><u>Question</u></h5><h5 class="span6"><u>Answer</u></h5></div>
			<div class="row">
				<ul>
					<ol id="questions" class="col-md-6">
						{{{match-question [0].question}}}
					</ol>
					<ol id="sortable" type="A"  class="col-md-6">
						{{#each .}}		
							<li class="" id="{{answerid}}"><a href="#"><p>{{{question-helper answers}}}</p></a></li>
						{{/each}}
					</ol>
				</ul>
			</div>
	</div>

{{else}}

{{#ifEqualSEC [0].qtypeid}}
  <b><ol start="{{[0].sno}}"  ><li class="qstid-li" qstid="{{[0].questionid}}">{{{question-helper [0].question}}}</li></ol></b>
	<center><span style="color: crimson;"><strong >Note: </strong>If you have judge the answer <strong>Drag</strong> and <strong> Drop</strong> on blank area may be you will get correct answer</span></center>
	<h5>Answer</h5>
	<div class="row">
	<ol id="draggable">
		{{#each .}}
			<li style="display:inline;margin-left: 15px;margin-bottom: 9px;padding: 5px;"><i class="icon-cog" style="font-size: 8pt;margin-right: 5px;"></i>{{answers}}</li>
		{{/each}}	
	</ol>
</div>
			
{{else}}
<div class="row">
		<div class="col-md-6">
				<div class="alert-message alert-message-success pad5">
					<ol start="{{[0].sno}}"><li>{{{question-helper [0].question}}}</li></ol>
				</div>
		</div>
		<div class="col-md-6">
			{{#each .}}
				<div class="alert-message alert-message-info">
					<h4><label class="radio deselected padleft10">
      					<input type="radio" name="optionsRadios"  value="option1" class="qst-chk"  qstid="{{questionid}}" answerid="{{answerid}}" {{select-me studentanswer}}>
      					{{{question-helper answers}}}
    				</label></h4>
				</div>	
			{{/each}}
		</div>
</div>
{{/ifEqualSEC}}

{{/ifEquals}}

<div class="col-md-12" style="display:none" id="description">
	<h5>Answer Description</h5>
<hr style="margin-top: 0px;">
	{{{answer-description []}}}
<hr>	
</div>

	
<center id="p-status" style="display:none">
    	<span style="color: red;" >Your answer has updated!!!</spna>
    </center>
    <div style="text-align: center;">
    	{{{dynamic-btn [0].evaluationid [0].answerstate}}}
    		
    </div>		
{{/each}}

</script>
<!-- QUESTIONS TEMPLATE END -->
<script id="questionsAnswertmpl" type="text/x-handlebars-template">
<center><h4><u>Your Answered Details</u></h4></center>
<h6 class="pull-right" id="points">Points : 0 out of 0</h6>
<br>

<ol>
{{#each .}}
{{#ifEquals [0].id}}
	<b><li class="qst-chk"  qstid="{{[0].questionid}}"> Match The Following </li></b>
		<div>
			<div class="row"><h5 class="span6"><u>Question</u></h5><h5 class="span6" style="margin-left: 0px;"><u>Correct Answer</u></h5></div>
			<div class="row" style="margin-top: 0px;">
				<ul>
					<ol id="questions" class="span6">
						{{{match-question [0].question}}}
					</ol>
					<ol id="sortable" type="A"  class="span6">
						{{#each .}}		
							<li class="" id="{{answerid}}"><a href="#"><p>{{{question-helper answers}}}</p></a></li>
						{{/each}}
					</ol>
				</ul>
			</div>
	</div>
{{else}}
<div class="row">
	<b><li>{{{question-helper [0].question}}}</li></b>
	<h6 style="color: cornflowerblue;"><u>Correct Answer</u></h6>
		<div class="row">
			<div class="span1" style="width: 5px;">
				<i class="icon-ok" style="font-size: 16pt; color: rgb(116, 235, 116);"></i>
			</div> 
			<div class="span8">
				<ol type="A" class="sign{{[0].questionid}}">
					{{#answer .}}
						{{{question-helper answers}}}				
					{{/answer}}
				</ol>
			</div>
		</div>
	
</div>
{{/ifEquals}}
<h6 style="color: cornflowerblue;"><u>Your Answer</u></h6>
<div class="row">
	<div class="col-md-1 icon{{[0].questionid}}" style="width: 5px;">
	</div> 
	<div class="col-md-8 pad0">
		<ol class="st-ans" type="A">
		
 		</ol>
	</div>
</div>
<hr>
{{/each}}
</ol>
<center>
	<span class='btn btn-flat btn-success' onClick=SATEvaluation.Controllers.displayResult() style='width:100px;'>Back</span>
</center>
</script>

<!-- STUDENT RESULT START -->
<script id="stdAnstmpl" type="text/x-handlebars-template">
	{{#each .}}
		{{{std-status answerstate}}}
	{{/each}}
</script>
<!-- STUDENT RESULT END -->

<!-- STUDENT RESULT START -->
<script id="stdResulttmpl" type="text/x-handlebars-template">
<center>
	<div class="pagination result" style="margin-bottom: -2px !important;">
 	 	<ul>
			{{#each .}}
				{{{answer-status answerstate}}}
			{{/each}}
	 	 </ul>
	</div>
</center>
<div class="row">
	<ul class="thumbnails" >
		<li class="col-md-4">
			<center style="margin-top: 50px;">
  				<div id="socialGraph_3" style="height: 160px;"></div>
			<center>
		</li>
		<li class="col-md-8" style="margin-bottom: 0px;">
			
				<div class="row alert-message alert-message-info pad10 marbottom10">
					<h4><span class="quizreport">Great!<span><div class="pull-right" id="crtqstatus" style=" margin-top: -14px; "><small style='color: darkgray;font-size: 10px;'>Questions:<strong class="strong">0 </strong>| Answered:<strong class="strong">0 </strong> | Skipped:<strong class="strong">0 </strong> | Correct:<strong class="strong">0 </strong></small></div></h4>
					<h5 id="status-sms">Good try! Lets start again try those are wrong</h5>
					<div class="row">
						<div class="col-md-5">Average score : <strong id="crtAverage">0%</strong></div>
						<div class="col-md-7 righttext modify-date-crnt"></div>					
					</div>
				</div>
				<div class="row alert-message alert-message-success pad10">
					<h4>Class Activity !</h4>
					<h5><small style='color: darkgray;font-size: 10px;'>Questions:<strong class="strong">0 </strong>| Answered:<strong class="strong">0 </strong> | Skipped:<strong class="strong">0 </strong>  | Correct:<strong class="strong">0 </strong> | 1st time correct:<strong class="strong">0 </strong></small></h5>					
					<div class="row">
						<div class="col-md-5">First Time Score : <strong id="firstTime">0%</strong></div>
						<div class="col-md-7 modify-date righttext"></div>
					</div>
				</div>
	</li>
		<center>
			<span class="btn btn-flat btn-success" style="padding-top: 2px;padding-bottom: 3px;" onClick="SATEvaluation.Business.getCorrectAnswer()">Check your answers</span>
			<span class="btn btn-flat btn-primary" style="padding-top: 2px;padding-bottom: 3px;" onClick="SATEvaluation.Controllers.changePage()">Try again</span>
		</center>
	</ul>
</div>
<div class="row">
	<ul class="thumbnails" >
		<li class="col-md-12" style="margin-bottom: 0px;">
  			<div class="thumbnail status-bar" style=" background:#e84f4c;padding: 10px;">
   			</div>
		</li>
	</ul>
</div>

</script>
<!-- STUDENT RESULT END -->
<!-- SAT-EVALUATION RESULT START -->
<script id="qustionResulttmpl" type="text/x-handlebars-template">
<center>
	{{#each .}}
		<h2>Result</h2>
		{{ans}}
		<p style=" text-align: center;" ><b>{{percent}}</b></p><p style=" text-align: center;" >{{sms}}</p><p style=" text-align: center;" ><b>Time Spent</b><br>{{time}}</p>
		<a type='button' class='btn btn-flat btn-primary pull-left' onClick="SATEvaluation.Views.renderStdAnswer('view')"  style='height: 18px;' >Check Your Answers</a>
		<a type='button' class='btn btn-flat btn-success pull-right' onClick="SATEvaluation.Views.renderStdAnswer('re-again')"  style='height: 18px;' >Try Again</a>
	{{/each}}
</center>
</script>
<!-- SAT-EVALUATION RESULT END-->

<!-- SAT-EVALUATION QUESTION EMPTY TEMPLATE START -->
<script id="emptyQuestionstmpl" type="text/x-handlebars-template">
<div style="text-align:center;" >
   <div style="height:350px;">
    	<div style="padding-top: 70px;">
     		<i class="icon-question-sign" style="font-size: 60pt;color: gray;"></i>
       		<h2 style="color: gray;padding-bottom: 10px;"> Questions Not Available </h2>
 		</div>
 	</div>
 </div>
</script>
<!-- SAT-EVALUATION QUESTION EMPTY TEMPLATE END -->

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
		
		String roleName = userDetailsAdaptor.getUser().getProperty(
				"rolename");
	%>
	<input type="hidden" id="rolename" value="<%=roleName%>">
	<span id="context" style="display: none;"><%=request.getContextPath()%></span>
	<span class="pagat" orgpersonid="<%=orgpersonid%>"
		style="display: none;"></span>
	<div class="container-fluid ">
		<div class="row titlecontainer">
			<div class="col-md-12 lefttext">
				<a class="slidetoggledrop"><i class="fa fa-align-justify"></i></a> <a
					href="<%=request.getContextPath()%>/app/mycourses">My Courses </a>
				/ <a class="learningcourse previewbtn"></a> / learn
				<div class="dropdownlecturestable whitebg padtop10"
					style="display: none;"></div>
			</div>

		</div>
	</div>

	<div class="container-fluid whitebg pad0">
		<div id="alllectures" class="col-md-12 pad0 borderbottom1"></div>
	</div>

	<div class="container-fluid whitebg boxshadow pad0">
		<div class="col-md-12 pad0">
			<div class="col-md-7 pad10" id="contenttable"></div>
			<div class="col-md-5 pad0 padright10 lecturesupplementary">
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane active" id="first">
						<ul id="learntab"
							class="nav nav-tabs informationviewul padbottom0">
							<li class="borderbottoma informationtablink active tlink" al="info"><a
								class="learningtablinksec">Information</a></li>
							<li class="borderbottoma notestablink tlink" al="notes"><a
								class="learningtablinksec"> Notes </a></li>
							<li class="borderbottoma scratchtablink tlink" al="scratchpad"><a
								class="learningtablinksec"> Scratch Pad </a></li>
							<li class="borderbottoma"><a class="learningtablinksec quiz"
								href="#"> Quiz </a></li>
						</ul>
						<div id="learningsidetab"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div class="row martop30">
			<div class="col-md-12 whitebg">
				<ul class="nav nav-tabs informationview pad0" id="myTab" 
					style="margin-bottom: 10px;">
					<!-- <li class="borderbottoma diagramtablink tlink" al="diagram"><a
						class="learningtablinksec"> Course Log Book </a></li> --> 
					<li class="borderbottoma discussiontablink tlink" al="discussion"><a
						class="learningtablinksec"> Discussions </a></li>
 					<!-- <li class="borderbottoma activitytablink tlink" al="activity"><a
						class="learningtablinksec"> Activity </a></li>
					<li class="borderbottoma boxitemtablink tlink" al="box"><a
						class="learningtablinksec"> Box Item </a></li> -->  					
					<li class="borderbottoma tabletablink tlink" al="table"><a
						class="learningtablinksec"> HandOuts </a></li>
					<li class="borderbottoma maptablink tlink" al="map"><a
						class="learningtablinksec"> Mind Map </a></li>
					<li class="borderbottoma referencetablink tlink" al="reference"><a
						class="learningtablinksec"> Teachers Guide </a></li>
					<li class="borderbottoma weblinkstablink tlink" al="weblink"><a
						class="learningtablinksec"> Weblinks </a></li>
					<li class="borderbottoma projectideastablink tlink" al="projectidea"><a
						class="learningtablinksec"> Project Ideas </a></li>
					<li class="borderbottoma reviewstablink tlink" al="review"><a
						class="learningtablinksec"> Reviews </a></li>  
				</ul>
				<div class="tab-content">
					<div id="myTabContent3" class="tab-content"
						style="min-height: 250px;"></div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal" id="questionmodal" data-backdrop="static"
		style="top: 10%;">
		<div class="modal-dialog width70per">
			<div class="modal-content">
				<div class="modal-header">

					<div class="row">
						<div class="col-md-12">
							<div class="col-md-10">
								<h4 class="modal-title"
									style="margin-bottom: 0px; font-weight: normal; font-size: 15px !important;"></h4>
								<small style="color: gray;" class="total-qst">Total
									no.of Question(s) : 0</small>
							</div>
							<div class="col-md-2 ">
								<font class="clock size20 cornflowerblue">00:00</font>
								<button type="button" class="close close-btn"
									data-dismiss="modal" aria-hidden="true">X</button>
							</div>
						</div>
					</div>
					<%-- <small id="status" style="color: cornflowerblue;margin-left: 10px;"></small> --%>
				</div>

				<div class="modal-body" style="padding-top: 0px;">
					<div id="showmessage" style="padding-left: 8%; width: 90%;">
					</div>
					<center>
						<div class="pagination result"
							style="margin-bottom: -5px !important;">
							<ul id="stdAnsUL">
							</ul>
						</div>
					</center>
					<div id="questionsDiv"></div>

				</div>
				<div class="modal-footer"
					style="padding: 4px 9px 8px; display: none;">
					<span class="alert-danger notification pull-right" role="alert"
						style="font-size: 10pt; margin-bottom: 0px; padding-top: 2px; padding-bottom: 3px; padding-right: 3px; padding-left: 10px;">
						I am done, lets evaluate... <a type="button"
						class="btn btn-flat btn-danger delt"
						style="font-size: smaller; height: 16px; padding: 0px 5px 1px 5px; margin-bottom: 1px;"
						onclick="SATEvaluation.Controllers.displayResult()">Yes</a>
					</span>
				</div>
			</div>
		</div>

	</div>

	<span id="contentlecstarttime"></span>
	<span id="contentlecendtime"></span>
	<form method="post" name="previewform" action="">
	<input type="hidden" name="courseid" value="">
		<s:hidden name="courseenrollmentid" value="%{courseenrollmentid}"></s:hidden>
		<s:hidden name="courselectureid" value="%{courselectureid}"></s:hidden>
	</form>
	<!-- Javascript Dependencies Start -->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/whiteboard/js/react-0.10.0.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/whiteboard/js/literallycanvas.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/flowplayer/iGplayer.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/users/learningpage.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/users/calculatetimespent.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/flowplayer/playerOptions/flowPlayerOptions.js"></script>
 	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/users/evaluation.js"></script>
	<script type="text/javascript" 
		src="<%=request.getContextPath()%>/assets/lib/js/jquery.ui.js"></script>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/pagination/js/jquery.pagination.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/chart/js/morris.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/chart/js/raphael-min.js"></script>
	<!-- Javascript Dependencies End -->
</body>
</html>