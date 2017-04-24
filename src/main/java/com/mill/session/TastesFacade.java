/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.session;

import com.mill.model.Tastes;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mill2
 */
@Singleton
public class TastesFacade extends AbstractFacade<Tastes> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public TastesFacade()
    {
        super(Tastes.class);
    }

    public Tastes findByKeys(int idproducts, int idusers)
    {
        Tastes result = null;
        TypedQuery<Tastes> query = em.createNamedQuery("Tastes.findByKeys", Tastes.class);
        query.setParameter("idproducts", idproducts);
        query.setParameter("idusers", idusers);
        result = query.getSingleResult();
        return result;
    }

    public int deleteTaste(int idproducts, int idusers)
    {
        int count = -1;
        Query query = em.createNamedQuery("Tastes.deleteTastes");
        query.setParameter("idproducts", idproducts);
        query.setParameter("idusers", idusers);
        count = query.executeUpdate();
        return count;
    }

}
