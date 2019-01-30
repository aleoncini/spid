package it.redhat.spid.rest;

import it.redhat.spid.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("test")
public class RedirectBindEndPoint {

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

    @GET
    @Path("trace")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet(@QueryParam("SAMLRequest") String deflatedEncodedSamlAuthnRequest) {
        logger.info("=================> SAML HTTP-Redirect Binding");
        if (deflatedEncodedSamlAuthnRequest == null){
            return Response.ok("{\"success\":\"FALSE\",\"result\":\"Unable to get saml authn request object.\"}").build();
        }
        try {
            String authNRequest = new Util().decodeAuthnRequestXML(deflatedEncodedSamlAuthnRequest);
            logger.info("=================> DEFLATED and ENCODED SAML Token <============");
            logger.info(authNRequest );
            logger.info("=================> ENCODED SAML Token <=========================");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok("{\"success\":\"FALSE\",\"result\":\"Unable to decode saml request\"}").build();
        }
        return Response.ok("{\"success\":\"true\",\"result\":\"OK\"}").build();
    }

    @GET
    @Path("logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doLogOut(@Context HttpServletRequest request) {
        logger.info("===================> SAML Logout");
        // Do nothing
        return Response.ok("{\"success\":\"true\",\"result\":\"OK\"}").build();
    }

}