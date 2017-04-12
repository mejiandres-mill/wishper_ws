/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill;

import com.mill.controller.CentralProcessor;
import com.mill.exceptions.WSException;
import com.mill.security.Secured;
import com.mill.utils.Constants;
import com.mill.utils.Message;
import com.mill.utils.Result;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.sql.SQLException;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author mill2
 */
@Stateless
@ApplicationPath("/wishper")
@Path("/ws")
public class WishperWS extends Application {

    private CentralProcessor processor;

    public WishperWS()
    {
        System.out.println("*********************** Constructor *****************************");
        processor = new CentralProcessor();
    }

    @POST
    @Secured
    @Path("/process")
    @Consumes("application/json")
    @Produces("application/json")
    public Response process(Message message, @Context SecurityContext securityContext)
    {
        long t0 = System.currentTimeMillis();
        System.out.println("Validating user...");
        Principal principal = securityContext.getUserPrincipal();
        String username = principal.getName();
        System.out.println("Access granted to " + username + "...");
        try
        {
            System.out.println("Processing request...");
            return Response.status(200).entity(processor.process(message, username)).build();
        } catch (Exception e)
        {
            e.printStackTrace();
            Result result = new Result();
            if (e instanceof SQLException)
            {
                result.setState(Constants.DATABASE_ERROR);
                result.setData(e.getMessage());
            } else if (e instanceof NamingException)
            {
                result.setState(Constants.JDNI_ERROR);
                result.setData(e.getMessage());

            } else if (e instanceof WSException)
            {
                WSException wse = (WSException) e;
                result.setState(wse.getErrorCode());
                result.setData(wse.getMessage());
            } else if (e instanceof NoSuchAlgorithmException)
            {
                result.setState(Constants.SHA256_ERROR);
                result.setData("Error de seguridad, no se encontró el algoritmo");
            } else if (e instanceof NotAuthorizedException)
            {
                result.setState(Constants.AUTHENTICATION_ERROR);
                result.setData(e.getMessage());
                return Response.status(Response.Status.UNAUTHORIZED).entity(result).build();
            } else
            {
                result.setState(0);
                result.setData("Ocurrió un error inesperado");
            }
            System.out.println("Finish processing...");
            return Response.status(500).entity(result).build();
        }finally{
            System.out.println("Request processed in " + (System.currentTimeMillis() - t0) + "ms...");
        }
    }
}
