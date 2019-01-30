package it.redhat.spid.rest;

import it.redhat.spid.common.IDPResponse;
import it.redhat.spid.model.AuthNRequest;
import it.redhat.spid.model.SamlResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("saml")
public class AuthNRequestEndPoint {
    private static final Logger logger = LoggerFactory.getLogger("it.redhat.spid");

    @GET
    @Produces(MediaType.APPLICATION_XHTML_XML)
    public Response doGet(@QueryParam("SAMLRequest") String deflatedEncodedSamlAuthnRequest) {
        logger.info("=================> HTTP-Redirect binding");
        AuthNRequest authNRequest = new AuthNRequest().build(deflatedEncodedSamlAuthnRequest);
        logger.info(authNRequest.toString());

        return Response.status(200).entity(new IDPResponse().setSamlResponse(new SamlResponse()).toString()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@QueryParam("SAMLRequest") String deflatedEncodedSamlAuthnRequest) {
        logger.info("=================> HTTP-POST binding");
        return Response.status(200).entity("{\"binding\":\"HTTP-POST\"}\n" ).build();
    }
}
