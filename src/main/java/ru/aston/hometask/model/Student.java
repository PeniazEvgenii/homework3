package ru.aston.hometask.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private UUID id;
    private String firstname;
    private String lastname;
    private String birthDate;
}
