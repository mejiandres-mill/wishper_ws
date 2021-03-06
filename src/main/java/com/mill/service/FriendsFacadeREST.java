/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.service;

import com.mill.model.Friends;
import com.mill.model.FriendsPK;
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
@Path("com.mill.model.friends")
public class FriendsFacadeREST extends AbstractFacade<Friends> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    private FriendsPK getPrimaryKey(PathSegment pathSegment)
    {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;friender=frienderValue;friendee=friendeeValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.mill.model.FriendsPK key = new com.mill.model.FriendsPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> friender = map.get("friender");
        if (friender != null && !friender.isEmpty())
        {
            key.setFriender(new java.lang.Integer(friender.get(0)));
        }
        java.util.List<String> friendee = map.get("friendee");
        if (friendee != null && !friendee.isEmpty())
        {
            key.setFriendee(new java.lang.Integer(friendee.get(0)));
        }
        return key;
    }

    public FriendsFacadeREST()
    {
        super(Friends.class);
    }

    @POST
    @Override
    @Consumes(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public void create(Friends entity)
    {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public void edit(@PathParam("id") PathSegment id, Friends entity)
    {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id)
    {
        com.mill.model.FriendsPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public Friends find(@PathParam("id") PathSegment id)
    {
        com.mill.model.FriendsPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public List<Friends> findAll()
    {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public List<Friends> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to)
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
