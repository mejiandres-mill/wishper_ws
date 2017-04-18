package com.mill.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mill.utils.Constants;
import com.mill.utils.Message;
import com.mill.utils.Result;
import com.mill.exceptions.WSException;
import com.mill.model.Friends;
import com.mill.model.FriendsPK;
import com.mill.model.Users;
import com.mill.session.FriendsFacade;
import com.mill.session.UsersFacade;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;

public class PeopleManager {

    private ObjectMapper mapper;
    private UsersFacade usersFacade = lookupUsersFacadeBean();
    private FriendsFacade friendsFacade = lookupFriendsFacadeBean();

    public PeopleManager(ObjectMapper mapper)
    {
        this.mapper = mapper;
    }

    public Result process(Message message, String username) throws WSException, SQLException, NamingException
    {
        try
        {
            switch (message.getOperation())
            {
                case Constants.OPER_SHOW_FRIENDS:
                    return showPeople(message.getData(), username, true);
                case Constants.OPER_SHOW_PEOPLE:
                    return showPeople(message.getData(), username, false);
                case Constants.OPER_ADD_FRIEND:
                    return createRelationShip(message.getData(), username, true);
                case Constants.OPER_REM_FRIEND:
                    return createRelationShip(message.getData(), username, false);
                case Constants.OPER_IS_FRIEND:
                    return isFriend(message.getData(), username);
                default:
                    throw new WSException(Constants.INVALID_OPERATION, "Operación no válida");
            }
        } catch (JsonParseException e)
        {
            throw new WSException(Constants.JSON_ERROR, "Error transformando JSON");
        } catch (JsonMappingException e)
        {
            e.printStackTrace();
            throw new WSException(Constants.JSON_ERROR, "Error en mapeo JSON");
        } catch (IOException e)
        {
            throw new WSException(Constants.JSON_ERROR, "Error de flujo de JSON");
        }
    }

    public Result showPeople(String data, String username, boolean friends)
            throws SQLException, WSException, JsonProcessingException, NamingException
    {
        Result r = new Result();
        Users user = usersFacade.getUserByEmail(username);
        List<Users> result = new ArrayList<>();
        if (!friends)
        {
            List<Users> all = usersFacade.findAll();
            List<Friends> friendsies = user.getFriendsList1();
            for (Friends f : friendsies)
            {
                result.add(f.getUsers());
            }
            all.removeAll(result);
            r.setState(Constants.STATE_OK);
            r.setData(mapper.writeValueAsString(all));
        } else
        {
            List<Friends> friendsies = user.getFriendsList1();
            for (Friends f : friendsies)
            {
                result.add(f.getUsers());
            }
            r.setState(Constants.STATE_OK);
            r.setData(mapper.writeValueAsString(result));
        }
        return r;
    }

    @SuppressWarnings("unchecked")
    private Result createRelationShip(String data, String username, boolean friends) throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();
        Users user = usersFacade.getUserByEmail(username);
        Map<String, Object> json = mapper.readValue(data, Map.class);

        if (friends)
        {
            Friends f = new Friends();
            f.setUsers1(user);
            f.setUsers(usersFacade.find(json.get("idusers")));
            f.setBefriendDate(new Date(System.currentTimeMillis()));
            f.setFriendsPK(new FriendsPK(user.getIdusers(), f.getUsers().getIdusers()));
            friendsFacade.create(f);
            r.setData("Amigo agregado");
        } else
        {

            System.out.println(user.getIdusers() + "  " + (int) json.get("idusers"));
            int count = friendsFacade.deleteRelationShip(user.getIdusers(), (int) json.get("idusers"));
            if (count > 0)
            {
                r.setData("Amigo eliminado");
            }
            else
            {
                r.setData("Amistad no existente");
            }
        }
        r.setState(Constants.STATE_OK);
        return r;
    }

    private Result isFriend(String data, String username) throws JsonParseException, JsonMappingException, IOException, SQLException, NamingException
    {
        Result r = new Result();
        Users user = usersFacade.getUserByEmail(username);
        Map<String, Object> json = mapper.readValue(data, Map.class);
        Friends f = friendsFacade.findRelationShip(user.getIdusers(), (int) json.get("idusers"));
        r.setState(Constants.STATE_OK);
        r.setData((f != null) ? "true" : "false");
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

    private FriendsFacade lookupFriendsFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (FriendsFacade) c.lookup("java:global/wishper_ws/FriendsFacade!com.mill.session.FriendsFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
