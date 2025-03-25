package be.integration.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CertServices {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public CertServices() {

        // MIGHT NEED TO CHANGE THE PATH IN PROD
        String certPath = System.getenv("PWD") + "/certificates/";

        try {
            Path privateKeyFile = Paths.get(certPath, "private_key.pem");
            Path publicKeyFile = Paths.get(certPath, "public_key.pem");

            this.privateKey = loadPrivateKey(privateKeyFile);
            this.publicKey = loadPublicKey(publicKeyFile);

            System.out.println("[CertServices] Keys loaded successfully from: " + certPath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load certificates: " + e.getMessage(), e);
        }
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    private PrivateKey loadPrivateKey(Path path) throws IOException, GeneralSecurityException {
        String keyPEM = new String(Files.readAllBytes(path))
                .replaceAll("-----BEGIN (.*)-----", "")
                .replaceAll("-----END (.*)-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytes = Base64.getDecoder().decode(keyPEM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }


    private PublicKey loadPublicKey(Path path) throws IOException, GeneralSecurityException {
        // READ CERTIFICATES FILES
        byte[] certBytes = Files.readAllBytes(path);

        // CREATE X509 CERTIFICATES
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        InputStream certStream = new ByteArrayInputStream(certBytes);
        X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(certStream);

        // GET PUBLIC KEY FROM CERTIFICATES
        return certificate.getPublicKey();
    }

}
