package it.redhat.spid.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SamlResponse {
    private AuthNRequest authNRequest;
    private String uuid;
    private String issueIstant;

    public SamlResponse(){
        this.uuid = UUID.randomUUID().toString();
        DateFormat UTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
        this.issueIstant = UTCFormat.format(new Date());
    }

    public AuthNRequest getAuthNRequest() {
        return authNRequest;
    }

    public SamlResponse setAuthNRequest(AuthNRequest authNRequest) {
        this.authNRequest = authNRequest;
        return this;
    }

    public String getUuid(){
        return this.uuid;
    }

    public String getAction(){
        return authNRequest.getAssertionConsumerServiceURL();
    }

    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("<samlp:Response ").append("\n");
        buffer.append("    xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"").append("\n");
        buffer.append("    xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\"").append("\n");
        buffer.append("    ID=\"").append(uuid).append("\"\n");
        buffer.append("    Version=\"2.0\"").append("\n");
        buffer.append("    IssueInstant=\"").append(issueIstant).append("\"\n");
        buffer.append("    InResponseTo=\"").append(authNRequest.getID()).append("\"\n");
        buffer.append("    Destination=\"").append(authNRequest.getIssuer()).append("\">\n");
        buffer.append("  <samlp:Status>").append("\n");
        buffer.append("").append("\n");
        buffer.append("  </samlp:Status>").append("\n");
        buffer.append("    <samlp:StatusCode Value=\"urn:oasis:names:tc:SAML:2.0:status:Success\" />").append("\n");
        buffer.append("  <saml:Issuer").append("\n");
        buffer.append("      NameQualifier=\"").append("\n");
        buffer.append("").append("\n");
        buffer.append("").append("\n");
        buffer.append("").append("\n");
        buffer.append("").append("\n");
        buffer.append("").append("\n");
        buffer.append("").append("").append("\"\n");


        String ret = "<samlp:Response xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" \n" +
                "    xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\" \n" +
                "    ID=\"_66bc42b27638a8641536e534ec09727a8aaa\" \n" +
                "    Version=\"2.0\" \n" +
                "    InResponseTo=\"_4d38c302617b5bf98951e65b4cf304711e2166df20\" \n" +
                "    IssueInstant=\"2015-01-29T10:01:03Z\" \n" +
                "    Destination=\"http://spid-sp.it\">\n" +
                "\t<saml:Issuer NameQualifier=\"”https://spidIdp.spidIdpProvider.it”\n" +
                "\t    Format=\" urn:oasis:names:tc:SAML:2.0:nameid-format:entity\">\n" +
                "\t\tspididp.it \n" +
                "\t</saml:Issuer>\n" +
                "\t<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
                "\t\t............. \n" +
                "\t</ds:Signature>\n" +
                "\t<samlp:Status>\n" +
                "\t\t<samlp:StatusCode Value=\"urn:oasis:names:tc:SAML:2.0:status:Success\" /> \n" +
                "\t</samlp:Status>\n" +
                "</samlp:Response>";
        return buffer.toString();
    }
}
