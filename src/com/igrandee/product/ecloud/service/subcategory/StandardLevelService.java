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
import com.igrandee.product.ecloud.model.StandardLevel;

@Service
@Scope("prototype")
@Named
public class StandardLevelService extends GenericEntityService<StandardLevel, Integer> {

	@Override
	protected Class<StandardLevel> entityClass() {
		// TODO Auto-generated method stub
		return null;
	}
	public List<?> getStandardLevelList(String status){
		try {
			return getCurrentSession().createCriteria(StandardLevel.class)
					.add(Restrictions.eq("this.standardlevelstatus", status.charAt(0)))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.standardlevelid").as("standardlevelid"))
							.add(Projections.property("this.standardlevelname").as("standardlevelname"))
							.add(Projections.property("this.standardlevelstatus").as("standardlevelstatus"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;	
		}
	}
	
	public List<?> getStandardavailability(String standardlevelname){
		try {
			return getCurrentSession().createCriteria(StandardLevel.class)

					.add(Restrictions.in("this.standardlevelstatus",new Character[] {'A','D'}))
					.add(Restrictions.like("this.standardlevelname",standardlevelname ).ignoreCase())
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.standardlevelid").as("standardlevelid"))
							).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isCoursesPublishedorDrafted(int standardid){
		List<?> courseCategoryList=getCurrentSession().createCriteria(Coursecategory.class)
										.createAlias("courses", "courses")
										.createAlias("courses.standardlevel", "standardlevel")
										.add(Restrictions.in("courses.coursestatus", new String[]{"A","P","T"}))
										.add(Restrictions.eq("standardlevel.standardlevelid", standardid))
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
