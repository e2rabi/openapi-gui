package com.errabi.sandbox.services;

import com.errabi.sandbox.entities.Role;
import com.errabi.sandbox.entities.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.gen.*;
import com.nimbusds.jwt.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {
    private final RSAKey rsaJWK;
    private final JWSSigner signer;
    @Value("${jwt.issuer}") // spring expression language
    private String issuer ;
    public JwtTokenService() throws JOSEException {
        // Generate RSA key pair
        this.rsaJWK = new RSAKeyGenerator(2048).keyID("123").generate();
        this.signer = new RSASSASigner(rsaJWK);
    }

    public String generateToken(User user) throws JOSEException {
        // Prepare JWT with claims set
        String roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(","));

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .issueTime(new Date())
                .expirationTime(new Date(new Date().getTime() + 60 * 60 * 1000)) // 1 hour expiration
                .audience(user.getUsername())
                .claim("scope", roles)
                .claim("userLangage", "en_US")
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.getKeyID()).build(),
                claimsSet);

        // Compute the RSA signature
        signedJWT.sign(signer);

        // Serialize to compact form
        return signedJWT.serialize();
    }
}
