/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cec.superhero.repositories;

import com.cec.superhero.models.Sighting;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author etdeh
 */
@Repository
public interface SightingRepository extends JpaRepository<Sighting, Integer>{
    List<Sighting> findByLocationId(int id);
    //@Query("select s from sightings s where s.super_id = ?1")
    //List<Sighting> findBySuperId(int id);
    List<Sighting> findByDate(LocalDateTime date);
}
