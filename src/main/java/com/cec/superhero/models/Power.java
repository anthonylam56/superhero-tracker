/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cec.superhero.models;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author etdeh
 */
@Entity
@Table (name="powers")
public class Power {
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private int id;
    @NotBlank(message = "Power must have name")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "Power needs a description")
    @Column(nullable = false)
    private String descr;
    
    @ManyToMany(mappedBy = "powers")
    private List<Super> supers = new ArrayList<Super>();
    
    public void addSuper(Super sup){
        this.supers.add(sup);
        sup.getPowers().add(this);
    }
    
    public void removeSuper(Super sup){
        this.supers.remove(sup);
        sup.getPowers().remove(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
    
    public  String  getJSON()
    {
        Gson gson = new Gson();
       return gson.toJson(this);
    }

    public List<Super> getSupers() {
        return supers;
    }

    public void setSupers(List<Super> supers) {
        this.supers = supers;
    }
    
    

    @Override
    public String toString() {
        return "Power{" + "id=" + id + ", name=" + name + ", descr=" + descr + ", supers=" + supers + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.id;
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + Objects.hashCode(this.descr);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Power other = (Power) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.descr, other.descr)) {
            return false;
        }
        return true;
    }
    
    
}
