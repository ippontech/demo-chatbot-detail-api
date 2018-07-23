package tech.ippon.chatbotdemo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import tech.ippon.chatbotdemo.domain.InsurancePlan;
import tech.ippon.chatbotdemo.domain.*; // for static metamodels
import tech.ippon.chatbotdemo.repository.InsurancePlanRepository;
import tech.ippon.chatbotdemo.service.dto.InsurancePlanCriteria;


/**
 * Service for executing complex queries for InsurancePlan entities in the database.
 * The main input is a {@link InsurancePlanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InsurancePlan} or a {@link Page} of {@link InsurancePlan} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InsurancePlanQueryService extends QueryService<InsurancePlan> {

    private final Logger log = LoggerFactory.getLogger(InsurancePlanQueryService.class);

    private final InsurancePlanRepository insurancePlanRepository;

    public InsurancePlanQueryService(InsurancePlanRepository insurancePlanRepository) {
        this.insurancePlanRepository = insurancePlanRepository;
    }

    /**
     * Return a {@link List} of {@link InsurancePlan} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InsurancePlan> findByCriteria(InsurancePlanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InsurancePlan> specification = createSpecification(criteria);
        return insurancePlanRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link InsurancePlan} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InsurancePlan> findByCriteria(InsurancePlanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InsurancePlan> specification = createSpecification(criteria);
        return insurancePlanRepository.findAll(specification, page);
    }

    /**
     * Function to convert InsurancePlanCriteria to a {@link Specification}
     */
    private Specification<InsurancePlan> createSpecification(InsurancePlanCriteria criteria) {
        Specification<InsurancePlan> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), InsurancePlan_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), InsurancePlan_.name));
            }
            if (criteria.getSummary() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSummary(), InsurancePlan_.summary));
            }
            if (criteria.getYearlyPremium() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYearlyPremium(), InsurancePlan_.yearlyPremium));
            }
            if (criteria.getDeductable() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeductable(), InsurancePlan_.deductable));
            }
            if (criteria.getCoveragePerPerson() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoveragePerPerson(), InsurancePlan_.coveragePerPerson));
            }
            if (criteria.getCoveragePerAccident() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoveragePerAccident(), InsurancePlan_.coveragePerAccident));
            }
        }
        return specification;
    }

}
