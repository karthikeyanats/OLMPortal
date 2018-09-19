package com.igrandee.product.ecloud.service.livesession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.livesession.Livesessionenrollment;

@Service
@Scope("prototype")
public class LiveSessionEnrollService extends GenericEntityService<Livesessionenrollment, Integer> {

	@Override
	protected Class<Livesessionenrollment> entityClass() {
		// TODO Auto-generated method stub
		return Livesessionenrollment.class;
	}

}
