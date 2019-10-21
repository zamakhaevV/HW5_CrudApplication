package com.sberbank.homework.crudspringboot.service;

import com.sberbank.homework.crudspringboot.dao.PersonRepository;
import com.sberbank.homework.crudspringboot.exception.PersonNotFoundException;
import com.sberbank.homework.crudspringboot.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person doesn't exist"));
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person updatedPerson) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person doesn't exist"));
        person.update(updatedPerson);
        return personRepository.save(person);
    }

    public void deletePersonById(Long id) {
        personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person doesn't exist"));
        personRepository.deleteById(id);
    }
}
