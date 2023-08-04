package com.example.demo.rest.controllers;

import com.example.demo.data.HouseDataService;
import com.example.demo.model.House;
import com.example.demo.rest.assemblers.HouseModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class HouseController {
    @Autowired
    HouseDataService dataService;
    @Autowired
    HouseModelAssembler modelAssembler;
    @GetMapping("/houses")
    public EntityModel<House> getHouseById(@RequestParam(name = "id") long id) throws Exception {
        Optional<House> house = dataService.getHouseById(id);
        if(!house.isPresent()){
            throw new Exception("House wasn't found");
        }
        else {
            return modelAssembler.toModel(house.get());
        }
    }
    @GetMapping("/houses/all")
    public CollectionModel<EntityModel<House>> getAll(){
        List<EntityModel<House>> houses = dataService.getAll().stream().map(modelAssembler::toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(houses, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HouseController.class).getAll()).withSelfRel());
    }
    @DeleteMapping("/houses")
    public EntityModel<String> deleteHouse(@RequestParam(name = "id") long id) throws Exception {
        if(!dataService.getHouseById(id).isPresent()){
            throw new Exception("House wasn't found");
        }else {
            dataService.delete(id);
            return EntityModel.of("Deleted");
        }
    }
}
