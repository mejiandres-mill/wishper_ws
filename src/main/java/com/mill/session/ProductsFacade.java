/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.session;

import com.mill.model.Products;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mill2
 */
@Singleton
public class ProductsFacade extends AbstractFacade<Products> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public ProductsFacade()
    {
        super(Products.class);
    }

    public List<Products> lookup(String term)
    {
        List<Products> result = null;
        TypedQuery<Products> query = em.createNamedQuery("Products.lookup", Products.class);
        query.setParameter("term", "%" + term + "%");
        result = query.getResultList();
        return result;
    }

}
