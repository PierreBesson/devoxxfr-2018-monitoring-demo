package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.CatalogApp;

import com.mycompany.myapp.domain.Tour;
import com.mycompany.myapp.repository.TourRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TourResource REST controller.
 *
 * @see TourResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApp.class)
public class TourResourceIntTest {

    private static final String DEFAULT_TOUR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TOUR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_DURATION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_TO_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TO_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTourMockMvc;

    private Tour tour;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TourResource tourResource = new TourResource(tourRepository);
        this.restTourMockMvc = MockMvcBuilders.standaloneSetup(tourResource)
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
    public static Tour createEntity(EntityManager em) {
        Tour tour = new Tour()
            .tourName(DEFAULT_TOUR_NAME)
            .type(DEFAULT_TYPE)
            .duration(DEFAULT_DURATION)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .description(DEFAULT_DESCRIPTION);
        return tour;
    }

    @Before
    public void initTest() {
        tour = createEntity(em);
    }

    @Test
    @Transactional
    public void createTour() throws Exception {
        int databaseSizeBeforeCreate = tourRepository.findAll().size();

        // Create the Tour
        restTourMockMvc.perform(post("/api/tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isCreated());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeCreate + 1);
        Tour testTour = tourList.get(tourList.size() - 1);
        assertThat(testTour.getTourName()).isEqualTo(DEFAULT_TOUR_NAME);
        assertThat(testTour.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTour.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testTour.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testTour.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testTour.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tourRepository.findAll().size();

        // Create the Tour with an existing ID
        tour.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourMockMvc.perform(post("/api/tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isBadRequest());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTourNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tourRepository.findAll().size();
        // set the field null
        tour.setTourName(null);

        // Create the Tour, which fails.

        restTourMockMvc.perform(post("/api/tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isBadRequest());

        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTours() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        // Get all the tourList
        restTourMockMvc.perform(get("/api/tours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tour.getId().intValue())))
            .andExpect(jsonPath("$.[*].tourName").value(hasItem(DEFAULT_TOUR_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(sameInstant(DEFAULT_TO_DATE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        // Get the tour
        restTourMockMvc.perform(get("/api/tours/{id}", tour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tour.getId().intValue()))
            .andExpect(jsonPath("$.tourName").value(DEFAULT_TOUR_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.fromDate").value(sameInstant(DEFAULT_FROM_DATE)))
            .andExpect(jsonPath("$.toDate").value(sameInstant(DEFAULT_TO_DATE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTour() throws Exception {
        // Get the tour
        restTourMockMvc.perform(get("/api/tours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);
        int databaseSizeBeforeUpdate = tourRepository.findAll().size();

        // Update the tour
        Tour updatedTour = tourRepository.findOne(tour.getId());
        // Disconnect from session so that the updates on updatedTour are not directly saved in db
        em.detach(updatedTour);
        updatedTour
            .tourName(UPDATED_TOUR_NAME)
            .type(UPDATED_TYPE)
            .duration(UPDATED_DURATION)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .description(UPDATED_DESCRIPTION);

        restTourMockMvc.perform(put("/api/tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTour)))
            .andExpect(status().isOk());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate);
        Tour testTour = tourList.get(tourList.size() - 1);
        assertThat(testTour.getTourName()).isEqualTo(UPDATED_TOUR_NAME);
        assertThat(testTour.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTour.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testTour.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testTour.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testTour.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTour() throws Exception {
        int databaseSizeBeforeUpdate = tourRepository.findAll().size();

        // Create the Tour

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTourMockMvc.perform(put("/api/tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tour)))
            .andExpect(status().isCreated());

        // Validate the Tour in the database
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);
        int databaseSizeBeforeDelete = tourRepository.findAll().size();

        // Get the tour
        restTourMockMvc.perform(delete("/api/tours/{id}", tour.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tour> tourList = tourRepository.findAll();
        assertThat(tourList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tour.class);
        Tour tour1 = new Tour();
        tour1.setId(1L);
        Tour tour2 = new Tour();
        tour2.setId(tour1.getId());
        assertThat(tour1).isEqualTo(tour2);
        tour2.setId(2L);
        assertThat(tour1).isNotEqualTo(tour2);
        tour1.setId(null);
        assertThat(tour1).isNotEqualTo(tour2);
    }
}
