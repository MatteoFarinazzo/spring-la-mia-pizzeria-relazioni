package org.learning.springpizzeria.repository;

import org.learning.springpizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    //aggiungo un metodo per cercare Pizza per nome
    List<Pizza> findByNameContaining(String search);
}
