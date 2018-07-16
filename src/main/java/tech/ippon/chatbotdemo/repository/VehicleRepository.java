package tech.ippon.chatbotdemo.repository;

import tech.ippon.chatbotdemo.domain.Vehicle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data  repository for the Vehicle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query("select vehicle from Vehicle vehicle where vehicle.driver.userLogin = :login")
    List<Vehicle> findByDriverUserLogin(@Param("login") String login);

}
