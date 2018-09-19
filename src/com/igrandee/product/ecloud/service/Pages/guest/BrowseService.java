package com.igrandee.product.ecloud.service.Pages.guest;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Coursecategory;

@Service
@Named
@Scope("prototype")
public class BrowseService extends GenericEntityService<Coursecategory, Integer>{

	@Override
	protected Class<Coursecategory> entityClass() {
		// TODO Auto-generated method stub
		return Coursecategory.class;
	}
	
	public List<?> getBoards(String organizationid){
		try{
			return getCurrentSession().createCriteria(Coursecategory.class)
					.createAlias("this.board", "board")
					.createAlias("this.courses", "course")
					.createAlias("course.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organization")
					.add(Restrictions.eq("organization.organizationid",Long.parseLong(organizationid)))
					.add(Restrictions.eq("board.boardstatus",'A'))
					.add(Restrictions.eq("this.coursecategorystatus","A"))
					.add(Restrictions.eq("course.coursestatus","A"))
					.setProjection(Projections.distinct(Projections.projectionList()
							.add(Projections.property("board.boardid").as("boardid"))
							.add(Projections.property("board.boardname").as("boardname"))
							.add(Projections.property("board.boardstatus").as("boardstatus")))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<?> getLimitedBoards(String organizationid){
		List<?> limitedBoardsList=null;
		try{
			limitedBoardsList= getCurrentSession().createCriteria(Coursecategory.class)
					.createAlias("this.board", "board")
					.createAlias("this.courses", "course")
					.createAlias("course.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organization")
					.add(Restrictions.eq("organization.organizationid",Long.parseLong(organizationid)))
					.add(Restrictions.eq("board.boardstatus",'A'))
					.add(Restrictions.eq("this.coursecategorystatus","A"))
					.add(Restrictions.eq("course.coursestatus","A"))
					.setProjection(Projections.distinct(Projections.projectionList()
							.add(Projections.property("board.boardid").as("boardid"))
							.add(Projections.property("board.boardname").as("boardname"))
							.add(Projections.property("board.boardstatus").as("boardstatus")))
							).setMaxResults(4).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return limitedBoardsList;
	}
	
	public List<?> getCategories(int boardid,String organizationid){
		try{
			return getCurrentSession().createCriteria(Coursecategory.class)
					.createAlias("this.board", "board")
					.createAlias("this.courses", "course")
					.createAlias("course.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organization")
					.add(Restrictions.eq("organization.organizationid",Long.parseLong(organizationid)))
					.add(Restrictions.eq("board.boardstatus",'A'))
					.add(Restrictions.eq("board.boardid",boardid))
					.add(Restrictions.eq("this.coursecategorystatus","A"))
					.add(Restrictions.eq("course.coursestatus","A"))
					.setProjection(Projections.distinct(Projections.projectionList()
							.add(Projections.property("this.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("this.coursecategoryname").as("coursecategoryname"))
							)).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
