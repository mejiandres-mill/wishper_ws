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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "chats", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Chats.findAll", query = "SELECT c FROM Chats c"),
    @NamedQuery(name = "Chats.findByIdchats", query = "SELECT c FROM Chats c WHERE c.idchats = :idchats"),
    @NamedQuery(name = "Chats.findByName", query = "SELECT c FROM Chats c WHERE c.name = :name"),
    @NamedQuery(name = "Chats.findByCreationdate", query = "SELECT c FROM Chats c WHERE c.creationdate = :creationdate")
})
public class Chats implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idchats;
    @Size(max = 124)
    private String name;
    @Temporal(TemporalType.DATE)
    private Date creationdate;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chats")
    private List<Chatusers> chatusersList;
    @JoinColumn(name = "users_idusers", referencedColumnName = "idusers")
    @ManyToOne
    private Users usersIdusers;
    @OneToMany(mappedBy = "chatsIdchats")
    private List<Messages> messagesList;

    public Chats()
    {
    }

    public Chats(Integer idchats)
    {
        this.idchats = idchats;
    }

    public Integer getIdchats()
    {
        return idchats;
    }

    public void setIdchats(Integer idchats)
    {
        this.idchats = idchats;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getCreationdate()
    {
        return creationdate;
    }

    public void setCreationdate(Date creationdate)
    {
        this.creationdate = creationdate;
    }

    @XmlTransient
    public List<Chatusers> getChatusersList()
    {
        return chatusersList;
    }

    public void setChatusersList(List<Chatusers> chatusersList)
    {
        this.chatusersList = chatusersList;
    }

    public Users getUsersIdusers()
    {
        return usersIdusers;
    }

    public void setUsersIdusers(Users usersIdusers)
    {
        this.usersIdusers = usersIdusers;
    }

    @XmlTransient
    public List<Messages> getMessagesList()
    {
        return messagesList;
    }

    public void setMessagesList(List<Messages> messagesList)
    {
        this.messagesList = messagesList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idchats != null ? idchats.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chats))
        {
            return false;
        }
        Chats other = (Chats) object;
        if ((this.idchats == null && other.idchats != null) || (this.idchats != null && !this.idchats.equals(other.idchats)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.Chats[ idchats=" + idchats + " ]";
    }
    
}
