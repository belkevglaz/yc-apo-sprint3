package ru.belkevglaz.ypa.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 */
@Provider
public class AlreadyExistsExceptionHandler implements ExceptionMapper<AlreadyExistsException> {
	@Override
	public Response toResponse(AlreadyExistsException e) {
		return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
	}
}
