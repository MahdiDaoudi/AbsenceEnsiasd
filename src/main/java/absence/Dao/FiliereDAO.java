package absence.Dao;

import absence.Modeles.Filiere;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FiliereDAO {
    private Connection connection;

    public FiliereDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void ajouterFiliere(String nomFiliere) throws SQLException {
        String sql = "INSERT INTO filiere (NOM_FILIERE) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nomFiliere);
            statement.executeUpdate();
        }
    }

    public List<Filiere> obtenirToutesLesFilieres() throws SQLException {
        String sql = "SELECT * FROM filiere";
        List<Filiere> filieres = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Filiere filiere = new Filiere(resultSet.getInt("ID_FILIERE"),
                        resultSet.getString("NOM_FILIERE"));
                filieres.add(filiere);
            }
        }
        return filieres;
    }

    public void modifierFiliere(int idFiliere, String nomFiliere) throws SQLException {
        String sql = "UPDATE filiere SET NOM_FILIERE = ? WHERE ID_FILIERE = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nomFiliere);
            statement.setInt(2, idFiliere);
            statement.executeUpdate();
        }
    }

    public void supprimerFiliere(int idFiliere) throws SQLException {
        String sql = "DELETE FROM filiere WHERE ID_FILIERE = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idFiliere);
            statement.executeUpdate();
        }
    }


}
