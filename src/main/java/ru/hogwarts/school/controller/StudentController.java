package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.FacultyCreationRequest;
import ru.hogwarts.school.model.FacultyUpdationRequest;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student add(@RequestBody Student request){
        return studentService.add(request);
    }

    @DeleteMapping
    public ResponseEntity delete(long id){
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public Collection<Student> getAll(){
        return studentService.getAll();
    }

    @GetMapping("/getAll/{age}")
    public List<Student> getAll(@PathVariable int age){
        return studentService.getAll(age);
    }

    @PutMapping
    public ResponseEntity<Student> update(@RequestBody Student request){
        Student foundStudent = studentService.update(request);

        if (foundStudent == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @GetMapping("/getAll/between")
    public List<Student> getAll(@RequestParam int min, @RequestParam int max){
        return studentService.getAll(min, max);
    }

    @GetMapping("/getFaculty")
    public Faculty getFaculty(@RequestBody Student request){
        return studentService.getFaculty(request);
    }

    @GetMapping("/getNumberStudents")
    public int getNumberStudents(){
        return studentService.getNumberStudents();
    }

    @GetMapping("/getAvgAge")
    public int getAvgAge(){
        return studentService.getAvgAge();
    }

    @GetMapping("/getLastFiveStudent")
    public List<Student> getLastFiveStudent(){
        return studentService.getLastFiveStudent();
    }
}
