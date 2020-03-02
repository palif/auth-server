package business.controller;

import business.configuration.Mapper;
import business.model.User;
import business.service.SecurityService;
import io.jsonwebtoken.Claims;
import util.JWTUtil;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;

@Path("/")
public class SecurityController {

    private SecurityService service = new SecurityService();

    /**
     * Register the user
     * @param user
     * @param headers
     * @return
     */
    /*
    @Path("register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(User user) {

        if (user == null) return Response.status(400).build();
        if (user.getEmail() == null) return Response.status(400).build();
        if (user.getPassword() == null) return Response.status(400).build();
        data.model.User entity = Mapper.map(user, data.model.User.class);
        if(service.register(entity)) {
            return Response.status(201).build();
        }
        return Response.status(400).build();
    }
     */

    /**
     * Authenticate the user by issuing a new token
     * @param user
     * @param headers
     * @return
     */
    @Path("authenticate")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(User user, @Context HttpHeaders headers) {

        if (user == null) return Response.status(400).build();

        data.model.User entity = Mapper.map(user, data.model.User.class);
        if(service.authenticate(entity)) {
            if(headers.getHeaderString(JWTUtil.getHeaderName()) == null) {
                String newToken = issueToken(user);
                return Response.status(201).header(JWTUtil.getHeaderName(), newToken).build();
            } else {
                String token = headers.getHeaderString(JWTUtil.getHeaderName());
                Claims claims = JWTUtil.decodeJWT(token);
                if (claims.getExpiration().getTime() < System.currentTimeMillis()) {
                    String newToken = issueToken(user);
                    return Response.status(201).header(JWTUtil.getHeaderName(), newToken).build();
                }
                return Response.status(200).build();
            }
        }

        return Response.status(401).build();

    }

    private static String issueToken(User user) {
        String jwt = JWTUtil.createJWT(String.valueOf(user.getId()), JWTUtil.getIssuer(), user.getEmail(), 86400000);
        return jwt;
    }

}
