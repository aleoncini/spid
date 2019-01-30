package it.redhat.spid.model;

public class SamlResponse {

    public String toString(){
        return "<samlp:Response xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" \n" +
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
    }
}
