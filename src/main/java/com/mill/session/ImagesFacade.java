/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.session;

import com.mill.model.Images;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author mill2
 */
@Stateless
public class ImagesFacade extends AbstractFacade<Images> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public ImagesFacade()
    {
        super(Images.class);
    }
    
    public Images findByUrl(String url)
    {
        Images result = null;
        TypedQuery<Images> query = em.createNamedQuery("Images.findByUrl", Images.class);
        query.setParameter("url", url);
        try
        {
            result = query.getSingleResult();
            return result;
        }catch(NoResultException nre)
        {
            return null;
        }
    }
    
}
