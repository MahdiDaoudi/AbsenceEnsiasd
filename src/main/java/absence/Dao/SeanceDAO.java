package absence.Dao;

import absence.Modeles.Seance;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SeanceDAO {
    private Connection connection;

    public SeanceDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void ajouterSeance(LocalDateTime dateSeance, int duree, String typeSeance, int idModule, int idClasse) throws SQLException {
        String sql = "INSERT INTO seance (DATE_SEANCE, DUREE, TYPE_SEANCE, ID_MODULE, ID_CLASSE) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.valueOf(dateSeance));
            statement.setInt(2, duree);
            statement.setString(3, typeSeance);
            statement.setInt(4, idModule);
            statement.setInt(5, idClasse);
            statement.executeUpdate();
        }
    }

    public List<Seance> obtenirToutesLesSeances() throws SQLException {
        String sql = "SELECT * FROM seance";
        List<Seance> seances = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Seance seance = new Seance(
                        resultSet.getInt("ID_SEANCE"),
                        resultSet.getTimestamp("DATE_SEANCE").toLocalDateTime(),
                        resultSet.getInt("DUREE"),
                        resultSet.getString("TYPE_SEANCE"),
                        resultSet.getInt("ID_MODULE"),
                        resultSet.getInt("ID_CLASSE")
                );
                seances.add(seance);
            }
        }
        return seances;
    }

    public void modifierSeance(int idSeance, LocalDateTime dateSeance, int duree, String typeSeance, int idModule, int idClasse) throws SQLException {
        String sql = "UPDATE seance SET DATE_SEANCE = ?, DUREE = ?, TYPE_SEANCE = ?, ID_MODULE = ?, ID_CLASSE = ? WHERE ID_SEANCE = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.valueOf(dateSeance));
            statement.setInt(2, duree);
            statement.setString(3, typeSeance);
            statement.setInt(4, idModule);
            statement.setInt(5, idClasse);
            statement.setInt(6, idSeance);
            statement.executeUpdate();
        }
    }

    public void supprimerSeance(int idSeance) throws SQLException {
        String sql = "DELETE FROM seance WHERE ID_SEANCE = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idSeance);
            statement.executeUpdate();
        }
    }
}
