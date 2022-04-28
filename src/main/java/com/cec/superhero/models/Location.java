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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author etdeh
 */
@Entity
@Table (name="locations")
public class Location {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @NotBlank(message = "Location needs a name")
    @Column(nullable = false)
    private String name;
    @Column
    private String descr;
    @NotBlank(message = "Location needs a description")
    @Column(nullable = false)
    private String address;
    //@NotBlank(message = "Latitude required.")
    @Column(nullable = false)
    private float latitude;
    //@NotBlank(message = "Longitude required")
    @Column(nullable = false)
    private float longitude;
    @OneToMany(
        mappedBy = "location",
            cascade = CascadeType.ALL,
            orphanRemoval = true    
    )
    private List<Sighting> sightings = new ArrayList<Sighting>();
    
    public Location addSighting(Sighting sit){
        sightings.add(sit);
        sit.setLocation(this);
        return this;
    }
    
    public Location removeSighting(Sighting sit){
        sightings.remove(sit);
        sit.setLocation(null);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
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

    
    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", name=" + name + ", descr=" + descr + ", address=" + address + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.descr);
        hash = 41 * hash + Objects.hashCode(this.address);
        hash = 41 * hash + Float.floatToIntBits(this.latitude);
        hash = 41 * hash + Float.floatToIntBits(this.longitude);
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
        final Location other = (Location) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Float.floatToIntBits(this.latitude) != Float.floatToIntBits(other.latitude)) {
            return false;
        }
        if (Float.floatToIntBits(this.longitude) != Float.floatToIntBits(other.longitude)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.descr, other.descr)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }
    
    
}
