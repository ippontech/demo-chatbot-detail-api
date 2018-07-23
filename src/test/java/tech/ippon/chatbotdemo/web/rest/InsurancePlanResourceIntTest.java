package tech.ippon.chatbotdemo.web.rest;

import tech.ippon.chatbotdemo.InsuranceMicroserviceApp;

import tech.ippon.chatbotdemo.domain.InsurancePlan;
import tech.ippon.chatbotdemo.repository.InsurancePlanRepository;
import tech.ippon.chatbotdemo.service.InsurancePlanService;
import tech.ippon.chatbotdemo.web.rest.errors.ExceptionTranslator;
import tech.ippon.chatbotdemo.service.dto.InsurancePlanCriteria;
import tech.ippon.chatbotdemo.service.InsurancePlanQueryService;

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


import static tech.ippon.chatbotdemo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InsurancePlanResource REST controller.
 *
 * @see InsurancePlanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InsuranceMicroserviceApp.class)
public class InsurancePlanResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUMMARY = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARY = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_YEARLY_PREMIUM = new BigDecimal(1);
    private static final BigDecimal UPDATED_YEARLY_PREMIUM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DEDUCTABLE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DEDUCTABLE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_COVERAGE_PER_PERSON = new BigDecimal(1);
    private static final BigDecimal UPDATED_COVERAGE_PER_PERSON = new BigDecimal(2);

    private static final BigDecimal DEFAULT_COVERAGE_PER_ACCIDENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_COVERAGE_PER_ACCIDENT = new BigDecimal(2);

    @Autowired
    private InsurancePlanRepository insurancePlanRepository;

    

    @Autowired
    private InsurancePlanService insurancePlanService;

    @Autowired
    private InsurancePlanQueryService insurancePlanQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInsurancePlanMockMvc;

    private InsurancePlan insurancePlan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InsurancePlanResource insurancePlanResource = new InsurancePlanResource(insurancePlanService, insurancePlanQueryService);
        this.restInsurancePlanMockMvc = MockMvcBuilders.standaloneSetup(insurancePlanResource)
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
    public static InsurancePlan createEntity(EntityManager em) {
        InsurancePlan insurancePlan = new InsurancePlan()
            .name(DEFAULT_NAME)
            .summary(DEFAULT_SUMMARY)
            .yearlyPremium(DEFAULT_YEARLY_PREMIUM)
            .deductable(DEFAULT_DEDUCTABLE)
            .coveragePerPerson(DEFAULT_COVERAGE_PER_PERSON)
            .coveragePerAccident(DEFAULT_COVERAGE_PER_ACCIDENT);
        return insurancePlan;
    }

    @Before
    public void initTest() {
        insurancePlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsurancePlan() throws Exception {
        int databaseSizeBeforeCreate = insurancePlanRepository.findAll().size();

        // Create the InsurancePlan
        restInsurancePlanMockMvc.perform(post("/api/insurance-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurancePlan)))
            .andExpect(status().isCreated());

        // Validate the InsurancePlan in the database
        List<InsurancePlan> insurancePlanList = insurancePlanRepository.findAll();
        assertThat(insurancePlanList).hasSize(databaseSizeBeforeCreate + 1);
        InsurancePlan testInsurancePlan = insurancePlanList.get(insurancePlanList.size() - 1);
        assertThat(testInsurancePlan.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInsurancePlan.getSummary()).isEqualTo(DEFAULT_SUMMARY);
        assertThat(testInsurancePlan.getYearlyPremium()).isEqualTo(DEFAULT_YEARLY_PREMIUM);
        assertThat(testInsurancePlan.getDeductable()).isEqualTo(DEFAULT_DEDUCTABLE);
        assertThat(testInsurancePlan.getCoveragePerPerson()).isEqualTo(DEFAULT_COVERAGE_PER_PERSON);
        assertThat(testInsurancePlan.getCoveragePerAccident()).isEqualTo(DEFAULT_COVERAGE_PER_ACCIDENT);
    }

    @Test
    @Transactional
    public void createInsurancePlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insurancePlanRepository.findAll().size();

        // Create the InsurancePlan with an existing ID
        insurancePlan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsurancePlanMockMvc.perform(post("/api/insurance-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurancePlan)))
            .andExpect(status().isBadRequest());

        // Validate the InsurancePlan in the database
        List<InsurancePlan> insurancePlanList = insurancePlanRepository.findAll();
        assertThat(insurancePlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInsurancePlans() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList
        restInsurancePlanMockMvc.perform(get("/api/insurance-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insurancePlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY.toString())))
            .andExpect(jsonPath("$.[*].yearlyPremium").value(hasItem(DEFAULT_YEARLY_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].deductable").value(hasItem(DEFAULT_DEDUCTABLE.intValue())))
            .andExpect(jsonPath("$.[*].coveragePerPerson").value(hasItem(DEFAULT_COVERAGE_PER_PERSON.intValue())))
            .andExpect(jsonPath("$.[*].coveragePerAccident").value(hasItem(DEFAULT_COVERAGE_PER_ACCIDENT.intValue())));
    }
    

    @Test
    @Transactional
    public void getInsurancePlan() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get the insurancePlan
        restInsurancePlanMockMvc.perform(get("/api/insurance-plans/{id}", insurancePlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insurancePlan.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.summary").value(DEFAULT_SUMMARY.toString()))
            .andExpect(jsonPath("$.yearlyPremium").value(DEFAULT_YEARLY_PREMIUM.intValue()))
            .andExpect(jsonPath("$.deductable").value(DEFAULT_DEDUCTABLE.intValue()))
            .andExpect(jsonPath("$.coveragePerPerson").value(DEFAULT_COVERAGE_PER_PERSON.intValue()))
            .andExpect(jsonPath("$.coveragePerAccident").value(DEFAULT_COVERAGE_PER_ACCIDENT.intValue()));
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where name equals to DEFAULT_NAME
        defaultInsurancePlanShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the insurancePlanList where name equals to UPDATED_NAME
        defaultInsurancePlanShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where name in DEFAULT_NAME or UPDATED_NAME
        defaultInsurancePlanShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the insurancePlanList where name equals to UPDATED_NAME
        defaultInsurancePlanShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where name is not null
        defaultInsurancePlanShouldBeFound("name.specified=true");

        // Get all the insurancePlanList where name is null
        defaultInsurancePlanShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllInsurancePlansBySummaryIsEqualToSomething() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where summary equals to DEFAULT_SUMMARY
        defaultInsurancePlanShouldBeFound("summary.equals=" + DEFAULT_SUMMARY);

        // Get all the insurancePlanList where summary equals to UPDATED_SUMMARY
        defaultInsurancePlanShouldNotBeFound("summary.equals=" + UPDATED_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllInsurancePlansBySummaryIsInShouldWork() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where summary in DEFAULT_SUMMARY or UPDATED_SUMMARY
        defaultInsurancePlanShouldBeFound("summary.in=" + DEFAULT_SUMMARY + "," + UPDATED_SUMMARY);

        // Get all the insurancePlanList where summary equals to UPDATED_SUMMARY
        defaultInsurancePlanShouldNotBeFound("summary.in=" + UPDATED_SUMMARY);
    }

    @Test
    @Transactional
    public void getAllInsurancePlansBySummaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where summary is not null
        defaultInsurancePlanShouldBeFound("summary.specified=true");

        // Get all the insurancePlanList where summary is null
        defaultInsurancePlanShouldNotBeFound("summary.specified=false");
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByYearlyPremiumIsEqualToSomething() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where yearlyPremium equals to DEFAULT_YEARLY_PREMIUM
        defaultInsurancePlanShouldBeFound("yearlyPremium.equals=" + DEFAULT_YEARLY_PREMIUM);

        // Get all the insurancePlanList where yearlyPremium equals to UPDATED_YEARLY_PREMIUM
        defaultInsurancePlanShouldNotBeFound("yearlyPremium.equals=" + UPDATED_YEARLY_PREMIUM);
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByYearlyPremiumIsInShouldWork() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where yearlyPremium in DEFAULT_YEARLY_PREMIUM or UPDATED_YEARLY_PREMIUM
        defaultInsurancePlanShouldBeFound("yearlyPremium.in=" + DEFAULT_YEARLY_PREMIUM + "," + UPDATED_YEARLY_PREMIUM);

        // Get all the insurancePlanList where yearlyPremium equals to UPDATED_YEARLY_PREMIUM
        defaultInsurancePlanShouldNotBeFound("yearlyPremium.in=" + UPDATED_YEARLY_PREMIUM);
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByYearlyPremiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where yearlyPremium is not null
        defaultInsurancePlanShouldBeFound("yearlyPremium.specified=true");

        // Get all the insurancePlanList where yearlyPremium is null
        defaultInsurancePlanShouldNotBeFound("yearlyPremium.specified=false");
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByDeductableIsEqualToSomething() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where deductable equals to DEFAULT_DEDUCTABLE
        defaultInsurancePlanShouldBeFound("deductable.equals=" + DEFAULT_DEDUCTABLE);

        // Get all the insurancePlanList where deductable equals to UPDATED_DEDUCTABLE
        defaultInsurancePlanShouldNotBeFound("deductable.equals=" + UPDATED_DEDUCTABLE);
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByDeductableIsInShouldWork() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where deductable in DEFAULT_DEDUCTABLE or UPDATED_DEDUCTABLE
        defaultInsurancePlanShouldBeFound("deductable.in=" + DEFAULT_DEDUCTABLE + "," + UPDATED_DEDUCTABLE);

        // Get all the insurancePlanList where deductable equals to UPDATED_DEDUCTABLE
        defaultInsurancePlanShouldNotBeFound("deductable.in=" + UPDATED_DEDUCTABLE);
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByDeductableIsNullOrNotNull() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where deductable is not null
        defaultInsurancePlanShouldBeFound("deductable.specified=true");

        // Get all the insurancePlanList where deductable is null
        defaultInsurancePlanShouldNotBeFound("deductable.specified=false");
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByCoveragePerPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where coveragePerPerson equals to DEFAULT_COVERAGE_PER_PERSON
        defaultInsurancePlanShouldBeFound("coveragePerPerson.equals=" + DEFAULT_COVERAGE_PER_PERSON);

        // Get all the insurancePlanList where coveragePerPerson equals to UPDATED_COVERAGE_PER_PERSON
        defaultInsurancePlanShouldNotBeFound("coveragePerPerson.equals=" + UPDATED_COVERAGE_PER_PERSON);
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByCoveragePerPersonIsInShouldWork() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where coveragePerPerson in DEFAULT_COVERAGE_PER_PERSON or UPDATED_COVERAGE_PER_PERSON
        defaultInsurancePlanShouldBeFound("coveragePerPerson.in=" + DEFAULT_COVERAGE_PER_PERSON + "," + UPDATED_COVERAGE_PER_PERSON);

        // Get all the insurancePlanList where coveragePerPerson equals to UPDATED_COVERAGE_PER_PERSON
        defaultInsurancePlanShouldNotBeFound("coveragePerPerson.in=" + UPDATED_COVERAGE_PER_PERSON);
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByCoveragePerPersonIsNullOrNotNull() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where coveragePerPerson is not null
        defaultInsurancePlanShouldBeFound("coveragePerPerson.specified=true");

        // Get all the insurancePlanList where coveragePerPerson is null
        defaultInsurancePlanShouldNotBeFound("coveragePerPerson.specified=false");
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByCoveragePerAccidentIsEqualToSomething() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where coveragePerAccident equals to DEFAULT_COVERAGE_PER_ACCIDENT
        defaultInsurancePlanShouldBeFound("coveragePerAccident.equals=" + DEFAULT_COVERAGE_PER_ACCIDENT);

        // Get all the insurancePlanList where coveragePerAccident equals to UPDATED_COVERAGE_PER_ACCIDENT
        defaultInsurancePlanShouldNotBeFound("coveragePerAccident.equals=" + UPDATED_COVERAGE_PER_ACCIDENT);
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByCoveragePerAccidentIsInShouldWork() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where coveragePerAccident in DEFAULT_COVERAGE_PER_ACCIDENT or UPDATED_COVERAGE_PER_ACCIDENT
        defaultInsurancePlanShouldBeFound("coveragePerAccident.in=" + DEFAULT_COVERAGE_PER_ACCIDENT + "," + UPDATED_COVERAGE_PER_ACCIDENT);

        // Get all the insurancePlanList where coveragePerAccident equals to UPDATED_COVERAGE_PER_ACCIDENT
        defaultInsurancePlanShouldNotBeFound("coveragePerAccident.in=" + UPDATED_COVERAGE_PER_ACCIDENT);
    }

    @Test
    @Transactional
    public void getAllInsurancePlansByCoveragePerAccidentIsNullOrNotNull() throws Exception {
        // Initialize the database
        insurancePlanRepository.saveAndFlush(insurancePlan);

        // Get all the insurancePlanList where coveragePerAccident is not null
        defaultInsurancePlanShouldBeFound("coveragePerAccident.specified=true");

        // Get all the insurancePlanList where coveragePerAccident is null
        defaultInsurancePlanShouldNotBeFound("coveragePerAccident.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultInsurancePlanShouldBeFound(String filter) throws Exception {
        restInsurancePlanMockMvc.perform(get("/api/insurance-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insurancePlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY.toString())))
            .andExpect(jsonPath("$.[*].yearlyPremium").value(hasItem(DEFAULT_YEARLY_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].deductable").value(hasItem(DEFAULT_DEDUCTABLE.intValue())))
            .andExpect(jsonPath("$.[*].coveragePerPerson").value(hasItem(DEFAULT_COVERAGE_PER_PERSON.intValue())))
            .andExpect(jsonPath("$.[*].coveragePerAccident").value(hasItem(DEFAULT_COVERAGE_PER_ACCIDENT.intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultInsurancePlanShouldNotBeFound(String filter) throws Exception {
        restInsurancePlanMockMvc.perform(get("/api/insurance-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingInsurancePlan() throws Exception {
        // Get the insurancePlan
        restInsurancePlanMockMvc.perform(get("/api/insurance-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsurancePlan() throws Exception {
        // Initialize the database
        insurancePlanService.save(insurancePlan);

        int databaseSizeBeforeUpdate = insurancePlanRepository.findAll().size();

        // Update the insurancePlan
        InsurancePlan updatedInsurancePlan = insurancePlanRepository.findById(insurancePlan.getId()).get();
        // Disconnect from session so that the updates on updatedInsurancePlan are not directly saved in db
        em.detach(updatedInsurancePlan);
        updatedInsurancePlan
            .name(UPDATED_NAME)
            .summary(UPDATED_SUMMARY)
            .yearlyPremium(UPDATED_YEARLY_PREMIUM)
            .deductable(UPDATED_DEDUCTABLE)
            .coveragePerPerson(UPDATED_COVERAGE_PER_PERSON)
            .coveragePerAccident(UPDATED_COVERAGE_PER_ACCIDENT);

        restInsurancePlanMockMvc.perform(put("/api/insurance-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInsurancePlan)))
            .andExpect(status().isOk());

        // Validate the InsurancePlan in the database
        List<InsurancePlan> insurancePlanList = insurancePlanRepository.findAll();
        assertThat(insurancePlanList).hasSize(databaseSizeBeforeUpdate);
        InsurancePlan testInsurancePlan = insurancePlanList.get(insurancePlanList.size() - 1);
        assertThat(testInsurancePlan.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInsurancePlan.getSummary()).isEqualTo(UPDATED_SUMMARY);
        assertThat(testInsurancePlan.getYearlyPremium()).isEqualTo(UPDATED_YEARLY_PREMIUM);
        assertThat(testInsurancePlan.getDeductable()).isEqualTo(UPDATED_DEDUCTABLE);
        assertThat(testInsurancePlan.getCoveragePerPerson()).isEqualTo(UPDATED_COVERAGE_PER_PERSON);
        assertThat(testInsurancePlan.getCoveragePerAccident()).isEqualTo(UPDATED_COVERAGE_PER_ACCIDENT);
    }

    @Test
    @Transactional
    public void updateNonExistingInsurancePlan() throws Exception {
        int databaseSizeBeforeUpdate = insurancePlanRepository.findAll().size();

        // Create the InsurancePlan

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInsurancePlanMockMvc.perform(put("/api/insurance-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurancePlan)))
            .andExpect(status().isBadRequest());

        // Validate the InsurancePlan in the database
        List<InsurancePlan> insurancePlanList = insurancePlanRepository.findAll();
        assertThat(insurancePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInsurancePlan() throws Exception {
        // Initialize the database
        insurancePlanService.save(insurancePlan);

        int databaseSizeBeforeDelete = insurancePlanRepository.findAll().size();

        // Get the insurancePlan
        restInsurancePlanMockMvc.perform(delete("/api/insurance-plans/{id}", insurancePlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InsurancePlan> insurancePlanList = insurancePlanRepository.findAll();
        assertThat(insurancePlanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InsurancePlan.class);
        InsurancePlan insurancePlan1 = new InsurancePlan();
        insurancePlan1.setId(1L);
        InsurancePlan insurancePlan2 = new InsurancePlan();
        insurancePlan2.setId(insurancePlan1.getId());
        assertThat(insurancePlan1).isEqualTo(insurancePlan2);
        insurancePlan2.setId(2L);
        assertThat(insurancePlan1).isNotEqualTo(insurancePlan2);
        insurancePlan1.setId(null);
        assertThat(insurancePlan1).isNotEqualTo(insurancePlan2);
    }
}
