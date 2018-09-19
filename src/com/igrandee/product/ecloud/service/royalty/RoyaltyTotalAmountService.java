package com.igrandee.product.ecloud.service.royalty;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.royalty.Royaltytotalamount;

@Service
@Scope("prototype")
public class RoyaltyTotalAmountService extends GenericEntityService<Royaltytotalamount, Integer> {

	private static Logger royaltyTotalAmountService = Logger.getLogger(RoyaltyTotalAmountService.class);
	
	@Override
	protected Class<Royaltytotalamount> entityClass() {
		// TODO Auto-generated method stub
		return Royaltytotalamount.class;
	}

	public List<?> getRoyaltytotalamounts(){
		List<?> royaltytotalamountlist = null;
		try{
			royaltytotalamountlist = getCurrentSession().createCriteria(Royaltytotalamount.class)
	         .add(Restrictions.eq("royaltytotalamountstatus", 'A'))
	         .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		}
		catch(Exception e){
			if(royaltyTotalAmountService.isInfoEnabled()){
				royaltyTotalAmountService.info("error in getRoyaltytotalamounts() in RoyaltyTotalAmountService"+e);
			}
		}
		return royaltytotalamountlist;
	}
	public List<?> getRoyaltyTotalAmount(int authorid){
		List<?> royaltytotalamountlist = null;
		try{
			royaltytotalamountlist = getCurrentSession().createCriteria(Royaltytotalamount.class)
					.add(Restrictions.eq("authorid", authorid))
					.add(Restrictions.eq("royaltytotalamountstatus", 'A'))
					.setProjection(Projections.projectionList()
							.add(Projections.property("totalamount").as("totalamount"))
					.add(Projections.property("pendingamount").as("pendingamount"))
					.add(Projections.property("authorid").as("authorid")))
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(royaltyTotalAmountService.isInfoEnabled()){
				royaltyTotalAmountService.info("error in getRoyaltyTotalAmount() in RoyaltyTotalAmountService"+e);
			}
		}
		return royaltytotalamountlist;
	}
	
	
}
