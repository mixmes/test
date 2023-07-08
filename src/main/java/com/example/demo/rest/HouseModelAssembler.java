package com.example.demo.rest;

import com.example.demo.controllers.HouseController;
import com.example.demo.model.House;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
@Service
public class HouseModelAssembler implements RepresentationModelAssembler<House, EntityModel<House>> {
    @Override
    public EntityModel<House> toModel(House entity) {
        try {
            return EntityModel.of(entity,
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HouseController.class).getHouseById(entity.getId())).withSelfRel()
                    ,WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HouseController.class).getAll()).withRel("houses")
                    ,WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HouseController.class).deleteHouse(entity.getId())).withRel("delete")
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
