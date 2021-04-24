package com.test.beercatalogue.controller;

import com.test.beercatalogue.model.Beer;
import com.test.beercatalogue.model.Manufacturer;
import com.test.beercatalogue.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/manufacturer")
public class ManufacturerController {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @GetMapping(value = "/id/{id}", produces = "application/json")
    public Optional<Manufacturer> getById(@PathVariable Integer id) {
        return manufacturerRepository.findById(id);
    }

    @PostMapping(produces = "application/json")
    public Manufacturer add(@Valid @RequestBody  Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable Integer id) {
        manufacturerRepository.deleteById(id);
        return "done";
    }

    @PutMapping(produces = "application/json")
    public Manufacturer update(@Valid @RequestBody Manufacturer manufacturer) throws Exception {
        if (manufacturer.getId() == null) {
            throw new Exception("manufacturer id is null");
        }
        return manufacturerRepository.save(manufacturer);
    }

    @GetMapping(value = "/all", params = { "name", "page", "size" })
    public ResponseEntity<Map<String, Object>> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {

        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Manufacturer> manufacturerPage;
            if (name == null || name.isBlank())
                manufacturerPage = manufacturerRepository.findAll(paging);
            else
                manufacturerPage = manufacturerRepository.findByNameContaining(name, paging);
            Map<String, Object> response = new HashMap<>();
            response.put("manufacturers", manufacturerPage.getContent());
            response.put("currentPage", manufacturerPage.getNumber());
            response.put("totalItems", manufacturerPage.getTotalElements());
            response.put("totalPages", manufacturerPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
