package com.AE2_2_Mongo_Java.school.dao;

import com.AE2_2_Mongo_Java.school.models.Teacher;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    private static TeacherDAO instance;
    private final MongoCollection<Document> collection;

    private TeacherDAO(MongoDatabase database) {
        this.collection = database.getCollection("profesores");
    }

    public static TeacherDAO getInstance(MongoDatabase database) {
        if (instance == null) {
            synchronized (TeacherDAO.class) {
                if (instance == null) {
                    instance = new TeacherDAO(database);
                }
            }
        }
        return instance;
    }

    private Teacher documentToTeacher(Document doc) {
        String id = doc.getString("_id"); // `_id` tratado como String

        return new Teacher(
                id != null ? id : "Unknown", // Valor predeterminado para `_id`
                doc.getDouble("rating") != null ? doc.getDouble("rating") : 0.0, // Valor predeterminado para rating
                doc.getInteger("age") != null ? doc.getInteger("age") : 0, // Valor predeterminado para age
                doc.getString("name") != null ? doc.getString("name") : "Unknown", // Valor predeterminado para name
                doc.getString("gender") != null ? doc.getString("gender") : "Unknown", // Valor predeterminado para gender
                doc.getString("email") != null ? doc.getString("email") : "Unknown", // Valor predeterminado para email
                doc.getString("phone") != null ? doc.getString("phone") : "Unknown", // Valor predeterminado para phone
                doc.getList("subjects", String.class) != null ? doc.getList("subjects", String.class) : List.of(), // Lista vacía por defecto
                doc.getString("title") != null ? doc.getString("title") : "Unknown" // Valor predeterminado para title
        );
    }

    public void insertTeacher(Teacher teacher) {
        Document doc = new Document("_id", teacher.getId())
                .append("rating", teacher.getRating())
                .append("age", teacher.getAge())
                .append("name", teacher.getName())
                .append("gender", teacher.getGender())
                .append("email", teacher.getEmail())
                .append("phone", teacher.getPhone())
                .append("subjects", teacher.getSubjects())
                .append("title", teacher.getTitle());
        collection.insertOne(doc);
        System.out.println("Profesor insertado correctamente.");
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        for (Document doc : collection.find()) {
            teachers.add(documentToTeacher(doc));
        }
        return teachers;
    }

    public boolean updateTeacherTitle(String email, String newTitle) {
        Document result = collection.findOneAndUpdate(
                new Document("email", email),
                new Document("$set", new Document("title", newTitle))
        );
        return result != null;
    }
}
