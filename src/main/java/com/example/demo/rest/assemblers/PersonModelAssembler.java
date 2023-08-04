package com.example.demo.rest.assemblers;

import com.example.demo.rest.controllers.PersonController;
import com.example.demo.model.Person;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

@Service
public class PersonModelAssembler implements RepresentationModelAssembler<Person, EntityModel<Person>> {
    @Override
    public EntityModel<Person> toModel(Person entity) {
        try {
            return EntityModel.of(entity,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).getPersonById(entity.getId())).withSelfRel()
                    ,WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).getAll()).withRel("persons")
                    ,WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).delete(entity.getId())).withRel("delete")
                    ,WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PersonController.class).update(entity.getId(),entity)).withRel("update")
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
