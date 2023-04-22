package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {
    private long idCount = 0;
    private Map<Long, Faculty> faculties = new HashMap<>();

    public Faculty add(String name, String color){
        long id = idCount++;
        Faculty faculty = new Faculty(id, name, color);
        faculties.put(id, faculty);

        return faculty;
    }

    public Faculty delete(long id){
        return faculties.remove(id);
    }

    public Map<Long, Faculty> getAll(){
        return faculties;
    }

    public Map<Long, Faculty> getAll(String color){
        Map<Long,Faculty> facultyColor = new HashMap<>();
        long idCount = 0;
        for (Faculty faculty: faculties.values())
            if (faculty.getColor().equals(color))
            facultyColor.put(idCount++, faculty);

        return facultyColor;
    }

    public Faculty update(long id, String name, String color){
        Faculty faculty = faculties.get(id);

        if(faculty != null) {
            faculty.setName(name);
            faculty.setColor(color);

            return faculty;
        }

        return null;
    }
}
