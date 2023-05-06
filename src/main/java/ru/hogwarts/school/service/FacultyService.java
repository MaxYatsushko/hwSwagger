package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class FacultyService {

    private Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty add(Faculty faculty){
        logger.debug("Requesting add faculty id={} name={}", faculty.getId(), faculty.getName());
        Faculty addFaculty = facultyRepository.save(faculty);
        logger.debug("The request of add is successful and faculty id={} name={}", addFaculty.getId(), addFaculty.getName());

        return addFaculty;
    }

    public void delete(long id){
        logger.debug("Requesting delete faculty id={}", id);
        facultyRepository.deleteById(id);
        logger.debug("The request of delete is successful");
    }

    public Collection<Faculty> getAll(){
        logger.debug("Requesting getAll");
        Collection<Faculty> faculties = facultyRepository.findAll();
        logger.debug("The request of getAll is successful and received numbers={}", faculties.size());

        return faculties;
    }

    public List<Faculty> getAll(String color){
        logger.debug("Requesting getAll by color={}", color);
        List<Faculty> faculties = facultyRepository.getFacultiesByColor(color);
        logger.debug("The request of getAll by color is successful and received numbers={}", faculties.size());

        return faculties;
    }

    public List<Faculty> getAll(String name, String color){
        logger.debug("Requesting getAll by name={} or by color={}", name, color);
        List<Faculty> faculties = facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(name, color);
        logger.debug("The request of getAll by color or by name is successful and received numbers={}", faculties.size());

        return faculties;
    }

    public List<Student> getStudents(Faculty faculty){
        logger.debug("Requesting getStudents by faculty id={} name={}", faculty.getId(), faculty.getName());
        List<Student> students = faculty.getStudents();
        logger.debug("The request of getStudents by faculty is successful and received numbers={}", students.size());

        return students;
    }
    public Faculty update(Faculty faculty){
        logger.debug("Requesting update faculty id={} name={}", faculty.getId(), faculty.getName());
        Faculty updateFaculty = facultyRepository.save(faculty);
        logger.debug("The request of update is successful and faculty id={} name={}", updateFaculty.getId(), updateFaculty.getName());

        return updateFaculty;
    }

    public String getLongestName(){
        logger.debug("Requesting getLongestName");
        String nameLongest = facultyRepository.findAll().stream()
                .map(faculty -> faculty.getName())
                .max(Comparator.comparingInt(String::length))
                .orElse("Не найден");
        logger.debug("The request of getLongestName is successful and longest name={}", nameLongest);

        return nameLongest;
    }

    public int getIntExecSomeFunctionTest1() {
        long start = System.currentTimeMillis();
        int someInt = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();
        logger.info("getIntExecSomeFunctionTest1 result: {}, time: {}", someInt, finish - start);
        return someInt;
    }
     public int getIntExecSomeFunctionTest2() {
        long start = System.currentTimeMillis();
        int someInt = IntStream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();
        logger.info("getIntExecSomeFunctionTest2 result: {}, time: {}", someInt, finish - start);
        return someInt;
    }

    public int getIntExecSomeFunctionTest3() {
        long start = System.currentTimeMillis();
        int someInt = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();
        logger.info("getIntExecSomeFunctionTest3 result: {}, time: {}", someInt, finish - start);
        return someInt;
    }

    public int getIntExecSomeFunctionTest4() {
        long start = System.currentTimeMillis();
        int someInt = Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();
        logger.info("getIntExecSomeFunctionTest4 result: {}, time: {}", someInt, finish - start);
        return someInt;
    }
    public int getIntExecSomeFunctionTest5() {
        long start = System.currentTimeMillis();
        int someInt = IntStream.iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long finish = System.currentTimeMillis();
        logger.info("getIntExecSomeFunctionTest5 result: {}, time: {}", someInt, finish - start);
        return someInt;
    }
}
