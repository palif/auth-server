package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.junit.Assert;
import org.junit.Test;

public class JWTUtilTest {
    /*
    Create a simple JWT, decode it, and assert the claims
 */
    @Test
    public void createAndDecodeJWT() {

        String jwtId = "user.id";
        String jwtIssuer = "KTH";
        String jwtSubject = "username";
        int jwtTimeToLive = 800000;

        String jwt = JWTUtil.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        );

        System.out.println(jwt);

        Claims claims = JWTUtil.decodeJWT(jwt);
        System.out.println(claims);

        Assert.assertEquals(jwtId, claims.getId());
        Assert.assertEquals(jwtIssuer, claims.getIssuer());
        Assert.assertEquals(jwtSubject, claims.getSubject());

    }

    /*
        Attempt to decode a bogus JWT and expect an exception
     */
    @Test(expected = MalformedJwtException.class)
    public void decodeShouldFail() {

        String notAJwt = "This is not a JWT";

        // This will fail with expected exception listed above
        Claims claims = JWTUtil.decodeJWT(notAJwt);

    }

    /*
    Create a simple JWT, modify it, and try to decode it
 */
    @Test(expected = SignatureException.class)
    public void createAndDecodeTamperedJWT() {

        String jwtId = "SOMEID1234";
        String jwtIssuer = "JWT Demo";
        String jwtSubject = "Andrew";
        int jwtTimeToLive = 800000;

        String jwt = JWTUtil.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        );

        // tamper with the JWT

        StringBuilder tamperedJwt = new StringBuilder(jwt);
        tamperedJwt.setCharAt(22, 'I');

        Assert.assertNotEquals(jwt, tamperedJwt);

        // this will fail with a SignatureException

        JWTUtil.decodeJWT(tamperedJwt.toString());

    }
}
