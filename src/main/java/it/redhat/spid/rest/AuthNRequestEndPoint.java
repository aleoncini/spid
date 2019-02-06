package it.redhat.spid.rest;

import it.redhat.spid.model.IDPResponse;
import it.redhat.spid.model.*;
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
        return Response.status(200).entity(processRequest(deflatedEncodedSamlAuthnRequest)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response doPost(@QueryParam("SAMLRequest") String deflatedEncodedSamlAuthnRequest) {
        logger.info("=================> HTTP-POST binding");
        return Response.status(200).entity(processRequest(deflatedEncodedSamlAuthnRequest, true)).build();
    }

    private String processRequest(String deflatedEncodedSamlAuthnRequest){
        return this.processRequest(deflatedEncodedSamlAuthnRequest, false);
    }

    private String processRequest(String deflatedEncodedSamlAuthnRequest, boolean isPostBinding){
        AuthNRequest authNRequest = new AuthNRequest().build(deflatedEncodedSamlAuthnRequest);
        logger.info(authNRequest.toString());

        // To Be Implemented
        // set Service Provider by configuration
        ServiceProvider sp = new ServiceProvider()
                .setAssertionConsumerService("http://sso.leo:8080/auth/realms/lc_poc/broker/lc_saml/endpoint")
                .setEntityID("http://sso.leo:8080/auth/realms/lc_poc");
        IdentityProvider idp = new IdentityProvider()
                .setEntityID("http://localhost:8080/saml");
        AuthNRequestValidator validator = new AuthNRequestValidator()
                .setAuthNRequest(authNRequest)
                .setIdentityProvider(idp)
                .setServiceProvider(sp);
        if (isPostBinding){
            validator.setIsHttpPostBinding();
        }
        validator.isValid();
        logger.info(validator.report());
        return new IDPResponse().setSamlResponse(new SamlResponse()).toString();
    }
}
