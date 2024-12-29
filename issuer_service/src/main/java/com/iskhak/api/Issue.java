package com.iskhak.api;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Issue {

    private UUID id;
    private LocalDate issueAt;
    private UUID bookId;
    private UUID readerId;

}
