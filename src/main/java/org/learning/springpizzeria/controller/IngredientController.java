package org.learning.springpizzeria.controller;

import jakarta.validation.Valid;
import org.learning.springpizzeria.model.Ingredient;
import org.learning.springpizzeria.model.Pizza;
import org.learning.springpizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("ingredientsList", ingredientRepository.findAll());
        return "ingredients/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("formIngredient", new Ingredient());
        return "ingredients/create";
    }

    @PostMapping("/create")
    public String ingrediente(@Valid @ModelAttribute("formIngredient") Ingredient formIngredient, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredienti", ingredientRepository.findAll());
            return "ingredients/create";
        } else {
            Ingredient saved = ingredientRepository.save(formIngredient);
            return "redirect:/ingredients";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Ingredient> result = ingredientRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("ingredient", result.get());
            return "ingredients/create";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with id" + id + "not found");
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult) {

        Optional<Ingredient> result = ingredientRepository.findById(id);
        if (result.isPresent()) {
            Ingredient ingredientToEdit = result.get();
            if (bindingResult.hasErrors()) {
                return "ingredients/create";
            }

            Ingredient savedIngredient = ingredientRepository.save(formIngredient);
            return "redirect:/ingredients";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with id" + id + "not found");
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Ingredient> result = ingredientRepository.findById(id);
        if (result.isPresent()) {
            ingredientRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("redirectMessage", "l'ingrediente " + result.get().getName() + " è stato eliminato");
            return "redirect:/ingredients";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "L'ingrediente con id: " + id + "non è stato trovato");
        }
    }
}
