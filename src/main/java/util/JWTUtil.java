package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.JacksonDeserializer;
import io.jsonwebtoken.io.JacksonSerializer;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/*
    Our simple static class that demonstrates how to create and decode JWTs.
 */
public class JWTUtil {

    // The secret key. This should be in a property file NOT under source
    // control and not hard coded in real life. We're putting it here for
    // simplicity.
    private static String ISSUER = "KTH";
    private static String SECRET_KEY = "4pmzFifTZ5EcDFjLkKxFRqqJ692egwCf3IsN7rpEQ5P5B";
    private static String HEADER_NAME = "AUTHORIZATION";

    //Sample method to construct a JWT
    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        ObjectMapper objectMapper = getMyObjectMapper();

        builder.serializeToJsonWith(new JacksonSerializer<>(objectMapper));

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static Claims decodeJWT(String jwt) {
        ObjectMapper objectMapper;
        Claims claims;
        try {
            objectMapper = getMyObjectMapper();

            //This line will throw an exception if it is not a signed JWS (as expected)
            claims = Jwts.parser()
                    .deserializeJsonWith(new JacksonDeserializer<>(objectMapper))
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(jwt).getBody();
            return claims;
        } catch (SignatureException e) {
            System.err.println("Exception dec");
        } finally {
            objectMapper = null;
            claims = null;
        }
        return null;
    }

    public static String getIssuer() {
        return ISSUER;
    }

    public static String getHeaderName() { return HEADER_NAME; }

    private static ObjectMapper getMyObjectMapper() {
        return new ObjectMapper();
    }

}