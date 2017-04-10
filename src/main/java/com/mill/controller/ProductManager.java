package com.mill.controller;

import static com.mill.utils.Constants.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mill.exceptions.WSException;
import com.mill.utils.Message;
import com.mill.utils.Result;

public class ProductManager {

	private ObjectMapper mapper;

	public ProductManager(ObjectMapper mapper)
	{
		this.mapper = mapper;
	}

//	public Result process(Message message, String username) throws SQLException, WSException, NamingException
//	{
//		try
//		{
//			switch (message.getOperation())
//			{
//			case OPER_SHOW_RAND_PRODS:
//				return showRandomProducts(message.getData(), username);
//			case OPER_LIKE_PRODUCT:
//				return addTaste(message.getData(), username, true);
//			case OPER_REJECT_PRODUCT:
//				return addTaste(message.getData(), username, false);
//			case OPER_RECOMEND_PRODS:
//				return recomendProduct(message.getData(), username);
//			case OPER_SHOW_LIKED:
//				return showUserProds(message.getData(), username, true);
//			case OPER_SHOW_REJECTED:
//				return showUserProds(message.getData(), username, false);
//			case OPER_SHOW_RECOMENDATIONS:
//				return showRecomendations(message.getData(), username);
//			case OPER_ADD_PRODUCT:
//				return addProduct(message.getData(), username);
//			case OPER_SHOW_PRODUCT:
//				return setProductVisibility(message.getData(), username, true);
//			case OPER_HIDE_PRODUCT:
//				return setProductVisibility(message.getData(), username, false);
//			case OPER_UPDATE_PRODUCT:
//				return updateProduct(message.getData(), username);
//			case OPER_ADD_PRODUCT_TAG:
//				return addTag(message.getData(), username);
//			case OPER_ADD_PRODUCT_IMAGE:
//				return addImage(message.getData(), username);
//			default:
//				throw new WSException(INVALID_OPERATION, "Operación no válida");
//			}
//		} catch (JsonParseException e)
//		{
//			throw new WSException(JSON_ERROR, "Error transformando JSON");
//		} catch (JsonMappingException e)
//		{
//			throw new WSException(JSON_ERROR, "Error en mapeo JSON");
//		} catch (IOException e)
//		{
//			throw new WSException(JSON_ERROR, "Error de flujo de JSON");
//		}
//	}
//
//	private Result addProduct(String data, String username)
//			throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
//	{
//		Result r = new Result();
//		Connection conn = sqlUtil.getConnection();
//
//		Product product = mapper.readValue(data, Product.class);
//
//		boolean success = factory.getDaoInsert().<Product> putInto(conn, TABLE_PRODUCTS, product, factory, false);
//
//		r.setState(success ? STATE_OK : DATABASE_ERROR);
//		r.setData(success ? "Producto agregado" : "Error de base de datos");
//
//		return r;
//	}
//
//	@SuppressWarnings("unchecked")
//	private Result addTag(String data, String username)
//			throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
//	{
//		Result r = new Result();
//		Connection conn = sqlUtil.getConnection();
//		Map<String, Object> map = mapper.readValue(data, Map.class);
//		List<Tag> tags = factory.getDaoRead().<Tag> getAllForInputExact(conn, TABLE_TAGS, "name",
//				(String) map.get("name"), factory);
//		conn.close();
//		Tag tag = null;
//		if (tags.isEmpty())
//		{
//			conn = sqlUtil.getConnection();
//			tag = new Tag();
//			tag.setName((String) map.get("name"));
//			factory.getDaoInsert().<Tag> putInto(conn, TABLE_TAGS, tag, factory, false);
//			conn = sqlUtil.getConnection();
//			tags = factory.getDaoRead().<Tag> getAllForInputExact(conn, TABLE_TAGS, "name", tag.getName(), factory);
//			conn.close();
//		}
//		tag = tags.get(0);
//		conn = sqlUtil.getConnection();
//		ProductTags pt = new ProductTags();
//		int idproducts = (int) map.get("idproducts");
//		pt.setProduct(idproducts);
//		pt.setTag(tag.getIdtags());
//
//		boolean success = factory.getDaoInsert().<ProductTags> putInto(conn, TABLE_PRODUCT_TAGS, pt, factory, false);
//
//		r.setState(success ? STATE_OK : DATABASE_ERROR);
//		r.setData(success ? "Tag agregado" : "Error de base de datos");
//		return r;
//	}
//
//	@SuppressWarnings("unchecked")
//	private Result addImage(String data, String username)
//			throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
//	{
//		Result r = new Result();
//		Connection conn = sqlUtil.getConnection();
//		Map<String, Object> map = mapper.readValue(data, Map.class);
//		List<Image> images = factory.getDaoRead().<Image> getAllForInputExact(conn, TABLE_IMAGES, "url",
//				(String) map.get("url"), factory);
//		conn.close();
//		Image image = null;
//		if (images.isEmpty())
//		{
//			conn = sqlUtil.getConnection();
//			image = new Image();
//			image.setUrl((String) map.get("url"));
//			factory.getDaoInsert().<Image> putInto(conn, TABLE_IMAGES, image, factory, false);
//			conn = sqlUtil.getConnection();
//			images = factory.getDaoRead().<Image> getAllForInputExact(conn, TABLE_TAGS, "name", image.getUrl(), factory);
//			conn.close();
//		}
//		image = images.get(0);
//		conn = sqlUtil.getConnection();
//		ProductImages pt = new ProductImages();
//		int idproducts = (int) map.get("idproducts");
//		pt.setProduct(idproducts);
//		pt.setImage(image.getIdimages());
//		pt.setShow(true);
//
//		boolean success = factory.getDaoInsert().<ProductImages> putInto(conn, TABLE_PRODUCT_IMAGES, pt, factory, false);
//
//		r.setState(success ? STATE_OK : DATABASE_ERROR);
//		r.setData(success ? "Imagen agregada" : "Error de base de datos");
//		return r;
//	}
//
//	private Result showRandomProducts(String data, String username)
//			throws SQLException, WSException, JsonProcessingException, NamingException
//	{
//		Result r = new Result();
//		Connection conn = sqlUtil.getConnection();
//		List<User> user = factory.getDaoRead().<User> getAllForInputExact(conn, TABLE_USERS, "email", username, factory);
//		conn.close();
//
//		String query = " SELECT p.idproducts AS idproducts, p.name AS 'name', p.price AS price, p.description AS description, p.stores_idstores AS stores_idstores, p.show as `show` "
//				+ " FROM products p "
//				+ " WHERE p.idproducts NOT IN ( SELECT t.products_idproducts FROM tastes t WHERE t.users_idusers = ?) LIMIT 20";
//		Object[] params = new Object[1];
//		params[0] = user.get(0).getIdusers();
//		List<Product> prods = sqlUtil.executeDBOperation(query, TABLE_PRODUCTS, params, factory);
//		if (prods.isEmpty())
//		{
//			r.setState(NO_RESULTS_ERROR);
//			r.setData("No se encontraron productos");
//		} else
//		{
//			for (Product p : prods)
//			{
//				query = "SELECT i.idimages AS idimages, i.url AS url "
//						+ "FROM images i INNER JOIN product_images p ON i.idimages = p.images_idimages "
//						+ "WHERE p.products_idproducts = ? AND `show`='1' ";
//				params = new Object[1];
//				params[0] = p.getIdproducts();
//				List<Image> imgs = sqlUtil.executeDBOperation(query, TABLE_IMAGES, params, factory);
//				for (Image i : imgs)
//				{
//					p.addImage(i);
//				}
//			}
//			r.setState(STATE_OK);
//			r.setData(mapper.writeValueAsString(prods));
//		}
//		return r;
//	}
//
//	private Result addTaste(String data, String username, boolean like)
//			throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
//	{
//		Result r = new Result();
//		Connection conn = sqlUtil.getConnection();
//		List<User> user = factory.getDaoRead().<User> getAllForInputExact(conn, TABLE_USERS, "email", username, factory);
//		conn.close();
//
//		Taste taste = mapper.readValue(data, Taste.class);
//		taste.setUser(user.get(0).getIdusers());
//		taste.setLike(like);
//		conn = sqlUtil.getConnection();
//
//		if (factory.getDaoRead().<Taste> exists(conn, TABLE_TASTES, taste, factory))
//		{
//			factory.getDaoUpdate().<Taste> merge(conn, TABLE_TASTES, taste);
//			r.setData("Gusto actualizado");
//		} else
//		{
//			factory.getDaoInsert().<Taste> putInto(conn, TABLE_TASTES, taste, factory, false);
//			r.setData("Gusto creado");
//		}
//
//		r.setState(STATE_OK);
//		return r;
//	}
//
//	private Result recomendProduct(String data, String username)
//			throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
//	{
//		Result r = new Result();
//		Connection conn = sqlUtil.getConnection();
//		List<User> user = factory.getDaoRead().<User> getAllForInputExact(conn, TABLE_USERS, "email", username, factory);
//		conn.close();
//
//		Recomendation recomendation = mapper.readValue(data, Recomendation.class);
//		recomendation.setRecomender(user.get(0).getIdusers());
//
//		conn = sqlUtil.getConnection();
//
//		if (factory.getDaoRead().<Recomendation> exists(conn, TABLE_RECOMENDATIONS, recomendation, factory))
//		{
//			r.setState(STATE_OK);
//			r.setData("Recomendación ya se hizo");
//			conn.close();
//		} else
//		{
//			conn.close();
//			conn = sqlUtil.getConnection();
//			factory.getDaoInsert().<Recomendation> putInto(conn, TABLE_RECOMENDATIONS, recomendation, factory, false);
//			r.setState(STATE_OK);
//			r.setData("Recomendación hecha");
//		}
//
//		return r;
//	}
//
//	private Result showUserProds(String data, String username, boolean liked)
//			throws SQLException, JsonProcessingException, WSException, NamingException
//	{
//		Result r = new Result();
//		Connection conn = sqlUtil.getConnection();
//		List<User> user = factory.getDaoRead().<User> getAllForInputExact(conn, TABLE_USERS, "email", username, factory);
//		conn.close();
//
//		String query = " SELECT p.idproducts AS idproducts, p.name AS 'name', p.price AS price, p.description AS description, p.stores_idstores AS stores_idstores, p.show as `show` "
//				+ " FROM products p "
//				+ " WHERE p.idproducts IN ( SELECT t.products_idproducts FROM tastes t WHERE t.users_idusers = ? AND t.liked = ?)";
//		Object[] params = new Object[2];
//		params[0] = user.get(0).getIdusers();
//		params[1] = liked;
//		List<Product> prods = sqlUtil.executeDBOperation(query, TABLE_PRODUCTS, params, factory);
//		if (prods.isEmpty())
//		{
//			r.setState(NO_RESULTS_ERROR);
//			r.setData("No se encontraron productos");
//		} else
//		{
//			for (Product p : prods)
//			{
//				query = "SELECT i.idimages AS idimages, i.url AS url "
//						+ "FROM images i INNER JOIN product_images p ON i.idimages = p.images_idimages "
//						+ "WHERE p.products_idproducts = ? AND `show`='1' ";
//				params = new Object[1];
//				params[0] = p.getIdproducts();
//				List<Image> imgs = sqlUtil.executeDBOperation(query, TABLE_IMAGES, params, factory);
//				for (Image i : imgs)
//				{
//					p.addImage(i);
//				}
//			}
//			r.setState(STATE_OK);
//			r.setData(mapper.writeValueAsString(prods));
//		}
//		return r;
//	}
//
//	private Result showRecomendations(String data, String username)
//			throws SQLException, WSException, JsonProcessingException, NamingException
//	{
//		Result r = new Result();
//		Connection conn = sqlUtil.getConnection();
//		List<User> user = factory.getDaoRead().<User> getAllForInputExact(conn, TABLE_USERS, "email", username, factory);
//		conn.close();
//
//		String query = " SELECT p.idproducts AS idproducts, p.name AS 'name', p.price AS price, p.description AS description, p.stores_idstores AS stores_idstores, p.show as `show` "
//				+ " FROM products p "
//				+ " WHERE p.idproducts IN ( SELECT r.products_idproducts FROM recomendations r WHERE r.receipient = ? AND r.showed = 0)";
//		Object[] params = new Object[1];
//		params[0] = user.get(0).getIdusers();
//		List<Product> prods = sqlUtil.executeDBOperation(query, TABLE_PRODUCTS, params, factory);
//		if (prods.isEmpty())
//		{
//			r.setState(NO_RESULTS_ERROR);
//			r.setData("No se encontraron productos");
//		} else
//		{
//			for (Product p : prods)
//			{
//				query = "SELECT i.idimages AS idimages, i.url AS url "
//						+ "FROM images i INNER JOIN product_images p ON i.idimages = p.images_idimages "
//						+ "WHERE p.products_idproducts = ? AND `show`='1' ";
//				params = new Object[1];
//				params[0] = p.getIdproducts();
//				List<Image> imgs = sqlUtil.executeDBOperation(query, TABLE_IMAGES, params, factory);
//				for (Image i : imgs)
//				{
//					p.addImage(i);
//				}
//			}
//			r.setState(STATE_OK);
//			r.setData(mapper.writeValueAsString(prods));
//		}
//		return r;
//	}
//
//	private Result setProductVisibility(String data, String username, boolean show)
//			throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
//	{
//		Result r = new Result();
//		Connection conn = sqlUtil.getConnection();
//
//		Product product = mapper.readValue(data, Product.class);
//		long[] keys = new long[1];
//		keys[0] = product.getIdproducts();
//		product = factory.getDaoRead().<Product> get(conn, TABLE_PRODUCTS, keys, factory);
//		conn.close();
//
//		if (product != null)
//		{
//			product.setShow(show);
//			conn = sqlUtil.getConnection();
//			boolean success = factory.getDaoUpdate().<Product> merge(conn, TABLE_PRODUCTS, product);
//
//			r.setState(success ? STATE_OK : DATABASE_ERROR);
//			r.setData(success ? show? "Producto visible" : "Producto oculto" : "Error de base de datos");
//		}
//		else
//		{
//			r.setState(STATE_OK);
//			r.setData("Producto no existe");
//		}
//
//		return r;
//	}
//
//	private Result updateProduct(String data, String username)
//			throws SQLException, JsonParseException, JsonMappingException, IOException
//	{
//		Result r = new Result();
//		Connection conn = sqlUtil.getConnection();
//
//		Product product = mapper.readValue(data, Product.class);
//
//		boolean success = factory.getDaoUpdate().<Product> merge(conn, TABLE_PRODUCTS, product);
//
//		r.setState(success ? STATE_OK : DATABASE_ERROR);
//		r.setData(success ? "Producto actualizado" : "Error de base de datos");
//
//		return r;
//	}

}
