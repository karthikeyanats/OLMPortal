package com.igrandee.product.ecloud.service.royalty;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.royalty.Requestlimit;

@Service
@Scope("prototype")
public class RequestLimitService extends GenericEntityService<Requestlimit, Integer>{

	private static Logger requestLimitServiceLogger = Logger.getLogger(RequestLimitService.class);
	
	@Override
	protected Class<Requestlimit> entityClass() {
		return Requestlimit.class;
	}

	public List<?> getRequestLimit(){
		List<?> requestLimitList = null;
		try{
			requestLimitList= getCurrentSession().createCriteria(Requestlimit.class)
					 .createAlias("createdby", "orgperson")
						.add(Restrictions.eq("requestlimitstatus", 'A'))
						.setProjection(Projections.projectionList()
								.add(Projections.property("id").as("id"))
								.add(Projections.property("orgperson.orgpersonid").as("createdby"))
								.add(Projections.property("dateofcreation").as("dateofcreation"))
								.add(Projections.property("minimumamount").as("minimumamount"))
								.add(Projections.property("requestlimitstatus").as("requestlimitstatus")))
								.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(requestLimitServiceLogger.isInfoEnabled()){
				requestLimitServiceLogger.info("error in getRequestLimit() in RequestLimitService"+e);
			}
		}
		return requestLimitList;
	}
	
	public int getRequestLimitId(){
		try{
		return Integer.parseInt(getCurrentSession().createCriteria(Requestlimit.class)
								.add(Restrictions.eq("requestlimitstatus", 'A'))
									.setProjection(Projections.projectionList()
										.add(Projections.property("id").as("id")))
										.setResultTransformer(Criteria.PROJECTION).uniqueResult().toString());
		}
		catch(Exception exception){
			return 0;
		}
	}
	
}
