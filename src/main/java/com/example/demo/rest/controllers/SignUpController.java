package com.example.demo.rest.controllers;


import com.example.demo.config.WebSecurityConfig;
import com.example.demo.data.OwnerDataService;
import com.example.demo.data.PersonDataService;
import com.example.demo.model.Owner;
import com.example.demo.model.Person;
import com.example.demo.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;


@RestController
@Import(WebSecurityConfig.class)
public class SignUpController {
    Logger log = LogManager.getLogger();

    @Autowired
    private PersonDataService personDataService;
    @Autowired
    private OwnerDataService ownerDataService;
    @PostMapping("/signup")
    @ResponseStatus(code = HttpStatus.OK)
    public String signUpPerson(User user) {
        log.info("Starting registration");
        if(user.getRole().equals("USER")){
            Person person = new Person();
            person.setPassword(user.getPassword());
            person.setUsername(user.getUsername());
            person.setRole(user.getRole());
            person.setName("name");
            person.setAge((short) 20);
            personDataService.save(person);
        }
        else if(user.getRole().equals("ADMIN")) {
            Owner owner = new Owner();
            owner.setPassword(user.getPassword());
            owner.setUsername(user.getUsername());
            owner.setRole(user.getRole());
            ownerDataService.save(owner);
        }
        return "OK";
    }




}
