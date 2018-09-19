package com.igrandee.product.ecloud.service.Pages.admin;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Metainfodescription;

@Service
@Named
@Scope("prototype")
public class LectureMetaInfoService extends GenericEntityService<Metainfodescription, Integer>{

	@Override
	protected Class<Metainfodescription> entityClass() {
		return Metainfodescription.class;
	}

	public List<?> getMetaDescription(String metainfoid,String descriptiontype){
		List<?> descriptionsList =null;
		try	{
			descriptionsList = getCurrentSession().createCriteria(Metainfodescription.class)
					
					.createAlias("this.metainfo", "metainfo")
					
					.add(Restrictions.eq("metainfo.metainfoid",Integer.parseInt(metainfoid)))
					.add(Restrictions.eq("this.descriptiontype",descriptiontype))
					.add(Restrictions.eq("this.status","A"))
					
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.id").as("descid"))
							.add(Projections.property("this.description").as("description"))
							.add(Projections.property("this.originalfilename").as("originalfilename"))
							.add(Projections.property("this.descriptiontype").as("descriptiontype")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}
	
	public List<?> getLectureMetaDescription(String lectureid,String descriptiontype){
		List<?> descriptionsList =null;
		try	{
			descriptionsList = getCurrentSession().createCriteria(Metainfodescription.class)
					
					.createAlias("this.metainfo", "metainfo")
					.createAlias("metainfo.courselecture", "courselecture")
					
					.add(Restrictions.eq("this.descriptiontype",descriptiontype))
					.add(Restrictions.eq("courselecture.courselectureid",lectureid))
					.add(Restrictions.eq("this.status","A"))
					
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.id").as("descid"))
							.add(Projections.property("this.description").as("description"))
							.add(Projections.property("this.originalfilename").as("originalfilename"))
							.add(Projections.property("this.descriptiontype").as("descriptiontype")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return descriptionsList;
	}
	
	public String updateMetaDescription(String metainfoid,String description){
		try{
			String hql = "UPDATE Metainfodescription set description = :description "  + 
					"WHERE id = :id";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("description", description);
			query.setParameter("id", Integer.parseInt(metainfoid));
			Integer result = query.executeUpdate();
			if(result.SIZE>0){
				return "OK";
			}
			else{
				return "NOTOK";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "NOTOK";
		}
	}
	
	public String deleteMetaDescription(String descid){
		try{
			String hql = "UPDATE Metainfodescription set status = :status "  + 
					"WHERE id = :id";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("status", "D");
			query.setParameter("id", Integer.parseInt(descid));
			Integer result = query.executeUpdate();
			if(result.SIZE>0){
				return "OK";
			}
			else{
				return "NOTOK";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "NOTOK";
		}
	}
	
	public List<?> loadMataInfoPath(String metainfoid) {
		List<?> query=null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Metainfo.class)
					
					.createAlias("this.courselecture", "courselecture")
					.createAlias("courselecture.coursesection", "coursesection")
					.createAlias("coursesection.course", "course")
					.createAlias("course.coursecategory", "coursecategory")
					.add(Restrictions.eq("this.metainfoid",Integer.parseInt(metainfoid)))
					.setProjection(Projections.projectionList()
							.add(Projections.property("coursecategory.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("coursesection.coursesectionid").as("coursesectionid"))
							.add(Projections.property("courselecture.courselectureid").as("courselectureid"))
							)
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			e.printStackTrace();
		}
		return query;
	}
	
}
