package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "owners")
public class Owner extends User{

    private String name;
    private String age;
    @OneToMany
    private List<House> houses = new ArrayList<>();
    public void deleteFromHouse(House house,Person person) throws Exception {
        if(houses.contains(house)){
            if (house.getTenants().contains(person)){
                house.getTenants().remove(person);
            }
            else {
                throw new Exception("No such person");
            }
        }
        else {
            throw new Exception("No such house");
        }
    }
    public void addToHouse(House house,Person person) throws Exception {
        if(houses.contains(house)){
            if (!house.getTenants().contains(person)){
                house.getTenants().add(person);
            }
            else {
                throw new Exception("such person already exists");
            }
        }
        else {
            throw new Exception("No such house");
        }
    }
    public void deleteHouse(House house) throws Exception {
        if(houses.contains(house)){
            houses.remove(house);
        }
        else {
            throw new Exception("No such house");
        }
    }
    public void addHouse(House house) throws Exception {
        if(houses.contains(house)){
            throw new Exception("Such house already exits");
        }
        else {
            houses.add(house);
        }
    }
}
