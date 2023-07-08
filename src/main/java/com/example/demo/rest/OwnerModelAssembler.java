package com.example.demo.rest;

import com.example.demo.controllers.OwnerController;
import com.example.demo.controllers.PersonController;
import com.example.demo.model.Owner;
import org.hibernate.EntityMode;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

@Service
public class OwnerModelAssembler implements RepresentationModelAssembler<Owner, EntityModel<Owner>> {
    @Override
    public EntityModel<Owner> toModel(Owner entity) {
       try {
            return EntityModel.of(entity,
                     WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OwnerController.class).getOwnerById(entity.getId())).withSelfRel()
                    ,WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OwnerController.class).getAll()).withRel("owners")
                    ,WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OwnerController.class).update(entity.getId(),entity)).withRel("update"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OwnerController.class).deleteById(entity.getId())).withRel("delete")

            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
