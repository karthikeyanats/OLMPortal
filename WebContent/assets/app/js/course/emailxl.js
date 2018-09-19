
var templates={
	showAlert:function(msg,alertmsg){
		$(".alertmsgbtn").show();
		$(".alermessage").text(""+msg+","+alertmsg+"");
		setTimeout(function(){
			$('.alertmsgbtn').hide();					
		},2500);
	}
};

var TemplateString=(function(){
	
	uploadTemplate=function(){
		return '<div id="uploadpart" style=""><div><div class="row marbottom10">' +
			'<div class="alert alert-danger alertmsgbtn martop7 marbottom10" style="display:none;margin-left:10px;margin-right:10px;width:95%;">'+
			'<span class="alermessage"></span></div><input type="file" name="file" class="uploademails col-md-5" id="uploademails" accept=".xls, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">'+
		'<button type="button" class="btn btn-flat btn-success emailuploadxls marright7">Upload</button>'+
		'<button  class="btn btn-flat btn-info uploadcancel marright7" type="button">Cancel</button>'+
		 '<br><br><p class="centertext"><span id="finalemail" style="display:none">Remove the unwanted email ids from the below list and '+
		'<a title="Confirm Email List" class="btn btn-primary">Click Here</a></span></p></div>'+
		'<div class="row marbottom10"><span id="uploadprocess" class="marleft15" style=""><small style="color: chocolate;"><span><u>Note</u> :Only xls format allowed , Download and see the format </span></small><a class="btn btn-flat" id="dwnld" title="Download format file" href='+courseData.getContextPath()+'/assets/emailtemplate.xls>'+
		'<i class="fa fa-download"></i></a></span><span class="upload-status pull-right" style="color: brown;padding-top: 10px;padding-right: 33px;display:none;">Please wait uploading...</span>'+
		'<progress id="progressBar" class="upload-status" max="100" value="0" style="height: 4px;width: 96%;vertical-align: text-top;display:none;"></progress></div></div>'+
		'<table class="table table-bordered whitebg table-striped" id="Emailtable" style="display:none">'+
        '<thead>'+
         ' <tr>'+
           ' <th >Email Id</th>'+
            '<th>Remove</th>'+            
          '</tr>'+
        '</thead>'+
        '<tbody id="emiltbl">'+         
        '</tbody>'+
      '</table>'+
      '</div>';
		
	};
	
	loadedtableTmpl=function(){
		 return '{{#each .}}'+
					'<tr>'+					
						'<td><a class="mailids">{{question}}</a></td>'+						
						'<td><a class="fa fa-remove btn btn-danger removerow"></a></td>'+
					'</tr>'+
					'{{/each}}' ;
	 };
	var renderer=function(){
		compileTemplate(uploadTemplate(),null,"#uploadextfilediv");
	};
	var uptblTemplate=function(data){		
		 compileTemplate(loadedtableTmpl(),data,"#emiltbl");
		 $('#Emailtable').show();
		 $('.removerow').click(removedata);
		 $('#finalemail').attr('style','display:');
		 $('#uploadprocess').attr('style','display:none');
		 $('#finalemail').click(emailfinal);
		 
	 };
	 emailfinal=function(){
		 $('#uploadpart').attr('style','display:none');
		 $('#emaildiv').show();
		 allmailid=[];
		 $('.mailids').each(function( index ) {
			 allmailid.push($( this ).text());
			});
		  $('.emailuser').val(allmailid);
	 };
	 removedata=function(){
		  $(this).closest( 'tr').remove();
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
	 };
	return {
		rendererForm:renderer,
		uptblTemplate:uptblTemplate
	};
});

var email=function(){
   var templateString =new TemplateString();
   var renderTemplate=function(){
	   templateString.rendererForm();
	   $(".emailuploadxls").click(uploadEmailData);
	  
	   $('.uploadcancel').click(function(){
			 $('#emaildiv').show();
			 $('#uploadpart').css('display','none');
		 });
   	};	
   
   	uploadEmailData=function(){
   		var progressBar=document.getElementById("progressBar");
		progressBar.value=0;
		var fileType = $("#uploademails").val().split('/').pop().split('\\').pop();
		fileType=fileType.substring(fileType.lastIndexOf("."),fileType.length).toLowerCase();
		if($("#uploademails").val().length == 0){
			templates.showAlert("failed"," Select a file ( .xls )");
			return;
		}
		if(fileType == ".xls" || fileType == ".xlsx" || fileType == ".cvs"){
			$(".upload-status").show();
			var xhr=new XMLHttpRequest();
			xhr.onload=function(data){
				if(xhr.status == 200){
					obj=xhr.responseText;
					var statusTextVal = obj.replace(/""/g, '"');
					if(statusTextVal.replace(/""/g, '"')=="PRECONDITION_FAILED"){
						$('#saveAction' ).prop( "disabled", false );
						templates.showAlert("failed","Error ! Invalid File");
					}else{
						if(obj.length > 2){
							//renderTemplate("#qstSheettempl",JSON.parse(obj),"#qstSheetTabl");
							templateString.uptblTemplate(JSON.parse(obj));
							
						}else{
							templates.showAlert("failed","Error ! Content not found in uploaded document");
						}
					}
					
					progressBar.value=0;
					$(".upload-status").hide();
					$(".chk-all").click(function(){
						$(".chk-self").prop("checked", $(this).is(':checked'));	
						$(".chk-self").attr("status",$(this).is(':checked'));
					});
					var $elemnt=$(".chk-self");
					$elemnt.click(function (){
						$(".chk-all").prop("checked", $elemnt.is(':checked'));	
						$(this).attr("status",$elemnt.is(':checked'));
					});
					
				}else if(xhr.status == 412){
					$('#saveAction' ).prop( "disabled", false );
					templates.showAlert("Failed","Error ! Upload failed. Invalid File");
					progressBar.value=0;
					$(".upload-status").hide();
					$(".chk-all").click(function(){
						$(".chk-self").prop("checked", $(this).is(':checked'));	
						$(".chk-self").attr("status",$(this).is(':checked'));
					});
					var $elemnt=$(".chk-self");
					$elemnt.click(function (){
						$(".chk-all").prop("checked", $elemnt.is(':checked'));	
						$(this).attr("status",$elemnt.is(':checked'));
					});
				}
				
				else{
					$('#saveAction' ).prop( "disabled", false );
					templates.showAlert("Failed","Error ! Upload failed.Can not connect to server");
					progressBar.value=0;
					$(".upload-status").hide();
					$(".chk-all").click(function(){
						$(".chk-self").prop("checked", $(this).is(':checked'));	
						$(".chk-self").attr("status",$(this).is(':checked'));
					});
					var $elemnt=$(".chk-self");
					$elemnt.click(function (){
						$(".chk-all").prop("checked", $elemnt.is(':checked'));	
						$(this).attr("status",$elemnt.is(':checked'));
					});
				}
			};
			xhr.upload.onprogress=function(e){
				var percentComplete=(e.loaded/e.total)*100;
				progressBar.value=percentComplete;
			};
			xhr.onerror=function(){
				//console.log("Error ! upload failed.Can not connect to server");
				templates.showAlert("Failed","Error ! Upload failed.Can not connect to server");
				//alert("Upload failed.Can not connect to server");
			};
			var fd=new FormData();
			fd.append("file",document.getElementById('uploademails').files[0]);
			fd.append("filetype",fileType);
			xhr.open("POST",courseData.getContextPath()+"/rest/adminusers/NewExcelParser",true);
			xhr.send(fd);
		}else{
			templates.showAlert("Failed","Only '.xls and .xlsx' file can upload");
		}

   	};
   
	return {
		uploadxlEmail:renderTemplate
	};
}();
