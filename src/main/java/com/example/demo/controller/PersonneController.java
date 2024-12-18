package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.PersonneRepository;
import com.example.demo.model.Personne;

import java.util.List;
import java.util.Optional;

@Controller
public class PersonneController {
    @Autowired
    private PersonneRepository personneRepository;

    // Afficher le formulaire pour ajouter une personne
    @GetMapping("addPersonne")
    public String addPersonne() {
        return "addPersonne"; 
    }

    // Ajouter une personne dans la base de données
    @PostMapping("addPersonne")
    public String addPersonne(@RequestParam String nom,
                              @RequestParam String prenom,
                              Model model) {
        Personne personne = new Personne();
        personne.setNom(nom);
        personne.setPrenom(prenom);
        personneRepository.save(personne);
        model.addAttribute("nom", nom);
        model.addAttribute("prenom", prenom);
        return "confirm";
    }

    // Afficher la liste des personnes
    @GetMapping("showPersonnes")
    public String showPersonne(Model model) {
        model.addAttribute("personnes", personneRepository.findAll());
        return "showPersonnes";
    }

    // Supprimer une personne par ID
    @GetMapping("deletePersonne/{id}")
    public String deletePersonne(@PathVariable Long id) {
        personneRepository.deleteById(id);
        return "showPersonnes";
    }

    // Afficher le formulaire pour modifier une personne
    @GetMapping("editPersonne/{id}")
    public String editPersonne(@PathVariable Long id, Model model) {
        Personne personneOpt = personneRepository.findById(id).get();
        model.addAttribute("personne", personneOpt);
        return "editPersonne"; 
    }

    // Enregistrer les modifications pour une personne
    @PostMapping("editPersonne")
    public String saveEditedPersonne(@RequestParam Long id,
                                     @RequestParam String nom,
                                     @RequestParam String prenom) {
        Optional<Personne> personneOpt = personneRepository.findById(id);
        if (personneOpt.isPresent()) {
            Personne personne = personneOpt.get();
            personne.setNom(nom);
            personne.setPrenom(prenom);
            personneRepository.save(personne);
        }
        return "showPersonnes";
    }
 // recupere nombre de personne par page 
    @GetMapping(value = "/showAllByPage/{page}/{size}")
    public ModelAndView showAllByPage(@PathVariable int page, 
    @PathVariable int size) {
    Page<Personne> personnes = personneRepository.findAll(PageRequest.of(
    page, size));
    ModelAndView mv = new ModelAndView(); 
    mv.setViewName("showPersonnes"); 
    mv.addObject("personnes", personnes.getContent()); 
    return mv;
    }
 // Recuper les personnes ordonnes
    @GetMapping(value = "/showAllSorted") 
    public ModelAndView showAllSorted() {
    List<Personne> personnes = personneRepository.findAll( 
    Sort.by("nom").descending());
    ModelAndView mv = new ModelAndView();
    mv.setViewName("showPersonnes"); 
    mv.addObject("personnes", personnes); 
    return mv;
    }
}
