package absence.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/gestionpresence";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Charger le pilote JDBC explicitement si nécessaire
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion réussie à la base de données !");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("probleme au niveau driver");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("probleme au niveau sql");

        }
        return connection;
    }
    public static void deconnecter() {
        if (getConnection() != null) {
            try {
                getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
    }
}
