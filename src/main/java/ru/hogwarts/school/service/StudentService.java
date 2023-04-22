package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {

    private long idCount = 0;
    private Map<Long, Student> students = new HashMap<>();

    public Student add(String name, int age){
        long id = idCount++;
        Student student = new Student(id, name, age);
        students.put(id, student);

        return student;
    }

    public Student delete(long id){
        return students.remove(id);
    }

    public Map<Long, Student> getAll(){
        return students;
    }

    public Student update(long id, String name, int age){
        Student student = students.get(id);

        if(student != null) {
            student.setName(name);
            student.setAge(age);

            return student;
        }

        return null;
    }
}
