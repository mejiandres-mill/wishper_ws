package com.mill.controller;

import java.io.IOException;
import java.sql.Connection;
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

public class PeopleManager {

	private ObjectMapper mapper;

	public PeopleManager(ObjectMapper mapper)
	{
		this.mapper = mapper;
	}
//
//	public Result process(Message message, String username) throws WSException, SQLException, NamingException
//	{
//		try
//		{
//			switch (message.getOperation())
//			{
//			case Constants.OPER_SHOW_FRIENDS:
//				return showPeople(message.getData(), username, true);
//			case Constants.OPER_SHOW_PEOPLE:
//				return showPeople(message.getData(), username, false);
//			case Constants.OPER_ADD_FRIEND:
//				return createRelationShip(message.getData(), username, true);
//			case Constants.OPER_REM_FRIEND:
//				return createRelationShip(message.getData(), username, false);
//			case Constants.OPER_IS_FRIEND:
//				return isFriend(message.getData(), username);
//			default:
//				throw new WSException(Constants.INVALID_OPERATION, "Operación no válida");
//			}
//		} catch (JsonParseException e)
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
//	public Result showPeople(String data, String username, boolean friends)
//			throws SQLException, WSException, JsonProcessingException, NamingException
//	{
//		Result r = new Result();
//		Connection conn = sqlUtil.getConnection();
//		List<User> users = factory.getDaoRead().<User> getAllForInputExact(conn, Constants.TABLE_USERS, "email",
//				username, factory);
//		conn.close();
//		Object[] params = new Object[1];
//		params[0] = users.get(0).getIdusers();
//		List<User> result = new ArrayList<>();
//		conn = sqlUtil.getConnection();
//		if (friends)
//		{
//			List<Friendship> friendships = sqlUtil.<Friendship> executeDBOperation(
//					"SELECT * FROM friends WHERE friender = ?", Constants.TABLE_FRIENDS, params, factory);
//			for (Friendship f : friendships)
//				result.add(factory.getDaoRead().<User> get(conn, Constants.TABLE_USERS,
//						new long[] { f.getFriendee() }, factory));
//			r.setData(mapper.writeValueAsString(result));
//		} else
//		{
//			params = new Object[2];
//			params[0] = users.get(0).getIdusers();
//			params[1] = users.get(0).getIdusers();
//			List<User> people = sqlUtil.<User> executeDBOperation(
//					"SELECT DISTINCT(idusers) as idusers, name, gender, birthdate, countries_idcountries, images_idimages, active, password, apikey, entrydate, email FROM users LEFT JOIN friends ON (idusers = friender) WHERE idusers <> ? AND idusers NOT IN (SELECT DISTINCT(friendee) FROM friends WHERE friender = ?)",
//					Constants.TABLE_USERS, params, factory);
//			r.setData(mapper.writeValueAsString(people));
//		}
//		conn.close();
//		return r;
//	}
//	
//	@SuppressWarnings("unchecked")
//	private Result createRelationShip(String data, String username, boolean friends) throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
//	{
//		Result r = new Result();
//		Connection conn = sqlUtil.getConnection();
//		List<User> users = factory.getDaoRead().<User> getAllForInputExact(conn, Constants.TABLE_USERS, "email",
//				username, factory);
//		conn.close();
//		Map<String, Object> json = mapper.readValue(data, Map.class);
//		Friendship f = new Friendship();
//		f.setFriender(users.get(0).getIdusers());
//		int friendee = (int) json.get("idusers");
//		f.setFriendee(friendee );
//		if(friends)
//		{
//			boolean success = factory.getDaoInsert().<Friendship>putInto(sqlUtil.getConnection(), Constants.TABLE_FRIENDS, f, factory,  true);
//			r.setState(success ? Constants.STATE_OK : Constants.DATABASE_ERROR);
//			r.setData(success ? "Amigo agregado" : "Error al agregar el amigo");
//		}
//		else
//		{
//			boolean success = factory.getDaoDelete().<Friendship>deleteFrom(sqlUtil.getConnection(), Constants.TABLE_FRIENDS, f, factory);
//			r.setState(success ? Constants.STATE_OK : Constants.DATABASE_ERROR);
//			r.setData(success ? "Amigo eliminado" : "Error al eliminar el amigo");
//		}
//		return r;
//	}
//	
//	@SuppressWarnings("unchecked")
//	private Result isFriend(String data, String username) throws JsonParseException, JsonMappingException, IOException, SQLException, NamingException
//	{
//		Result r = new Result();
//		Connection conn = sqlUtil.getConnection();
//		List<User> users = factory.getDaoRead().<User> getAllForInputExact(conn, Constants.TABLE_USERS, "email",
//				username, factory);
//		Map<String, Object> json = mapper.readValue(data, Map.class);
//		Friendship f = new Friendship();
//		f.setFriender(users.get(0).getIdusers());
//		int friendee = (int) json.get("idusers");
//		f.setFriendee(friendee );
//		r.setState(Constants.STATE_OK);
//		if(factory.getDaoRead().<Friendship>exists(conn, Constants.TABLE_FRIENDS, f, factory))
//			r.setData("true");
//		else
//			r.setData("false");
//		conn.close();
//		return r;
//	}

}
