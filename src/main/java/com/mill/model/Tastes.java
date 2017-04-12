/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mill2
 */
@Entity
@Table(name = "tastes", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Tastes.findAll", query = "SELECT t FROM Tastes t"),
    @NamedQuery(name = "Tastes.findByUsersIdusers", query = "SELECT t FROM Tastes t WHERE t.tastesPK.usersIdusers = :usersIdusers"),
    @NamedQuery(name = "Tastes.findByProductsIdproducts", query = "SELECT t FROM Tastes t WHERE t.tastesPK.productsIdproducts = :productsIdproducts"),
    @NamedQuery(name = "Tastes.findByLiked", query = "SELECT t FROM Tastes t WHERE t.liked = :liked"),
    @NamedQuery(name = "Tastes.findByInterDate", query = "SELECT t FROM Tastes t WHERE t.interDate = :interDate"),
    @NamedQuery(name = "Tastes.findByKeys", query= "SELECT t FROM Tastes t WHERE t.tastesPK.productsIdproducts = :idproducts AND t.tastesPK.usersIdusers = :idusers")
})
public class Tastes implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TastesPK tastesPK;
    private Boolean liked;
    @Column(name = "inter_date")
    @Temporal(TemporalType.DATE)
    private Date interDate;
    @JoinColumn(name = "products_idproducts", referencedColumnName = "idproducts", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Products products;
    @JoinColumn(name = "users_idusers", referencedColumnName = "idusers", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public Tastes()
    {
    }

    public Tastes(TastesPK tastesPK)
    {
        this.tastesPK = tastesPK;
    }

    public Tastes(int usersIdusers, int productsIdproducts)
    {
        this.tastesPK = new TastesPK(usersIdusers, productsIdproducts);
    }

    public TastesPK getTastesPK()
    {
        return tastesPK;
    }

    public void setTastesPK(TastesPK tastesPK)
    {
        this.tastesPK = tastesPK;
    }

    public Boolean getLiked()
    {
        return liked;
    }

    public void setLiked(Boolean liked)
    {
        this.liked = liked;
    }

    public Date getInterDate()
    {
        return interDate;
    }

    public void setInterDate(Date interDate)
    {
        this.interDate = interDate;
    }

    public Products getProducts()
    {
        return products;
    }

    public void setProducts(Products products)
    {
        this.products = products;
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
        hash += (tastesPK != null ? tastesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tastes))
        {
            return false;
        }
        Tastes other = (Tastes) object;
        if ((this.tastesPK == null && other.tastesPK != null) || (this.tastesPK != null && !this.tastesPK.equals(other.tastesPK)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.Tastes[ tastesPK=" + tastesPK + " ]";
    }
    
}
