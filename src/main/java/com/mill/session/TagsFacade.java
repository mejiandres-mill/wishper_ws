/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.session;

import com.mill.model.Tags;
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
public class TagsFacade extends AbstractFacade<Tags> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public TagsFacade()
    {
        super(Tags.class);
    }
    
    public Tags findByName(String name)
    {
        Tags result = null;
        TypedQuery<Tags> query = em.createNamedQuery("Tags.findByName", Tags.class);
        query.setParameter("name", name);
        return result;
    }
    
}
