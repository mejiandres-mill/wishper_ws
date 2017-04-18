package com.mill.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.NamingException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mill.utils.Message;
import com.mill.utils.Result;
import com.mill.utils.Constants;
import com.mill.exceptions.WSException;
import com.mill.model.Stores;
import com.mill.model.Tags;
import com.mill.session.StoresFacade;
import com.mill.session.TagsFacade;
import static com.mill.utils.Constants.NO_RESULTS_ERROR;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;

public class StoreManager {

    private final ObjectMapper mapper;
    private final StoresFacade storesFacade = lookupStoresFacadeBean();
    private final TagsFacade tagsFacade = lookupTagsFacadeBean();

    public StoreManager(ObjectMapper mapper)
    {
        this.mapper = mapper;
    }

    public Result process(Message message, String username) throws SQLException, WSException, NamingException
    {
        try
        {
            switch (message.getOperation())
            {
                case Constants.OPER_ADD_STORE:
                    return addStore(message.getData(), username);
                case Constants.OPER_UPDATE_STORE:
                    return updateStore(message.getData(), username);
                case Constants.OPER_ADD_STORE_TAG:
                    return addTag(message.getData(), username);
                default:
                    throw new WSException(Constants.INVALID_OPERATION, "Operación no válida");
            }
        } catch (JsonParseException e)
        {
            throw new WSException(Constants.JSON_ERROR, "Error transformando JSON");
        } catch (JsonMappingException e)
        {
            throw new WSException(Constants.JSON_ERROR, "Error en mapeo JSON");
        } catch (IOException e)
        {
            throw new WSException(Constants.JSON_ERROR, "Error de flujo de JSON");
        }
    }

    private Result addStore(String data, String username) throws JsonParseException, JsonMappingException, IOException, SQLException, NamingException
    {
        Result r = new Result();
        Stores s = mapper.readValue(data, Stores.class);
        System.out.println("Creating store...");
        storesFacade.create(s);
        r.setState(Constants.STATE_OK);
        r.setData("Tienda creada");
        return r;
    }

    private Result updateStore(String data, String username) throws JsonParseException, JsonMappingException, IOException, SQLException, NamingException
    {
        Result r = new Result();
        Stores s = mapper.readValue(data, Stores.class);
        System.out.println("Fetching store...");
        Stores fetchedStore = storesFacade.find(s.getIdstores());
        
        if(fetchedStore != null)
        {
            System.out.println("Store found...");
            fetchedStore.setCountriesIdcountries(s.getCountriesIdcountries());
            fetchedStore.setName(s.getName());
            fetchedStore.setSite(s.getSite());
            System.out.println("Updating store info...");
            storesFacade.edit(fetchedStore);
            r.setState(Constants.STATE_OK);
            r.setData("Tienda actualizada");
        }
        else
        {
            System.out.println("Store not found :(");
            r.setState(NO_RESULTS_ERROR);
            r.setData("Tienda inexistente");
        }
        return r;
    }

    private Result addTag(String data, String username) throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();
        Map<String, Object> map = mapper.readValue(data, Map.class);
        String tagName = (String) map.get("name");
        int idstores = (int) map.get("idstores");
        System.out.println("Checking tag...");
        Tags tag = tagsFacade.findByName(tagName);
        if (tag == null)
        {
            System.out.println("Tag not found, creating...");
            tag = new Tags();
            tag.setName(tagName);
            tagsFacade.create(tag);
        }
        System.out.println("Fetching store...");
        Stores s = storesFacade.find(idstores);
        if(s != null)
        {
            System.out.println("Store found...");
            s.getTagsList().add(tag);
            System.out.println("Adding tag...");
            tagsFacade.edit(tag);
            r.setState(Constants.STATE_OK);
            r.setData("Tag agregado");
        }
        else
        {
            System.out.println("Store not found :(");
            r.setState(NO_RESULTS_ERROR);
            r.setData("Tienda inexistente");
        }
        return r;
    }

    private StoresFacade lookupStoresFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (StoresFacade) c.lookup("java:global/wishper_ws/StoresFacade!com.mill.session.StoresFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private TagsFacade lookupTagsFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (TagsFacade) c.lookup("java:global/wishper_ws/TagsFacade!com.mill.session.TagsFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
