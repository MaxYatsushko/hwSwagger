package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;

import java.util.List;


public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> getFacultiesByColor(String color);
    List<Faculty> findAllByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
}
