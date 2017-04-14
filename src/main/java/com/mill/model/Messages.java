/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mill2
 */
@Entity
@Table(name = "messages", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Messages.findAll", query = "SELECT m FROM Messages m"),
    @NamedQuery(name = "Messages.findByIdmessages", query = "SELECT m FROM Messages m WHERE m.idmessages = :idmessages"),
    @NamedQuery(name = "Messages.findByTime", query = "SELECT m FROM Messages m WHERE m.time = :time"),
    @NamedQuery(name = "Messages.findByMessage", query = "SELECT m FROM Messages m WHERE m.message = :message")
})
public class Messages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idmessages;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Size(max = 256)
    private String message;
    @JoinTable(name = "message_products", joinColumns =
    {
        @JoinColumn(name = "messages_idmessages", referencedColumnName = "idmessages")
    }, inverseJoinColumns =
    {
        @JoinColumn(name = "products_idproducts", referencedColumnName = "idproducts")
    })
    @ManyToMany
    private List<Products> productsList;
    @JsonIgnore
    @JoinColumn(name = "chats_idchats", referencedColumnName = "idchats")
    @ManyToOne
    private Chats chatsIdchats;
    @JsonIgnore
    @JoinColumn(name = "users_idusers", referencedColumnName = "idusers")
    @ManyToOne
    private Users usersIdusers;

    public Messages()
    {
    }

    public Messages(Integer idmessages)
    {
        this.idmessages = idmessages;
    }

    public Integer getIdmessages()
    {
        return idmessages;
    }

    public void setIdmessages(Integer idmessages)
    {
        this.idmessages = idmessages;
    }

    public Date getTime()
    {
        return time;
    }

    public void setTime(Date time)
    {
        this.time = time;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @XmlTransient
    public List<Products> getProductsList()
    {
        return productsList;
    }

    public void setProductsList(List<Products> productsList)
    {
        this.productsList = productsList;
    }

    public Chats getChatsIdchats()
    {
        return chatsIdchats;
    }

    public void setChatsIdchats(Chats chatsIdchats)
    {
        this.chatsIdchats = chatsIdchats;
    }

    public Users getUsersIdusers()
    {
        return usersIdusers;
    }

    public void setUsersIdusers(Users usersIdusers)
    {
        this.usersIdusers = usersIdusers;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idmessages != null ? idmessages.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Messages))
        {
            return false;
        }
        Messages other = (Messages) object;
        if ((this.idmessages == null && other.idmessages != null) || (this.idmessages != null && !this.idmessages.equals(other.idmessages)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.Messages[ idmessages=" + idmessages + " ]";
    }
    
}
