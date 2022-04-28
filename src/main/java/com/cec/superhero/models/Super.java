/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cec.superhero.models;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author etdeh
 */
@Entity
@Table (name="supers")
public class Super {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @NotBlank(message = "Name must be provided")
    @Column(nullable = false)
    private String name;
    @Column
    private String descr;
    @Column
    private Boolean hero;
    @Column(nullable = true, length = 64)
    private String photos;
    @ManyToMany
    @JoinTable(name = "powers_has_super",
            joinColumns = {@JoinColumn(name = "super_id")},
            inverseJoinColumns = {@JoinColumn(name = "powers_id")})
    private Set<Power> powers = new HashSet<Power>();
    @ManyToMany
    @JoinTable(name = "super_has_organization",
            joinColumns = {@JoinColumn(name = "super_id")},
            inverseJoinColumns = {@JoinColumn(name = "organization_id")})
    private Set<Organization> organizations = new HashSet<Organization>();
    @OneToMany(
            mappedBy = "superp",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Sighting> sightings = new ArrayList<Sighting>();
    
    public Super addSighting(Sighting sit){
        sightings.add(sit);
        sit.setSuperp(this);
        return this;
    }
    
    public Super removeSighting(Sighting sit){
        sightings.remove(sit);
        sit.setSuperp(null);
        return this;
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

    public Boolean getHero() {
        return hero;
    }

    public void setHero(Boolean hero) {
        this.hero = hero;
    }

    public Set<Power> getPowers() {
        return powers;
    }

    public void setPowers(Set<Power> powers) {
        this.powers = powers;
    }

    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }

    public List<Sighting> getSightings() {
        return sightings;
    }

    public void setSightings(List<Sighting> sightings) {
        this.sightings = sightings;
    }

    public  String  getJSON()
    {
        Gson gson = new Gson();
       return gson.toJson(this);
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    
    @Override
    public String toString() {
        return "Super{" + "id=" + id + ", name=" + name + ", descr=" + descr + ", hero=" + hero + '}';
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.id;
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.descr);
        hash = 73 * hash + Objects.hashCode(this.hero);
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
        final Super other = (Super) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.descr, other.descr)) {
            return false;
        }
        if (!Objects.equals(this.hero, other.hero)) {
            return false;
        }
        return true;
    }
    
    
}
