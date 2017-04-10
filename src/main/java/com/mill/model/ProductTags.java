/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mill2
 */
@Entity
@Table(name = "product_tags", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "ProductTags.findAll", query = "SELECT p FROM ProductTags p"),
    @NamedQuery(name = "ProductTags.findByIdproductTags", query = "SELECT p FROM ProductTags p WHERE p.idproductTags = :idproductTags")
})
public class ProductTags implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproduct_tags")
    private Integer idproductTags;
    @JoinColumn(name = "tags_idtags", referencedColumnName = "idtags")
    @ManyToOne(optional = false)
    private Tags tagsIdtags;
    @JoinColumn(name = "products_idproducts", referencedColumnName = "idproducts")
    @ManyToOne(optional = false)
    private Products productsIdproducts;

    public ProductTags()
    {
    }

    public ProductTags(Integer idproductTags)
    {
        this.idproductTags = idproductTags;
    }

    public Integer getIdproductTags()
    {
        return idproductTags;
    }

    public void setIdproductTags(Integer idproductTags)
    {
        this.idproductTags = idproductTags;
    }

    public Tags getTagsIdtags()
    {
        return tagsIdtags;
    }

    public void setTagsIdtags(Tags tagsIdtags)
    {
        this.tagsIdtags = tagsIdtags;
    }

    public Products getProductsIdproducts()
    {
        return productsIdproducts;
    }

    public void setProductsIdproducts(Products productsIdproducts)
    {
        this.productsIdproducts = productsIdproducts;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idproductTags != null ? idproductTags.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductTags))
        {
            return false;
        }
        ProductTags other = (ProductTags) object;
        if ((this.idproductTags == null && other.idproductTags != null) || (this.idproductTags != null && !this.idproductTags.equals(other.idproductTags)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.ProductTags[ idproductTags=" + idproductTags + " ]";
    }
    
}
