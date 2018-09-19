package com.igrandee.product.ecloud.service.plan;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Planpayment;

/**
 * 
 * @author muniyarasu_a
 *
 */
@Service
@Named
@Scope("prototype")
public class PlanPaymentService  extends GenericEntityService<Planpayment, Integer>{

	@Override
	protected Class<Planpayment> entityClass() {
		return Planpayment.class;
	}

}
