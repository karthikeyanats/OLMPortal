<%@page import="com.igrandee.core.login.util.UserDetailsAdaptor"%>
<%@page language="java" import="java.util.ResourceBundle"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page
	import="org.springframework.security.core.context.SecurityContextHolder,
 com.igrandee.product.ecloud.session.SessionDetail"%>
<%
	UserDetailsAdaptor userDetailsAdaptor = null;
	userDetailsAdaptor = (UserDetailsAdaptor) SecurityContextHolder
			.getContext().getAuthentication().getPrincipal();
	String personid = userDetailsAdaptor.getUser().getProperty(
			"orgpersonid");
	String roleName = userDetailsAdaptor.getUser().getProperty(
			"rolename");
	ResourceBundle bundle = null;
	bundle = ResourceBundle.getBundle("application");
	String board = bundle.getString("label.board");
	String category = bundle.getString("label.category");
	String medium = bundle.getString("label.medium");
	String standardlevel = bundle.getString("label.standardlevel");
	String section = bundle.getString("label.section");
	String lecture = bundle.getString("label.lecture");
%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/fullcalendar/css/fullcalendar.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/plugins/datepicker/css/bootstrap-datetimepicker.min.css">

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/plugins/summernote/summernote.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/flowplayer/skin/minimalist.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/assets/whiteboard/css/literallycanvas.css">
<script id="noquestionsstmpl" type="text/x-handlebars-template">
<p class="description center">No Questions have been asked yet</p>
</script>
<script id="faqtmpl" type="text/x-handlebars-template">
<table class="table table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Question</th>
			<th>Answers</th>
		</tr>
	</thead>
	<tbody>
	{{#if .}}
		{{#each .}}
			<tr class="pad5">
				<td>{{math @index "+" 1}}</td>
				<td>{{FAQquestion}}<br>
					<font class="lightbrown">Asked By : {{askedperson}} at {{askeddate}}</font></td>				
				<td class="centertext">
					<a class="btn btn-primary answerlink individualfaq" FAQid="{{FAQid}}" FAQquestion="{{FAQquestion}}">
						<i class="fa fa-angle-double-right"></i>
					</a>
				</td>
			</tr>
		{{/each}}
	{{else}}
		<tr>
			<td colspan="4">No Questions</td>
		</tr>
	{{/if}}
	</tbody>
</table>
</script>
<script id="answerstmpl" type="text/x-handlebars-template">
<table class="table table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>			
			<th>Answer</th>
		</tr>
	</thead>
	<tbody>
	{{#if .}}
		{{#each .}}
			<tr class="pad5">
				<td>{{math @index "+" 1}}</td>
				<td>
					{{#correctanswer faqanswerstatus faqanswerid faqanswer}}
							<input type='checkbox' id='correctanswer' checked 
								faqanswerid={{faqanswerid}} class='correctanswerchk marright7'>
								{{faqanswer}}
					{{else}}
							<input type='checkbox' id='correctanswer' 
								faqanswerid={{faqanswerid}} class='correctanswerchk marright7'>
									{{faqanswer}}
					{{/correctanswer}}
					<br><font class="lightbrown">Answered By {{answeredperson}} On {{answereddate}}</font>
				</td>
			</tr>
		{{/each}}
	{{else}}
		<tr>
			<td colspan="2" class="centertext">No Answers</td>
		</tr>
	{{/if}}
	</tbody>
</table>
</script>
<script id="trashanswerstmpl" type="text/x-handlebars-template">
<table class="table table-striped whitebg">
	<thead>
		<tr>
			<th>Sl.No</th>			
			<th>Answer</th>
		</tr>
	</thead>
	<tbody>
	{{#if .}}
		{{#each .}}
			<tr class="pad5">
				<td>{{math @index "+" 1}}</td>
				<td class="individualanswer">
					{{#correctanswer faqanswerstatus faqanswerid faqanswer}}
						<input type='checkbox' id='correctanswer' checked faqanswerid={{faqanswerid}} class='correctanswerchk'>&nbsp;<font>{{faqanswer}}</font><br>
					{{else}}
						<input type='checkbox' id='correctanswer' faqanswerid={{faqanswerid}} class='correctanswerchk'>&nbsp;<font>{{faqanswer}}</font><br>
					{{/correctanswer}}	
					<br><font class="lightbrown">Answered By {{answeredperson}} On {{answereddate}}</font>
				</td>
			</tr>
		{{/each}}
	{{else}}
		<tr>
			<td colspan="2" class="centertext">No Answers to delete</td>
		</tr>
	{{/if}}
	</tbody>
</table>
</script>
<!-- Course Basic Information Template start -->


<script id="basicinfotmpl" type="text/x-handlebars-template">
{{#each .}}
<li class="leftsideli leftsideseperateli">Author :<font class="liitems"> {{firstName}} </font></li>
<li class="leftsideli leftsideseperateli">Qualification :<font class="liitems"> {{eduqualification}}</font></li>
<li class="leftsideli leftsideseperateli pricebtn" price="{{price}}" priceicon ="<s:text name="label.price"></s:text>"></li>
<li class="leftsideli leftsideseperateli">Questions
	<span class="pull-right draftcoursecount">
		{{questionscount}}
	</span>
	<ul class="questionspart" style="margin-left: 5px;margin-top: 5px;">		
	</ul>
</li>
<li class="leftsideli leftsideseperateli">Notes
	<span class="pull-right draftcoursecount">
		{{notescount}}
	</span>
	<ul class="notespart" style="margin-left: 5px;margin-top: 5px;">
		
	</ul>
</li>
{{/each}}
</script>
<!-- Course Basic Information Template end -->

<script id="courseheadertmpl" type="text/x-handlebars-template">
{{#each .}}
	<form name="coursemanageform" method="post">
		<a coursestatus="{{coursestatus}}" courseid="{{courseid}}" sectioncount="{{sectioncount}}" lecturecount="{{lecturecount}}" contentcount="{{contentcount}}"
			 class='martop3 white btn btn-blue publishcoursebtn statusbtn{{coursestatus}}'>
			Publish
		</a> 
		<input type="hidden" name="coursetitlehidden" value="{{coursetitle}}">
		<input type="hidden" name="courseid" value="{{courseid}}">
		<input type="hidden" name="bankinfo" value="">
	</form>
{{/each}}
</script>

<script id="coursecontenttmpl" type="text/x-handlebars-template">
<div class="form-horizontal pad5">
	<div class="row martopbottom10 ">
		<div class="col-md-12 newsectionrow centertext">
			<a class="newsectionbtn btn btn-flat btn-blue white" courseid=""> 
				Add New <%=section%>
			</a>
		</div>
	</div>
	{{#each .}}
	<div class="boxshadow pad5 marbottom10">
	<table class='table whitebg marbottom5 borderbottom1'>
		<tbody>
			<tr>
				<td class="noborder capitalise padtop15 size17"><%=section%> : {{decode coursesectiontitle}}<i class="fa fa-pencil editsectionbtn padleft10"   
					coursesectiontitle="{{coursesectiontitle}}" courseid="{{courseid}}" coursesectionid="{{coursesectionid}}" title="Edit this section"></i></th>
				<td class="noborder pull-right">
					<a class="white btn btn-blue deletecourse deletestatus{{coursestatus}}" 
						courseid="{{courseid}}" coursesectionid="{{coursesectionid}}" style="display:none;" title="Delete <%=section%>">
						<i class="fa fa-trash width10"></i>
					</a>
					<a class="white btn btn-blue addNewLecturebtn" courseid="{{courseid}}" 
						coursesectionid="{{coursesectionid}}" coursesectiontitle="{{coursesectiontitle}}" 
						title="Add New <%=lecture%>">
						<i class="fa fa-plus width10"></i>
					</a>
				</td>
			</tr>
		</tbody>
	</table>
	{{#if lectures}}
		{{#each lectures}}
			<table class='table whitebg marbottom10'>
				<tbody>
					<tr>
						<td class="padtop10 col-md-1 ligtgreenbg noborder roundicon1 contenttypeclass preview-lecture-btn" contentstatus="{{contentstatus}}" contenttype="{{contentytpe}}" 
                       		courselectureid="{{courselectureid}}" courselecturetitle="{{courselecturetitle}}" courseid="{{courseid}}" coursesectionid="{{coursesectionid}}" contentPath ="{{contentpath}}" title="Preview Content">
						</td>						
						<td class="noborder capitalise padtop15"><%=lecture%> : {{decodes courselecturetitle}}<i class="fa fa-pencil editlecturebtn padleft10" courselectureid="{{courselectureid}}" courselecturetitle="{{courselecturetitle}}" courseid="{{courseid}}" coursesectionid="{{coursesectionid}}" title="Edit <%=lecture%>"></i></th>
						<td class="noborder pull-right">
							<a class="btn btn-blue white displayinblock editmetainfobtn" metainfoid="{{metainfoid}}" courselectureid="{{courselectureid}}" title="Edit Meta Info">
								<i class="fa fa-info width10"></i>
							</a>
							<a class="btn quizbtn btn-blue displayinblock white" courselecturetitle="{{courselecturetitle}}" courseid="{{courseid}}" courselectureid="{{courselectureid}}" title="View Quiz">
								<i class="fa fa-question width10" ></i>
							</a>
							<a class="btn btn-blue white displayinblock deletelectcourse deletelecturestatus{{coursestatus}}" courseid="{{courseid}}" courselectureid="{{courselectureid}}" style="display:none;" title="Delete Lecture">
								<i class="fa fa-trash width10" ></i>
							</a>
							<a class="btn btn-blue white displayinblock lecturequestbtn {{coursestatus}}" courseid="{{courseid}}" courselectureid="{{courselectureid}}" title="<%=lecture%> Questions">
								<i class="fa fa-comment width10"></i>
							</a>
							<a class="btn btn-blue white displayinblock addContentbtn" coursetitle="{{coursetitle}}" coursesectiontitle="{{decodes coursesectiontitle}}" courselecturetitle="{{courselecturetitle}}" courselectureid="{{courselectureid}}" courseid="{{courseid}}" 
								coursesectionid="{{coursesectionid}}" title="Add Content">
								<i class="fa fa-plus width10"></i>
								<input type="hidden" name="courselectureid" value=""/>
							</a>							
						</td>
					</tr>
				</tbody>
			</table>
		{{/each}}
	</div>
	{{else}}
	<div class="row nolecturediv">
		<h5 style="text-align:center;">
			No <%=lecture%> Found
		</h5>
	</div>
	{{/if}}
{{/each}}
</div>
</script>
<!-- Course Basic Information Template start -->
<script id="coursebasicinfotmpl" type="text/x-handlebars-template">
{{#each .}}
<div class="form-horizontal" style="margin-top: 10px; padding-left: 10px;">
	<div class="form-group">
	<label class="col-sm-4 control-label"></label>		
		<div class="col-sm-7 marright7">
			<font class='pull-right' style="color:red;">* Fields are mandatory</font>		
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label righttext">Course Title <font
			style="color: red; margin-right: 5px;">*</font> : </label>
		<div class="col-sm-7">
			<input type="text" required="required" contenteditable="true"
			class="col-md-11 form-control" value="{{coursetitle}}" name="coursetitle" savedvalue="{{coursetitle}}"
			maxlength="50" placeholder="Enter Course Title">
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label righttext">Sub Title : </label>
		<div class="col-sm-7">
			<input type="text" required="required" contenteditable="true"
				class="col-md-11 form-control" value="{{coursesubtitle}}" name="coursesubtitle"
				maxlength="100" placeholder="Enter Course Subtitle">
		</div>
	</div>
	
	<div class="form-group">
		<label class="righttext col-sm-4 control-label righttext">Course Description<font
			style="color: red; margin-right: 5px;">*</font> : </label>
		<div class="col-sm-7">
			<textarea class="col-md-12 borderall1gray" required="required" rows=5
				name="coursedescription" maxlength="1000">{{coursedescription}}</textarea>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 righttext">Intended Audience : </label>
		<div class="col-sm-7">
			<textarea class="col-sm-12 borderall1gray" required="required" name="intendedaudience" rows=3
				maxlength="500" placeholder="Intended Audience for the Course">{{intendedaudience}}</textarea>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-4 righttext" maxlength="45">Keywords<font
			style="color: red; margin-right: 5px;">*</font> : </label>
		<div class="col-sm-7">
			<input type="text" contenteditable="true" required="required"
				class="col-md-11 form-control" value="{{coursegoal}}" name="coursegoal"
				maxlength="45">
		</div>
	</div>
	<div class="form-group">
		<label class="righttext col-sm-4 control-label">Price <font
			style="color: red; margin-right: 5px;">*</font> : </label>
		<div class="col-sm-7">
			<div class="col-md-4 pad0">
				<div class="btn-group lefttext">
        			<button type="button" price="{{price}}" class="btn pricestatusbtn priceactivebtn white">Price</button>
        			<button type="button" price="0" class="btn pricefreebtn pricestatusbtn white">Free</button>        
      			</div>				
			</div>
			<div class="col-md-4">
				<input type="text" contenteditable="true" required="required" 
					class="form-control col-md-12 priceshowbtn" priceid="{{priceid}}"
					value="{{price}}" name="price" maxlength="15">
			</div>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-4 righttext">Privacy :</label>
		<div class="col-sm-7">
			<div class="col-md-12 pad0">
				<div class="btn-group lefttext">
        			<button type="button" class="btn publicbtn privacybtn white">Public</button>
        			<button type="button" class="btn privatebtnbtn privacybtn white">Private</button>
					<input type="hidden" privacyid="{{privacyid}}"
					value="{{privacyname}}" name="privacyname">        
      			</div>				
			</div>
		</div>
	</div>
	<div class="form-group">
		<label class=" col-sm-4 righttext">Instructional Level : </label>
		<div class="col-sm-7">			
				<div class="btn-group lefttext">
        			<button type="button" class="btn levelbtn white border" level="Beginner">Beginner</button> 
					<button type="button" class="btn levelbtn white border" level="Intermediate">Intermediate</button>
					<button type="button" class="btn levelbtn white" level="Advanced">Advanced</button>        
					<input type="hidden" name="instructoinallevel"
				value="{{instructoinallevel}}"> <input type="hidden"
				name="coursestatus" value="{{coursestatus}}">      						
			</div>
		</div>		
	</div>
	<div class="form-group">
		<input type="hidden" id="boardsid" value="{{boardid}}">
		<label class="control-label righttext col-sm-4"><%=board%><font style="color: red; margin-right: 5px;">*</font> : </label>
		<div class="col-md-4">
			<select class="col-md-6 form-control" name="" id="boardlistinfo">
			<option value="0">--Select <%=board%>--</option>
			</select> 
		</div>
	</div>
	<div class="form-group">
		<input type="hidden" name="coursecategoryid"
			value="{{coursecategoryid}}"> <label class="control-label righttext col-sm-4"><%=category%><font
			style="color: red; margin-right: 5px;">*</font> : </label>
		<div class="col-md-4">
			<select class="col-md-6 form-control" name="crscategoryid" id="categorytable">
			</select><input type="hidden"
				value="<s:property value="%{coursecategoryname}" />"
				id="crscategoryidhiddenval"> <input type="hidden"
				value="<s:property value="%{coursecategoryid}" />"
				id="coursecategoryidhiddentextfield">
		</div>
	</div>
	<div class="form-group">
		<input type="hidden" id="mediumids" value="{{mediumid}}">
		 <label class="control-label col-sm-4 righttext"><%=medium%><font style="color: red; margin-right: 5px;">*</font> : </label>
		<div class="col-md-4">
			<select class="col-md-6 form-control" name="" id="mediumlistinfo">
			<option value="0">--Select <%=medium%>--</option>
			</select> 
		</div>
	</div>
	<div class="form-group">
		<input type="hidden" id="standardids" value="{{standardid}}">
		<label class="control-label col-sm-4 righttext"><%=standardlevel%><font style="color: red; margin-right: 5px;">*</font> : </label>
		<div class="col-md-4">
			<select class="col-md-6 form-control" name="" id="standardlistinfo">
			<option value="0">--Select <%=standardlevel%>--</option>
			</select> 
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-4 righttext">Promo Video<font
			style="color: red; margin-right: 5px;">*</font> : </label>
		<div class="col-md-8">
			<input type="file" id="promovideo" required="required" uploadedpath="{{coursepromevideopath}}">
 			<span style="color:red;">Recommended Formats : flv, mp4.<br> Recommended Size: 30 MB</span>
			 
         <div class="progress progress-info promostatusbar" style="height:5px;width:400px;margin-top:6px;display:none;">
  			<div class="progress-bar progress-bar-success promostatus"></div>
		</div>
        <div class="promomsg" style="display:none;font-weight: bold;"></div>
        <div class="filename" value={{coursepromevideopath}}>{{promofilename}}&nbsp&nbsp&nbsp&nbsp
         {{#if coursepromevideopath}}
          <a  class="btn btn-blue previewpromo white martop7" data-toggle="tooltip" title="save before preview" data-placement="top" >preview video</a>
         {{/if}}
        </div>
        <div class="modal promo-preview-content-modal fade" id="promo-preview-content-modal">
			<div class="modal-dialog">
			<div class="modal-content col-md-12">
			<div class="modal-header" id="promo-preview-modal-header">
 				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Promo Video Preview</h4>
			</div>
			<div class="modal-body" style="min-height: 375px;">
			<div class="row" id="promo-content-space"></div>
		</div>
		</div>
		</div>
		</div>
		</div>       
	</div>
	
	<div class="form-group padbottom10">		
		<label class="control-label col-sm-2 righttext"></label>
		<div class="col-md-3">
		<span class="btn btn-flat btn-primary coursebasicinfosavebtn white width100"
			courseid="{{courseid}}"> Save </span> 
		</div>
	</div>

	<input type="hidden" name="courselogo1" value="{{courselogo}}"> 
	<input type="hidden" name="coursepromevideopath"
		value="{{coursepromevideopath}}"> <input type="hidden"
		name="coursecreateddate" value="{{coursecreateddate}}">
</div>
{{/each}}
</script>
<!-- Road Map Template start -->
<script id="roadmaptmpl1" type="text/x-handlebars-template">
<div class="marbottom10">
            <div class="tabbable tabs-left">
              <ul class="nav nav-tabs pad0 padtop10 ">
                <li class="borderbottoma active roadmaptab"><a href="#lA" data-toggle="tab" >Plan 
				</a></li>
              <li class="borderbottoma roadmaptab"><a href="#lB" data-toggle="tab">Step 1 <span class="pull-right"><i class="icon-chevron-right"></i></span></a></li>
                <li class="borderbottoma roadmaptab"><a href="#lC" data-toggle="tab">Step 2 <span class="pull-right"><i class="icon-chevron-right"></i></span></a></li>
				<li class="borderbottoma roadmaptab"><a href="#lD" data-toggle="tab">Step 3 <span class="pull-right"><i class="icon-chevron-right"></i></span></a></li>
                <li class="borderbottoma roadmaptab"><a href="#lE" data-toggle="tab">Step 4 <span class="pull-right"><i class="icon-chevron-right"></i></span></a></li>
			</ul>
              <div class="tab-content pad10 padtop20">
                <div class="tab-pane active" id="lA">
                  <h4>Plan Your Course</h4>
                  <div class="row">
                     <div class="col-md-12">
                         <p class="justifytext">Planning is key to online learning. Not only will it save you countless production hours,
						     it will also help create an organized and effective online learning experience.
						      Overall, your course should be a progressive series of independent Lectures
						      (or standalone lessons) that help students achieve the course goal.
						      To help plan your course, follow the guidelines below.
                         </p>
					 </div>
                  </div>                   
					<div class="row">
                        <div class="col-md-12">
					  		<p style="text-align:justify;">
                                1.You must have to fill the Basic information of the course.They are 
                                 <ol>
                                   <li>Title</li>
                                   <li>Description</li>
								   <li>Keywords</li>
                                   <li>Price</li> 
								   <li>Category </li> 
								   <li>Sub-Category</li> 
								   <li>Medium</li> 
								   <li>Standard Level</li> 
								   <li>Promo video</li>
                                 </ol>
                             <p style="text-align:justify;">
                                2.Upload the Logo                                
                             </p>
                              <p style="text-align:justify;">
                                3.You must have to fill  content  of your course.They are
                                 <ol>
                                   <li><%=section%></li>
                                   <li><%=lecture%></li>
                                   <li>Meta info</li> 
								   <li>Questions </li> 
                                   <li>Content</li>
                                 </ol>                                 
                             </p>
                          </p>
                         </div>
                     </div>
                  </div>
                <div class="tab-pane" id="lB">
                  <div class="">
                    <h4> Basic  Information Your Course</h4>
                     <div class="row">
                         <div class="col-md-12">
                              <p style="text-align:justify;">
                                   <ol> 
                                      <li>In Basic Information Course Title should not have more than 50 characters.</li>                                    
                                      <li>Course Description should not have more than 1000 characters.</li>
                                      <li>Set the Price of your Course.</li>
									  <li>Select the Category of your Course.</li>
							          <li>Keyword should not have more than 45 characters.</li>
 									  <li>Promo video have to be added.<br><span style="color:red">Recommended size : 30 MB. 
									<br>Recommended Formats : flv, mp4.</span></li>
                                    </ol>
                              </p>
                         </div>
					 </div>                     
 				  </div>
                </div>
                <div class="tab-pane" id="lC">
               	 <h4> Create Your Course Logo</h4>
                  <div class="row">
                         <div class="col-md-12">
                           <p style="text-align:justify;">
							  You have to upload the Logo for your course.<br>
								<span style="color:red">Recommended Formats : jpg,jpeg,png.<br>Recommended size : 1 MB.</span>
                           </p>
                     </div>
                 </div>
                </div>
               <div class="tab-pane" id="lD">
               	 <h4> Create Your Course Content</h4>
                  <div class="row">
                         <div class="col-md-12">
                           <p style="text-align:justify;">
							Here is where you'll get your hands dirty by creating new course materials or modifying
							existing ones. You will need to choose the best way to discuss your Lecture topic
							(e.g. Video,Pdf), but do note:
							Reamedi recommends that your course consists of at least 60% video lecture content
							(includes screencasts) and has a mix of different content types for differentiated
							instruction.
                           </p>
                           		  <ol> 
                                      <li>In Course Content,section title should not have more than 50 characters.</li>                                    
                                      <li>Add lecture for the section,here the lecture title should not have more than 50 characters.</li>                                       
                                      <li>Upload the content of your Course.</br>
                                       <span style="color:red">Recommended Formats : flv,mp4,pdf,swf<br>
										<span style="color:red">Recommended Size : 800 MB</li>
                                  </ol>
							<p style="text-align:justify;">
							Create the time schedule for a particular course in a date, you will be allowed to 
                            edit and delete the schedule.Schedule Modal will be open in current and future date. In 
                            Previous date should not allowed to create the schedule.
                           </p>
                     </div>	
                 </div>
                </div>
                <div class="tab-pane" id="lE">
               	 <h4> Publish Your Course</h4>
                  <div class="row">
                         <div class="col-md-12">
                           <p style="text-align:justify;">
							 Once you have created or prepared the course content, you will be ready to upload
							 and publish your course on the Reamedi platform. The process is very easy,
							 to publish the Course.
                           </p>
                     </div>
                 </div>
                </div>

 <div class="tab-pane" id="lF">
               	 <h4> Create Schedule</h4>
                  <div class="row-fluid">
                         <div class="span12">
                           <p style="text-align:justify;">
							Create the time schedule for a particular course in a date, you will be allowed to 
                            edit and delete the schedule.Schedule Modal will be open in current and future date. In 
                            Previous date should not allowed to create the schedule.
                           </p>
                     </div>
                 </div>
                </div>
              </div>
            </div> 
</script>
<!-- Course Basic Information Template start -->
<script id="coursebasicinfotmpl1" type="text/x-handlebars-template">
{{#each .}}
<div class="form-horizontal" style="margin-top: 10px; padding-left: 10px;">	
	<div class="form-group">
		<label class="col-sm-4 control-label righttext"><b>Title : </b></label>
		<div class="col-sm-7 control-label">
			<p>{{coursetitle}}</p>			
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label righttext"><b>Sub-Title : </b></label>
		<div class="col-sm-7 control-label">
			{{#if coursesubtitle}}
				<p>{{coursesubtitle}}</p>
			{{else}}
			<label class="control-label">-----------</label>
			{{/if}}
						
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label righttext"><b>Course Description : </b></label>
		<div class="col-sm-7 control-label">
			{{#if coursedescription}}
				<p">{{coursedescription}}</p>
			{{else}}
			<label class="control-label">-----------</label>
			{{/if}}
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label righttext"><b>Price : </b></label>
		<div class="col-sm-7 control-label">
			{{#if price}}
				<p">{{price}}</p>
			{{else}}
			<label class="control-label">-----------</label>
			{{/if}}
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label righttext"><b>Board : </b></label>
		<div class="col-sm-7 control-label">
			{{#if boardname}}
				<p>{{boardname}}</p>
			{{else}}
			<label class="control-label">-----------</label>
			{{/if}}			
		</div>
	</div>	
	<div class="form-group">
		<label class="col-sm-4 control-label righttext"><b>Category : </b></label>
		<div class="col-sm-7 control-label">
			{{#if coursecategoryname}}
				<p>{{coursecategoryname}}</p>
			{{else}}
			<label class="control-label">-----------</label>
			{{/if}}			
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label righttext"><b>Medium : </b></label>
		<div class="col-sm-7 control-label">
			{{#if mediumname}}
				<p>{{mediumname}}</p>
			{{else}}
			<label class="control-label">-----------</label>
			{{/if}}			
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label righttext"><b>Standard Level : </b></label>
		<div class="col-sm-7 control-label">
			{{#if standardname}}
				<p>{{standardname}}</p>
			{{else}}
			<label class="control-label">-----------</label>
			{{/if}}			
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label righttext"><b>Privacy : </b></label>
		<div class="col-sm-7 control-label">
			{{#if privacyname}}
				<p>{{privacyname}}</p>
			{{else}}
			<label class="control-label">-----------</label>
			{{/if}}		
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label righttext"><b>Keywords : </b></label>
		<div class="col-sm-7 control-label">
			{{#if coursegoal}}
				<p>{{coursegoal}}</p>
			{{else}}
			<label class="control-label">-----------</label>
			{{/if}}			
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label righttext"><b>Intended Audience : </b></label>
		<div class="col-sm-7 control-label">
			{{#if intendedaudience}}
				<p>{{intendedaudience}}</p>
			{{else}}
			<label class="control-label">-----------</label>
			{{/if}}			
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-4 control-label righttext"><b>Instructional Level : </b></label>
		<div class="col-sm-7 control-label">
			{{#if instructoinallevel}}
				<p>{{instructoinallevel}}</p>
			{{else}}
			<label class="control-label">-----------</label>
			{{/if}}			
		</div>
	</div>
    <div class="form-group">
		<label class="col-sm-4 control-label righttext"><b>Promo Video : </b></label>
		<div class="col-sm-7 control-label">
			{{#if coursepromevideopath}}
				<p>{{coursepromevideopath}}&nbsp&nbsp&nbsp<input type="button" promofilename={{coursepromevideopath}} class="btn btn-blue previewpromo1" value="preview video"/></p>
                <div class="modal promo-preview-content-modal1 fade" id="promo-preview-content-modal1">
					<div class="modal-dialog">
						<div class="modal-content"><div class="modal-header" id="promo-preview-modal-header1">
 						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                		<h3 class="modal-title">Promo Video Preview</h3>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-12"id="promo-content-space1"></div>
					</div>
			</div></div></div>
			{{else}}
			<label class="control-label">-----------</label>
			{{/if}}			
		</div>
	</div>
</div>
{{/each}}
</script>
<!-- Course Basic Information Template End -->
<!-- New Course Logo Template Start -->
<script id="newcourselogotmpl" type="text/x-handlebars-template">
{{#each .}}
<div class="col-md-12 form-horizontal">
	<form 
		action="" method="post"
		enctype="multipart/form-data" name="uplogoform">
		<input type="hidden" name='courseidval' value="{{courseid}}">
		<input type="hidden" name='logosizeval' value="">
		<div class="form-group martop10 martopbottom10">
			<label class="col-sm-2 control-label righttext" for="filebutton">Logo : </label>
			<div class="col-sm-8 marbottom10">
				<input name="cologo" id="input-logo-file" class="input-logo-file marbottom10" type="file">
				<a class="btn btn-flat btn-blue cancelfileupload martop7 white">Cancel</a>
				<p class="help-block" style="margin: 0px;">
<span style="color:red;">Recommended Formats : jpg,jpeg,png.<br>Recommended Size : 1 MB</span>
</p>
				<div class="control-group prggrop martop10">
					<font class="progressper">0% </font>Uploaded <span class="pull-right">Please
						wait...</span>
					<div class="progress">
						<div class="logoupprogress progress-bar progress-bar-success"></div>
					</div>
				</div>
				<div class="alert alert-warning page-alert" id="alert-3" style="display: none;">
        			<strong>Warning!</strong> Invalid file format.
    			</div>
			</div>         
	</div>	

 		<div class="alert alert-error errblcok" style="display:none;">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<strong class="strbtn">Warning!</strong> <font class="emsg">Best
				check yo self, you're not looking too good.</font>
		</div>
		
		<div class="form-group">
			<label class="control-label col-sm-2" for="singlebutton"></label>
			<div class="col-sm-4">
				<a class="btn btn-flat btn-success uploadlogocnt">Upload</a>
			</div>
		</div>
		<input type="hidden" value="<%=request.getContextPath()%>" class="contextpath"></span>
	</form>
</div>
{{/each}}
</script>
<!-- New Course Logo Template End -->
<!-- Course Logo Template Start -->
<script id="courselogotmpl" type="text/x-handlebars-template">
{{#each .}}
<div class="form-horizontal"
	style="margin-top: 10px; padding-left: 10px;">
	<div class="form-group">
		<label class="control-label col-sm-2 righttext"><b>Logo : </b></label>
		<div class="col-sm-4">
			<img
				src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{courselogo}}&filetype=logo"
				class="height140 marbottom10 courselogo"></img> <input type="hidden"
				name="courselogo" value="{{courselogo}}"> <a
				class="btn btn-flat btn-blue white changelogobtn"> Change Image </a>
		</div>
	</div>
</div>
{{/each}}
</script>
<!-- Course Logo Template End -->

<!-- Category List Template start -->
<script id="categorytmpl" type="text/x-handlebars-template">
<option value="0">--Select <%=category%>--</option>
{{#each .}}
	<option value="{{coursecategoryid}}">{{coursecategoryname}}</option>
{{/each}}
</script>
<!-- Category List Template End -->

<!-- Upload Template Template start -->
<script id="uploadcontenttmpl" type="text/x-handlebars-template">
<div class="col-md-12 martop10" id="newcon">
	<form class="form-horizontal" method="post"
		enctype="multipart/form-data" name="upform">
		<input type="hidden" name="courselectureidhidden" value=""> <input
			type="hidden" name="filesizeval" value="">
		<div class="form-group">
			<label class="col-sm-3 righttext" for="filebutton">Content : </label>
			<div class="col-sm-8">
				<input name="file" class="input-file" type="file" id="contfile">
				<p class="help-block"><span style="color:red;">Recommended Formats :pdf,swf,flv,mp4.<br>Recommended Size : 800 MB</span></p>

			</div>
		</div>
 
		<div class="form-group encgroup" style="display: none">
			<label class="col-sm-3 righttext">Encrypt Video : </label>
			<div class="col-sm-5">
				<div class="col-md-8 pad0">
					<div class="btn-group lefttext">
        				<button type="button" class="btn btn-blue encryptbtn white" enst="Y">Yes</button>
        				<button type="button" class="btn encryptbtn white" enst="Y">No</button>
						<input type="hidden" name="enstatus" value="Y">        
      				</div>				
				</div>
				
			</div>
		</div>


 <div class="alert alert-warning page-alert" id="invalidalert" style="display: none;">
        <button type="button" class="close"><span aria-hidden="true">×</span></button>
        <strong>Warning!</strong> Invalid File Format.
    </div>	
		<div class="alert alert-danger errblcok">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<strong class="strbtn">Warning!</strong> <font class="emsg">Best
				check yo self, you're not looking too good.</font>
		</div>
		<div class="form-group prggrop">
			<font class="progressper">0% </font>Uploaded <span class="pull-right">Please
				wait...</span>
			<div class="progress" style="width:100%";>
				<div class="progress-bar progress-bar-success"></div>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 " for="singlebutton"></label>
			<div class="col-sm-5">
				<a class="btn btn-success uploadcnt">Upload</a>
			</div>
		</div>
	</form>
</div>
<div id='showcontenttable'></div>
</script>

<!-- Upload Template Template start -->
<!-- Uploaded Content Template Start -->
<script id="fileuploadrendertemplate" type="text/x-handlebars-template">
<table class="table table-bordered table-hover table-striped">
	<thead>
		<tr>
			<th>FileName</th>
			<th>Status</th>			
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		{{#each .}}
		<tr>
			<td class="overflowtext">{{contentpath}}</td>
			<td class="contentapprovestatus" approvestatus="{{approvestatus}}" approvedescription="{{approvedescription}}"></td>			
			<td><a contentpath="{{oripath}}" contentid={{contentid}}
				class="btn btn-danger removecontentbtn"> Remove </a></td>
		</tr>
		{{/each}}
	</tbody>
</table>
</script>
<!-- Uploaded Content Template end -->

<script id="constructionstmpl" type="text/x-handlebars-template">
<div style="text-align:center;" >
   <div style="height:350px;">
    	<div style="padding-top: 120px;">
       			<i class="icon-wrench" style="font-size: 60pt;color: gray;"></i>
       			<h2 style="color: gray;padding-bottom: 10px;">Under Construction</h2>
 		</div>
 	</div>
 </div>
</script>


<script id="newQstinfotmpl" type="text/x-handlebars-template">
<div class="row-fluid" style="text-align: center;">
	<div class="input-append radioBtn" style="padding-top: 5px;" >
	{{#each .}}
		<a class="btn btn-flat qutype" data-toggle="happy" data-title="{{id}}" templ="{{id}}templ" >{{questiontype}}</a>
	{{/each}}
 		<input type="hidden" name="happy" id="happy" value="1">
	</div>
</div>
<hr>

<div id="dynamicForm">
</div>

</script>

<script id="previewContentTempl-pdf" type="text/x-handlebars-template"> 
<div class="col-md-12">
	<object class="pdfcontent width100 min450" data="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{contentpath}}">
		Unable to view the content
	</object>
</div>  
</script>

<script id="encpreviewContentTempl-video"
	type="text/x-handlebars-template"> 	
<div class="col-md-12 flowplayer width100 min450" data-swf="<%=request.getContextPath()%>/assets/flowplayer/igplayer.swf" data-ratio="0.5" style="width: 95%; height: 570px;">
	<video data-setup='{"customControlsOnMobile": true}'>
		<source type="video/flv" src="<%=request.getContextPath()%>/PlayFile?r1=serverpath&r2={{contentpath}}">
		</source>
	</video>
</div>	   
</script>


<script id="previewContentTempl-video" type="text/x-handlebars-template"> 
<div class="col-md-12 flowplayer preview  width100 min450 max450" data-swf="<%=request.getContextPath()%>/assets/flowplayer/igplayer.swf" data-ratio="0.5" style="width: 95%; height: 570px;">
	<video data-setup='{"customControlsOnMobile": true}'>
		<source type="video/flv" src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{contentpath}}">
		</source>
	</video>
</div>   
</script>

<script id="autopreviewContentTempl-video"
	type="text/x-handlebars-template"> 
<div class="col-md-12 flowplayer autopreview  width100 min450 max450" data-swf="<%=request.getContextPath()%>/assets/flowplayer/igplayer.swf" data-ratio="0.5" style="width: 95%; height: 570px;">
	<video data-setup='{"customControlsOnMobile": true}'>
		<source type="video/flv" src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{contentpath}}">
		</source>
	</video>
</div>   
</script>

<script id="promoContentTempl-video" type="text/x-handlebars-template"> 
<div class="col-md-12 flowplayer promo width100 min450 max450" data-swf="<%=request.getContextPath()%>/assets/flowplayer/igplayer.swf" data-ratio="0.5" style="width: 95%; height: 570px;">
	<video data-setup='{"customControlsOnMobile": true}'>
		<source type="video/flv" src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2=/{{coursepromevideopath}}">
		</source>
	</video>
</div>	   
</script>

<script id="previewContentTempl-swf" type="text/x-handlebars-template"> 
	<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" 
		codebase="http://download.macromedia.com/pub/shockwave/
		cabs/flash/swflash.cab#version=6,0,40,0" id="mymoviename" > 
			<param name="movie" value="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{contentpath}}" /> 
			<param name="quality" value="high" /> 
			<param name="bgcolor" value="#fff" /> 
			<param name="wmode" value="transparent"> 
			<embed class="swfembed" style="width: 100%;height: 440PX;" src="<%=request.getContextPath()%>/OpenFile?r1=serverpath&r2={{contentpath}}" 
				wmode="transparent" quality="high" bgcolor="#ffffff" name="mymoviename" align="" type="application/x-shockwave-flash"> 
			</embed> 
	</object>  
</script>

<script id="nocontenttmpl" type="text/x-handlebars-template">
<div class="row-fluid">
	<img class="datanotfound" src="<%=request.getContextPath()%>/assets/app/images/contenttype/contentnotfound.png" />
</div>
</script>

<%-- <script id="questionsinfotmpl" type="text/x-handlebars-template">
	<div class="row-fluid">
		Current questions
	</div>
	</script> --%>
<script id="preview-content-modal-title-template"
	type="text/x-handlebars-template">
				<button type="button" class="close" data-dismiss="modal">x</button>
				<h3>Preview Content : {{courselecturetitle}}</h3>
</script>
<!-- new question Template start -->
<script id="newquiztmpl" type="text/x-handlebars-template">
<div class="form-horizontal">
<!-- Text input-->
<div class="control-group">
  <label class="control-label" for="textinput">Quiz Question</label>
  <div class="controls">
    <input name="textinput" type="text" placeholder="Enter your Quiz Question Here..........." 
		class="input-xlarge questioninput">    
  </div>
</div>

<!-- Prepended checkbox -->
<div class="control-group">
  <label class="control-label" for="prependedcheckbox">Option 1</label>
  <div class="controls">
    <div class="input-append">
      
      <input name="prependedcheckbox" class="input-xlarge option" type="text" placeholder="Option 1">
<span class="add-on">
        <label class="radio">
          <input type="radio" name="checkbox">
        </label>
      </span>    
</div>
    
  </div>
</div>

<!-- Prepended checkbox -->
<div class="control-group">
  <label class="control-label" for="prependedcheckbox">Option 2</label>
  <div class="controls">
    <div class="input-append">
      
      <input name="prependedcheckbox" class="input-xlarge option" type="text" placeholder="Option 2">
<span class="add-on">
        <label class="radio">
          <input type="radio" name="checkbox">
        </label>
      </span>    
</div>
    
  </div>
</div>

<!-- Prepended checkbox -->
<div class="control-group">
  <label class="control-label" for="prependedcheckbox">Option 3</label>
  <div class="controls">
    <div class="input-append">
     
      <input name="prependedcheckbox" class="input-xlarge option" type="text" placeholder="Option 3">
    
 <span class="add-on">
        <label class="radio">
          <input type="radio" name="checkbox">
        </label>
      </span></div>
    
  </div>
</div>

<!-- Prepended checkbox -->
<div class="control-group">
  <label class="control-label" for="prependedcheckbox">Option 4</label>
  <div class="controls">
    <div class="input-append">
      
      <input name="prependedcheckbox" class="input-xlarge option" type="text" placeholder="Option 4">
  <span class="add-on">
        <label class="radio">
          <input type="radio" name="checkbox">
        </label>
      </span>  </div>
    
  </div>
</div>
<!-- Button -->
<div class="control-group">
  <label class="control-label" for="singlebutton"></label>
  <div class="controls">
    <button name="singlebutton" class="btn btn-primary btn-flat newquestionsavebtn">Save</button>
  </div>
</div>
</div>
</script>
<!-- new question Template end -->
<!-- Manage course Handlebar Templates End -->


<script id="timetemplate" type="text/x-handlebars-template">
<table class="table table-bordered table-hover table-striped">
	<thead>
		<tr>
			<th>Sl.No</th>
			<th>Start Time</th>
			<th>End Time</th>
            <th class="width28">Description</th>
			<th>price(<i class="fa iconclass <s:text name='label.price'></s:text>"></i>)</th>
            <th>Action</th>			
			
		</tr>
	</thead>
	<tbody>
{{#if .}}
		{{#each .}}
		<tr>
            <td>{{math @index "+" 1}}</td>
			<td>{{starttime}}</td>
			<td>{{endtime}}</td>
            <td class="breakword">{{description}}</td>		
			<td>{{price}}</td>	

            <td><a class="btn btn-flat editlive "  livesessionid="{{livesessionid}}">
						<i class="fa fa-pencil"></i></a>
<a class="btn btn-flat deletelive" livesessionid="{{livesessionid}}">
						<i class="fa fa-trash icon-large"></i>
					</a>
					</td>
		</tr>
		{{/each}}
{{else}}
<tr>
			<td colspan="7"><p class="center categorydescription">No Schedule</p></td>
		</tr>
{{/if}}
	</tbody>
</table>
</script>


<script id="errormessagetmpl" type="text/x-handlebars-template">
<div class="alert alert-danger alert-dismissible" role="alert">
<button type="button" class="close" data-dismiss="alert">
	<span aria-hidden="true">×</span>
</button>
<strong>Warning!</strong> {{errormessage}}</div>
</script>




</head>
<body>
	<%
		String courseid1 = request.getParameter("courseid");
	%>
	<div class="alert alert-success publishedscss" style="display: none;">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<strong>Success!</strong> Course has been published.
	</div>
	<input type="hidden" class="sectionhiddenval">

	<div class="container-fluid titlecontainer">
		<div class="row">
			<div class="col-md-12 lefttext pad0">
				<div class="col-md-10">
					<%
						if (roleName.equalsIgnoreCase("user")) {
					%>
					<a href="<%=request.getContextPath()%>/app/mycourses">My
						Courses </a>
					<%
						} else {
					%>
					<a href="<%=request.getContextPath()%>/app/dashboard">Dashboard
					</a> / <a href="<%=request.getContextPath()%>/app/browseA">Browse
						Courses </a>
					<%
						}
					%>
					/ <a>Manage Course</a> - <font class="rightpanetitle"
						courseid="<%=courseid1%>" coursetitle="">Course</font>
				</div>
				<div class="col-md-2 righttext" id="courseheadertable"></div>
			</div>
		</div>
	</div>

	<div class="container-fluid marbottom10">
		<div class="row">
			<div class="col-md-12 pad0">
				<div class="col-md-4 whitebg min450 boxshadow pad0"
					id="courseroadmaptable"></div>
				<div class="col-md-8 padright0 min450" id="managecolmd">
					<div class="tabbable tabs-left whitebg boxshadow">
						<ul id="myTab" class="nav nav-tabs padbottom0">
							<li id="basicli" class="borderbottoma active"><a
								href="#basicinfo" class="learningtablinks" data-toggle="tab">
									Basic Information </a></li>
							<li id="courselogoli" class="borderbottoma"><a
								class="learningtablinks" href="#courselogo">Logo</a></li>
							<li id="contentli" class="borderbottoma"><a
								class="learningtablinks" href="#content" data-toggle="tab">
									Content </a></li>
							<li id="scheduleli" class="borderbottoma"><a
								class="learningtablinks" href="#schedule" data-toggle="tab">
									Schedule </a></li>
							<li class="borderbottoma" id="examTabs" style="display:none;"><a id="exam"
								class="learningtablinks" href="#examInfo" data-toggle="tab">
									Exam </a></li>
							<li class="borderbottoma"><a style="display: none;"
								id="quizAtag" class="learningtablinks" href="#quizinfo"
								data-toggle="tab"> Quiz </a></li>
						</ul>
						<div class="tab-content min500">
							<div class="tab-pane active" id="basicinfo">
								<div id="coursebasicinfotable">Basic Information</div>
							</div>
							<div class="tab-pane" id="content">
								<div id="coursecontenttable"></div>
							</div>
							<div class="tab-pane" id="courselogo">
								<div id="courselogotable"></div>
							</div>
							<div class="tab-pane pad20" id="schedule">
								<div id="calendar" style="display: block;"></div>
							</div>
							<div id="questionTab">
								<div class="col-md-12 questionpart">
									<div id="pop1" class="popbox martop10">
										<font class="lineheight30">Questions</font><a
											class="btn btn-primary ansbackqnbtn pull-right"
											data-popbox="pop1"> <i
											class="fa fa-angle-double-left marright7"></i>Back to
											Lectures
										</a>
									</div>
									<span class="coursetitle"><font
										class="coursetitle lectureheader" FAQid=""></font></span><br>
									<div id="faqtable"></div>
								</div>
								<div class="col-md-12 answerpart" style="display: none;">
									<div class="col-md-6 martop7 padleft0">
										<i class="fa fa-question-circle marright7"></i><font
											class="coursetitle questionheader lineheight30" FAQid=""></font>
									</div>
									<div class="col-md-6 martop7 padright0">
										<a class="btn btn-primary backqnbtn pull-right"
											data-popbox="pop1"><i class="fa fa-angle-double-left"></i>
											Questions</a> <a id='trashanslistbtn'
											class="btn btn-primary pull-right marright7"> <i
											class="fa fa-trash-o marright7"></i><i class="fa fa-th-list"></i></a>
										<a id='trashansbtn'
											class="btn btn-primary pull-right marright7"> <i
											class="fa fa-trash-o"></i></a> <a id='correctansbtn'
											class="btn btn-primary pull-right marright7"> <i
											class="fa fa-check"></i></a>
									</div>
									<div id="pop1" class="martop10"></div>

									<br> <span class="coursetitle"></span><br>
									<div class="row-fluid answerinput" style="display: none;">
										<div class="span12">
											<textarea class="answertextarea" maxlength="500"
												placeholder="Enter Your Answer"></textarea>
										</div>
									</div>
									<div id="faqanwsertable">Answers</div>
								</div>
								<div class="col-md-12 trashanswerpart" style="display: none;">
									<div class="col-md-6 martop7 padleft0">
										<font class="lineheight30">Trashed Answers</font>
									</div>
									<div class="col-md-6 martop7 padright0 marbottom5">
										<a class="btn btn-primary trashbackqnbtn pull-right"
											data-popbox="pop1"><i class="fa fa-angle-double-left"></i>
											Answers</a> <a id='deleteansbtn'
											class="btn btn-primary pull-right marright7"> <i
											class="fa fa-times"></i></a>
									</div>
									<div id="pop1" class="popbox"></div>
									<div id="faqtrashanwsertable">Trash List</div>
								</div>
							</div>
							<div class="tab-pane" id="examInfo">
								<div id="examTab" style="margin: 10px;"></div>
							</div>
							<div class="tab-pane" id="quizinfo">
								<div id="quiztab" class="whitebg"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- New Section Modal Start-->

	<div class="modal fade" id="newsectionmodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Add New <%=section %> for 
						<span class="sectiontitlename"></span>
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-3 righttext">Section
								Title : </label>
							<div class="col-sm-6">
								<input type="text" name="coursesectiontitle"
									class="newsectiontitle form-control"
									placeholder="Enter Section title" maxlength="50">
								<div class="alert alert-danger pad10 martop7" id="sectionerr"
									style="display: none;">please enter Section Title</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3 righttext"> </label>
							<div class="col-sm-4">
								<a class="btn-flat btn btn-blue newsectionsavebtn white"
									courseid="">Save</a>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- New Section Modal End-->

	<!-- Edit Section Modal Start-->


	<div class="modal fade" id="editSectionModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Edit Section</h4>
				</div>
				<div class="modal-body">
					<div class="row-fluid form-horizontal">
						<div class="form-group">
							<label class="control-label col-sm-3 righttext">Section
								Title : </label>
							<div class="col-sm-8">
								<input type="text" class="titleforedit form-control"
									name="coursesectiontitle" placeholder="Section title"
									maxlength="50">
								<div class="alert alert-danger pad10 martop7"
									id="editsectionmsgshow2" style="display: none;">
									<p id="editsectionmsgshow2">Empty Section Name is not
										allowed</p>
								</div>
								<div class="alert alert-danger pad10 martop7"
									id="editsectionmsgshow" style="display: none;">
									<p id="editsectionmsgshow">Empty section Name is not
										allowed</p>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3"></label>
							<div class="col-sm-5">
								<a class="btn-flat btn btn-blue editsectionsavebtn white"
									courseid="">Update</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Edit Section Modal End-->

	<!-- New Lecture Modal Start-->

	<div class="modal fade" id="newlecturemodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20 overflowtext">
					Add New <%=lecture %> for <font class="lecturetitlename"></font>
					</h4>
				</div>
				<div class="modal-body">
					<form id="addNewLecture"
						action="<%=request.getContextPath()%>/rest/file/addNewLecture"
						enctype="multipart/form-data" name="leccontentdetail"
						method="post">
						<input type="hidden" class="hidcoursesectionid"
							name="hidcoursesectionid">
						<div class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-sm-4 righttext"><%=lecture %>
									Title : <font style="color: red; margin-right: 5px;">*</font>
								</label>
								<div class="col-sm-8">
									<input type="text" name="courselecturetitle" len="50"
										maxlength="50" class="newlecturetitle lecture form-control"
										placeholder="<%=lecture %> title" errorid="courselecturetitle1">
									<div class="alert alert-danger pad10 martop7"
										id="courselecturetitle1" style="display: none;"></div>
									<div class="alert alert-danger pad10 martop7" id="errormsgshow"
										style="display: none;">
										<p id="errormsgshow">Empty <%=lecture %> Name is not allowed</p>
									</div>
									<div class="alert alert-danger pad10 martop7"
										id="editerrormsgshow" style="display: none;">
										<p id="editerrormsgshow">Empty <%=lecture %> Name is not allowed</p>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-4"></label>
								<div class="col-sm-5">
									<input type="submit" id="savenewlecture" value="save"
										class="btn btn-blue white">
								</div>
							</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	</div>



	<div class="modal hide" id="nometadatalmodal">
		<form id="nometaLecture"
			action="<%=request.getContextPath()%>/rest/file/nometadata"
			enctype="multipart/form-data" name="nometadetail" method="post">

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">x</button>
				<h3>
					<span class="">Edit MetaInfo</span>
				</h3>
			</div>
			<div class="modal-body">
				<div class="row-fluid form-horizontal">
					<input type="hidden" class="hidcoursesectionid"
						name="hidcoursesectionid">
					<div class="control-group">
						<label class="control-label">Keywords : </label>
						<div class="controls">
							<input type="text" name="coursekeywords" len=500 maxlength="500"
								class="metainfocoursekeywords metainfo span10"
								placeholder="Keywords" errorid="metainfocoursekeywords">
							<div class="alert alert-error  error-mask error-mask2"
								id="metainfocoursekeywords" style="display: none;"></div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Web links : </label>
						<div class="controls">
							<input type="text" name="courseweblinks" len="492"
								maxlength="500" class="metainfocourseweblinks metainfo span10"
								placeholder="Web Links" errorid="metainfocourseweblinks">
							<div class="alert alert-error  error-mask error-mask2"
								id="metainfocourseweblinks" style="display: none;"></div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Summary : </label>
						<div class="controls">
							<textarea name="coursesummary" len="492" maxlength="500"
								class="metainfocoursesummary metainfo span10"
								placeholder="Summary" errorid="metainfocoursesummary"></textarea>
							<div class="alert alert-error  error-mask error-mask2"
								id="metainfocoursesummary" style="display: none;"></div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Box Item : </label>
						<div class="controls">
							<textarea name="courseboxiem" len="492" maxlength="500"
								class="metainfocourseboxiem metainfo span10"
								placeholder="Box Item" errorid="metainfocourseboxiem"></textarea>
							<div class="alert alert-error  error-mask error-mask2"
								id="metainfocourseboxiem" style="display: none;"></div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Project Ideas : </label>
						<div class="controls">
							<textarea name="courseprojidea" len="492" maxlength="500"
								class="metainfocourseprojidea metainfo span10"
								placeholder="Project Ideas" errorid="metainfocourseprojidea"></textarea>
							<div class="alert alert-error  error-mask error-mask2"
								id="metainfocourseprojidea" style="display: none;"></div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Activity : </label>
						<div class="controls">
							<textarea name="courseactivity" len="492" maxlength="500"
								class="metainfocourseactivity metainfo span10"
								placeholder="Activity" errorid="metainfocourseactivity"></textarea>
							<div class="alert alert-error  error-mask error-mask2"
								id="metainfocourseactivity" style="display: none;"></div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Reference : </label>
						<div class="controls">
							<input type="file" name="coursereference"
								class="coursereference span10">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Diagram : </label>
						<div class="controls">
							<input type="file" name="coursediagram" accept="image/*"
								class="coursediagram span10">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Table : </label>
						<div class="controls">
							<input type="file" name="coursetable" accept="image/*"
								class="coursetable span10">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Map : </label>
						<div class="controls">
							<input type="file" name="coursemap" accept="image/*"
								class="coursemap span10">
						</div>
					</div>


				</div>

			</div>
			<div class="modal-footer">
				<a class="btn-flat btn btn-blue anewlecturesavebtn"
					id="savenometainfo">Save</a> <input type="submit"
					id="savenometainfo" value="save"
					class="btn-flat btn btn-blue newlecturesavesubmitbtn"
					style="display: none">
			</div>
		</form>
	</div>


	<!-- Edit Lecture Modal Start-->
	<div class="modal fade" id="editlecturemodal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title size20">Edit <%=lecture %></h4>
				</div>
				<div class="modal-body">
					<div class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-4 righttext"><%=lecture %> Title : </label>
							<div class="col-sm-8">
								<input type="text" name="courselecturetitle" maxlength="50"
									class="editlecturetitle form-control"
									placeholder="Lecture title">
								<div class="alert alert-danger pad10 martop7" id="alertempty"
									style="display: none;">
									<P id="alertempty">Empty <%=lecture %> Name is not allowed.</P>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4"></label>
							<div class="col-sm-6">
								<a class="btn btn-blue editlecturesavebtn white"
									coursesectionid="">Update</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Edit Lecture Modal End-->

	<div class="modal fade" id="lecturemetainfomodal">
		<div class="modal-dialog width60per" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Edit <%=lecture %> Meta Information</h4>
				</div>
				<div class="modal-body" id="lecturemetainfoholder"></div>
			</div>
		</div>
	</div>

	<!-- New Content Modal Start-->

	<div class="modal fade preview-content-modal" id="addContentmodal" data-backdrop="static">
		<div class="modal-dialog width60per" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<span class="contenttitlename"> </span>
					</h4>
				</div>
				<div class="modal-body">
					<div class="row pad10" id="uploadtable"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- New Content Modal End-->

	<div class="modal fade" id="messagemodal">
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
					<p class="description mesdesc" style="padding: 10px;"></p>
				</div>
			</div>
		</div>
	</div>




	<!-- START WHITEBOARD MODAL -->
	<div class="modal modal-large fade" id="whiteboarModal"
		data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close close-btn" data-dismiss="modal"
						aria-hidden="true">X</button>
					<h4>Whiteboard</h4>
				</div>
				<div class="large-modal-body">
					<div class="literally one">
						<canvas id="original-canvas"></canvas>
					</div>
				</div>
				<!-- End More Info Template -->
				<div class="modal-footer">
					<span class="btn btn-flat btn-success pull-right"
						id="uploadDrawobject">Insert Drawed Object</span>
				</div>
			</div>
		</div>
	</div>

	<!-- Schedule CONTENT MODAL -->
	<div class="modal fade" id="scheduleModal">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">
						Live Session Schedule for <font class="enrolldate"></font>
					</h4>
				</div>
				<div class="modal-body" style="padding: 9px!important;">
					<form id="scheduleForm" class="form-horizontal" action=""
						method="post">
						<div class="form-group">
							<label class="col-sm-5 righttext" for="description">Description<font
								style="color: red; margin-left: 2px;">*</font> :
							</label>
							<div class="col-sm-6">
								<textarea rows="" cols=""
									class="sessiondescription form-control col-sm-12 liveerror"
									id="sessiondescription" maxlength="500"></textarea>
							</div>
							<div class="col-md-5"></div>
							<div
								class="alert alert-danger alert-dismissible col-md-6 martopbottom10 pad10 marleft15"
								id="sessiondescriptionerror" style="display: none;">Please
								enter schedule description</div>
						</div>
						<div class="form-group">
							<label class="righttext col-sm-5 control-label">Price <font
								style="color: red; margin-right: 5px;">*</font> :
							</label>
							<div class="col-sm-7">
								<div class="col-md-6 pad0">
									<div class="btn-group lefttext">
										<button type="button"
											class="btn btn-default bgclass price active">Price</button>
										<button type="button" class="btn btn-default bgclass free">Free</button>
									</div>
								</div>
								<div class="col-md-4 pad0">
									<input type="text" class="form-control col-md-10 liveerror"
										id="schedulepriceid" maxlength="7">
								</div>
							</div>
							<div class="col-md-5"></div>
							<div
								class="alert alert-danger alert-dismissible col-md-6 marleft15 martopbottom10 pad10"
								id="schedulepriceiderror" style="display: none;">Please
								enter price</div>
							<div
								class="alert alert-danger alert-dismissible col-md-6 marleft15 martopbottom10 pad10"
								id="numbererror" style="display: none;">Digits Only</div>
						</div>
						<div class="form-group">
							<label class="righttext col-sm-5 control-label">From <font
								style="color: red; margin-right: 5px;">*</font> :
							</label>
							<div class="col-sm-6">
								<div class='input-group date' id='datetimepicker2'>
									<input type="text" class="form-control starttime liveerror"
										data-format=" hh:mm" type="text" id="starttime"
										pattern="[0-9:\s]+"><span class="input-group-addon"
										id="basic-addon2"><i class="fa fa-calendar"></i> </span>
								</div>
							</div>
							<div class="col-md-5"></div>
							<div
								class="alert alert-danger alert-dismissible col-md-6 marleft15 martopbottom10 pad10"
								id="starttimeerror" style="display: none;">Please select a
								time</div>
						</div>
						<div class="form-group">
							<label class="righttext col-sm-5 control-label">To <font
								style="color: red; margin-right: 5px;">*</font> :
							</label>
							<div class="col-sm-6">
								<div class='input-group date' id='datetimepicker1'>
									<input data-format=" hh:mm" type="text"
										class="endtime form-control date liveerror" id="endtime"
										pattern="[0-9:\s]+"> <span class="input-group-addon"><i
										class="fa fa-calendar"></i> </span>
								</div>
							</div>
							<div class="col-md-5"></div>
							<div
								class="alert alert-danger alert-dismissible col-md-6 marleft15 martopbottom10 pad10"
								id="endtimeerror" style="display: none;">Please select a
								time</div>
						</div>
						<input type="hidden" name="splitdate" id="splitdate" value="">
						<input type="hidden" name="selectdate" id="selected" value="">
						<input type="hidden" name="scheduleprice" id="scheduleprice"
							value="">
						<s:hidden name="courseid" value="%{courseid}"></s:hidden>
						<div class="form-group">
							<label class="righttext col-sm-3 control-label"> </label>
							<div class="col-sm-4">
								<a class="btn btn-blue schedulesave white" id="submitButton">Save</a>
								<a class="btn btn-blue scheduleupdate white" id="submitButton">Update</a>
								<a class="btn btn-danger btn-close " id="cancel">Cancel</a>
							</div>
						</div>
						<div class="alert alert-success col-md-12 martopbottom10 pad10"
							id="alertsuccess" style="display: none"></div>
						<div class="martop30">
							<h4>Scheduled Time</h4>
							<div id="timetable"></div>
						</div>
					</form>


				</div>
			</div>
		</div>
	</div>

	<!-- PREVIEW CONTENT MODAL -->
	<div class="modal fade" id="preview-content-modal" data-backdrop="static">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" id="preview-modal-header"></div>
				<div class="modal-body" style="min-height: 480PX;">
					<div class="row-fluid" id="preview-content-space"></div>
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
					<h3 class="modal-title">Message</h3>
				</div>
				<div class="modal-body">
					<p style="color: #F00" class="alertmessageval">
						Your current plan does not support publishing more than <span
							class="maxcourse"></span> course. Please Upgrade your plan
					</p>
				</div>
				<div class="modal-footer">
					<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="smallModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Question Papers</h4>
				</div>
				<div class="modal-body">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>Question Paper Title</th>
								<th>Created Date</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody id="questionPaperTable">
							<tr>
								<td colspan="3">Question paper not found</td>
							</tr>
						</tbody>

					</table>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>

	<script src="<%=request.getContextPath()%>/assets/lib/js/moment.min.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/plugins/datepicker/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/formjs/jquery.form.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/flowplayer/iGplayer.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/course/managecourse.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/fullcalendar/js/fullcalendar.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/sat/templates.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/sat/exampaper.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/sat/controller.js"></script>
	<script src="<%=request.getContextPath()%>/assets/app/js/sat/sat.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/whiteboard/js/react-0.10.0.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/whiteboard/js/literallycanvas.js"></script>
	<!-- End -->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/plugins/summernote/summernote.min.js"></script>
	<!-- End -->
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/course/lecturemetainfo.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/app/js/course/managecontentevents.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/assets/lib/js/jquery.ui.js"></script>
	<!-- Javascript Dependencies End-->
	<s:hidden name="courseid" value="%{courseid}"></s:hidden>
	<input type="hidden" name="personid" id="orgpersonid"
		value="<%=personid%>">
</body>
</html>