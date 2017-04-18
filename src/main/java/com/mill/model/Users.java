/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mill.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mill2
 */
@Entity
@Table(name="users", catalog = "wiishper2db", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByIdusers", query = "SELECT u FROM Users u WHERE u.idusers = :idusers"),
    @NamedQuery(name = "Users.findByName", query = "SELECT u FROM Users u WHERE u.name = :name"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByGender", query = "SELECT u FROM Users u WHERE u.gender = :gender"),
    @NamedQuery(name = "Users.findByBirthdate", query = "SELECT u FROM Users u WHERE u.birthdate = :birthdate"),
    @NamedQuery(name = "Users.findByEntrydate", query = "SELECT u FROM Users u WHERE u.entrydate = :entrydate"),
    @NamedQuery(name = "Users.findByActive", query = "SELECT u FROM Users u WHERE u.active = :active"),
    @NamedQuery(name = "Users.findByApikey", query = "SELECT u FROM Users u WHERE u.apikey = :apikey"),
    @NamedQuery(name = "Users.lookUp", query = "SELECT u FROM Users u WHERE u.name LIKE :term OR u.email LIKE :term")
})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idusers;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    private String name;
    @Size(max = 256)
    private String password;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    private String email;
    @Size(max = 1)
    private String gender;
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Temporal(TemporalType.DATE)
    private Date entrydate;
    private Boolean active;
    @Size(max = 256)
    private String apikey;
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "users")
    private List<Tastes> tastesList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Recomendations> recomendationsList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users1")
    private List<Recomendations> recomendationsList1;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Friends> friendsList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users1", fetch = FetchType.EAGER)
    private List<Friends> friendsList1;
    @JoinColumn(name = "countries_idcountries", referencedColumnName = "idcountries")
    @ManyToOne
    private Countries countriesIdcountries;
    @JoinColumn(name = "images_idimages", referencedColumnName = "idimages")
    @ManyToOne
    private Images imagesIdimages;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Chatusers> chatusersList;
    @JsonIgnore
    @OneToMany(mappedBy = "usersIdusers")
    private List<Chats> chatsList;
    @JsonIgnore
    @OneToMany(mappedBy = "usersIdusers")
    private List<Messages> messagesList;

    public Users()
    {
    }

    public Users(Integer idusers)
    {
        this.idusers = idusers;
    }

    public Users(Integer idusers, String name, String email)
    {
        this.idusers = idusers;
        this.name = name;
        this.email = email;
    }
    
    public Users(Users u)
    {
        idusers = u.idusers;
        active = u.active;
        name = u.name;
        email = u.email;
        password = u.password;
        birthdate = u.birthdate;
        entrydate = u.entrydate;
        gender = u.gender;
        imagesIdimages = u.imagesIdimages;
        apikey = u.apikey;
        countriesIdcountries = u.countriesIdcountries;
    }

    public Integer getIdusers()
    {
        return idusers;
    }

    public void setIdusers(Integer idusers)
    {
        this.idusers = idusers;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public Date getBirthdate()
    {
        return birthdate;
    }

    public void setBirthdate(Date birthdate)
    {
        this.birthdate = birthdate;
    }

    public Date getEntrydate()
    {
        return entrydate;
    }

    public void setEntrydate(Date entrydate)
    {
        this.entrydate = entrydate;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public String getApikey()
    {
        return apikey;
    }

    public void setApikey(String apikey)
    {
        this.apikey = apikey;
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

    @XmlTransient
    public List<Recomendations> getRecomendationsList1()
    {
        return recomendationsList1;
    }

    public void setRecomendationsList1(List<Recomendations> recomendationsList1)
    {
        this.recomendationsList1 = recomendationsList1;
    }

    @XmlTransient
    public List<Friends> getFriendsList()
    {
        return friendsList;
    }

    public void setFriendsList(List<Friends> friendsList)
    {
        this.friendsList = friendsList;
    }

    @XmlTransient
    public List<Friends> getFriendsList1()
    {
        return friendsList1;
    }

    public void setFriendsList1(List<Friends> friendsList1)
    {
        this.friendsList1 = friendsList1;
    }

    public Countries getCountriesIdcountries()
    {
        return countriesIdcountries;
    }

    public void setCountriesIdcountries(Countries countriesIdcountries)
    {
        this.countriesIdcountries = countriesIdcountries;
    }

    public Images getImagesIdimages()
    {
        return imagesIdimages;
    }

    public void setImagesIdimages(Images imagesIdimages)
    {
        this.imagesIdimages = imagesIdimages;
    }

    @XmlTransient
    public List<Chatusers> getChatusersList()
    {
        return chatusersList;
    }

    public void setChatusersList(List<Chatusers> chatusersList)
    {
        this.chatusersList = chatusersList;
    }

    @XmlTransient
    public List<Chats> getChatsList()
    {
        return chatsList;
    }

    public void setChatsList(List<Chats> chatsList)
    {
        this.chatsList = chatsList;
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

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idusers != null ? idusers.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users))
        {
            return false;
        }
        Users other = (Users) object;
        if ((this.idusers == null && other.idusers != null) || (this.idusers != null && !this.idusers.equals(other.idusers)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "[ idusers=" + idusers + " ] \n"
              +"[ email=" + email + "  ] \n"
              +"[ apikey=" + apikey + "  ] \n"
              +"[ name=" + name + "  ] \n";
    }
    
}
