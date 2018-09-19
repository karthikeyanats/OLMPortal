package com.igrandee.product.ecloud.action.course;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.igrandee.product.ecloud.action.MasterActionSupport;
import com.igrandee.product.ecloud.model.Courselecture;
import com.igrandee.product.ecloud.model.Note;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.service.course.CourseLectureService;
import com.igrandee.product.ecloud.service.course.NotesService;

public class NotesAction extends MasterActionSupport{

	private static final long serialVersionUID = 1L;
	private static Logger NotesActionlogger = Logger.getLogger(NotesAction.class);
	
	private static ResourceBundle resourceBundle;
	
	@Inject
	NotesService NotesServiceobj;
	
	@Autowired
	private Note Noteobj;
	
	@Autowired
	private Courselecture Courselectureobj;
	
	@Inject
	CourseLectureService CourseLectureServiceobj;
	
	@Autowired
	private Orgperson Orgpersonobj;
	
	private String courselectureid;
	private String notesdescription;
	private String notesid;
	private String notetype;
	private String notestatus;
	
	/**
	 * @author seenivasagan_p
	 * Method to get notes for particular lecture
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value="/loadLectureNotes")
 	public void loadLectureNotes(){
		try{
			List<?> NotesList=NotesServiceobj.loadNotes(Integer.parseInt(courselectureid),Integer.parseInt(getAttribute("orgpersonid")));
			List encodedNotesList=new ArrayList();
			Iterator<?> iterator=NotesList.iterator();
			while(iterator.hasNext()){
				Map<String, String> map=(Map<String, String>) iterator.next();
				map.put("notesdescription", URLEncoder.encode(map.get("notesdescription"),"UTF-8"));
				encodedNotesList.add(map);
			}
			checkobj().toJsonResponse(encodedNotesList);
		}
		catch(Exception e){
			if(NotesActionlogger.isInfoEnabled()){
				NotesActionlogger.error("error in loadLectureNotes() in NotesAction",e);
			}
		}  
	}
	/**
	 * @author seenivasagan_p
	 * Method to get notes for particular lecture
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value="/loadNotesDescription")
 	public void loadNotesDescription(){
		try{
			List<?> NotesList=NotesServiceobj.loadNoteDescription(Integer.parseInt(notesid));
			List encodedNotesList=new ArrayList();
			Iterator<?> iterator=NotesList.iterator();
			while(iterator.hasNext()){
				Map<String, String> map=(Map<String, String>) iterator.next();
				map.put("notesdescription", URLEncoder.encode(map.get("notesdescription"),"UTF-8"));
				encodedNotesList.add(map);
			}
			checkobj().toJsonResponse(encodedNotesList);
		}
		catch(Exception e){
			if(NotesActionlogger.isInfoEnabled()){
				NotesActionlogger.error("error in loadNotesDescription() in NotesAction",e);
			}
		}  
	}
	
	@Action(value="/updateNotesDescription")
 	public void updateNotesDescription(){
		try{
			String NotesList=NotesServiceobj.saveUpdatedNoteDescription(Integer.parseInt(notesid),URLDecoder.decode(notesdescription));
			updateNewNotes(notesid,courselectureid);
			checkobj().toJsonString(NotesList);
		}
		catch(Exception e){
			if(NotesActionlogger.isInfoEnabled()){
				NotesActionlogger.error("error in updateNotesDescription() in NotesAction",e);
			}
		}  
	}
	
	
	/**
	 * @author seenivasagan_p
	 * Method Used to write the content to text file
	 */
	@Action(value="/writeNotes")
	public void writeNotes(){
		String filetobeDownloaded="";
		try	{
			List<?> allNotesList=NotesServiceobj.loadNotes(Integer.parseInt(courselectureid),Integer.parseInt(getAttribute("orgpersonid")));
			resourceBundle	= ResourceBundle.getBundle("serversetup");
			String serverPath=resourceBundle.getString("serverpath");
			String filepath=returnFileName(courselectureid).substring(returnFileName(courselectureid).lastIndexOf("/")+1);
			String filename=filepath+".txt";
			String foldertobecreated=serverPath+returnFileName(courselectureid)+"/Notes/"+getAttribute("orgpersonid")+"/";
			filetobeDownloaded=returnFileName(courselectureid)+"/Notes/"+getAttribute("orgpersonid")+"/"+filename;
			String filetobewritten=foldertobecreated+filename;
			File f = new File(foldertobecreated);
			if (!f.exists()) {
				f.mkdirs();
			}
			File file = new File(filetobewritten);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			String notesdescriptioncombined="";
			for(int i=0;i<allNotesList.size();i++){
				Map allNotesListMap=(Map) allNotesList.get(i);
				notesdescriptioncombined=""+allNotesListMap.get("notesdescription");
				bw.write(notesdescriptioncombined);
				bw.flush();
				bw.newLine();
			}
			bw.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		checkobj().toJsonString(filetobeDownloaded);
	}

	private String returnFileName(String courselectureid){
		try{
			List<?> contentFolder=CourseLectureServiceobj.loadLecturePath(Integer.parseInt(courselectureid));
			String category="";
			String course="";
			String section="";
			String lecture="";
			for(int i=0;i<contentFolder.size();i++){
				Map FoldertitleMap=(Map) contentFolder.get(i);
				category=""+FoldertitleMap.get("coursecategoryid");
				course=""+FoldertitleMap.get("courseid");
				section=""+FoldertitleMap.get("coursesectionid");
				lecture=""+FoldertitleMap.get("courselectureid");			
			}
			String folderPath=getAttribute("organizationid")+"/"+getAttribute("orgpersonid")+"/"+category+"/"+course+"/"+section+"/"+lecture;
			folderPath=folderPath.replaceAll(" ", "");
			return folderPath;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @author seenivasagan_p
	 * Method Used to save the notes
	 * @param void
	 * @return response
	 */
	@Action(value="/saveNotes")
	public void saveNotes(){
		try	{
			Noteobj.setNotesdescription(URLDecoder.decode(notesdescription));
			Noteobj.setCreateddate(new Timestamp(new Date().getTime()));
 			//Status R denoted Private Notes
 			//Status U denoted Public Notes
 			Noteobj.setNotesstatus("R");
 			Noteobj.setNotetype("N");
			Courselectureobj.setCourselectureid(Integer.parseInt(courselectureid));
			Orgpersonobj.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
			Noteobj.setOrgperson(Orgpersonobj);
			Noteobj.setCourselecture(Courselectureobj);
			String savedNotesResult=NotesServiceobj.save(Noteobj).toString();
			updateNewNotes(savedNotesResult,courselectureid);
			checkobj().toJsonString("ok");
		}
		catch(Exception e){
			if(NotesActionlogger.isInfoEnabled()){
				NotesActionlogger.error("error in saveNotes() in NotesAction",e);
			}
		}
	}
	/**
	 * @author seenivasagan_p
	 * Method Used to save the notes
	 * @param void
	 * @return response
	 */
	@Action(value="/deleteNotes")
	public void deleteNotes(){
		try	{
			
			String deletedNotesResult=NotesServiceobj.updateNoteStatus(Integer.parseInt(notesid),"T");
			checkobj().toJsonString(deletedNotesResult);
		}
		catch(Exception e){
			if(NotesActionlogger.isInfoEnabled()){
				NotesActionlogger.error("error in saveNotes() in NotesAction",e);
			}
		}
	}
	
	
	
	private void updateNewNotes(String savedNotesResult,String courselectureid){
		try{
			
			resourceBundle	= ResourceBundle.getBundle("serversetup");
			String serverPath=resourceBundle.getString("serverpath");
			
			List<?> notesDescriptions=NotesServiceobj.loadNoteDescription(Integer.parseInt(savedNotesResult));
						
			String rootpath=returnFileName(courselectureid);
			String filname=rootpath.substring(rootpath.lastIndexOf("/")+1)+"-"+savedNotesResult+".txt";
			String foldername=serverPath+rootpath+"/Notes/"+getAttribute("orgpersonid")+"/"+savedNotesResult+"/";
			String filetobewritten=foldername+filname;
			File f = new File(foldername);
			if (!f.exists()) {
				f.mkdirs();
			}
			File file = new File(filetobewritten);
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			String notesdescriptioncombined="";
			for(int i=0;i<notesDescriptions.size();i++){
				Map allNotesListMap=(Map) notesDescriptions.get(i);
				notesdescriptioncombined=""+allNotesListMap.get("notesdescription");
				bw.write(notesdescriptioncombined);
				bw.flush();
				bw.newLine();
			}
			bw.close();
			NotesServiceobj.saveNotePath(Integer.parseInt(savedNotesResult),rootpath+"/Notes/"+getAttribute("orgpersonid")+"/"+savedNotesResult+"/"+filname);
			
		}
		catch(Exception e){
			if(NotesActionlogger.isInfoEnabled()){
				NotesActionlogger.error("error in updateNewNotes() in NotesAction",e);
			}
		}
		
	}

	@Action(value="/getNotesList")
	public void loadNotesWithStatus(){
		try{
			List<?> NotesList = null;
			if(notestatus.equalsIgnoreCase("R")){
				NotesList=NotesServiceobj.loadNotesByStatus(Integer.parseInt(courselectureid),getAttribute("orgpersonid"),notetype,notestatus);
			}else{
				NotesList=NotesServiceobj.loadNotesByStatus(Integer.parseInt(courselectureid),null,notetype,notestatus);
			}
			if(NotesList.size()!=0){
				List encodedNotesList=new ArrayList();
				Iterator<?> iterator=NotesList.iterator();
				while(iterator.hasNext()){
					Map<String, String> map=(Map<String, String>) iterator.next();
					map.put("notesdescription", URLEncoder.encode(map.get("notesdescription"),"UTF-8"));
					encodedNotesList.add(map);
				}
				checkobj().toJsonResponse(encodedNotesList);	
			}else{
				checkobj().toJsonString("nocontent");
			}
		}
		catch(Exception e){
			if(NotesActionlogger.isInfoEnabled()){
				NotesActionlogger.error("error in loadLectureNotes() in NotesAction",e);
			}
			checkobj().toJsonString("error");
		}
	}
	
	@Action(value="/updateNoteStatus")
	public void updateNoteStatus(){
		try	{
			String deletedNotesResult=NotesServiceobj.updateNoteStatus(Integer.parseInt(notesid),notestatus);
			checkobj().toJsonString(deletedNotesResult);
		}
		catch(Exception e){
			if(NotesActionlogger.isInfoEnabled()){
				NotesActionlogger.error("error in updateNoteStatus() in NotesAction",e);
			}
			checkobj().toJsonString("0");
		}
	}
	
	
	/*getters and setters*/
	public String getCourselectureid() {
		return courselectureid;
	}
	public void setCourselectureid(String courselectureid) {
		this.courselectureid = courselectureid;
	}

	public String getNotesdescription() {
		return notesdescription;
	}

	public void setNotesdescription(String notesdescription) {
		this.notesdescription = notesdescription;
	}
	public String getNotesid() {
		return notesid;
	}
	public void setNotesid(String notesid) {
		this.notesid = notesid;
	}
	public String getNotetype() {
		return notetype;
	}
	public void setNotetype(String notetype) {
		this.notetype = notetype;
	}
	public String getNotestatus() {
		return notestatus;
	}
	public void setNotestatus(String notestatus) {
		this.notestatus = notestatus;
	}
	

}
