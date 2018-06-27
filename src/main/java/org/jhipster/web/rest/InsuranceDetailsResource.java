package org.jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.jhipster.domain.InsuranceDetails;

import org.jhipster.repository.InsuranceDetailsRepository;
import org.jhipster.web.rest.errors.BadRequestAlertException;
import org.jhipster.web.rest.util.HeaderUtil;
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
 * REST controller for managing InsuranceDetails.
 */
@RestController
@RequestMapping("/api")
public class InsuranceDetailsResource {

    private final Logger log = LoggerFactory.getLogger(InsuranceDetailsResource.class);

    private static final String ENTITY_NAME = "insuranceDetails";

    private final InsuranceDetailsRepository insuranceDetailsRepository;

    public InsuranceDetailsResource(InsuranceDetailsRepository insuranceDetailsRepository) {
        this.insuranceDetailsRepository = insuranceDetailsRepository;
    }

    /**
     * POST  /insurance-details : Create a new insuranceDetails.
     *
     * @param insuranceDetails the insuranceDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insuranceDetails, or with status 400 (Bad Request) if the insuranceDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insurance-details")
    @Timed
    public ResponseEntity<InsuranceDetails> createInsuranceDetails(@RequestBody InsuranceDetails insuranceDetails) throws URISyntaxException {
        log.debug("REST request to save InsuranceDetails : {}", insuranceDetails);
        if (insuranceDetails.getId() != null) {
            throw new BadRequestAlertException("A new insuranceDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsuranceDetails result = insuranceDetailsRepository.save(insuranceDetails);
        return ResponseEntity.created(new URI("/api/insurance-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insurance-details : Updates an existing insuranceDetails.
     *
     * @param insuranceDetails the insuranceDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insuranceDetails,
     * or with status 400 (Bad Request) if the insuranceDetails is not valid,
     * or with status 500 (Internal Server Error) if the insuranceDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insurance-details")
    @Timed
    public ResponseEntity<InsuranceDetails> updateInsuranceDetails(@RequestBody InsuranceDetails insuranceDetails) throws URISyntaxException {
        log.debug("REST request to update InsuranceDetails : {}", insuranceDetails);
        if (insuranceDetails.getId() == null) {
            return createInsuranceDetails(insuranceDetails);
        }
        InsuranceDetails result = insuranceDetailsRepository.save(insuranceDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, insuranceDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insurance-details : get all the insuranceDetails.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of insuranceDetails in body
     */
    @GetMapping("/insurance-details")
    @Timed
    public List<InsuranceDetails> getAllInsuranceDetails() {
        log.debug("REST request to get all InsuranceDetails");
        return insuranceDetailsRepository.findAll();
        }

    /**
     * GET  /insurance-details/:id : get the "id" insuranceDetails.
     *
     * @param id the id of the insuranceDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insuranceDetails, or with status 404 (Not Found)
     */
    @GetMapping("/insurance-details/{id}")
    @Timed
    public ResponseEntity<InsuranceDetails> getInsuranceDetails(@PathVariable Long id) {
        log.debug("REST request to get InsuranceDetails : {}", id);
        InsuranceDetails insuranceDetails = insuranceDetailsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(insuranceDetails));
    }

    /**
     * DELETE  /insurance-details/:id : delete the "id" insuranceDetails.
     *
     * @param id the id of the insuranceDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insurance-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsuranceDetails(@PathVariable Long id) {
        log.debug("REST request to delete InsuranceDetails : {}", id);
        insuranceDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
