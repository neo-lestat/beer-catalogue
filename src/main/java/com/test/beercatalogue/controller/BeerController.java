package com.test.beercatalogue.controller;

import com.test.beercatalogue.model.Beer;
import com.test.beercatalogue.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/beer")
public class BeerController {

    @Autowired
    private BeerRepository beerRepository;

    @GetMapping(value = "/{id}", produces = "application/json")
    public Optional<Beer> getById(@PathVariable Integer id) {
        return beerRepository.findById(id);
    }

    @PostMapping(produces = "application/json")
    public Beer add(@Valid @RequestBody Beer beer) {
        return beerRepository.save(beer);
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable Integer id) {
        beerRepository.deleteById(id);
        return "done";
    }

    @PutMapping(produces = "application/json")
    public Beer update(@Valid @RequestBody Beer beer) throws Exception {
        if (beer.getId() == null) {
            throw new Exception("beer id is null");
        }
        return beerRepository.save(beer);
    }

    @GetMapping(value = "/all", params = { "name", "page", "size" })
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {

        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Beer> beerPage;
            if (name == null || name.isBlank())
                beerPage = beerRepository.findAll(paging);
            else
                beerPage = beerRepository.findByNameContaining(name, paging);
            Map<String, Object> response = new HashMap<>();
            response.put("beers", beerPage.getContent());
            response.put("currentPage", beerPage.getNumber());
            response.put("totalItems", beerPage.getTotalElements());
            response.put("totalPages", beerPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
