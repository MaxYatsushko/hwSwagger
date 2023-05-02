package ru.hogwarts.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface AvatarRepository extends PagingAndSortingRepository<Avatar, Long> {
    //@Query("select av from Avatar av where av.student.id = :id")
    Optional<Avatar> findByStudentId(Long id);

    Page<Avatar> findAll(Pageable pageable);
}
