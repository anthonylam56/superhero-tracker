/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cec.superhero.models;

import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author etdeh
 */
@Entity
@Table (name="sightings")
public class Sighting {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    //@NotBlank(message = "Please select a date")
    @Column(nullable = false)
    private LocalDateTime date;
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "super_id")
    private Super superp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Super getSuperp() {
        return superp;
    }

    public void setSuperp(Super superp) {
        this.superp = superp;
    }
    
    public  String  getJSON()
    {
        Gson gson = new Gson();
       return gson.toJson(this);
    }


    @Override
    public String toString() {
        return "Sighting{" + "id=" + id + ", date=" + date + ", location=" + location + ", superp=" + superp + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.date);
        hash = 67 * hash + Objects.hashCode(this.location);
        hash = 67 * hash + Objects.hashCode(this.superp);
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
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.superp, other.superp)) {
            return false;
        }
        return true;
    }

    
}
