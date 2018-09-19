package com.igrandee.product.ecloud.helper;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.igrandee.core.login.util.AbstractIGActionSupport;


public class JsonHelper extends AbstractIGActionSupport{

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(JsonHelper.class);
	private  Gson json=new Gson();
	private  HttpServletResponse response=ServletActionContext.getResponse();
	
	public  HttpServletResponse toJsonResponse(List<?> inputList) {
				
		    String jsonString="";
			try {
				jsonString=this.toJsonString(inputList);
				response.setContentType("text/json"); 
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write(jsonString);
			} catch (Exception e) {
				logger.error("", e);
			}
			return response;
	}
	
	public   HttpServletResponse toJsonResponse(String jsonString) {
		   
		try {
			response.setContentType("text/json");
			response.setHeader("Cache-Control", "no-cache");
			response.getWriter().write(jsonString);
		}catch (Exception e) {
			logger.error("", e);
		}
		return response;
}

	public   String toJsonString(List<?> list) {
		
			if(!isNullorEmpty(list))
			{
				try {
					
					json = new GsonBuilder().setDateFormat("dd-MMM-yyyy").create();
					return json.toJson(list);
						
				} catch (Exception e) {
					logger.error("", e);
				}			
			}			
			return json.toJson("false");
	}
		
	public   boolean isNullorEmpty(List<?> list)
	{
	        if(list!=null)
	        {
	        	if(list.size()>0)
	        	{
	        		return false;
	        	}
	        }
			return true;
	}

	public HttpServletResponse toJsonString(String jsonString){
		try {
		response.setContentType("text/json");
		response.setHeader("Cache-Control", "no-cache");
	 	response.getWriter().write(json.toJson(jsonString));
		}
		 catch (Exception e) {
				logger.error("", e);
			}
		return null;
	}
	
	
}