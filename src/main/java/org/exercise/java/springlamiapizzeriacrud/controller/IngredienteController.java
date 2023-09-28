package org.exercise.java.springlamiapizzeriacrud.controller;

import org.exercise.java.springlamiapizzeriacrud.model.Ingrediente;
import org.exercise.java.springlamiapizzeriacrud.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ingredienti")
public class IngredienteController {
    @Autowired
    private IngredienteRepository ingredienteRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("ingredienteList", ingredienteRepository.findAll());
        model.addAttribute("ingredienteObj", new Ingrediente());
        return "ingredienti/index";
    }

    @PostMapping("/create")
    public String doCreate(@ModelAttribute("ingredientiObj") Ingrediente ingredienteForm) {
        ingredienteRepository.save(ingredienteForm);
        return "redirect:/ingredienti";
    }

    @PostMapping("/delete/{ingId}")
    public String delete(@PathVariable("ingId") Integer id) {
        ingredienteRepository.deleteById(id);
        return "redirect:/ingredienti";
    }
}
