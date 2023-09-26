package org.exercise.java.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.exercise.java.springlamiapizzeriacrud.model.Pizza;
import org.exercise.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(@RequestParam(name = "keyword") Optional<String> searchKeyword, Model model) {
        List<Pizza> pizzaList;
        String keyword = "";
        if (searchKeyword.isPresent()) {
            keyword = searchKeyword.get();
            pizzaList = pizzaRepository.findByNameContainingOrDescriptionContaining(keyword, keyword);
        } else {
            pizzaList = pizzaRepository.findAll();
        }
        model.addAttribute("pizzas", pizzaList);
        model.addAttribute("keyword", keyword);
        return "/list";
    }


    @GetMapping("/show/{pizzaId}")
    public String show(@PathVariable("pizzaId") Integer id, Model model) {
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isPresent()) {
            Pizza pizzaFromDb = pizzaOptional.get();
            model.addAttribute("pizza", pizzaFromDb);
            return "/details";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("nuovaPizza", new Pizza());
        return "/form";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("nuovaPizza") Pizza formPizza, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/form";
        }
        pizzaRepository.save(formPizza);
        return "redirect:/pizza";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("pizza", result.get());
            return "/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/edit";
        }
        pizzaRepository.save(formPizza);
        return "redirect:/pizza";
    }

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id) {
        pizzaRepository.deleteById(id);
        return "redirect:/pizza";
    }


}
