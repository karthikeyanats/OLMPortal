package com.igrandee.product.ecloud.service.royalty;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.royalty.Royaltyconfig;

@Service
@Scope("prototype")
public class RoyaltyConfigService extends GenericEntityService<Royaltyconfig, Integer> {

	private static Logger royaltyConfigServiceLogger = Logger.getLogger(RoyaltyConfigService.class);
	
	@Override
	protected Class<Royaltyconfig> entityClass() {
		return Royaltyconfig.class;
	}
	
	public List<?> getRoyalty(int type,int page,int firstResult){
		List<?> royaltyConfigList = null;
		Criteria criteria=getCurrentSession().createCriteria(Royaltyconfig.class)
				.createAlias("createdby", "orgperson")
				.createAlias("royaltytype", "royaltytype")
				.setFirstResult(Math.abs(firstResult)).setMaxResults(20)
				.add(Restrictions.eq("royaltyconfigstatus", 'A'))
				.add(Restrictions.eq("royaltytype.royaltytypeid", type));
		
		if(page>0){
			criteria.addOrder(Order.asc("effectfrom"))
			.setFirstResult(Math.abs(firstResult)).setMaxResults(20)
			.add(Restrictions.gt("effectfrom", new Date()));
		}
		else if(page==0){
			criteria.setMaxResults(1)
			.add(Restrictions.gt("effectto", new Date()))
			.add(Restrictions.lt("effectfrom", new Date()));
		}
		else{
			criteria.addOrder(Order.desc("effectfrom"))
			.setFirstResult(Math.abs(firstResult)).setMaxResults(20)
			.addOrder(Order.desc("effectto"))
			.add(Restrictions.lt("effectto", new Date()));
		}
		try{
			
			royaltyConfigList	=	criteria.setProjection(Projections.projectionList()	
					.add(Projections.property("id").as("id"))
					.add(Projections.property("authorroyalty").as("authorroyalty"))
					.add(Projections.property("orgperson.orgpersonid").as("createdby"))
					.add(Projections.property("dateofcreation").as("dateofcreation"))
					.add(Projections.property("effectfrom").as("effectfrom"))
					.add(Projections.property("effectto").as("effectto"))
					.add(Projections.property("royaltyconfigstatus").as("royaltyconfigstatus"))
					.add(Projections.property("orgroyalty").as("orgroyalty")))
	.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(royaltyConfigServiceLogger.isInfoEnabled()){
				royaltyConfigServiceLogger.info("error in getRoyalty() in RoyaltyConfigService"+e);
			}
		}
		
		
		return royaltyConfigList;
		
	}
	
	public boolean checkSchedule(Date clientProposalFromDate,Date clientProposalToDate,int royaltyconfigid,int type){
	Criteria criteria=getCurrentSession().createCriteria(Royaltyconfig.class).createAlias("royaltytype", "royaltytype");		
	if(royaltyconfigid>0){
		criteria.add(Restrictions.ne("id", royaltyconfigid));
	}	
		List<?> list=criteria
				    .add(Restrictions.or(Restrictions.or(Restrictions.between("effectfrom", clientProposalFromDate, clientProposalToDate),Restrictions.between("effectto", clientProposalFromDate, clientProposalToDate)),
				    		Restrictions.and(Restrictions.le("effectfrom", clientProposalFromDate), Restrictions.ge("effectto", clientProposalToDate))))						    
		        	.add(Restrictions.eq("royaltyconfigstatus", 'A'))
		        	.add(Restrictions.eq("royaltytype.royaltytypeid", type))
		        	.setProjection(Projections.projectionList()
		        	.add(Projections.property("id").as("id"))).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date fromDate=new Date(calendar.getTimeInMillis());
		if(list.isEmpty()&&clientProposalToDate.after(new Date())&&clientProposalToDate.after(clientProposalFromDate)&&(clientProposalFromDate.after(new Date())||clientProposalFromDate.equals(fromDate)||royaltyconfigid>0)){
			return true;
		}		
		return false;
		
	}	

	public Royaltyconfig getRoyalty(String hql,int id){
		Royaltyconfig royaltyconfig = null;
		try{
		Query query=getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		query.setMaxResults(1);
		royaltyconfig=(Royaltyconfig) query.uniqueResult();
		}
		catch(Exception e){
			if(royaltyConfigServiceLogger.isInfoEnabled()){
				royaltyConfigServiceLogger.info("error in getRoyalty() in RoyaltyConfigService"+e);
			}
		}
		return royaltyconfig;
	}
	
	
	public boolean isFinishedOrCurrent(int id){
		Royaltyconfig royaltyconfig=(Royaltyconfig) getCurrentSession().get(Royaltyconfig.class, id);
		Date effectfrom=royaltyconfig.getEffectfrom();
		Date effectto=royaltyconfig.getEffectto();
		if(new Date().after(effectfrom) || (new Date().after(effectto))){
			return true;
		}
	return false;

  }
	
	public boolean isRoyaltyTaken(int royaltyid){
	List<?> ids=	     getCurrentSession().createCriteria(Royaltyconfig.class)
		         .createAlias("royaltypayments", "royaltypayments")
		         .add(Restrictions.eq("id", royaltyid))
		         .setProjection(Projections.property("royaltypayments.id"))
		         .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	if(ids.size()!=0){
		return true;
	}
	else{
		return false;
	}
	
	}
	
	public boolean isDefaultRoyaltyTaken(int royaltytypeid) throws ParseException{
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fromDate = df.parse(formatter.format(new Date())+" 00:00:00");
		Date toDate = df.parse(formatter.format(new Date())+" 23:59:59");
		
		List<?> defaultList=getCurrentSession().createCriteria(Royaltyconfig.class)
							.createAlias("royaltytype", "royaltytype")
							.createAlias("royaltypayments", "royaltypayments")
								.add(Restrictions.eq("royaltytype.royaltytypeid", royaltytypeid))
								.add(Restrictions.between("royaltypayments.dateofpyament", fromDate, toDate))
								.add(Restrictions.eq("royaltyconfigstatus", 'E'))
									.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		
		if(defaultList.size()>0){
			return true;
		}
		else{
			return false;
		}		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<?, ?>> getDefaultRoyalty(int type){
		List<?> royaltyConfigList = null;
		try{
			royaltyConfigList	=getCurrentSession().createCriteria(Royaltyconfig.class)
					.createAlias("createdby", "orgperson")
					.createAlias("royaltytype", "royaltytype")
					.add(Restrictions.eq("royaltyconfigstatus", 'E'))
					.add(Restrictions.eq("royaltytype.royaltytypeid", type))
			          .setProjection(Projections.projectionList()
			        		  .add(Projections.property("id").as("id"))
			        		  .add(Projections.property("authorroyalty").as("authorroyalty"))
			        		  .add(Projections.property("orgperson.orgpersonid").as("createdby"))
			        		  .add(Projections.property("dateofcreation").as("dateofcreation"))
			        		  .add(Projections.property("effectfrom").as("effectfrom"))
			        		  .add(Projections.property("effectto").as("effectto"))
			        		  .add(Projections.property("royaltyconfigstatus").as("royaltyconfigstatus"))
			        		  .add(Projections.property("orgroyalty").as("orgroyalty")))
			        		  	.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();	
		}
		catch(Exception e){
			if(royaltyConfigServiceLogger.isInfoEnabled()){
				royaltyConfigServiceLogger.info("error in getDefaultRoyalty() in RoyaltyConfigService"+e);
			}
		}
		return (List<HashMap<?, ?>>) royaltyConfigList;
	}
	
    public Map<String,Boolean> isRoyaltySet(Date nextDay){   	
    	  
    	Integer id= (Integer) getCurrentSession().createCriteria(Royaltyconfig.class)
    			.createAlias("royaltytype", "royaltytype")
    	     .add(Restrictions.le("effectfrom", nextDay))
    	     .add(Restrictions.gt("effectto", nextDay))
    	     .add(Restrictions.eq("royaltyconfigstatus", 'A'))
    	     .add(Restrictions.eq("royaltytype.royaltytypeid", 1))
    	       .setProjection(Projections.property("id").as("id"))
    	       .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
    	
    	Integer idLive=(Integer) getCurrentSession().createCriteria(Royaltyconfig.class)
    						.createAlias("royaltytype", "royaltytype")
    							.add(Restrictions.le("effectfrom", nextDay))
    							.add(Restrictions.gt("effectto", nextDay))
    							.add(Restrictions.eq("royaltyconfigstatus", 'A'))
    							.add(Restrictions.eq("royaltytype.royaltytypeid", 2))
    							.setProjection(Projections.property("id").as("id"))
    							.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).uniqueResult();
    	
    	HashMap<String,Boolean> status=new HashMap<String, Boolean>();
    	
    	if(id!=null&&idLive!=null){
    		status.put("course", true);
    		status.put("live", true);
    		return status;
    	}
    	else if(id==null&&idLive!=null){
    		status.put("course", false);
    		status.put("live", true);
    		return status;
    	}  
    	else if(id!=null&&idLive==null){
    		status.put("course", true);
    		status.put("live", false);
    		return status;
    	}  
    	else{
    		status.put("course", false);
    		status.put("live", false);
    		return status;
    	}
    }
    
    public List<?> expiryDate(Integer[] royaltytypes){
    	
    	Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 00);
		Date todaysDate=new Date(calendar.getTimeInMillis());
     
    return  getCurrentSession().createCriteria(Royaltyconfig.class)
    	   .createAlias("royaltytype", "royaltytype")
    	   .add(Restrictions.le("effectto", todaysDate))
    	   .add(Restrictions.eq("royaltyconfigstatus", 'A'))
    	   .add(Restrictions.in("royaltytype.royaltytypeid", royaltytypes))
    	   .setProjection(Projections.projectionList()
    			   .add(Projections.max("effectto").as("expirydate"))
    			   .add(Projections.property("royaltytype.royaltytypeid").as("royaltytypeid"))
    			   .add(Projections.property("royaltytype.royaltytype").as("royaltytype"))
    			   .add(Projections.groupProperty("royaltytype.royaltytypeid"))) 
    	   .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
    	   
    }
}