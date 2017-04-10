/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "stores", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Stores.findAll", query = "SELECT s FROM Stores s"),
    @NamedQuery(name = "Stores.findByIdstores", query = "SELECT s FROM Stores s WHERE s.idstores = :idstores"),
    @NamedQuery(name = "Stores.findByName", query = "SELECT s FROM Stores s WHERE s.name = :name"),
    @NamedQuery(name = "Stores.findBySite", query = "SELECT s FROM Stores s WHERE s.site = :site")
})
public class Stores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idstores;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    private String name;
    @Size(max = 1024)
    private String site;
    @JoinTable(name = "store_tags", joinColumns =
    {
        @JoinColumn(name = "stores_idstores", referencedColumnName = "idstores")
    }, inverseJoinColumns =
    {
        @JoinColumn(name = "tags_idtags", referencedColumnName = "idtags")
    })
    @ManyToMany
    private List<Tags> tagsList;
    @JoinColumn(name = "countries_idcountries", referencedColumnName = "idcountries")
    @ManyToOne
    private Countries countriesIdcountries;
    @OneToMany(mappedBy = "storesIdstores")
    private List<Products> productsList;

    public Stores()
    {
    }

    public Stores(Integer idstores)
    {
        this.idstores = idstores;
    }

    public Stores(Integer idstores, String name)
    {
        this.idstores = idstores;
        this.name = name;
    }

    public Integer getIdstores()
    {
        return idstores;
    }

    public void setIdstores(Integer idstores)
    {
        this.idstores = idstores;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSite()
    {
        return site;
    }

    public void setSite(String site)
    {
        this.site = site;
    }

    @XmlTransient
    public List<Tags> getTagsList()
    {
        return tagsList;
    }

    public void setTagsList(List<Tags> tagsList)
    {
        this.tagsList = tagsList;
    }

    public Countries getCountriesIdcountries()
    {
        return countriesIdcountries;
    }

    public void setCountriesIdcountries(Countries countriesIdcountries)
    {
        this.countriesIdcountries = countriesIdcountries;
    }

    @XmlTransient
    public List<Products> getProductsList()
    {
        return productsList;
    }

    public void setProductsList(List<Products> productsList)
    {
        this.productsList = productsList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idstores != null ? idstores.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stores))
        {
            return false;
        }
        Stores other = (Stores) object;
        if ((this.idstores == null && other.idstores != null) || (this.idstores != null && !this.idstores.equals(other.idstores)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.Stores[ idstores=" + idstores + " ]";
    }
    
}
