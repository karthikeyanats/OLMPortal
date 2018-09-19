package com.igrandee.product.ecloud.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.product.ecloud.helper.JsonHelper;

public class MasterActionSupport extends AbstractIGActionSupport
{

	private static final long serialVersionUID = -8376329941478171254L;

	private JsonHelper jsonHelper;

	public JsonHelper getJsonHelper() {
		return jsonHelper;
	}

	public void setJsonHelper(JsonHelper jsonHelper) {
		this.jsonHelper = jsonHelper;
	}
	
	public JsonHelper checkobj()
	{
		if(jsonHelper == null)
			return jsonHelper = new JsonHelper();
		else
			return jsonHelper;	
		
	}
	
	/**
	 * @author seenivasagan_p
	 * Method to get the json structure from node
	 * @param nodename, nodelist
	 * @return converted node string
	 */
	public String ConvertRootnode(String nodename,Object nodelist){
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectWriter writer = objectMapper.writer();
		String entity = null;
		try {
			entity = writer.withRootName(nodename).writeValueAsString(nodelist);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return entity;
	}
}
