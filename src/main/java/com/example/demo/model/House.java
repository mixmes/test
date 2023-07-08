package com.example.demo.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name  = "houses")
public class House {
    @Id
    @GeneratedValue
    private long id;
    private String address;
    @OneToMany
    private List<Person> tenants;

}
