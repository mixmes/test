package com.example.demo.repositories;

import com.example.demo.model.House;
import com.example.demo.model.Owner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends CrudRepository<Owner,Long> {
    public Optional<Owner> findById(long id);
    public List<Owner> findAll();
    public void deleteById(long id);

}
