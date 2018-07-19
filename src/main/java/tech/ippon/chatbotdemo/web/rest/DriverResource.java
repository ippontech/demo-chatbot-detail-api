package tech.ippon.chatbotdemo.web.rest;

import com.codahale.metrics.annotation.Timed;
import tech.ippon.chatbotdemo.domain.Driver;
import tech.ippon.chatbotdemo.repository.DriverRepository;
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

import tech.ippon.chatbotdemo.security.AuthoritiesConstants;
import tech.ippon.chatbotdemo.security.SecurityUtils;

/**
 * REST controller for managing Driver.
 */
@RestController
@RequestMapping("/api")
public class DriverResource {

    private final Logger log = LoggerFactory.getLogger(DriverResource.class);

    private static final String ENTITY_NAME = "driver";

    private final DriverRepository driverRepository;

    public DriverResource(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    /**
     * POST  /drivers : Create a new driver.
     *
     * @param driver the driver to create
     * @return the ResponseEntity with status 201 (Created) and with body the new driver, or with status 400 (Bad Request) if the driver has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/drivers")
    @Timed
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) throws URISyntaxException {
        log.debug("REST request to save Driver : {}", driver);
        if (driver.getId() != null) {
            throw new BadRequestAlertException("A new driver cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Driver result = driverRepository.save(driver);
        return ResponseEntity.created(new URI("/api/drivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drivers : Updates an existing driver.
     *
     * @param driver the driver to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated driver,
     * or with status 400 (Bad Request) if the driver is not valid,
     * or with status 500 (Internal Server Error) if the driver couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/drivers")
    @Timed
    public ResponseEntity<Driver> updateDriver(@RequestBody Driver driver) throws URISyntaxException {
        log.debug("REST request to update Driver : {}", driver);
        if (driver.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Driver result = driverRepository.save(driver);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, driver.getId().toString()))
            .body(result);
    }
    /**
     * GET /drivers : get all the drivers.
     *
     * @param login the login of the driver to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of drivers in body
     */
    @GetMapping("/drivers")
    @Timed
    public List<Driver> getAllDrivers() {// associated with the current account
        if (SecurityUtils.getCurrentUserLogin().isPresent()){
            String login=SecurityUtils.getCurrentUserLogin().get();
            // SecurityContextHolder.getContext().getAuthentication();
            if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
                log.debug("REST request for admin, he has all rights");
                return driverRepository.findAll();
            }
            log.debug("REST request to get all Drivers with the login: {}",login );
            return driverRepository.findByUserLogin(login);
        }
        log.debug("REST request to get all Drivers");
        return driverRepository.findAll();
    }
    
    /**
     * GET  /drivers/:id : get the "id" driver.
     *
     * @param id the id of the driver to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the driver, or with status 404 (Not Found)
     */
    @GetMapping("/drivers/{id}")
    @Timed
    public ResponseEntity<Driver> getDriver(@PathVariable Long id) {
        log.debug("REST request to get Driver : {}", id);
        Optional<Driver> driver = driverRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(driver);
    }

    /**
     * DELETE  /drivers/:id : delete the "id" driver.
     *
     * @param id the id of the driver to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/drivers/{id}")
    @Timed
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        log.debug("REST request to delete Driver : {}", id);

        driverRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
