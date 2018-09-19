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
 * com.igrandee.products.ecloud.blog.ecloud.blogutil.action
 * SkillAction.java
 * Created Jul 23, 2014 10:51:00 AM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.action.blog;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.blog.Post;
import com.igrandee.product.ecloud.model.blog.Postskill;
import com.igrandee.product.ecloud.model.blog.Skill;
import com.igrandee.product.ecloud.service.blog.SkillService;
import com.igrandee.product.ecloud.viewmodel.blog.SkillViewModel;
import com.sun.jersey.api.core.InjectParam;

/**
 * @author balajichander_r
 *
 */
@Path("/blog/skill") 
public class SkillAction extends AbstractIGActionSupport {

	/**
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 1L;

	static final Logger skillActionLogger = Logger.getLogger(SkillAction.class);
	
	@InjectParam
	Skill skill;
	
	@InjectParam
	SkillService skillService;
	
	@InjectParam
	Postskill postskill;
	
	@InjectParam
	Post post;
	
	@InjectParam
	Orgperson orgperson;
	
	Map<?, ?> hashMap;
	
	
	/**
	 * 
	 * @param skillViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(SkillViewModel skillViewModel) {
		
		skillActionLogger.info("*************** CREATE A SKILL  ******************  ");
		skillActionLogger.info("ACTION :: CLIENT skillViewModel == > " +skillViewModel); 
		
		skill.setName(skillViewModel.getSkillName());
		skill.setDescription(java.net.URLDecoder.decode(skillViewModel.getSkillDescription()));
		skill.setMediapath(skillViewModel.getSkillMediaPath());
		orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
		skill.setOrgperson(orgperson);
		skill.setStatus(skillViewModel.getSkillStatus());
		
		int isSkill = skillService.save(skill); 
		
		skillActionLogger.info("ACTION :: RESPONSE skill == > " +skill);
		skillActionLogger.info("*************** *************** ******************  ");
		
		if(isSkill > 0){
			return Response.status(Status.OK).entity(skill).build();
		}
		
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 
	 * @param skillViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(SkillViewModel skillViewModel) {
		
		skillActionLogger.info("*************** UPDATE A SKILL ******************  ");
		skillActionLogger.info("ACTION :: CLIENT skillViewModel == > " +skillViewModel);
		skillActionLogger.info("*************** *************** ******************  ");
		 
		skill.setId(Integer.parseInt(skillViewModel.getSkillId()));
		skill.setName(skillViewModel.getSkillName());
		skill.setDescription(java.net.URLDecoder.decode(skillViewModel.getSkillDescription()));
		skill.setMediapath(skillViewModel.getSkillMediaPath());
		orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
		skill.setOrgperson(orgperson);
		skill.setStatus(skillViewModel.getSkillStatus());
		
		skill = skillService.update(skill);
		skillActionLogger.info("ACTION :: RESPONSE skill == > " +skill);
		skillActionLogger.info("*************** *************** ******************  ");
		
		if(skill != null){
			return Response.status(Status.OK).entity(skill).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 
	 * @param skillViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delete(SkillViewModel skillViewModel) {
		
		skillActionLogger.info("***************  DELETE A SKILL  ******************  ");
		skillActionLogger.info("ACTION :: CLIENT skillViewModel == > " +skillViewModel); 
		
		skill.setId(Integer.parseInt(skillViewModel.getSkillId()));
		skill.setName(skillViewModel.getSkillName());
		skill.setDescription(java.net.URLDecoder.decode(skillViewModel.getSkillDescription()));
		orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
		skill.setOrgperson(orgperson);
		skill.setMediapath(skillViewModel.getSkillMediaPath());
		skill.setStatus("D"); 
		
		skill = skillService.update(skill);
		skillActionLogger.info("ACTION ::  RESPONSE skill == > " +skill);
		skillActionLogger.info("****************************************************  ");
		
		if(skill != null){
			return Response.status(Status.OK).entity(skill).build();
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 
	 * @param skillViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/skill") 
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getASkill(SkillViewModel skillViewModel) {
		
		skillActionLogger.info("*************** GET A SKILL     ******************  ");
		skillActionLogger.info("ACTION :: CLIENT skillViewModel == > " +skillViewModel); 
		
		List<?> skillList = skillService.getSkillOf(Integer.parseInt(skillViewModel.getSkillId())); 
		
		skillActionLogger.info("ACTION :: RESPONSE skillList == > " + skillList);
		skillActionLogger.info("****************************************************  ");
		
		if(skillList.size()>0 && ! skillList.isEmpty()){
			return Response.status(Status.OK).entity(skillList).build();
		}
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}

	/**
	 * 
	 * @param skillViewModel
	 * @return Status.OK | Status.INTERNAL_SERVER_ERROR Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/skills") 
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getSkills(SkillViewModel skillViewModel) {
		
		skillActionLogger.info("***************   GET SKILLS    ******************  ");
		skillActionLogger.info("ACTION :: CLIENT skillViewModel == > " +skillViewModel); 
		
		List<?> skillList = skillService.getSkills(Long.parseLong(getAttribute("organizationid")) ); 
		
		skillActionLogger.info("ACTION :: RESPONSE skillList == > " +skillList);
		skillActionLogger.info("****************************************************  ");
		
		if(skillList.size() > 0 && ! skillList.isEmpty()){
			return Response.status(Status.OK).entity(skillList).build();
		}
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}
	
	/**
	 * 
	 * @param tagViewModel
	 * @return Status.NO_CONTENT | Status.ACCEPTED 
	 * @author balajichander_r
	 */
	@POST
	@Path("/duplicate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response checkDuplicate(SkillViewModel skillViewModel){
		
		skillActionLogger.info("*************** CHECK FOR DUPLICATE SKILLS ******************  " );
		skillActionLogger.info("ACTION :: CLIENT skill name == > " + skillViewModel.getSkillName() );  
		boolean assertValue = skillService.checkDuplicate(skillViewModel.getSkillName(),getAttribute("orgpersonid")); 
		skillActionLogger.info("ACTION :: CLIENT assertValue == > " + assertValue );
		if(assertValue == true){
			skillActionLogger.info("ACTION :: RESPONSE is Skill duplicate ? == > " + assertValue );
			skillActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(Status.ACCEPTED).build();
			
		} 
		skillActionLogger.info("ACTION :: FAILURE RESPONSE is skill duplicate ?, == > " + assertValue);
		skillActionLogger.info("**************************************************  "); 
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}
	
	/**
	 *  
	 * @param skillViewModel
	 * @return Status.NO_CONTENT | Status.ACCEPTED
	 * @author balajichander_r 
	 */
	@POST
	@Path("/skillroll/duplicate")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response checkUpdateDuplicate(SkillViewModel skillViewModel){
		skillActionLogger.info("*************** CHECK FOR DUPLICATE SKILLS ******************  " );
		skillActionLogger.info("ACTION :: CLIENT categoryTitlePhrase == > " + skillViewModel.getSkillName() + " category id : " + skillViewModel.getSkillId() );
		
		boolean assertValue = skillService.checkDuplicate( skillViewModel.getSkillName(), Integer.parseInt(skillViewModel.getSkillId()),getAttribute("orgpersonid")); 
		skillActionLogger.info("ACTION :: CLIENT assertValue == > " + assertValue );
		if(assertValue == true){
			skillActionLogger.info("ACTION :: RESPONSE is category duplicate ? == > " + assertValue );
			skillActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(Status.ACCEPTED).build();
		} 
		skillActionLogger.info("ACTION :: FAILURE RESPONSE is category duplicate ?, == > " + assertValue);
		skillActionLogger.info("**************************************************  "); 
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();	
	}
	
	/**
	 * 
	 * @param tagId
	 * @return Status.ACCEPTED | Status.NO_CONTENT Response
	 * @author balajichander_r
	 */
	@POST
	@Path("/skillroll/post/{id}")
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response assertPostAvailablility( @PathParam("id") Integer skillId){
		skillActionLogger.info("*************** ASSERT POST AVAILABILITY FOR THIS CATEGORY ******************  " );
		skillActionLogger.info("ACTION :: CLIENT tagId == > " + skillId );
		boolean assertValue = skillService.assertPostAvailability(skillId);
		if(assertValue == false){ 
			skillActionLogger.info("ACTION :: RESPONSE is post available for skill ? == > " + assertValue );
			skillActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(Status.ACCEPTED).build();
		}
		skillActionLogger.info("ACTION :: FAILURE RESPONSE is post available for skill  ?, == > " + assertValue);
		skillActionLogger.info("**************************************************  "); 
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();	
		
	}
	
}
