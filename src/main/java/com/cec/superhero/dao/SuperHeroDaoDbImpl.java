/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cec.superhero.dao;

import com.cec.superhero.models.Location;
import com.cec.superhero.models.Organization;
import com.cec.superhero.models.Power;
import com.cec.superhero.models.Sighting;
import com.cec.superhero.models.Super;
import com.cec.superhero.repositories.LocationRepository;
import com.cec.superhero.repositories.OrganizationRepository;
import com.cec.superhero.repositories.PowerRepository;
import com.cec.superhero.repositories.SightingRepository;
import com.cec.superhero.repositories.SuperRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author etdeh
 */
@Repository
public class SuperHeroDaoDbImpl implements SuperHeroDao{
    @Autowired
    LocationRepository location;  
    @Autowired
    SuperRepository supers;
    @Autowired
    SightingRepository sighting;
    @Autowired
    PowerRepository power;
    @Autowired
    OrganizationRepository organ;
    
    public enum Models {SUPERS,POWERS,ORGANIZATIONS,LOCATIONS,SIGHTINGS}
    
    @Override
    @Transactional
    public List<Super> getSupersSeenAtLoc(Location loc)throws DataIntegrityViolationException {
        loc = location.findById(loc.getId()).orElse(null);
        List<Sighting> sites = sighting.findByLocationId(loc.getId());
        return sites.stream().map(s -> s.getSuperp()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Location> getLocsWhereSuperSeen(Super sup) throws DataIntegrityViolationException{
        sup = supers.findById(sup.getId()).orElse(null);
        List<Sighting> sites = sup.getSightings();
        return sites.stream().map(s -> s.getLocation()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Sighting> getSightingsByDate(LocalDateTime date)throws DataIntegrityViolationException {
        return sighting.findByDate(date);
    }

    @Override
    @Transactional
    public List<Super> getMembersOfOrg(String name) throws DataIntegrityViolationException{
        List<Organization> orgs = organ.findByName(name);
        return orgs.stream().flatMap(o -> o.getSupers().stream()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Organization> getSuperBelongsTo(String name) throws DataIntegrityViolationException{
        List<Super> sups = supers.findByName(name);
        return sups.stream().flatMap(s -> s.getOrganizations().stream()).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Sighting reportNewSighting(Location loc, Super sup) throws DataIntegrityViolationException{
        loc = location.save(loc);
        sup = supers.save(sup);
        Sighting newSight = new Sighting();
        newSight.setLocation(loc);
        newSight.setSuperp(sup);
        return sighting.save(newSight);
    }

    /**
     * Cast return to desired class when called 
     * example Super sup = (Super)SuperHeroDao.findById(Models.SUPERS, int 3)
     * will cast return object as Super class on return
     * 
     * @param type
     * @param id
     * @return
     */
    @Override
    public Object findById(Models type, int id) throws DataIntegrityViolationException{
        switch(type){
            case SUPERS:
                return supers.findById(id).orElse(null);
            case POWERS:
                return power.findById(id).orElse(null);
            case ORGANIZATIONS:
                return organ.findById(id).orElse(null);
            case SIGHTINGS:
                return sighting.findById(id).orElse(null);
            case LOCATIONS:
                return location.findById(id).orElse(null);
            default:
                return null;
        }
    }
    
    /**
     * Cast return value if desired
     * example Super sup = (Super)SuperHeroDao.save(Models.SUPERS, sup)
     * 
     * @param type
     * @param ob
     * @return
     */
    @Override
    @Transactional
    public Object saveOrUpdate(Models type, Object ob)throws DataIntegrityViolationException {
        switch(type){
            case SUPERS:
                return supers.save((Super)ob);
            case POWERS:
                return power.save((Power)ob);
            case ORGANIZATIONS:
                return organ.save((Organization)ob);
            case SIGHTINGS:
                return sighting.save((Sighting)ob);
            case LOCATIONS:
                return location.save((Location)ob);
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public Boolean deleteById(Models type, int id) throws DataIntegrityViolationException{
        switch(type){
            case SUPERS:
                supers.deleteById(id);
                break;
            case POWERS:
                Power pow = (Power)this.findById(Models.POWERS, id);
                for(Super sup : pow.getSupers()){
                    sup.getPowers().remove(pow);
                }
                power.deleteById(id);
                break;
            case ORGANIZATIONS:
                Organization org = (Organization)this.findById(Models.ORGANIZATIONS, id);
                for(Super sup : org.getSupers()){
                    sup.getOrganizations().remove(org);
                }
                organ.deleteById(id);
                break;
            case SIGHTINGS:
                sighting.deleteById(id);
                break;
            case LOCATIONS:
                location.deleteById(id);
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public Boolean existsById(Models type, int id) throws DataIntegrityViolationException{
        switch(type){
            case SUPERS:
                return supers.existsById(id);
            case POWERS:
                return power.existsById(id);
            case ORGANIZATIONS:
                return organ.existsById(id);
            case SIGHTINGS:
                return sighting.existsById(id);
            case LOCATIONS:
                return location.existsById(id);
            default:
                return false;
        }
    }

    
    @Override
    public long count(Models type) throws DataIntegrityViolationException{
        switch(type){
            case SUPERS:
                return supers.count();
            case POWERS:
                return power.count();
            case ORGANIZATIONS:
                return organ.count();
            case SIGHTINGS:
                return sighting.count();
            case LOCATIONS:
                return location.count();
            default:
                return 0;
        }
    }

    @Override
    public List<Location> findAllLocs() throws DataIntegrityViolationException{
        return location.findAll();
    }

    @Override
    public List<Organization> findAllOrgs() throws DataIntegrityViolationException{
        return organ.findAll();
    }

    @Override
    public List<Power> findAllPow()throws DataIntegrityViolationException {
        return power.findAll();
    }

    @Override
    public List<Sighting> findAllSight()throws DataIntegrityViolationException {
        return sighting.findAll();
    }

    @Override
    public List<Super> findAllSups()throws DataIntegrityViolationException {
        return supers.findAll();
    }
    
    
}
