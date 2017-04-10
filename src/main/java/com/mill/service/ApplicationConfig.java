/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author mill2
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources)
    {
        resources.add(com.mill.WishperWS.class);
        resources.add(com.mill.security.AuthenticationFilter.class);
        resources.add(com.mill.service.ChatsFacadeREST.class);
        resources.add(com.mill.service.ChatusersFacadeREST.class);
        resources.add(com.mill.service.CountriesFacadeREST.class);
        resources.add(com.mill.service.FriendsFacadeREST.class);
        resources.add(com.mill.service.ImagesFacadeREST.class);
        resources.add(com.mill.service.MessagesFacadeREST.class);
        resources.add(com.mill.service.ProductImagesFacadeREST.class);
        resources.add(com.mill.service.ProductTagsFacadeREST.class);
        resources.add(com.mill.service.ProductsFacadeREST.class);
        resources.add(com.mill.service.PurchasesFacadeREST.class);
        resources.add(com.mill.service.RecomendationsFacadeREST.class);
        resources.add(com.mill.service.StoresFacadeREST.class);
        resources.add(com.mill.service.TagsFacadeREST.class);
        resources.add(com.mill.service.TastesFacadeREST.class);
        resources.add(com.mill.service.UsersFacadeREST.class);
    }
    
}
