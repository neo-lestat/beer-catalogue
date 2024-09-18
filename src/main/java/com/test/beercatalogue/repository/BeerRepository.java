package com.test.beercatalogue.repository;

import com.test.beercatalogue.model.Beer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface BeerRepository extends JpaRepository<Beer, Integer> {
    Page<Beer> findByNameContaining(String name, Pageable pageable);
}
