package tech.ippon.chatbotdemo.web.rest;

import com.codahale.metrics.annotation.Timed;
import tech.ippon.chatbotdemo.domain.InsurancePlan;
import tech.ippon.chatbotdemo.repository.InsurancePlanRepository;
import tech.ippon.chatbotdemo.web.rest.errors.BadRequestAlertException;
import tech.ippon.chatbotdemo.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InsurancePlan.
 */
@RestController
@RequestMapping("/api")
public class InsurancePlanResource {

    private final Logger log = LoggerFactory.getLogger(InsurancePlanResource.class);

    private static final String ENTITY_NAME = "insurancePlan";

    private final InsurancePlanRepository insurancePlanRepository;

    public InsurancePlanResource(InsurancePlanRepository insurancePlanRepository) {
        this.insurancePlanRepository = insurancePlanRepository;
    }

    /**
     * POST  /insurance-plans : Create a new insurancePlan.
     *
     * @param insurancePlan the insurancePlan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insurancePlan, or with status 400 (Bad Request) if the insurancePlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insurance-plans")
    @Timed
    public ResponseEntity<InsurancePlan> createInsurancePlan(@RequestBody InsurancePlan insurancePlan) throws URISyntaxException {
        log.debug("REST request to save InsurancePlan : {}", insurancePlan);
        if (insurancePlan.getId() != null) {
            throw new BadRequestAlertException("A new insurancePlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsurancePlan result = insurancePlanRepository.save(insurancePlan);
        return ResponseEntity.created(new URI("/api/insurance-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insurance-plans : Updates an existing insurancePlan.
     *
     * @param insurancePlan the insurancePlan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insurancePlan,
     * or with status 400 (Bad Request) if the insurancePlan is not valid,
     * or with status 500 (Internal Server Error) if the insurancePlan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insurance-plans")
    @Timed
    public ResponseEntity<InsurancePlan> updateInsurancePlan(@RequestBody InsurancePlan insurancePlan) throws URISyntaxException {
        log.debug("REST request to update InsurancePlan : {}", insurancePlan);
        if (insurancePlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InsurancePlan result = insurancePlanRepository.save(insurancePlan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, insurancePlan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insurance-plans : get all the insurancePlans.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of insurancePlans in body
     */
    @GetMapping("/insurance-plans")
    @Timed
    public List<InsurancePlan> getAllInsurancePlans() {
        log.debug("REST request to get all InsurancePlans");
        return insurancePlanRepository.findAll();
    }

    /**
     * GET  /insurance-plans/:id : get the "id" insurancePlan.
     *
     * @param id the id of the insurancePlan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insurancePlan, or with status 404 (Not Found)
     */
    @GetMapping("/insurance-plans/{id}")
    @Timed
    public ResponseEntity<InsurancePlan> getInsurancePlan(@PathVariable Long id) {
        log.debug("REST request to get InsurancePlan : {}", id);
        Optional<InsurancePlan> insurancePlan = insurancePlanRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(insurancePlan);
    }

    /**
     * DELETE  /insurance-plans/:id : delete the "id" insurancePlan.
     *
     * @param id the id of the insurancePlan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insurance-plans/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsurancePlan(@PathVariable Long id) {
        log.debug("REST request to delete InsurancePlan : {}", id);

        insurancePlanRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
