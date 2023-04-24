package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.FacultyCreationRequest;
import ru.hogwarts.school.model.FacultyUpdationRequest;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty request){
        return facultyService.add(request);
    }

    @DeleteMapping
    public ResponseEntity<Faculty> delete(long id){
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public Collection<Faculty> getAll(){
        return facultyService.getAll();
    }
    @GetMapping("/getAll/{color}")
    public List<Faculty> getAll(@PathVariable String color){
        return facultyService.getAll(color);
    }

    @PutMapping
    public ResponseEntity<Faculty> update(@RequestBody Faculty request){
        Faculty foundFaculty = facultyService.update(request);

        if (foundFaculty == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(foundFaculty);
    }
}
