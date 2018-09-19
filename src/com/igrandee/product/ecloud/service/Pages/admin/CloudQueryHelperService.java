package com.igrandee.product.ecloud.service.Pages.admin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Coursecategory;

@Service
@Named
@Scope("prototype")
public class CloudQueryHelperService extends GenericEntityService<Coursecategory, Integer>{

	private static Logger CloudQueryHelperServicelogger = Logger.getLogger(CloudQueryHelperService.class);
	@Override
	protected Class<Coursecategory> entityClass() {
		// TODO Auto-generated method stub
		return Coursecategory.class;
	}
	
	public List<?> getSelectQueryResultList(String query){
		List<?> selectQueryResultListList = null;
		try{
			selectQueryResultListList=getCurrentSession().createSQLQuery(query)
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
			CloudQueryHelperServicelogger.error("error in getSelectQueryResultList in CloudQueryHelperService "+e);
			selectQueryResultListList = EcloudExceptionBuilder(e);
		}
		return selectQueryResultListList;
	}
	
	private List<?> EcloudExceptionBuilder(Exception e) {
		// TODO Auto-generated method stub
		List al = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("message", e.getMessage());
		map.put("rootcausemessage", ExceptionUtils.getRootCauseMessage(e));
		al.add(map);
		return al;
	}

	public int getUpdateQueryResultList(String query){
		int updateQueryResultListList = 0;
		try{
			updateQueryResultListList=getCurrentSession().createSQLQuery(query)
					.executeUpdate();
		}
		catch(Exception e){
			CloudQueryHelperServicelogger.error("error in getUpdateQueryResultList in CloudQueryHelperService "+e);
			e.printStackTrace();
		}
		return updateQueryResultListList;
	}
	
}
