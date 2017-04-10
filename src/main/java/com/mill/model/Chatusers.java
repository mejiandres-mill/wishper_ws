/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mill2
 */
@Entity
@Table(name = "chatusers", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Chatusers.findAll", query = "SELECT c FROM Chatusers c"),
    @NamedQuery(name = "Chatusers.findByUsersIdusers", query = "SELECT c FROM Chatusers c WHERE c.chatusersPK.usersIdusers = :usersIdusers"),
    @NamedQuery(name = "Chatusers.findByChatsIdchats", query = "SELECT c FROM Chatusers c WHERE c.chatusersPK.chatsIdchats = :chatsIdchats"),
    @NamedQuery(name = "Chatusers.findByJoined", query = "SELECT c FROM Chatusers c WHERE c.joined = :joined")
})
public class Chatusers implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ChatusersPK chatusersPK;
    @Temporal(TemporalType.DATE)
    private Date joined;
    @JoinColumn(name = "chats_idchats", referencedColumnName = "idchats", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Chats chats;
    @JoinColumn(name = "users_idusers", referencedColumnName = "idusers", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public Chatusers()
    {
    }

    public Chatusers(ChatusersPK chatusersPK)
    {
        this.chatusersPK = chatusersPK;
    }

    public Chatusers(int usersIdusers, int chatsIdchats)
    {
        this.chatusersPK = new ChatusersPK(usersIdusers, chatsIdchats);
    }

    public ChatusersPK getChatusersPK()
    {
        return chatusersPK;
    }

    public void setChatusersPK(ChatusersPK chatusersPK)
    {
        this.chatusersPK = chatusersPK;
    }

    public Date getJoined()
    {
        return joined;
    }

    public void setJoined(Date joined)
    {
        this.joined = joined;
    }

    public Chats getChats()
    {
        return chats;
    }

    public void setChats(Chats chats)
    {
        this.chats = chats;
    }

    public Users getUsers()
    {
        return users;
    }

    public void setUsers(Users users)
    {
        this.users = users;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (chatusersPK != null ? chatusersPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chatusers))
        {
            return false;
        }
        Chatusers other = (Chatusers) object;
        if ((this.chatusersPK == null && other.chatusersPK != null) || (this.chatusersPK != null && !this.chatusersPK.equals(other.chatusersPK)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.Chatusers[ chatusersPK=" + chatusersPK + " ]";
    }
    
}
