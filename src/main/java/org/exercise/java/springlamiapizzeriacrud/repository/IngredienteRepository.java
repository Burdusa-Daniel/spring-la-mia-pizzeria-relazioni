package org.exercise.java.springlamiapizzeriacrud.repository;

import org.exercise.java.springlamiapizzeriacrud.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository  extends JpaRepository<Ingrediente,Integer> {

}
