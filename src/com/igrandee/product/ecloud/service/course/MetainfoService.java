package com.igrandee.product.ecloud.service.course;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Metainfo;

@Service
@Named
@Scope("prototype")
public class MetainfoService extends GenericEntityService<Metainfo, Serializable>{
	private static Logger Metainfologger = Logger.getLogger(Metainfo.class);

	@Override
	protected Class<Metainfo> entityClass() {
		// TODO Auto-generated method stub
		return null;
	} 

	public List<?> loadreference(int metainfoid) {
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(Metainfo.class)
					.add(Restrictions.eq("this.metainfoid",metainfoid))
					 .setProjection(Projections.projectionList()
							.add(Projections.property("this.reference").as("reference"))
							.add(Projections.property("this.maps").as("maps"))
							.add(Projections.property("this.tables").as("tables"))
							.add(Projections.property("this.diagrams").as("diagrams"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(Metainfologger.isInfoEnabled())	{
				Metainfologger.error("error in loadreference() in MetainfoService",e);
			}
		}
		return query;
	}
	
}
