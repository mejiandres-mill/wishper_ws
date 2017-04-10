/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mill2
 */
@Embeddable
public class FriendsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    private int friender;
    @Basic(optional = false)
    @NotNull
    private int friendee;

    public FriendsPK()
    {
    }

    public FriendsPK(int friender, int friendee)
    {
        this.friender = friender;
        this.friendee = friendee;
    }

    public int getFriender()
    {
        return friender;
    }

    public void setFriender(int friender)
    {
        this.friender = friender;
    }

    public int getFriendee()
    {
        return friendee;
    }

    public void setFriendee(int friendee)
    {
        this.friendee = friendee;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (int) friender;
        hash += (int) friendee;
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FriendsPK))
        {
            return false;
        }
        FriendsPK other = (FriendsPK) object;
        if (this.friender != other.friender)
        {
            return false;
        }
        if (this.friendee != other.friendee)
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.FriendsPK[ friender=" + friender + ", friendee=" + friendee + " ]";
    }
    
}
