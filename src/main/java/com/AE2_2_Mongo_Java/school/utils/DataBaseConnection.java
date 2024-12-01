package com.AE2_2_Mongo_Java.school.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DataBaseConnection {
    private static DataBaseConnection instance; // Instancia única de la conexión
    private MongoClient mongoClient;
    private MongoDatabase database;

    private DataBaseConnection() {
        String connectionString = "mongodb+srv://estebanbennettb:j70JtSDtkmXEtvAA@cluster0.owc35.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase("centro_estudios");
    }

    public static DataBaseConnection getInstance() {
        if (instance == null) {
            synchronized (DataBaseConnection.class) {
                if (instance == null) {
                    instance = new DataBaseConnection();
                }
            }
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return this.database;
    }

    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión con MongoDB cerrada.");
        }
    }
}
