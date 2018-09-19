/**
 * 
 */
package com.igrandee.product.ecloud.service.sat;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.satutil.model.LiveQuizAnswer;

/**
 * @author selvarajan_j
 *
 */
@Service
@Scope("prototype")
public class LiveQuizAnswerService extends GenericEntityService<LiveQuizAnswer, Integer>{
	static final Logger liveQuizAnswerService = Logger.getLogger(LiveQuizAnswerService.class);
	
	@Override
	protected Class<LiveQuizAnswer> entityClass() {
 		return LiveQuizAnswer.class;
	}
}