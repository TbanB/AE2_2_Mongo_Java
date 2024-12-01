package com.AE2_2_Mongo_Java.school.controllers;

import com.AE2_2_Mongo_Java.school.dao.StudentDAO;
import com.AE2_2_Mongo_Java.school.models.Student;

import java.util.List;
import java.util.Scanner;

public class StudentController {
    private final StudentDAO studentDAO;

    public StudentController(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public void insertStudent(Scanner scanner) {
        System.out.println("=== Insertar Estudiante ===");
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Edad: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        System.out.print("Género: ");
        String gender = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Teléfono: ");
        String phone = scanner.nextLine();
        System.out.print("Calificación: ");
        int calification = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea
        System.out.print("Grado Superior: ");
        String higherGrade = scanner.nextLine();

        Student student = new Student(null, 0.0, age, name, gender, email, phone, calification, higherGrade, true);
        studentDAO.insertStudent(student);
    }

    public void listAllStudents() {
        System.out.println("=== Listar Estudiantes ===");
        List<Student> students = studentDAO.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
        } else {
            students.forEach(System.out::println);
        }
    }

    public void findStudentByEmail(Scanner scanner) {
        System.out.println("=== Buscar Estudiante por Email ===");
        System.out.print("Email del estudiante: ");
        String email = scanner.nextLine();

        Student student = studentDAO.findStudentByEmail(email);
        if (student != null) {
            System.out.println("Estudiante encontrado: " + student);
        } else {
            System.out.println("No se encontró un estudiante con el email proporcionado.");
        }
    }

    public void deleteStudentsByLowCalification() {
        System.out.println("=== Eliminar Estudiantes con Calificación Baja ===");
        studentDAO.deleteStudentsByCalification(5);
        System.out.println("Estudiantes con calificación menor a 5 eliminados.");
    }
}
