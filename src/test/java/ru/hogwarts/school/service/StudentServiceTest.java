package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.GetMapping;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        when(studentRepository.findAllByAge(ageFind)).thenReturn(students);

        //начало теста
        //studentService.add(stud1);
        //studentService.add(stud2);
        //studentService.add(stud3);
        List<Student> actualStudents = studentService.getAll(ageFind);
        assertTrue(students.contains(actualStudents.get(size - 2)) && students.contains(actualStudents.get(size - 1)));
        //assertEquals(students, actualStudents); - порядок элементов разный при выполнении всех тестов. Локально работает на True, также в дебаге
        verify(studentRepository).findAllByAge(ageFind);
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

    @Test
    void getAllAges_success() {
        //входные данные
        int ageFisrt = 18;
        int ageSecond = 20;
        Student stud1 = new Student();
        stud1.setAge(ageFisrt);

        Student stud2 = new Student();
        stud2.setAge(ageSecond + ageSecond);

        Student stud3 = new Student();
        stud3.setAge(ageSecond);

        List<Student> students = List.of(stud1, stud3);

        //ожидаемый результат
        when(studentRepository.findAllByAgeBetween(ageFisrt, ageSecond)).thenReturn(students);

        //начало теста
        Collection<Student> actualStudents = studentService.getAll(ageFisrt, ageSecond);
        assertEquals(students, actualStudents);

    }

    @Test
    void getNumberStudents_success() {
        //входные данные
        int ageFisrt = 18;
        int ageSecond = 20;
        Student stud1 = new Student();
        stud1.setAge(ageFisrt);

        Student stud2 = new Student();
        stud2.setAge(ageSecond + ageSecond);

        Student stud3 = new Student();
        stud3.setAge(ageSecond);

        List<Student> students = List.of(stud1, stud3);

        //ожидаемый результат
        when(studentRepository.getNumberStudents()).thenReturn(students.size());

        //начало теста
        int actualNumberStudents = studentService.getNumberStudents();
        assertTrue(students.size() == actualNumberStudents);
    }

    @Test
    void getAvgAge_success() {
        //входные данные
        int ageFisrt = 18;
        int ageSecond = 20;
        Student stud1 = new Student();
        stud1.setAge(ageFisrt);

        Student stud2 = new Student();
        stud2.setAge(ageSecond + ageSecond);

        Student stud3 = new Student();
        stud3.setAge(ageSecond);

        List<Student> students = List.of(stud1, stud2, stud3);

        //ожидаемый результат
        int sumAge = 0;
        for (Student student: students){
            sumAge += student.getAge();
        }
        int avgAge = sumAge / students.size();
        when(studentRepository.getAvgAge()).thenReturn(avgAge);

        //начало теста
        int actualAvgAgeStudents = studentService.getAvgAge();
        assertTrue(avgAge == actualAvgAgeStudents);
    }

    @Test
    void getLastFiveStudent_success() {
        //входные данные
        int ageFisrt = 18;
        int ageSecond = 20;
        Student stud1 = new Student();
        stud1.setAge(ageFisrt);

        Student stud2 = new Student();
        stud2.setAge(ageSecond + ageSecond);

        Student stud3 = new Student();
        stud3.setAge(ageSecond);

        Student stud4 = new Student();
        stud4.setAge(ageSecond);

        Student stud5 = new Student();
        stud5.setAge(ageSecond);

        Student stud6 = new Student();
        stud6.setAge(ageSecond);

        Student stud7 = new Student();
        stud7.setAge(ageSecond);

        List<Student> students = List.of(stud3, stud4, stud5, stud6, stud7);

        //ожидаемый результат
        int sumAge = 0;
        for (Student student: students){
            sumAge += student.getAge();
        }
        int avgAge = sumAge / students.size();
        when(studentRepository.getLastFiveStudent()).thenReturn(students);

        //начало теста
        List<Student> actualLastFiveStudents = studentService.getLastFiveStudent();
        assertEquals(students, actualLastFiveStudents);
    }

    @Test
    void getFaculty_success() {

        //входные данные
        int ageFisrt = 18;
        int ageSecond = 20;
        Student stud1 = new Student();
        stud1.setAge(ageFisrt);

        Faculty faculty = new Faculty();
        faculty.setName("Grif");
        stud1.setFaculty(faculty);

        //ожидаемый результат
        stud1.setFaculty(faculty);
        when(studentRepository.save(stud1)).thenReturn(stud1);

        //начало теста
        studentService.add(stud1);
        Faculty actualFaculty = studentService.getFaculty(stud1);
        assertEquals(faculty, actualFaculty);


    }

    @Test
    void getStudentsNameBeginA_success() {

        //входные данные
        String name = "Adfdsfds", name2 = "Absd", name3 = "fdfsd";
        Student stud1 = new Student();
        stud1.setName(name);

        Student stud2 = new Student();
        stud2.setName(name2);

        Student stud3 = new Student();
        stud3.setName(name3);

        List<Student> students = List.of(stud1, stud2, stud3);

        //ожидаемый результат
        when(studentRepository.findAll()).thenReturn(students);
        List<String> expectedNames = List.of(name2.toUpperCase(), name.toUpperCase());

        //начало теста
        List<String> actualNames = studentService.getStudentsNameBeginA();
        assertEquals(expectedNames, actualNames);
    }

    @Test
    void getAvgAgeStream_success() {

        //входные данные
        int age = 17, age2 = 18, age3 = 18;
        Student stud1 = new Student();
        stud1.setAge(age);

        Student stud2 = new Student();
        stud2.setAge(age2);

        Student stud3 = new Student();
        stud3.setAge(age3);

        List<Student> students = List.of(stud1, stud2, stud3);

        //ожидаемый результат
        when(studentRepository.findAll()).thenReturn(students);
        Double expectedAvgAge = (double) (age + age2 + age3) / 3;

        //начало теста
        Double actualAvgAge = studentService.getAvgAgeStream();
        assertEquals(expectedAvgAge, actualAvgAge);
    }
}