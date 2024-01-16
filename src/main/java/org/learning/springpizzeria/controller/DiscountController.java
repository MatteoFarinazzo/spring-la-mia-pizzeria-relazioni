package org.learning.springpizzeria.controller;

import org.learning.springpizzeria.model.Offerta;
import org.learning.springpizzeria.model.Pizza;
import org.learning.springpizzeria.repository.OffertaRepository;
import org.learning.springpizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            Pizza pizzaOffer = result.get();
            model.addAttribute("pizza", pizzaOffer);
            Offerta newOffer = new Offerta();
            model.addAttribute("offerta", newOffer);
            return "offerte/create";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pizza with id " + pizzaId + " not found");
        }
    }

    @PostMapping("/create")
    public String menu(Offerta formOfferta) {
        Offerta storedOffer = offertaRepository.save(formOfferta);
        return "redirect:/pizze/show/" + storedOffer.getPizza().getId();
    }


}
