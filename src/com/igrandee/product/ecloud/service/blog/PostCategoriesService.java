package com.igrandee.product.ecloud.service.blog;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.blog.Postcategory;

@Named
@Service
@Scope("prototype")
public class PostCategoriesService extends GenericEntityService<Postcategory, Integer> {

	
	private final Logger postCategoryServiceLogger = Logger.getLogger(Postcategory.class);

	@Override
	protected Class<Postcategory> entityClass() { 
		return Postcategory.class;
	}
	
	/**
	 * 
	 * @param postId
	 * @param categoryId
	 * @return returnInteger Integer
	 */
	public Integer getPostCategoryId(Integer postId, Integer categoryId){
	 
		List<?> queryList = null;
		Integer returnInteger = 0;
		Criteria query = null;
		try {
			  
				query = getCurrentSession().createCriteria(Postcategory.class);
				query.createAlias("this.category", "category")
			     	 .createAlias("this.post", "post");
				query.add(Restrictions.eq("category.id", categoryId))
				 	 .add(Restrictions.eq("post.id", postId))
				 	 .add(Restrictions.eq("post.status", "A"))
				 	 .add(Restrictions.eq("category.status", "A"))
				 	 .add(Restrictions.eq("this.status", "A"));
				query.setProjection(Projections.projectionList().add(Projections.property("this.id").as("postcateogryid")));
			queryList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
			 
			if(! queryList.isEmpty() & queryList.size() == 1){
 				Iterator<?> queryListIterator = queryList.listIterator();
				while (queryListIterator.hasNext()) {
					Map<?,?> object =  (Map<?, ?>) queryListIterator.next();
					Object val = object.get("postcateogryid");
					if(val !=null){
 						returnInteger =  Integer.parseInt(""+object.get("postcateogryid"));
 					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			postCategoryServiceLogger.error("error in getPostCategoryId() for PostCategoriesService",e);
		}
		return returnInteger;
	}

	/**
	 * @param postId
	 * @param categoryId
	 * @return
	 */
	public Boolean isPostAvailable(Integer postId, Integer categoryId) {
		 boolean returnValue = false;
		 try {
			if( postId > 0  &&  postId != null && categoryId >0 & categoryId != null){
 				Criteria query = null;
				List<?> queryList = null;
				Integer returnInteger = 0;
				query = getCurrentSession().createCriteria(Postcategory.class);
				query.createAlias("this.category", "category")
		     	 	 .createAlias("this.post", "post");
				query.add(Restrictions.eq("category.id", categoryId))
				 	 .add(Restrictions.eq("post.id", postId))
				 	 .add(Restrictions.eq("post.status", "A"))
				 	 .add(Restrictions.eq("category.status", "A"))
				 	 .add(Restrictions.eq("this.status", "A"));
				query.setProjection(Projections.projectionList().add(Projections.property("this.id").as("postcateogryid")));
				queryList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
				if(! queryList.isEmpty() & queryList.size() == 1){
 					Iterator<?> queryListIterator = queryList.listIterator();
					while (queryListIterator.hasNext()) {
						Map<?,?> object =  (Map<?, ?>) queryListIterator.next();
						Object val = object.get("postcateogryid");
						if(val !=null){
 							returnInteger =  Integer.parseInt(""+object.get("postcateogryid"));
 						}
						if(returnInteger > 0){
 							returnValue = true;
						}
					}
				}
			}
		} catch (Exception e) {
			 postCategoryServiceLogger.error("error in isPostAvailable() for PostCategoriesService",e);
		}
		return returnValue;
	}

	/**
	 * @param id
	 */
	public void deleteDepsOf(int id) {
		Query query = null;
		 try {
			query = getCurrentSession().createQuery("delete Postcategory where post_id = :postid");
			query.setParameter("postid", id);
			query.executeUpdate();  
		} catch (Exception e) {
		    postCategoryServiceLogger.error("error in deleteDepsOf() for PostCategoriesService",e);
		}		
	}	  

}
