package it.redhat.spid.model;

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
        buffer.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">").append("\n");
        buffer.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">").append("\n");
        buffer.append("<head><title>IDP Response</title></head>").append("\n");
        buffer.append("<body onload=\"document.forms[0].submit()\">").append("\n");
        buffer.append("<form action=\"").append(samlResponse.getAction()).append("\" method=\"POST\">").append("\n");
        buffer.append("  <input type=\"hidden\" name=\"RelayState\" value=\"token\"/>").append("\n");
        buffer.append("  <input type=\"hidden\" name=\"SAMLResponse\" value=\"").append(samlResponse.toString()).append("\"/>");
        buffer.append("  <input type=\"submit\" value=\"Submit\" />").append("\n");
        buffer.append("</form></body></html>").append("\n");
        return buffer.toString();
    }

}
