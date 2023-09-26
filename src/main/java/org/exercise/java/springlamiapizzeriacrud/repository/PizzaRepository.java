package org.exercise.java.springlamiapizzeriacrud.repository;

import org.exercise.java.springlamiapizzeriacrud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    List<Pizza> findByNameContainingOrDescriptionContaining(String name, String description);
}
