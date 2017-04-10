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
@Table(name ="countries", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Countries.findAll", query = "SELECT c FROM Countries c"),
    @NamedQuery(name = "Countries.findByIdcountries", query = "SELECT c FROM Countries c WHERE c.idcountries = :idcountries"),
    @NamedQuery(name = "Countries.findByName", query = "SELECT c FROM Countries c WHERE c.name = :name")
})
public class Countries implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idcountries;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "countriesIdcountries")
    private List<Stores> storesList;
    @JsonIgnore
    @OneToMany(mappedBy = "countriesIdcountries")
    private List<Users> usersList;

    public Countries()
    {
    }

    public Countries(Integer idcountries)
    {
        this.idcountries = idcountries;
    }

    public Countries(Integer idcountries, String name)
    {
        this.idcountries = idcountries;
        this.name = name;
    }

    public Integer getIdcountries()
    {
        return idcountries;
    }

    public void setIdcountries(Integer idcountries)
    {
        this.idcountries = idcountries;
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
    public List<Users> getUsersList()
    {
        return usersList;
    }

    public void setUsersList(List<Users> usersList)
    {
        this.usersList = usersList;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idcountries != null ? idcountries.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Countries))
        {
            return false;
        }
        Countries other = (Countries) object;
        if ((this.idcountries == null && other.idcountries != null) || (this.idcountries != null && !this.idcountries.equals(other.idcountries)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.mill.model.Countries[ idcountries=" + idcountries + " ]";
    }
    
}
