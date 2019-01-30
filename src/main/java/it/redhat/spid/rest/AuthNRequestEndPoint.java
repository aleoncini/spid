package it.redhat.spid.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("saml")
public class AuthNRequestEndPoint {
    private static final Logger logger = LoggerFactory.getLogger("it.redhat.spid");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet() {
        logger.info("=================> HTTP-Redirect binding");
        return Response.status(200).entity("{\"binding\":\"HTTP-Redirect\"}\n" ).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost() {
        logger.info("=================> HTTP-POST binding");
        return Response.status(200).entity("{\"binding\":\"HTTP-POST\"}\n" ).build();
    }
}
