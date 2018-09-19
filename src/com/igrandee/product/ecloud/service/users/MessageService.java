package com.igrandee.product.ecloud.service.users;

import java.io.Serializable;
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
import com.igrandee.product.ecloud.model.Message;

@Service
@Scope("prototype")
@Named
public class MessageService extends GenericEntityService<Message, Serializable> {
	private static Logger MessageServicelogger = Logger.getLogger(MessageService.class);

	@Override
	protected Class<Message> entityClass() {
		// TODO Auto-generated method stub
		return Message.class;
	}
	
	/**
	 * @author raja_r
	 * Service Method to get message 
	 * @param
	 * @return
	 */ 
	public List<?> getMessage(int orgpersonid) 
	{
		List<?> query = null;
		try{
 		 query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Message.class)
				.createAlias("this.toorgperson", "toorgperson")
				.createAlias("toorgperson.organizationid","organizationid")
				.createAlias("toorgperson.personid", "topersonid")
				.createAlias("this.fromorgperson", "fromorgperson")
				.createAlias("fromorgperson.personid", "frompersonid")
  				.add(Restrictions.eq("toorgperson.orgpersonid",orgpersonid))
  				.add(Restrictions.eq("this.messagestatus","A"))
  				.addOrder(Order.desc("this.messagedate"))
 				.setProjection(Projections.projectionList()
 						.add(Projections.property("this.messageid").as("messageid"))
						.add(Projections.property("this.message").as("message"))
						.add(Projections.property("this.messagedate").as("messagedate"))
						.add(Projections.property("frompersonid.firstName").as("fromUserFirstName"))
						.add(Projections.property("frompersonid.lastName").as("fromUserLastName"))
						.add(Projections.property("fromorgperson.orgpersonid").as("fromUserpersonid"))						
						.add(Projections.property("topersonid.firstName").as("toUserFirstName"))
						.add(Projections.property("topersonid.lastName").as("toUserLastName"))
						.add(Projections.property("toorgperson.orgpersonid").as("toUserpersonid"))
						.add(Projections.property("organizationid.organizationid").as("organizationid"))						
  						).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
  		} catch (Exception e){
  			if(MessageServicelogger.isInfoEnabled())
			{
  				MessageServicelogger.error("error in getMessage() in MessageService",e);
			};
		}
		return  query; 
 	} 

	public List<?> getMyMessage(int orgpersonid) 
	{
		List<?> query = null;
		try{
 		 query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Message.class)
				.createAlias("this.toorgperson", "toorgperson")
				.createAlias("toorgperson.personid", "topersonid")
				.createAlias("toorgperson.organizationid","toorganization")
				.createAlias("this.fromorgperson", "fromorgperson")
				.createAlias("fromorgperson.organizationid","fromorganization")
				.createAlias("fromorgperson.personid", "frompersonid")				
  				.add(Restrictions.eq("fromorgperson.orgpersonid",orgpersonid))
  				.add(Restrictions.eq("this.messagestatus","A"))
  				.addOrder(Order.desc("this.messagedate"))
 				.setProjection(Projections.projectionList()
 						.add(Projections.property("this.messageid").as("messageid")) 
						.add(Projections.property("this.message").as("message"))
						.add(Projections.property("this.messagedate").as("messagedate"))
						.add(Projections.property("frompersonid.firstName").as("fromUserFirstName"))
						.add(Projections.property("frompersonid.lastName").as("fromUserLastName"))
						.add(Projections.property("fromorgperson.orgpersonid").as("fromUserpersonid"))
						.add(Projections.property("fromorganization.organizationid").as("fromorganizationid"))						
						.add(Projections.property("topersonid.firstName").as("toUserFirstName"))
						.add(Projections.property("topersonid.lastName").as("toUserLastName"))
						.add(Projections.property("toorgperson.orgpersonid").as("toUserpersonid"))
						.add(Projections.property("toorganization.organizationid").as("toorganizationid"))
						
  						)
 						.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
  		} catch (Exception e){
  			if(MessageServicelogger.isInfoEnabled())
			{
  				MessageServicelogger.error("error in getMessage() in MessageService",e);
			};
		}
		return  query; 
 	} 
	

	/**
	 * 
	 * @param postid
	 * @return 
	 */
	public int deleteMessage(int messageid){ 
 		try {
			messageid = getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Message set messagestatus='D' where messageid="+messageid+"").executeUpdate();
		} catch (Exception e) {
			MessageServicelogger.error("error in deleteMessage() in MessageService",e);
		}
		return messageid;
	}
	
	
}
