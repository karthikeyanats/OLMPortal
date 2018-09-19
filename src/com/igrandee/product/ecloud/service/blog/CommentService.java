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
 * CommentService.java
 * Created Jul 23, 2014 11:04:08 AM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.service.blog;

import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.blog.Comment;

/**
 * @author balajichander_r
 *
 */
@Named
@Service
@Scope("prototype")
public class CommentService extends GenericEntityService<Comment, Integer>{

	
	private static final Logger commentServiceLogger  = Logger.getLogger(CommentService.class);
	/* (non-Javadoc)
	 * @see com.igrandee.bean.service.GenericEntityService#entityClass()
	 */
	@Override
	protected Class<Comment> entityClass() { 
		return Comment.class;
	}

	/**
	 * @param commentID
	 * @return commentList List<?>
	 * @author balajichander_r
	 */
	public List<?> getCommentDetails(int commentID) {
		List<?> commentList = null;Criteria query = null;
		try   {
			query = getCurrentSession().createCriteria(Comment.class);
			query.createAlias("this.post", "post")
				 .createAlias("this.orgperson", "commenter")
				 .createAlias("commenter.personid", "commenterdet");
			query.add(Restrictions.eq("this.status", "A"))
				 .add(Restrictions.eq("post.status", "A"))
				 .add(Restrictions.eq("commenter.orgpersonstatus", 'A'))
				 .add(Restrictions.eq("commenterdet.personstatus", 'A'))
				 .add(Restrictions.eq("this.id", commentID));
			query.setProjection(Projections.projectionList()
								 .add(Projections.property("this.id").as("commentid"))
								 .add(Projections.property("this.commentText").as("commnetText"))
								 .add(Projections.property("this.date").as("commentDate"))
								 .add(Projections.property("this.status").as("commentStatus"))
								 .add(Projections.property("post.id").as("postId"))
								 .add(Projections.property("post.title").as("postTitle"))
								 .add(Projections.property("commenterdet.personid").as("perseonid"))
								 .add(Projections.property("commenterdet.firstName").as("personFirstName"))
								 .add(Projections.property("commenterdet.lastName").as("personLastName"))
								 .add(Projections.property("commenterdet.middleName").as("personMiddleName"))
								 .add(Projections.property("commenterdet.personphotourl").as("personPhotoURL")) 
								 );
			Order order = Order.asc("this.date");
			query.addOrder(order);
			commentList = query.setResultTransformer( Criteria.ALIAS_TO_ENTITY_MAP).list();
			
			
			
		} catch (Exception e) {
			commentServiceLogger.error("error in getCommentDetails() in CommentService",e);
		}
		 
		return commentList;
	}
	
	/**
	 * 
	 * @return commentList List<?>
	 * @author balajichander_r
	 */
	public List<?> getCommentDetailsOfAPost(int postID) {
		List<?> commentList = null;Criteria query = null;
		try   {
			query = getCurrentSession().createCriteria(Comment.class);
			query.createAlias("this.post", "post")
				 .createAlias("this.orgperson", "commenter")
				 .createAlias("commenter.personid", "commenterdet");
			query.add(Restrictions.eq("this.status", "A"))
				 .add(Restrictions.eq("post.status", "A"))
				 .add(Restrictions.eq("commenter.orgpersonstatus", 'A'))
				 .add(Restrictions.eq("commenterdet.personstatus", 'A'))
				 .add(Restrictions.eq("post.id", postID));
			query.setProjection(Projections.projectionList()
								 .add(Projections.property("this.id").as("commentid"))
								 .add(Projections.property("this.commentText").as("commnetText"))
								 .add(Projections.property("this.date").as("commentDate"))
								 .add(Projections.property("this.status").as("commentStatus"))
								 .add(Projections.property("post.id").as("postId"))
								 .add(Projections.property("post.title").as("postTitle"))
								 .add(Projections.property("commenterdet.personid").as("perseonid"))
								 .add(Projections.property("commenterdet.firstName").as("personFirstName"))
								 .add(Projections.property("commenterdet.lastName").as("personLastName"))
								 .add(Projections.property("commenterdet.middleName").as("personMiddleName"))
								 .add(Projections.property("commenterdet.personphotourl").as("personPhotoURL")) 
								 );
			Order order = Order.desc("this.date");
			query.addOrder(order);
			commentList = query.setResultTransformer( Criteria.ALIAS_TO_ENTITY_MAP).list(); 
		} catch (Exception e) {
			commentServiceLogger.error("error in getCommentDetailsOfAPost() in CommentService",e);
		}
		 
		return commentList;
	}

}
