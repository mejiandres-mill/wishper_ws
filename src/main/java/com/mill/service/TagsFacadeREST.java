/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.service;

import com.mill.model.Tags;
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

/**
 *
 * @author mill2
 */
@Stateless
@Path("com.mill.model.tags")
public class TagsFacadeREST extends AbstractFacade<Tags> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public TagsFacadeREST()
    {
        super(Tags.class);
    }

    @POST
    @Override
    @Consumes(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public void create(Tags entity)
    {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public void edit(@PathParam("id") Integer id, Tags entity)
    {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id)
    {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public Tags find(@PathParam("id") Integer id)
    {
        return super.find(id);
    }

    @GET
    @Override
    @Produces(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public List<Tags> findAll()
    {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public List<Tags> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to)
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
