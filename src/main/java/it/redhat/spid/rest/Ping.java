package it.redhat.spid.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("ping")
public class Ping {
    private static final Logger logger = LoggerFactory.getLogger("it.redhat.spid");
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet() {
        logger.info("===================> received ping...");
        return Response.status(200).entity("{\"ping\":\"pong\"}\n" ).build();
    }
}
