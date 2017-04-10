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
public class RecomendationsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    private int recomender;
    @Basic(optional = false)
    @NotNull
    private int receipient;
    @Basic(optional = false)
    @NotNull
    @Column(name = "products_idproducts")
    private int productsIdproducts;

    public RecomendationsPK()
    {
    }

    public RecomendationsPK(int recomender, int receipient, int productsIdproducts)
    {
        this.recomender = recomender;
        this.receipient = receipient;
        this.productsIdproducts = productsIdproducts;
    }

    public int getRecomender()
    {
        return recomender;
    }

    public void setRecomender(int recomender)
    {
        this.recomender = recomender;
    }

    public int getReceipient()
    {
        return receipient;
    }

    public void setReceipient(int receipient)
    {
        this.receipient = receipient;
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
        hash += (int) recomender;
        hash += (int) receipient;
        hash += (int) productsIdproducts;
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecomendationsPK))
        {
            return false;
        }
        RecomendationsPK other = (RecomendationsPK) object;
        if (this.recomender != other.recomender)
        {
            return false;
        }
        if (this.receipient != other.receipient)
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
        return "com.mill.model.RecomendationsPK[ recomender=" + recomender + ", receipient=" + receipient + ", productsIdproducts=" + productsIdproducts + " ]";
    }
    
}
