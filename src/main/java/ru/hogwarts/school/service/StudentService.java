package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(Student student){
        return studentRepository.save(student);
    }

    public void delete(long id){
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAll(){
        return studentRepository.findAll();
    }

    public Set<Student> getAll(int age){

        Set<Student> studentAge = new HashSet<>();
        Collection<Student> students = studentRepository.findAll();
        for (Student student: students)
            if (student.getAge() == age)
                studentAge.add(student);

        return studentAge;
    }

    public List<Student> getAll(int minAge, int maxAge){
        return studentRepository.findAllByAgeBetween(minAge, maxAge);
    }

    public Faculty getFaculty(Student student){
        return student.getFaculty();
    }

    public Student update(Student student){
        return studentRepository.save(student);
    }
}
