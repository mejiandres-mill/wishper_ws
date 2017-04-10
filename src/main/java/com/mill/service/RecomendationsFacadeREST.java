/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.service;

import com.mill.model.Recomendations;
import com.mill.model.RecomendationsPK;
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
@Path("com.mill.model.recomendations")
public class RecomendationsFacadeREST extends AbstractFacade<Recomendations> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    private RecomendationsPK getPrimaryKey(PathSegment pathSegment)
    {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;recomender=recomenderValue;receipient=receipientValue;productsIdproducts=productsIdproductsValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.mill.model.RecomendationsPK key = new com.mill.model.RecomendationsPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> recomender = map.get("recomender");
        if (recomender != null && !recomender.isEmpty())
        {
            key.setRecomender(new java.lang.Integer(recomender.get(0)));
        }
        java.util.List<String> receipient = map.get("receipient");
        if (receipient != null && !receipient.isEmpty())
        {
            key.setReceipient(new java.lang.Integer(receipient.get(0)));
        }
        java.util.List<String> productsIdproducts = map.get("productsIdproducts");
        if (productsIdproducts != null && !productsIdproducts.isEmpty())
        {
            key.setProductsIdproducts(new java.lang.Integer(productsIdproducts.get(0)));
        }
        return key;
    }

    public RecomendationsFacadeREST()
    {
        super(Recomendations.class);
    }

    @POST
    @Override
    @Consumes(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public void create(Recomendations entity)
    {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public void edit(@PathParam("id") PathSegment id, Recomendations entity)
    {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id)
    {
        com.mill.model.RecomendationsPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public Recomendations find(@PathParam("id") PathSegment id)
    {
        com.mill.model.RecomendationsPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public List<Recomendations> findAll()
    {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(
    {
        MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON
    })
    public List<Recomendations> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to)
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
