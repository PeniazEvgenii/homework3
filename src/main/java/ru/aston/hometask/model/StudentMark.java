package ru.aston.hometask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentMark {
    private Student student;
    private int mark;

    @Override
    public String toString() {
        return student.getFirstname() +
                " " + student.getLastname() +
                " - " + mark;
    }
}
