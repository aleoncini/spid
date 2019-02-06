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
    @Produces(MediaType.APPLICATION_XHTML_XML)
    public Response doGet(@QueryParam("SAMLRequest") String deflatedEncodedSamlAuthnRequest) {
        logger.info(config.toString());
        return Response.status(200).entity(config.toString()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("load")
    public Response doPost(@QueryParam("SAMLRequest") String deflatedEncodedSamlAuthnRequest) {
        logger.info("=================> HTTP-POST binding");
        return Response.status(200).entity("").build();
    }

}
