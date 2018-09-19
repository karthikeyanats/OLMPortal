/**
 * TEMPLATES OF SELF ASSESSMENT TEST
 * sat.js
 * @Copyright 2014-2015 iGrandee pvt.ltd.
 * @author selvarajan_j
 * @contact selvarajan_j@igrandee.com,jjjackpterson@gmail.com
 * 
 * Wed Feb 18 2015 5:15 
 */

/**
 * RENDERER TEMPLATES CORRESPONDING ELEMENTS 
 */
var Renderer=function(){
	
	/**
	 * SELECT TYPE TEMPLATES 
	 * @returns TEMPLATE STRING 
	 */
	var selectTemplate = function() {
		return '<center>'
				+ '<ul class="breadcrumb">'
				+
				// TOGGLE VISIBLE LABEL
				'<li class="toggle-elm">'
				+ '<b class="marright5">Question Types : </b><b style="display:none;" class="toggle-elm">Question Entry Form</b>'
				+ '</li>'
				+
				// TOGGLE HIDDEN LABEL
				'<li style="display:none;" class="toggle-elm">'
				+ '<b>Select Question Entry Form</b>'
				+ '</li>'
				+

				'<li><select class="" >{{#each .}}<option value="{{id}}">{{questiontype}}</option>{{/each}}</select></li>'
				+
				// TOGGLE HIDDEN BUTTON
				'<li class="toggle-elm" style="display:none;">'
				+ '<a class="btn btn-flat btn-success tgl"><i class="icon-chevron-left">&nbsp;Back To Questions</i></a>'
				+ '</li>'
				+
				// TOGGLE VISIBILE BUTTON
				'<li class="toggle-elm">'
				+ '<a  class="btn btn-flat btn-success" id="loadqtBtn"><i class="fa fa-chevron-left">&nbsp;Load Question</i></a>'
				+ '</li>'
				+ '<li style="margin-left: 10px;" class="toggle-elm">'
				+ '<a  class="btn btn-flat btn-primary tgl"><i class="icon-question-sign">&nbsp;New Question</i></a>'
				+ '</li>'
				+ '</ul>'
				+ '</center>'
				+ '<div>'
				+ '<h5 class="toggle-elm borderbottom1 padbottom10 padleft10"><span class="questionHeader">True/False </span> Types Question(s)<small id="totalQst" style="margin-left: 6px;">Total no.of question(s): 0</small></h5><div>'
				+ '<h5 class="toggle-elm borderbottom1 padbottom10 padleft10" style="display:none;"><span class="questionHeader">True/False</span> Type Question Entry Form</h5><div>'
				+ '<div id="questionDive"></div>';
	};
	/**
	 * EMPTY QUESTION TEMPLATE
	 * 
	 * @returns TEMPLATE STRING
	 */
	var emptyQstTemplate = function() {
		return '<div style="text-align:center;">'
				+ '<div style="height:350px;">'
				+ '<div style="padding-top: 70px;">'
				+ '<i class="fa fa-question-sign" style="font-size: 60pt;color: gray;"></i>'
				+ '<h2 style="color: gray;padding-bottom: 10px;"> Questions Not Available </h2>'
				+
				/*
				 * '<a class="btn btn-flat btn-info"><span class="btn-text">
				 * Create New Questions </span></a>'+
				 */
				'</div>' + '</div>' + '</div>';
	};
	/**
	 * TRUE OR FALSE ,OBJECTIVE ,FILL IN THE BLANK and DESCRIPTIVE TYPE
	 * TEMPLATES
	 * 
	 * @returns TEMPLATE STRING
	 */
	var tfobjtTemplate = function() {
		return '<ol class="scrollofquestion padleft10 pad0">' + '{{#each .}}' + '<div class="col-md-12">'
				+ '<li><p>{{{question-helper [0].question}}}</p></li>' + '<ol>'
				+ '{{#each .}}'
				+ '<li style="{{answer}}">{{{question-helper answers}}}</li>'
				+ '{{/each}}' + '</ol>' + '</div>' + addBtn() + ''
				+ '{{/each}}' + '</ol>';

	};
	/**
	 * MATCH THE FOLLOWING TYPE TEMPLATE
	 * 
	 * @return TEMPLATE STRING
	 */
	var mtfTemplate = function() {
		return '<ol>{{#each .}}'
				+'<div class="col-md-12">'
				+ '<li><h5> Match The Following</h5>'
				+ '<div class="col-md-6">'
				+ '<h6><u>{{index}}Question</u></h6>'
				+ '</div>'
				+ '<div class="col-md-6 padright0">'
				+ '<h6><u>Answer</u></h6>'
				+ '</div>'
				+ ''
				+ '<div class="col-md-12 pad0">'
				+ '<ol id="questiondiv">'
				+ '{{{match-question [0].question}}}' + '</ol>' + '</div>'
				+ '' + addBtn() + '</li></div>{{/each}}</ol>';
	};
	/**
	 * MATCH THE FOLLOWING EDIT TEMPLAE
	 * 
	 * @returns STRING TEMPLATE
	 */
	var edtMfTemplate = function() {
		return '<ul class="pad0">{{#each .}}<li style="list-style: inherit!important;"><div class="row">' + '{{{edit-match-question [0].question}}}'
				+ '</div></li>{{/each}}</ul>';

	};
	

		/**
		 * UPLOAD QUESTION SHEET TABLE TEMPLATE
		 */
	var uptblTemplate = function() {
		return '{{#each .}}'
				+ '<tr>'
				+ '<td style="text-align: center;"><label><input type="checkbox" class="chk-self" status="false"></label></td>'
				+ '<td>{{question}}</td>' + '<td><B>{{crtanswer}}</B></td>'
				+ '<td>' + '<ol type="A">' + '{{#each option}}'
				+ '<li>{{.}}</li>' + '{{/each}}' + '</ol>	' + '</td>' + '</tr>'
				+ '{{/each}}';
	};
 /**
	 * EXAM CONFIG TEMPLATE
	 * 
	 * @return TEMPLATE STRING
	 */
	var examConfigTemplate = function() {
		return '{{#each .}}'
				+ '{{#ifEquals [0].id}}'
				+ '<tr><td colspan="3"> <span class="label label-important pull-right">Mark : {{[0].mark}}</span>'
				+ '<h5><input type="checkbox" value="{{[0].questionid}}" style="height: 20px;width: 20px;margin-top: 0px;" >Match The Following</h5>'
				+ '<div class="col-md-12">'
				+ '<div class="col-md-6">'
				+ '<h6><u>Question</u></h6>'
				+ '</div>'
				+ '<div class="col-md-6">'
				+ '<h6><u>Answer</u></h6>'
				+ '</div>'
				+ '</div>'
				+ '<div class="col-md-12">'
				+ '<ol style="margin-left: 0px;" class="col-md-12" id="questiondiv">'
				+ '{{{match-question [0].question}}}'
				+ '</ol>'
				+ '</div>'
				+

				'</td><tr>'
				+ '{{else}}'
				+ '<tr>'
				+ '<td><center><input type="checkbox" value="{{[0].questionid}}" style="height: 20px;width: 20px;margin-top: 0px;"><span class="label label-important"> Mark : {{[0].mark}}</span></center></td>'
				+ '<td>{{{question-helper [0].question}}}</td>' + '<td>'
				+ '<ol type="A" >' + '{{#each .}}'
				+ '<li>{{{question-helper answers}}}</li>' + '{{/each}}'
				+ '</ol>' + '</td>' + '</tr>' +
				'{{/ifEquals}}' + '{{/each}}';

	};
	/**
	 * GENERATE QUESTION FOR EXAM
	 * 
	 * @param STRING
	 *            AS TEMPLATE
	 */
	genQstTemplate = function() {
		return '{{#each .}}'
				+ '{{#ifEquals [0].id}}'
				+ '<li questionid="{{[0].questionid}}" >'
				+ '<span class="btn btn-flat btn-danger pull-right qdeleteAng" sectionid="{{[0].sectionid}}" title="Delete Question"><i class="glyphicon glyphicon-trash" > </i></span>'
				+ '<h5>Match The Following</h5>'
				+ '<div class="col-md-12">'
				+ '<div class="col-md-6">'
				+ '<h6><u>Question</u></h6>'
				+ '</div>'
				+ '<div class="col-md-6">'
				+ '<h6><u>Answer</u></h6>'
				+ '</div>'
				+ '</div>'
				+ '<div class="col-md-12">'
				+ '<ol style="margin-left: 0px;" class="col-md-12" id="questiondiv">'
				+ '{{{match-question [0].question}}}'
				+ '</ol>'
				+ '</div>'
				+

				'</li>'
				+ '{{else}}'
				+ '<li questionid="{{[0].questionid}}"  sectionid="{{[0].sectionid}}">'
				+ '<span class="btn btn-flat btn-danger pull-right qdeleteAng" sectionid="{{[0].sectionid}}" title="Delete Question"><i class="glyphicon glyphicon-trash"> </i></span>'
				+ '<p>{{{question-helper [0].question}}}</p>'
				+ '<div class="col-md-12">'
				+ '<ol style="margin-left: 0px;">'
				+ '{{#each .}}'
				+ '<li style="display: inline-block;margin-left:0px;margin-right: 10px;" class="col-md-6">{{slno}} {{{question-helper answers}}}</li>'
				+ '{{/each}}' + '</ol>' + '</div>' +

				'</li>' + '{{/ifEquals}}' + '{{/each}}';
	};
 /**
	 * GENERATE QUESTION OR TYPES TEMPLATE
	 * 
	 * @return STRING AS TEMPLATE
	 */
	orTypeTemplate = function() {
		return '<li>'
				+ '<ol type="A" class="ortype">{{#each .}}'
				+ '<span class="btn btn-flat btn-danger pull-right qdeleteAng" sectionid="{{[0].sectionid}}" title="Delete Question"><i class="glyphicon glyphicon-trash" > </i></span>'
				+ '<li  questionid={{[0].questionid}}>'
				+ '{{#ifEquals [0].id}}'

				
				+ '<h5>Match The Following</h5>'
				+ '<div class="col-md-12">'
				+ '<div class="col-md-6">'
				+ '<h6><u>Question</u></h6>'
				+ '</div>'
				+ '<div class="col-md-6">'
				+ '<h6><u>Answer</u></h6>'
				+ '</div>'
				+ '</div>'
				+ '<div class="col-md-12">'
				+ '<ol style="margin-left: 0px;" class="col-md-12" id="questiondiv">'
				+ '{{{match-question [0].question}}}'
				+ '</ol>'
				+ '</div>'
				+ '{{else}}'

				
				+ '<p>sadasda{{{question-helper [0].question}}}</p>'
				+ '<div class="col-md-12">'
				+ '<ol style="margin-left: 0px;">'
				+ '{{#each .}}'
				+ '<li style="display: inline-block;margin-left:0px;margin-right: 10px;" class="col-md-6">{{slno}} {{{question-helper answers}}}</li>'
				+ '{{/each}}' + '</ol>' + '</div>'
				+ '{{/ifEquals}}</li>'
				+ '{{{or-operation}}}'	
				+ '{{/each}} </ol></li>';
	};
	
	orTypeUPTemplate = function() {
		return '<li>'
				+'<ol type="A" class="ortype">{{#each .}}'
				+ '<span class="btn btn-flat btn-danger pull-right qdeleteAng" sectionid="{{sectionid}}" title="Delete Question"><i class="glyphicon glyphicon-trash"> </i></span>'
				+ '<li  questionid={{questionid}}>'
								
				+ '<p>{{{question-helper question}}}</p>'
				
				+ '</li>'
				
				+'<center style="margin-bottom: 10px;margin-top: 10px;margin-left: -50px;"><strong>( or )</strong></center>'
				+ '{{#ortype }}'
					+ '<li ><p>{{{question-helper question}}}</p></li>'
				+ '{{/ortype}}'
				+ '{{/each}} </ol></li>';
	};
 
	 
// ENTRY FORM START	
	/**
	 * TRUE / FALSE TYPE TEMPLATE
	 * 
	 * @returns TEMPLATE STRING
	 */
	var tftEntryForm = function() {
		return '<h5 class="mar0">Enter Question<span style="color: red;"> *</span></h5>'
				+ '<div class="summernote-container"></div>'
				+ '<div class="col-md-12">'
				+ '<b>Answer</b><span style="color: red;"> *</span> '
				+ '</div>'
				+ '<div class="col-md-12">'
				+ '<div class="input-append radioBtn">'
				+ '<a class="btn btn-flat btn-blue" data-toggle="tf" data-title="1" >True</a>'
				+ '<a class="btn btn-flat" data-toggle="tf" data-title="0" >False</a>'
				+ '<input type="hidden" name="tf" id="tf" value="1">'
				+ '</div>'
				+ '</div>'
				+ '<div class="col-md-12">'
				+ '<h5><a  id="openDescr"><u>Enter Answer Description</u> </a></h5>'
				+ '<div class="description" ></div><br>' + '</div>'
				+ '</div><CENTER>' + addSaveBtn() + '</CENTER><hr>';
	};
	/**
	 * OBJECTIVE TYPE TEMPLATE
	 * 
	 * @returns TEMPLATE STRING
	 */
	var objtEntryForm = function() {
		return '<center>'
				+ '<a  class="btn btn-flat btn-danger upload-qst">'
				+ '<i class="fa fa-plus">&nbsp;Upload Question Sheet</i>'
				+ '</a>'
				+ '</center>'
				+ '<div class="row centertext">'
				+ '<center>'
				+ '<div class="col-md-5">'
				+ '<hr>'
				+ '</div>'
				+ '<div class="col-md-1">'
				+ '<label class="martop7">or</label>'
				+ '</div>'
				+ '<div class="col-md-6">'
				+ '<hr>'
				+ '</div>'
				+ '</center>'
				+ '</div>'
				+

				'<div class="row centertext marbottom10">'
				+ '<b>Enter Question</b><span style="color: red;"> *</span>'
				+ '</div>'
				+ '<div class="summernote-container"></div>'
				+ '<ol class="ol-list">'
				+ '</ol>'
				+ '<center>'
				+ '<a  class="btn btn-flat btn-primary" id="moreoption">'
				+ '<i class="icon-plus">&nbsp;Add More Options</i>'
				+ '</a>'
				+ '</center>'
				+ '<div class="col-md-12">'
				+ '<h5><a  id="openDescr"><u>Enter Answer Description</u> </a></h5>'
				+ '<div class="description" ></div><br>' + '</div>'
				+ '<CENTER>' + addSaveBtn() + '</CENTER><hr>';
	};
	

	/**
	 * FILL IN THE BLANK TYPE TEMPLATE
	 * 
	 * @param TEMPLATE
	 *            STRING
	 */
	var fitbtEntryForm = function() {
		return '<div>'
				+ '<center>'
				+ '<div>'
				+ '<textarea style="height: 50px;margin: 0px;width: 461px;" id="area" placeholder="Enter question..." ></textarea>'
				+ '<br>'
				+ '<br>'
				+

				'</div>'
				+ '<center class="showmessage col-md-12"></center>'
				+ '<button type="submit" class="btn btn-primary add-item" style="margin-right: 5px">Add</button>'
				+ '<button type="submit" class="btn btn-success add-input">Add Blank</button>'
				+

				'</center>'
				+ '<br>'
				+ '<div id="dyncamicItem">'
				+ '</div>'
				+ '<div class="col-md-12">'
				+ '<a  class="btn btn-flat btn-success choice-btn" style="padding-bottom: 2px; padding-top: 1px; margin-top: 3px;">'
				+ '<i class="fa fa-plus">&nbsp;Add Choice</i>'
				+ '</a>'
				+ '<div>'
				+ '<br>'
				+ '<div id="choice">'
				+ '</div>'
				+ '</div>'
				+ '<div class="col-md-12">'
				+ '<h5><a  id="openDescr"><u>Enter Answer Description</u> </a></h5>'
				+ '<div class="description" ></div>' + '</div>'
				+ '</div><br>	<CENTER>' + addSaveBtn() + '</CENTER><hr>';
	};
 /**
	 * MATCH THE FOLLOWING TYPE TEMPLATE
	 * 
	 * @param TEMPLATE
	 *            STRING
	 */
	var mtftEntryForm = function() {
		return '<div class="col-md-12">'
				+ '<div class="col-md-6 pad0">'
				+ '<h5>Question</h5>'
				+ '<div class="summernote-container" id="qstsummer"></div>'
				+ '</div>'
				+ '<div class="col-md-6 padright0">'
				+ '<h5>Answer</h5>'
				+ '<div class="summernote-container" id="anssummer"></div>'
				+ '</div>'
				+ '</div><br>'
				//+ '<center class="showmessage col-md-12"></center>'
				+ '<div class="col-md-12 martop7">'
				+ '<center>'
				+ '<a class="btn btn-flat btn-primary marright5" id="addpreview">'
				+ 'Add Preview'
				+ '</a>'
				+'<span class="btn btn-flat btn-danger cancelbtn" onclick="sat.cancelQuestion()">Cancel</span>'
				+ '</center>'
				+ '</div>'
				+ '<h5>Question Preview</h5>'
				+ '<div class="col-md-12">'
				+ '<div class="col-md-6">'
				+ '<h6><u>Question</u></h6>'
				+ '</div>'
				+ '<div class="col-md-6">'
				+ '<h6><u>Answer</u></h6>'
				+ '</div>'
				+ '</div>'
				+

				'<div class="col-md-12">'
				+ '<ol style="margin-left: 0px;" class="col-md-12" id="questiondiv">'
				+ '</ol>'
				+ '</div>'
				+

				'<div class="col-md-12">'
				+ '<h5><a  id="openDescr"><u>Enter Answer Description</u> </a></h5>'
				+ '<div class="description marbottom10" ></div>' + '</div>'
				+ '<br><CENTER>' + addSaveBtn() + '</CENTER><hr>';
	};
 /**
	 * DESCRIPTIVE TYPE TEMPLATE
	 * 
	 * @return STRING TEMPLATE
	 */
	var destEntryForm = function() {
		return '<div class="col-md-12">'
				+'<div class="col-md-12 form-horizontal">'
				+'<div class="form-group">'
				+'<label class="col-sm-2 control-label righttext">Mark : </label>'
				+'<div class="col-sm-1 pad0">'
				+'<input type="text" class="form-control" value="5" id="mark" maxlength="2">'
				+'</div>'
				+'</div>'
				+ '</div></div>'
				+ '<div class="row">'
				+'<div class="col-md-12">'
				+'<div class="col-md-12">'
				+ '<h5 class="marleft7">Passage With Question<span style="color: red;"> *</span></h5>'
				+ '</div></div></div>'
				+ '<div class="summernote-container"></div>'
				+ '<div class="col-md-12"> '
				+ '<b>Answer</b><span style="color: red;"> *</span>'
				+ '</div>'
				+ '<div class="answerdiv" ></div>'
				+ '<div class="col-md-12"> '
				+ '<h5><a href="#" id="openDescr"><u>Enter Answer Description</u> </a></h5>'
				+ '<div class="description" ></div>' + '</div>'
				+ '<br><CENTER>' + addSaveBtn() + '</CENTER><hr>';
	};
 /**
	 * UPLOAD QUESTION SHEET TEMPLATE
	 * 
	 * @return STRING AS TEMPLATE
	 */
	var uploadForm = function() {
		return '<div class="control-group">'
				+ '<label class="control-label">Attachment </label>'
				+ '<div class="controls">'
				+ '<input type="file" name="file" class="qst-attach span3" id="qst-attach-id" accept=".xls, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">'
				+ '<button type="button" class="btn btn-flat btn-success uploadxls" style="padding-top: 2px;padding-bottom: 2px;margin-right: 5px;">Upload</button>'
				+ '<small style="color: chocolate;">Acceptable only xls,xlsx</small>'
				+ '<a class="btn btn-flat" id="dwnld"  title="Download format file">'
				+ '<i class="fa fa-download"></i>'
				+ '</a>'
				+ '<span class="upload-status pull-right" style="color: bcol-md-12n;padding-top: 10px;padding-right: 33px;display:none;">Please wait uploading...</span>'
				+ '<progress id="progressBar" class="upload-status" max="100" value="0" style="height: 4px;width: 96%;vertical-align: text-top;display:none;"></progress>'
				+ '</div>'
				+ '</div>'
				+ '<table class="table table-bordered">'
				+ '<thead>'
				+ '<tr>'
				+ '<th style="text-align: center;"><label><input type="checkbox" class="chk-all"></label></th>'
				+ '<th>Questions</th>'
				+ '<th>Correct Answer</th>'
				+ '<th width="270px">Options</th>'
				+ '</tr>'
				+ '</thead>'
				+ '<tbody id="qstSheetTabl">'
				+ '<tr>'
				+ '<td colspan="6"><center><b style="color: darkgray;">Question not found</b></center></td>'
				+ '</tr>'
				+ '</tbody>'
				+ '</table>'
				+ '<center><span class="btn btn-flat btn-success" id="savequestion" onclick="sat.saveQuestions()" style="margin-right: 3px;">Save</span><span class="btn btn-flat btn-info back-btn">Back</span></center>'
				+ '<br><CENTER>' + addSaveBtn() + '</CENTER><hr>';
	};

	 
 /**
	 * ADD EXAM TEMPLATE
	 * 
	 * @return STRING AS TEMPLATE
	 */
	examForm = function() {
		return '<div class="col-md-12">' 
				+ '<h4><i class="icon-list-alt" style="font-size: 16pt;"></i>  Preview Question Paper'
				+ '</h4>'
				+ '</div>'
				+ '<hr style="margin: 5px 0;">'
				
				+ '<ul class="breadcrumb">'
				 
				+  '<center><b>Question Title : </b>'
				+  '<input type="text" id="titleinput" required="required" maxlength="50" style="margin-bottom: 0px;margin-right:10px">'
				+  '<button class="btn btn-danger" type="button" data-toggle="modal" style="margin-right: 5px;" data-target="#smallModal" onClick="expaper.loadQuestionPaper()">Open Question Paper</button>'
				//+  '<li class="pull-right"><b>Question Title : </b></li>'
				/*+  '<span class="pull-right">'
				+  '<b>QUESTION PAPERS </b>'
				+  '<select id="questionpaper"></select><button class="btn btn-success">Load</button></span>'*/
				+  '</center></ul>'
				
				+ '<div class="col-md-12">'
				+ '<center id="id-8642"><h4 style="color:gainsboro">No Preview Data</h4></center>'
				+ '<ul id="preview" class="thumbnails" style="padding-left: 0px;"></ul>'
				+ '</div>'
				
				+ '<div class="breadcrumb"><center>'
				+ '<div class="col-md-12" style="display:none"> <label class="checkbox span3"><input type="checkbox"  id="uporchk"> Make <strong>or</strong> type question</label></div>'
				+ '<button class="btn btn-success" id="generatBtn" type="button" style="margin-right: 5px;" onClick="expaper.savePaperData()">Generate Question Paper</button>'
				+ '<button class="btn btn-info"  type="button" style="margin-right: 5px;">Generate Answer Key</button>'
				+ '<button class="btn btn-danger"  type="button" style="margin-right: 5px;" onclick="expaper.clearAll()">Cancel</button>'
				+ '<button class="btn btn-primary"  type="button" onclick="expaper.exportPDF()">Export PDF</button>'
				+ '</center></div>'
				
				+ '<hr>'
				+ '<div class="col-md-12"> <h4><i class="icon-wrench" style="font-size: 16pt;"></i>  Question Configuration</h4></div>'	
				+ '<div id="entry" class="thumbnails"></div>'
				
				+ '<div class="col-md-12">'
				+ '<center><span class="btn btn-flat btn-success" id="questBtn"  style="margin-right: 6px;">Config Questions</span></center>'
				+ '</div><br>'
				+ '<div class="col-md-12">'
				+ '<div class="summernote-container"></div>'
				+ '</div> <br>'
				+

				'<div class="col-md-12">'
				+ '<center>'
				+ '<span class="btn btn-primary" id="previewBtn">Add Preview</span>'
				+ '<span class="btn btn-danger" id="cancelBtn" >Clear</span>'
				+ '</center>' + '</div>' + '<hr>';
	};
	/**
	 * ADD QUESTION CONFIGURATION TEMPLATE
	 * 
	 * @return STRING AS TEMPLATE
	 */
	qstEntryForm = function() {
		return '<center>'  
				 +'<ul class="breadcrumb">'
				 
				 +'<li style="margin-right: 10px;">'
				 +'<b>Lecture</b>'
				 +'</li>'
				 
				 +'<li style="margin-right: 15px;">'
				 +'<select id="select-chap" name="questiontype" style="margin-bottom: 0px;">'
				 +'<option value="0">SELECT TYPES</option>{{#each .}}<option value="{{id}}">{{questiontype}}</option>{{/each}}'
				 +'</select>'
				 +'</li>'
				 
				 +'<li style="margin-right: 10px;">'
				 +'<b>Types</b>'
				 +'</li>'
				 
				 +'<li style="margin-right: 15px;">'
				 +'<select class="select-item" name="questiontype" style="margin-bottom: 0px;">'
				 +'<option value="0">SELECT TYPES</option>{{#each .}}<option value="{{id}}">{{questiontype}}</option>{{/each}}'
				 +'</select>'
				 +'</li>'
				
				 +'<li id="marksDiv" style="display:none; margin-right: 15px;">'
				 +'<b style="margin-right: 10px;">Marks</b>'
				 +'<select id="qtype" style="margin-bottom: 0px;width: 60px;" >'
				 +'<option  value="0">ALL</option>'
				 +'<option value="1">1</option>'
				 +'<option  value="2">2</option>'
				 +'<option value="4">4</option>'
				 +'<option  value="5">5</option>'
				 +'<option value="8">8</option>'
				 +'<option  value="10">10</option>'
				 +'<option value="15">15</option>'
				 +'<option  value="20">20</option>'
				 +'</select>'
				 +'</li>'
				 
				 +'<li>'
				 +'<span class="btn btn-flat btn-primary" id="loadQstBtn" style="width: 100px;margin-right: 6px;">Load</span>'
				 +'</li>'
				 
				 +'</ul>'
				 +'</center>'

				/*
				 * '<div id="entryFormDiv" class="col-md-12">' + '<div
				 * class="control-group span4">' + '<b>Question Type<font
				 * style="color: red; margin-right: 5px;">*</font>' + '</b>' + '<div
				 * class="controls">' + '<select class="select-item"
				 * name="questiontype"><option value="0">SELECT TYPES</option>{{#each
				 * .}}<option value="{{id}}">{{questiontype}}</option>{{/each}}</select>' + '</div>' + '</div>' + '<div
				 * class="control-group span4" id="marksDiv"
				 * style="display:none;">' + '<b>Marks</b>' + '<div
				 * class="controls">' + '<select id="qtype" >' + '<option
				 * value="0">ALL</option>' + '<option value="1">1</option>' + '<option
				 * value="2">2</option>' + '<option value="4">4</option>' + '<option
				 * value="5">5</option>' + '<option value="8">8</option>' + '<option
				 * value="10">10</option>' + '<option value="15">15</option>' + '<option
				 * value="20">20</option>' + '</select>' + '</div>' + '</div>' + '<div
				 * class="control-group >' + '<div class="controls">' + '<span
				 * class="btn btn-flat btn-primary" id="loadQstBtn"
				 * style="width: 100px;margin-right: 6px;margin-top: 15px;">Load</span>' + '</div>' + '</div>'
				 */
				+'<h5 id="lbl"></h5>'
				+ '<div>'
				+ '<table class="table table-bordered">'
				+ '<thead>'
				+ '<tr>'
				+ '<th>Check</th>'
				+ '<th>Question</th>'
				+ '<th style="width: 300px;">Answer</th>'
				+ '</tr>'
				+ '</thead>'
				+ '<tbody id="qstDiv">'
				+ '</tbody>'
				+ '</table>'
				+ '<div class="col-md-12"> <label class="checkbox span3"><input type="checkbox"  id="orchk"> Make <strong>or</strong> type question</label>'
				+ '<span class="pull-right" style="color: crimson;"> <strong>Note: </strong> If you make <strong>or</strong> type question you must select two question then click checkbox </span>'
				+ '</div>'
				+ '<center>'
				+ '<span class="btn btn-flat btn-info" id="addQustionsBtn" style="width: 100px;margin-right: 6px;padding: 2px;">Add Preview</span>'
				+ '<span class="btn btn-flat btn-danger" id="closeBtn" style="width: 100px;margin-right: 6px;padding: 2px;">Close Me</span>'
				+ '</center>' + '</div>' + '</div><hr>';

	};
	/**
	 * ADD DELETE AND EDIT BUTTONS
	 * 
	 * @returns TEMPLATE STRING
	 */
	addBtn = function() {
		return '<div class="col-md-12 borderbottom1 padbottom10">'
				+ '<span id="{{[0].questionid}}">'
				+ '</span>'
				+ '<a class="btn btn-danger pull-right" questionid="{{[0].questionid}}"  title="Delete Question" onClick="sat.deleteQuestion({{[0].questionid}})">'
				+ '<i class="glyphicon glyphicon-trash"></i>'
				+ '</a>'
				+ '<a class="btn btn-primary pull-right edit-Item marright5" questionid="{{[0].questionid}}" title="Edit Question" >'
				+ '<i class="fa fa-pencil "></i>' + '</a>' + '</div>';
	};
	

	/**
	 * SAVE AND CANCEL BUTTON ACTION
	 */
	addSaveBtn = function() {
		return '<center class="showmessage col-md-12"></center>'
				+ '<div class="questionentry">'
				+ '<span class="btn btn-flat btn-primary"  style="width: 100px;margin-right: 6px;" onClick="sat.backToQuestion()">Back</span>'
				+ '<span class="btn btn-flat btn-success" id="saveBtn"  style="width: 100px;margin-right: 6px;" onClick="sat.saveQuestions()">Save</span>'
				+ '<span class="btn btn-flat btn-danger" style="width: 100px;"onClick="sat.cancelQuestion()">Cancel</span>'
				+ '</div>';
	};
	

	/**
	 * GLOBALLY RETURENS
	 */
	return {
		selectTemplate : selectTemplate,
		emptyQstTemplate : emptyQstTemplate,
		tfobjtTemplate : tfobjtTemplate,
		mtfTemplate : mtfTemplate,
		uptblTemplate : uptblTemplate,
		examConfigTemplate : examConfigTemplate,
		genQstTemplate : genQstTemplate,
		orTypeTemplate : orTypeTemplate,
		orTypeUPTemplate:orTypeUPTemplate,
		tftEntryForm : tftEntryForm,
		objtEntryForm : objtEntryForm,
		fitbtEntryForm : fitbtEntryForm,
		mtftEntryForm : mtftEntryForm,
		destEntryForm : destEntryForm,
		edtMfTemplate : edtMfTemplate,
		uploadForm : uploadForm,
		examForm : examForm,
		qstEntryForm : qstEntryForm,

		success : function(message) {
			return '<div class="alert alert-success alert-fixed-top" role="alert" ><button type="button" class="close" data-dismiss="alert"><div aria-hidden="true">&times;</div></button><strong>Success !</strong> "'
					+ message + '".</div>';
		},
		fail : function(message) {
			return '<div class="alert alert-danger alert-fixed-top" role="alert" ><button type="button" class="close" data-dismiss="alert"><div aria-hidden="true">&times;</div></button><strong>Failed !</strong> "'
					+ message + '".</div>';
		}
	};
};


var templates=function(){
	/**
	 * Create Instance of Renderer 
	 */
	var renderer=new Renderer();
	var element="#quiztab";
	var qstElement="#questionDive";
	
 // START QUESTION VIEW 
	 /**
	  * MENUS RENDERER 
	  * @param JSONData
	  */
	 var selectTemplate=function(data){
		 compileTemplate(renderer.selectTemplate(),data,element);
	 };
	 
	 /**
	  * EMPTY QUESTION RENTERER
	  */
	 emptyQstRenderer=function(){
		 compileTemplate(renderer.tfTemplate(),data,qstElement);
	 };
	 /**
	  * TRUE OR FALSE TYPE QUESTIONS 
	  * @param JSONData
	  */
	 tfobjtTemplate=function(data){
		 compileTemplate(renderer.tfobjtTemplate(),data,qstElement);
		 
	 };
	 /**
	  * OBJECTIVE TYPE QUESTIONS
	  * @param JSONData
	  */
	 mtfTemplate=function(data){
		 compileTemplate(renderer.mtfTemplate(),data,qstElement);
	 };
	 /**
	  * MATCH THE FOLLOWING TEMPLATE
	  */
	 edtMfTemplate=function(data){
		 data=_.groupBy(data,'questionid', function(model){
				return model;
			});
		 console.log("get data print vaues "+data);
		 compileTemplate(renderer.edtMfTemplate(),data,"#questiondiv");
	 };
	 uptblTemplate=function(data){
		 compileTemplate(renderer.uptblTemplate(),data,"#qstSheetTabl");
	 };
 // END 
	 
 // START ENTRY FORM
	 /**
	  * CONFIG QUESTION ENTRY FORM
	  */
	 /**
	  * LOAD TRUE / FALSE TYPE TEMPLATE 
	  */
	 var tftEntryForm=function(){
		 compileTemplate(renderer.tftEntryForm(),null,qstElement);
	 };
	 /**
	  * LOAD OBJECTIVE TYPE TEMPLATE
	  */
	 var objtEntryForm=function (){
		 compileTemplate(renderer.objtEntryForm(),null,qstElement);
	 };
	 /**
	  * LOAD FILL IN THE BLANK TYPE TEMPLATE
	  */
	 var fitbtEntryForm=function(){
		 compileTemplate(renderer.fitbtEntryForm(),null,qstElement);
	 };
	 /**
	  * LOAD MATCH THE FOLLOWING TYPE TEMPLATE
	  */
	 var mtftEntryForm=function(){
		 compileTemplate(renderer.mtftEntryForm(),null,qstElement);
	 };
	 /**
	  * LOAD OBJECTIVE TYPE TEMPLATE
	  */
	 var destEntryForm=function(){
		 compileTemplate(renderer.destEntryForm(),null,qstElement);
	 };
	 /**
	  * LOAD UPLOAD QUESTION SHEET TEMPLATE
	  */
	 var uploadForm=function(){
		 compileTemplate(renderer.uploadForm(),null,qstElement);
	 }; 
	 /**
	  * LOAD TEMPLATE EXAM FORM
	  */
	 var examForm=function(){
		 compileTemplate(renderer.examForm(),null,"#examTab");
		 $("#examTab").slimscroll({/*height: "520px",*/alwaysVisible: true});
	 };
	 /**
	  * LOAD QUESTION SELECTION FORM
	  */
	 var qstEntryForm=function(data){
		 compileTemplate(renderer.qstEntryForm(),data,"#entry");
	 };
	 
// END 
 /**
	 * SHOW ALERT
	 */
	var showAlert = function(alert_type, message) {
		$('.alert').remove();
		if (alert_type == "success")
			compileTemplate(renderer.success(message), null, '.showmessage');
		else if (alert_type == "failed")
			compileTemplate(renderer.fail(message), null, '.showmessage');

		$(".alert").fadeIn('slow').delay(2500).fadeOut('slow');
	};
	 
 	/**
	 * COMPILING DYNAMIC TEMPLATE 
	 * @param template string type
	 * @param JSONData String type
	 * @param OutputSelectors String type
	 */
	 compileTemplate=function(dynElement,jsonData,outputSelector){
		template = Handlebars.compile(dynElement);
		var templateHtml = template(jsonData);
		$(outputSelector).html(templateHtml);
		$("#questionDive").slimscroll({/*height: "430px",*/alwaysVisible: true});
	 };
	 
 	/**
	 * COMPILING DYNAMIC TEMPLATE 
	 * @param template string type
	 * @param JSONData String type
	 * @param OutputSelectors String type
	 */
	 appendTemplate=function(dynElement,jsonData,outputSelector){
		template = Handlebars.compile(dynElement);
		var templateHtml = template(jsonData);
		$(outputSelector).append(templateHtml);	
	 };
	 	 

	 /**
		 * DATA MANIPULATE FUNCTIONS
		 * 
		 * @param JSONData
		 * @returns Grouping data as list
		 */
	var groupJSON = function(data, qstTypeId, dynaDiv) {
		var questions = {};
		var id = parseInt(qstTypeId);
		if (data != "NOT_FOUND") {
			questions = _.groupBy(data, 'questionid', function(model) {
				return model;
			});
			if (id == 0)
				examConfigTemplate(questions);
			else if (id == 4)
				mtfTemplate(questions);
			else if (id <= 5)
				tfobjtTemplate(questions);
			else if (id == 1000){ // LOAD SELECTED QUESTION FOR EXAM QUESTION
				onetime=0;
				genQstTemplate(questions, dynaDiv);
			}
		} else {
			if (id != 0)
				compileTemplate(renderer.emptyQstTemplate(), null, qstElement);
			else
				compileTemplate(
						'<tr><td colspan="3"><center><h5 style="color: lightgray;">Question Not Found</h5></center></td></tr>',
						null, '#qstDiv');
		}

		$("#totalQst").text(
				"Total no.of question(s): " + Object.keys(questions).length);

	};
	var updatePaper=function(data, qstTypeId, dynaDiv){
		var temp="";
		temp=renderer.orTypeUPTemplate();
		arr=new Array();
		arr.push(data);
		 appendTemplate(temp,arr,"#"+dynaDiv);
	};
	 examConfigTemplate=function(questions){
		 compileTemplate(renderer.examConfigTemplate(),questions,'#qstDiv'); 
	 };
	 /**
	  * LOAD SELECTED QUESTION
	  * @param Collections
	  */
	 var genQstTemplate=function(data,dynaDiv){
		 if($("#orchk").prop( "checked" ) )
			 temp=renderer.orTypeTemplate();
		 else 
			 temp=renderer.genQstTemplate();
			 
		 appendTemplate(temp,data,"#"+dynaDiv);
	 };
	 

	 /**
		 * BINDING RENDERER FUNCTIONS FOR PUBLIC ACCESS
		 */
	return {
		selectTemplate : selectTemplate,
		edtMfTemplate : edtMfTemplate,
		uptblTemplate : uptblTemplate,
		groupJSON : groupJSON,
		updatePaper:updatePaper,

		tftEntryForm : tftEntryForm,
		objtEntryForm : objtEntryForm,
		fitbtEntryForm : fitbtEntryForm,
		mtftEntryForm : mtftEntryForm,
		destEntryForm : destEntryForm,
		examForm : examForm,
		qstEntryForm : qstEntryForm,

		uploadForm : uploadForm,
		showAlert : showAlert
	};
}();
//test
/**
 * CONFIG HANDLEBARS PROTOTYPES
 */
/**
 * @returns type of html object
 */
Handlebars.registerHelper('question-helper', function(val){
	return val.replace(new RegExp("courseData.getContextPath()", 'g'),courseData.getContextPath());
});
/**
 * @returns element objects
 */
Handlebars.registerHelper('match-question', function(question, options) {
	var items = question.split("%J@A#C&K~");
	var slno = 1;
	var object = this;
	var out = '';
	for ( var i = 0; i < object.length; i++) {
		var ans = object[i].answers.replace(/&nbsp;/g, "");
		var qst = "";
		ans = ans.replace(new RegExp("courseData.getContextPath()", 'g'),
				courseData.getContextPath());
		if (items.length > i) {
			qst = items[i].trim().replace(/&nbsp;/g, "");
			qst = qst.trim().replace(new RegExp("courseData.getContextPath()", 'g'),
					courseData.getContextPath());
		}
		out += '<div class="col-md-12 match-container pad0" ><span class="col-md-6 pad0">'
				+ slno + '. '  +qst+ '</span><div class="col-md-6">' + ans
				+ '</div></div>';
		slno++;
	}
	return out;
});

/**
 * @returns element objects
 */
Handlebars
		.registerHelper(
				'edit-match-question',
				function(question, options) {
					var items = question.split("%J@A#C&K~");
					var object = this;
					var out = '';
					var btn = '<div class="col-md-12 marbottom10"><a class="btn btn-danger pull-right matchbtn"  title="Delete Question">'
							+ '<i class="glyphicon glyphicon-trash"></i>'
							+ '</a>'
							+ '<a class="btn btn-primary pull-right matchbtn marright7" title="Edit Question" >'
							+ '<i class="fa fa-pencil"></i>' + '</a></div>';
					for ( var i = 0; i < object.length; i++) {
						var ans = object[i].answers.trim();
						var qst = "";
						ans = ans.replace(new RegExp(
								"courseData.getContextPath()", 'g'), courseData
								.getContextPath());
						if (items.length > i) {
							qst = items[i].trim();
							qst = qst.replace(new RegExp(
									"courseData.getContextPath()", 'g'),
									courseData.getContextPath());
						}
						out += '<div class="match-container col-md-12 pad0 marbottom10 borderbottom1" answerid="'
								+ object[i].answerid
								+ '"><div class="col-md-6">'
								+ qst
								+ '</div><div class="col-md-6">'
								+ ans
								+ '</div>'
								+ btn
								+ ''
								+ '</div>';
					}
					return out;
				});

/**
 * @returns css
 */
Handlebars.registerHelper('answer', function() {
	var qtypeid = this.id;
	var correctans = this.correctans;
	var answers = this.answers;
	if (qtypeid == "3" || qtypeid == "4") {
		if (correctans != 111)
			return "color: forestgreen;";
		else
			return "";
	}
	if (qtypeid != "1") {
		if (correctans != "0")
			return "color: forestgreen;";
		else
			return "";
	} else {
		if (answers == "True" && correctans == "1")
			return "color: forestgreen;";
		else if (answers == "False" && correctans == "0")
			return "color: forestgreen;";
		else
			return "";
	}
});
Handlebars.registerHelper('ifEquals', function(id,options) {
	if(id == '4')
     return options.fn(this);
	  
  return options.inverse(this);
});
var onetime=0;
Handlebars.registerHelper('or-operation', function(id) {
	if(onetime == 1)
     return '';
	onetime=1;
	
	return '<center style="margin-bottom: 10px;margin-top: 10px;margin-left: -50px;"><strong>( or )</strong></center>';
});