package com.test.beercatalogue.controller;

import com.test.beercatalogue.model.Beer;
import com.test.beercatalogue.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BeerRepository repository;

    @Test
    void getById() throws Exception {
        Beer beer = new Beer();
        beer.setId(1);
        beer.setName("estrella");
        given(repository.findById(eq(1))).willReturn(Optional.of(beer));
        mvc.perform(get("/api/beer/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(beer.getId())))
                .andExpect(jsonPath("$.name", equalTo(beer.getName())));
    }

    @Test
    void add() throws Exception {
        Beer beer = new Beer();
        beer.setId(1);
        beer.setName("estrella");
        given(repository.save(any(Beer.class))).willReturn(beer);
        mvc.perform(post("/api/beer").content("{\"name\":\"estrella\",\"nationality\":\"spanish\"}")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(beer.getId())))
                .andExpect(jsonPath("$.name", equalTo(beer.getName())));
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/beer/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo("done")));
    }

    @Test
    void update() throws Exception {
        Beer beer = new Beer();
        beer.setId(2);
        beer.setName("galicia");
        given(repository.save(any(Beer.class))).willReturn(beer);
        mvc.perform(put("/api/beer").content("{\"id\":2,\"name\":\"galicia\",\"nationality\":\"spanish\"}")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(beer.getId())))
                .andExpect(jsonPath("$.name", equalTo(beer.getName())));
    }

    @Test
    void getAll() throws Exception {
        Beer beer = new Beer();
        beer.setId(1);
        beer.setName("estrella");
        Page<Beer> beerPage = new PageImpl<>(List.of(beer), PageRequest.of(0, 1), 1);
        given(repository.findAll(any(Pageable.class))).willReturn(beerPage);
        mvc.perform(get("/api/beer/all?name=&page=0&size=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beers", hasSize(1)))
                .andExpect(jsonPath("$.totalItems", equalTo(1)))
                .andExpect(jsonPath("$.totalPages", equalTo(1)))
                .andExpect(jsonPath("$.currentPage", equalTo(0)));

    }
}