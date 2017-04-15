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
@Table(name = "recomendations", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Recomendations.findAll", query = "SELECT r FROM Recomendations r"),
    @NamedQuery(name = "Recomendations.findByRecomender", query = "SELECT r FROM Recomendations r WHERE r.recomendationsPK.recomender = :recomender"),
    @NamedQuery(name = "Recomendations.findByReceipient", query = "SELECT r FROM Recomendations r WHERE r.recomendationsPK.receipient = :receipient"),
    @NamedQuery(name = "Recomendations.findByProductsIdproducts", query = "SELECT r FROM Recomendations r WHERE r.recomendationsPK.productsIdproducts = :productsIdproducts"),
    @NamedQuery(name = "Recomendations.findByRecDate", query = "SELECT r FROM Recomendations r WHERE r.recDate = :recDate"),
    @NamedQuery(name = "Recomendations.findByShowed", query = "SELECT r FROM Recomendations r WHERE r.showed = :showed"),
    @NamedQuery(name = "Recomendations.findByKeys", query = "SELECT r FROM Recomendations r WHERE r.recomendationsPK.recomender = :recomender AND r.recomendationsPK.receipient = :receipient AND r.recomendationsPK.productsIdproducts = :idproducts"),
    @NamedQuery(name = "Recomendations.deleteRecomendations", query = "DELETE FROM Recomendations r WHERE r.recomendationsPK.productsIdproducts = :idproducts AND r.recomendationsPK.receipient = :idusers")
})
public class Recomendations implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RecomendationsPK recomendationsPK;
    @Column(name = "rec_date")
    @Temporal(TemporalType.DATE)
    private Date recDate;
    private Boolean showed;
    @JoinColumn(name = "receipient", referencedColumnName = "idusers", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;
    @JoinColumn(name = "recomender", referencedColumnName = "idusers", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users1;
    @JoinColumn(name = "products_idproducts", referencedColumnName = "idproducts", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Products products;

    public Recomendations()
    {
    }

    public Recomendations(RecomendationsPK recomendationsPK)
    {
        this.recomendationsPK = recomendationsPK;
    }

    public Recomendations(int recomender, int receipient, int productsIdproducts)
    {
        this.recomendationsPK = new RecomendationsPK(recomender, receipient, productsIdproducts);
    }

    public RecomendationsPK getRecomendationsPK()
    {
        return recomendationsPK;
    }

    public void setRecomendationsPK(RecomendationsPK recomendationsPK)
    {
        this.recomendationsPK = recomendationsPK;
    }

    public Date getRecDate()
    {
        return recDate;
    }

    public void setRecDate(Date recDate)
    {
        this.recDate = recDate;
    }

    public Boolean getShowed()
    {
        return showed;
    }

    public void setShowed(Boolean showed)
    {
        this.showed = showed;
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

    public Products getProducts()
    {
        return products;
    }

    public void setProducts(Products products)
    {
        this.products = products;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (recomendationsPK != null ? recomendationsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recomendations))
        {
            return false;
        }
        Recomendations other = (Recomendations) object;
        if ((this.recomendationsPK == null && other.recomendationsPK != null) || (this.recomendationsPK != null && !this.recomendationsPK.equals(other.recomendationsPK)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.Recomendations[ recomendationsPK=" + recomendationsPK + " ]";
    }
    
}
