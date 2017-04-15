/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.session;

import com.mill.model.Users;
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
public class UsersFacade extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public UsersFacade()
    {
        super(Users.class);
    }
    
    public Users getUserByEmail(String email) throws NoResultException
    {
        Users result = null;
        TypedQuery<Users> query = em.createNamedQuery("Users.findByEmail", Users.class);
        query.setParameter("email", email);
        try
        {
            result = query.getSingleResult();
            return result;
        }catch(NoResultException nre)
        {
            return null;
        }        
    }
    
    public Users getUserByApiKey(String apiKey)
    {
        Users result = null;
        TypedQuery<Users> query = em.createNamedQuery("Users.findByApikey", Users.class);
        query.setParameter("apikey", apiKey);
        try
        {
            result = query.getSingleResult();
            return result;
        }catch(NoResultException nre)
        {
            return null;
        }        
    }
    
    public List<Users> lookup(String term) 
    {
        List<Users> result = null;
        TypedQuery<Users> query =  em.createNamedQuery("Users.lookUp", Users.class);
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
