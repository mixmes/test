package com.example.demo.repositories;

import com.example.demo.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
@Repository
public interface PersonRepository extends CrudRepository<Person,Long> {
    public Optional<Person> findByName(String name);
    public Optional<Person> findById(Long id);
    public List<Person> findAll();
    public void deleteById(long id);
}
