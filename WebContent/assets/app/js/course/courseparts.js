var courseParts = (function () {
	var objects={
			board:function(boardname,boardstatus,status,boardid){
				var boardobj = {
						boardname: boardname,
						boardstatus:boardstatus,
						status:status,
						boardid:boardid
				};
				return boardobj; 
			},
			boardforcategory:function(boardid,coursecategorystatus){
				var boardforcategoryobj = {
						coursecategorystatus: coursecategorystatus,
						boardid:boardid
				};
				return boardforcategoryobj; 
			},
			category:function(coursecategoryname,coursecategorydescription,coursecategorystatus,boardid,status,coursecategoryid){
				var categoryobj = {
						coursecategoryname: coursecategoryname,
						coursecategorydescription:coursecategorydescription,
						coursecategorystatus:coursecategorystatus,
						coursecategoryid:coursecategoryid,
						boardid:boardid,
						status:status
				};
				return categoryobj; 
			},
			medium:function(mediumname,mediumstatus,status,mediumid){
				var mediumobj = {
						mediumname: mediumname,
						mediumstatus:mediumstatus,
						status:status,
						mediumid:mediumid
				};
				return mediumobj; 
			},
			standardlevel:function(standardlevelname,standardlevelstatus,status,standardlevelid){
				var levelobj = {
						standardlevelname: standardlevelname,
						standardlevelstatus:standardlevelstatus,
						status:status,
						standardlevelid:standardlevelid
				};
				return levelobj; 
			}
	};
	var controller = {	
			board:function(status){
				return Ajax.get(courseData.getContextPath()+"/rest/board/"+status+"",undefined,"json","application/json");				
			},
			boardUpdate:function(boardobj){
				return Ajax.post(courseData.getContextPath()+"/rest/board/",boardobj,"json","application/json");
			},
			category:function(boardcategoryobj){
				return Ajax.post(courseData.getContextPath()+"/rest/boardcategory/",boardcategoryobj,"json","application/json");
			},
			categoryUpdate:function(categoryobj){
				return Ajax.put(courseData.getContextPath()+"/rest/boardcategory/",categoryobj,"json","application/json");
			},
			medium:function(status){
				return Ajax.get(courseData.getContextPath()+"/rest/medium/"+status+"",undefined,"json","application/json");
			},
			mediumUpdate:function(mediumobj){
				return Ajax.post(courseData.getContextPath()+"/rest/medium/",mediumobj,"json","application/json");
			},
			standardlevel:function(status){
				return Ajax.get(courseData.getContextPath()+"/rest/standard/"+status+"",undefined,"json","application/json");
			},
			standardLevelUpdate:function(levelobj){
				return Ajax.post(courseData.getContextPath()+"/rest/standard/",levelobj,"json","application/json");
			},
	}; 
	var model = {
			board:function(status){
				return controller.board(status);				
			},
			boardUpdate:function(boardobj){
				return controller.boardUpdate(boardobj);				
			},
			category:function(categoryobj){
				return controller.category(categoryobj);
			},
			categoryUpdate:function(categoryobj){
				return controller.categoryUpdate(categoryobj);
			},
			medium:function(status){
				return controller.medium(status);
			},
			mediumUpdate:function(boardobj){
				return controller.mediumUpdate(boardobj);				
			},
			standardlevel:function(status){
				return controller.standardlevel(status);
			},
			standardlevelUpdate:function(levelobj){
				return controller.standardLevelUpdate(levelobj);				
			},
	};

	var view = {	
			coursepartLinks:function(){
				renderTemplate("#coursepartlinkstmpl", null, "#coursedescriptiontable");
				var $boardlink=$('.boardlink');
				helper.linkSelector("boa");
				$boardlink.click(function(){
					helper.linkSelector($(this).attr('st'));
				});
			},
			board:function(st,list){
				var labelvalue=$('.boardlink[st=boa]').text();
				renderTemplate("#boardstmpl", list, "#coursepartLinksdescription");
				$boardnameval=$('.boardnameval'),$boardslistbtn=$('.boardslistbtn');
				$boardopbtn=$('.boardopbtn');
				if(st=="A"){
					$boardslistbtn.text("Trashed "+labelvalue);
					$('.linkstitle').text("Active "+labelvalue);
					$('.boardmanagelinks[op=edit]').text("Edit");
					$('.boardmanagelinks[op=trash]').text("Trash");
				}else if(st=="D"){
					$boardslistbtn.text("Active "+labelvalue);
					$('.linkstitle').text("Trashed "+labelvalue);
					$('.boardmanagelinks[op=edit]').text("Delete");
					$('.boardmanagelinks[op=trash]').text("Restore");
				}
				$('.newboard').click(function(e)  {
					$('.boarddropbox').slideDown(400);
					$boardopbtn.text('Save').attr('boardid','');
					$boardnameval.val('');					
				});
				$('.closeboardbtn').click(function(){
					$('.boarddropbox').slideUp(400);
				});
				//category button
				$boardopbtn.click(function(){
					if($boardnameval.val().trim().length!=0){
						var op=$(this).text();
						if(confirm("Are you sure you want to "+op+" this category")){
						if(op=="Save"){
							var boardobj=objects.board($boardnameval.val(),"A","Save");
							helper.boardUpdater(null,boardobj,
									"A",""+labelvalue+" Has been successfully Created.","A","Something went Wrong. Please Check back later.");														
						}
						
						else if(op=="Update"){
							var boardobj=objects.board($boardnameval.val(),"A","Update",$(this).attr('boardid'));
							helper.boardUpdater(null,boardobj,
									"A",""+labelvalue+" Details Has been Updated.","A","Something went Wrong. Please Check back later.");							
							}
						}	
					}else{
						helper.alerter("failure","Fill "+labelvalue+" Name","Failure !");
					}
				});
				$('.boardmanagelinks').click(function(e)  {
					var that=$(this);
					var op=$(this).text();					
					if(op==="Edit"){
						$('.boarddropbox').slideDown(400);
						$boardopbtn.text('Update').attr('boardid',$(this).attr('boardid'));
						$boardnameval.val($(this).attr('boardname'));	
					}else if(op==="Trash"){						
						var boardobj=objects.board(that.attr('boardname'),"D","Update",that.attr('boardid'));
						helper.boardUpdater("Are you sure you want to trash this "+labelvalue+"",boardobj,
								"D",""+labelvalue+" Has been moved to trash.","D","Something went Wrong. Please Check back later.");
					}else if(op==="Delete"){
						var boardobj=objects.board(that.attr('boardname'),"X","Update",that.attr('boardid'));
						helper.boardUpdater("Are you sure you want to delete this "+labelvalue+"",boardobj,
								"D",""+labelvalue+" Has been Deleted.","D","Something went Wrong. Please Check back later.");
					}else if(op==="Restore"){
						var boardobj=objects.board(that.attr('boardname'),"A","Update",that.attr('boardid'));
						helper.boardUpdater("Are you sure you want to Restore this "+labelvalue+"",boardobj,
								"A",""+labelvalue+" Has been Restored.","D","Something went Wrong. Please Check back later.");
					}			
				});
				$boardslistbtn.click(function(){
					var labelname=$(this).text();
					if(labelname=="Active "+labelvalue){
						view.board("A",model.board("A"));	
					}else if(labelname=="Trashed "+labelvalue){
						view.board("D",model.board("D"));	
					} 
				});	
			},
			boardcategory:function(st,list){
				renderTemplate("#boardforcategoriestmpl", list, "#coursepartLinksdescription");
				var categoryobj=objects.boardforcategory($('#boardforcategory').val(),"A");
				var list=model.category(categoryobj);
				view.category(st,list,$('#boardforcategory').val());
				$('#boardforcategory').change(function(){
					var categoryobj=objects.boardforcategory($(this).val(),"A");
					view.category(st,model.category(categoryobj),$(this).val());
				});
			},
			category:function(st,list,boardid){
				var labelvalue=$('.boardlink[st=cat]').text();
				renderTemplate("#boardcategoriestmpl", list, "#boardcategoriestable");
				$boardnameval=$('.categorynameval'),$boardslistbtn=$('.boardslistbtn');
				$boardopbtn=$('.categoryopbtn'),$categorydescriptionval=$('.categorydescription');
				if(st=="A"){
					$boardslistbtn.text("Trashed "+labelvalue);
					$('.linkstitle').text("Active "+labelvalue);
					$('.boardmanagelinks[op=edit]').text("Edit");
					$('.boardmanagelinks[op=trash]').text("Trash");
				}else if(st=="D"){
					$boardslistbtn.text("Active "+labelvalue);
					$('.linkstitle').text("Trashed "+labelvalue);
					$('.boardmanagelinks[op=edit]').text("Delete");
					$('.boardmanagelinks[op=trash]').text("Restore");
				}
				$('.newcategory').click(function(e)  {
					$('.boarddropbox').slideDown(400);
					$boardopbtn.text('Save').attr('boardid',boardid);
					$boardnameval.val('');		
					$categorydescriptionval.val('');
				});
				$('.closeboardbtn').click(function(){
					$('.boarddropbox').slideUp(400);
				});
				$boardslistbtn.click(function(){
					var labelname=$(this).text();
					if(labelname=="Active "+labelvalue){
						var categoryobj=objects.boardforcategory(boardid,"A");
						view.category("A",model.category(categoryobj),boardid);						
					}else if(labelname=="Trashed "+labelvalue){
						var categoryobj=objects.boardforcategory(boardid,"D");
						view.category("D",model.category(categoryobj),boardid);
					} 
				});
				// sub category button2
				$boardopbtn.click(function(){
					var categoryname=$('.categorynameval').val();
					var categorydescription=$('.categorydescription').val();
					if(categoryname.trim().length!=0){
						if(categorydescription.trim().length!=0){
							var op=$(this).text();
							if(confirm("Are you sure you want to "+op+" this sub-category")){
							if(op=="Save"){
								var catobj=objects.category(categoryname, categorydescription, "A",boardid,"Save");
								helper.categoryUpdater(st,null,catobj,
										"A",""+labelvalue+" Has been successfully Created.","A","Something went Wrong. Please Check back later.");														
							}else if(op=="Update"){
								var catobj=objects.category(categoryname, categorydescription, "A", boardid,"Update",$(this).attr('coursecategoryid'));
								helper.categoryUpdater(st,null,catobj,
										"A",""+labelvalue+" Details Has been Updated.","A","Something went Wrong. Please Check back later.");							
							}
							}
						}else{
							helper.alerter("failure","Fill "+labelvalue+" Description","Failure !");
						}
					}else{
						helper.alerter("failure","Fill "+labelvalue+" Name","Failure !");
					}
				});
				$('.boardmanagelinks').click(function(e)  {
					var that=$(this);
					var op=that.text();					
					if(op==="Edit"){
						$('.boarddropbox').slideDown(400);
						$boardopbtn.text('Update').attr('boardid',$(this).attr('boardid')).attr('coursecategoryid',$(this).attr('coursecategoryid'));
						$boardnameval.val($(this).attr('coursecategoryname'));
						$categorydescriptionval.val($(this).attr('coursecategorydescription'));

					}else if(op==="Trash"){	
						var catobj=objects.category($(this).attr('coursecategoryname'), $(this).attr('coursecategorydescription'), "D", boardid,"Update",$(this).attr('coursecategoryid'));
						helper.categoryUpdater("D","Are you sure you want to trash this "+labelvalue+"",catobj,
								"D",""+labelvalue+" Has been moved to Trash.","A","Something went Wrong. Please Check back later.");
					}else if(op==="Delete"){
						var catobj=objects.category($(this).attr('coursecategoryname'), $(this).attr('coursecategorydescription'), "X", boardid,"Update",$(this).attr('coursecategoryid'));
						helper.categoryUpdater("D","Are you sure you want to delete this "+labelvalue+"",catobj,
								"D",""+labelvalue+" Has been Deleted.","A","Something went Wrong. Please Check back later.");
					}else if(op==="Restore"){
						var catobj=objects.category($(this).attr('coursecategoryname'), $(this).attr('coursecategorydescription'), "A", boardid,"Update",$(this).attr('coursecategoryid'));
						helper.categoryUpdater("A","Are you sure you want to Restore this "+labelvalue+"",catobj,
								"A",""+labelvalue+" Has been Restored.","D","Something went Wrong. Please Check back later.");						
					}			
				});
			},
			medium:function(st,list){
				var labelvalue=$('.boardlink[st=med]').text();
				renderTemplate("#mediumstmpl", list, "#coursepartLinksdescription");
				$boardnameval=$('.mediumnameval'),$boardslistbtn=$('.boardslistbtn');
				$boardopbtn=$('.mediumopbtn');
				if(st=="A"){
					$boardslistbtn.text("Trashed "+labelvalue);
					$('.linkstitle').text("Active "+labelvalue);
					$('.boardmanagelinks[op=edit]').text("Edit");
					$('.boardmanagelinks[op=trash]').text("Trash");
				}else if(st=="D"){
					$boardslistbtn.text("Active "+labelvalue);
					$('.linkstitle').text("Trashed "+labelvalue);
					$('.boardmanagelinks[op=edit]').text("Delete");
					$('.boardmanagelinks[op=trash]').text("Restore");
				}
				$('.newmedium').click(function(e)  {
					$('.boarddropbox').slideDown(400);
					$boardopbtn.text('Save').attr('mediumid','');
					$boardnameval.val('');					
				});
				$('.closeboardbtn').click(function(){
					$('.boarddropbox').slideUp(400);
				});
				$boardopbtn.click(function(){
					if($boardnameval.val().trim().length!=0){
						var op=$(this).text();
						if(confirm("Are you sure you want to "+op+" this medium")){
							if(op=="Save"){
								var boardobj=objects.medium($boardnameval.val(),'A',"save");
								helper.mediumUpdater(null,boardobj,
										"A",""+labelvalue+" Has been successfully Created.","A","Something went Wrong. Please Check back later.");														
							}else if(op=="Update"){
								var boardobj=objects.medium($boardnameval.val(),'A',"update",$(this).attr('mediumid'));
								helper.mediumUpdater(null,boardobj,
										"A",""+labelvalue+" Details Has been Updated.","A","Something went Wrong. Please Check back later.");							
							}	
						}
					}
					else{
						$('.alertmessages').show().removeClass('alert-success').removeClass('alert-danger');
						$('.alertmessages').addClass('alert-danger');	
						$('.alertstatus').text(' ');
						$('.alertmessage').html('Please enter the medium');
						setTimeout(function(){
							$('.alertmessages').hide();					
						},1500);
					}
				});
				$('.boardmanagelinks').click(function(e)  {
					var that=$(this);
					var op=$(this).text();					
					if(op==="Edit"){
						$('.boarddropbox').slideDown(400);
						$boardopbtn.text('Update').attr('mediumid',$(this).attr('mediumid'));
						$boardnameval.val($(this).attr('mediumname'));	
					}else if(op==="Trash"){						
						var boardobj=objects.medium(that.attr('mediumname'),'D',"Update",that.attr('mediumid'));
						helper.mediumUpdater("Are you sure you want to trash this "+labelvalue+"",boardobj,
								"D",""+labelvalue+" Has been moved to trash.","D","Something went Wrong. Please Check back later.");
					}else if(op==="Delete"){
						var boardobj=objects.medium(that.attr('mediumname'),'X',"Update",that.attr('mediumid'));
						helper.mediumUpdater("Are you sure you want to delete this "+labelvalue+"",boardobj,
								"D",""+labelvalue+" Has been Deleted.","D","Something went Wrong. Please Check back later.");
					}else if(op==="Restore"){
						var boardobj=objects.medium(that.attr('mediumname'),'A',"Update",that.attr('mediumid'));
						helper.mediumUpdater("Are you sure you want to Restore this "+labelvalue+"",boardobj,
								"A",""+labelvalue+" Has been Restored.","D","Something went Wrong. Please Check back later.");
					}			
				});
				$boardslistbtn.click(function(){
					var labelname=$(this).text();
					if(labelname=="Active "+labelvalue){
						view.medium("A",model.medium("A"));	
					}else if(labelname=="Trashed "+labelvalue){
						view.medium("D",model.medium("D"));	
					} 
				});	
			},
			standardlevel:function(st,list){				
				var labelvalue=$('.boardlink[st=sta]').text();
				renderTemplate("#standarlevelsstmpl", list, "#coursepartLinksdescription");
				$boardnameval=$('.standardlevelnameval'),$boardslistbtn=$('.boardslistbtn');
				$boardopbtn=$('.boardopbtn');
				if(st=="A"){
					$boardslistbtn.text("Trashed "+labelvalue);
					$('.linkstitle').text("Active "+labelvalue);
					$('.boardmanagelinks[op=edit]').text("Edit");
					$('.boardmanagelinks[op=trash]').text("Trash");
				}else if(st=="D"){
					$boardslistbtn.text("Active "+labelvalue);
					$('.linkstitle').text("Trashed "+labelvalue);
					$('.boardmanagelinks[op=edit]').text("Delete");
					$('.boardmanagelinks[op=trash]').text("Restore");
				}
				$('.newstandardlevel').click(function(e)  {
					$('.boarddropbox').slideDown(400);
					$boardopbtn.text('Save').attr('standardlevelid','');
					$boardnameval.val('');					
				});
				$('.closeboardbtn').click(function(){
					$('.boarddropbox').slideUp(400);
				});
				$boardopbtn.click(function(){
					if($boardnameval.val().trim().length!=0){
						var op=$(this).text();
						if(confirm("Are you sure you want to " +op + " this Standard Level")){
							if(op=="Save"){
								var boardobj=objects.standardlevel($boardnameval.val(),"A","Save");
								helper.standardLevelUpdater(null,boardobj,
										"A",""+labelvalue+" Has been successfully Created.","A","Something went Wrong. Please Check back later.");														
							}else if(op=="Update"){
								var boardobj=objects.standardlevel($boardnameval.val(),"A","Update",$(this).attr('standardlevelid'));
								helper.standardLevelUpdater(null,boardobj,
										"A",""+labelvalue+" Details Has been Updated.","A","Something went Wrong. Please Check back later.");							
							}	
						}
					}else{
						$('.alertmessages').show().removeClass('alert-success').removeClass('alert-danger');
						$('.alertmessages').addClass('alert-danger');	
						$('.alertstatus').text(' ');
						$('.alertmessage').html('Please enter the standard level');
						setTimeout(function(){
							$('.alertmessages').hide();					
						},1500);
					}
				});
				$('.boardmanagelinks').click(function(e)  {
					var that=$(this);
					var op=$(this).text();					
					if(op==="Edit"){
						$('.boarddropbox').slideDown(400);
						$boardopbtn.text('Update').attr('standardlevelid',$(this).attr('standardlevelid'));
						$boardnameval.val($(this).attr('standardlevelname'));	
					}else if(op==="Trash"){						
						var boardobj=objects.standardlevel(that.attr('standardlevelname'),"D","Update",that.attr('standardlevelid'));
						helper.standardLevelUpdater("Are you sure you want to trash this "+labelvalue+"",boardobj,
								"D",""+labelvalue+" Has been moved to trash.","D","Something went Wrong. Please Check back later.");
					}else if(op==="Delete"){
						var boardobj=objects.standardlevel(that.attr('standardlevelname'),"X","Update",that.attr('standardlevelid'));
						helper.standardLevelUpdater("Are you sure you want to delete this "+labelvalue+"",boardobj,
								"D",""+labelvalue+" Has been Deleted.","D","Something went Wrong. Please Check back later.");
					}else if(op==="Restore"){
						var boardobj=objects.standardlevel(that.attr('standardlevelname'),"A","Update",that.attr('standardlevelid'));
						helper.standardLevelUpdater("Are you sure you want to Restore this "+labelvalue+"",boardobj,
								"A",""+labelvalue+" Has been Restored.","D","Something went Wrong. Please Check back later.");
					}			
				});
				$boardslistbtn.click(function(){
					var labelname=$(this).text();
					if(labelname=="Active "+labelvalue){
						view.standardlevel("A",model.standardlevel("A"));	
					}else if(labelname=="Trashed "+labelvalue){
						view.standardlevel("D",model.standardlevel("D"));	
					} 
				});	
			}
	}; 
	var helper={
			alerter:function(type,message,alertstatus){
				$('.alertmessages').show().removeClass('alert-success').removeClass('alert-danger');
				if(type=="success"){
					$('.alertmessages').addClass('alert-success');	
				}else if(type=="failure"){
					$('.alertmessages').addClass('alert-danger');	
				} 
				$('.alertstatus').text(alertstatus);
				$('.alertmessage').html(message);
				setTimeout(function(){
					$('.alertmessages').hide();					
				},4000);
			},
			boardUpdater:function(confirmationdialog,obj,successstatus,successmessage,failurestatus,failuremessage){
				var labelvalue=$('.boardlink[st=boa]').text();
				$('.boarddropbox').slideUp();				
				if(confirmationdialog!=null){
					if(confirm(confirmationdialog) == true)	{
						var successmsg=model.boardUpdate(obj);
						if(successmsg=="OK"){
							this.alerter("success",successmessage,"Success !");
							view.board(successstatus,model.board(successstatus));
						}else if(successmsg=="PRECONDITION_FAILED"){
							this.alerter("failure",""+labelvalue+" Already Exists","Failure !"); 
							view.board(failurestatus,model.board(failurestatus));
						}
						else if(successmsg=="CONFLICT"){
							this.alerter("failure","Published or Drafted Courses are in this "+labelvalue+", Can't be Trashed","Failure !"); 
							view.board(failurestatus,model.board(failurestatus));
						}

						else{
							this.alerter("failure",failuremessage,"Failure !"); 
							view.board(failurestatus,model.board(failurestatus));
						}
					}
				}
				else{
					var successmsg=model.boardUpdate(obj);
					if(successmsg=="OK"){
						this.alerter("success",successmessage,"Success !");
						view.board(successstatus,model.board(successstatus));
					}else if(successmsg=="PRECONDITION_FAILED"){
						this.alerter("failure",""+labelvalue+" Already Exists","Failure !"); 
						view.board(failurestatus,model.board(failurestatus));
					}else if(successmsg=="CONFLICT"){
						this.alerter("failure","Published or Drafted Courses are in this "+labelvalue+", Can't be Edited","Failure !"); 
						view.board(failurestatus,model.board(failurestatus));
					}else{
						this.alerter("failure",failuremessage,"Failure !"); 
						view.board(failurestatus,model.board(failurestatus));
					}
				}
			},
			categoryUpdater:function(st,confirmationdialog,obj,successstatus,successmessage,failurestatus,failuremessage){
				var labelvalue=$('.boardlink[st=cat]').text();
				$('.boarddropbox').slideUp();
				if(confirmationdialog!=null){
					if(confirm(confirmationdialog) == true)	{
						var successmsg=model.categoryUpdate(obj);
						if(successmsg=="OK"){
							this.alerter("success",successmessage,"Success !");
							var categoryobj=objects.boardforcategory($('#boardforcategory').val(),successstatus);
							view.category(st,model.category(categoryobj),$('#boardforcategory').val());
						}else if(successmsg=="PRECONDITION_FAILED"){
							this.alerter("failure",""+labelvalue+" Already exists","Failure !"); 
							var categoryobj=objects.boardforcategory($('#boardforcategory').val(),failurestatus);
							view.category(st,model.category(categoryobj),$('#boardforcategory').val());
						}
						else if(successmsg=="CONFLICT"){
							this.alerter("failure","Published or Drafted Courses are in this "+labelvalue+", Can't be Trashed","Failure !"); 
							view.board(failurestatus,model.board(failurestatus));
						}						
						else{
							this.alerter("failure",failuremessage,"Failure !"); 
							var categoryobj=objects.boardforcategory($('#boardforcategory').val(),failurestatus);
							view.category(st,model.category(categoryobj),$('#boardforcategory').val());
						}
					}}else{
						var successmsg=model.categoryUpdate(obj);
						if(successmsg=="OK"){
							this.alerter("success",successmessage,"Success !");
							var categoryobj=objects.boardforcategory($('#boardforcategory').val(),successstatus);
							view.category(st,model.category(categoryobj),$('#boardforcategory').val());							
						}else if(successmsg=="PRECONDITION_FAILED"){
							this.alerter("failure",""+labelvalue+" Already exists","Failure !"); 
							var categoryobj=objects.boardforcategory($('#boardforcategory').val(),failurestatus);
							view.category(st,model.category(categoryobj),$('#boardforcategory').val());
						}else if(successmsg=="CONFLICT"){
							this.alerter("failure","Published or Drafted Courses are in this "+labelvalue+", Can't be Edited","Failure !"); 
							view.board(failurestatus,model.board(failurestatus));
						}else{
							this.alerter("failure",failuremessage,"Failure !"); 
							var categoryobj=objects.boardforcategory($('#boardforcategory').val(),failurestatus);
							view.category(st,model.category(categoryobj),$('#boardforcategory').val());
						}
					}
			},
			mediumUpdater:function(confirmationdialog,obj,successstatus,successmessage,failurestatus,failuremessage){
				var labelvalue=$('.boardlink[st=med]').text();
				$('.boarddropbox').slideUp();
				if(confirmationdialog!=null){
					if(confirm(confirmationdialog) == true)	{
						var successmsg=model.mediumUpdate(obj);
						if(successmsg=="OK"){
							this.alerter("success",successmessage,"Success !");
							view.medium(successstatus,model.medium(successstatus));
						}else if(successmsg=="PRECONDITION_FAILED"){
							this.alerter("failure",""+labelvalue+" Already exists","Failure !"); 
							view.medium(failurestatus,model.medium(failurestatus));
						}						
						else if(successmsg=="CONFLICT"){
							this.alerter("failure","Published or Drafted Courses are in this "+labelvalue+", Can't be Trashed","Failure !"); 
							view.board(failurestatus,model.board(failurestatus));
						}						
						else{
							this.alerter("failure",failuremessage,"Failure !"); 
							view.medium(failurestatus,model.medium(failurestatus));
						}
					}}else{
						var successmsg=model.mediumUpdate(obj);
						if(successmsg=="OK"){
							this.alerter("success",successmessage,"Success !");
							view.medium(successstatus,model.medium(successstatus));
						}else if(successmsg=="PRECONDITION_FAILED"){
							this.alerter("failure",""+labelvalue+" Already exists","Failure !"); 
							view.medium(failurestatus,model.medium(failurestatus));
						}else if(successmsg=="CONFLICT"){
							this.alerter("failure","Published or Drafted Courses are in this "+labelvalue+", Can't be Edited","Failure !"); 
							view.board(failurestatus,model.board(failurestatus));
						}else{
							this.alerter("failure",failuremessage,"Failure !"); 
							view.medium(failurestatus,model.medium(failurestatus));
						}
					}
			},
			standardLevelUpdater:function(confirmationdialog,obj,successstatus,successmessage,failurestatus,failuremessage){
				var labelvalue=$('.boardlink[st=sta]').text();
				$('.boarddropbox').slideUp();
				if(confirmationdialog!=null){
					if(confirm(confirmationdialog) == true)	{
						var successmsg=model.standardlevelUpdate(obj);
						if(successmsg=="OK"){
							this.alerter("success",successmessage,"Success !");
							view.standardlevel(successstatus,model.standardlevel(successstatus));
						}else if(successmsg=="PRECONDITION_FAILED"){
							this.alerter("failure",""+labelvalue+" Already exists","Failure !"); 
							view.standardlevel(failurestatus,model.standardlevel(failurestatus));
						}else if(successmsg=="CONFLICT"){
							this.alerter("failure","Published or Drafted Courses are in this "+labelvalue+", Can't be Trashed","Failure !"); 
							view.board(failurestatus,model.board(failurestatus));
						}						
						else{
							this.alerter("failure",failuremessage,"Failure !"); 
							view.standardlevel(failurestatus,model.standardlevel(failurestatus));
						}
					}
				}else{
					var successmsg=model.standardlevelUpdate(obj);
					if(successmsg=="OK"){
						this.alerter("success",successmessage,"Success !");
						view.standardlevel(successstatus,model.standardlevel(successstatus));
					}else if(successmsg=="PRECONDITION_FAILED"){
						this.alerter("failure",""+labelvalue+" Already exists","Failure !"); 
						view.standardlevel(failurestatus,model.standardlevel(failurestatus));
					}else if(successmsg=="CONFLICT"){
						this.alerter("failure","Published or Drafted Courses are in this "+labelvalue+", Can't be Edited","Failure !"); 
						view.board(failurestatus,model.board(failurestatus));
					}else{
						this.alerter("failure",failuremessage,"Failure !"); 
						view.standardlevel(failurestatus,model.standardlevel(failurestatus));
					}
				}
			},
			linkSelector:function(linktype){
				switch (linktype){
				case 'boa': 
					view.board("A",model.board("A"));
					break;
				case 'cat':
					view.boardcategory("A",model.board("A"));
					break;
				case 'med': 
					view.medium("A",model.medium("A"));
					break;
				case 'sta': 
					view.standardlevel("A",model.standardlevel("A"));
					break;
				}
			}
	};
	return {
		init: function () {
			initialize();
		}
	}; 
	function initialize(){
		view.coursepartLinks();		
	};	
});