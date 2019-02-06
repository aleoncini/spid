package it.redhat.spid.model;

public class IdentityProvider {
    private String entityID;

    public IdentityProvider(){
        this.entityID = "https://spid.identityprovider.it";
    }

    public String getEntityID() {
        return entityID;
    }

    public IdentityProvider setEntityID(String entityID) {
        this.entityID = entityID;
        return this;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ ");
        buffer.append("\"entityID\": \"").append(entityID).append("\"");
        buffer.append(" }");
        return  buffer.toString();
    }

}
