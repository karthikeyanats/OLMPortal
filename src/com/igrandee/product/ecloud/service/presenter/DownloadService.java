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
package com.igrandee.product.ecloud.service.presenter;

import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Presenterapp;
import com.igrandee.product.ecloud.model.Presenterappdetail;
import com.igrandee.product.ecloud.model.Presentertype;

/**
 * @author vetrichelvi_m
 * 
 */
@Service
@Named
@Scope("prototype")
public class DownloadService extends GenericEntityService<Presenterapp, Integer> {
 
	@Override
	protected Class<Presenterapp> entityClass() {
		return Presenterapp.class;
	}

	private static final Logger PresenterappLogger = Logger.getLogger(Presenterapp.class);

	/**
	 * @param parseInt
	 * @return
	 */

	public List<?> getPresenterapps() {
		List<?> getpresenterlist = null;
		try {
			getpresenterlist = getCurrentSession().createCriteria(Presenterapp.class)
					.createAlias("this.presenterappdetails", "presenterappdetails",JoinType.LEFT_OUTER_JOIN,Restrictions.eq("presenterappdetails.detailstatus", 'A'))
					.createAlias("presenterappdetails.presenterappDownloadhistories", "presenterappdownloadhistory",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("presenterappdetails.presentertype", "presentertypename")
					.add(Restrictions.eq("this.presenterstatus", 'A')) 
					.setProjection(Projections.projectionList()
							.add(Projections.property("presentertypename.typename").as("typename"))
							.add(Projections.property("presentertypename.id").as("typeid"))
							.add(Projections.property("this.presenterid").as("presenterid"))
							.add(Projections.property("this.applicationname").as("applicationname"))
							.add(Projections.property("this.apppath").as("apppath"))
							.add(Projections.property("this.helpurlpath").as("helpurlpath"))
							.add(Projections.property("this.presenterstatus").as("presenterstatus"))
							.add(Projections.property("presenterappdetails.id").as("presenterappdetailsid"))
							.add(Projections.property("presenterappdetails.price").as("price")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
 
		} catch (Exception e) {
			PresenterappLogger.error("error in DownloadService for this getPresenterapps()",e);
		}
		return getpresenterlist;
	}   
	public List<?> getPresentertype() {
		List<?> getpresentertypelist = null;
		try {
			getpresentertypelist = getCurrentSession().createCriteria(Presentertype.class)
					.add(Restrictions.eq("this.typestatus", 'A'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.typename").as("typename"))
							.add(Projections.property("this.id").as("id"))
							.add(Projections.property("this.typestatus").as("typestatus")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		} catch (Exception e) {
			PresenterappLogger.error("error in DownloadService for this getPresentertype()",e);
		}
		return getpresentertypelist;
	}

	public List<?> getPresenterAppDetailsList(String presenterid) {
		List<?> presenterAppDetailsList = null;
		try {
			presenterAppDetailsList = getCurrentSession().createCriteria(Presenterappdetail.class)
					.createAlias("this.presenterapp", "presenterapp")
					.createAlias("this.presentertype", "presentertype")
					.add(Restrictions.eq("presenterapp.presenterid", Integer.parseInt(presenterid)))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.id").as("presenterdetailid"))
							.add(Projections.property("this.workingduration").as("workingduration"))
							.add(Projections.property("this.logincount").as("logincount"))
							.add(Projections.property("this.price").as("price"))
							.add(Projections.property("this.detailstatus").as("detailstatus"))
							.add(Projections.property("presentertype.typename").as("typename"))
							.add(Projections.property("presentertype.typestatus").as("typestatus"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		} catch (Exception e) {
			PresenterappLogger.error("error in DownloadService for this getPresenterAppDetailsList()",e);
		}
		return presenterAppDetailsList;
	}

	public List<?> getEditpresenter(int presenterid) {
		List<?> getpresenterlist= null;
		try {
			getpresenterlist = getCurrentSession().createCriteria(Presenterapp.class)
					.createAlias("this.presenterappdetails", "presenterappdetails")
					.createAlias("presenterappdetails.presentertype", "presentertypename",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("this.presenterid", presenterid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.presenterid").as("presenterid"))
							.add(Projections.property("presentertypename.typename").as("typename"))
							.add(Projections.property("presentertypename.id").as("typeid"))
							.add(Projections.property("this.presenterid").as("presenterid"))
							.add(Projections.property("this.applicationname").as("applicationname"))
							.add(Projections.property("this.apppath").as("apppath"))
							.add(Projections.property("this.helpurlpath").as("helpurlpath"))
							.add(Projections.property("this.presenterstatus").as("presenterstatus"))
							.add(Projections.property("presenterappdetails.id").as("presenterappdetailsid"))
							.add(Projections.property("presenterappdetails.logincount").as("logincount"))
							.add(Projections.property("presenterappdetails.workingduration").as("workingduration"))
							.add(Projections.property("presenterappdetails.detailstatus").as("detailstatus"))
							.add(Projections.property("presenterappdetails.price").as("price")))

							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		} catch (Exception e) {
			PresenterappLogger.error("error in DownloadService for this getEditpresenter()",e);
		}
		return getpresenterlist;
	}


	public List<?> getPresenterdetails() {
		
		List<?> getpresenterdetailslist = null;
		
		try {
			
			getpresenterdetailslist = getCurrentSession().createCriteria(Presenterapp.class)
					
					.createAlias("this.presenterappdetails", "presenterappdetails")
					.createAlias("presenterappdetails.presentertype", "presentertypename")
					.add(Restrictions.eq("presenterappdetails.detailstatus", 'A'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.presenterid").as("presenterid"))
							.add(Projections.property("presentertypename.typename").as("typename"))
							.add(Projections.property("presentertypename.id").as("typeid"))
							.add(Projections.property("this.presenterid").as("presenterid"))
							.add(Projections.property("this.applicationname").as("applicationname"))
							.add(Projections.property("this.apppath").as("apppath"))
							.add(Projections.property("this.helpurlpath").as("helpurlpath"))
							.add(Projections.property("this.presenterstatus").as("presenterstatus"))
							.add(Projections.property("presenterappdetails.id").as("presenterappdetailsid"))
							.add(Projections.property("presenterappdetails.logincount").as("logincount"))
							.add(Projections.property("presenterappdetails.workingduration").as("workingduration"))							
							.add(Projections.property("presenterappdetails.price").as("price")))
							.addOrder(Order.desc("presenterid"))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			System.out.println("price>>>>>>>>>>>>>>>>"+getpresenterdetailslist);
		} 
		catch (Exception e) {
			PresenterappLogger.error("error in DownloadService for this getPresenterdetails()",e);
			
		}
		return getpresenterdetailslist;
		
	}   
 
	public List<?> getpresenterupdate() {
		List<?> getpresenterdetailslist = null;
		try {
			getpresenterdetailslist = getCurrentSession().createCriteria(Presenterapp.class)
					.createAlias("this.presenterappdetails", "presenterappdetails")
					.createAlias("presenterappdetails.presentertype", "presentertypename",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.add(Restrictions.eq("this.presenterstatus", 'A'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.presenterid").as("presenterid"))
							.add(Projections.property("presentertypename.typename").as("typename"))
							.add(Projections.property("presentertypename.id").as("typeid"))
							.add(Projections.property("this.presenterid").as("presenterid"))
							.add(Projections.property("this.applicationname").as("applicationname"))
							.add(Projections.property("this.apppath").as("apppath"))
							.add(Projections.property("this.helpurlpath").as("helpurlpath"))
							.add(Projections.property("this.presenterstatus").as("presenterstatus"))
							.add(Projections.property("presenterappdetails.id").as("presenterappdetailsid"))
							.add(Projections.property("presenterappdetails.logincount").as("logincount"))
							.add(Projections.property("presenterappdetails.workingduration").as("workingduration"))
							.add(Projections.property("presenterappdetails.price").as("price")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		} catch (Exception e) {
			PresenterappLogger.error("error in DownloadService for this getpresenterupdate()",e);
		}
		return getpresenterdetailslist;
	}   

	public Integer updatePresenter(int presenterid){
		try	{			
			Query query=getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Presenterapp set presenterstatus='D' where presenterid=:presenterid");
			query.setParameter("presenterid", presenterid);
			return query.executeUpdate();

		}
		catch(Exception e){
			if(PresenterappLogger.isInfoEnabled()){
				PresenterappLogger.error("error in DownloadService for this updatePresenter()",e);
			}
			return 0;
		}

	}

	public Integer deactivatePresenter(){
		try	{			
			Query query=getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Presenterapp set presenterstatus='D' where presenterstatus=:presenterstatus");
			query.setParameter("presenterstatus", 'A');
			query.executeUpdate();
			return 1;
		}
		catch(Exception e){
			if(PresenterappLogger.isInfoEnabled()){
				PresenterappLogger.error("error in DownloadService for this deactivatePresenter()",e);
			}
			return 0;
		}
	}	
	
}
