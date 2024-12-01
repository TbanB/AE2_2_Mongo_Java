import com.AE2_2_Mongo_Java.school.controllers.StudentController;
import com.AE2_2_Mongo_Java.school.controllers.TeacherController;
import com.AE2_2_Mongo_Java.school.dao.StudentDAO;
import com.AE2_2_Mongo_Java.school.dao.TeacherDAO;
import com.AE2_2_Mongo_Java.school.utils.DataBaseConnection;
import com.mongodb.client.MongoDatabase;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataBaseConnection connection = DataBaseConnection.getInstance();
        MongoDatabase database = connection.getDatabase();

        StudentDAO studentDAO = StudentDAO.getInstance(database);
        TeacherDAO teacherDAO = TeacherDAO.getInstance(database);
        StudentController studentController = new StudentController(studentDAO);
        TeacherController teacherController = new TeacherController(teacherDAO);

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Crear Profesor");
            System.out.println("2. Crear Estudiante");
            System.out.println("3. Mostrar Todos los Datos");
            System.out.println("4. Mostrar Profesores");
            System.out.println("5. Mostrar Estudiantes");
            System.out.println("6. Buscar Estudiante por Email");
            System.out.println("7. Buscar Profesor por Rango de Edad");
            System.out.println("8. Actualizar Título de Profesor");
            System.out.println("9. Dar de Baja Estudiantes con Calificación < 5");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    teacherController.insertTeacher(scanner);
                    break;

                case 2:
                    studentController.insertStudent(scanner);
                    break;

                case 3:
                    System.out.println("\n=== Todos los Profesores ===");
                    teacherController.listAllTeachers();
                    System.out.println("\n=== Todos los Estudiantes ===");
                    studentController.listAllStudents();
                    break;

                case 4:
                    teacherController.listAllTeachers();
                    break;

                case 5:
                    studentController.listAllStudents();
                    break;

                case 6:
                    studentController.findStudentByEmail(scanner);
                    break;

                case 7:
                    teacherController.findTeachersByAgeRange(scanner);
                    break;

                case 8:
                    teacherController.updateTeacherTitle(scanner);
                    break;

                case 9:
                    studentController.deleteStudentsByLowCalification();
                    break;

                case 10:
                    exit = true;
                    System.out.println("Cerrando");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
        connection.closeConnection();
    }
}
