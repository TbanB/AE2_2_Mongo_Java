package com.AE2_2_Mongo_Java.school.controllers;

import com.AE2_2_Mongo_Java.school.dao.TeacherDAO;
import com.AE2_2_Mongo_Java.school.models.Teacher;

import java.util.List;
import java.util.Scanner;

public class TeacherController {
    private final TeacherDAO teacherDAO;

    public TeacherController(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    public void insertTeacher(Scanner scanner) {
        System.out.println("=== Insertar Profesor ===");
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
        System.out.print("Título: ");
        String title = scanner.nextLine();
        System.out.print("Número de asignaturas: ");
        int subjectCount = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        System.out.println("Introduce las asignaturas:");
        List<String> subjects = new java.util.ArrayList<>();
        for (int i = 0; i < subjectCount; i++) {
            System.out.print("Asignatura " + (i + 1) + ": ");
            subjects.add(scanner.nextLine());
        }

        Teacher teacher = new Teacher(null, 0.0, age, name, gender, email, phone, subjects, title);
        teacherDAO.insertTeacher(teacher);
    }

    public void listAllTeachers() {
        System.out.println("=== Listar Profesores ===");
        List<Teacher> teachers = teacherDAO.getAllTeachers();
        if (teachers.isEmpty()) {
            System.out.println("No hay profesores registrados.");
        } else {
            teachers.forEach(System.out::println);
        }
    }

    public void findTeachersByAgeRange(Scanner scanner) {
        System.out.println("=== Buscar Profesor por Rango de Edad ===");
        System.out.print("Edad mínima: ");
        int minAge = scanner.nextInt();
        System.out.print("Edad máxima: ");
        int maxAge = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        List<Teacher> teachers = teacherDAO.getAllTeachers();
        List<Teacher> filteredTeachers = teachers.stream()
                .filter(t -> t.getAge() >= minAge && t.getAge() <= maxAge)
                .toList();

        if (filteredTeachers.isEmpty()) {
            System.out.println("No se encontraron profesores en este rango de edad.");
        } else {
            filteredTeachers.forEach(System.out::println);
        }
    }

    public void updateTeacherTitle(Scanner scanner) {
        System.out.println("=== Actualizar Título del Profesor ===");
        System.out.print("Email del profesor: ");
        String email = scanner.nextLine();
        System.out.print("Nuevo título: ");
        String newTitle = scanner.nextLine();

        boolean updated = teacherDAO.updateTeacherTitle(email, newTitle);
        if (updated) {
            System.out.println("Título actualizado correctamente.");
        } else {
            System.out.println("No se encontró un profesor con el email proporcionado.");
        }
    }
}
