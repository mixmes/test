package com.example.demo.controllers;

import com.example.demo.data.PersonDataService;
import com.example.demo.model.House;
import com.example.demo.model.Person;
import com.example.demo.rest.PersonModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PersonController {
    @Autowired
    PersonDataService dataService;
    @Autowired
    PersonModelAssembler modelAssembler;
    @GetMapping("/person")
    public EntityModel<Person> getPersonById(@RequestParam(name = "id") long id) throws Exception {
        Optional<Person> person = dataService.findById(id);
        if(!person.isPresent()){
            throw new Exception("No such person");
        }
        else {
           return modelAssembler.toModel(person.get());
        }
    }
    @GetMapping("/persons/all")
    public CollectionModel<EntityModel<Person>> getAll(){
        List<EntityModel<Person>> house = dataService.getAll().stream().map(modelAssembler::toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(house, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).getAll()).withSelfRel());
    }
    @PostMapping("/persons/update")
    public EntityModel<Person> update(@RequestParam(name = "id") long id,Person person) throws Exception {
        if(!dataService.findById(id).isPresent()){
            throw new Exception("No such person");
        }
        else {
            dataService.update(person);
            return modelAssembler.toModel(person);
        }
    }
    @DeleteMapping("/persons")
    public EntityModel<String> delete(@RequestParam(name="id")long id) throws Exception {
        if(!dataService.findById(id).isPresent()){
            throw new Exception("No such person");
        }
        else {
            dataService.delete(id);
            return EntityModel.of("Deleted");
        }
    }

}
