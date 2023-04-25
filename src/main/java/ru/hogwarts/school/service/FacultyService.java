package ru.hogwarts.school.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public void delete(long id){
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAll(){
        return facultyRepository.findAll();
    }

    public List<Faculty> getAll(String color){
        return facultyRepository.getFacultiesByColor(color);
    }

    public List<Faculty> getAll(String name, String color){
        return facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public List<Student> getStudents(Faculty faculty){
        return faculty.getStudents();
    }
    public Faculty update(Faculty faculty){
        return facultyRepository.save(faculty);
    }
}
