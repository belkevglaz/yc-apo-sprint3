package ru.belkevglaz.ypa.exceptions;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {
	@Override
	public Response toResponse(NotFoundException e) {
		return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
	}
}
