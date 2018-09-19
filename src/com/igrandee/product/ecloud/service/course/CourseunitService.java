package com.igrandee.product.ecloud.service.course;

import java.io.Serializable;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Courselecture;

@Service
@Named
@Scope("prototype")
public class CourseunitService extends GenericEntityService<Courselecture, Serializable>{

	@Override
	protected Class<Courselecture> entityClass() {
		// TODO Auto-generated method stub
		return Courselecture.class;
	}

}
