package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long>  {
    //@Query("select av from Avatar av where av.student.id = :id")
    Optional<Avatar> findByStudentId(Long id);
}
