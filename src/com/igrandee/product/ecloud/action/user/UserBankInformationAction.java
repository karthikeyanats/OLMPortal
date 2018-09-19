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
 * com.igrandee.product.ecloud.action.user
 * UserBankInformationAction.java
 * Created Sep 25, 2014 4:01:37 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.action.user;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.product.ecloud.model.BankDetails;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.service.users.BankDetailsService;
import com.igrandee.product.ecloud.serviceadapter.UserBankInformationAdapter;
import com.igrandee.product.ecloud.viewmodel.UserBankInformationViewModel;
import com.sun.jersey.api.core.InjectParam;

/**
 * @author balajichander_r
 *
 */
@Path("/binfo")
public class UserBankInformationAction extends AbstractIGActionSupport{

	/**
	 * AUTO GENERATED DEFAULT SERIAL VERSION ID STUB
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 1L;
	private final Logger userBankInformationActionLogger = Logger.getLogger(UserBankInformationAction.class);
 
	
	@InjectParam
	BankDetails bankDetails;
	
	@InjectParam
	Orgperson orgperson;
	
	@InjectParam
	BankDetailsService bankDetailsService; 
	
	@InjectParam
	UserBankInformationAdapter userBankInformationAdapter;
	
	@POST 
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response createBankInfo( UserBankInformationViewModel userBankInformationViewModel){
		userBankInformationActionLogger.info("*************** CREATE BANKERS INFO ******************  ");
		userBankInformationActionLogger.info("ACTION :: CLIENT BANKERSINFOVIEWMODEL == > " + userBankInformationViewModel);
		int orgPersonId = Integer.parseInt(getAttribute("orgpersonid"));
		int bankInfo =0;  
		 try{
			 bankDetails = userBankInformationAdapter.convertNewBankInfoEntity(userBankInformationViewModel,orgperson, orgPersonId ); 
			 bankInfo =  bankDetailsService.save(bankDetails); 
		 }catch(Exception e){
			 e.printStackTrace();
		 } 
		if(bankInfo > 0){
			userBankInformationActionLogger.info("**************************************************  ");
			return Response.status(Status.CREATED).entity(bankDetails).build(); 
		} 
		userBankInformationActionLogger.info("**************************************************  ");
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(Status.INTERNAL_SERVER_ERROR).build();
	}
	
	
	@GET
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response getBankInfo( UserBankInformationViewModel userBankInformationViewModel){
		userBankInformationActionLogger.info("*************** CREATE BANKERS INFO ******************  ");
		userBankInformationActionLogger.info("ACTION :: CLIENT BANKERSINFOVIEWMODEL == > " + userBankInformationViewModel); 
		List<?> bankDetailsList = null;  
		 try{  
			 bankDetailsList =  bankDetailsService.getBankDetails(Integer.parseInt(getAttribute("orgpersonid"))); 
		 }catch(Exception e){
			 e.printStackTrace();
		 } 
		if(bankDetailsList.size() > 0 & ! bankDetailsList.isEmpty()){
			userBankInformationActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(bankDetailsList).build(); 
		} 
		bankDetailsList = null;
		userBankInformationActionLogger.info("**************************************************  ");
		return Response.status(Status.NO_CONTENT).entity(bankDetailsList).build();
	}
	
	@PUT 
	@Consumes( MediaType.APPLICATION_JSON)
	@Produces( MediaType.APPLICATION_JSON)
	public Response updateBankInfo( UserBankInformationViewModel userBankInformationViewModel){
		userBankInformationActionLogger.info("*************** CREATE BANKERS INFO ******************  ");
		userBankInformationActionLogger.info("ACTION :: CLIENT BANKERSINFOVIEWMODEL == > " + userBankInformationViewModel); 
		int orgPersonId = Integer.parseInt(getAttribute("orgpersonid")); 
 		 try{
			 bankDetails = userBankInformationAdapter.convertUpdateBankInfoEntity(userBankInformationViewModel,orgperson, orgPersonId ); 
			 bankDetails =  bankDetailsService.update(bankDetails); 
		 }catch(Exception e){
			 e.printStackTrace();
		 } 
		if(bankDetails != null){
			userBankInformationActionLogger.info("**************************************************  ");
			return Response.status(Status.ACCEPTED).entity(bankDetails).build(); 
		} 
		userBankInformationActionLogger.info("**************************************************  ");
		return Response.status(Status.NO_CONTENT).entity(Status.NO_CONTENT).build();
	}
	
	
}
