/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cec.superhero.dao;

import com.cec.superhero.dao.SuperHeroDaoDbImpl.Models;
import com.cec.superhero.models.Location;
import com.cec.superhero.models.Organization;
import com.cec.superhero.models.Power;
import com.cec.superhero.models.Sighting;
import com.cec.superhero.models.Super;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author etdeh
 */
public interface SuperHeroDao {
    List<Super> getSupersSeenAtLoc(Location loc)throws DataIntegrityViolationException;
    List<Location> getLocsWhereSuperSeen(Super sup)throws DataIntegrityViolationException;
    List<Sighting> getSightingsByDate(LocalDateTime date)throws DataIntegrityViolationException;
    List<Super> getMembersOfOrg(String name)throws DataIntegrityViolationException;
    List<Organization> getSuperBelongsTo(String name)throws DataIntegrityViolationException;
    Sighting reportNewSighting(Location loc, Super sup)throws DataIntegrityViolationException;
    //views objects
    Object findById(Models type, int id)throws DataIntegrityViolationException;
    //for creating or updating objects
    Object saveOrUpdate(Models type, Object ob)throws DataIntegrityViolationException;
    //for deleting objects
    Boolean deleteById(Models type, int id)throws DataIntegrityViolationException;
    //checks if object exists
    Boolean existsById(Models type, int id)throws DataIntegrityViolationException;
    //finds all objects of type
    List<Location> findAllLocs()throws DataIntegrityViolationException;
    List<Organization> findAllOrgs()throws DataIntegrityViolationException;
    List<Power> findAllPow()throws DataIntegrityViolationException;
    List<Sighting> findAllSight()throws DataIntegrityViolationException;
    List<Super> findAllSups()throws DataIntegrityViolationException;
    //gets object count
    long count(Models type)throws DataIntegrityViolationException;
}
