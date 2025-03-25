package be.integration.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

public class TokenServices {

    private static final String ISSUER = "amazi-auth-server";

    private long EXPIRATION_TIME_MILLIS = 1000 * 60 * 60; // 1 hour

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public TokenServices(CertServices certServices) {
        RSAPrivateKey privateKey = (RSAPrivateKey) certServices.getPrivateKey();
        RSAPublicKey publicKey = (RSAPublicKey) certServices.getPublicKey();

        this.algorithm = Algorithm.RSA256(publicKey, privateKey);
        this.verifier = JWT.require(this.algorithm)
                .withIssuer(ISSUER)
                .build();

        System.out.println("[TokenServices] Initialized with issuer: " + ISSUER);
    }

    public void setExpirationTimeMillis(long expirationTimeMillis) {
        this.EXPIRATION_TIME_MILLIS = expirationTimeMillis;
    }

    public String generateToken(String userId) {
        try {
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            Date expiryDate = new Date(nowMillis + EXPIRATION_TIME_MILLIS);

            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(userId)
                    .withIssuedAt(now)
                    .withExpiresAt(expiryDate)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            System.err.println("[TokenServices] Error creating JWT: " + exception.getMessage());
            return null;
        }
    }

    public DecodedJWT verifyToken(String token) {
        try {
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            System.err.println("[TokenServices] Token verification failed: " + exception.getMessage());
            return null;
        }
    }
}
