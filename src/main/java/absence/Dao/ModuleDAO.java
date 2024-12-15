package absence.Dao;

import absence.Modeles.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleDAO {
    private Connection connection;

    public ModuleDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void ajouterModule(String nomModule) throws SQLException {
        String sql = "INSERT INTO module (NOM_MODULE) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nomModule);
            statement.executeUpdate();
        }
    }

    public List<Module> obtenirTousLesModules() throws SQLException {
        String sql = "SELECT * FROM module";
        List<Module> modules = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Module module = new Module(resultSet.getInt("ID_MODULE"), resultSet.getString("NOM_MODULE"));
                modules.add(module);
            }
        }
        return modules;
    }

    public void modifierModule(int idModule, String nomModule) throws SQLException {
        String sql = "UPDATE module SET NOM_MODULE = ? WHERE ID_MODULE = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nomModule);
            statement.setInt(2, idModule);
            statement.executeUpdate();
        }
    }

    public void supprimerModule(int idModule) throws SQLException {
        String sql = "DELETE FROM module WHERE ID_MODULE = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idModule);
            statement.executeUpdate();
        }
    }
}
