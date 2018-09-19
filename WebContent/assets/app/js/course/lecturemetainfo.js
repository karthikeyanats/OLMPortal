$(document).ready(function(){
	Handlebars.registerHelper('linkcheck', function(desctype,desc){ 
		if(desctype=="Weblink"){
			text = Handlebars.Utils.escapeExpression(desc);
			var result = '<a href="http://' + text + '" target=_blank>' + text + '</a>';
			return new Handlebars.SafeString(result);
		}else{
			return desc;
		}
	});
});
var lecturemetainfo=(function(){
	var controller={
			infodesc:function(id,type){
				return Ajax.get(courseData.getContextPath()+"/rest/metadesc/"+id+"/"+type+"",undefined,"json","application/json");
			},
			infodescsave:function(obj){
				return Ajax.post(courseData.getContextPath()+"/rest/metadesc/",obj,"json","application/json");
			},
			infodescdelete:function(id){
				return helper.del(courseData.getContextPath()+"/rest/metadesc/"+id,undefined,"json","application/json");
			}
	};
	var model={
			infodesc:function(){
				var list=controller.infodesc($('#descriptionholder').attr('metaid'), $('.metalink.active').attr('nt'));
				if(list!=="NO_CONTENT"){
					return list;	
				}else{
					return null;
				}
			},
			infodescsave:function(obj){
				return controller.infodescsave(obj);
			},
			infodescdelete:function(id){
				return controller.infodescdelete(id);
			}
	};
	var objects={
			infodescsave:function(descriptiontype){
				return obj={
						description:$('#desctextarea').val().trim(),
						descriptiontype:$('.metalink.active').attr('nt'),
						status:"A",
						metainfoid:$('#descriptionholder').attr('metaid'),
						type:"save"
				};
			},
			infodescupdate:function(descid){
				return obj={
						descid:descid,
						description:$('#desctextarea').val().trim(),
						type:"update"
				};
			}
	};
	var view={
			infotypes:function(ids){
				$('#lecturemetainfomodal').modal('show');
				//$('#lecturemetainfomodal').css("width","75%").css("left","35%");
				helper.render(templates.infotypes(),null,"#lecturemetainfoholder");
				$('#descriptionholder').attr('metaid',ids);
				var $metalink=$('.metalink'),$metalinkfirst=$('.metalink:first'),
				$desctitle=$('.desctitle'),$newtitle=$('.newtitle'),$descthtitle=$('.descthtitle');
				$metalinkfirst.addClass('active');
				view.infodesc();
				$metalink.click(function() {
					$metalink.removeClass('active');
					$(this).addClass('active');
					$desctitle.text($(this).attr('at'));
					$newtitle.text($(this).attr('nt'));
					$descthtitle.text($(this).attr('nt'));
					view.infodesc();
				});				
			},
			infodesc:function(){
				var list=model.infodesc();
				helper.render(templates.decsription(),list,"#descriptionholder");
				var infotype=_.contains(["Reference", "Diagram", "Table","Map"], $('.metalink.active').attr('nt'));
				if(infotype==true){
					view.infowithupload();
				}else{
					view.infotext();
				}				
			},
			infowithupload:function(){
				helper.render(templates.newfile(),null,"#newdescription");
				$("#infofilesubmit").click(function(){
					$("#infofilesubmit").hide();
					helper.checkextn();
				});
				$('#tabledesc').slimScroll({
					height: '250px'
									});
				$('.updatemsg').hide();
				$newdescbtn=$('.newdescbtn'),$cancelbtn=$('.cancelbtn'),$deletedesc=$('.deletedesc');
				$deletedesc.off().click(function(){
					var str=$('.metalink.active').attr('nt');
					var checkstr =  confirm('Are you sure you want to delete this '+str);
					if(checkstr == true)	{
						model.infodescdelete($(this).attr('descid'));
						$('.newfileinfomsg').show();
						setTimeout(function(){
							$('.newfileinfomsg').hide();
							view.infodesc();
						},2000);
					}
				});
			},
			infotext:function(){
				helper.render(templates.newtext(),null,"#newdescription");
				var detype = $('.metalink.active').attr('nt');
				
				$('#desctextarea').keyup(function(){
					el = $(this);
					if(detype=="Keyword"){
						if(el.val().length >= 21){
							el.val( el.val().substr(0, 20) );
						}
					}else{
						if(el.val().length >= 501){
							el.val( el.val().substr(0, 500) );
						}	
					}
				});
				$('#tabledesc').slimScroll({
					height: '250px'
				});
				$('.updatemsg').hide();
				$newdescbtn=$('.newdescbtn'),$newtextdescription=$('#newtextdescription'),
				$cancelbtn=$('.cancelbtn'),$editdesc=$('.editdesc'),$deletedesc=$('.deletedesc');
				$newdescbtn.click(function(){
					$('#desctextarea').val('');
					$('#descsave').text("Save");
				});
				$cancelbtn.click(function(){
					$('#desctextarea').val('');
				});
				$('#descsave').off().click(function(){
					$(this).hide();
					var txt=$(this).text();
					if($('#desctextarea').val().trim().length!=0){
						if(txt=="Save"){
							var savesucess= model.infodescsave(objects.infodescsave());
							if(savesucess=="OK"){
								$('#desctextarea').val('');
								$('.updatemsg').show();
								$('.updatemsg').text("Saved");
								setTimeout(function(){
									$('#descsave').show();
									$('.updatemsg').hide();
									view.infodesc();
								},1000);

							}else{
								$('.updatemsg').show();
								$('.updatemsg').text("Failed");
								setTimeout(function(){
									$('#descsave').show();
									$('.updatemsg').hide();
								},1000);
							}
						}else{
							var savesucess= model.infodescsave(objects.infodescupdate($(this).attr('descid')));
							if(savesucess=="OK"){
								$('#desctextarea').val('');
								$('#descsave').text("Save");
								$('.updatemsg').show();
								$('.updatemsg').text("Updated");
								setTimeout(function(){
									$('.updatemsg').hide();
									$('#descsave').show();
									view.infodesc();
								},1000);
							}else{
								$('.updatemsg').show();
								$('.updatemsg').text("Failed");
								setTimeout(function(){
									$('.updatemsg').hide();
									$('#descsave').show();
								},1000);
							}
						}
					}else{
						$('.updatemsg').show();
						$('.updatemsg').text("Empty! Fill it.");
						setTimeout(function(){
							$('.updatemsg').hide();
							$('#descsave').show();
						},1000);
					}
				});
				$editdesc.off().click(function(){
					var desc=$(this).attr('desc');
					$('#desctextarea').val(desc);
					$('#descsave').text("Update");
					$('#descsave').attr("descid",$(this).attr('descid'));
				});
				$deletedesc.off().click(function(){
					var str=$('.metalink.active').attr('nt');
					var checkstr =  confirm('Are you sure you want to delete this '+str);
					if(checkstr == true)	{
						model.infodescdelete($(this).attr('descid'));
						$('.deletesucess').show();
						setTimeout(function(){
							$('.deletesucess').hide();
							view.infodesc();
						},2000);
					}
				});
			}
	};
	var templates={
			infotypes:function(){
				return "<div class='row'><div class='col-md-12'>" +
				"<div class='col-md-3 pad0'><ul class='reviewli pad0'>" +
				"<li class='metalink greybgwhiteborder active' at='Keywords' nt='Keyword'><a class='white'>Keywords</a></li>" +
				"<li class='metalink greybgwhiteborder' at='Weblinks' nt='Weblink'><a class='white'>Weblinks</a></li>" +
				"<li class='metalink greybgwhiteborder' at='Summary' nt='Summary'><a class='white'>Summary</a></li>" +
				"<li class='metalink greybgwhiteborder' at='Box Items' nt='Box Item'><a class='white'>Box Items</a></li>" +
				"<li class='metalink greybgwhiteborder' at='Project Ideas' nt='Project Idea'><a class='white'>Project Ideas</a></li>" +
				"<li class='metalink greybgwhiteborder' at='Activities' nt='Activity'><a class='white'>Activities</a></li>" +
				"<li class='metalink greybgwhiteborder' at='References' nt='Reference'><a class='white'>Teachers Guide</a></li>" +
				"<li class='metalink greybgwhiteborder' at='Diagrams' nt='Diagram'><a class='white'>Course Log Book</a></li>" +
				"<li class='metalink greybgwhiteborder' at='Tables' nt='Table'><a class='white'>HandOuts</a></li>" +
				"<li class='metalink greybgwhiteborder' at='Maps' nt='Map'><a class='white'>Mind Maps</a></li></ul></div>" +
				"<div class='col-md-9'><h4 class='borderbottom1 lineheight40 mar0'>" +
				"<font class='desctitle'>Keywords</font>" +
				"<a class='btn btn-success newdescbtn pull-right marbottom10' >New " +
				"<font class='newtitle'>Keyword</font></a></h4>" +
				"<div class='row martopbottom10' id='newdescription'></div>" +
				"<div class='row' id='tabledesc'><div class='col-md-12'><table class='table whitebg table-striped table-bordered'" +
				"><thead><tr><th>Sl.No</th><th class='descthtitle'>Description</th>" +
				"<th class='text-center'>Action</th></tr></thead><tbody id='descriptionholder'></tbody>" +
				"</table></div></div></div></div></div>";
			},
			newtext:function(){
				return "<div class='row' id='newtextdescription'><div class='col-md-12'>" +
				"<div class='col-md-8'><textarea placeholder='Enter Description' id='desctextarea' " +
				"class='col-md-12 form-control'></textarea></div>" +
				"<div class='col-md-4 martop10'><div class='row' >" +
				"<div class='alert alert-info updatemsg' style='display:none;'></div></div><div class='row'>" +
				"<a class='btn btn-success marright7' id='descsave'>Save</a>" +
				"<a class='btn btn-danger cancelbtn'>Reset</a></div></div>" +
				"</div></div><div class='alert alert-info marleft15 deletesucess' style='display:none;'>Successfully Deleted</div>";
			},
			newfile:function(){
				return "<div class='col-md-12' id='newfiledescription'>" +
				"<form class='form-horizontal'><fieldset><div class='form-group'>" +
				"<label class='col-sm-3 righttext'>Choose File : </label><div class='col-sm-9'>" +
				"<input class='input-file' id='info-file' name='infofile' type='file'></div></div>" +
				"<div class='form-group'><label class='col-sm-3 righttext'></label>" +
				"<div class='col-sm-9'><a id='infofilesubmit' name='button1id' class='btn btn-success' >Upload</a></div></div></fieldset>" +
				"<div class='row alert alert-info newfileinfomsg marleftt7' style='display:none;'>Successfully Deleted</div>" +
				"</form></div>"; 
			},
			decsription:function(){
				return "{{#if .}}{{#each .}}<tr><td>{{math @index '+'1}}</td><td class=' breakword width60per'>" +
				"{{#if originalfilename}}" +
				"<a href='"+courseData.getContextPath()+"/OpenFile?r1=serverpath&r2={{description}}' target='_blank'>{{originalfilename}}</a>" +
				"{{else}}{{linkcheck descriptiontype description}}{{/if}}</td><td class='text-center width20'>" +
				"{{#if originalfilename}}{{else}}<a class='btn btn-info btn-xs editdesc marright7' descid='{{descid}}' desc='{{description}}'>" +
				"<i class='fa fa-pencil'></i> </a>{{/if}}<a descid='{{descid}}' class='btn btn-danger btn-xs deletedesc'>" +
				"<i class='fa fa-trash icon-large'></i> </a></td></tr>{{/each}}{{else}}<tr>" +
				"<td colspan='3' style='text-align:center'>No data found</td><tr>{{/if}}";
			}
	};
	var helper={
			render:function(tmpl,list,outputselector){
				var template = Handlebars.compile(tmpl);
				var templateHtml = template(list);
				$(outputselector).html(templateHtml);
			},
			del:function(url,data,dataType,contentType){
				var output = Ajax.ajaxHelper("DELETE",url,data,dataType,contentType);
				return output;	
			},
			checkextn:function(){
				if($('#info-file').val()!=""){
					var fileextn=document.getElementById('info-file').value.split('.').pop();
					var imgextensions=["png","jpeg","jpg","gif","pdf"];
					var fileextensions=["pdf"];
					var uploadfor=$('.metalink.active').attr('nt');
					if(uploadfor=="Reference"){
						
						
						if(_.contains(fileextensions,fileextn.toLowerCase())){
							helper.uploadFiles();
						}else{
							$('.newfileinfomsg').show().text("Not a Valid Extension. Only "+fileextensions+" is allowed");
							setTimeout(function(){
								$('.newfileinfomsg').hide();
								$("#infofilesubmit").show();
								$('#info-file').val('');
							},2000);
						}
					}else{
						if(_.contains(imgextensions,fileextn.toLowerCase())){
							helper.uploadFiles();
						}else{
							$('.newfileinfomsg').show().text("Not a Valid Extension. Only "+imgextensions+" are supported");
							setTimeout(function(){
								$('.newfileinfomsg').hide();
								$("#infofilesubmit").show();
								$('#info-file').val('');
							},2000);
						}
					}
				}
				else{
					$('.newfileinfomsg').show().text("Empty! Choose a file");
					setTimeout(function(){
						$("#infofilesubmit").show();
						$('.newfileinfomsg').hide();
					},2000);
				}	
			},
			uploadFiles:function(){
				var xhr=new XMLHttpRequest();
				xhr.onload=function(data){
					if(xhr.status == 200){
						$('.newfileinfomsg').show().text("Uploaded");
						setTimeout(function(){
							$('.newfileinfomsg').hide();
							view.infodesc();
						},2000);
					}else{
						$('.newfileinfomsg').show().text("Failed. Try again later");
						setTimeout(function(){
							$('.newfileinfomsg').hide();
							$('#info-file').val('');
						},2000);
					}
				};
				xhr.onerror=function(){
					$('.newfileinfomsg').show().text("Error ! upload failed.Can not connect to server");
					setTimeout(function(){
						$('.newfileinfomsg').hide();
						$('#info-file').val('');
					},2000);
				};
				var fd=new FormData();
				fd.append("infofile",document.getElementById('info-file').files[0]);
				fd.append("metainfoid",$('#descriptionholder').attr('metaid'));
				fd.append("desctype",$('.metalink.active').attr('nt'));
				xhr.open("POST",courseData.getContextPath()+"/rest/metadesc/upload",true);
				xhr.send(fd);
			}
	};
	return {
		init:function(ids){
			initialize(ids);
		}
	};
	function initialize(ids){
		view.infotypes(ids);
	}
});