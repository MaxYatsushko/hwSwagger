package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {FacultyService.class})
@ExtendWith(SpringExtension.class)
class FacultyServiceTest {

    @Autowired
    private FacultyService facultyService;

    @MockBean
    private FacultyRepository facultyRepository;

    @BeforeEach
    void init(){
        facultyService = new FacultyService(facultyRepository);
    }

    @Test
    void add_success() {
        //входные данные
        Faculty fac1 = new Faculty();

        Collection<Faculty> faculties = List.of(fac1);

        //ожидаемый результат
        when(facultyRepository.save(fac1)).thenReturn(fac1);
        when(facultyRepository.findAll()).thenReturn((List<Faculty>) faculties);

        //начало теста
        Faculty actualFaculty = facultyService.add(fac1);
        Collection<Faculty> actualFaculties = facultyService.getAll();
        assertEquals(faculties, actualFaculties);
        assertEquals(fac1, actualFaculty);
        verify(facultyRepository).findAll();
        verify(facultyRepository).save(fac1);
    }

    @Test
    void delete_success() {
        //входные данные
        long id = 0;
        Faculty fac1 = new Faculty();

        Collection<Faculty> faculties = List.of(fac1);

        //ожидаемый результат
        when(facultyRepository.findAll()).thenReturn((List<Faculty>) faculties);

        //начало теста
        facultyService.add(fac1);
        Collection<Faculty> actualFaculties1 = facultyService.getAll();
        assertEquals(faculties, actualFaculties1);

        facultyService.delete(id);
        Collection<Faculty> actualFaculties2 = facultyService.getAll();
        assertEquals(faculties, actualFaculties2);
        verify(facultyRepository).deleteById(id);
    }

    @Test
    void getAll_success() {
        //входные данные
        Faculty fac1 = new Faculty();

        Collection<Faculty> faculties = List.of(fac1);

        //ожидаемый результат
        when(facultyRepository.findAll()).thenReturn((List<Faculty>) faculties);

        //начало теста
        facultyService.add(fac1);
        Collection<Faculty> actualFaculties = facultyService.getAll();
        assertEquals(faculties, actualFaculties);
        verify(facultyRepository).findAll();
    }

    @Test
    void getAllByColor() {
        //входные данные
        String colorFind = "Green";
        Faculty fac1 = new Faculty();
        fac1.setColor(colorFind);
        Faculty fac2 = new Faculty();
        fac2.setColor("Black");
        Faculty fac3 = new Faculty();
        fac3.setColor(colorFind);

        List<Faculty> faculties = List.of(fac1, fac3);

        //ожидаемый результат
        when(facultyRepository.getFacultiesByColor(colorFind)).thenReturn(faculties);

        //начало теста
        facultyService.add(fac1);
        facultyService.add(fac2);
        facultyService.add(fac3);
        List<Faculty> actualFaculties = facultyService.getAll(colorFind);
        assertEquals(faculties, actualFaculties);
    }

    @Test
    void update_success() {
        //входные данные
        String colorBeginner = "Green";
        String colorLast = "Blue";
        Faculty fac1 = new Faculty();
        fac1.setColor(colorBeginner);

        List<Faculty> faculties = List.of(fac1);

        //ожидаемый результат
        when(facultyRepository.findAll()).thenReturn(faculties);
        when(facultyRepository.save(fac1)).thenReturn(fac1);

        //начало теста
        facultyService.add(fac1);
        Collection<Faculty> actualFaculties = facultyService.getAll();
        assertEquals(faculties, actualFaculties);

        fac1.setColor(colorLast);
        Faculty facultyActual = facultyService.update(fac1);
        Collection<Faculty> actualFaculties2 = facultyService.getAll();
        assertEquals(faculties, actualFaculties2);
        assertEquals(fac1, facultyActual);
        assertTrue(colorLast == facultyActual.getColor());
    }
}