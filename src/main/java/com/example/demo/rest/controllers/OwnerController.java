package com.example.demo.rest.controllers;

import com.example.demo.data.HouseDataService;
import com.example.demo.data.OwnerDataService;
import com.example.demo.data.PersonDataService;
import com.example.demo.model.House;
import com.example.demo.model.Owner;
import com.example.demo.model.Person;
import com.example.demo.rest.assemblers.HouseModelAssembler;
import com.example.demo.rest.assemblers.OwnerModelAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@RestController
public class OwnerController {
    @Autowired
    OwnerDataService dataService;
    @Autowired
    HouseDataService houseDataService;
    @Autowired
    OwnerModelAssembler modelAssembler;
    @Autowired
    PersonDataService personDataService;
    @Autowired
    HouseModelAssembler houseModelAssembler;
    @GetMapping("/owner")
    public EntityModel<Owner> getOwnerById(@RequestParam(name = "id") long id) throws Exception {
        Optional<Owner> owner = dataService.getById(id);
        if(!owner.isPresent()){
            throw new Exception("No such owner");
        }
        else {
            return modelAssembler.toModel(owner.get());
        }
    }
    @GetMapping("/owner/all")
    public CollectionModel<EntityModel<Owner>> getAll(){
        List<EntityModel<Owner>> owners = dataService.findAll().stream().map(modelAssembler::toModel) //
                .collect(Collectors.toList());
        return CollectionModel.of(owners, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OwnerController.class).getAll()).withSelfRel());
    }
    @PostMapping("/owner/update")
    public EntityModel<Owner> update(@RequestParam(name ="id") long id,Owner owner) throws Exception {
        if(!dataService.getById(id).isPresent()){
            throw new Exception("Owner doesn't exist");
        }
        else {
            dataService.update(owner);
            return modelAssembler.toModel(owner);
        }
    }
    @DeleteMapping("/owner/{id}")
    public EntityModel<String> deleteById(@PathVariable(name = "id") long id) throws Exception {
        log.info("Deleting");
        if(!dataService.getById(id).isPresent()){
            throw new Exception("Owner doesn't exist");
        }
        else {
            dataService.deleteById(id);
            return EntityModel.of("Deleted");
        }
    }
    @GetMapping("owner/houses")
    public CollectionModel<EntityModel<House>> getAllHomesByOwnerId(@RequestParam(name= "id") long id) throws Exception {
        if(!dataService.getById(id).isPresent()){
            throw new Exception("Owner doesn't exist");
        }
        else {
            List<EntityModel<House>> houses = houseDataService.getAll().stream().map(houseModelAssembler::toModel) //
                    .collect(Collectors.toList());
            return CollectionModel.of(houses, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HouseController.class).getAll()).withSelfRel());
        }
    }
    @PutMapping("/owner/{id}/houses/")
    public void addHouse(@PathVariable(name="id") long id,House house) throws Exception {
        Optional<Owner> owner = dataService.getById(id);
        if(!owner.isPresent()){
            throw new Exception("No such owner");
        }
        else {
            owner.get().addHouse(house);
            dataService.update(owner.get());
        }
    }
    @DeleteMapping("/owner/{id}/houses/{house_id}")
    public void deleteHouse(@PathVariable(name="id") long id,@PathVariable(name="house_id") long house_id) throws Exception {
        Optional<Owner> owner = dataService.getById(id);
        Optional<House> house = houseDataService.getHouseById(house_id);
        if(!owner.isPresent()){
            throw new Exception("No such owner");
        }
        else {
            if(house.isPresent()){
                owner.get().deleteHouse(house.get());
                dataService.update(owner.get());
            }
            else {
                throw new Exception("No such house");
            }
        }
    }
    @DeleteMapping("/owner/{id}/houses/{house_id}/tenants/{tenant_id}")
    public void deleteFromHouse(@PathVariable(name="id") long id,@PathVariable(name="house_id")long houseId,@PathVariable(name="tenant_id") long tenantId) throws Exception {
        Optional<Owner> owner = dataService.getById(id);
        Optional<Person> person = personDataService.findById(tenantId);
        Optional<House> house = houseDataService.getHouseById(houseId);
        if(owner.isPresent() && person.isPresent() && house.isPresent()){
            owner.get().deleteFromHouse(house.get(),person.get());
            dataService.update(owner.get());
        }
        else {
            throw new Exception("Bad id");
        }
    }
    @PutMapping("/owner/{id}/houses/{house_id}/tenants/{tenant_id}")
    public void addToHouse(@PathVariable(name="id") long id,@PathVariable(name="house_id")long houseId,@PathVariable(name="tenant_id") long tenantId) throws Exception {
        Optional<Owner> owner = dataService.getById(id);
        Optional<Person> person = personDataService.findById(tenantId);
        Optional<House> house = houseDataService.getHouseById(houseId);
        if(owner.isPresent() && person.isPresent() && house.isPresent()){
            owner.get().addToHouse(house.get(),person.get());
            dataService.update(owner.get());
        }
        else {
            throw new Exception("Bad id");
        }
    }
}
