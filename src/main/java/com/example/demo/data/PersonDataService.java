package com.example.demo.data;

import com.example.demo.model.Person;
import com.example.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonDataService {
    @Autowired
    PersonRepository personRepository;
    public Optional<Person> findById(long id){
        return personRepository.findById(id);
    }
    public Optional<Person> findByName(String name){
        return personRepository.findByName(name);
    }

    public void save(Person person){
        personRepository.save(person);
    }
    public List<Person> getAll(){
        return  personRepository.findAll();
    }
    public void update(Person person){
        personRepository.save(person);
    }
    public void delete(long id){
        personRepository.deleteById(id);
    }
}
