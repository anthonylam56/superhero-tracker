/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cec.superhero.repositories;

import com.cec.superhero.models.Organization;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author etdeh
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer>{
    List<Organization> findByName(String name);
}
