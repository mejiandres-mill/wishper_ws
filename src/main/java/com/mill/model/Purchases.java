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
@Table(name = "purchases", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Purchases.findAll", query = "SELECT p FROM Purchases p"),
    @NamedQuery(name = "Purchases.findByUsersIdusers", query = "SELECT p FROM Purchases p WHERE p.purchasesPK.usersIdusers = :usersIdusers"),
    @NamedQuery(name = "Purchases.findByProductsIdproducts", query = "SELECT p FROM Purchases p WHERE p.purchasesPK.productsIdproducts = :productsIdproducts"),
    @NamedQuery(name = "Purchases.findByPurchaseDate", query = "SELECT p FROM Purchases p WHERE p.purchaseDate = :purchaseDate")
})
public class Purchases implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PurchasesPK purchasesPK;
    @Column(name = "purchase_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;

    public Purchases()
    {
    }

    public Purchases(PurchasesPK purchasesPK)
    {
        this.purchasesPK = purchasesPK;
    }

    public Purchases(int usersIdusers, int productsIdproducts)
    {
        this.purchasesPK = new PurchasesPK(usersIdusers, productsIdproducts);
    }

    public PurchasesPK getPurchasesPK()
    {
        return purchasesPK;
    }

    public void setPurchasesPK(PurchasesPK purchasesPK)
    {
        this.purchasesPK = purchasesPK;
    }

    public Date getPurchaseDate()
    {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate)
    {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (purchasesPK != null ? purchasesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Purchases))
        {
            return false;
        }
        Purchases other = (Purchases) object;
        if ((this.purchasesPK == null && other.purchasesPK != null) || (this.purchasesPK != null && !this.purchasesPK.equals(other.purchasesPK)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.Purchases[ purchasesPK=" + purchasesPK + " ]";
    }
    
}
