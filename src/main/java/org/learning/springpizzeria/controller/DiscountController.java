package org.learning.springpizzeria.controller;

import jakarta.validation.Valid;
import org.learning.springpizzeria.model.Offerta;
import org.learning.springpizzeria.model.Pizza;
import org.learning.springpizzeria.repository.OffertaRepository;
import org.learning.springpizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/offerte")


public class DiscountController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OffertaRepository offertaRepository;

    @GetMapping("/create")
    public String offer(@RequestParam(name = "pizzaId", required = true) Integer pizzaId, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            Pizza pizza = result.get();
            model.addAttribute("pizza", pizza);
            Offerta newOffer = new Offerta();
            newOffer.setPizza(pizza);
            model.addAttribute("offerta", newOffer);
            return "offerte/create";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pizza with id " + pizzaId + " not found");
        }
    }

    @PostMapping("/create")
    public String menu(@Valid @ModelAttribute("offerta") Offerta formOfferta, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pizza", formOfferta.getPizza());
            return "offerte/create";
        }
        if (formOfferta.getEndDate() != null && formOfferta.getEndDate().isBefore(formOfferta.getStartDate())) {
            formOfferta.setEndDate(formOfferta.getStartDate());
        }
        Offerta storedOffer = offertaRepository.save(formOfferta);
        return "redirect:/pizze/show/" + storedOffer.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Offerta> result = offertaRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("offerta", result.get());
            return "offerte/edit";

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "L'offerta con id " + id + " non è stata trovata");
        }

    }

    @PostMapping("edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("offerta") Offerta formOfferta, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "offerte/edit";
        }

        Offerta updatedOfferta = offertaRepository.save(formOfferta);
        return "redirect:/pizze/show/" + updatedOfferta.getPizza().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<Offerta> result = offertaRepository.findById(id);
        if (result.isPresent()) {
            Offerta offertaToDelete = result.get();
            offertaRepository.delete(offertaToDelete);
            return "redirect:/pizze/show/" + offertaToDelete.getPizza().getId();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "L'offerta con id " + id + " non è stata trovata");
        }
    }


}
