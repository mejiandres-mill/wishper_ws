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
public class PurchasesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "users_idusers")
    private int usersIdusers;
    @Basic(optional = false)
    @NotNull
    @Column(name = "products_idproducts")
    private int productsIdproducts;

    public PurchasesPK()
    {
    }

    public PurchasesPK(int usersIdusers, int productsIdproducts)
    {
        this.usersIdusers = usersIdusers;
        this.productsIdproducts = productsIdproducts;
    }

    public int getUsersIdusers()
    {
        return usersIdusers;
    }

    public void setUsersIdusers(int usersIdusers)
    {
        this.usersIdusers = usersIdusers;
    }

    public int getProductsIdproducts()
    {
        return productsIdproducts;
    }

    public void setProductsIdproducts(int productsIdproducts)
    {
        this.productsIdproducts = productsIdproducts;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (int) usersIdusers;
        hash += (int) productsIdproducts;
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PurchasesPK))
        {
            return false;
        }
        PurchasesPK other = (PurchasesPK) object;
        if (this.usersIdusers != other.usersIdusers)
        {
            return false;
        }
        if (this.productsIdproducts != other.productsIdproducts)
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.PurchasesPK[ usersIdusers=" + usersIdusers + ", productsIdproducts=" + productsIdproducts + " ]";
    }
    
}
