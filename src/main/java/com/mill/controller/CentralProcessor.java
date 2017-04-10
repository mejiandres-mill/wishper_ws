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
	
	private AccessManager accessManager;
	private PeopleManager peopleManager;
	private ProductManager productManager;
	private StoreManager storeManager;
	private ChatManager chatManager;
	private ObjectMapper mapper;
        
        
	
	public CentralProcessor() 
	{
		mapper = new ObjectMapper();
		accessManager = new AccessManager(mapper);
		peopleManager = new PeopleManager( mapper);		
		storeManager = new StoreManager(mapper);
		productManager = new ProductManager( mapper);
		chatManager = new ChatManager(mapper);
	}
	
	public Result process(Message message, String username) throws WSException, SQLException, NoSuchAlgorithmException, NamingException
	{
		int operation = message.getOperation() / 100;
		switch(operation)
		{
			case 1:
				return accessManager.process(message, username);
			case 2:
				return peopleManager.process(message, username);
			case 3:
				return null; //productManager.process(message, username);
			case 4:
				return null; // storeManager.process(message, username);
			case 5:
				return null; // chatManager.process(message, username);
			default:
				throw new WSException(Constants.INVALID_OPERATION, "Operación no válida");
		}
	}
	

}
