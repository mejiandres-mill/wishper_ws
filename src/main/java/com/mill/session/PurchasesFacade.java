/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.session;

import com.mill.model.Purchases;
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
public class PurchasesFacade extends AbstractFacade<Purchases> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public PurchasesFacade()
    {
        super(Purchases.class);
    }
    
    public List<Purchases> listbyUser(int idusers)
    {
        List<Purchases> result = null;
        TypedQuery<Purchases> query = em.createNamedQuery("Purchases.findByUsersIdusers", Purchases.class);
        query.setParameter("usersIdusers", idusers);
        
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
