package com.igrandee.product.ecloud.service.Pages.guest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Board;
import com.igrandee.product.ecloud.model.Course;
import com.igrandee.product.ecloud.model.Coursecategory;



@Service
@Named
@Scope("prototype")
public class GuestCoursesService extends GenericEntityService<Board, Integer>{
	private static Logger GuestCoursesServicelogger = Logger.getLogger(GuestCoursesService.class);

	@Override
	protected Class<Board> entityClass() {
		// TODO Auto-generated method stub
		return Board.class;
	}

	@SuppressWarnings("unchecked")
	public List<?> getBoardWiseCategories(String organizationid){
		try{
			List<Integer> boardlist=getCurrentSession().createCriteria(Course.class)
					.createAlias("this.coursecategory", "coursecategory")
					.createAlias("coursecategory.board", "board")
					.add(Restrictions.eq("coursecategory.coursecategorystatus","A"))
					.add(Restrictions.eq("board.boardstatus",'A'))
					.add(Restrictions.eq("this.coursestatus","A"))
					.setProjection(Projections.distinct(Projections.projectionList()
							.add(Projections.property("board.boardid").as("boardid"))
							)).setMaxResults(4).list();
			if(boardlist.size()>0){
				List<Integer> categorieslist = new ArrayList<Integer>();
				for(int i=0;i<boardlist.size();i++){
					categorieslist.addAll(getCurrentSession().createCriteria(Coursecategory.class)
							.createAlias("this.board", "board",org.hibernate.sql.JoinType.LEFT_OUTER_JOIN,Restrictions.eq("board.boardstatus",'A'))
							.createAlias("this.courses", "course")
							.add(Restrictions.eq("this.coursecategorystatus","A"))
							.add(Restrictions.eq("course.coursestatus","A"))
							.add(Restrictions.eq("board.boardid",boardlist.get(i)))
							.setProjection((Projections.distinct((Projections.projectionList()
									.add(Projections.property("this.coursecategoryid").as("coursecategoryid"))
									)))).setMaxResults(4).list());	
				}
				if(categorieslist.size()>0){
					List<?> topBoardsList=getCurrentSession().createCriteria(Coursecategory.class)
							.createAlias("this.board", "board")
							.createAlias("this.courses", "course")
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
									.add(Projections.property("course.courselogo").as("courselogo"))
									
									/*.add(Projections.property("price.courseid").as("courseid"))
									.add(Projections.property("price.price").as("price"))*/

									
									
									).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
					if(topBoardsList.size()>0){
						return topBoardsList;	
					}else{
						return null;
					}
				}else{
					return null;
				}
			}else{
				return null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			GuestCoursesServicelogger.error("error in getBoardWiseCategories in GuestCoursesService ",e);
			return null;
		}
	}
}
