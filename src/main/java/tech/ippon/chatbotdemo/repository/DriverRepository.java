package tech.ippon.chatbotdemo.repository;

import tech.ippon.chatbotdemo.domain.Driver;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data  repository for the Driver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query("select driver from Driver driver where driver.userLogin = :login")
    List<Driver> findByUserLogin(@Param("login") String login);
}