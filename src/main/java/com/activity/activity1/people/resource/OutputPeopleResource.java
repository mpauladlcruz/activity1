package com.activity.activity1.people.resource;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Path("/people")
public class OutputPeopleResource implements Serializable {
	ArrayList<PeopleResponse> list = new ArrayList();
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response people (PeopleRequest peopleRequest) throws ParseException {
		
		PeopleResponse peopleResponse = new PeopleResponse();
		
		boolean testDate = String.valueOf(peopleRequest.getBirthDate()).matches("([0-9]{2})/([0-9]{2})/([0-9]{4})");
		
		if (StringUtils.isEmpty(peopleRequest.getFirstName()) || StringUtils.isEmpty(peopleRequest.getLastName())
				|| StringUtils.isEmpty(peopleRequest.getBirthDate())) {
			return Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("All fields are required.").type(MediaType.TEXT_PLAIN).build();
		} 
		else if (testDate == false) {
			return Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Invalid date format. It should be MM/dd/yyyy").type(MediaType.TEXT_PLAIN).build();
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			
			Date dateformat = sdf.parse(peopleRequest.getBirthDate());
			
			String mf = new SimpleDateFormat("MM").format(dateformat);
			String yf = new SimpleDateFormat("yyyy").format(dateformat);
			String df = new SimpleDateFormat("dd").format(dateformat);
			
			int intDate= Integer.parseInt(df)+1;
			
			int length = String.valueOf(intDate).length();
			
			if (length <=1 ) {
				peopleResponse.setBirthDate(mf+"-"+0+intDate+"-"+yf);
			} else {
				peopleResponse.setBirthDate(mf+"-"+intDate+"-"+yf);
			}
			
			peopleResponse.setFirstName(peopleRequest.getFirstName());
			peopleResponse.setLastName(peopleRequest.getLastName());
			list.add(peopleResponse);
/*			return Response.ok().entity(peopleResponse).build();*/
			return Response.status(HttpServletResponse.SC_CREATED).entity(peopleResponse).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response peopleAll(PeopleRequest request) {
		return Response.ok().entity(list).build();
	}
	
}
