/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.session;

import com.mill.model.ProductTags;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mill2
 */
@Singleton
public class ProductTagsFacade extends AbstractFacade<ProductTags> {

    @PersistenceContext(unitName = "com.mill_wishper_ws_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public ProductTagsFacade()
    {
        super(ProductTags.class);
    }
    
}
