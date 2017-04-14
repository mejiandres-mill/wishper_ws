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
import com.mill.model.Chats;
import com.mill.model.Chatusers;
import com.mill.model.Messages;
import com.mill.model.Users;
import com.mill.session.ChatsFacade;
import com.mill.session.ChatusersFacade;
import com.mill.session.MessagesFacade;
import com.mill.session.UsersFacade;
import com.mill.utils.Message;
import com.mill.utils.Result;
import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;

public class ChatManager {

    private final ObjectMapper mapper;
    private final UsersFacade usersFacade = lookupUsersFacadeBean();
    private final ChatsFacade chatsFacade = lookupChatsFacadeBean();
    private final ChatusersFacade chatusersFacade = lookupChatusersFacadeBean();
    private final MessagesFacade messagesFacade = lookupMessagesFacadeBean();

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
            e.printStackTrace();
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
        Map<String, Object> map = mapper.readValue(data, Map.class);
        String name = (String) map.get("name");
        System.out.println("Fetching user...");
        Users user = usersFacade.getUserByEmail(username);

        Chats chat = new Chats();
        chat.setCreationdate(new Date(System.currentTimeMillis()));
        chat.setName(name);
        chat.setUsersIdusers(user);

        System.out.println("Creating chat...");
        chatsFacade.create(chat);
        r.setState(STATE_OK);
        r.setData(mapper.writeValueAsString(chat));

        return r;
    }

    private Result addParticipant(String data, String username)
            throws JsonParseException, JsonMappingException, IOException, SQLException, NamingException
    {
        Result r = new Result();
        Map<String, Object> map = mapper.readValue(data, Map.class);
        int iduser = (int) map.get("user");
        int idchat = (int) map.get("chat");

        System.out.println("Fetching participant...");
        Users participant = usersFacade.find(iduser);
        if (participant != null)
        {
            System.out.println("User found, looking up chat...");
            Chats chat = chatsFacade.find(idchat);
            if (chat != null)
            {
                System.out.println("Chat found, adding participant...");
                Chatusers cu = new Chatusers(iduser, idchat);
                cu.setJoined(new Date(System.currentTimeMillis()));
                cu.setChats(chat);
                cu.setUsers(participant);
                chatusersFacade.create(cu);
                r.setState(STATE_OK);
                r.setData("Usuario agregado");
            } else
            {
                System.out.println("Chat not found...");
                r.setState(NO_RESULTS_ERROR);
                r.setData("Chat no existe");
            }
        } else
        {
            System.out.println("User not found...");
            r.setState(NO_RESULTS_ERROR);
            r.setData("El usuario no existe");
        }

        return r;
    }

    private Result saveMessage(String data, String username) throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();
        Map<String, Object> map = mapper.readValue(data, Map.class);
        int idchat = (int) map.get("chat");
        String message = (String) map.get("message");
        System.out.println("Fetching user...");
        Users user = usersFacade.getUserByEmail(username);
        if (user != null)
        {
            System.out.println("User found, fetching chat...");
            Chats c = chatsFacade.find(idchat);
            if (c != null)
            {
                System.out.println("Chat found, adding message...");
                Messages m = new Messages();
                m.setMessage(message);
                m.setTime(new Date(System.currentTimeMillis()));
                m.setChatsIdchats(c);
                m.setUsersIdusers(user);

                messagesFacade.create(m);
                r.setState(STATE_OK);
                r.setData("Mensaje agregado");
            } else
            {
                System.out.println("Chat not found ...");
                r.setState(NO_RESULTS_ERROR);
                r.setData("Chat no ha sido creado");
            }
        } else
        {
            System.out.println("User not found :( ...");
            r.setState(NO_RESULTS_ERROR);
            r.setData("Usuario no existente");
        }

        return r;
    }

    private Result loadChat(String data, String username) throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();
        Map<String, Object> map = mapper.readValue(data, Map.class);
        int idchat = (int) map.get("chat");
        System.out.println("Fetching chat...");
        Chats chat = chatsFacade.find(idchat);
        if (chat != null)
        {
            System.out.println("Chat found, checking user in chat");
            Users user = chat.getUsersIdusers();
            if (user.getEmail().equalsIgnoreCase(username))
            {
                r.setData(mapper.writeValueAsString(chat));
                r.setState(STATE_OK);
                System.out.println("User validated...");
            } else
            {
                List<Users> participants = chat.getParticipants();
                if (participants != null || !participants.isEmpty())
                {
                    for (Users u : participants)
                    {
                        if (u.getEmail().equalsIgnoreCase(username))
                        {
                            r.setData(mapper.writeValueAsString(chat));
                            r.setState(STATE_OK);
                            System.out.println("User validated...");
                            break;
                        }
                        System.out.println("User not found in chat...");
                        r.setState(CHAT_ERROR);
                        r.setData("No perteneces a este chat");
                    }
                } else
                {
                    System.out.println("User not found in chat...");
                    r.setState(NO_RESULTS_ERROR);
                    r.setData("No perteneces a este chat");
                }
            }
        } else
        {
            System.out.println("Chat not found...");
            r.setState(NO_RESULTS_ERROR);
            r.setData("Chat no existe");
        }

        return r;
    }

    private Result loadChats(String data, String username) throws SQLException, WSException, JsonProcessingException, NamingException
    {
        Result r = new Result();
        System.out.println("Fetching user...");
        Users user = usersFacade.getUserByEmail(username);
        List<Chats> chats = new ArrayList<>();

        System.out.println("Checking user owned chats...");
        chats.addAll(user.getChatsList());
        System.out.println("Checking chats where user participate...");
        List<Chatusers> list = user.getChatusersList();
        for (Chatusers cu : list)
        {
            chats.add(cu.getChats());
        }

        if (chats.isEmpty())
        {
            System.out.println("No chats found...");
            r.setState(STATE_OK);
            r.setData("No tienes chats");
        } else
        {
            System.out.println("Chats found...");
            r.setState(STATE_OK);
            r.setData(mapper.writeValueAsString(chats));
        }

        return r;
    }

    private Result saveProductMessage(String data, String username) throws SQLException, JsonParseException, JsonMappingException, IOException, NamingException
    {
        Result r = new Result();
        System.out.println("Fetching user...");
        Users user = usersFacade.getUserByEmail(username);
        Map<String, Object> map = mapper.readValue(data, Map.class);
        System.out.println("Fetching chat...");
        Chats chat = chatsFacade.find((int) map.get("chatsIdchats"));
        if (chat != null)
        {
            Messages message = mapper.readValue(data, Messages.class);
            message.setUsersIdusers(user);
            message.setTime(new Date(System.currentTimeMillis()));
            message.setChatsIdchats(chat);
            System.out.println("Persisting message...");
            messagesFacade.create(message);
            r.setState(STATE_OK);
            r.setData("Mensaje guardado");
        } else
        {

            System.out.println("Chat not found...");
            r.setState(CHAT_ERROR);
            r.setData("Chat no existe");
        }

        return r;
    }

    private boolean checkUserOnChat(Chats chat, String username) throws SQLException, NamingException
    {
        Users user = chat.getUsersIdusers();
        if (user.getEmail().equalsIgnoreCase(username))
        {
            return true;
        } else
        {
            List<Users> participants = chat.getParticipants();
            if (participants != null || !participants.isEmpty())
            {
                for (Users u : participants)
                {
                    if (u.getEmail().equalsIgnoreCase(username))
                    {
                        return true;
                    }
                    return false;
                }
            } else
            {
                return false;
            }
        }
        return false;
    }

    private UsersFacade lookupUsersFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (UsersFacade) c.lookup("java:global/wishper_ws-1.0-SNAPSHOT/UsersFacade!com.mill.session.UsersFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ChatsFacade lookupChatsFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (ChatsFacade) c.lookup("java:global/wishper_ws-1.0-SNAPSHOT/ChatsFacade!com.mill.session.ChatsFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ChatusersFacade lookupChatusersFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (ChatusersFacade) c.lookup("java:global/wishper_ws-1.0-SNAPSHOT/ChatusersFacade!com.mill.session.ChatusersFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private MessagesFacade lookupMessagesFacadeBean()
    {
        try
        {
            Context c = new InitialContext();
            return (MessagesFacade) c.lookup("java:global/wishper_ws-1.0-SNAPSHOT/MessagesFacade!com.mill.session.MessagesFacade");
        } catch (NamingException ne)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
