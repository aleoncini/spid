package it.redhat.spid.rest;

import org.keycloak.adapters.saml.SamlPrincipal;
import org.keycloak.saml.processing.core.saml.v2.util.AssertionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("saml")
public class RedirectBindEndPoint {

    private static final Logger logger = LoggerFactory.getLogger("it.redhat.spid");

    @GET
    @Path("trace")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet(@Context HttpServletRequest request) {
        logger.info("===================> SAML HTTP-Redirect Binding");
        if (request == null){
            return Response.ok("{\"success\":\"FALSE\",\"result\":\"Unable to get request object.\"}").build();
        }
        String token = getAssertion(request);
        if (token == null){
            return Response.ok("{\"success\":\"FALSE\",\"result\":\"Keycloak IDToken not available.\"}").build();
        }
        return Response.ok("{\"success\":\"true\",\"result\":\"OK\"}").build();
    }

    private String getAssertion(HttpServletRequest request) {

        if (! (request.getUserPrincipal() instanceof SamlPrincipal)) {
            logger.info("=================> Error Principal not instance of SamlPrincipal");
        }
        SamlPrincipal principal = (SamlPrincipal) request.getUserPrincipal();
        String token = null;
        try {
            token = AssertionUtil.asString(principal.getAssertion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("=================> SAML Token <=================================");
        logger.info(token);
        logger.info("=================> END of SAML Token <==========================");

        return token;
    }

}