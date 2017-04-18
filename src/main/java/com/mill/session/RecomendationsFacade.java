/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.session;

import com.mill.model.Recomendations;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author mill2
 */
@Singleton
public class RecomendationsFacade extends AbstractFacade<Recomendations> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public RecomendationsFacade()
    {
        super(Recomendations.class);
    }
    
    public Recomendations findByKeys(int recomender, int receipient, int idproducts)
    {
        Recomendations result = null;
        TypedQuery<Recomendations> query = em.createNamedQuery("Recomendations.findByKeys", Recomendations.class);
        query.setParameter("recomender", recomender);
        query.setParameter("receipient", receipient);
        query.setParameter("idproducts", idproducts);
        try
        {
            result = query.getSingleResult();
            return result;
        }catch(NoResultException nre)
        {
            return null;
        }
    }
    
    public int deleteRecomendation(int idproducts, int idusers)
    {
        Query query = em.createNamedQuery("Recomendations.deleteRecomendations");
        query.setParameter("idproducts", idproducts);
        query.setParameter("idusers", idusers);
        
        return query.executeUpdate();
    }
    
}
