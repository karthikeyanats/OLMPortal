package com.igrandee.product.ecloud.service.admin;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Personallocation;

@Service
@Named
@Scope("prototype")
public class PersonallocationService  extends GenericEntityService<Personallocation, Integer>{

	@Override
	protected Class<Personallocation> entityClass() {
 		return Personallocation.class;
	}

}
