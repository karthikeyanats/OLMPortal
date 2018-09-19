/*
 * The contents of this file are subject to the terms
 * of the i-Grandee Software Technologies 
 * Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 * 
 * You can obtain a copy of the license at
 * http://www.igrandee.com/licenses
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * Copyright (c) 2014 i-Grandee Software Technologies. All rights reserved.
 
 * Redistribution and use in source and binary forms, with or without
 * modification, are strictly prohibited, And permitted only when the 
 * following conditions are met,
 * 
 * 	 - On acquired the legal permission from i-Grandee corporate office and 
 * 	   following the below listed commitments.
 * 
 * 	 - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Igrandee or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *     
 */
/*
 * ecloud-blogutil 
 * com.igrandee.products.ecloud.blog.ecloud.blogutil.service
 * PostTagsService.java
 * Created Jul 18, 2014 5:04:25 PM
 * by balajichander_r
 */
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
import com.igrandee.product.ecloud.model.blog.Posttag;

/**
 * @author balajichander_r
 *
 */
@Named
@Service
@Scope("prototype")
public class PostTagsService extends GenericEntityService<Posttag, Integer>{

	private final Logger postTagsServiceLogger = Logger.getLogger(Posttag.class);
 
	@Override
	protected Class<Posttag> entityClass() { 
		return Posttag.class;
	}

	/**
	 * 
	 * @param postId
	 * @param tagId
	 * @return returnInteger Integer
	 */
	public Integer getPostTagId(Integer postId, Integer tagId){
 		Integer returnInteger = 0;
		Criteria query = null;
		try {
			
			query = getCurrentSession().createCriteria(Posttag.class);
					query.createAlias("this.tag", "tag")
					     .createAlias("this.post", "post");
					query.add(Restrictions.eq("tag.id", tagId))
						 .add(Restrictions.eq("post.id", postId))
						 .add(Restrictions.eq("post.status", "A"))
						 .add(Restrictions.eq("tag.status", "A"))
						 .add(Restrictions.eq("this.status", "A"));
					query.setProjection(Projections.projectionList().add(Projections.property("this.id").as("posttagid")));
			List<?> queryList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list(); 
			if(! queryList.isEmpty() & queryList.size() == 1){
 				Iterator<?> queryListIterator = queryList.listIterator();
				while (queryListIterator.hasNext()) {
					Map<?,?> object =  (Map<?, ?>) queryListIterator.next();
					Object val = object.get("posttagid");
					if(val != null){
 						returnInteger = Integer.parseInt( ""+ object.get("posttagid"));
 					} 
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			postTagsServiceLogger.error("error in PostTagsService for getPostTagId()",e);
		}
		return returnInteger;
	}

	/**
	 * @param id
	 */
	public void deleteDepsOf(int id) {
		Query query  = null;
		 try {
			 query = getCurrentSession().createQuery("delete from Posttag where post_id = :postid ");
			 query.setParameter("postid", id);
			 query.executeUpdate(); 
		} catch (Exception e) {
			 postTagsServiceLogger.error("error in PostTagsService for deleteDepsOf()",e);
		} 
	}; 
	
}
