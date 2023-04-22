package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.FacultyCreationRequest;
import ru.hogwarts.school.model.FacultyUpdationRequest;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Map;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty add(@RequestBody FacultyCreationRequest facultyCreationRequest){
        return facultyService.add(facultyCreationRequest.getName(), facultyCreationRequest.getColor());
    }

    @DeleteMapping
    public Faculty delete(long id){
        return facultyService.delete(id);
    }

    @GetMapping("/getAll")
    public Map<Long, Faculty> getAll(){
        return facultyService.getAll();
    }

    @PutMapping
    public Faculty update(@RequestBody FacultyUpdationRequest facultyUpdationRequest){
        return facultyService.update(facultyUpdationRequest.getId(), facultyUpdationRequest.getName(), facultyUpdationRequest.getColor());
    }
}
