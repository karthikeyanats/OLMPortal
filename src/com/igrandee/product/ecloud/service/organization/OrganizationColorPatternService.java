package com.igrandee.product.ecloud.service.organization;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.core.organization.model.Organization;

@Service
@Named
@Scope("prototype")
public class OrganizationColorPatternService  extends GenericEntityService<Organization, Serializable>{

	@Override
	protected Class<Organization> entityClass() {
		// TODO Auto-generated method stub
		return Organization.class;
	}

	public List<?> getActualThemeColor(String orgid){
		List<?> orgThemeColorList = null;
		try{
			String query = "SELECT a.id,a.orgid,a.patternid,b.id as cpatternid,b.patternnumber FROM orgcolorpattern a,colorpattern b " +
					"where a.orgid='"+orgid+"' and a.patternid=b.id and a.status='A' and b.status='A'";
			orgThemeColorList=
					getCurrentSession().createSQLQuery(query)
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return orgThemeColorList;
	}
	
	public List<?> getAvailableThemeColor(){
		List<?> themeColorsList = null;
		try{
			themeColorsList=
					getCurrentSession().createSQLQuery("SELECT * FROM colorpattern b " +
							"where b.status='A'")
					.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return themeColorsList	;
	}
	
	public int updateOrgThemeColor(String patternid,String id){
		int themeColorsList = 0;
		try{
			themeColorsList=
					getCurrentSession().createSQLQuery("UPDATE orgcolorpattern " +
							"SET patternid = '"+patternid+"' where orgid = '"+id+"'")
					.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return themeColorsList	;
	}
	

}
