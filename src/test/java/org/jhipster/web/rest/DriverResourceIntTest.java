package org.jhipster.web.rest;

import org.jhipster.MicroserviceApp;

import org.jhipster.domain.Driver;
import org.jhipster.repository.DriverRepository;
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
import java.time.ZoneId;
import java.util.List;

import static org.jhipster.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DriverResource REST controller.
 *
 * @see DriverResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MicroserviceApp.class)
public class DriverResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LICENSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LICENSE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PAST_ACCIDENT = 1;
    private static final Integer UPDATED_PAST_ACCIDENT = 2;

    private static final Long DEFAULT_ZIP_CODE = 1L;
    private static final Long UPDATED_ZIP_CODE = 2L;

    private static final Boolean DEFAULT_DISABLE = false;
    private static final Boolean UPDATED_DISABLE = true;

    private static final Boolean DEFAULT_MARITAL_STATUS = false;
    private static final Boolean UPDATED_MARITAL_STATUS = true;

    private static final Boolean DEFAULT_OWNS_HOME = false;
    private static final Boolean UPDATED_OWNS_HOME = true;

    private static final Boolean DEFAULT_MILITARY_SERVICE = false;
    private static final Boolean UPDATED_MILITARY_SERVICE = true;

    private static final String DEFAULT_USER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_USER_LOGIN = "BBBBBBBBBB";

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDriverMockMvc;

    private Driver driver;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DriverResource driverResource = new DriverResource(driverRepository);
        this.restDriverMockMvc = MockMvcBuilders.standaloneSetup(driverResource)
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
    public static Driver createEntity(EntityManager em) {
        Driver driver = new Driver()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .birthDate(DEFAULT_BIRTH_DATE)
            .licenseDate(DEFAULT_LICENSE_DATE)
            .pastAccident(DEFAULT_PAST_ACCIDENT)
            .zipCode(DEFAULT_ZIP_CODE)
            .disable(DEFAULT_DISABLE)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .ownsHome(DEFAULT_OWNS_HOME)
            .militaryService(DEFAULT_MILITARY_SERVICE)
            .userLogin(DEFAULT_USER_LOGIN);
        return driver;
    }

    @Before
    public void initTest() {
        driver = createEntity(em);
    }

    @Test
    @Transactional
    public void createDriver() throws Exception {
        int databaseSizeBeforeCreate = driverRepository.findAll().size();

        // Create the Driver
        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driver)))
            .andExpect(status().isCreated());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeCreate + 1);
        Driver testDriver = driverList.get(driverList.size() - 1);
        assertThat(testDriver.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testDriver.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testDriver.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testDriver.getLicenseDate()).isEqualTo(DEFAULT_LICENSE_DATE);
        assertThat(testDriver.getPastAccident()).isEqualTo(DEFAULT_PAST_ACCIDENT);
        assertThat(testDriver.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testDriver.isDisable()).isEqualTo(DEFAULT_DISABLE);
        assertThat(testDriver.isMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testDriver.isOwnsHome()).isEqualTo(DEFAULT_OWNS_HOME);
        assertThat(testDriver.isMilitaryService()).isEqualTo(DEFAULT_MILITARY_SERVICE);
        assertThat(testDriver.getUserLogin()).isEqualTo(DEFAULT_USER_LOGIN);
    }

    @Test
    @Transactional
    public void createDriverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = driverRepository.findAll().size();

        // Create the Driver with an existing ID
        driver.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driver)))
            .andExpect(status().isBadRequest());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDrivers() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList
        restDriverMockMvc.perform(get("/api/drivers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(driver.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].licenseDate").value(hasItem(DEFAULT_LICENSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].pastAccident").value(hasItem(DEFAULT_PAST_ACCIDENT)))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.intValue())))
            .andExpect(jsonPath("$.[*].disable").value(hasItem(DEFAULT_DISABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].ownsHome").value(hasItem(DEFAULT_OWNS_HOME.booleanValue())))
            .andExpect(jsonPath("$.[*].militaryService").value(hasItem(DEFAULT_MILITARY_SERVICE.booleanValue())))
            .andExpect(jsonPath("$.[*].userLogin").value(hasItem(DEFAULT_USER_LOGIN.toString())));
    }

    @Test
    @Transactional
    public void getDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", driver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(driver.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.licenseDate").value(DEFAULT_LICENSE_DATE.toString()))
            .andExpect(jsonPath("$.pastAccident").value(DEFAULT_PAST_ACCIDENT))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.intValue()))
            .andExpect(jsonPath("$.disable").value(DEFAULT_DISABLE.booleanValue()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.booleanValue()))
            .andExpect(jsonPath("$.ownsHome").value(DEFAULT_OWNS_HOME.booleanValue()))
            .andExpect(jsonPath("$.militaryService").value(DEFAULT_MILITARY_SERVICE.booleanValue()))
            .andExpect(jsonPath("$.userLogin").value(DEFAULT_USER_LOGIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDriver() throws Exception {
        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);
        int databaseSizeBeforeUpdate = driverRepository.findAll().size();

        // Update the driver
        Driver updatedDriver = driverRepository.findOne(driver.getId());
        // Disconnect from session so that the updates on updatedDriver are not directly saved in db
        em.detach(updatedDriver);
        updatedDriver
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .licenseDate(UPDATED_LICENSE_DATE)
            .pastAccident(UPDATED_PAST_ACCIDENT)
            .zipCode(UPDATED_ZIP_CODE)
            .disable(UPDATED_DISABLE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .ownsHome(UPDATED_OWNS_HOME)
            .militaryService(UPDATED_MILITARY_SERVICE)
            .userLogin(UPDATED_USER_LOGIN);

        restDriverMockMvc.perform(put("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDriver)))
            .andExpect(status().isOk());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeUpdate);
        Driver testDriver = driverList.get(driverList.size() - 1);
        assertThat(testDriver.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testDriver.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testDriver.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testDriver.getLicenseDate()).isEqualTo(UPDATED_LICENSE_DATE);
        assertThat(testDriver.getPastAccident()).isEqualTo(UPDATED_PAST_ACCIDENT);
        assertThat(testDriver.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testDriver.isDisable()).isEqualTo(UPDATED_DISABLE);
        assertThat(testDriver.isMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testDriver.isOwnsHome()).isEqualTo(UPDATED_OWNS_HOME);
        assertThat(testDriver.isMilitaryService()).isEqualTo(UPDATED_MILITARY_SERVICE);
        assertThat(testDriver.getUserLogin()).isEqualTo(UPDATED_USER_LOGIN);
    }

    @Test
    @Transactional
    public void updateNonExistingDriver() throws Exception {
        int databaseSizeBeforeUpdate = driverRepository.findAll().size();

        // Create the Driver

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDriverMockMvc.perform(put("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driver)))
            .andExpect(status().isCreated());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);
        int databaseSizeBeforeDelete = driverRepository.findAll().size();

        // Get the driver
        restDriverMockMvc.perform(delete("/api/drivers/{id}", driver.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Driver.class);
        Driver driver1 = new Driver();
        driver1.setId(1L);
        Driver driver2 = new Driver();
        driver2.setId(driver1.getId());
        assertThat(driver1).isEqualTo(driver2);
        driver2.setId(2L);
        assertThat(driver1).isNotEqualTo(driver2);
        driver1.setId(null);
        assertThat(driver1).isNotEqualTo(driver2);
    }
}
