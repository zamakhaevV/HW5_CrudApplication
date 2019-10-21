package com.sberbank.homework.crudspringboot;

import com.sberbank.homework.crudspringboot.dto.PersonDto;
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
        PersonDto personDto = new PersonDto();
        personDto.setName("New person");
        personDto.setAge(35);
        personDto.setCompany("company3");

        ResponseEntity<String> response = restTemplate.postForEntity(
                getApiUrl(),
                personDto,
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

        PersonDto personDto = restTemplate.getForObject(
                String.format("%s/%d", getApiUrl(), id),
                PersonDto.class);

        personDto.setName("new name");
        personDto.setAge(100);

        restTemplate.put(
                String.format("%s/%d", getApiUrl(), id),
                personDto);

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

        PersonDto personDto = restTemplate.getForObject(
                String.format("%s/%d", getApiUrl(), id),
                PersonDto.class);

        assertNotNull(personDto);

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
