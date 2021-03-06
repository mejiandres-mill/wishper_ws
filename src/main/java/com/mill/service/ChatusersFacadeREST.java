/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.service;

import com.mill.model.Chatusers;
import com.mill.model.ChatusersPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author mill2
 */
@Stateless
@Path("com.mill.model.chatusers")
public class ChatusersFacadeREST extends AbstractFacade<Chatusers> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    private ChatusersPK getPrimaryKey(PathSegment pathSegment)
    {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;usersIdusers=usersIdusersValue;chatsIdchats=chatsIdchatsValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.mill.model.ChatusersPK key = new com.mill.model.ChatusersPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> usersIdusers = map.get("usersIdusers");
        if (usersIdusers != null && !usersIdusers.isEmpty())
        {
            key.setUsersIdusers(new java.lang.Integer(usersIdusers.get(0)));
        }
        java.util.List<String> chatsIdchats = map.get("chatsIdchats");
        if (chatsIdchats != null && !chatsIdchats.isEmpty())
        {
            key.setChatsIdchats(new java.lang.Integer(chatsIdchats.get(0)));
        }
        return key;
    }

    public ChatusersFacadeREST()
    {
        super(Chatusers.class);
    }

    @POST
    @Override
    @Consumes(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public void create(Chatusers entity)
    {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public void edit(@PathParam("id") PathSegment id, Chatusers entity)
    {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id)
    {
        com.mill.model.ChatusersPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public Chatusers find(@PathParam("id") PathSegment id)
    {
        com.mill.model.ChatusersPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public List<Chatusers> findAll()
    {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public List<Chatusers> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to)
    {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST()
    {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }
    
}
