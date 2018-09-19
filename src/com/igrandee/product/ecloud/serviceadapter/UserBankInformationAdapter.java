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
 * UserBankInformationAdapter.java
 * Created Sep 25, 2014 4:57:45 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.serviceadapter;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.igrandee.product.ecloud.model.BankDetails;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.util.blog.BlogCommonsUtil;
import com.igrandee.product.ecloud.viewmodel.UserBankInformationViewModel;

/**
 * @author balajichander_r
 *
 */
@Component
@Scope("prototype")
public class UserBankInformationAdapter {

	
	private final Logger userBankInformationAdapterLogger = Logger.getLogger(UserBankInformationAdapter.class);
	private static final BlogCommonsUtil BLOG_COMMONS_UTIL = new BlogCommonsUtil();
	
	/**
	 * @param orgperson 
	 * @param userBankInformationAction 
	 * @param userBankInformationViewModel 
	 * @param orgPersonId 
	 * 
	 */
	public BankDetails convertNewBankInfoEntity(UserBankInformationViewModel userBankInformationViewModel,  Orgperson orgperson, int orgPersonId) {
		BankDetails bankDetails = null;
		
		try {
			bankDetails = new BankDetails(); 
			bankDetails.setBankName(userBankInformationViewModel.getBankInfoName());
			bankDetails.setBranchName(userBankInformationViewModel.getBankInfoBranchName());
			bankDetails.setAccountNumber(userBankInformationViewModel.getBankInfoAccountNumber());
			bankDetails.setCountry(userBankInformationViewModel.getCountry());
			bankDetails.setDate(BLOG_COMMONS_UTIL.getCurrentTimStamp());
			bankDetails.setIfscCode(userBankInformationViewModel.getBankInfoIFSCCode());
			bankDetails.setLocation(userBankInformationViewModel.getBankInfoBranchLocation()); 
			orgperson = new Orgperson();
			orgperson.setOrgpersonid(orgPersonId);
			bankDetails.setOrgperson(orgperson);
			bankDetails.setStatus('A');
			
		} catch (Exception e) {
			e.printStackTrace();
			userBankInformationAdapterLogger.error(e);
		}
		
		return bankDetails; 
	}
	
	
	public BankDetails convertUpdateBankInfoEntity(UserBankInformationViewModel userBankInformationViewModel,  Orgperson orgperson, int orgPersonId) {
		BankDetails bankDetails = null;
		
		try { 
			
			bankDetails = new BankDetails(); 
			bankDetails.setId(Integer.parseInt(userBankInformationViewModel.getBankInfoId()));
			bankDetails.setBankName(userBankInformationViewModel.getBankInfoName());
			bankDetails.setBranchName(userBankInformationViewModel.getBankInfoBranchName());
			bankDetails.setAccountNumber(userBankInformationViewModel.getBankInfoAccountNumber());
			bankDetails.setCountry(userBankInformationViewModel.getCountry());
			bankDetails.setDate(BLOG_COMMONS_UTIL.getCurrentTimStamp());
			bankDetails.setIfscCode(userBankInformationViewModel.getBankInfoIFSCCode());
			bankDetails.setLocation(userBankInformationViewModel.getBankInfoBranchLocation()); 
			orgperson = new Orgperson();
			orgperson.setOrgpersonid(orgPersonId);
			bankDetails.setOrgperson(orgperson);
			bankDetails.setStatus('A');
			
		} catch (Exception e) {
			e.printStackTrace();
			userBankInformationAdapterLogger.error(e);
		}
		
		return bankDetails; 
	}
 
	
	
	
	
}
