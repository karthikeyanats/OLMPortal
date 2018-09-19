package com.igrandee.product.ecloud.service.presenter;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Presenterappdetail;

@Named
@Service
@Scope("prototype")
public class PresenterAppDetailService extends GenericEntityService<Presenterappdetail, Integer>{

  	@Override
	protected Class<Presenterappdetail> entityClass() {
 		return Presenterappdetail.class;
	}
  
	
}
