package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.InfoController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class HomeWorkSwaggerUiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private AvatarService avatarService;

    @SpyBean
    private InfoServiceProd infoServiceProd;

    @InjectMocks
    private StudentController studentController;

    @InjectMocks
    private FacultyController facultyController;

    @InjectMocks
    private InfoController infoController;


    /////////////////////////////////////////////////////
    //Область тесты студентов
    /////////////////////////////////////////////////////
    @Test
    void getAll_Student_success() throws Exception {
        //входные данные
        int age1 = 19, age2 = 20;
        long id1 = 0, id2 = 1;
        String name1 = "Vasya", name2 = "Miasha";
        Student stud1 = new Student();
        stud1.setId(id1);
        stud1.setAge(age1);
        stud1.setName(name1);

        Student stud2 = new Student();
        stud2.setId(id2);
        stud2.setAge(age2);
        stud2.setName(name2);

        List<Student> students = List.of(stud1, stud2);

        //ожидаемый результат
        when(studentRepository.findAll()).thenReturn(students);

        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                .get("/student/getAll")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value(name1))
                .andExpect(jsonPath("$[0].age").value(age1))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].age").value(age2));

    }

    @Test
    void getAllAge_Student_success() throws Exception {
        //входные данные
        int age1 = 19, age2 = 20;
        long id1 = 0, id2 = 1;
        String name1 = "Vasya", name2 = "Miasha";
        Student stud1 = new Student();
        stud1.setId(id1);
        stud1.setAge(age1);
        stud1.setName(name1);

        Student stud2 = new Student();
        stud2.setId(id2);
        stud2.setAge(age2);
        stud2.setName(name2);

        List<Student> students = List.of(stud1);

        //ожидаемый результат
        when(studentRepository.findAllByAge(age1)).thenReturn(students);

        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/getAll/"+age1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value(name1))
                .andExpect(jsonPath("$[0].age").value(age1));

    }

    @Test
    void add_Student_success() throws Exception {
        //входные данные
        int age = 19;
        long id = 0;
        String name = "Vasya";
        Student stud1 = new Student();
        stud1.setId(id);
        stud1.setAge(age);
        stud1.setName(name);

        String jsonData = new ObjectMapper().writeValueAsString(stud1);

        //ожидаемый результат
        when(studentService.add(any(Student.class))).thenReturn(stud1);

        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void delete_Student_success() throws Exception {
        //входные данные
        int age = 19;
        long id = 0;
        String name = "Vasya";
        Student stud1 = new Student();
        stud1.setId(id);
        stud1.setAge(age);
        stud1.setName(name);

        String jsonData = new ObjectMapper().writeValueAsString(stud1);

        //ожидаемый результат
        studentService.add(stud1);
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());

        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student?id=" + id)
                        .content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Collection<Student> actualStudents = studentService.getAll();
        Assertions.assertEquals(Collections.emptyList(), actualStudents);
    }

    @Test
    void update_Student_success() throws Exception {

        //входные данные
        int age = 19;
        long id = 0;
        String name = "Vasya";
        Student stud1 = new Student();
        stud1.setId(id);
        stud1.setAge(age);
        stud1.setName(name);

        String jsonData = new ObjectMapper().writeValueAsString(stud1);

        //ожидаемый результат
        when(studentRepository.save(any(Student.class))).thenReturn(stud1);

        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void update_Student_BadRequest() throws Exception {
        //входные данные
        int age = 19;
        long id = 0;
        String name = "Vasya";
        Student stud1 = new Student();
        stud1.setId(id);
        stud1.setAge(age);
        stud1.setName(name);

        String jsonData = new ObjectMapper().writeValueAsString(stud1);


        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllBetweenAge_Student_success() throws Exception {
        //входные данные
        int ageMin = 19, ageMax = 20;
        long id1 = 0, id2 = 1;
        String name1 = "Vasya", name2 = "Miasha";
        Student stud1 = new Student();
        stud1.setId(id1);
        stud1.setAge(ageMin);
        stud1.setName(name1);

        Student stud2 = new Student();
        stud2.setId(id2);
        stud2.setAge(ageMax);
        stud2.setName(name2);

        List<Student> students = List.of(stud1, stud2);

        //ожидаемый результат
        when(studentRepository.findAllByAgeBetween(ageMin, ageMax)).thenReturn(students);

        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/getAll/between?min="+ageMin + "&max=" + ageMax)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[0].name").value(name1))
                .andExpect(jsonPath("$[0].age").value(ageMin))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].age").value(ageMax));

    }
    @Test
    void getFaculty_Student_success() throws Exception {
        //входные данные
        int age1 = 19;
        long id1 = 0;
        String name1 = "Vasya", nameFac = "Griffindor", color = "red";
        Student stud1 = new Student();
        stud1.setId(id1);
        stud1.setAge(age1);
        stud1.setName(name1);

        Faculty faculty = new Faculty();
        faculty.setId(id1);
        faculty.setName(nameFac);
        faculty.setColor(color);

        stud1.setFaculty(faculty);
        List<Student> students = List.of(stud1);

        String jsonData = new ObjectMapper().writeValueAsString(stud1);
        //ожидаемый результат
        when(studentRepository.findAll()).thenReturn(students);
        when(studentService.getFaculty(stud1)).thenReturn(faculty);


        //начало теста
        Faculty actualFac = studentService.getFaculty(stud1);
        Assertions.assertEquals(faculty, actualFac);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/getFaculty")
                        .content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(nameFac))
                .andExpect(jsonPath("$.color").value(color));

    }

    /////////////////////////////////////////////////////
    //Область тесты студентов Конец
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    //Область тесты факультет
    /////////////////////////////////////////////////////

    @Test
    void add_Faculty_success() throws Exception {
        //входные данные
        String name = "Grif", color = "red";
        long id = 0;
        Faculty fac1 = new Faculty();
        fac1.setId(id);
        fac1.setColor(color);
        fac1.setName(name);

        String jsonData = new ObjectMapper().writeValueAsString(fac1);

        //ожидаемый результат
        when(facultyService.add(any(Faculty.class))).thenReturn(fac1);

        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void delete_Faculty_success() throws Exception {
        //входные данные
        String name = "Grif", color = "red";
        long id = 0;
        Faculty fac1 = new Faculty();
        fac1.setId(id);
        fac1.setColor(color);
        fac1.setName(name);

        String jsonData = new ObjectMapper().writeValueAsString(fac1);

        //ожидаемый результат
        when(facultyRepository.findAll()).thenReturn(Collections.emptyList());

        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty?id=" + id)
                        .content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Collection<Faculty> actualFaculties = facultyService.getAll();
        Assertions.assertEquals(Collections.emptyList(), actualFaculties);
    }

    @Test
    void update_Faculty_success() throws Exception {
        //входные данные
        String name = "Grif", color = "red";
        long id = 0;
        Faculty fac1 = new Faculty();
        fac1.setId(id);
        fac1.setColor(color);
        fac1.setName(name);

        String jsonData = new ObjectMapper().writeValueAsString(fac1);

        //ожидаемый результат
        when(facultyRepository.save(any(Faculty.class))).thenReturn(fac1);

        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void update_Faculty_BadRequest() throws Exception {
        //входные данные
        String name = "Grif", color = "red";
        long id = 0;
        Faculty fac1 = new Faculty();
        fac1.setId(id);
        fac1.setColor(color);
        fac1.setName(name);

        String jsonData = new ObjectMapper().writeValueAsString(fac1);

        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAll_Faculty_success() throws Exception {
        //входные данные
        String name = "Grif", name2 = "Slyz", color = "red", color2 = "green";
        long id = 0, id2 = 1;
        Faculty fac1 = new Faculty();
        fac1.setId(id);
        fac1.setColor(color);
        fac1.setName(name);

        Faculty fac2 = new Faculty();
        fac2.setId(id2);
        fac2.setColor(color2);
        fac2.setName(name2);

        List<Faculty> faculties = List.of(fac1, fac2);

        //ожидаемый результат
        when(facultyRepository.findAll()).thenReturn(faculties);

        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getAll")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color))
                .andExpect(jsonPath("$[1].id").value(id2))
                .andExpect(jsonPath("$[1].name").value(name2))
                .andExpect(jsonPath("$[1].color").value(color2));

    }
    @Test
    void getAllColor_Faculty_success() throws Exception {
        //входные данные
        String name = "Grif", name2 = "Slyz", color = "red", color2 = "green";
        long id = 0, id2 = 1;
        Faculty fac1 = new Faculty();
        fac1.setId(id);
        fac1.setColor(color);
        fac1.setName(name);

        Faculty fac2 = new Faculty();
        fac2.setId(id2);
        fac2.setColor(color2);
        fac2.setName(name2);

        List<Faculty> faculties = List.of(fac1);

        //ожидаемый результат
        when(facultyRepository.getFacultiesByColor(color)).thenReturn(faculties);

        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getAll/"+color)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color))
                .andExpect(jsonPath("$[1].color").doesNotExist());

    }

    @Test
    void getAllFaculty_Faculty_success() throws Exception {
        //входные данные
        String name = "Grif", name2 = "Slyz", color = "red", color2 = "green";
        long id = 0, id2 = 1;
        Faculty fac1 = new Faculty();
        fac1.setId(id);
        fac1.setColor(color);
        fac1.setName(name);

        Faculty fac2 = new Faculty();
        fac2.setId(id2);
        fac2.setColor(color2);
        fac2.setName(name2);

        List<Faculty> faculties = List.of(fac1);

        //ожидаемый результат
        when(facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(name, color)).thenReturn(faculties);

        //начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getAll/faculty?color="+ color +"&name=" + name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].color").value(color))
                .andExpect(jsonPath("$[1].color").doesNotExist());

    }

    @Test
    void getStudents_Faculty_success() throws Exception {
        //входные данные
        long id1 = 0;
        String color = "green", nameFac = "Grif";

        Faculty faculty = new Faculty();
        faculty.setId(id1);
        faculty.setName(nameFac);
        faculty.setColor(color);

        List<Faculty> faculties = List.of(faculty);

        String jsonData = new ObjectMapper().writeValueAsString(faculty);

        //ожидаемый результат
        when(facultyService.getStudents(faculty)).thenReturn(Collections.emptyList());
        when(facultyRepository.findAll()).thenReturn(faculties);

        //начало теста
        List<Student> actualStudents = facultyService.getStudents(faculty);
        Assertions.assertEquals(Collections.emptyList(), actualStudents);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getStudents")
                        .content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").doesNotExist())
                .andExpect(jsonPath("$[0].name").doesNotExist());
    }
    /////////////////////////////////////////////////////
    //Область тесты факультет Конец
    /////////////////////////////////////////////////////


}
