package com.mill.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mill.exceptions.WSException;
import com.mill.utils.Constants;
import com.mill.utils.Message;
import com.mill.utils.Result;

public class CentralProcessor {

    private final AccessManager accessManager;
    private final PeopleManager peopleManager;
    private final ProductManager productManager;
    private final StoreManager storeManager;
    private final ChatManager chatManager;
    private final ObjectMapper mapper;

    public CentralProcessor()
    {
        mapper = new ObjectMapper();
        accessManager = new AccessManager(mapper);
        peopleManager = new PeopleManager(mapper);
        storeManager = new StoreManager(mapper);
        productManager = new ProductManager(mapper);
        chatManager = new ChatManager(mapper);
    }

    public Result process(Message message, String username) throws WSException, SQLException, NoSuchAlgorithmException, NamingException
    {
        int operation = message.getOperation() / 100;
        switch (operation)
        {
            case 1:
                System.out.println("Calling access manager with operation: " + message.getOperation() + "...");
                return accessManager.process(message, username);
            case 2:
                System.out.println("Calling people manager with operation: " + message.getOperation() + "...");
                return peopleManager.process(message, username);
            case 3:
                System.out.println("Calling product manager with operation: " + message.getOperation() + "...");
                return productManager.process(message, username);
            case 4:
                System.out.println("Calling store manager with operation: " + message.getOperation() + "...");
                return storeManager.process(message, username);
            case 5:
                System.out.println("Calling chat manager with operation: " + message.getOperation() + "...");
                return chatManager.process(message, username);
            default:
                throw new WSException(Constants.INVALID_OPERATION, "Operación no válida");
        }
    }

}
