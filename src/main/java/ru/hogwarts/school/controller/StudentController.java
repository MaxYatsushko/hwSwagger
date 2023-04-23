package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.FacultyCreationRequest;
import ru.hogwarts.school.model.FacultyUpdationRequest;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student add(@RequestBody Student request){
        return studentService.add(request.getName(), request.getAge());
    }

    @DeleteMapping
    public Student delete(long id){
        return studentService.delete(id);
    }

    @GetMapping("/getAll")
    public Map<Long, Student> getAll(){
        return studentService.getAll();
    }
    @GetMapping("/getAll/{age}")
    public Map<Long, Student> getAll(@PathVariable int age){
        return studentService.getAll(age);
    }

    @PutMapping
    public Student update(@RequestBody Student request){
        return studentService.update(request.getId(), request.getName(), request.getAge());
    }
}
