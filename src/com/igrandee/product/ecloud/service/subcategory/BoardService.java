package com.igrandee.product.ecloud.service.subcategory;

import java.util.List;

import javax.inject.Named;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Board;
import com.igrandee.product.ecloud.model.Coursecategory;

@Service
@Scope("prototype")
@Named
public class BoardService extends GenericEntityService<Board, Integer>{

	@Override
	protected Class<Board> entityClass() {
		// TODO Auto-generated method stub
		return null;
	}
	public List<?> getBoardList(String status){
		try {
			return getCurrentSession().createCriteria(Board.class)
					.add(Restrictions.eq("this.boardstatus", status.charAt(0)))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.boardid").as("boardid"))
							.add(Projections.property("this.boardname").as("boardname"))
							.add(Projections.property("this.boardstatus").as("boardstatus"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<?> getBoardavailability(String boardname){
		try {
			return getCurrentSession().createCriteria(Board.class)
					.add(Restrictions.in("this.boardstatus",new Character[] {'A','D'}))
					.add(Restrictions.like("this.boardname",boardname ).ignoreCase())
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.boardid").as("boardid"))
							).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isCoursesPublishedorDrafted(int boardid){
				
		List<?> courseCategoryList=  getCurrentSession().createCriteria(Coursecategory.class)
								.createAlias("courses", "courses")
								.createAlias("board", "board")
									.add(Restrictions.in("courses.coursestatus", new String[]{"A","P","T"}))
									.add(Restrictions.eq("board.boardid", boardid))
									.setProjection(Projections.projectionList()
											.add(Projections.distinct(Projections.property("coursecategoryid")))).list();
		  
		  if(courseCategoryList.size()!=0){
			  return true;
		  }
		  else{		  
			  return false;
		  }
		
	}

	public List<?> getBoardWiseCategories(String organizationid){
		try{
			List<?> boardlist=getCurrentSession().createCriteria(Coursecategory.class)
					.createAlias("this.board", "board")
					.createAlias("this.courses", "course")
					.add(Restrictions.eq("board.boardstatus",'A'))
					.add(Restrictions.eq("course.coursestatus","A"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("board.boardid").as("boardid"))
							).setMaxResults(4).list();

			List<?> categorieslist=getCurrentSession().createCriteria(Coursecategory.class)
					.createAlias("this.board", "board",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.eq("board.boardstatus",'A'))
					.createAlias("this.courses", "course")
					.add(Restrictions.eq("this.coursecategorystatus","A"))
					.add(Restrictions.eq("course.coursestatus","A"))
					.add(Restrictions.in("board.boardid",boardlist))
					.setProjection(Projections.distinct((Projections.projectionList()
							.add(Projections.property("this.coursecategoryid").as("coursecategoryid"))
							))).setMaxResults(4).list();

			List<?> topBoardsList=getCurrentSession().createCriteria(Coursecategory.class)
					.createAlias("this.board", "board",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.eq("board.boardstatus",'A'))
					.createAlias("this.courses", "course",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
					.createAlias("course.orgperson", "orgperson")
					.createAlias("orgperson.organizationid", "organization")

					.add(Restrictions.eq("organization.organizationid",Long.parseLong(organizationid)))
					.add(Restrictions.in("this.coursecategoryid",categorieslist))
					.add(Restrictions.eq("course.coursestatus","A"))

					.setProjection(Projections.projectionList()

							.add(Projections.property("board.boardid").as("boardid"))
							.add(Projections.property("board.boardname").as("boardname"))

							.add(Projections.property("this.coursecategoryid").as("coursecategoryid"))
							.add(Projections.property("this.coursecategoryname").as("coursecategoryname"))
							.add(Projections.property("this.coursecategorydescription").as("coursecategorydescription"))
							.add(Projections.property("this.filename").as("filename"))
							.add(Projections.property("this.filepath").as("filepath"))

							.add(Projections.property("course.courseid").as("courseid"))
							.add(Projections.property("course.coursetitle").as("coursetitle"))

							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			return topBoardsList;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
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
}
