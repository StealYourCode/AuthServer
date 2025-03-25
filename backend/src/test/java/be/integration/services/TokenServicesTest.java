//package be.integration.services;

//import junit.framework.TestCase;
//import com.auth0.jwt.interfaces.DecodedJWT;
//
//public class TokenServicesTest extends TestCase {
//    private TokenServices tokenServices;
//
//    @Override
//    protected void setUp() throws Exception {
//        CertServices certServices = new CertServices();
//        tokenServices = new TokenServices(certServices);
//    }
//
//    public void testGenerateToken() {
//        String token = tokenServices.generateToken("user123");
//        assertNotNull(token);
//    }
//
//    public void testVerifyValidToken() {
//        String token = tokenServices.generateToken("user123");
//        DecodedJWT decodedJWT = tokenServices.verifyToken(token);
//        assertNotNull(decodedJWT);
//        assertEquals("user123", decodedJWT.getSubject());
//    }
//
//    public void testVerifyInvalidToken() {
//        DecodedJWT decodedJWT = tokenServices.verifyToken("invalid.token.value");
//        assertNull(decodedJWT);
//    }
//}