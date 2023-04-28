package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByAgeBetween(int min, int max);
    List<Student> findAllByAge(int age);
}
