/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cec.superhero.controller;

import com.cec.superhero.dao.SuperHeroDao;
import com.cec.superhero.dao.SuperHeroDaoDbImpl.Models;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.cec.superhero.models.Super;
import com.cec.superhero.models.Location;
import com.cec.superhero.models.Organization;
import com.cec.superhero.models.Power;
import com.cec.superhero.models.Sighting;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author etdeh
 */
@Controller
public class MainController {

    @Autowired
    SuperHeroDao dao;

    @GetMapping("index")
    public String homePage(Model model, HttpSession session) {
        String head = (false) ? model.getAttribute("test").toString():"Superhero Sightings";
        List<Sighting> sightings = dao.findAllSight().stream().limit(10)
                .collect(Collectors.toList());
        System.out.println(session.getAttribute("test"));
        //Puts text in header
        model.addAttribute("headerText", head);
        //Sightings for the ticker tape
        model.addAttribute("sightings",sightings);

        return "index";
    }

    //--------------Hero Villain Pages --------------------

    @GetMapping("heroes")
    public String heroesPage(Model model){
        List<Super> supers = dao.findAllSups();
        List<Power> powers = dao.findAllPow();
        List<Organization> organs = dao.findAllOrgs();
        List<Location> locs = dao.findAllLocs();
        //List<Organization> orgs = dao.findAllOrgs();
        model.addAttribute("powers", powers);
        model.addAttribute("organs", organs);
        model.addAttribute("supers",supers);
        //model.addAttribute("orgs",orgs);
        model.addAttribute("locs",locs);
        model.addAttribute("selects",new int[]{0,0});
        return "heroes";
    }

    @PostMapping("superlocations")
    public String superByLocal(Model model, HttpServletRequest request){
        int locId = Integer.parseInt(request.getParameter("locSelect"));
        Location floc = (Location)dao.findById(Models.LOCATIONS, locId);
        if(floc == null){
            return "redirect:/heroes";
        }
        List<Super> supers = dao.getSupersSeenAtLoc(floc);
        List<Power> powers = dao.findAllPow();
        List<Organization> organs = dao.findAllOrgs();
        List<Location> locs = dao.findAllLocs();
        model.addAttribute("powers", powers);
        model.addAttribute("organs", organs);
        model.addAttribute("supers",supers);
        model.addAttribute("locs",locs);
        model.addAttribute("selects",new int[]{0,locId});
        return "heroes";
    }

    @PostMapping("superorgs")
    public String superByOrg(Model model, HttpServletRequest request){
        int orgId = Integer.parseInt(request.getParameter("orgSelect"));
        Organization org = (Organization)dao.findById(Models.ORGANIZATIONS, orgId);
        if(org == null){
            return "redirect:/heroes";
        }
        List<Super> supers = org.getSupers();
        List<Power> powers = dao.findAllPow();
        List<Organization> organs = dao.findAllOrgs();
        List<Location> locs = dao.findAllLocs();
        model.addAttribute("powers", powers);
        model.addAttribute("organs", organs);
        model.addAttribute("supers",supers);
        model.addAttribute("locs",locs);
        model.addAttribute("selects",new int[]{orgId,0});
        return "heroes";
    }

    @GetMapping("deleteSuper")
    public String deleteSuper(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteById(Models.SUPERS, id);
        return "redirect:/heroes";
    }

    @GetMapping("editSuper")
    public String editSuper(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Super sup = (Super)dao.findById(Models.SUPERS, id);
        List<Power> pows = dao.findAllPow();
        List<Organization> orgs = dao.findAllOrgs();
        model.addAttribute("super", sup);
        model.addAttribute("pows", pows);
        model.addAttribute("orgs", orgs);
        model.addAttribute("headerText", "Editing "+sup.getName());
        return "editPage";
    }

    @PostMapping("editSuper")
    public String updateSuper(HttpServletRequest request,
                @RequestParam("image") MultipartFile multipartFile){
        int id = Integer.parseInt(request.getParameter("id"));
        Super sp = (Super)dao.findById(Models.SUPERS, id);
        List<Power> pows = dao.findAllPow();
        List<Organization> orgs = dao.findAllOrgs();
        sp.setName(request.getParameter("name"));
        sp.setDescr(request.getParameter("descr"));
        boolean hr = (request.getParameter("hero").equals("hero")) ? true:false;
        sp.setHero(hr);
        sp.getPowers().clear();
        for(Power pow : pows){
            if(request.getParameter(pow.getName()) != null){
                sp.getPowers().add(pow);
            }
        }
        sp.getOrganizations().clear();
        for(Organization org : orgs){
            if(request.getParameter(org.getName()) != null){
                sp.getOrganizations().add(org);
            }
        }
        String fileName;
        if(multipartFile.isEmpty()){
            fileName = "";
        }else{
            fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            fileName = sp.getId()+sp.getName().replaceAll("[ ]", "") + fileName.substring(fileName.lastIndexOf("."));
        }
        if(!fileName.isBlank()){
            sp.setPhotos("/img/"+fileName);
        }
        if(!multipartFile.isEmpty() && !fileName.isBlank()){
            try{
                FileUploadUtil.saveFile("src/main/resources/static/img", fileName, multipartFile);
            }catch(IOException e){
                System.out.println(e);
            }
        }
        dao.saveOrUpdate(Models.SUPERS, sp);
        return "redirect:/heroes";
    }

    @PostMapping("heroes")
     public String supersForm(HttpServletRequest request,
             @RequestParam("image") MultipartFile multipartFile)throws IOException  {
        String name = request.getParameter("name");
        String descr = request.getParameter("descr");
        boolean hero = (request.getParameter("hero")=="true") ? true:false;
        String[] powers = request.getParameterValues("powers");
        String[] organs = request.getParameterValues("organs");

        //create hero
        Super newSup = new Super();
        newSup.setName(name);
        newSup.setDescr(descr);
        newSup.setHero(hero);

        for(String id : powers){
            newSup.getPowers().add((Power)dao.findById(Models.POWERS,
                    Integer.parseInt(id)));
        }
        for(String id : organs){
            newSup.getOrganizations().add((Organization)dao.findById(Models.ORGANIZATIONS,
                    Integer.parseInt(id)));
        }

        newSup = (Super)dao.saveOrUpdate(Models.SUPERS, newSup);
        String fileName;
        if(multipartFile.isEmpty()){
            fileName = "";
        }else{
            fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            fileName = newSup.getId()+name.replaceAll("[ ]", "") + fileName.substring(fileName.lastIndexOf("."));
        }
        if(!fileName.isBlank()){
            newSup.setPhotos("/img/"+fileName);
        }
        if(!multipartFile.isEmpty() && !fileName.isBlank()){
            try{
                FileUploadUtil.saveFile("src/main/resources/static/img", fileName, multipartFile);
            }catch(IOException e){
                System.out.println(e);
            }
        }
        dao.saveOrUpdate(Models.SUPERS, newSup);
        return "redirect:/heroes";
    }

    //-------------Super Powers Pages-------------------------------

    @GetMapping("superpowers")
    public String powersPage(Model model, HttpSession session){
        List<Power> powers = dao.findAllPow();
        model.addAttribute("powers",powers);
        session.setAttribute("test", "Hello World");
        return "superpowers";
    }

    @GetMapping("editPower")
    public String editPower(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Power pow = (Power)dao.findById(Models.POWERS, id);
        model.addAttribute("power", pow);
        model.addAttribute("headerText", "Editing "+pow.getName());
        return "editPowerPage";
    }

    @PostMapping("editPower")
    public String updateEditedPower(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        Power pow = (Power)dao.findById(Models.POWERS, id);
        pow.setName(request.getParameter("name"));
        pow.setDescr(request.getParameter("descr"));
        dao.saveOrUpdate(Models.POWERS, pow);
        return "redirect:/superpowers";
    }

    @GetMapping("deletePower")
    public String deletePower(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteById(Models.POWERS, id);
        return "redirect:/superpowers";
    }

    @PostMapping("superpowers")
     public String powersForm(Power pow, HttpServletRequest request) {

        dao.saveOrUpdate(Models.POWERS, pow);

        return "redirect:/superpowers";
    }

    //-----------Locations of sightings pages------------------

    @GetMapping("locations")
    public String locationsPage(Model model){
        List<Location> locs = dao.findAllLocs();
        List<Super> supers = dao.findAllSups();
        model.addAttribute("supers",supers);
        model.addAttribute("locs",locs);
        model.addAttribute("select",0);
        return "locations";
    }

    @PostMapping("localsupers")
    public String localBySuper(Model model, HttpServletRequest request){
        int supId = Integer.parseInt(request.getParameter("locSelect"));
        Super sup = (Super)dao.findById(Models.SUPERS, supId);
        if(sup == null){
            return "redirect:/locations";
        }
        List<Super> supers = dao.findAllSups();
        List<Location> locs = dao.getLocsWhereSuperSeen(sup);
        model.addAttribute("supers",supers);
        model.addAttribute("locs",locs);
        model.addAttribute("selects",supId);
        return "locations";
    }

    @GetMapping("editLocal")
    public String editLocal(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Location loc = (Location)dao.findById(Models.LOCATIONS, id);
        model.addAttribute("loc", loc);
        model.addAttribute("headerText", "Editing "+loc.getName());
        return "editLocalPage";
    }

    @PostMapping("editLocal")
    public String updateEditedLocal(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        Location loc = (Location)dao.findById(Models.LOCATIONS, id);
        loc.setName(request.getParameter("name"));
        loc.setDescr(request.getParameter("descr"));
        loc.setAddress(request.getParameter("address"));
        loc.setLatitude(Float.parseFloat(request.getParameter("lat")));
        loc.setLongitude(Float.parseFloat(request.getParameter("log")));
        dao.saveOrUpdate(Models.LOCATIONS, loc);
        return "redirect:/locations";
    }

    @GetMapping("deleteLocal")
    public String deleteLocal(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteById(Models.LOCATIONS, id);
        return "redirect:/locations";
    }

    @PostMapping("locations")
    public String locationsForm(HttpServletRequest request) {
        String name = request.getParameter("name");
        String descr = request.getParameter("descr");
        String address = request.getParameter("address");
        float lat = Float.parseFloat(request.getParameter("latitude"));
        float log = Float.parseFloat(request.getParameter("longitude"));
        Location nl = new Location();
        nl.setName(name);
        nl.setDescr(descr);
        nl.setAddress(address);
        nl.setLatitude(lat);
        nl.setLongitude(log);
        dao.saveOrUpdate(Models.LOCATIONS, nl);

        return "redirect:/locations";
    }

    //----------Super Hero/Villain Organization pages---------------

    @GetMapping("organizations")
    public String organizationsPage(Model model){
        List<Organization> orgs = dao.findAllOrgs();
        List<Super> supers = dao.findAllSups();
        model.addAttribute("supers",supers);
        model.addAttribute("orgs",orgs);
        model.addAttribute("select",0);
        return "organizations";
    }

    @PostMapping("organsupers")
    public String orgBySuper(Model model, HttpServletRequest request){
        int supId = Integer.parseInt(request.getParameter("supSelect"));
        Super sup = (Super)dao.findById(Models.SUPERS, supId);
        if(sup == null){
            return "redirect:/organizations";
        }
        List<Super> supers = dao.findAllSups();
        Set<Organization> orgs = sup.getOrganizations();
        model.addAttribute("supers",supers);
        model.addAttribute("orgs",orgs);
        model.addAttribute("selects",supId);
        return "organizations";
    }

    @GetMapping("editOrg")
    public String editOrg(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Organization org = (Organization)dao.findById(Models.ORGANIZATIONS, id);
        model.addAttribute("org", org);
        model.addAttribute("headerText", "Editing "+org.getName());
        return "editOrgPage";
    }

    @PostMapping("editOrg")
    public String updateEditedOrg(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        Organization org = (Organization)dao.findById(Models.ORGANIZATIONS, id);
        org.setName(request.getParameter("name"));
        org.setDescr(request.getParameter("descr"));
        org.setAddress(request.getParameter("address"));
        dao.saveOrUpdate(Models.ORGANIZATIONS, org);
        return "redirect:/organizations";
    }

    @GetMapping("deleteOrg")
    public String deleteOrg(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteById(Models.ORGANIZATIONS, id);
        return "redirect:/organizations";
    }

    @PostMapping("organizations")
     public String organizationsForm(Organization organ, HttpServletRequest request) {

        dao.saveOrUpdate(Models.ORGANIZATIONS, organ);

        return "redirect:/organizations";
    }

    //--------------Super Sightings Pages---------------------------

    @GetMapping("sightings")
    public String sightingsPage(Model model){
        List<Sighting> sightings = dao.findAllSight();
        List<Super> supers = dao.findAllSups();
        List<Location> locs = dao.findAllLocs();
        List<Sighting> times = dao.findAllSight();
        model.addAttribute("times", times);
        model.addAttribute("supers", supers);
        model.addAttribute("locs", locs);
        model.addAttribute("sightings", sightings);
        model.addAttribute("select", 0);
        return "sightings";
    }

    @PostMapping("sitebydate")
    public String siteByDate(Model model, HttpServletRequest request){
        int siteId = Integer.parseInt(request.getParameter("locSelect"));
        Sighting fsite = (Sighting)dao.findById(Models.SIGHTINGS, siteId);
        if(fsite == null){
            return "redirect:/sightings";
        }
        List<Sighting> sightings = dao.getSightingsByDate(fsite.getDate());
        List<Super> supers = dao.findAllSups();
        List<Location> locs = dao.findAllLocs();
        List<Sighting> times = dao.findAllSight();
        model.addAttribute("times", times);
        model.addAttribute("supers", supers);
        model.addAttribute("locs", locs);
        model.addAttribute("sightings", sightings);
        model.addAttribute("select", siteId);
        return "sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sg = (Sighting)dao.findById(Models.SIGHTINGS, id);
        List<Super> sups = dao.findAllSups();
        List<Location> locs = dao.findAllLocs();
        model.addAttribute("locs", locs);
        model.addAttribute("sups", sups);
        model.addAttribute("sg", sg);
        model.addAttribute("headerText", "Editing Sighting From "+sg.getDate());
        return "editSightingPage";
    }

    @PostMapping("editSighting")
    public String updateSighting(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sg = (Sighting)dao.findById(Models.SIGHTINGS, id);
        sg.setDate(LocalDateTime.parse(request.getParameter("date")));
        sg.setSuperp((Super)dao.findById(Models.SUPERS, Integer.parseInt(request.getParameter("seenSuper"))));
        sg.setLocation((Location)dao.findById(Models.LOCATIONS, Integer.parseInt(request.getParameter("local"))));
        dao.saveOrUpdate(Models.SIGHTINGS, sg);
        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteById(Models.SIGHTINGS, id);
        return "redirect:/sightings";
    }

    @PostMapping("sightings")
     public String sightingsForm(HttpServletRequest request) {
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
        Sighting sg = new Sighting();
        sg.setDate(date);
        sg.setLocation((Location)dao.findById(Models.LOCATIONS,
                Integer.parseInt(request.getParameter("locs"))));
        sg.setSuperp((Super)dao.findById(Models.SUPERS,
                Integer.parseInt(request.getParameter("supers"))));
        dao.saveOrUpdate(Models.SIGHTINGS, sg);
        return "redirect:/sightings";
     }

    @PostMapping("/api/locals")
    @ResponseBody
    public Map<String,String[]> getLocs(HttpServletRequest request){
        Map<String,String[]> result = new HashMap<>();
        for(Sighting s : dao.findAllSight()){
            String[] sa = {s.getDate().toString(),s.getSuperp().getName(),
            s.getLocation().getName(),String.valueOf(s.getLocation().getLatitude()),
            String.valueOf(s.getLocation().getLongitude())};
            result.put(String.valueOf(s.getId()), sa);
        }
        return result;
    }
}
