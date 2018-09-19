package com.igrandee.product.ecloud.service.presenter;
import java.sql.Date;
import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Presenterappdetail;
import com.igrandee.product.ecloud.model.Presentertype;

@Named
@Service
@Scope("prototype")
public class PresentertypeService extends GenericEntityService<Presentertype, Integer>{

	
	private static final Logger PresentertypelLogger = Logger.getLogger(Presentertype.class);
	@Override
	protected Class<Presentertype> entityClass() {
 		return Presentertype.class;
	}
	public List<?> getPresentertype(String presenterid) {
		List<?> getpresentertypelist = null;
		try {
			getpresentertypelist = getCurrentSession().createCriteria(Presentertype.class)
					.add(Restrictions.eq("this.typestatus", 'A'))
					.add(Restrictions.eq("this.id", Integer.parseInt(presenterid)))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.id").as("id"))
							.add(Projections.property("this.typename").as("typename")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		} catch (Exception e) {
			PresentertypelLogger.error("error in  PresentertypeService in getPresentertype() ",e);
		}
		return getpresentertypelist;
	} 
	public List<?> getPresentertypelist(char status) {
		List<?> getpresentertypelist = null;
		try {
			getpresentertypelist = getCurrentSession().createCriteria(Presentertype.class)
					.add(Restrictions.eq("this.typestatus",status))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.id").as("id"))
							.add(Projections.property("this.typename").as("typename")))
							.addOrder(Order.desc("this.id"))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		} catch (Exception e) {
			PresentertypelLogger.error("error in  PresentertypeService in getPresentertypelist() ",e);		
		}
		return getpresentertypelist;
	} 
	
	public boolean deletePresentertypelist(int id){
		Query query=getCurrentSession().createQuery("update Presentertype p set p.typestatus=:status where p.id=:id");
		query.setParameter("status", 'D');
		query.setParameter("id", id);
		int result=query.executeUpdate();
		if(result>0){
			return true;
		}
		return false;
	}
	
	
	public Integer updatePresentertype(int presenterid,String typename){
		Integer updatetype = null;
		try	{
			
			Date date=new Date(System.currentTimeMillis());
			updatetype=getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Presentertype set typename='"+typename+"', dateofcreation='"+date+"'  where id='"+presenterid+"'").executeUpdate();
		}
		catch(Exception e){
			if(PresentertypelLogger.isInfoEnabled()){
				PresentertypelLogger.error("error in  PresentertypeService in updatePresentertype() ",e);		
			}
		}
		return updatetype;
		
	}
	public Integer statusPresentertype(int presenterid,char status){
		Integer statustype = null;
		try	{
			
			Date date=new Date(System.currentTimeMillis());
			statustype=getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Presentertype set typestatus='"+status+"', dateofcreation='"+date+"'  where  id='"+presenterid+"'").executeUpdate();
		}
		catch(Exception e){
			if(PresentertypelLogger.isInfoEnabled()){
				PresentertypelLogger.error("error in  PresentertypeService in statusPresentertype() ",e);		
			}
		}
		return statustype;
		
	}
	 
	public List<?> checkPresentertype(String presentername) {
		List<?> checkpresentertypelist = null;
		try {
			checkpresentertypelist = getCurrentSession().createCriteria(Presentertype.class)
					.add(Restrictions.eq("this.typename",presentername))
					.add(Restrictions.or(Restrictions.eq("this.typestatus",'A'), Restrictions.eq("this.typestatus",'T')))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.id").as("id"))
							.add(Projections.property("this.typename").as("typename"))
							.add(Projections.property("this.typestatus").as("typestatus")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

		} catch (Exception e) {
			PresentertypelLogger.error("error in  PresentertypeService in checkPresentertype() ",e);		
		}
		return checkpresentertypelist;
	} 
	public List<?> checkPresentertypeupdate(int id,String presentername) {
		List<?> checkpresentertypeupdate = null;
		try {
			checkpresentertypeupdate = getCurrentSession().createCriteria(Presenterappdetail.class)
					.createAlias("this.presentertype", "presentertype")
					.add(Restrictions.eq("presentertype.typename",presentername))
					.add(Restrictions.eq("presentertype.typestatus",'A'))
					.add(Restrictions.eq("presentertype.id",id))
					.setProjection(Projections.projectionList()
							.add(Projections.property("presentertype.id").as("id"))
							.add(Projections.property("presentertype.typename").as("typename")))
							.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
							
		} catch (Exception e) {
			e.printStackTrace();
			PresentertypelLogger.error("error in  PresentertypeService in checkPresentertypeupdate() ",e);		
		}
		return checkpresentertypeupdate;
	} 	
	
}
