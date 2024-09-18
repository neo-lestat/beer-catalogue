package com.test.beercatalogue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.beercatalogue.model.Beer;
import com.test.beercatalogue.model.Manufacturer;
import com.test.beercatalogue.repository.BeerRepository;
import com.test.beercatalogue.repository.ManufacturerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BeerControllerIntegrationTest {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private BeerRepository beerRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    public void setUp() throws Exception {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("estrella");
        manufacturer.setNationality("spanish");
        manufacturer = manufacturerRepository.save(manufacturer);
        Beer beer = new Beer();
        beer.setName("estrella");
        beer.setDescription("beer ale");
        beer.setBeerType("ale");
        beer.setGraduation(0.4f);
        beer.setManufacturer(manufacturer);
        beerRepository.save(beer);
        beer = new Beer();
        beer.setName("cometa");
        beer.setDescription("beer pale");
        beer.setBeerType("pale");
        beer.setGraduation(0.5f);
        beer.setManufacturer(manufacturer);
        beerRepository.save(beer);
    }


    @Test
    void getAll() throws Exception {

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/beer/all?name=&page=0&size=5",
                String.class);
        assertThat(response.getStatusCode().value(), is(200));
        Map<String, Object> objectMap = mapper.readValue(response.getBody(), Map.class);
        assertThat(objectMap.get("currentPage"), is(0));
        assertThat(objectMap.get("totalItems"), is(2));
        assertThat(objectMap.get("totalPages"), is(1));
        assertThat(objectMap.get("beers"), notNullValue());
    }
}