package com.mill.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mill.utils.Message;
import com.mill.utils.Result;
import com.mill.utils.Constants;
import com.mill.exceptions.WSException;

public class StoreManager {
	
	private ObjectMapper mapper;
	
	public StoreManager(ObjectMapper mapper)
	{
		this.mapper = mapper;
	}
	
//	public Result process(Message message, String username) throws SQLException, WSException, NamingException
//	{
//		try
//		{
//			switch(message.getOperation())
//			{
//				case Constants.OPER_ADD_STORE:
//					return addStore(message.getData(), username);
//				case Constants.OPER_UPDATE_STORE:
//					return updateStore(message.getData(), username);
//				case Constants.OPER_ADD_STORE_TAG:
//					return addTag(message.getData(), username);
//				default:
//					throw new WSException(Constants.INVALID_OPERATION, "Operación no válida");
//			}
//		}catch (JsonParseException e)
//		{
//			throw new WSException(Constants.JSON_ERROR, "Error transformando JSON");
//		} catch (JsonMappingException e)
//		{
//			throw new WSException(Constants.JSON_ERROR, "Error en mapeo JSON");
//		} catch (IOException e)
//		{
//			throw new WSException(Constants.JSON_ERROR, "Error de flujo de JSON");
//		}
//	}
//	
//	private Result addStore(String data, String username) throws JsonParseException, JsonMappingException, IOException, SQLException, NamingException
//	{
//		Result r = new Result();
//		Store store = mapper.readValue(data, Store.class);
//		Connection conn = sqlUtil.getConnection();
//		boolean success = factory.getDaoInsert().<Store>putInto(conn, Constants.TABLE_STORES, store, factory, false);
//		r.setState(success ? Constants.STATE_OK : Constants.DATABASE_ERROR);
//		r.setData(success ? "Tienda agregada" : "Error de base de datos");
//		return r;
//	}
//	
//	private Result updateStore(String data, String username) throws JsonParseException, JsonMappingException, IOException, SQLException, NamingException
//	{
//		Result r = new Result();
//		Store store = mapper.readValue(data, Store.class);
//		Connection conn = sqlUtil.getConnection();
//		List<Store> stores = factory.getDaoRead().<Store>getAllForInputExact(conn, Constants.TABLE_STORES, "name", store.getName(), factory);
//		conn.close();
//		boolean success;
//		conn = sqlUtil.getConnection();
//		if(stores.isEmpty())
//		{
//			success = factory.getDaoInsert().<Store>putInto(conn, Constants.TABLE_STORES, store, factory, false);
//		}
//		else
//		{
//			store.setIdstores(stores.get(0).getIdstores());
//			success = factory.getDaoUpdate().<Store>merge(conn, Constants.TABLE_STORES, store);
//		}
//		r.setState(success ? Constants.STATE_OK : Constants.DATABASE_ERROR);
//		r.setData(success ? "Tienda actualizada" : "Error de base de datos");
//		return r;
//	}
//	
//	@SuppressWarnings("unchecked")
//	private Result addTag(String data, String username) throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
//	{
//		Result r = new Result();
//		Connection conn = sqlUtil.getConnection();
//		Map<String, Object> map = mapper.readValue(data, Map.class);
//		List<Tag> tags = factory.getDaoRead().<Tag> getAllForInputExact(conn, Constants.TABLE_TAGS, "name", (String) map.get("name"), factory);
//		conn.close();
//		Tag tag = null;
//		if(tags.isEmpty())
//		{
//			conn = sqlUtil.getConnection();
//			tag = new Tag();
//			tag.setName((String) map.get("name")); 
//			factory.getDaoInsert().<Tag>putInto(conn, Constants.TABLE_TAGS, tag, factory, false);
//			conn = sqlUtil.getConnection();
//			tags = factory.getDaoRead().<Tag> getAllForInputExact(conn, Constants.TABLE_TAGS, "name", tag.getName(), factory);
//			conn.close();
//		}
//		tag = tags.get(0);
//		conn = sqlUtil.getConnection();
//		StoreTags st = new StoreTags();
//		int idstores =  (int) map.get("idstores");
//		st.setStore((long) idstores);
//		st.setTag(tag.getIdtags());
//		boolean success = factory.getDaoInsert().<StoreTags>putInto(conn, Constants.TABLE_STORE_TAGS, st, factory, false);
//		
//		r.setState(success ? Constants.STATE_OK : Constants.DATABASE_ERROR);
//		r.setData(success ? "Tag agregado" : "Error de base de datos");
//		return r;
//	}

}
