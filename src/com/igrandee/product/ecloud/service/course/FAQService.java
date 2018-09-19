package com.igrandee.product.ecloud.service.course;

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
import com.igrandee.product.ecloud.model.Faq;


@Service
@Named
@Scope("prototype")
public class FAQService extends GenericEntityService<Faq, Serializable> {
	
	private static Logger FAQServicelogger = Logger.getLogger(FAQService.class);
	@Override
	protected Class<Faq> entityClass() {
		// TODO Auto-generated method stub
		return Faq.class;
	}

	/**
	 * @author seenivasagan_p
	 * Method to get the questions for individual lecture
	 * @param courselectureid
	 * @return
	 */
	public List<?> getLectureQuestions(int courselectureid){
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courselecture.class)
					.createAlias("this.faq", "faq")
					.createAlias("faq.askedby", "askedby")
					.createAlias("askedby.personid", "askedpersonobj")
					.addOrder(Order.desc("faq.FAQid"))
					.add(Restrictions.eq("this.courselectureid",courselectureid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("faq.FAQquestion").as("FAQquestion"))
							.add(Projections.property("faq.FAQid").as("FAQid"))
							.add(Projections.property("faq.askeddate").as("askeddate"))
							.add(Projections.property("askedpersonobj.firstName").as("askedperson"))
							.add(Projections.property("askedpersonobj.personphotourl").as("photo"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			if(FAQServicelogger.isInfoEnabled()){
				FAQServicelogger.error("error in getLectureQuestions() in FAQService",e);
			}
		}
		return query;
	}
}
