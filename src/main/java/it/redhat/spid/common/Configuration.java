package it.redhat.spid.common;

import it.redhat.spid.model.IdentityProvider;
import it.redhat.spid.model.ServiceProvider;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("config")
public class Configuration {
    public final static int STANDARD_CHECK_MODE = 0;
    public final static int STRICT_CHECK_MODE = 1;
    private IdentityProvider identityProvider;
    private ServiceProvider serviceProvider;
    private String statusCode;
    private int checkMode = STANDARD_CHECK_MODE;

    public Configuration(){
        this.identityProvider = new IdentityProvider().setEntityID("http://localhost:8080");
        this.serviceProvider = new ServiceProvider()
                .setEntityID("http://sso.local:8080/auth/realms/")
                .setAssertionConsumerService("http://sso.local:8080/auth/realms/spid/broker/spid-client/endpoint");
        this.statusCode = "urn:oasis:names:tc:SAML:2.0:status:AuthnFailed";
    }

    public IdentityProvider getIdentityProvider() {
        return identityProvider;
    }

    public Configuration setIdentityProvider(IdentityProvider idp) {
        this.identityProvider = idp;
        return this;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public Configuration setServiceProvider(ServiceProvider sp) {
        this.serviceProvider = sp;
        return this;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public Configuration setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public int getCheckMode() {
        return checkMode;
    }

    public Configuration setCheckMode(int mode) {
        this.checkMode = mode;
        return this;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ ");
        buffer.append("\"statusCode\": \"").append(statusCode).append("\", ");
        buffer.append("\"checkMode\": ").append(checkMode).append(", ");
        buffer.append("\"serviceProvider\": ").append(serviceProvider).append(", ");
        buffer.append("\"identityProvider\": ").append(identityProvider);
        buffer.append(" }");
        return  buffer.toString();
    }

}
