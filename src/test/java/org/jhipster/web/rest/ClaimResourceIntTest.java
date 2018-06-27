package org.jhipster.web.rest;

import org.jhipster.MicroserviceApp;

import org.jhipster.domain.Claim;
import org.jhipster.repository.ClaimRepository;
import org.jhipster.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.jhipster.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClaimResource REST controller.
 *
 * @see ClaimResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MicroserviceApp.class)
public class ClaimResourceIntTest {

    private static final Boolean DEFAULT_INJURY_INVOLVED = false;
    private static final Boolean UPDATED_INJURY_INVOLVED = true;

    private static final LocalDate DEFAULT_ACCIDENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACCIDENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_ACCIDENT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACCIDENT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ACCIDENT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_ACCIDENT_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_ACCIDENT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_ACCIDENT_STATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PASSENGERS_IN_CARS = false;
    private static final Boolean UPDATED_PASSENGERS_IN_CARS = true;

    private static final String DEFAULT_DAMAGE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_DAMAGE_LOCATION = "BBBBBBBBBB";

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClaimMockMvc;

    private Claim claim;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClaimResource claimResource = new ClaimResource(claimRepository);
        this.restClaimMockMvc = MockMvcBuilders.standaloneSetup(claimResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Claim createEntity(EntityManager em) {
        Claim claim = new Claim()
            .injuryInvolved(DEFAULT_INJURY_INVOLVED)
            .accidentDate(DEFAULT_ACCIDENT_DATE)
            .accidentTime(DEFAULT_ACCIDENT_TIME)
            .accidentCity(DEFAULT_ACCIDENT_CITY)
            .accidentState(DEFAULT_ACCIDENT_STATE)
            .passengersInCars(DEFAULT_PASSENGERS_IN_CARS)
            .damageLocation(DEFAULT_DAMAGE_LOCATION);
        return claim;
    }

    @Before
    public void initTest() {
        claim = createEntity(em);
    }

    @Test
    @Transactional
    public void createClaim() throws Exception {
        int databaseSizeBeforeCreate = claimRepository.findAll().size();

        // Create the Claim
        restClaimMockMvc.perform(post("/api/claims")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isCreated());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeCreate + 1);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.isInjuryInvolved()).isEqualTo(DEFAULT_INJURY_INVOLVED);
        assertThat(testClaim.getAccidentDate()).isEqualTo(DEFAULT_ACCIDENT_DATE);
        assertThat(testClaim.getAccidentTime()).isEqualTo(DEFAULT_ACCIDENT_TIME);
        assertThat(testClaim.getAccidentCity()).isEqualTo(DEFAULT_ACCIDENT_CITY);
        assertThat(testClaim.getAccidentState()).isEqualTo(DEFAULT_ACCIDENT_STATE);
        assertThat(testClaim.isPassengersInCars()).isEqualTo(DEFAULT_PASSENGERS_IN_CARS);
        assertThat(testClaim.getDamageLocation()).isEqualTo(DEFAULT_DAMAGE_LOCATION);
    }

    @Test
    @Transactional
    public void createClaimWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = claimRepository.findAll().size();

        // Create the Claim with an existing ID
        claim.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimMockMvc.perform(post("/api/claims")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClaims() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList
        restClaimMockMvc.perform(get("/api/claims?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claim.getId().intValue())))
            .andExpect(jsonPath("$.[*].injuryInvolved").value(hasItem(DEFAULT_INJURY_INVOLVED.booleanValue())))
            .andExpect(jsonPath("$.[*].accidentDate").value(hasItem(DEFAULT_ACCIDENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].accidentTime").value(hasItem(DEFAULT_ACCIDENT_TIME.toString())))
            .andExpect(jsonPath("$.[*].accidentCity").value(hasItem(DEFAULT_ACCIDENT_CITY.toString())))
            .andExpect(jsonPath("$.[*].accidentState").value(hasItem(DEFAULT_ACCIDENT_STATE.toString())))
            .andExpect(jsonPath("$.[*].passengersInCars").value(hasItem(DEFAULT_PASSENGERS_IN_CARS.booleanValue())))
            .andExpect(jsonPath("$.[*].damageLocation").value(hasItem(DEFAULT_DAMAGE_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void getClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get the claim
        restClaimMockMvc.perform(get("/api/claims/{id}", claim.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(claim.getId().intValue()))
            .andExpect(jsonPath("$.injuryInvolved").value(DEFAULT_INJURY_INVOLVED.booleanValue()))
            .andExpect(jsonPath("$.accidentDate").value(DEFAULT_ACCIDENT_DATE.toString()))
            .andExpect(jsonPath("$.accidentTime").value(DEFAULT_ACCIDENT_TIME.toString()))
            .andExpect(jsonPath("$.accidentCity").value(DEFAULT_ACCIDENT_CITY.toString()))
            .andExpect(jsonPath("$.accidentState").value(DEFAULT_ACCIDENT_STATE.toString()))
            .andExpect(jsonPath("$.passengersInCars").value(DEFAULT_PASSENGERS_IN_CARS.booleanValue()))
            .andExpect(jsonPath("$.damageLocation").value(DEFAULT_DAMAGE_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClaim() throws Exception {
        // Get the claim
        restClaimMockMvc.perform(get("/api/claims/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();

        // Update the claim
        Claim updatedClaim = claimRepository.findOne(claim.getId());
        // Disconnect from session so that the updates on updatedClaim are not directly saved in db
        em.detach(updatedClaim);
        updatedClaim
            .injuryInvolved(UPDATED_INJURY_INVOLVED)
            .accidentDate(UPDATED_ACCIDENT_DATE)
            .accidentTime(UPDATED_ACCIDENT_TIME)
            .accidentCity(UPDATED_ACCIDENT_CITY)
            .accidentState(UPDATED_ACCIDENT_STATE)
            .passengersInCars(UPDATED_PASSENGERS_IN_CARS)
            .damageLocation(UPDATED_DAMAGE_LOCATION);

        restClaimMockMvc.perform(put("/api/claims")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClaim)))
            .andExpect(status().isOk());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.isInjuryInvolved()).isEqualTo(UPDATED_INJURY_INVOLVED);
        assertThat(testClaim.getAccidentDate()).isEqualTo(UPDATED_ACCIDENT_DATE);
        assertThat(testClaim.getAccidentTime()).isEqualTo(UPDATED_ACCIDENT_TIME);
        assertThat(testClaim.getAccidentCity()).isEqualTo(UPDATED_ACCIDENT_CITY);
        assertThat(testClaim.getAccidentState()).isEqualTo(UPDATED_ACCIDENT_STATE);
        assertThat(testClaim.isPassengersInCars()).isEqualTo(UPDATED_PASSENGERS_IN_CARS);
        assertThat(testClaim.getDamageLocation()).isEqualTo(UPDATED_DAMAGE_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();

        // Create the Claim

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClaimMockMvc.perform(put("/api/claims")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isCreated());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);
        int databaseSizeBeforeDelete = claimRepository.findAll().size();

        // Get the claim
        restClaimMockMvc.perform(delete("/api/claims/{id}", claim.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Claim.class);
        Claim claim1 = new Claim();
        claim1.setId(1L);
        Claim claim2 = new Claim();
        claim2.setId(claim1.getId());
        assertThat(claim1).isEqualTo(claim2);
        claim2.setId(2L);
        assertThat(claim1).isNotEqualTo(claim2);
        claim1.setId(null);
        assertThat(claim1).isNotEqualTo(claim2);
    }
}
