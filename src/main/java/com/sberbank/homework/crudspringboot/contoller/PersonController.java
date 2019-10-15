package com.sberbank.homework.crudspringboot.contoller;

import com.sberbank.homework.crudspringboot.dao.PersonRepository;
import com.sberbank.homework.crudspringboot.exception.PersonNotFoundException;
import com.sberbank.homework.crudspringboot.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PersonController {

    private PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/persons")
    public Iterable getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/persons/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person doesn't exist"));
    }

    @PostMapping("/persons")
    public Person createPerson(@Valid @RequestBody Person person) {
        return personRepository.save(person);
    }

    @PutMapping("/persons/{id}")
    public Person updatePerson(@PathVariable Long id,
                               @Valid @RequestBody Person updatedPerson) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person doesn't exist"));
        person.update(updatedPerson);
        return personRepository.save(person);
    }

    @DeleteMapping("/persons/{id}")
    public void deletePersonById(@PathVariable Long id) {
        personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person doesn't exist"));
        personRepository.deleteById(id);
    }

}
