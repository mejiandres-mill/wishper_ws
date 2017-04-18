/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mill2
 */
@Entity
@Table(name = "products", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p"),
    @NamedQuery(name = "Products.findByIdproducts", query = "SELECT p FROM Products p WHERE p.idproducts = :idproducts"),
    @NamedQuery(name = "Products.findByName", query = "SELECT p FROM Products p WHERE p.name = :name"),
    @NamedQuery(name = "Products.findByDescription", query = "SELECT p FROM Products p WHERE p.description = :description"),
    @NamedQuery(name = "Products.findByPrice", query = "SELECT p FROM Products p WHERE p.price = :price"),
    @NamedQuery(name = "Products.findByShowing", query = "SELECT p FROM Products p WHERE p.showing = :showing"),
    @NamedQuery(name = "Products.findByPublishdate", query = "SELECT p FROM Products p WHERE p.publishdate = :publishdate"),
    @NamedQuery(name = "Products.lookup", query = "SELECT p FROM Products p WHERE p.description LIKE :term OR p.name LIKE :term")
})
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idproducts;
    @Size(max = 256)
    private String name;
    @Size(max = 1024)
    private String description;
    @Size(max = 20)
    private String price;
    private Integer showing;
    @Temporal(TemporalType.DATE)
    private Date publishdate;
    @ManyToMany(mappedBy = "productsList")
    @JsonIgnore
    private List<Messages> messagesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "products")
    @JsonIgnore
    private List<Tastes> tastesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "products")
    @JsonIgnore
    private List<Recomendations> recomendationsList;
    @JoinColumn(name = "stores_idstores", referencedColumnName = "idstores")
    @ManyToOne
    private Stores storesIdstores;
    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productsIdproducts")
    private List<ProductImages> productImagesList;
    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productsIdproducts")
    private List<ProductTags> productTagsList;
    @Transient
    private List<Images> images;

    public Products()
    {
        images = new ArrayList<>();
    }

    public Products(Integer idproducts)
    {
        this.idproducts = idproducts;
        images = new ArrayList<>();
    }

    public Integer getIdproducts()
    {
        return idproducts;
    }

    public void setIdproducts(Integer idproducts)
    {
        this.idproducts = idproducts;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public Integer getShowing()
    {
        return showing;
    }

    public void setShowing(Integer showing)
    {
        this.showing = showing;
    }

    public Date getPublishdate()
    {
        return publishdate;
    }

    public void setPublishdate(Date publishdate)
    {
        this.publishdate = publishdate;
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

    @XmlTransient
    public List<Tastes> getTastesList()
    {
        return tastesList;
    }

    public void setTastesList(List<Tastes> tastesList)
    {
        this.tastesList = tastesList;
    }

    @XmlTransient
    public List<Recomendations> getRecomendationsList()
    {
        return recomendationsList;
    }

    public void setRecomendationsList(List<Recomendations> recomendationsList)
    {
        this.recomendationsList = recomendationsList;
    }

    public Stores getStoresIdstores()
    {
        return storesIdstores;
    }

    public void setStoresIdstores(Stores storesIdstores)
    {
        this.storesIdstores = storesIdstores;
    }

    @XmlTransient
    public List<ProductImages> getProductImagesList()
    {
        return productImagesList;
    }

    public void setProductImagesList(List<ProductImages> productImagesList)
    {
        this.productImagesList = productImagesList;
    }

    @XmlTransient
    public List<ProductTags> getProductTagsList()
    {
        return productTagsList;
    }

    public void setProductTagsList(List<ProductTags> productTagsList)
    {
        this.productTagsList = productTagsList;
    }
    
    public List<Images> getImages()
    {
        if(images == null)
            images = new ArrayList<>();
        for(ProductImages pi :productImagesList)
        {
            images.add(pi.getImagesIdimages());
        }
        return images;
    }
    
    public void setImages(List<Images> images)
    {
        this.images = images;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idproducts != null ? idproducts.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Products))
        {
            return false;
        }
        Products other = (Products) object;
        if ((this.idproducts == null && other.idproducts != null) || (this.idproducts != null && !this.idproducts.equals(other.idproducts)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.Products[ idproducts=" + idproducts + " ]";
    }
    
}
