package com.igrandee.product.ecloud.service.course;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.igrandee.bean.service.GenericEntityService;
import com.igrandee.product.ecloud.model.Note;

@Service
@Named
@Scope("prototype")
public class NotesService extends GenericEntityService<Note, Serializable>{

	private static Logger NotesServicelogger = Logger.getLogger(NotesService.class);

	@Override
	protected Class<Note> entityClass() {
		// TODO Auto-generated method stub
		return Note.class;
	}
	/**
	 * @author seenivasagan_p
	 * Method to get the notes for individual lecture
	 * @param courselectureid
	 * @return
	 */
	public List<?> loadNotes(int courselectureid,int orgpersonid) {
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courselecture.class)
					.createAlias("this.notes", "notes")
					.createAlias("notes.orgperson", "orgperson")
					.add(Restrictions.eq("notes.notesstatus","A"))
					.add(Restrictions.eq("orgperson.orgpersonid",orgpersonid))
					.addOrder(Order.desc("notes.createddate"))

					.add(Restrictions.eq("this.courselectureid",courselectureid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("notes.notesid").as("notesid"))
							.add(Projections.property("notes.notesdescription").as("notesdescription"))
							.add(Projections.property("notes.createddate").as("createddate")) 
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(NotesServicelogger.isInfoEnabled())	{
				NotesServicelogger.error("error in loadNotes() in NotesService",e);
			}
		}
		return query;
	}
	/**
	 * @author seenivasagan_p
	 * Method to get individual note's description
	 * @param notesid
	 * @return
	 */
	public List<?> loadNoteDescription(int notesid) {
		List<?> query =null;
		try	{
			query = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Note.class)
					.add(Restrictions.eq("this.notesid",notesid))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.notesid").as("notesid"))
							.add(Projections.property("this.notesdescription").as("notesdescription"))
							.add(Projections.property("this.notespath").as("notespath"))
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
			
		}
		catch(Exception e)	{
			if(NotesServicelogger.isInfoEnabled())	{
				NotesServicelogger.error("error in loadNoteDescription() in NotesService",e);
			}
		}
		return query;
	}

	public String saveUpdatedNoteDescription(int notesid,String notesdescription) {
		String noteupdated="";
		try	{
			String hql = "UPDATE Note set notesdescription = :notesdescription "  + 
					"WHERE notesid = :notesid";
			Query query = getCurrentSession().createQuery(hql);
			query.setParameter("notesdescription", notesdescription);
			query.setParameter("notesid", notesid);
			query.executeUpdate();
			noteupdated="updated";
		}
		catch(Exception e)	{
			if(NotesServicelogger.isInfoEnabled())	{
				NotesServicelogger.error("error in loadNoteDescription() in NotesService",e);
			}
		}
		return noteupdated;
	}

	public String updateNoteStatus(int notesid,String status) {
		String notedeleted="";
		try	{
			getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Note " +
					"set notesstatus='"+status+"' where notesid="+notesid +" ").executeUpdate();
			notedeleted="updated";
		}
		catch(Exception e)	{
			if(NotesServicelogger.isInfoEnabled())	{
				NotesServicelogger.error("error in deleteNote() in NotesService",e);
			}
		}
		return notedeleted;
	}



	public String saveNotePath(int notesid,String notespath) {
		String noteupdated="";
		System.out.println("notespath uis "+notespath);
		try	{
			getCurrentSession().createQuery("update com.igrandee.product.ecloud.model.Note " +
					"set notespath='"+notespath+"' where notesid="+notesid +" ").executeUpdate();
			noteupdated="updated";
		}
		catch(Exception e)	{
			if(NotesServicelogger.isInfoEnabled())	{
				NotesServicelogger.error("error in loadNoteDescription() in NotesService",e);
			}
			noteupdated="error";
		}
		return noteupdated;
	}
	
	
	
	public List<?> loadNotesByStatus(int courselectureid,String orgpersonid,String notetype,String notestatus) {
		List<?> query =null;
		try	{
			Criteria crit = getCurrentSession().createCriteria(com.igrandee.product.ecloud.model.Courselecture.class)
					.createAlias("this.notes", "notes")
					.createAlias("notes.orgperson", "orgperson")
					.createAlias("orgperson.personid", "person");
			
					
			if(orgpersonid!=null){
				crit.add(Restrictions.eq("orgperson.orgpersonid",Integer.parseInt(orgpersonid)));
			}		

			query = crit.add(Restrictions.eq("this.courselectureid",courselectureid))
					.add(Restrictions.eq("notes.notetype",notetype))
					.add(Restrictions.eq("notes.notesstatus",notestatus))
					.addOrder(Order.desc("notes.createddate"))
					.setProjection(Projections.projectionList()
							.add(Projections.property("this.courselectureid").as("courselectureid"))
							
							
							.add(Projections.property("person.firstName").as("firstName"))
							.add(Projections.property("person.lastName").as("lastName"))
							
							.add(Projections.property("notes.notesid").as("notesid"))
							.add(Projections.property("notes.notesdescription").as("notesdescription"))
							.add(Projections.property("notes.notespath").as("notespath"))
							.add(Projections.property("notes.createddate").as("createddate")) 
							
							).setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		}
		catch(Exception e)	{
			if(NotesServicelogger.isInfoEnabled())	{
				NotesServicelogger.error("error in loadNotes() in NotesService",e);
			}
		}
		return query;
	}
}