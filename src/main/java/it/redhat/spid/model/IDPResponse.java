package it.redhat.spid.common;

import it.redhat.spid.model.SamlResponse;

public class IDPResponse {
    private SamlResponse samlResponse;

    public SamlResponse getSamlResponse() {
        return samlResponse;
    }

    public IDPResponse setSamlResponse(SamlResponse samlResponse) {
        this.samlResponse = samlResponse;
        return this;
    }

    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        buffer.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">");
        buffer.append("<head><title>IDP Response</title></head>");
        buffer.append("<body onload=\"document.forms[0].submit()\">");
        buffer.append("<form action=\"http://sso.leo:8080/auth/realms/lc_poc/broker/lc_saml/endpoint\" method=\"post\">");
        buffer.append("  <input type=\"hidden\" name=\"RelayState\" value=\"token\"/>");
        buffer.append("  <input type=\"hidden\" name=\"SAMLResponse\" value=\"").append(samlResponse.toString()).append("\"/>");
        buffer.append("  <input type=\"submit\" value=\"Submit\" />");
        buffer.append("</form></body></html>");
        return buffer.toString();
    }

}
