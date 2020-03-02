package business.filter;

import business.configuration.Secure;
import io.jsonwebtoken.Claims;
import util.JWTUtil;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class JWTTokenFilter implements ContainerRequestFilter {


    @Context
    HttpServletRequest request;

    @Override
    @Secure
    public void filter(ContainerRequestContext requestContext) {

        try {
            System.out.println("Filter executed.");
            System.out.println("Path " + requestContext.getUriInfo().getPath());
            System.out.println("Absolute Path " + requestContext.getUriInfo().getAbsolutePath().toString());
            System.out.println("Base URI " + requestContext.getUriInfo().getBaseUri());

            if (requestContext.getUriInfo().getAbsolutePath().toString().contains("/auth/")) {
                String authorizationHeader = requestContext.getHeaderString("AUTHORIZATION");
                if (authorizationHeader != null) {
                    String token = authorizationHeader.trim();
                    if (token != null) {
                        Claims claims = JWTUtil.decodeJWT(token);
                        if (claims.getExpiration().getTime() < System.currentTimeMillis()) return;
                        else requestContext.abortWith(Response.status(200).entity("Token is still valid").build());
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } else {
                requestContext.abortWith(Response.status(403).build());
            }


        } catch (Exception e) {
            requestContext.abortWith(Response.status(400).entity(e.getMessage()).build());
        }

    }
}
