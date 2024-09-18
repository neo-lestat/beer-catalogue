package com.test.beercatalogue.repository;

import com.test.beercatalogue.model.Manufacturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
    Page<Manufacturer> findByNameContaining(String name, Pageable pageable);
}
