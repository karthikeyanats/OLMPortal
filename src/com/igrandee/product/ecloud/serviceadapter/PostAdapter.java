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
 * ecloudbaseutil 
 * com.igrandee.product.ecloud.serviceadapter
 * PostAdapter.java
 * Created Jul 30, 2014 5:04:46 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.serviceadapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.igrandee.product.ecloud.action.blog.PostAction;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.blog.BlogMedia;
import com.igrandee.product.ecloud.model.blog.Category;
import com.igrandee.product.ecloud.model.blog.Post;
import com.igrandee.product.ecloud.model.blog.Postcategory;
import com.igrandee.product.ecloud.model.blog.Postskill;
import com.igrandee.product.ecloud.model.blog.Posttag;
import com.igrandee.product.ecloud.model.blog.Skill;
import com.igrandee.product.ecloud.model.blog.Tag;
import com.igrandee.product.ecloud.service.blog.PostCategoriesService;
import com.igrandee.product.ecloud.service.blog.PostService;
import com.igrandee.product.ecloud.service.blog.PostSkillsService;
import com.igrandee.product.ecloud.service.blog.PostTagsService;
import com.igrandee.product.ecloud.util.blog.BlogCommonsUtil;
import com.igrandee.product.ecloud.viewmodel.blog.PostViewModel;
import com.sun.jersey.core.header.ContentDisposition;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;

/**
 * @author balajichander_r
 *
 */
@SuppressWarnings({ "rawtypes"})
@Component
@Scope("prototype")
public class PostAdapter {
	private static final BlogCommonsUtil BLOG_COMMONS_UTIL = new BlogCommonsUtil();
	Logger postAdapterLogger = Logger.getLogger(PostAdapter.class);
    /** 
     * @param post
	 * @param postViewModel
	 * @param postAction
	 * @param orgperson
	 * @param postcategory
	 * @param postskill
	 * @param posttag
	 * @param skill
	 * @param tag
	 * @param category
	 * @return post Post
	 */
	public Post convertNewPostEntity(Post post, PostViewModel postViewModel, Integer orgpersonid, Orgperson orgperson, Postcategory postcategory, Postskill postskill, Posttag posttag, Skill skill, Tag tag, Category category){ 
		 
		 try {
			  
			post.setTitle(postViewModel.getPostTitle());
			post.setContent(postViewModel.getPostContent());
			orgperson.setOrgpersonid(orgpersonid);
			post.setOrgperson(orgperson);
			post.setMedia(postViewModel.getPostMedia());
			post.setDate(BLOG_COMMONS_UTIL.getCurrentTimStamp());
			post.setCommentable(postViewModel.getIsPostCommentable());
			post.setBannerpath(postViewModel.getPostBannerPath());
			 
			
			List<?> categoryList = new ArrayList(); 
			categoryList = (List<?>) postViewModel.getCategoryIds();
			Iterator<?> categoryListIterator = categoryList.listIterator(); 
			HashSet<Postcategory> postCategorySet=new HashSet<Postcategory>();
			post.setPostcategories(postCategorySet);
			while (categoryListIterator.hasNext()) {
				Integer categoryId = Integer.parseInt(categoryListIterator.next()+""); 
				category = new Category();
				category.setId(categoryId);
				postcategory = new Postcategory();
				postcategory.setCategory(category); 
				postcategory.setStatus("A");  
				post.addPostcategory(postcategory); 
			}
			
			post.setPostcategories(postCategorySet);
			
			List<?> skillList = new ArrayList(); 
			skillList = (List<?>) postViewModel.getSkillIds();
			Iterator<?> skillListIterator = skillList.listIterator();
		    HashSet<Postskill> postSkillSet = new HashSet<Postskill>();
		    post.setPostskills(postSkillSet);
			while (skillListIterator.hasNext()) {
				Integer skillId = Integer.parseInt( skillListIterator.next()+""); 
				skill = new Skill();
				skill.setId(skillId);
				postskill = new Postskill();
				postskill.setSkill(skill);
				postskill.setStatus("A");
				post.addPostskill(postskill);
			}

			List<?> tagList = new ArrayList(); 
			tagList = (List<?>) postViewModel.getTagIds();
			Iterator<?> tagListIterator = tagList.listIterator();
			HashSet<Posttag> postTagSet = new HashSet<Posttag>();
			post.setPosttags(postTagSet);
			while (tagListIterator.hasNext()) {
				Integer tagId = Integer.parseInt( tagListIterator.next()+""); 
				tag = new Tag();
				tag.setId(tagId);
				posttag = new Posttag();
				posttag.setTag(tag);
				posttag.setStatus("A");
				post.addPosttag(posttag);
			}
			 
			post.setStatus("A");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return post;
	 }
     /**
     * @param post
	 * @param postAction
	 * @param postService
	 * @param postViewModel
	 * @param orgpersonid
	 * @param orgperson
	 * @param postcategory
	 * @param postskill
	 * @param posttag
	 * @param category
	 * @param tag
	 * @param skill
	 * @return post Post
	 */
	@SuppressWarnings({ "unchecked" })
	public Post convertUpdatePostEntity(Post post,PostAction postAction,SessionFactory sessionFactory,PostService postService,PostViewModel postViewModel,   Orgperson orgperson, Postcategory postcategory, Postskill postskill, Posttag posttag, Category category, Tag tag, Skill skill, Integer postID, PostCategoriesService postCategoriesService, PostSkillsService postSkillsService, PostTagsService postTagsService ){
		 
		 try { 
			 
			 	//working on post object
				post = new Post();
				post.setId(Integer.parseInt(postViewModel.getPostId()));
				post.setTitle(postViewModel.getPostTitle());
				post.setContent(postViewModel.getPostContent());
				orgperson.setOrgpersonid(Integer.parseInt(postAction.getAttribute("orgpersonid")));
				post.setOrgperson(orgperson);
				post.setMedia(postViewModel.getPostMedia());
				post.setDate(BLOG_COMMONS_UTIL.getCurrentTimStamp());
				post.setCommentable(postViewModel.getIsPostCommentable()); 
				post.setBannerpath(postViewModel.getPostBannerPath()); 
				post.setStatus("A");
				
				//DELETE POST
				if(postViewModel.getPostStatus().equals("D")){
					post.setStatus("D"); 
					postAdapterLogger.info("STATUS ==== > " + postViewModel.getPostStatus());
					
				}//UPDATE POST 
				else if (postViewModel.getPostStatus().equals("U")) {   
					 
				 
				  Collection<Integer> cCatId  = new HashSet( new ArrayList<Integer>((List<Integer>) postViewModel.getCategoryIds()));
				  Collection<Integer> cTagId  = new HashSet(  new ArrayList<Integer>((List<Integer>) postViewModel.getTagIds()));
				  Collection<Integer> cSkillId  = new HashSet(  new ArrayList<Integer>((List<Integer>) postViewModel.getSkillIds()));
				  
				   postCategoriesService.deleteDepsOf(post.getId());
				   postTagsService.deleteDepsOf(post.getId());
				   postSkillsService.deleteDepsOf(post.getId());
				   
				    
					Iterator<?> categoryListIterator = cCatId.iterator(); 
					HashSet<Postcategory> postCategorySet=new HashSet<Postcategory>();
					post.setPostcategories(postCategorySet);
					while (categoryListIterator.hasNext()) {
						Integer categoryId = Integer.parseInt(categoryListIterator.next()+""); 
						category = new Category();
						category.setId(categoryId);
						postcategory = new Postcategory();
						postcategory.setCategory(category); 
						postcategory.setStatus("A");  
						post.addPostcategory(postcategory); 
					}
					
					 
					Iterator<?> skillListIterator = cSkillId.iterator(); 
				    HashSet<Postskill> postSkillSet = new HashSet<Postskill>();
				    post.setPostskills(postSkillSet);
					while (skillListIterator.hasNext()) {
						Integer skillId = Integer.parseInt( skillListIterator.next()+""); 
						skill = new Skill();
						skill.setId(skillId);
						postskill = new Postskill();
						postskill.setSkill(skill);
						postskill.setStatus("A");
						post.addPostskill(postskill);
					}
						
					
					
					Iterator<?> tagListIterator = cTagId.iterator(); 
					HashSet<Posttag> postTagSet = new HashSet<Posttag>();
					post.setPosttags(postTagSet);
					while (tagListIterator.hasNext()) {
						Integer tagId = Integer.parseInt( tagListIterator.next()+""); 
						tag = new Tag();
						tag.setId(tagId);
						posttag = new Posttag();
						posttag.setTag(tag);
						posttag.setStatus("A");
						post.addPosttag(posttag);
					} 
					
					 
				}  
				
			} catch (Exception e) {
				e.printStackTrace();
				postAdapterLogger.error(e);
			}
			 return post;
	 }
 	public List<BlogMedia> uploadMediaAndGetFileDetails(List<FormDataBodyPart> file, BlogMedia blogMedia, Orgperson orgperson,Integer orgpersonid) { 
		List<BlogMedia>  fileMetaList = null;
		String actualFilepath =null;  
		try {
			for (int i = 0; i < file.size() ; i++) {
				fileMetaList = new  ArrayList<BlogMedia>();
				FormDataBodyPart this_formDataBodyPartFile = file.get(i);
				ContentDisposition this_contentDispositionHeader  = this_formDataBodyPartFile.getContentDisposition();
				InputStream this_fileInputStream = this_formDataBodyPartFile.getValueAs(InputStream.class);
				postAdapterLogger.info("ADAPTER :: CLIENT inputMedia == > " + this_contentDispositionHeader.getFileName());
				actualFilepath =  this.uploadMedia(this_fileInputStream, (FormDataContentDisposition) this_contentDispositionHeader);
				String actualFileName = this_contentDispositionHeader.getFileName(), obsoluteFilePath = null;
				obsoluteFilePath = BLOG_COMMONS_UTIL.getObsoluteFilePath(actualFilepath);
				blogMedia.setName(actualFileName);
				postAdapterLogger.info(obsoluteFilePath);
				blogMedia.setPath(obsoluteFilePath);
				blogMedia.setStatus("A");
				orgperson.setOrgpersonid(orgpersonid);
				blogMedia.setOrgperson( orgperson); 
				fileMetaList.add(blogMedia); 
			} 
		} catch (Exception e) {
			postAdapterLogger.error(e);
		}
		return fileMetaList;
	}
 	public String uploadMedia(InputStream inputMedia, FormDataContentDisposition fileDetail){
		String  returnURL = null;
		 try {
			  
			 returnURL = BLOG_COMMONS_UTIL.uploadFile(inputMedia,fileDetail,"postmedia"); 
		} catch (Exception e) {
			 postAdapterLogger.error(e);
		}
		return returnURL;
	}
}
