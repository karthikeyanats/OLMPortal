package com.igrandee.product.ecloud.viewmodel.download;

import java.util.List;

public class DownloadDetialsViewModel {
    String presenterid;
	String applicationname;
	String path;
	String helpurl;
	String presentertypeid [];
	String workingduration [];
	String logincount [];
	String price[];
	List<?> typeobj;
	String typeid;
	char status;	
		
	public String getPresenterid() {
		return presenterid;
	}
	public void setPresenterid(String presenterid) {
		this.presenterid = presenterid;
	}
	public String[] getPresentertypeid() {
		return presentertypeid;
	}
	public void setPresentertypeid(String[] presentertypeid) {
		this.presentertypeid = presentertypeid;
	}
	public String[] getWorkingduration() {
		return workingduration;
	}
	public void setWorkingduration(String[] workingduration) {
		this.workingduration = workingduration;
	}
	public String[] getLogincount() {
		return logincount;
	}
	public void setLogincount(String[] logincount) {
		this.logincount = logincount;
	}
	public String getApplicationname() {
		return applicationname;
	}
	public void setApplicationname(String applicationname) {
		this.applicationname = applicationname;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getHelpurl() {
		return helpurl;
	}
	public void setHelpurl(String helpurl) {
		this.helpurl = helpurl;
	}
	public String[] getPrice(){
		return price;
	}
	public void setPrice(String[] price){
		this.price=price;
	}
	public List<?> getTypeobj() {
		return typeobj;
	}
	public void setTypeobj(List<?> typeobj) {
		this.typeobj = typeobj;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	
	
	
}
