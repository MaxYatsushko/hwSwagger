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

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).get();
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

    public List<Student> getAll(int age){
        return studentRepository.findAllByAge(age);
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

    public int getNumberStudents(){
        return studentRepository.getNumberStudents();
    }

    public int getAvgAge(){
        return studentRepository.getAvgAge();
    }

    public List<Student> getLastFiveStudent(){
        return studentRepository.getLastFiveStudent();
    }
}
