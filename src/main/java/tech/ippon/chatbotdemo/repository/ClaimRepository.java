package tech.ippon.chatbotdemo.repository;

import tech.ippon.chatbotdemo.domain.Claim;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Claim entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

}
