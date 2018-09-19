/**
 * 
 */
package com.igrandee.product.ecloud.service.sat;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.sat.Evaluation;

/**
 * @author selvarajan_j
 *
 */
@Service
@Scope("prototype")
@Named
public class EvaluationService  extends GenericEntityService<Evaluation, Integer>{
	static final Logger satQuestionServiceLogger = Logger.getLogger(EvaluationService.class);

	/* (non-Javadoc)
	 * @see com.igrandee.bean.service.GenericEntityService#entityClass()
	 */
	@Override
	protected Class<Evaluation> entityClass() {
 		return Evaluation.class;
	}
	
	/**
	 * Update Student Answer
	 * @param questionid
	 * @return 
	 */
	public int updateAnswer(int id,int answer,char state,int answerid,int attempt){
		try {
				id = getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.sat.Evaluation set studentanswer='"+answer+"',answerstate='"+state+"',answerid='"+answerid+"',attempt='"+attempt+"' where id="+id+"").executeUpdate();
		} catch (Exception e) {
			satQuestionServiceLogger.error("ERROR : in EvaluationService in  updateAnswer ",e);
		}
	 return id;
	}
	
}
