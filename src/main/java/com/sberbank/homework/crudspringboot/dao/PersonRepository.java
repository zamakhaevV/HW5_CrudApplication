package com.sberbank.homework.crudspringboot.dao;

import com.sberbank.homework.crudspringboot.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

}
