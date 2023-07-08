package com.example.demo.repositories;

import com.example.demo.model.House;
import com.example.demo.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseRepository extends CrudRepository<House,Long> {
    public Optional<House> findById(long id);
    public List<House> findAll();
    public Optional<House> findByAddress(String address);
    public void deleteById(long id);


}
