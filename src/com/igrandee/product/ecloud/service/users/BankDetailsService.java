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
 * com.igrandee.product.ecloud.service.users
 * BankDetailsService.java
 * Created Sep 25, 2014 5:16:55 PM
 * by balajichander_r
 */
package com.igrandee.product.ecloud.service.users;

import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.BankDetails;

/**
 * @author balajichander_r
 *
 */
@Service
@Scope("prototype")
@Named
public class BankDetailsService extends GenericEntityService<BankDetails, Integer>{

	/* (non-Javadoc)
	 * @see com.igrandee.bean.service.GenericEntityService#entityClass()
	 */
	@Override
	protected Class<BankDetails> entityClass() { 
		return BankDetails.class;
	}
   
	private static final Logger bankDetailsServiceLogger = Logger.getLogger(BankDetailsService.class);
	/**
	 * @param parseInt
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BankDetails> getBankDetails(int parseInt) {
		Criteria query = null;
		List<BankDetails> bankDetailsList = null;
		try {
			query = getCurrentSession().createCriteria(BankDetails.class) 
					.createAlias("orgperson", "orgperson");  
			query.add(Restrictions.eq("this.bankdetailsstatus", 'A'))
				 .add(Restrictions.eq("orgperson.orgpersonstatus", 'A'))
				 .add(Restrictions.eq("orgperson.orgpersonid", parseInt));  
			query	.setProjection(Projections.projectionList()
					.add(Projections.property("this.id").as("id"))
					.add(Projections.property("this.bankName").as("bankName"))
					.add(Projections.property("this.branchName").as("branchName"))
					.add(Projections.property("this.accountNumber").as("accountNumber"))
					.add(Projections.property("this.ifscCode").as("ifscCode"))
					.add(Projections.property("this.dateofcreation").as("dateofcreation"))
					.add(Projections.property("this.location").as("location"))
					.add(Projections.property("this.country").as("country"))
					.add(Projections.property("this.bankdetailsstatus").as("bankdetailsstatus"))
					.add(Projections.property("this.orgperson.orgpersonid").as("orgpersonid")) 
					);
			
			bankDetailsList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			bankDetailsServiceLogger.error("Error in BankDetailsService in getBankDetails()",e);
		}
		
		return bankDetailsList;
	}
	 
	
	
}
