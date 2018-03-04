package it.etianus.legal.web.rest;

import it.etianus.legal.LegalTechApp;

import it.etianus.legal.domain.Person;
import it.etianus.legal.repository.PersonRepository;
import it.etianus.legal.service.PersonService;
import it.etianus.legal.web.rest.errors.ExceptionTranslator;

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

import static it.etianus.legal.web.rest.TestUtil.sameInstant;
import static it.etianus.legal.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.etianus.legal.domain.enumeration.YesNotEnum;
import it.etianus.legal.domain.enumeration.IDTypeEnum;
import it.etianus.legal.domain.enumeration.YesNotEnum;
/**
 * Test class for the PersonResource REST controller.
 *
 * @see PersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LegalTechApp.class)
public class PersonResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_BIRTH_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BIRTH_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PIVA = "AAAAAAAAAA";
    private static final String UPDATED_PIVA = "BBBBBBBBBB";

    private static final String DEFAULT_CF = "AAAAAAAAAA";
    private static final String UPDATED_CF = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final YesNotEnum DEFAULT_FLAG_LEGAL = YesNotEnum.YES;
    private static final YesNotEnum UPDATED_FLAG_LEGAL = YesNotEnum.NO;

    private static final IDTypeEnum DEFAULT_ID_TYPE = IDTypeEnum.CARTAIDENTITA;
    private static final IDTypeEnum UPDATED_ID_TYPE = IDTypeEnum.PASSAPORTO;

    private static final String DEFAULT_ID_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ID_NUMBER = "BBBBBBBBBB";

    private static final YesNotEnum DEFAULT_FLAG_NEWSLETTER = YesNotEnum.YES;
    private static final YesNotEnum UPDATED_FLAG_NEWSLETTER = YesNotEnum.NO;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonMockMvc;

    private Person person;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonResource personResource = new PersonResource(personService);
        this.restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource)
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
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .birthDate(DEFAULT_BIRTH_DATE)
            .piva(DEFAULT_PIVA)
            .cf(DEFAULT_CF)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .flagLegal(DEFAULT_FLAG_LEGAL)
            .idType(DEFAULT_ID_TYPE)
            .idNumber(DEFAULT_ID_NUMBER)
            .flagNewsletter(DEFAULT_FLAG_NEWSLETTER);
        return person;
    }

    @Before
    public void initTest() {
        person = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPerson.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testPerson.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPerson.getPiva()).isEqualTo(DEFAULT_PIVA);
        assertThat(testPerson.getCf()).isEqualTo(DEFAULT_CF);
        assertThat(testPerson.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testPerson.getFlagLegal()).isEqualTo(DEFAULT_FLAG_LEGAL);
        assertThat(testPerson.getIdType()).isEqualTo(DEFAULT_ID_TYPE);
        assertThat(testPerson.getIdNumber()).isEqualTo(DEFAULT_ID_NUMBER);
        assertThat(testPerson.getFlagNewsletter()).isEqualTo(DEFAULT_FLAG_NEWSLETTER);
    }

    @Test
    @Transactional
    public void createPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person with an existing ID
        person.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setName(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setSurname(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setBirthDate(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFlagLegalIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setFlagLegal(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFlagNewsletterIsRequired() throws Exception {
        int databaseSizeBeforeTest = personRepository.findAll().size();
        // set the field null
        person.setFlagNewsletter(null);

        // Create the Person, which fails.

        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(sameInstant(DEFAULT_BIRTH_DATE))))
            .andExpect(jsonPath("$.[*].piva").value(hasItem(DEFAULT_PIVA.toString())))
            .andExpect(jsonPath("$.[*].cf").value(hasItem(DEFAULT_CF.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].flagLegal").value(hasItem(DEFAULT_FLAG_LEGAL.toString())))
            .andExpect(jsonPath("$.[*].idType").value(hasItem(DEFAULT_ID_TYPE.toString())))
            .andExpect(jsonPath("$.[*].idNumber").value(hasItem(DEFAULT_ID_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].flagNewsletter").value(hasItem(DEFAULT_FLAG_NEWSLETTER.toString())));
    }

    @Test
    @Transactional
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.birthDate").value(sameInstant(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.piva").value(DEFAULT_PIVA.toString()))
            .andExpect(jsonPath("$.cf").value(DEFAULT_CF.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.flagLegal").value(DEFAULT_FLAG_LEGAL.toString()))
            .andExpect(jsonPath("$.idType").value(DEFAULT_ID_TYPE.toString()))
            .andExpect(jsonPath("$.idNumber").value(DEFAULT_ID_NUMBER.toString()))
            .andExpect(jsonPath("$.flagNewsletter").value(DEFAULT_FLAG_NEWSLETTER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personService.save(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findOne(person.getId());
        // Disconnect from session so that the updates on updatedPerson are not directly saved in db
        em.detach(updatedPerson);
        updatedPerson
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .piva(UPDATED_PIVA)
            .cf(UPDATED_CF)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .flagLegal(UPDATED_FLAG_LEGAL)
            .idType(UPDATED_ID_TYPE)
            .idNumber(UPDATED_ID_NUMBER)
            .flagNewsletter(UPDATED_FLAG_NEWSLETTER);

        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerson)))
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPerson.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testPerson.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPerson.getPiva()).isEqualTo(UPDATED_PIVA);
        assertThat(testPerson.getCf()).isEqualTo(UPDATED_CF);
        assertThat(testPerson.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testPerson.getFlagLegal()).isEqualTo(UPDATED_FLAG_LEGAL);
        assertThat(testPerson.getIdType()).isEqualTo(UPDATED_ID_TYPE);
        assertThat(testPerson.getIdNumber()).isEqualTo(UPDATED_ID_NUMBER);
        assertThat(testPerson.getFlagNewsletter()).isEqualTo(UPDATED_FLAG_NEWSLETTER);
    }

    @Test
    @Transactional
    public void updateNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Create the Person

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personService.save(person);

        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Get the person
        restPersonMockMvc.perform(delete("/api/people/{id}", person.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Person.class);
        Person person1 = new Person();
        person1.setId(1L);
        Person person2 = new Person();
        person2.setId(person1.getId());
        assertThat(person1).isEqualTo(person2);
        person2.setId(2L);
        assertThat(person1).isNotEqualTo(person2);
        person1.setId(null);
        assertThat(person1).isNotEqualTo(person2);
    }
}
