package com.sberbank.homework.crudspringboot.contoller;

import com.sberbank.homework.crudspringboot.dto.PersonDto;
import com.sberbank.homework.crudspringboot.mapper.PersonMapper;
import com.sberbank.homework.crudspringboot.model.Person;
import com.sberbank.homework.crudspringboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PersonController {

    private PersonService personService;
    private PersonMapper personMapper;

    @Autowired
    public PersonController(PersonService personService,
                            PersonMapper personMapper) {
        this.personService = personService;
        this.personMapper = personMapper;
    }

    @GetMapping("/persons")
    public List<PersonDto> getAllPersons() {
        List<Person> personIterable = personService.getAllPerson();
        return personIterable
                .stream()
                .map(person -> personMapper.toDto(person))
                .collect(Collectors.toList());
    }

    @GetMapping("/persons/{id}")
    public PersonDto getPersonById(@PathVariable Long id) {
        return personMapper.toDto(personService.getPersonById(id));
    }

    @PostMapping("/persons")
    public PersonDto createPerson(@Valid @RequestBody PersonDto personDto) {
        Person person = personMapper.toEntity(personDto);
        return personMapper.toDto(
                personService.createPerson(person)
        );
    }

    @PutMapping("/persons/{id}")
    public PersonDto updatePerson(@PathVariable Long id,
                               @Valid @RequestBody PersonDto updatedPersonDto) {
        return personMapper.toDto(
                personService.updatePerson(id, personMapper.toEntity(updatedPersonDto))
        );
    }

    @DeleteMapping("/persons/{id}")
    public void deletePersonById(@PathVariable Long id) {
        personService.deletePersonById(id);
    }

}
