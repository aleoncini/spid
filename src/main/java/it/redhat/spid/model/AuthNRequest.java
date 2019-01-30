package it.redhat.spid.model;

import it.redhat.spid.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class AuthNRequest {
    private static final Logger logger = LoggerFactory.getLogger("it.redhat.spid");
    private Node root = new Util().newEmptyDocument();

    public String getID(){
        return  ((Element)root).getAttribute("ID");
    }

    public String getVersion(){
        return  ((Element)root).getAttribute("Version");
    }

    public String getIssueIstant(){
        return  ((Element)root).getAttribute("IssueInstant");
    }

    public String getDestination(){
        return  ((Element)root).getAttribute("Destination");
    }

    public String getForceAuthn(){
        return  ((Element)root).getAttribute("ForceAuthn");
    }

    public String getAssertionConsumerServiceIndex(){
        return  ((Element)root).getAttribute("AssertionConsumerServiceIndex");
    }

    public String getIsPassive(){
        return  ((Element)root).getAttribute("IsPassive");
    }

    public AuthNRequest build(String encodedDeflatedSamlRequest){
        String authNRequest = new Util().decodeAuthnRequestXML(encodedDeflatedSamlRequest);
        if (authNRequest == null){
            return this;
        }
        this.root = new Util().getAuthnRequestNode(authNRequest);
        return this;
    }

    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("============> AuthNRequest\n");
        buffer.append("                    ID: ").append(this.getID()).append("\n");
        buffer.append("               version: ").append(this.getVersion()).append("\n");
        buffer.append("          issue istant: ").append(this.getIssueIstant()).append("\n");
        buffer.append("           destination: ").append(this.getDestination()).append("\n");
        buffer.append("           force authn: ").append(this.getForceAuthn()).append("\n");
        buffer.append("  consumer service ndx: ").append(this.getAssertionConsumerServiceIndex()).append("\n");
        buffer.append("            is passive: ").append(this.getIsPassive()).append("\n");
        return buffer.toString();
    }

}
