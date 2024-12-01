package com.AE2_2_Mongo_Java.school.dao;

import com.AE2_2_Mongo_Java.school.models.Student;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static StudentDAO instance;
    private final MongoCollection<Document> collection;

    private StudentDAO(MongoDatabase database) {
        this.collection = database.getCollection("alumnos");
    }

    public static StudentDAO getInstance(MongoDatabase database) {
        if (instance == null) {
            synchronized (StudentDAO.class) {
                if (instance == null) {
                    instance = new StudentDAO(database);
                }
            }
        }
        return instance;
    }

    private Student documentToStudent(Document doc) {
        String id = doc.getString("_id");

        return new Student(
                id != null ? id : "Unknown",
                doc.getDouble("rating") != null ? doc.getDouble("rating") : 0.0,
                doc.getInteger("age") != null ? doc.getInteger("age") : 0,
                doc.getString("name") != null ? doc.getString("name") : "",
                doc.getString("gender") != null ? doc.getString("gender") : "",
                doc.getString("email") != null ? doc.getString("email") : "",
                doc.getString("phone") != null ? doc.getString("phone") : "",
                doc.getInteger("calificaation") != null ? doc.getInteger("calificaation") : 0,
                doc.getString("higher_grade") != null ? doc.getString("higher_grade") : "",
                false
        );
    }

    public void insertStudent(Student student) {
        Document doc = new Document("_id", student.getId())
                .append("rating", student.getRating())
                .append("age", student.getAge())
                .append("name", student.getName())
                .append("gender", student.getGender())
                .append("email", student.getEmail())
                .append("phone", student.getPhone())
                .append("calificaation", student.getCalification())
                .append("higher_grade", student.getHigherGrade());
        collection.insertOne(doc);
        System.out.println("Estudiante insertado correctamente.");
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        for (Document doc : collection.find()) {
            students.add(documentToStudent(doc));
        }
        return students;
    }

    public Student findStudentByEmail(String email) {
        Document doc = collection.find(new Document("email", email)).first();
        if (doc != null) {
            return documentToStudent(doc);
        }
        System.out.println("Estudiante con email " + email + " no encontrado.");
        return null;
    }

    public void deleteStudentsByCalification(int threshold) {
        collection.deleteMany(new Document("calificaation", new Document("$lt", threshold)));
        System.out.println("Estudiantes con calificación menor a " + threshold + " eliminados.");
    }
}
