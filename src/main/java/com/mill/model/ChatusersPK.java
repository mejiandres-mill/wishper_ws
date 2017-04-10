/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mill2
 */
@Embeddable
public class ChatusersPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "users_idusers")
    private int usersIdusers;
    @Basic(optional = false)
    @NotNull
    @Column(name = "chats_idchats")
    private int chatsIdchats;

    public ChatusersPK()
    {
    }

    public ChatusersPK(int usersIdusers, int chatsIdchats)
    {
        this.usersIdusers = usersIdusers;
        this.chatsIdchats = chatsIdchats;
    }

    public int getUsersIdusers()
    {
        return usersIdusers;
    }

    public void setUsersIdusers(int usersIdusers)
    {
        this.usersIdusers = usersIdusers;
    }

    public int getChatsIdchats()
    {
        return chatsIdchats;
    }

    public void setChatsIdchats(int chatsIdchats)
    {
        this.chatsIdchats = chatsIdchats;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (int) usersIdusers;
        hash += (int) chatsIdchats;
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChatusersPK))
        {
            return false;
        }
        ChatusersPK other = (ChatusersPK) object;
        if (this.usersIdusers != other.usersIdusers)
        {
            return false;
        }
        if (this.chatsIdchats != other.chatsIdchats)
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.ChatusersPK[ usersIdusers=" + usersIdusers + ", chatsIdchats=" + chatsIdchats + " ]";
    }
    
}
