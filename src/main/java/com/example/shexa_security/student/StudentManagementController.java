package com.example.shexa_security.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/student")
public class StudentManagementController {
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"Shexa Martin"),
            new Student(2,"Rue Martin"),
            new Student(3,"Zee Tee")
    );
    @GetMapping
    public List<Student> getAllStudents(){
        return STUDENTS;
    }
    @PostMapping()
    public void registerNewStudent(@RequestBody Student student){
        System.out.println("registerNewStudent");
        System.out.println(student);
    }
    @DeleteMapping(path="{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){
        System.out.println("deleteStudent");
        System.out.println(studentId);
    }
    @PutMapping(path="{studentId}")
    public void updateStudent(@PathVariable Integer studentId,@RequestBody Student student){
        System.out.println("updateStudent");
        System.out.println(String.format("%s %s", studentId, student));
    }
}
