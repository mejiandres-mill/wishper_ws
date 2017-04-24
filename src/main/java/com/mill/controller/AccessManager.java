package com.mill.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mill.exceptions.WSException;
import com.mill.model.Images;
import com.mill.utils.Constants;
import com.mill.utils.Message;
import com.mill.utils.Result;
import com.mill.utils.Security;
import com.mill.model.Users;
import com.mill.session.ImagesFacade;
import com.mill.session.UsersFacade;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityExistsException;

/**
 * This controller deals with all user signups and updates. Also, this
 * controller is in charge of authenticating users
 *
 */
public class AccessManager {

    UsersFacade usersFacade = lookupUsersFacadeBean();
    ImagesFacade imagesFacade = lookupImagesFacadeBean();

    private ObjectMapper mapper;

    public AccessManager(ObjectMapper mapper)
    {
        this.mapper = mapper;
    }

    public Result process(Message message, String username) throws WSException, SQLException, NoSuchAlgorithmException, NamingException
    {
        try
        {
            switch (message.getOperation())
            {
                case Constants.OPER_SIGNUP:
                    return singup(message.getData());
                case Constants.OPER_LOGIN:
                    return login(message.getData());
                case Constants.OPER_REFRESH_SESSION:
                    throw new WSException(Constants.NOT_IMPLEMENTED_ERROR, "Operación no implementada");
                case Constants.OPER_UPDATE_USER:
                    return updateUser(message.getData());
                case Constants.OPER_ADD_USER_IMAGE:
                    return addProfilePic(message.getData(), username);
                case Constants.OPER_VALIDATE_USER:
                    return validateUser(message.getData(), username);
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
        } catch (EJBException e)
        {
            throw new WSException(Constants.DATABASE_ERROR, "No lo encontramos:(");
        }
    }

    private Result singup(String data) throws IOException, NoSuchAlgorithmException
    {
        Users user = mapper.readValue(data, Users.class);
        Result r = new Result();

        user.setPassword(Security.sha256(user.getPassword()));
        user.setEntrydate(new Date(System.currentTimeMillis()));

        try
        {
            usersFacade.create(user);
            r.setState(Constants.STATE_OK);
            r.setData(mapper.writeValueAsString(user));
        } catch (EntityExistsException e)
        {

            r.setState(Constants.EXISTING_USER);
            r.setData("La dirección de correo electrónico ya está en uso");
        } finally
        {
            return r;
        }
    }

    private Result login(String data) throws JsonParseException, JsonMappingException, IOException, SQLException, NoSuchAlgorithmException, NamingException
    {
        Users user = mapper.readValue(data, Users.class);
        Result r = new Result();
        try
        {
            System.out.println("Searching user!!!!!!!!!!!!!!!!!!!!!!!!");
            Users databaseEntity = usersFacade.getUserByEmail(user.getEmail());
            if (databaseEntity != null)
            {
                System.out.println("User found...........................");
                if (databaseEntity.getPassword().equals(Security.sha256(user.getPassword())))
                {
                    databaseEntity.setApikey(Security.generateApiKey());
                    usersFacade.edit(databaseEntity);
                    r.setState(Constants.STATE_OK);
                    r.setData(mapper.writeValueAsString(databaseEntity));

                } else
                {
                    System.out.println("Incorrect password#######################");
                    r.setState(Constants.AUTHENTICATION_ERROR);
                    r.setData("La contraseña no es correcta");
                }
            } else
            {
                System.out.println("Woopsie missed it :(");
                r.setState(Constants.AUTHENTICATION_ERROR);
                r.setData("Esta cuenta de correo electrónico no se encuentra registrada");
            }
        } catch (EJBException e)
        {
            System.out.println("Woopsie missed it!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            r.setState(Constants.AUTHENTICATION_ERROR);
            r.setData("Esta cuenta de correo electrónico no se encuentra registrada");
        } finally
        {
            return r;
        }
    }

    private Result updateUser(String data) throws JsonParseException, JsonMappingException, IOException, SQLException, NoSuchAlgorithmException, NamingException
    {
        Result r = new Result();
        Users user = mapper.readValue(data, Users.class);

        try
        {
            user.setPassword(Security.sha256(user.getPassword()));
            usersFacade.edit(user);
            r.setState(Constants.STATE_OK);
            r.setData(mapper.writeValueAsString(user));
        } catch (IllegalArgumentException iae)
        {
            r.setState(Constants.AUTHENTICATION_ERROR);
            r.setData("Esta cuenta de correo electrónico no se encuentra registrada");
        } finally
        {
            return r;
        }
    }

    private Result addProfilePic(String data, String username) throws JsonParseException, JsonMappingException, IOException, SQLException, NamingException
    {
        Result r = new Result();
        Images image = mapper.readValue(data, Images.class);
        Users user = null;
        try
        {
            System.out.println("Searching user...");
            user = usersFacade.getUserByEmail(username);
        } catch (EJBException e)
        {
            System.out.println("Woopsie missed it, but you should not have");
            r.setState(Constants.AUTHENTICATION_ERROR);
            r.setData("Esta cuenta de correo electrónico no se encuentra registrada");
        }

        try
        {
            System.out.println("Checking image...");
            Images fetchedImage = imagesFacade.findByUrl(image.getUrl());
            if (fetchedImage == null)
            {
                System.out.println("Image not found, creating image...");
                imagesFacade.create(image);
                System.out.println("Image created, adding image to user...");
                user.setImagesIdimages(image);
                usersFacade.edit(user);
                System.out.println("Image added... 2");
            } else
            {
                System.out.println("Image found...");

                System.out.println(fetchedImage);
                user.setImagesIdimages(fetchedImage);
                System.out.println("Adding image to user...");
                usersFacade.edit(user);
                System.out.println("Image added...");
            }

        } catch (EJBException e)
        {
            System.out.println("Image not found, creating image...");
            imagesFacade.create(image);
            System.out.println("Image created, adding image to user...");
            user.setImagesIdimages(image);
            usersFacade.edit(user);
            System.out.println("Image added... 2");
        } finally
        {
            r.setState(Constants.STATE_OK);
            r.setData(mapper.writeValueAsString(user));
            return r;
        }
    }

    private Result validateUser(String data, String username) throws IOException, NoSuchAlgorithmException
    {
        Result r = new Result();
        Users user = mapper.readValue(data, Users.class);
        System.out.println("Checking if user exists...");
        Users check = usersFacade.getUserByEmail(user.getEmail());
        if (check != null)
        {
            System.out.println("Users checked, returning...");
            check.setApikey(Security.generateApiKey());
            usersFacade.edit(check);
            r.setState(Constants.STATE_OK);
            r.setData(mapper.writeValueAsString(check));

        } else
        {
            System.out.println("User not found, creating...");
            user.setPassword(Security.sha256(user.getPassword()));
            user.setEntrydate(new Date(System.currentTimeMillis()));
            user.setApikey(Security.generateApiKey());
            try
            {
                usersFacade.create(user);
                r.setState(Constants.STATE_OK);
                r.setData(mapper.writeValueAsString(user));
            } catch (EntityExistsException e)
            {
                r.setState(Constants.EXISTING_USER);
                r.setData("La dirección de correo electrónico ya está en uso");
            }
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

    private ImagesFacade lookupImagesFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (ImagesFacade) c.lookup("java:global/wishper_ws/ImagesFacade!com.mill.session.ImagesFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
