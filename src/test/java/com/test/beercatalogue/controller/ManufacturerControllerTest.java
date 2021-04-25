package com.test.beercatalogue.controller;

import com.test.beercatalogue.model.Manufacturer;
import com.test.beercatalogue.repository.ManufacturerRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManufacturerController.class)
class ManufacturerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ManufacturerRepository repository;

    @Test
    void getById() throws Exception {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1);
        manufacturer.setName("estrella");
        given(repository.findById(eq(1))).willReturn(Optional.of(manufacturer));
        mvc.perform(get("/api/manufacturer/id/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(manufacturer.getId())))
                .andExpect(jsonPath("$.name", equalTo(manufacturer.getName())));
    }

    @Test
    void add() throws Exception {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1);
        manufacturer.setName("estrella");
        given(repository.save(any(Manufacturer.class))).willReturn(manufacturer);
        mvc.perform(post("/api/manufacturer").content("{\"name\":\"estrella\",\"nationality\":\"spanish\"}")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                //.andDo(print())
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.id", equalTo(manufacturer.getId())))
                .andExpect(jsonPath("$.name", equalTo(manufacturer.getName())));
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/manufacturer/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo("done")));
    }

    @Test
    void update() throws Exception {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(2);
        manufacturer.setName("galicia");
        given(repository.save(any(Manufacturer.class))).willReturn(manufacturer);
        mvc.perform(put("/api/manufacturer").content("{\"id\":2,\"name\":\"galicia\",\"nationality\":\"spanish\"}")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(manufacturer.getId())))
                .andExpect(jsonPath("$.name", equalTo(manufacturer.getName())));
    }

    @Test
    void getAll() throws Exception {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(1);
        manufacturer.setName("estrella");
        Page<Manufacturer> manufacturerPage = new PageImpl<>(List.of(manufacturer), PageRequest.of(0, 1), 1);
        given(repository.findAll(any(Pageable.class))).willReturn(manufacturerPage);
        mvc.perform(get("/api/manufacturer/all?name=&page=0&size=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.manufacturers", hasSize(1)))
                .andExpect(jsonPath("$.totalItems", equalTo(1)))
                .andExpect(jsonPath("$.totalPages", equalTo(1)))
                .andExpect(jsonPath("$.currentPage", equalTo(0)));

    }
}