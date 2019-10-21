package com.sberbank.homework.crudspringboot.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "company", nullable = false)
    private String company;

    public void update(Person person) {
        this.name = person.name;
        this.company = person.company;
        this.age = person.age;
    }
}
