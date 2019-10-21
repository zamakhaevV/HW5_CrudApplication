package com.sberbank.homework.crudspringboot.mapper;

import com.sberbank.homework.crudspringboot.dto.PersonDto;
import com.sberbank.homework.crudspringboot.model.Person;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Person toEntity(PersonDto personDto) {
        if (personDto == null) {
            return null;
        } else {
            return modelMapper.map(personDto, Person.class);
        }
    }

    public PersonDto toDto(Person person) {
        if (person == null) {
            return null;
        } else {
            return modelMapper.map(person, PersonDto.class);
        }
    }
}
