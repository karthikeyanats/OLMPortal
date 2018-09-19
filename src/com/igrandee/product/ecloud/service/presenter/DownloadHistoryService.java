package com.igrandee.product.ecloud.service.presenter;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.PresenterappDownloadhistory;

@Service
@Scope("prototype")
public class DownloadHistoryService extends GenericEntityService<PresenterappDownloadhistory, Integer> {
		
	@Override
	protected Class<PresenterappDownloadhistory> entityClass() {
 		return PresenterappDownloadhistory.class;
 		
	}
	
	public List<?> getDownloadHistory(){
		
		return getCurrentSession().createCriteria(PresenterappDownloadhistory.class)
				.createAlias("presenterappdetail", "presenterappdetail")
				.createAlias("presenterappdetail.presentertype", "presentertype")				
				.createAlias("orgperson", "orgperson")
				.createAlias("orgperson.personid", "person")
				.createAlias("orgperson.organizationid","org")
				.addOrder(Order.desc("dateofdownload"))
				.setProjection(Projections.projectionList()
						.add(Projections.property("id").as("id"))
						.add(Projections.property("dateofdownload").as("dateofdownload"))
						.add(Projections.property("ipaddress").as("ipaddress"))
						.add(Projections.property("person.firstName").as("name"))
						.add(Projections.property("org.orgname").as("orgname"))
						.add(Projections.property("presentertype.typename").as("typename")))
						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();		
	}
	
}
