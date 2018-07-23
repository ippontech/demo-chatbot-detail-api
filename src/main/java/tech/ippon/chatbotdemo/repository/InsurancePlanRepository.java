package tech.ippon.chatbotdemo.repository;

import tech.ippon.chatbotdemo.domain.InsurancePlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InsurancePlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsurancePlanRepository extends JpaRepository<InsurancePlan, Long>, JpaSpecificationExecutor<InsurancePlan> {

}
