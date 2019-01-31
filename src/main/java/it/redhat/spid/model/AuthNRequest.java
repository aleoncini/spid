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
        String ret = "";
        try {
            ret = ((Element)root).getAttribute("ID");
        } catch (Exception e){
            //do nothing, probably the element is not contained in the assertion
        }
        return ret;
    }

    public String getVersion(){
        String ret = "";
        try {
            ret = ((Element)root).getAttribute("Version");
        } catch (Exception e){
            //do nothing, probably the element is not contained in the assertion
        }
        return ret;
    }

    public String getIssueIstant(){
        String ret = "";
        try {
            ret = ((Element)root).getAttribute("IssueInstant");
        } catch (Exception e){
            //do nothing, probably the element is not contained in the assertion
        }
        return ret;
    }

    public String getDestination(){
        String ret = "";
        try {
            ret = ((Element)root).getAttribute("Destination");
        } catch (Exception e){
            //do nothing, probably the element is not contained in the assertion
        }
        return ret;
    }

    public String getForceAuthn(){
        String ret = "";
        try {
            ret = ((Element)root).getAttribute("ForceAuthn");
        } catch (Exception e){
            //do nothing, probably the element is not contained in the assertion
        }
        return ret;
    }

    public String getAssertionConsumerServiceURL(){
        String ret = "";
        try {
            ret = ((Element)root).getAttribute("AssertionConsumerServiceURL");
        } catch (Exception e){
            //do nothing, probably the element is not contained in the assertion
        }
        return ret;
    }

    public String getProtocolBinding(){
        String ret = "";
        try {
            ret = ((Element)root).getAttribute("ProtocolBinding");
        } catch (Exception e){
            //do nothing, probably the element is not contained in the assertion
        }
        return ret;
    }

    public String getIsPassive(){
        String ret = "";
        try {
            ret = ((Element)root).getAttribute("IsPassive");
        } catch (Exception e){
            //do nothing, probably the element is not contained in the assertion
        }
        return ret;
    }

    public String getIssuer(){
        String ret = "";
        try {
            ret = ((Element)root).getElementsByTagName("saml:Issuer").item(0).getTextContent();
        } catch (Exception e){
            //do nothing, probably the element is not contained in the assertion
        }
        return ret;
    }

    public String getIssuerFormat(){
        String ret = "";
        try {
            ret = ((Element)((Element)root).getElementsByTagName("saml:Issuer").item(0)).getAttribute("Format");
        } catch (Exception e){
            //do nothing, probably the element is not contained in the assertion
        }
        return ret;
    }

    public String getIssuerNameQualifier(){
        String ret = "";
        try {
            ret = ((Element)((Element)root).getElementsByTagName("saml:Issuer").item(0)).getAttribute("NameQualifier");
        } catch (Exception e){
            //do nothing, probably the element is not contained in the assertion
        }
        return ret;
    }

    public String getNameIDPolicyFormat(){
        String ret = "";
        try {
            ret = ((Element)((Element)root).getElementsByTagName("samlp:NameIDPolicy").item(0)).getAttribute("Format");
        } catch (Exception e){
            //do nothing, probably the element is not contained in the assertion
        }
        return ret;
    }

    public String getRequestedAuthnContextComparison(){
        String ret = "";
        try {
            ret = ((Element)((Element)root).getElementsByTagName("samlp:RequestedAuthnContext").item(0)).getAttribute("Comparison");
        } catch (Exception e){
            //do nothing, probably the element is not contained in the assertion
        }
        return ret;
    }

    public String getAuthnContextClassRef(){
        String ret = "";
        try {
            ((Element)((Element)root).getElementsByTagName("samlp:RequestedAuthnContext").item(0)).getElementsByTagName("saml:AuthnContextClassRef").item(0).getTextContent();
        } catch (Exception e){
            //do nothing, probably the element is not contained in the assertion
        }
        return ret;
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
        buffer.append("  consumer service url: ").append(this.getAssertionConsumerServiceURL()).append("\n");
        buffer.append("consumer protocol bind: ").append(this.getProtocolBinding()).append("\n");
        buffer.append("            is passive: ").append(this.getIsPassive()).append("\n");
        buffer.append("                Issuer: ").append(this.getIssuer()).append("\n");
        buffer.append("         Issuer format: ").append(this.getIssuerFormat()).append("\n");
        buffer.append("           Issuer name: ").append(this.getIssuerNameQualifier()).append("\n");
        buffer.append(" Name id policy format: ").append(this.getNameIDPolicyFormat()).append("\n");
        buffer.append("      Authn comparison: ").append(this.getRequestedAuthnContextComparison()).append("\n");
        buffer.append("       Authn class ref: ").append(this.getAuthnContextClassRef()).append("\n");
        return buffer.toString();
    }

}
