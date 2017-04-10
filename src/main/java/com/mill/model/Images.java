/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mill2
 */
@Entity
@Table(name = "images", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Images.findAll", query = "SELECT i FROM Images i"),
    @NamedQuery(name = "Images.findByIdimages", query = "SELECT i FROM Images i WHERE i.idimages = :idimages"),
    @NamedQuery(name = "Images.findByUrl", query = "SELECT i FROM Images i WHERE i.url = :url")
})
public class Images implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idimages;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    private String url;
    @JsonIgnore
    @OneToMany(mappedBy = "imagesIdimages")
    private List<Users> usersList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "imagesIdimages")
    private List<ProductImages> productImagesList;

    public Images()
    {
    }

    public Images(Integer idimages)
    {
        this.idimages = idimages;
    }

    public Images(Integer idimages, String url)
    {
        this.idimages = idimages;
        this.url = url;
    }

    public Integer getIdimages()
    {
        return idimages;
    }

    public void setIdimages(Integer idimages)
    {
        this.idimages = idimages;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    @XmlTransient
    public List<Users> getUsersList()
    {
        return usersList;
    }

    public void setUsersList(List<Users> usersList)
    {
        this.usersList = usersList;
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

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idimages != null ? idimages.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Images))
        {
            return false;
        }
        Images other = (Images) object;
        if ((this.idimages == null && other.idimages != null) || (this.idimages != null && !this.idimages.equals(other.idimages)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "[ idimages=" + idimages + " ]\n"
              +"[ url=" + url +"]";
    }
    
}
