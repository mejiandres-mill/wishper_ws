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
@Table(name = "product_images", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "ProductImages.findAll", query = "SELECT p FROM ProductImages p"),
    @NamedQuery(name = "ProductImages.findByIdproductImages", query = "SELECT p FROM ProductImages p WHERE p.idproductImages = :idproductImages"),
    @NamedQuery(name = "ProductImages.findByShow", query = "SELECT p FROM ProductImages p WHERE p.show = :show")
})
public class ProductImages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproduct_images")
    private Integer idproductImages;
    private Short show;
    @JoinColumn(name = "images_idimages", referencedColumnName = "idimages")
    @ManyToOne(optional = false)
    private Images imagesIdimages;
    @JoinColumn(name = "products_idproducts", referencedColumnName = "idproducts")
    @ManyToOne(optional = false)
    private Products productsIdproducts;

    public ProductImages()
    {
    }

    public ProductImages(Integer idproductImages)
    {
        this.idproductImages = idproductImages;
    }

    public Integer getIdproductImages()
    {
        return idproductImages;
    }

    public void setIdproductImages(Integer idproductImages)
    {
        this.idproductImages = idproductImages;
    }

    public Short getShow()
    {
        return show;
    }

    public void setShow(Short show)
    {
        this.show = show;
    }

    public Images getImagesIdimages()
    {
        return imagesIdimages;
    }

    public void setImagesIdimages(Images imagesIdimages)
    {
        this.imagesIdimages = imagesIdimages;
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
        hash += (idproductImages != null ? idproductImages.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductImages))
        {
            return false;
        }
        ProductImages other = (ProductImages) object;
        if ((this.idproductImages == null && other.idproductImages != null) || (this.idproductImages != null && !this.idproductImages.equals(other.idproductImages)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.ProductImages[ idproductImages=" + idproductImages + " ]";
    }
    
}
