package it.redhat.spid.model;

public class ServiceProvider {
    private String assertionConsumerService;
    private String entityID;

    public ServiceProvider(){
        this.entityID = "http://spid.serviceprovider.it";
        this.assertionConsumerService = "http://spid.serviceprovider.it";
    }

    public String getEntityID() {
        return entityID;
    }

    public ServiceProvider setEntityID(String entityID) {
        this.entityID = entityID;
        return this;
    }

    public String getAssertionConsumerService() {
        return assertionConsumerService;
    }

    public ServiceProvider setAssertionConsumerService(String assertionConsumerService) {
        this.assertionConsumerService = assertionConsumerService;
        return this;
    }
}