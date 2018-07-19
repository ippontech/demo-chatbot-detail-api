package tech.ippon.chatbotdemo.repository;

import tech.ippon.chatbotdemo.domain.Claim;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data  repository for the Claim entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    @Query("select claim from Claim claim where claim.vehicle.id = :id")
    List<Claim> findByVehicleId(@Param("id") Long id);

}
