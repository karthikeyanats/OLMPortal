package com.igrandee.product.ecloud.service.subcategory;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Coursecategory;
import com.igrandee.product.ecloud.model.Medium;

@Service
@Scope("prototype")
@Named
public class MediumService extends GenericEntityService<Medium, Integer>{

	@Override
	protected Class<Medium> entityClass() {
		// TODO Auto-generated method stub
		return null;
	}
	public List<?> getMediumList(String status){
		try {
			return getCurrentSession().createCriteria(Medium.class)
					.add(Restrictions.eq("this.mediumstatus", status.charAt(0)))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.mediumid").as("mediumid"))
							.add(Projections.property("this.mediumname").as("mediumname"))
							.add(Projections.property("this.mediumstatus").as("mediumstatus"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<?> getMediumavailability(String mediumname){
		try {
			return getCurrentSession().createCriteria(Medium.class)
					.add(Restrictions.in("this.mediumstatus",new Character[] {'A','D'}))
					.add(Restrictions.like("this.mediumname",mediumname ).ignoreCase())
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.mediumid").as("mediumid"))
							).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isCoursesPublishedorDrafted(int mediumid){
		List<?> courseCategoryList=getCurrentSession().createCriteria(Coursecategory.class)
										.createAlias("courses", "courses")
										.createAlias("courses.medium", "medium")
										.add(Restrictions.in("courses.coursestatus", new String[]{"A","P","T"}))
										.add(Restrictions.eq("medium.mediumid", mediumid))
										.setProjection(Projections.projectionList()
												.add(Projections.distinct(Projections.property("coursecategoryid")))).list();
						if(courseCategoryList.size()!=0){
							return true;
						}
						else{
							return false;
						}		
	}
	
}
