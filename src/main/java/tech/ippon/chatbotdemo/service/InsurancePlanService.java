package tech.ippon.chatbotdemo.service;

import tech.ippon.chatbotdemo.domain.InsurancePlan;
import tech.ippon.chatbotdemo.repository.InsurancePlanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing InsurancePlan.
 */
@Service
@Transactional
public class InsurancePlanService {

    private final Logger log = LoggerFactory.getLogger(InsurancePlanService.class);

    private final InsurancePlanRepository insurancePlanRepository;

    public InsurancePlanService(InsurancePlanRepository insurancePlanRepository) {
        this.insurancePlanRepository = insurancePlanRepository;
    }

    /**
     * Save a insurancePlan.
     *
     * @param insurancePlan the entity to save
     * @return the persisted entity
     */
    public InsurancePlan save(InsurancePlan insurancePlan) {
        log.debug("Request to save InsurancePlan : {}", insurancePlan);        return insurancePlanRepository.save(insurancePlan);
    }

    /**
     * Get all the insurancePlans.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<InsurancePlan> findAll() {
        log.debug("Request to get all InsurancePlans");
        return insurancePlanRepository.findAll();
    }


    /**
     * Get one insurancePlan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<InsurancePlan> findOne(Long id) {
        log.debug("Request to get InsurancePlan : {}", id);
        return insurancePlanRepository.findById(id);
    }

    /**
     * Delete the insurancePlan by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InsurancePlan : {}", id);
        insurancePlanRepository.deleteById(id);
    }
}
