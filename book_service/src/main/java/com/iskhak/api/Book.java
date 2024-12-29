package com.iskhak.api;

import lombok.Data;

import java.util.UUID;

@Data
public class Book {

    private UUID id;
    private Author author;
    private String name;

}
