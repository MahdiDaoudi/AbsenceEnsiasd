package absence.Dao;

import absence.Modeles.Classe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClasseDAO {
    private Connection connection;

    public ClasseDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Ajouter une classe
    public void ajouterClasse(Classe classe) throws SQLException {
        String sql = "INSERT INTO classe (NOM_CLASSE, ID_FILIERE) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, classe.getNomClasse());
            statement.setInt(2, classe.getIdFiliere());
            statement.executeUpdate();
        }
    }

    // Obtenir toutes les classes
    public List<Classe> obtenirToutesLesClasses() throws SQLException {
        List<Classe> classes = new ArrayList<>();
        String sql = "SELECT * FROM classe";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Classe classe = new Classe(
                        resultSet.getInt("ID_CLASSE"),
                        resultSet.getString("NOM_CLASSE"),
                        resultSet.getInt("ID_FILIERE")
                );
                classes.add(classe);
            }
        }
        return classes;
    }

    // Modifier une classe
    public void modifierClasse(Classe classe) throws SQLException {
        String sql = "UPDATE classe SET NOM_CLASSE = ?, ID_FILIERE = ? WHERE ID_CLASSE = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, classe.getNomClasse());
            statement.setInt(2, classe.getIdFiliere());
            statement.setInt(3, classe.getIdClasse());
            statement.executeUpdate();
        }
    }

    // Supprimer une classe
    public void supprimerClasse(int idClasse) throws SQLException {
        String sql = "DELETE FROM classe WHERE ID_CLASSE = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idClasse);
            statement.executeUpdate();
        }
    }

    public  List<Classe> getClasseParFilier(int idDiliere) throws SQLException {
        List<Classe> classes = new ArrayList<>();
        String sql = "SELECT * from classe INNER JOIN filiere on classe.ID_FILIERE = filiere.ID_FILIERE WHERE filiere.id_filiere =?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idDiliere);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                classes.add(new Classe(resultSet.getInt("ID_CLASSE"), resultSet.getString("NOM_CLASSE"), resultSet.getInt("ID_FILIERE")));
            }
        }
        return classes;
    }

}
