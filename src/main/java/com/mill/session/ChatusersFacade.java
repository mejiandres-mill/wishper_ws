/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.session;

import com.mill.model.Chatusers;
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
public class ChatusersFacade extends AbstractFacade<Chatusers> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public ChatusersFacade()
    {
        super(Chatusers.class);
    }
    
    public List<Chatusers> findByChat(int idchat)
    {
        List<Chatusers> result = null;
        TypedQuery<Chatusers> query = em.createNamedQuery("Chatusers.findByChatsIdchats", Chatusers.class);
        query.setParameter("chatsIdchats", idchat);
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
