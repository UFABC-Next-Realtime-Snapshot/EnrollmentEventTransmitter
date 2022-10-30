package org.ufabc.next.enrollmenteventtransmitter.infrastructure.rest;

import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/student")
public class StudentController {

    // /**
    //  * @param id
    //  */
    // @GET
    // void getById(@QueryParam String id) {
    // }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{id}")
    public Response getStudentByID(@PathParam("id") String id ) {
        return Response.status(200).
            entity("id: " + id).build();
    }
}