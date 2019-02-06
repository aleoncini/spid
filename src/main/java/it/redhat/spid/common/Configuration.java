package it.redhat.spid.common;

import it.redhat.spid.model.IdentityProvider;
import it.redhat.spid.model.ServiceProvider;

import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named("config")
public class Configuration {
    private IdentityProvider identityProvider;
    private ServiceProvider serviceProvider;
    private String statusCode;

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

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ ");
        buffer.append("\"statusCode\": \"").append(statusCode).append("\",");
        buffer.append("\"serviceProvider\": ").append(serviceProvider).append(",");
        buffer.append("\"identityProvider\": ").append(identityProvider);
        buffer.append(" }");
        return  buffer.toString();
    }

}
