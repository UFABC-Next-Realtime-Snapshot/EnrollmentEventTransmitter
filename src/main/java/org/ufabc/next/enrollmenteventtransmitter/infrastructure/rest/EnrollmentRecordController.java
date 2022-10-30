package org.ufabc.next.enrollmenteventtransmitter.infrastructure.rest;

import org.ufabc.next.enrollmenteventtransmitter.application.enrollmentrecord.EnrollmentRecordService;
import org.ufabc.next.enrollmenteventtransmitter.infrastructure.rest.request.StudentRequest;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/enrollment")
public class EnrollmentRecordController {
    @Inject
    EnrollmentRecordService enrollmentService;

    @POST
    public Response add(StudentRequest request) {


        return Response.noContent().build();
    }
}
