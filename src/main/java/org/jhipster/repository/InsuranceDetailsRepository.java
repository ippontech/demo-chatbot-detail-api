package org.jhipster.repository;

import org.jhipster.domain.InsuranceDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InsuranceDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsuranceDetailsRepository extends JpaRepository<InsuranceDetails, Long> {

}
