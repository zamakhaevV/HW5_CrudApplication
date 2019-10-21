package com.sberbank.homework.crudspringboot.dto;

import lombok.Data;

@Data
public class PersonDto {

    private Long id;
    private String name;
    private int age;
    private String company;
}
