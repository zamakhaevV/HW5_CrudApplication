package com.sberbank.homework.crudspringboot;

import com.sberbank.homework.crudspringboot.model.Person;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrudSpringBootApplication.class,
                webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CrudSpringBootApplicationIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getApiUrl() {
        return String.format("http://localhost:%d/persons", port);
    }


    @Test
    public void shouldGetAllPersons() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                getApiUrl(),
                HttpMethod.GET,
                entity,
                String.class);

        String actualResponse = "[" +
                "{\"id\":1,\"name\":\"John\",\"age\":23,\"company\":\"company1\"}," +
                "{\"id\":2,\"name\":\"Steve\",\"age\":30,\"company\":\"company2\"}," +
                "{\"id\":3,\"name\":\"Ann\",\"age\":41,\"company\":\"company2\"}," +
                "{\"id\":4,\"name\":\"Kira\",\"age\":25,\"company\":\"company1\"}," +
                "{\"id\":5,\"name\":\"Leo\",\"age\":28,\"company\":\"company2\"}" +
                "]";
        String expectedResponse = response.getBody();

        assertNotNull(expectedResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldGetPersonById() {
        int id = 2;

        ResponseEntity<String> response = restTemplate.getForEntity(
                String.format("%s/%d", getApiUrl(), id),
                String.class);

        assertNotNull(response);

        String actualResponse = "{\"id\":2,\"name\":\"Steve\",\"age\":30,\"company\":\"company2\"}";
        String expectedResponse = response.getBody();

        assertNotNull(expectedResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldCreatePerson() {
        Person person = new Person();
        person.setName("New person");
        person.setAge(35);
        person.setCompany("company3");

        ResponseEntity<String> response = restTemplate.postForEntity(
                getApiUrl(),
                person,
                String.class);


        assertNotNull(response);

        String actualResponse = "{\"id\":6,\"name\":\"New person\",\"age\":35,\"company\":\"company3\"}";
        String expectedResponse = response.getBody();

        assertNotNull(expectedResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldUpdatePersonById() {
        int id = 3;

        Person person = restTemplate.getForObject(
                String.format("%s/%d", getApiUrl(), id),
                Person.class);

        person.setName("new name");
        person.setAge(100);

        restTemplate.put(
                String.format("%s/%d", getApiUrl(), id),
                person);

        ResponseEntity<String> response = restTemplate.getForEntity(
                String.format("%s/%d", getApiUrl(), id),
                String.class);

        assertNotNull(response);

        String actualResponse = "{\"id\":3,\"name\":\"new name\",\"age\":100,\"company\":\"company2\"}";
        String expectedResponse = response.getBody();

        assertNotNull(expectedResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldDeletePersonById() {
        int id = 4;

        Person person = restTemplate.getForObject(
                String.format("%s/%d", getApiUrl(), id),
                Person.class);

        assertNotNull(person);

        restTemplate.delete(String.format("%s/%d", getApiUrl(), id));

        ResponseEntity<String> response = restTemplate.getForEntity(
                    String.format("%s/%d", getApiUrl(), id),
                    String.class);

        assertNotNull(response);

        String actualResponse = "Person doesn't exist";
        String expectedResponse = response.getBody();

        assertNotNull(expectedResponse);
        assertEquals(expectedResponse, actualResponse);
    }
}
