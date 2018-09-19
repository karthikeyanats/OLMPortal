package com.igrandee.product.ecloud.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import com.igrandee.core.login.util.AbstractIGActionSupport;

public class MasterRegAction extends AbstractIGActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Action(value = "forwardToMasterreg",results = { @Result(name = "MasterReg", location = "app/course/masteradminreg.jsp") })
	public String masterRegMtd(){
		return "MasterReg";
	}
}
