package org.jhipster.repository;

import java.util.List;

import org.jhipster.domain.Driver;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Driver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    
    Driver findById(Long id);
    
    @Query("select driver from Driver driver where driver.userLogin = :login")
    List<Driver> findByUserLogin(@Param("login") String login);
}
