/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.session;

import com.mill.model.Friends;
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
public class FriendsFacade extends AbstractFacade<Friends> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public FriendsFacade()
    {
        super(Friends.class);
    }

    public Friends findRelationShip(int friender, int friendee)
    {
        Friends result = null;
        TypedQuery<Friends> query = em.createNamedQuery("Friends.findRelationship", Friends.class);
        query.setParameter("friender", friender);
        query.setParameter("friendee", friendee);
        result = query.getSingleResult();
        return result;
    }

    public int deleteRelationShip(int friender, int friendee)
    {
        int count = -1;
        Query query = em.createNamedQuery("Friends.deleteRelationship");
        query.setParameter("friender", friender);
        query.setParameter("friendee", friendee);
        count = query.executeUpdate();
        return count;
    }
}
