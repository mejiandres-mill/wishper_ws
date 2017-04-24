package com.mill.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mill.exceptions.WSException;
import com.mill.model.Friends;
import com.mill.model.Products;
import com.mill.model.Stores;
import com.mill.model.Users;
import com.mill.session.ProductsFacade;
import com.mill.session.StoresFacade;
import com.mill.session.UsersFacade;
import com.mill.utils.Constants;
import static com.mill.utils.Constants.INVALID_OPERATION;
import static com.mill.utils.Constants.JSON_ERROR;
import com.mill.utils.Message;
import com.mill.utils.Result;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SearchManager {

    private final ObjectMapper mapper;
    private final UsersFacade usersFacade = lookupUsersFacadeBean();
    private final ProductsFacade productsFacade = lookupProductsFacadeBean();
    private final StoresFacade storesFacade = lookupStoresFacadeBean();

    public SearchManager(ObjectMapper mapper)
    {
        this.mapper = mapper;
    }

    public Result process(Message message, String username) throws WSException
    {
        try
        {
            switch (message.getOperation())
            {
                case Constants.OPER_SEARCH_PEOPLE:
                    return searchPeople(message.getData(), username);
                case Constants.OPER_SEARCH_PRODUCTS:
                    return searchProducts(message.getData(), username);
                case Constants.OPER_SEARCH_STORE:
                    return searchStores(message.getData(), username);
                default:
                    throw new WSException(INVALID_OPERATION, "Operación no válida");
            }
        } catch (JsonParseException e)
        {
            throw new WSException(JSON_ERROR, "Error transformando JSON");
        } catch (JsonMappingException e)
        {
            e.printStackTrace();
            throw new WSException(JSON_ERROR, "Error en mapeo JSON");
        } catch (IOException e)
        {
            throw new WSException(JSON_ERROR, "Error de flujo de JSON");
        }
    }

    private Result searchPeople(String data, String username) throws JsonParseException, JsonMappingException, IOException
    {
        Result r = new Result();
        Users user = usersFacade.getUserByEmail(username);
        Map<String, Object> map = mapper.readValue(data, Map.class);
        String term = (String) map.get("term");
        System.out.println("Looking up...");
        List<Users> people = usersFacade.lookup(term);
        List<Users> friends = user.getFriendsis();
        for(Users u : people)
            u.setFriend(friends.contains(u));
        if (people != null && !people.isEmpty())
        {
            System.out.println("Something found...");
            r.setState(Constants.STATE_OK);
            r.setData(mapper.writeValueAsString(people));
        } else
        {
            System.out.println("Nothing found...");
            r.setState(Constants.NO_RESULTS_ERROR);
            r.setData("No encontramos nada...");
        }
        return r;
    }

    private Result searchProducts(String data, String usename) throws JsonParseException, JsonMappingException, IOException
    {
        Result r = new Result();
        Map<String, Object> map = mapper.readValue(data, Map.class);
        String term = (String) map.get("term");
        System.out.println("Looking up...");
        List<Products> prods = productsFacade.lookup(term);
        if (prods != null && !prods.isEmpty())
        {
            System.out.println("Something found...");
            r.setState(Constants.STATE_OK);
            r.setData(mapper.writeValueAsString(prods));
        } else
        {
            System.out.println("Nothing found...");
            r.setState(Constants.NO_RESULTS_ERROR);
            r.setData("No encontramos nada...");
        }
        return r;
    }

    private Result searchStores(String data, String username) throws JsonParseException, JsonMappingException, IOException
    {
        Result r = new Result();
        Map<String, Object> map = mapper.readValue(data, Map.class);
        String term = (String) map.get("term");
        System.out.println("Looking up...");
        List<Stores> stores = storesFacade.lookup(term);
        if (stores != null && !stores.isEmpty())
        {
            System.out.println("Something found...");
            r.setState(Constants.STATE_OK);
            r.setData(mapper.writeValueAsString(stores));
        } else
        {
            System.out.println("Nothing found...");
            r.setState(Constants.NO_RESULTS_ERROR);
            r.setData("No encontramos nada...");
        }
        return r;
    }

    private UsersFacade lookupUsersFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (UsersFacade) c.lookup("java:global/wishper_ws/UsersFacade!com.mill.session.UsersFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private ProductsFacade lookupProductsFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (ProductsFacade) c.lookup("java:global/wishper_ws/ProductsFacade!com.mill.session.ProductsFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
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

}
