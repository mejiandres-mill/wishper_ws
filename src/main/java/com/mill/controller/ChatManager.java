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
import com.mill.model.Users;
import com.mill.utils.Message;
import com.mill.utils.Result;

public class ChatManager {

    private ObjectMapper mapper;

    public ChatManager(ObjectMapper mapper)
    {
        this.mapper = mapper;
    }

    public Result process(Message message, String username) throws SQLException, WSException, NamingException
    {
        try
        {
            switch (message.getOperation())
            {
                case OPER_CREATE_CHAT:
                    return createChat(message.getData(), username);
                case OPER_ADD_PARTICIPANT:
                    return addParticipant(message.getData(), username);
                case OPER_SAVE_MESSAGE:
                    return saveMessage(message.getData(), username);
                case OPER_LOAD_CHAT:
                    return loadChat(message.getData(), username);
                case OPER_LOAD_CHATS:
                    return loadChats(message.getData(), username);
                case OPER_ADD_PRODUCT_MESSAGE:
                    return saveProductMessage(message.getData(), username);
                default:
                    throw new WSException(INVALID_OPERATION, "Operación no válida");
            }

        } catch (JsonParseException e)
        {
            throw new WSException(JSON_ERROR, "Error transformando JSON");
        } catch (JsonMappingException e)
        {
            throw new WSException(JSON_ERROR, "Error en mapeo JSON");
        } catch (IOException e)
        {
            throw new WSException(JSON_ERROR, "Error de flujo de JSON");
        }
    }

    private Result createChat(String data, String username)
            throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();
//        Connection conn = sqlUtil.getConnection();
//        List<User> user = factory.getDaoRead().<User>getAllForInputExact(conn, TABLE_USERS, "email", username, factory);
//        conn.close();
//
//        Chat chat = mapper.readValue(data, Chat.class);
//        String name = chat.getName();
//        chat.setUser(user.get(0).getIdusers());
//        chat.setName(name + System.currentTimeMillis());
//
//        conn = sqlUtil.getConnection();
//        boolean success = factory.getDaoInsert().<Chat>putInto(conn, TABLE_CHATS, chat, factory, false);
//
//        if (success)
//        {
//            conn = sqlUtil.getConnection();
//            List<Chat> chats = factory.getDaoRead().<Chat>getAllForInputExact(conn, TABLE_CHATS, "name",
//                    chat.getName(), factory);
//            conn.close();
//            chats.get(0).setName(name);
//            conn = sqlUtil.getConnection();
//            success = factory.getDaoUpdate().<Chat>merge(conn, TABLE_CHATS, chats.get(0));
//            r.setState(success ? STATE_OK : DATABASE_ERROR);
//            r.setData(success ? mapper.writeValueAsString(chats.get(0)) : "Error creando chat");
//        } else
//        {
//            r.setState(DATABASE_ERROR);
//            r.setData("Error creando chat");
//        }

        return r;
    }

    private Result addParticipant(String data, String username)
            throws JsonParseException, JsonMappingException, IOException, SQLException, NamingException
    {
        Result r = new Result();
//        ChatUsers cu = mapper.readValue(data, ChatUsers.class);
//        Connection conn = sqlUtil.getConnection();
//
//        List<User> user = factory.getDaoRead().<User>getAllForInputExact(conn, TABLE_USERS, "email", username, factory);
//        conn.close();
//
//        if (checkUserOnChat(cu.getChat(), user.get(0)))
//        {
//            conn = sqlUtil.getConnection();
//            boolean success = factory.getDaoInsert().<ChatUsers>putInto(conn, TABLE_CHATUSERS, cu, factory, false);
//
//            r.setState(success ? STATE_OK : DATABASE_ERROR);
//            r.setData(success ? "Participante agregado" : "Error agregando participante");
//        } else
//        {
//            r.setState(CHAT_ERROR);
//            r.setData("Participante no agregado");
//        }

        return r;
    }

    private Result saveMessage(String data, String username) throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();
//        Connection conn = sqlUtil.getConnection();
//        List<User> user = factory.getDaoRead().<User>getAllForInputExact(conn, TABLE_USERS, "email", username, factory);
//        conn.close();
//
//        com.mill.models.Message message = mapper.readValue(data, com.mill.models.Message.class);
//
//        if (checkUserOnChat(message.getChat(), user.get(0)))
//        {
//            message.setSender(user.get(0).getIdusers());
//            conn = sqlUtil.getConnection();
//            boolean success = factory.getDaoInsert().<com.mill.models.Message>putInto(conn, TABLE_MESSAGES, message, factory, false);
//
//            r.setState(success ? STATE_OK : DATABASE_ERROR);
//            r.setData(success ? "Mensaje agregado" : "Error agregando mensaje");
//        } else
//        {
//            r.setState(CHAT_ERROR);
//            r.setData("Mensaje no agregado");
//        }

        return r;
    }

    @SuppressWarnings("unchecked")
    private Result loadChat(String data, String username) throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();
//        Connection conn = sqlUtil.getConnection();
//        List<User> user = factory.getDaoRead().<User>getAllForInputExact(conn, TABLE_USERS, "email", username, factory);
//        conn.close();
//
//        Map<String, Object> map = mapper.readValue(data, Map.class);
//        int chatId = (int) map.get("chat");
//
//        if (checkUserOnChat(chatId, user.get(0)))
//        {
//            conn = sqlUtil.getConnection();
//            long[] keyValues = new long[1];
//            keyValues[0] = (int) map.get("chat");
//            Chat chat = factory.getDaoRead().<Chat>get(conn, TABLE_CHATS, keyValues, factory);
//            conn.close();
//            conn = sqlUtil.getConnection();
//            List<com.mill.models.Message> messages = factory.getDaoRead().<com.mill.models.Message>getAllForInputExact(conn, TABLE_MESSAGES, "chats_idchats", chat.getIdchats() + "", factory);
//            conn.close();
//            conn = sqlUtil.getConnection();
//            List<ChatUsers> cu = factory.getDaoRead().<ChatUsers>getAllForInputExact(conn, TABLE_CHATUSERS, "chats_idchats", chat.getIdchats() + "", factory);
//            conn.close();
//            chat.setMessages(messages);
//            for (ChatUsers c : cu)
//            {
//                conn = sqlUtil.getConnection();
//                keyValues[0] = c.getUser();
//                User u = factory.getDaoRead().<User>get(conn, TABLE_USERS, keyValues, factory);
//                conn.close();
//                chat.addParticipant(u);
//            }
//
//            r.setState(STATE_OK);
//            r.setData(mapper.writeValueAsString(chat));
//        } else
//        {
//            r.setState(CHAT_ERROR);
//            r.setData("Error cargando chat");
//        }

        return r;
    }

    private Result loadChats(String data, String username) throws SQLException, WSException, JsonProcessingException, NamingException
    {
        Result r = new Result();
//        Connection conn = sqlUtil.getConnection();
//        List<User> user = factory.getDaoRead().<User>getAllForInputExact(conn, TABLE_USERS, "email", username, factory);
//        conn.close();
//
//        String query = " SELECT c.idchats AS idchats, c.name AS name, c.creationdate AS creationdate "
//                + " FROM chats c INNER JOIN chatusers cu ON c.idchats = cu.chats_idchats "
//                + " WHERE cu.users_idusers = ?";
//        Object[] params = new Object[1];
//        params[0] = user.get(0).getIdusers();
//        List<Chat> chats = sqlUtil.executeDBOperation(query, TABLE_CHATS, params, factory);
//
//        conn = sqlUtil.getConnection();
//        chats.addAll(factory.getDaoRead().<Chat>getAllForInputExact(conn, TABLE_CHATS, "users_idusers", user.get(0).getIdusers() + "", factory));
//        conn.close();
//        r.setState(STATE_OK);
//        if (chats.isEmpty())
//        {
//            r.setData("No has iniciado ningún chat");
//        } else
//        {
//            r.setData(mapper.writeValueAsString(chats));
//        }

        return r;
    }

    private Result saveProductMessage(String data, String username) throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();
//        Connection conn = sqlUtil.getConnection();
//        List<User> user = factory.getDaoRead().<User>getAllForInputExact(conn, TABLE_USERS, "email", username, factory);
//        conn.close();
//
//        com.mill.models.Message message = mapper.readValue(data, com.mill.models.Message.class);
//        message.setSender(user.get(0).getIdusers());
//
//        if (checkUserOnChat(message.getChat(), user.get(0)))
//        {
//            conn = sqlUtil.getConnection();
//            long messageId = factory.getDaoInsert().<com.mill.models.Message>putInto(conn, TABLE_MESSAGES, message, factory);
//            if (messageId < 0)
//            {
//                r.setState(CHAT_ERROR);
//                r.setData("Error guardando mensaje");
//            } else
//            {
//                for (Product p : message.getProducts())
//                {
//                    conn = sqlUtil.getConnection();
//                    MessageProducts mp = new MessageProducts();
//                    mp.setMessage(messageId);
//                    mp.setProduct(p.getIdproducts());
//                    if (!factory.getDaoInsert().<MessageProducts>putInto(conn, TABLE_MESSAGE_PRODUCTS, mp, factory, false))
//                    {
//                        r.setState(CHAT_ERROR);
//                        r.setData("Error agregando producto al mensaje");
//                        return r;
//                    }
//                }
//                r.setState(STATE_OK);
//                r.setData("Mensaje guardado");
//            }
//        } else
//        {
//            r.setState(CHAT_ERROR);
//            r.setData("No perteneces a este chat o la conversación no existe");
//        }

        return r;
    }

    private boolean checkUserOnChat(long chatId, Users user) throws SQLException, NamingException
    {
//        Connection conn = sqlUtil.getConnection();
//        long[] keyValues = new long[1];
//        keyValues[0] = chatId;
//        Chat c = factory.getDaoRead().<Chat>get(conn, TABLE_CHATS, keyValues, factory);
//        conn.close();
//        if (c == null)
//        {
//            return false;
//        }
//        if (c.getUser() == user.getIdusers())
//        {
//            return true;
//        }
//        keyValues = new long[2];
//        keyValues[0] = user.getIdusers();
//        keyValues[1] = chatId;
//        conn = sqlUtil.getConnection();
//        return factory.getDaoRead().exists(conn, TABLE_CHATUSERS, keyValues, factory);
        return true;
    }

}
