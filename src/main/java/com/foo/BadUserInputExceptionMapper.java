package com.foo;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created with IntelliJ IDEA.
 * User: jose5124
 * Date: 7/6/12
 * Time: 4:57 PM
 * To change this template use File | Settings | File Templates.
 */
@Provider
public class BadUserInputExceptionMapper implements ExceptionMapper<BadUserInputException> {
    @Override
    public Response toResponse(BadUserInputException e) {
        return Response.status(400).entity("You're stupid").build();
    }
}
