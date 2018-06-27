package org.jhipster.web.rest;

import org.jhipster.MicroserviceApp;

import org.jhipster.domain.InsuranceDetails;
import org.jhipster.repository.InsuranceDetailsRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static org.jhipster.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.jhipster.domain.enumeration.InsuranceType;
/**
 * Test class for the InsuranceDetailsResource REST controller.
 *
 * @see InsuranceDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MicroserviceApp.class)
public class InsuranceDetailsResourceIntTest {

    private static final InsuranceType DEFAULT_LEVEL = InsuranceType.NONE;
    private static final InsuranceType UPDATED_LEVEL = InsuranceType.STATEMINIMUM;

    private static final BigDecimal DEFAULT_ANNUAL_PREMIUM = new BigDecimal(1);
    private static final BigDecimal UPDATED_ANNUAL_PREMIUM = new BigDecimal(2);

    private static final Boolean DEFAULT_INJURY_LIABILITY = false;
    private static final Boolean UPDATED_INJURY_LIABILITY = true;

    private static final Boolean DEFAULT_PROPERTY_LIABILITY = false;
    private static final Boolean UPDATED_PROPERTY_LIABILITY = true;

    private static final Boolean DEFAULT_UNINSURED_MOTORIST_INJURY = false;
    private static final Boolean UPDATED_UNINSURED_MOTORIST_INJURY = true;

    private static final Boolean DEFAULT_UNINSURED_MOTORIST_PROPERTY = false;
    private static final Boolean UPDATED_UNINSURED_MOTORIST_PROPERTY = true;

    @Autowired
    private InsuranceDetailsRepository insuranceDetailsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInsuranceDetailsMockMvc;

    private InsuranceDetails insuranceDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InsuranceDetailsResource insuranceDetailsResource = new InsuranceDetailsResource(insuranceDetailsRepository);
        this.restInsuranceDetailsMockMvc = MockMvcBuilders.standaloneSetup(insuranceDetailsResource)
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
    public static InsuranceDetails createEntity(EntityManager em) {
        InsuranceDetails insuranceDetails = new InsuranceDetails()
            .level(DEFAULT_LEVEL)
            .annualPremium(DEFAULT_ANNUAL_PREMIUM)
            .injuryLiability(DEFAULT_INJURY_LIABILITY)
            .propertyLiability(DEFAULT_PROPERTY_LIABILITY)
            .uninsuredMotoristInjury(DEFAULT_UNINSURED_MOTORIST_INJURY)
            .uninsuredMotoristProperty(DEFAULT_UNINSURED_MOTORIST_PROPERTY);
        return insuranceDetails;
    }

    @Before
    public void initTest() {
        insuranceDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsuranceDetails() throws Exception {
        int databaseSizeBeforeCreate = insuranceDetailsRepository.findAll().size();

        // Create the InsuranceDetails
        restInsuranceDetailsMockMvc.perform(post("/api/insurance-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceDetails)))
            .andExpect(status().isCreated());

        // Validate the InsuranceDetails in the database
        List<InsuranceDetails> insuranceDetailsList = insuranceDetailsRepository.findAll();
        assertThat(insuranceDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        InsuranceDetails testInsuranceDetails = insuranceDetailsList.get(insuranceDetailsList.size() - 1);
        assertThat(testInsuranceDetails.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testInsuranceDetails.getAnnualPremium()).isEqualTo(DEFAULT_ANNUAL_PREMIUM);
        assertThat(testInsuranceDetails.isInjuryLiability()).isEqualTo(DEFAULT_INJURY_LIABILITY);
        assertThat(testInsuranceDetails.isPropertyLiability()).isEqualTo(DEFAULT_PROPERTY_LIABILITY);
        assertThat(testInsuranceDetails.isUninsuredMotoristInjury()).isEqualTo(DEFAULT_UNINSURED_MOTORIST_INJURY);
        assertThat(testInsuranceDetails.isUninsuredMotoristProperty()).isEqualTo(DEFAULT_UNINSURED_MOTORIST_PROPERTY);
    }

    @Test
    @Transactional
    public void createInsuranceDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insuranceDetailsRepository.findAll().size();

        // Create the InsuranceDetails with an existing ID
        insuranceDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsuranceDetailsMockMvc.perform(post("/api/insurance-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceDetails)))
            .andExpect(status().isBadRequest());

        // Validate the InsuranceDetails in the database
        List<InsuranceDetails> insuranceDetailsList = insuranceDetailsRepository.findAll();
        assertThat(insuranceDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInsuranceDetails() throws Exception {
        // Initialize the database
        insuranceDetailsRepository.saveAndFlush(insuranceDetails);

        // Get all the insuranceDetailsList
        restInsuranceDetailsMockMvc.perform(get("/api/insurance-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insuranceDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].annualPremium").value(hasItem(DEFAULT_ANNUAL_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].injuryLiability").value(hasItem(DEFAULT_INJURY_LIABILITY.booleanValue())))
            .andExpect(jsonPath("$.[*].propertyLiability").value(hasItem(DEFAULT_PROPERTY_LIABILITY.booleanValue())))
            .andExpect(jsonPath("$.[*].uninsuredMotoristInjury").value(hasItem(DEFAULT_UNINSURED_MOTORIST_INJURY.booleanValue())))
            .andExpect(jsonPath("$.[*].uninsuredMotoristProperty").value(hasItem(DEFAULT_UNINSURED_MOTORIST_PROPERTY.booleanValue())));
    }

    @Test
    @Transactional
    public void getInsuranceDetails() throws Exception {
        // Initialize the database
        insuranceDetailsRepository.saveAndFlush(insuranceDetails);

        // Get the insuranceDetails
        restInsuranceDetailsMockMvc.perform(get("/api/insurance-details/{id}", insuranceDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insuranceDetails.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.annualPremium").value(DEFAULT_ANNUAL_PREMIUM.intValue()))
            .andExpect(jsonPath("$.injuryLiability").value(DEFAULT_INJURY_LIABILITY.booleanValue()))
            .andExpect(jsonPath("$.propertyLiability").value(DEFAULT_PROPERTY_LIABILITY.booleanValue()))
            .andExpect(jsonPath("$.uninsuredMotoristInjury").value(DEFAULT_UNINSURED_MOTORIST_INJURY.booleanValue()))
            .andExpect(jsonPath("$.uninsuredMotoristProperty").value(DEFAULT_UNINSURED_MOTORIST_PROPERTY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInsuranceDetails() throws Exception {
        // Get the insuranceDetails
        restInsuranceDetailsMockMvc.perform(get("/api/insurance-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsuranceDetails() throws Exception {
        // Initialize the database
        insuranceDetailsRepository.saveAndFlush(insuranceDetails);
        int databaseSizeBeforeUpdate = insuranceDetailsRepository.findAll().size();

        // Update the insuranceDetails
        InsuranceDetails updatedInsuranceDetails = insuranceDetailsRepository.findOne(insuranceDetails.getId());
        // Disconnect from session so that the updates on updatedInsuranceDetails are not directly saved in db
        em.detach(updatedInsuranceDetails);
        updatedInsuranceDetails
            .level(UPDATED_LEVEL)
            .annualPremium(UPDATED_ANNUAL_PREMIUM)
            .injuryLiability(UPDATED_INJURY_LIABILITY)
            .propertyLiability(UPDATED_PROPERTY_LIABILITY)
            .uninsuredMotoristInjury(UPDATED_UNINSURED_MOTORIST_INJURY)
            .uninsuredMotoristProperty(UPDATED_UNINSURED_MOTORIST_PROPERTY);

        restInsuranceDetailsMockMvc.perform(put("/api/insurance-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInsuranceDetails)))
            .andExpect(status().isOk());

        // Validate the InsuranceDetails in the database
        List<InsuranceDetails> insuranceDetailsList = insuranceDetailsRepository.findAll();
        assertThat(insuranceDetailsList).hasSize(databaseSizeBeforeUpdate);
        InsuranceDetails testInsuranceDetails = insuranceDetailsList.get(insuranceDetailsList.size() - 1);
        assertThat(testInsuranceDetails.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testInsuranceDetails.getAnnualPremium()).isEqualTo(UPDATED_ANNUAL_PREMIUM);
        assertThat(testInsuranceDetails.isInjuryLiability()).isEqualTo(UPDATED_INJURY_LIABILITY);
        assertThat(testInsuranceDetails.isPropertyLiability()).isEqualTo(UPDATED_PROPERTY_LIABILITY);
        assertThat(testInsuranceDetails.isUninsuredMotoristInjury()).isEqualTo(UPDATED_UNINSURED_MOTORIST_INJURY);
        assertThat(testInsuranceDetails.isUninsuredMotoristProperty()).isEqualTo(UPDATED_UNINSURED_MOTORIST_PROPERTY);
    }

    @Test
    @Transactional
    public void updateNonExistingInsuranceDetails() throws Exception {
        int databaseSizeBeforeUpdate = insuranceDetailsRepository.findAll().size();

        // Create the InsuranceDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInsuranceDetailsMockMvc.perform(put("/api/insurance-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insuranceDetails)))
            .andExpect(status().isCreated());

        // Validate the InsuranceDetails in the database
        List<InsuranceDetails> insuranceDetailsList = insuranceDetailsRepository.findAll();
        assertThat(insuranceDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInsuranceDetails() throws Exception {
        // Initialize the database
        insuranceDetailsRepository.saveAndFlush(insuranceDetails);
        int databaseSizeBeforeDelete = insuranceDetailsRepository.findAll().size();

        // Get the insuranceDetails
        restInsuranceDetailsMockMvc.perform(delete("/api/insurance-details/{id}", insuranceDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InsuranceDetails> insuranceDetailsList = insuranceDetailsRepository.findAll();
        assertThat(insuranceDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsuranceDetails.class);
        InsuranceDetails insuranceDetails1 = new InsuranceDetails();
        insuranceDetails1.setId(1L);
        InsuranceDetails insuranceDetails2 = new InsuranceDetails();
        insuranceDetails2.setId(insuranceDetails1.getId());
        assertThat(insuranceDetails1).isEqualTo(insuranceDetails2);
        insuranceDetails2.setId(2L);
        assertThat(insuranceDetails1).isNotEqualTo(insuranceDetails2);
        insuranceDetails1.setId(null);
        assertThat(insuranceDetails1).isNotEqualTo(insuranceDetails2);
    }
}
