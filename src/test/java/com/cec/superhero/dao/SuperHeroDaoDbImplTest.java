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
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.hibernate.Session;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author etdeh
 */

@SpringBootTest
public class SuperHeroDaoDbImplTest {
    
    @Autowired
    SuperHeroDao dao;
    
    @Autowired
    public SuperHeroDaoDbImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Super> sups = dao.findAllSups();
        for(Super sup:sups){dao.deleteById(Models.SUPERS, sup.getId());}
        List<Sighting> sts = dao.findAllSight();
        for(Sighting st:sts){dao.deleteById(Models.SIGHTINGS, st.getId());}
        List<Power> pows = dao.findAllPow();
        for(Power pow:pows){dao.deleteById(Models.POWERS, pow.getId());}
        List<Organization> orgs = dao.findAllOrgs();
        for(Organization org:orgs){dao.deleteById(Models.ORGANIZATIONS, org.getId());}
        List<Location> locs = dao.findAllLocs();
        for(Location loc:locs){dao.deleteById(Models.LOCATIONS, loc.getId());}
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetSupersSeenAtLoc() {
        Location lc = new Location();
        lc.setAddress("Mesa, AZ");
        lc.setDescr("Nice area");
        lc.setName("The Groves");
        lc.setLatitude((float)33.432102);
        lc.setLongitude((float)-111.737565);
        lc = (Location)dao.saveOrUpdate(Models.LOCATIONS, lc);
        
        Super st = new Super();
        st.setName("Swarm");
        st.setDescr("One like many");
        st.setHero(Boolean.TRUE);
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        
        Super st1 = new Super();
        st1.setName("Swarm");
        st1.setDescr("One like many");
        st1.setHero(Boolean.TRUE);
        st1 = (Super)dao.saveOrUpdate(Models.SUPERS, st1);
        
        Sighting stg = new Sighting();
        stg.setLocation(lc);
        stg.setSuperp(st);
        stg = (Sighting)dao.saveOrUpdate(Models.SIGHTINGS, stg);
        
        Sighting stg1 = new Sighting();
        stg1.setLocation(lc);
        stg1.setSuperp(st1);
        stg1 = (Sighting)dao.saveOrUpdate(Models.SIGHTINGS, stg1);
        
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        
        List<Super> sps = dao.getSupersSeenAtLoc(lc);
        assertEquals(2,sps.size(),"size should be two");
        
    }

    @Test
    public void testGetLocsWhereSuperSeen() {
        //make test objects
        Super st = new Super();
        st.setName("Swarm");
        st.setDescr("One like many");
        st.setHero(Boolean.TRUE);
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        
        Location lc = new Location();
        lc.setAddress("Mesa, AZ");
        lc.setDescr("Nice area");
        lc.setName("The Groves");
        lc.setLatitude((float)33.432102);
        lc.setLongitude((float)-111.737565);
        lc = (Location)dao.saveOrUpdate(Models.LOCATIONS, lc);
        
        Location lc1 = new Location();
        lc1.setAddress("Mesa, AZ");
        lc1.setDescr("Nice area");
        lc1.setName("The Groves");
        lc1.setLatitude((float)33.432102);
        lc1.setLongitude((float)-111.737565);
        lc1 = (Location)dao.saveOrUpdate(Models.LOCATIONS, lc);
        
        Sighting stg = new Sighting();
        stg.setLocation(lc);
        stg.setSuperp(st);
        stg = (Sighting)dao.saveOrUpdate(Models.SIGHTINGS, stg);
        
        Sighting stg1 = new Sighting();
        stg1.setLocation(lc1);
        stg1.setSuperp(st);
        stg1 = (Sighting)dao.saveOrUpdate(Models.SIGHTINGS, stg1);
        
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        
        List<Location> locs = dao.getLocsWhereSuperSeen(st);
        assertEquals(2,locs.size(),"should be two");
    }

    @Test
    public void testGetSightingsByDate() {
        Sighting stg = new Sighting();
        stg.setDate(LocalDateTime.of(2022, Month.MARCH, 24, 12, 30));
        Sighting stg1 = new Sighting();
        stg1.setDate(LocalDateTime.of(2022, Month.MARCH, 24, 12, 30));
        Sighting stg2 = new Sighting();
        stg2.setDate(LocalDateTime.of(2022, Month.MARCH, 24, 22, 30));
        stg = (Sighting)dao.saveOrUpdate(Models.SIGHTINGS, stg);
        stg1 = (Sighting)dao.saveOrUpdate(Models.SIGHTINGS, stg1);
        stg2 = (Sighting)dao.saveOrUpdate(Models.SIGHTINGS, stg2);
        List<Sighting> stgs = dao.getSightingsByDate(LocalDateTime.of(2022, Month.MARCH, 24, 12, 30));
        assertEquals(2,stgs.size());
        assertTrue(stgs.contains(stg),"should contain stg");
        assertTrue(stgs.contains(stg1),"should contain stg1");
        assertFalse(stgs.contains(stg2),"should not contain stg2");
        List<Sighting> stgs1 = dao.getSightingsByDate(LocalDateTime.of(2022, Month.MARCH, 24, 22, 30));
        assertEquals(1,stgs1.size());
        assertTrue(stgs1.contains(stg2),"should contain stg2");
    }

    @Test
    public void testGetMembersOfOrg() {
        //make test objects
        Super st = new Super();
        st.setName("Swarm");
        st.setDescr("One like many");
        st.setHero(Boolean.TRUE);
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        
        Super st1 = new Super();
        st1.setName("Swarm");
        st1.setDescr("One like many");
        st1.setHero(Boolean.TRUE);
        st1 = (Super)dao.saveOrUpdate(Models.SUPERS, st1);
        
        Organization org = new Organization();
        org.setName("The Avengers");
        org.setDescr("It sounds dramatic");
        org.setAddress("Avengers Tower");
        org.addSuper(st1);
        org.addSuper(st);
        org = (Organization)dao.saveOrUpdate(Models.ORGANIZATIONS, org);
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        st1 = (Super)dao.saveOrUpdate(Models.SUPERS, st1);
        
        List<Super> sp = dao.getMembersOfOrg("The Avengers");
        assertEquals(2,sp.size(),"Should be two");
        assertTrue(sp.contains(st),"Should have org");
        assertTrue(sp.contains(st1),"Should have org2");
    }

    @Test
    public void testGetSuperBelongsTo() {
        //make test objects
        Organization org = new Organization();
        org.setName("The Avengers");
        org.setDescr("It sounds dramatic");
        org.setAddress("Avengers Tower");
        //org.addSuper(st);
        org = (Organization)dao.saveOrUpdate(Models.ORGANIZATIONS, org);
        
        Organization org2 = new Organization();
        org2.setName("The Avengers");
        org2.setDescr("It sounds dramatic");
        org2.setAddress("Avengers Tower");
        //org.addSuper(st);
        org2 = (Organization)dao.saveOrUpdate(Models.ORGANIZATIONS, org2);
        
        Super st = new Super();
        st.setName("Swarm");
        st.setDescr("One like many");
        st.setHero(Boolean.TRUE);
        st.getOrganizations().add(org);
        st.getOrganizations().add(org2);
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        
        List<Organization> orgs = dao.getSuperBelongsTo("Swarm");
        assertEquals(2,orgs.size(),"Should be two");
        assertTrue(orgs.contains(org),"Should have org");
        assertTrue(orgs.contains(org2),"Should have org2");
    }

    @Test
    public void testReportNewSighting() {
        Super st = new Super();
        st.setName("Swarm");
        st.setDescr("One like many");
        st.setHero(Boolean.TRUE);
        
        Location lc = new Location();
        lc.setAddress("Mesa, AZ");
        lc.setDescr("Nice area");
        lc.setName("The Groves");
        lc.setLatitude((float)33.432102);
        lc.setLongitude((float)-111.737565);
        
        Sighting stg = dao.reportNewSighting(lc, st);
        assertTrue(dao.existsById(Models.SIGHTINGS, stg.getId()),"Should exist");
        assertTrue(dao.existsById(Models.LOCATIONS, lc.getId()),"Should exist");
        assertTrue(dao.existsById(Models.SUPERS, st.getId()),"Should exist");
        assertEquals(st.getId(),stg.getSuperp().getId());
        assertEquals(lc.getId(),stg.getLocation().getId());
    }

    @Test
    public void testFindById() {
        //make test objects
        Super st = new Super();
        st.setName("Swarm");
        st.setDescr("One like many");
        st.setHero(Boolean.TRUE);
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        
        Location lc = new Location();
        lc.setAddress("Mesa, AZ");
        lc.setDescr("Nice area");
        lc.setName("The Groves");
        lc.setLatitude((float)33.432102);
        lc.setLongitude((float)-111.737565);
        lc = (Location)dao.saveOrUpdate(Models.LOCATIONS, lc);
        
        Power pow = new Power();
        pow.setName("Immeasurable Speed");
        pow.setDescr("Too fast to see");
        pow = (Power)dao.saveOrUpdate(Models.POWERS, pow);
        
        Organization org = new Organization();
        org.setName("The Avengers");
        org.setDescr("It sounds dramatic");
        org.setAddress("Avengers Tower");
        org = (Organization)dao.saveOrUpdate(Models.ORGANIZATIONS, org);
        
        Sighting stg = new Sighting();
        stg.setLocation(lc);
        stg.setSuperp(st);
        stg = (Sighting)dao.saveOrUpdate(Models.SIGHTINGS, stg);
        
        pow.addSuper(st);
        org.addSuper(st);
        //insert into database
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        assertEquals(st,(Super)dao.findById(Models.SUPERS, st.getId()),"should be equal");
        assertEquals(lc,(Location)dao.findById(Models.LOCATIONS, lc.getId()),"should be equal");
        assertEquals(pow,(Power)dao.findById(Models.POWERS, pow.getId()),"should not equal");
        assertEquals(org,(Organization)dao.findById(Models.ORGANIZATIONS, org.getId()),"should be equal");
        assertEquals(stg,(Sighting)dao.findById(Models.SIGHTINGS, stg.getId()),"should be equal");
    }

    @Test
    public void testSaveOrUpdate() {
        //make test objects
        Super st = new Super();
        st.setName("Swarm");
        st.setDescr("One like many");
        st.setHero(Boolean.TRUE);
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        
        Location lc = new Location();
        lc.setAddress("Mesa, AZ");
        lc.setDescr("Nice area");
        lc.setName("The Groves");
        lc.setLatitude((float)33.432102);
        lc.setLongitude((float)-111.737565);
        lc = (Location)dao.saveOrUpdate(Models.LOCATIONS, lc);
        
        Power pow = new Power();
        pow.setName("Immeasurable Speed");
        pow.setDescr("Too fast to see");
        pow = (Power)dao.saveOrUpdate(Models.POWERS, pow);
        
        Organization org = new Organization();
        org.setName("The Avengers");
        org.setDescr("It sounds dramatic");
        org.setAddress("Avengers Tower");
        org = (Organization)dao.saveOrUpdate(Models.ORGANIZATIONS, org);
        
        Sighting stg = new Sighting();
        stg.setLocation(lc);
        stg.setSuperp(st);
        stg = (Sighting)dao.saveOrUpdate(Models.SIGHTINGS, stg);
        
        pow.addSuper(st);
        org.addSuper(st);
        //insert into database
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        //tests
        assertTrue(dao.existsById(Models.SUPERS, st.getId()),"Should exist");
        assertTrue(dao.existsById(Models.LOCATIONS, lc.getId()),"Should exist");
        assertTrue(dao.existsById(Models.ORGANIZATIONS, org.getId()),"Should exist");
        assertTrue(dao.existsById(Models.POWERS, pow.getId()),"Should exist");
        assertTrue(dao.existsById(Models.SIGHTINGS, stg.getId()),"Should exist");
        //make changes
        st.setDescr("dull");
        assertNotEquals(st,(Super)dao.findById(Models.SUPERS, st.getId()),"should not be equal");
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        assertEquals(st,(Super)dao.findById(Models.SUPERS, st.getId()),"should be equal");
        lc.setAddress("dull");
        assertNotEquals(lc,(Location)dao.findById(Models.LOCATIONS, lc.getId()),"should not be equal");
        lc = (Location)dao.saveOrUpdate(Models.LOCATIONS, lc);
        assertEquals(lc,(Location)dao.findById(Models.LOCATIONS, lc.getId()),"should be equal");
        pow.setDescr("dull");
        assertNotEquals(pow,(Power)dao.findById(Models.POWERS, pow.getId()),"should not be equal");
        pow = (Power)dao.saveOrUpdate(Models.POWERS, pow);
        assertEquals(pow,(Power)dao.findById(Models.POWERS, pow.getId()),"should not equal");
        org.setAddress("dull");
        assertNotEquals(org,(Organization)dao.findById(Models.ORGANIZATIONS, org.getId()),"should not be equal");
        org = (Organization)dao.saveOrUpdate(Models.ORGANIZATIONS, org);
        assertEquals(org,(Organization)dao.findById(Models.ORGANIZATIONS, org.getId()),"should be equal");
        stg.setDate(LocalDateTime.now().plusDays(3).truncatedTo(ChronoUnit.SECONDS));
        assertNotEquals(stg,(Sighting)dao.findById(Models.SIGHTINGS, stg.getId()),"should not be equal");
        stg = (Sighting)dao.saveOrUpdate(Models.SIGHTINGS, stg);
        Sighting newsg = (Sighting)dao.findById(Models.SIGHTINGS, stg.getId());
        assertEquals(stg,newsg,"should be equal");
        /*
        assertNotEquals(st,(Super)dao.findById(Models.SUPERS, st.getId()),"should not be equal");
        assertNotEquals(lc,(Location)dao.findById(Models.LOCATIONS, lc.getId()),"should not be equal");
        assertNotEquals(pow,(Power)dao.findById(Models.POWERS, pow.getId()),"should not be equal");
        assertNotEquals(org,(Organization)dao.findById(Models.ORGANIZATIONS, org.getId()),"should not be equal");
        assertNotEquals(stg,(Sighting)dao.findById(Models.SIGHTINGS, stg.getId()),"should not be equal");
        //update
        stg = (Sighting)dao.saveOrUpdate(Models.SIGHTINGS, stg);
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        lc = (Location)dao.saveOrUpdate(Models.LOCATIONS, lc);
        pow = (Power)dao.saveOrUpdate(Models.POWERS, pow);
        org = (Organization)dao.saveOrUpdate(Models.ORGANIZATIONS, org);
        //test again
        assertEquals(st,(Super)dao.findById(Models.SUPERS, st.getId()),"should be equal");
        assertEquals(lc,(Location)dao.findById(Models.LOCATIONS, lc.getId()),"should be equal");
        assertEquals(pow,(Power)dao.findById(Models.POWERS, pow.getId()),"should not equal");
        assertEquals(org,(Organization)dao.findById(Models.ORGANIZATIONS, org.getId()),"should be equal");
        assertEquals(stg,(Sighting)dao.findById(Models.SIGHTINGS, stg.getId()),"should be equal");
        */
    }

    @Test
    public void testExistsById() {
        //make test objects
        Super st = new Super();
        st.setName("Swarm");
        st.setDescr("One like many");
        st.setHero(Boolean.TRUE);
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        
        Location lc = new Location();
        lc.setAddress("Mesa, AZ");
        lc.setDescr("Nice area");
        lc.setName("The Groves");
        lc.setLatitude((float)33.432102);
        lc.setLongitude((float)-111.737565);
        lc = (Location)dao.saveOrUpdate(Models.LOCATIONS, lc);
        
        Power pow = new Power();
        pow.setName("Immeasurable Speed");
        pow.setDescr("Too fast to see");
        pow = (Power)dao.saveOrUpdate(Models.POWERS, pow);
        
        Organization org = new Organization();
        org.setName("The Avengers");
        org.setDescr("It sounds dramatic");
        org.setAddress("Avengers Tower");
        org = (Organization)dao.saveOrUpdate(Models.ORGANIZATIONS, org);
        
        Sighting stg = new Sighting();
        stg.setLocation(lc);
        stg.setSuperp(st);
        stg = (Sighting)dao.saveOrUpdate(Models.SIGHTINGS, stg);
        
        pow.addSuper(st);
        org.addSuper(st);
        //insert into database
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        //test results
        assertTrue(dao.existsById(Models.SUPERS, st.getId()),"Should exist");
        assertFalse(dao.existsById(Models.SUPERS, st.getId()+3),"Should not exist");
        assertTrue(dao.existsById(Models.LOCATIONS, lc.getId()),"Should exist");
        assertFalse(dao.existsById(Models.LOCATIONS, lc.getId()+3),"Should not exist");
        assertTrue(dao.existsById(Models.ORGANIZATIONS, org.getId()),"Should exist");
        assertFalse(dao.existsById(Models.ORGANIZATIONS, org.getId()+3),"Should not exist");
        assertTrue(dao.existsById(Models.POWERS, pow.getId()),"Should exist");
        assertFalse(dao.existsById(Models.POWERS, pow.getId()+3),"Should not exist");
        assertTrue(dao.existsById(Models.SIGHTINGS, stg.getId()),"Should exist");
        assertFalse(dao.existsById(Models.SIGHTINGS, stg.getId()+3),"Should not exist");
    }
    
    @Test
    public void testDeleteById() {
        //make test objects
        Super st = new Super();
        st.setName("Swarm");
        st.setDescr("One like many");
        st.setHero(Boolean.TRUE);
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        
        Location lc = new Location();
        lc.setAddress("Mesa, AZ");
        lc.setDescr("Nice area");
        lc.setName("The Groves");
        lc.setLatitude((float)33.432102);
        lc.setLongitude((float)-111.737565);
        lc = (Location)dao.saveOrUpdate(Models.LOCATIONS, lc);
        
        Power pow = new Power();
        pow.setName("Immeasurable Speed");
        pow.setDescr("Too fast to see");
        pow = (Power)dao.saveOrUpdate(Models.POWERS, pow);
        
        Organization org = new Organization();
        org.setName("The Avengers");
        org.setDescr("It sounds dramatic");
        org.setAddress("Avengers Tower");
        org = (Organization)dao.saveOrUpdate(Models.ORGANIZATIONS, org);
        
        Sighting stg = new Sighting();
        stg.setLocation(lc);
        stg.setSuperp(st);
        stg = (Sighting)dao.saveOrUpdate(Models.SIGHTINGS, stg);
        
        pow.addSuper(st);
        org.addSuper(st);
        //insert into database
        st = (Super)dao.saveOrUpdate(Models.SUPERS, st);
        //delete sighting
        boolean test = dao.deleteById(Models.SIGHTINGS, stg.getId());
        assertTrue(test,"should be true on success");
        assertFalse(dao.existsById(Models.SIGHTINGS, stg.getId()),"Should not exist");
        assertTrue(dao.existsById(Models.LOCATIONS, lc.getId()),"Location should not be deleted with sighting");
        stg = (Sighting)dao.saveOrUpdate(Models.SIGHTINGS, stg);
        assertTrue(dao.existsById(Models.SIGHTINGS, stg.getId()),"Should be back");
        //delete location
        assertTrue(dao.deleteById(Models.LOCATIONS,lc.getId()),"Should be true for success");
        assertFalse(dao.existsById(Models.LOCATIONS, lc.getId()),"Location should not exist anymore");
        assertFalse(dao.existsById(Models.SIGHTINGS, stg.getId()),"Sighting should not exist either");
        assertTrue(dao.deleteById(Models.SUPERS,st.getId()),"Should be true for success");
        assertTrue(dao.deleteById(Models.ORGANIZATIONS,org.getId()),"Should be true for success");
        assertTrue(dao.deleteById(Models.POWERS,pow.getId()),"Should be true for success");
        assertFalse(dao.existsById(Models.SUPERS, st.getId()),"Should not exist");
        assertFalse(dao.existsById(Models.ORGANIZATIONS, org.getId()),"Should not exist");
        assertFalse(dao.existsById(Models.POWERS, pow.getId()),"Should not exist");
    }

}
