package com.activity.activity1.resource;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

@Component
@Path("/calculator")
public class OutputResource {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response calculator(CalculatorRequest calculatorRequest) {
		
		CalculatorResponse calculatorResponse = new CalculatorResponse();
	
		if (calculatorRequest.getOperator().equals("") || calculatorRequest.getNum1().equals("") || calculatorRequest.getNum2().equals("")) {
			return Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("All fields are required.").type(MediaType.TEXT_PLAIN).build();
		} else if (!calculatorRequest.getNum1().matches("[0-9]+") || !calculatorRequest.getNum2().matches("[0-9]+")) {
			return Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Invalid number.").type(MediaType.TEXT_PLAIN).build();
		} else if (!calculatorRequest.getOperator().matches("[+-/*]+")) {
			return Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Invalid Operator").type(MediaType.TEXT_PLAIN).build();
		} else {
			int num1 = Integer.parseInt(calculatorRequest.getNum1());
			int num2 = Integer.parseInt(calculatorRequest.getNum2());
			
			if (calculatorRequest.getOperator().equals("+")) {
				calculatorResponse.setAction("Addition");
				calculatorResponse.setResult(num1 + num2);
				return Response.ok(calculatorResponse).status(Response.Status.OK).build();
			} else if (calculatorRequest.getOperator().equals("-")) {
				calculatorResponse.setAction("Subtraction");
				calculatorResponse.setResult(num1 - num2);
				return Response.ok(calculatorResponse).status(Response.Status.OK).build();
			} else if (calculatorRequest.getOperator().equals("*")) {
				calculatorResponse.setAction("Multiplication");
				calculatorResponse.setResult(num1 * num2);
				return Response.ok(calculatorResponse).status(Response.Status.OK).build();
			} else if (calculatorRequest.getOperator().equals("/")) {
				if (num2 == 0) {
					return Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Cannot divide by zero.").type(MediaType.TEXT_PLAIN).build();
				} else {
					calculatorResponse.setAction("Division");
					calculatorResponse.setResult(Double.parseDouble(String.format("%.5f", Double.valueOf(calculatorRequest.getNum1()) / Double.valueOf(calculatorRequest.getNum2()))));
					return Response.ok(calculatorResponse).status(Response.Status.OK).build();
				}
			} else {
				return Response.status(HttpServletResponse.SC_BAD_REQUEST).entity("Error.").type(MediaType.TEXT_PLAIN).build();
			}
		}

	}
}
