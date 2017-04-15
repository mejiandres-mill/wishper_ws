/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.session;

import com.mill.model.Stores;
import java.util.List;
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
public class StoresFacade extends AbstractFacade<Stores> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public StoresFacade()
    {
        super(Stores.class);
    }
    
    public List<Stores> lookup(String term)
    {
        List<Stores> result = null;
        TypedQuery<Stores> query = em.createNamedQuery("Stores.lookup", Stores.class);
        query.setParameter("term", "%" + term + "%");
        try
        {
            result = query.getResultList();
            return result;
        }catch(NoResultException nre)
        {
            return null;
        }
    }
    
}
