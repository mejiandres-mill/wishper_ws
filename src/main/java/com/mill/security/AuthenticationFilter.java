/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.security;

import com.mill.model.Users;
import com.mill.session.UsersFacade;
import com.mill.utils.HibernateUtil;
import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mill2
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter{
    
    UsersFacade usersFacade = lookupUsersFacadeBean();
    
    @Override
    public void filter(ContainerRequestContext requestcontext) throws IOException
    {
        long t0 = System.currentTimeMillis();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        String authorizationHeader = requestcontext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        if(authorizationHeader == null || !authorizationHeader.startsWith("Basic "))
                throw new NotAuthorizedException("El mensaje debe tener clave de autorización");
        
        String token = authorizationHeader.substring("Basic ".length()).trim();
        try
        {
            final Users user = validateToken(token);
            requestcontext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal()
                {
                    return new Principal() {
                        @Override
                        public String getName()
                        {
                            return user.getEmail();
                        }
                    };
                }

                @Override
                public boolean isUserInRole(String role)
                {
                    return true;
                }

                @Override
                public boolean isSecure()
                {
                    return false;
                }

                @Override
                public String getAuthenticationScheme()
                {
                    return null;
                }
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
            tx.rollback();
            requestcontext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }finally{
            System.out.println("Validation ended in " + (System.currentTimeMillis() - t0) + "ms..");
        }
    }
    
    private Users validateToken(String token) throws Exception
    {
        
        return usersFacade.getUserByApiKey(token);
    }
    
    private UsersFacade lookupUsersFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (UsersFacade) c.lookup("java:global/wishper_ws/UsersFacade!com.mill.session.UsersFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
