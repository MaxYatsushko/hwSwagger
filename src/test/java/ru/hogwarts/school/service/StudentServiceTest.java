package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {StudentService.class})
@ExtendWith(SpringExtension.class)
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    void add_success() {
        //входные данные
        Student stud1 = new Student();

        Collection<Student> students = List.of(stud1);

        //ожидаемый результат
        when(studentRepository.save(stud1)).thenReturn(stud1);
        when(studentRepository.findAll()).thenReturn((List<Student>) students);

        //начало теста
        Student actualStudent = studentService.add(stud1);
        Collection<Student> actualStudents = studentService.getAll();
        assertEquals(students, actualStudents);
        assertEquals(stud1, actualStudent);
        verify(studentRepository).findAll();
        verify(studentRepository).save(stud1);
    }

    @Test
    void delete_success() {
        //входные данные
        long id = 0;
        Student stud1 = new Student();

        Collection<Student> students = List.of(stud1);

        //ожидаемый результат
        when(studentRepository.findAll()).thenReturn((List<Student>) students);

        //начало теста
        studentService.add(stud1);
        Collection<Student> actualStudents1 = studentService.getAll();
        assertEquals(students, actualStudents1);

        studentService.delete(id);
        Collection<Student> actualStudents2 = studentService.getAll();
        assertEquals(students, actualStudents2);
        verify(studentRepository).deleteById(id);
    }

    @Test
    void getAll_success() {
        //входные данные
        Student stud1 = new Student();

        Collection<Student> students = List.of(stud1);

        //ожидаемый результат
        when(studentRepository.findAll()).thenReturn((List<Student>) students);

        //начало теста
        studentService.add(stud1);
        Collection<Student> actualStudents = studentService.getAll();
        assertEquals(students, actualStudents);
        verify(studentRepository).findAll();
    }

    @Test
    void getAllByAge() {
        //входные данные
        int ageFind = 19, size = 2;
        Student stud1 = new Student();
        stud1.setAge(ageFind);
        Student stud2 = new Student();
        stud2.setAge(ageFind + 2);
        Student stud3 = new Student();
        stud3.setAge(ageFind);

        List<Student> students = List.of(stud1, stud3);

        //ожидаемый результат
        when(studentRepository.findAll()).thenReturn(students);

        //начало теста
        studentService.add(stud1);
        studentService.add(stud2);
        studentService.add(stud3);
        List<Student> actualStudents = new ArrayList<>(studentService.getAll(ageFind));
        assertTrue(students.contains(actualStudents.get(size - 2)) && students.contains(actualStudents.get(size - 1)));
        //assertEquals(students, actualStudents); - порядок элементов разный при выполнении всех тестов. Локально работает на True, также в дебаге
        verify(studentRepository).findAll();
    }

    @Test
    void update_success() {
        //входные данные
        int ageBeginner = 18;
        int ageLast = 20;
        Student stud1 = new Student();
        stud1.setAge(ageBeginner);

        List<Student> students = List.of(stud1);

        //ожидаемый результат
        when(studentRepository.findAll()).thenReturn(students);
        when(studentRepository.save(stud1)).thenReturn(stud1);

        //начало теста
        studentService.add(stud1);
        Collection<Student> actualStudents = studentService.getAll();
        assertEquals(students, actualStudents);

        stud1.setAge(ageLast);
        Student studentActual = studentService.update(stud1);
        Collection<Student> actualStudents2 = studentService.getAll();
        assertEquals(students, actualStudents2);
        assertEquals(stud1, studentActual);
        assertTrue(ageLast == studentActual.getAge());
    }
}