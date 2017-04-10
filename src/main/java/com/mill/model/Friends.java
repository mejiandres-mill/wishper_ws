/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mill2
 */
@Entity
@Table(name = "friends", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Friends.findAll", query = "SELECT f FROM Friends f"),
    @NamedQuery(name = "Friends.findByFriender", query = "SELECT f FROM Friends f WHERE f.friendsPK.friender = :friender"),
    @NamedQuery(name = "Friends.findByFriendee", query = "SELECT f FROM Friends f WHERE f.friendsPK.friendee = :friendee"),
    @NamedQuery(name = "Friends.findByBefriendDate", query = "SELECT f FROM Friends f WHERE f.befriendDate = :befriendDate")
})
public class Friends implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FriendsPK friendsPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "befriend_date")
    @Temporal(TemporalType.DATE)
    private Date befriendDate;
    @JoinColumn(name = "friendee", referencedColumnName = "idusers", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;
    @JoinColumn(name = "friender", referencedColumnName = "idusers", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users1;

    public Friends()
    {
    }

    public Friends(FriendsPK friendsPK)
    {
        this.friendsPK = friendsPK;
    }

    public Friends(FriendsPK friendsPK, Date befriendDate)
    {
        this.friendsPK = friendsPK;
        this.befriendDate = befriendDate;
    }

    public Friends(int friender, int friendee)
    {
        this.friendsPK = new FriendsPK(friender, friendee);
    }

    public FriendsPK getFriendsPK()
    {
        return friendsPK;
    }

    public void setFriendsPK(FriendsPK friendsPK)
    {
        this.friendsPK = friendsPK;
    }

    public Date getBefriendDate()
    {
        return befriendDate;
    }

    public void setBefriendDate(Date befriendDate)
    {
        this.befriendDate = befriendDate;
    }

    public Users getUsers()
    {
        return users;
    }

    public void setUsers(Users users)
    {
        this.users = users;
    }

    public Users getUsers1()
    {
        return users1;
    }

    public void setUsers1(Users users1)
    {
        this.users1 = users1;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (friendsPK != null ? friendsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Friends))
        {
            return false;
        }
        Friends other = (Friends) object;
        if ((this.friendsPK == null && other.friendsPK != null) || (this.friendsPK != null && !this.friendsPK.equals(other.friendsPK)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.Friends[ friendsPK=" + friendsPK + " ]";
    }
    
}
