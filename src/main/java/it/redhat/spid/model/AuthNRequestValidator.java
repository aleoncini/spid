package it.redhat.spid.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthNRequestValidator {
    public static final int DEFAULT_CHECK_MODE = 0;
    public static final int STRICT_CHECK_MODE = 1;
    private AuthNRequest authNRequest = null;
    private int check_mode = DEFAULT_CHECK_MODE;
    private boolean isValid = true;
    private StringBuffer report;
    private IdentityProvider idp = new IdentityProvider();
    private ServiceProvider sp = new ServiceProvider();
    private boolean isHttpPostBinding = false;

    public AuthNRequest getAuthNRequest() {
        return authNRequest;
    }

    public AuthNRequestValidator setAuthNRequest(AuthNRequest authNRequest) {
        this.authNRequest = authNRequest;
        return this;
    }

    public IdentityProvider getIdentityProvider() {
        return idp;
    }

    public AuthNRequestValidator setIdentityProvider(IdentityProvider idp) {
        this.idp = idp;
        return this;
    }

    public ServiceProvider getServiceProvider() {
        return sp;
    }

    public AuthNRequestValidator setServiceProvider(ServiceProvider sp) {
        this.sp = sp;
        return this;
    }

    public int getCheck_mode() {
        return check_mode;
    }

    public AuthNRequestValidator setCheck_mode(int check_mode) {
        this.check_mode = check_mode;
        return this;
    }

    public boolean getIsHttpPostBinding() {
        return isHttpPostBinding;
    }

    public AuthNRequestValidator setIsHttpPostBinding() {
        this.isHttpPostBinding = true;
        return this;
    }

    private void initReport(){
        report = new StringBuffer("================================================================================\n");
    }

    public boolean isValid(){
        if (authNRequest == null){
            this.report.append("===> AuthNRequest not specified.\n");
            return false;
        }
        initReport();
        this.checkId();
        this.checkVersion();
        this.checkIssueIstant();
        this.checkDestination();
        this.checkForceAuthn();
        this.checkAssertionConsumerService();
        this.checkIsPassive();
        this.checkIssuer();
        this.checkNameIdPolicy();
        this.checkAuthnContext();
        this.checkSignature();
        return isValid;
    }

    private void checkId(){
        if ((authNRequest.getID() == null) || (authNRequest.getID().length() == 0)){
            this.report.append("===> ID not found.\n");
            isValid = false;
        }
    }

    private void checkVersion(){
        if ((authNRequest.getVersion() == null) || (authNRequest.getVersion().length() == 0)){
            this.report.append("===> Version not found\n");
            isValid = false;
        }
        if (! authNRequest.getVersion().equals("2.0")){
            this.report.append("===> Version is not 2.0: ").append(authNRequest.getVersion()).append("\n");
            isValid = false;
        }
    }

    private void checkIssueIstant(){
        if ((authNRequest.getIssueIstant() == null) || (authNRequest.getIssueIstant().length() == 0)){
            this.report.append("===> Issue Istant not found.\n");
            isValid = false;
        }
        DateFormat UTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
        Date issueIstant = null;
        try {
            issueIstant = UTCFormat.parse(authNRequest.getIssueIstant());
        } catch (Exception e) {
            this.report.append("===> Unable to parse Issue Istant in UTC format: ").append(e.getMessage()).append("\n");
            isValid = false;
        }
        if (issueIstant == null){
            this.report.append("===> Issue Istant not Valid.\n");
            isValid = false;
        }
    }

    private void checkDestination(){
        if ((authNRequest.getDestination() == null) || (authNRequest.getDestination().length() == 0)){
            this.report.append("===> Destination not found\n");
            isValid = false;
        }
        if (! authNRequest.getDestination().equals(idp.getEntityID())){
            this.report.append("===> Destination <").append(authNRequest.getDestination()).append("> not match idp entity ID: ").append(idp.getEntityID()).append("\n");
            isValid = false;
        }
    }

    private void checkForceAuthn(){
        if (authNRequest.getAuthnContextClassRef().equals(AuthNRequest.SPIDL1_CLASS)){
            return;
        }
        if ((authNRequest.getForceAuthn() == null) || (authNRequest.getForceAuthn().length() == 0)){
            this.report.append("===> Attribute Force Authn not Found.\n");
            isValid = false;
        }
        if (! authNRequest.getForceAuthn().equalsIgnoreCase("true")){
            this.report.append("===> Attribute Force Authn must be set TRUE if authn level is 2 or 3.\n");
            isValid = false;
        }
    }

    private void checkAssertionConsumerService(){
        if (check_mode == STRICT_CHECK_MODE){
            checkStrictlyAssertionConsumerService();
            return;
        }
        if ((authNRequest.getAssertionConsumerServiceURL() == null) || (authNRequest.getAssertionConsumerServiceURL().length() == 0)){
            this.report.append("===> Attribute Assertion Consumer URL not Found.\n");
            isValid = false;
        }
        if (! authNRequest.getAssertionConsumerServiceURL().equals(sp.getAssertionConsumerService())){
            this.report.append("===> Attribute Assertion Consumer URL ").append(authNRequest.getAssertionConsumerServiceURL()).append(" not match SP ").append(sp.getAssertionConsumerService()).append("\n");
            isValid = false;
        }
        if ((authNRequest.getProtocolBinding() == null) || (authNRequest.getProtocolBinding().length() == 0)){
            this.report.append("===> Attribute Protocol Binding not Found.\n");
            isValid = false;
        }
        if (! authNRequest.getProtocolBinding().equals("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST")){
            this.report.append("===> Attribute Protocol Binding value not correct: ").append(authNRequest.getProtocolBinding()).append("\n");
            isValid = false;
        }
    }

    private void checkIsPassive(){
        if (check_mode == DEFAULT_CHECK_MODE){
            return;
        }
        if ((authNRequest.getIsPassive() != null) && (authNRequest.getIsPassive().equalsIgnoreCase("true"))){
            this.report.append("===> Attribute isPassive must not be present.\n");
            isValid = false;
        }
    }

    private void checkIssuer(){
        if (check_mode == STRICT_CHECK_MODE){
            checkStrictlyIssuer();
            return;
        }
        if ((authNRequest.getIssuer() == null) || (authNRequest.getIssuer().length() == 0)){
            this.report.append("===> Element Issuer not Found.\n");
            isValid = false;
        }
        if (! authNRequest.getIssuer().equals(sp.getEntityID())){
            this.report.append("===> Issuer <").append(authNRequest.getIssuer()).append("> not match sp entity ID: ").append(sp.getEntityID()).append("\n");
            isValid = false;
        }
    }

    private void checkNameIdPolicy(){
        if (check_mode == DEFAULT_CHECK_MODE){
            return;
        }
        if ((authNRequest.getNameIDPolicyFormat() == null) || (authNRequest.getNameIDPolicyFormat().length() == 0)){
            this.report.append("===> Element NameIdPolicy not Found.\n");
            isValid = false;
        }
        if (! authNRequest.getNameIDPolicyFormat().equals("urn:oasis:names:tc:SAML:2.0:nameid-format:transient")){
            this.report.append("===> Name Id Policy Format not valid: ").append(authNRequest.getNameIDPolicyFormat()).append("\n");
            isValid = false;
        }
    }

    private void checkAuthnContext(){
        if ((authNRequest.getRequestedAuthnContextComparison() == null) || (authNRequest.getRequestedAuthnContextComparison().length() == 0)){
            this.report.append("===> Element Authn Context not Found.\n");
            isValid = false;
        }
    }

    private void checkSignature(){
        if (! isHttpPostBinding){
            return;
        }
        String signature = authNRequest.getSignature();
        if ((signature == null) || (signature.length() == 0)){
            this.report.append("===> Element Signature not Found.\n");
            isValid = false;
        }
        // validate signature to be implemented
    }

    private void checkStrictlyAssertionConsumerService(){
        this.report.append("===> Assertion Consumer Service - STRICT mode validation not yet implemented.\n");
        isValid = false;
    }

    private void checkStrictlyIssuer(){
        this.report.append("===> Issuer - STRICT mode validation not yet implemented.\n");
        isValid = false;
    }

    public String report(){
        return report.toString();
    }

}
