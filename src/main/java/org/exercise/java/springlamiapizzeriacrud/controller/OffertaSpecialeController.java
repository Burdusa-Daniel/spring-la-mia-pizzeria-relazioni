package org.exercise.java.springlamiapizzeriacrud.controller;

import org.exercise.java.springlamiapizzeriacrud.model.OffertaSpeciale;
import org.exercise.java.springlamiapizzeriacrud.model.Pizza;
import org.exercise.java.springlamiapizzeriacrud.repository.OffertaSpecialeRepository;
import org.exercise.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offerteSpeciali")
public class OffertaSpecialeController {
    @Autowired
    private OffertaSpecialeRepository offertaSpecialeRepository;
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping("/create")
    public String create(@RequestParam("pizzaId") Integer pizzaId, Model model) {
        Optional<Pizza> pizzaResult = pizzaRepository.findById(pizzaId);
        if (pizzaResult.isPresent()) {
            Pizza pizza = pizzaResult.get();
            OffertaSpeciale offertaSpeciale = new OffertaSpeciale();
            offertaSpeciale.setPizza(pizza);
            offertaSpeciale.setDataInizio(LocalDate.now());
            offertaSpeciale.setDataFine(LocalDate.now().plusDays(30));
            model.addAttribute("offertaSpeciale", offertaSpeciale);
            return "offerteSpeciali/form";
        } else {
            // se non esiste sollevo un'eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "pizza with id " + pizzaId + " not found");
        }
    }

    @PostMapping("/create")
    public String doCreate(@ModelAttribute("offertaSpeciale") OffertaSpeciale offertaSpecialeForm) {
        offertaSpecialeRepository.save(offertaSpecialeForm);
        return "redirect:/pizza/show/" + offertaSpecialeForm.getPizza().getId();
    }

    @GetMapping("/edit/{offertaSpecialeId}")
    public String edit(@PathVariable("offertaSpecialeId") Integer id, Model model) {
        Optional<OffertaSpeciale> offertaSpecialeResult = offertaSpecialeRepository.findById(id);
        if (offertaSpecialeResult.isPresent()) {
            model.addAttribute("offertaSpeciale", offertaSpecialeResult.get());
            return "/offerteSpeciali/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/edit/{offertaSpecialeId}")
    public String doEdit(@PathVariable("offertaSpecialeId") Integer offertaSpecialeId, @ModelAttribute("offertaSpeciale") OffertaSpeciale offertaSpecialeForm) {
        offertaSpecialeForm.setId(offertaSpecialeId);
        offertaSpecialeRepository.save(offertaSpecialeForm);
        return "redirect:/pizza/show/" + offertaSpecialeForm.getPizza().getId();
    }

    @PostMapping("/delete/{offertaSpecialeId}")
    public String delete(@PathVariable("offertaSpecialeId") Integer id) {
        Optional<OffertaSpeciale> offertaSpecialeResult = offertaSpecialeRepository.findById(id);
        if (offertaSpecialeResult.isPresent()) {
            Integer pizzaId = offertaSpecialeResult.get().getPizza().getId();
            offertaSpecialeRepository.deleteById(id);
            return "redirect:/pizza/show/" + pizzaId;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
