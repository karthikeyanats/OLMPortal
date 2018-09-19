package com.igrandee.product.ecloud.action.royalty;



import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.igrandee.core.login.util.AbstractIGActionSupport;
import com.igrandee.product.ecloud.helper.CustomStatus;
import com.igrandee.product.ecloud.model.Orgperson;
import com.igrandee.product.ecloud.model.royalty.Royaltyconfig;
import com.igrandee.product.ecloud.model.royalty.Royaltytype;
import com.igrandee.product.ecloud.service.royalty.RoyaltyConfigService;
import com.igrandee.product.ecloud.viewmodel.royalty.RoyaltyConfigVM;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.core.InjectParam;

@Path("/royalty")
public class RoyaltyConfigAction extends AbstractIGActionSupport{

	private static final long serialVersionUID = 1L;
	private static Logger royaltyConfigActionLogger	= Logger.getLogger(RoyaltyConfigAction.class); 
	@InjectParam
	RoyaltyConfigService royaltyConfigService;

	@InjectParam
	SessionFactory sessionFactory;

	@InjectParam
	Royaltyconfig royaltyconfig;

	@InjectParam
	Orgperson orgperson;

	@InjectParam
	Royaltytype royaltytype;

	int firstResult=0;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(RoyaltyConfigVM royaltyConfigVM){
		try{
			if(royaltyConfigVM!=null){

				royaltyconfig.setAuthorroyalty(Integer.parseInt(royaltyConfigVM.getAuthorroyalty()));
				orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
				royaltyconfig.setCreatedby(orgperson);
				royaltyconfig.setDateofcreation(new Date());
				royaltyconfig.setEffectfrom(new Date(Long.parseLong(royaltyConfigVM.getEffectfrom())));
				royaltyconfig.setEffectto(new Date(Long.parseLong(royaltyConfigVM.getEffectto())+86399999));
				royaltyconfig.setOrgroyalty(Integer.parseInt(royaltyConfigVM.getOrgroyalty()));
				royaltyconfig.setRoyaltyconfigstatus('A');			
				royaltytype.setRoyaltytypeid(Integer.parseInt(royaltyConfigVM.getRoyaltyconfigtype()));
				royaltyconfig.setRoyaltytype(royaltytype);

				Calendar calendar=Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				Date today=calendar.getTime();

				if((royaltyconfig.getEffectfrom().after(today)||royaltyconfig.getEffectfrom().equals(today))&&royaltyconfig.getEffectto().after(today)){
					
					if(royaltyconfig.getEffectfrom().equals(today)){
						if(royaltyConfigService.isDefaultRoyaltyTaken(royaltytype.getRoyaltytypeid())){
							return Response.ok().entity(CustomStatus.DEFAULTINPOWER).build();
						}
					}
					
					if(royaltyConfigService.checkSchedule(royaltyconfig.getEffectfrom(),royaltyconfig.getEffectto(),0,Integer.parseInt(royaltyConfigVM.getRoyaltyconfigtype()))){

						int status=royaltyConfigService.save(royaltyconfig);
						if(status>0){
							if(new Date().after(royaltyconfig.getEffectfrom())&&new Date().before(royaltyconfig.getEffectto()))
								return Response.ok().entity(Status.OK).build();
							else
								return Response.ok().entity(Status.CREATED).build();
						}
						return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();
					}
					else{
						return Response.ok().entity(CustomStatus.SCHEDULECONFLICT).build();
					}
				}
				else{
					return Response.ok().entity(CustomStatus.PASTDATE).build();
				}
			}
		}
		catch(Exception e){
			if(royaltyConfigActionLogger.isInfoEnabled()){
				royaltyConfigActionLogger.info("error in create() in RoyaltyConfigAction"+e);
			}
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();

	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoyaltyConfig(@QueryParam("type")int type,@QueryParam("page")int page){
		List<?> royaltyConfig=null;
		try{
			if(page>0){
				this.firstResult=page*20-20;
			}
			else if(page<0){
				this.firstResult=page*20+20;
			}
			royaltyConfig=royaltyConfigService.getRoyalty(type,page,this.firstResult);
			/*if(royaltyConfig.size()==0&&page==0){
				royaltyConfig=royaltyConfigService.getDefaultRoyalty(type);
			}*/
			if(royaltyConfig==null){
				return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		catch(Exception e){
			if(royaltyConfigActionLogger.isInfoEnabled()){
				royaltyConfigActionLogger.info("error in getRoyaltyConfig() in RoyaltyConfigAction"+e);
			}
		}
		return Response.ok().entity(royaltyConfig).build();
	}

	@Path("/default")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createDefaultRoyalty(RoyaltyConfigVM royaltyConfigVM){
		try{
			if(royaltyConfigVM!=null){
				List<HashMap<?, ?>> defaultRoyalty=royaltyConfigService.getDefaultRoyalty(Integer.parseInt(royaltyConfigVM.getRoyaltyconfigtype()));
				if(defaultRoyalty.size()!=0){
					royaltyconfig.setId((Integer)defaultRoyalty.get(0).get("id"));
				}
				else{
					royaltyconfig.setId(0);
				}
				royaltyconfig.setAuthorroyalty(Integer.parseInt(royaltyConfigVM.getAuthorroyalty()));
				orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
				royaltyconfig.setCreatedby(orgperson);
				royaltyconfig.setDateofcreation(new Date());
				royaltyconfig.setOrgroyalty(Integer.parseInt(royaltyConfigVM.getOrgroyalty()));
				royaltyconfig.setRoyaltyconfigstatus('E');
				royaltytype.setRoyaltytypeid(Integer.parseInt(royaltyConfigVM.getRoyaltyconfigtype()));
				royaltyconfig.setRoyaltytype(royaltytype);
				Royaltyconfig config=royaltyConfigService.saveOrUpdate(royaltyconfig);
				if(config!=null){
					return Response.ok().entity(Status.CREATED).build();
				}
				return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		catch(Exception e){
			if(royaltyConfigActionLogger.isInfoEnabled()){
				royaltyConfigActionLogger.info("error in createDefaultRoyalty() in RoyaltyConfigAction"+e);
			}
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();		

	}
	
	@Path("/default")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDefaultRoyalty(@QueryParam("type")int type){
		
		if(type!=0){
		List<?> defaultRoyalty=royaltyConfigService.getDefaultRoyalty(type);
			return Response.ok().entity(defaultRoyalty).build();
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();
		
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteById(@QueryParam("id")int id){
		if(!royaltyConfigService.isFinishedOrCurrent(id)){
			Session session=sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			try{
				Royaltyconfig royaltyconfig=(Royaltyconfig) session.get(Royaltyconfig.class, id);
				royaltyconfig.setRoyaltyconfigstatus('D');
				session.update(royaltyconfig);
				transaction.commit();
				session.close();
			}
			catch(Exception e){
				if(royaltyConfigActionLogger.isInfoEnabled()){
					royaltyConfigActionLogger.info("error in deleteById() in RoyaltyConfigAction"+e);
					transaction.rollback();
					session.close();
					return Response.ok().entity(Status.BAD_REQUEST).build();
				}
			}
			return Response.ok().entity(Status.ACCEPTED).build();
		}
		return Response.ok().entity(CustomStatus.FINISHEDCURRENT).build();
	}

	@Path("/current")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCurrentById(@QueryParam("id")int id){
		if(!royaltyConfigService.isRoyaltyTaken(id)){
			Session session=sessionFactory.openSession();
			Transaction transaction=session.beginTransaction();
			try{
				Royaltyconfig royaltyconfig=(Royaltyconfig) session.get(Royaltyconfig.class, id);
				royaltyconfig.setRoyaltyconfigstatus('D');
				session.update(royaltyconfig);
				transaction.commit();
				session.close();
			}
			catch(Exception e){
				if(royaltyConfigActionLogger.isInfoEnabled()){
					royaltyConfigActionLogger.info("error in deleteById() in RoyaltyConfigAction"+e);
					transaction.rollback();
					session.close();
					return Response.ok().entity(Status.BAD_REQUEST).build();
				}
			}
			return Response.ok().entity(Status.ACCEPTED).build();
		}
		return Response.ok().entity(CustomStatus.FINISHEDCURRENT).build();
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateById(RoyaltyConfigVM royaltyConfigVM,@QueryParam("id")int id){
		try{
			if(royaltyConfigVM!=null){
				royaltyconfig.setId(id);
				royaltyconfig.setAuthorroyalty(Integer.parseInt(royaltyConfigVM.getAuthorroyalty()));
				orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
				royaltyconfig.setCreatedby(orgperson);
				royaltyconfig.setDateofcreation(new Date());
				royaltyconfig.setEffectfrom(new Date(Long.parseLong(royaltyConfigVM.getEffectfrom())));
				royaltyconfig.setEffectto(new Date(Long.parseLong(royaltyConfigVM.getEffectto())+86399999));
				royaltyconfig.setOrgroyalty(Integer.parseInt(royaltyConfigVM.getOrgroyalty()));
				royaltyconfig.setRoyaltyconfigstatus('A');
				royaltytype.setRoyaltytypeid(Integer.parseInt(royaltyConfigVM.getRoyaltyconfigtype()));
				royaltyconfig.setRoyaltytype(royaltytype);

				if(!royaltyConfigService.isFinishedOrCurrent(id)){

					Calendar calendar=Calendar.getInstance();
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MILLISECOND, 0);
					Date today=calendar.getTime();

					if((royaltyconfig.getEffectfrom().after(today)||royaltyconfig.getEffectfrom().equals(today))&&royaltyconfig.getEffectto().after(today)){

						if(royaltyConfigService.checkSchedule(royaltyconfig.getEffectfrom(), royaltyconfig.getEffectto(),id,Integer.parseInt(royaltyConfigVM.getRoyaltyconfigtype()))){

							Royaltyconfig royaltyconfigstatus=royaltyConfigService.update(royaltyconfig);

							if(royaltyconfigstatus!=null){
								if(new Date().after(royaltyconfig.getEffectfrom())&&new Date().before(royaltyconfig.getEffectto()))
									return Response.ok().entity(Status.OK).build();
								else
									return Response.ok().entity(Status.CREATED).build();
							}

							return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();			      
						}
						return Response.ok().entity(CustomStatus.SCHEDULECONFLICT).build();
					}
					else{
						return Response.ok().entity(CustomStatus.PASTDATE).build();
					}
				}			
				else{
					return Response.ok().entity(CustomStatus.FINISHEDCURRENT).build();
				}
			}
		}
		catch(Exception e){
			if(royaltyConfigActionLogger.isInfoEnabled()){
				royaltyConfigActionLogger.info("error in updateById() in RoyaltyConfigAction"+e);
			}
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();

	}

	@PUT
	@Path("/current")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCurrentRoyaltyById(RoyaltyConfigVM royaltyConfigVM,@QueryParam("id")int id){
		try{
			if(royaltyConfigVM!=null){
				royaltyconfig.setId(id);
				royaltyconfig.setAuthorroyalty(Integer.parseInt(royaltyConfigVM.getAuthorroyalty()));
				orgperson.setOrgpersonid(Integer.parseInt(getAttribute("orgpersonid")));
				royaltyconfig.setCreatedby(orgperson);
				royaltyconfig.setDateofcreation(new Date());
				royaltyconfig.setEffectfrom(new Date(Long.parseLong(royaltyConfigVM.getEffectfrom())));
				royaltyconfig.setEffectto(new Date(Long.parseLong(royaltyConfigVM.getEffectto())+86399999));
				royaltyconfig.setOrgroyalty(Integer.parseInt(royaltyConfigVM.getOrgroyalty()));
				royaltyconfig.setRoyaltyconfigstatus('A');
				royaltytype.setRoyaltytypeid(Integer.parseInt(royaltyConfigVM.getRoyaltyconfigtype()));
				royaltyconfig.setRoyaltytype(royaltytype);

				if(!royaltyConfigService.isRoyaltyTaken(id)){

					Calendar calendar=Calendar.getInstance();
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MILLISECOND, 0);
					Date today=calendar.getTime();

					if(royaltyconfig.getEffectfrom().after(today)||royaltyconfig.getEffectfrom().equals(today)||royaltyconfig.getEffectto().after(today)){

						if(royaltyConfigService.checkSchedule(royaltyconfig.getEffectfrom(), royaltyconfig.getEffectto(),id,Integer.parseInt(royaltyConfigVM.getRoyaltyconfigtype()))){

							Royaltyconfig royaltyconfigstatus=royaltyConfigService.update(royaltyconfig);

							if(royaltyconfigstatus!=null){
								if(new Date().after(royaltyconfig.getEffectfrom())&&new Date().before(royaltyconfig.getEffectto()))
									return Response.ok().entity(Status.OK).build();
								else
									return Response.ok().entity(Status.CREATED).build();
							}

							return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();			      
						}
						return Response.ok().entity(CustomStatus.SCHEDULECONFLICT).build();
					}
					else{
						return Response.ok().entity(CustomStatus.PASTDATE).build();
					}
				}
				else{
					return Response.ok().entity(CustomStatus.FINISHEDCURRENT).build();
				}
			}
		}
		catch(Exception e){
			if(royaltyConfigActionLogger.isInfoEnabled()){
				royaltyConfigActionLogger.info("error in updateById() in RoyaltyConfigAction"+e);
			}
		}
		return Response.ok().entity(Status.BAD_REQUEST).build();

	}

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam("id")int id){
		RoyaltyConfigVM royaltyConfigVM=new RoyaltyConfigVM();
		Royaltyconfig royaltyconfig;
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		try{
			royaltyconfig=(Royaltyconfig) session.get(Royaltyconfig.class, id);
			royaltyConfigVM.setAuthorroyalty(""+royaltyconfig.getAuthorroyalty());
			royaltyConfigVM.setCreatedby(""+royaltyconfig.getCreatedby());
			royaltyConfigVM.setDateofcreation(""+royaltyconfig.getDateofcreation());
			royaltyConfigVM.setEffectfrom(""+royaltyconfig.getEffectfrom());
			royaltyConfigVM.setEffectto(""+royaltyconfig.getEffectto());
			royaltyConfigVM.setOrgroyalty(""+royaltyconfig.getOrgroyalty());
			royaltyConfigVM.setRoyaltyconfigstatus(""+royaltyconfig.getRoyaltyconfigstatus());
			royaltyConfigVM.setRoyaltyconfigtype(""+royaltyconfig.getRoyaltytype().getRoyaltytypeid());

			transaction.commit();
			session.close();
		}
		catch(Exception e){
			if(royaltyConfigActionLogger.isInfoEnabled()){
				royaltyConfigActionLogger.info("error in getById() in RoyaltyConfigAction"+e);
				transaction.rollback();
				session.close();
				return Response.ok().entity(Status.BAD_REQUEST).build();
			}
		}
		return Response.ok().entity(royaltyConfigVM).build();
	}
	@Path("/expiration")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response royaltyCheck(){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DATE,1);
		Date nextDay=calendar.getTime();

		if(getAttribute("orgstatus").equalsIgnoreCase("M")){
			List<?> expiryDate=null;
			if(royaltyConfigService.isRoyaltySet(nextDay).get("course")&&royaltyConfigService.isRoyaltySet(nextDay).get("live")){
				return Response.ok().entity(Status.ACCEPTED).build();
			}
			else if(royaltyConfigService.isRoyaltySet(nextDay).get("course")&&!royaltyConfigService.isRoyaltySet(nextDay).get("live")){
				expiryDate=royaltyConfigService.expiryDate(new Integer[]{2});             
			}
			else if(!royaltyConfigService.isRoyaltySet(nextDay).get("course")&&royaltyConfigService.isRoyaltySet(nextDay).get("live")){
				expiryDate=royaltyConfigService.expiryDate(new Integer[]{1});             
			}
			else{
				expiryDate=royaltyConfigService.expiryDate(new Integer[]{1,2}); 
			}
			if(!expiryDate.isEmpty()){
				return Response.ok().entity(expiryDate).build();
			}
			else{
				return Response.ok().entity(Status.INTERNAL_SERVER_ERROR).build();
			}
		}else{
			return Response.ok().entity(Status.ACCEPTED).build();
		}
	}
}