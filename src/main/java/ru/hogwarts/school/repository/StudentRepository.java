package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByAgeBetween(int min, int max);
    List<Student> findAllByAge(int age);
    @Query(value = "select count(*) from Student", nativeQuery = true)
    int getNumberStudents();

    @Query(value = "select avg(age) from Student", nativeQuery = true)
    int getAvgAge();

    @Query(value = "select * from Student order by id desc limit 5", nativeQuery = true)
    List<Student> getLastFiveStudent();
}
