package tech.ippon.chatbotdemo.web.rest;

import com.codahale.metrics.annotation.Timed;
import tech.ippon.chatbotdemo.domain.Claim;
import tech.ippon.chatbotdemo.repository.ClaimRepository;
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

import tech.ippon.chatbotdemo.security.SecurityUtils;

/**
 * REST controller for managing Claim.
 */
@RestController
@RequestMapping("/api")
public class ClaimResource {

    private final Logger log = LoggerFactory.getLogger(ClaimResource.class);

    private static final String ENTITY_NAME = "claim";

    private final ClaimRepository claimRepository;

    //private final ClaimService claimService;

    public ClaimResource(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
        // this.claimService = claimService;
    }

    /**
     * POST  /claims : Create a new claim.
     *
     * @param claim the claim to create
     * @return the ResponseEntity with status 201 (Created) and with body the new claim, or with status 400 (Bad Request) if the claim has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/claims")
    @Timed
    public ResponseEntity<Claim> createClaim(@RequestBody Claim claim) throws URISyntaxException {
        log.debug("REST request to save Claim : {}", claim);
        if (claim.getId() != null) {
            throw new BadRequestAlertException("A new claim cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Claim result = claimRepository.save(claim);
        return ResponseEntity.created(new URI("/api/claims/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /claims : Updates an existing claim.
     *
     * @param claim the claim to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated claim,
     * or with status 400 (Bad Request) if the claim is not valid,
     * or with status 500 (Internal Server Error) if the claim couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/claims")
    @Timed
    public ResponseEntity<Claim> updateClaim(@RequestBody Claim claim) throws URISyntaxException {
        log.debug("REST request to update Claim : {}", claim);
        if (claim.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Claim result = claimRepository.save(claim);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, claim.getId().toString()))
            .body(result);
    }

     /**
     * GET  /claims : get all the claims.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of claims in body
     */
    @GetMapping("/claims")
    @Timed
    public List<Claim> getAllClaims() {
        log.debug("REST request to get all Claims");
        return claimRepository.findAll();
    }


   /**
     * GET  /claims : get all the claims.
     *
     * @param id the id of the vehicle to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of claims in body
     */
    @GetMapping("/claims/byVehicle/{id}")
    @Timed
    public List<Claim> getAllClaimsByCarId(@PathVariable Long id) {
        log.debug("REST request to get all Claims with Drivers' id: {}", id);
        return claimRepository.findByVehicleId(id);
    }

    /**
     * GET  /claims/:id : get the "id" claim.
     *
     * @param id the id of the claim to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the claim, or with status 404 (Not Found)
     */
    @GetMapping("/claims/{id}")
    @Timed
    public ResponseEntity<Claim> getClaim(@PathVariable Long id) {
        log.debug("REST request to get Claim : {}", id);
        Optional<Claim> claim = claimRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(claim);
    }

    /**
     * DELETE  /claims/:id : delete the "id" claim.
     *
     * @param id the id of the claim to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/claims/{id}")
    @Timed
    public ResponseEntity<Void> deleteClaim(@PathVariable Long id) {
        log.debug("REST request to delete Claim : {}", id);

        claimRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
