package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "persons")
public class Person extends User{

    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private short age;


}
