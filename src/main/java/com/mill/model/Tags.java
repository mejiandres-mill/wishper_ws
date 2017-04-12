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
import javax.persistence.ManyToMany;
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
@Table(name = "tags", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Tags.findAll", query = "SELECT t FROM Tags t"),
    @NamedQuery(name = "Tags.findByIdtags", query = "SELECT t FROM Tags t WHERE t.idtags = :idtags"),
    @NamedQuery(name = "Tags.findByName", query = "SELECT t FROM Tags t WHERE t.name = :name")
})
public class Tags implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idtags;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    private String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "tagsList")
    private List<Stores> storesList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tagsIdtags")
    private List<ProductTags> productTagsList;

    public Tags()
    {
    }

    public Tags(Integer idtags)
    {
        this.idtags = idtags;
    }

    public Tags(Integer idtags, String name)
    {
        this.idtags = idtags;
        this.name = name;
    }

    public Integer getIdtags()
    {
        return idtags;
    }

    public void setIdtags(Integer idtags)
    {
        this.idtags = idtags;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @XmlTransient
    public List<Stores> getStoresList()
    {
        return storesList;
    }

    public void setStoresList(List<Stores> storesList)
    {
        this.storesList = storesList;
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

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idtags != null ? idtags.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tags))
        {
            return false;
        }
        Tags other = (Tags) object;
        if ((this.idtags == null && other.idtags != null) || (this.idtags != null && !this.idtags.equals(other.idtags)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.Tags[ idtags=" + idtags + " ]";
    }
    
}
