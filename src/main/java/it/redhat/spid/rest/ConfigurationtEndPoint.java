package it.redhat.spid.rest;

import it.redhat.spid.common.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("config")
public class ConfigurationtEndPoint {
    private static final Logger logger = LoggerFactory.getLogger("org.beccaria.domotica");

    @Inject
    @Named("config")
    private Configuration config;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet() {
        logger.info(config.toString());
        return Response.status(200).entity(config.toString()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sp")
    public Response getServiceProvider() {
        logger.info("\n" + config.getServiceProvider().toString());
        return Response.status(200).entity(config.getServiceProvider().toString()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sp")
    public Response updateServiceProvider(@QueryParam("entityID") String entityID) {
        logger.info("=================> updateServiceProvider");
        return Response.status(200).entity("{\"test\":\"test\"}\n").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("idp")
    public Response getIdentityProvider() {
        logger.info("\n" + config.getIdentityProvider().toString());
        return Response.status(200).entity(config.getIdentityProvider().toString()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("idp")
    public Response updateIdentityProvider(@QueryParam("entityID") String entityID) {
        logger.info("=================> updateIdentityProvider");
        return Response.status(200).entity("{\"test\":\"test\"}\n").build();
    }

}