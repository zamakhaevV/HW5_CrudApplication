package com.sberbank.homework.crudspringboot.dao;

import com.sberbank.homework.crudspringboot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
