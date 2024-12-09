package absence.Dao;

import absence.Modeles.Seance;
import javafx.scene.Scene;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SeanceDAO {
    private Connection connection;

    public SeanceDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public int ajouterSeance(Seance seance) throws SQLException {
        String sql = "INSERT INTO seance (DATE_SEANCE, HEURE_DEBUT,HEURE_FIN, TYPE_SEANCE, ID_MODULE, ID_CLASSE,ID_USER) VALUES (?, ?, ?, ?, ?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(seance.getDateSeance()));
            statement.setTime(2, Time.valueOf(seance.getHeure_debut()));
            statement.setTime(3, Time.valueOf(seance.getHeure_fin()));
            statement.setString(4, seance.getTypeSeance());
            statement.setInt(5, seance.getIdModule());
            statement.setInt(6, seance.getIdClasse());
            statement.setInt(7, seance.getId_user());
            int ligneInsere =  statement.executeUpdate();
            if (ligneInsere > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                    if (resultSet.next()){
                        return resultSet.getInt(1);
                    }
            }
            return 0;
    }

    public List<Seance> obtenirToutesLesSeances() throws SQLException {
        String sql = "SELECT * FROM seance";
        List<Seance> seances = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Seance seance = new Seance();
                seance.setIdSeance(resultSet.getInt("ID_SEANCE"));
                Date date = resultSet.getDate("DATE_SEANCE");
                seance.setDateSeance(date.toLocalDate());
                Time time = resultSet.getTime("HEURE_DEBUT");
                seance.setHeure_debut(time.toLocalTime());
                time = resultSet.getTime("HEURE_FIN");
                seance.setHeure_fin(time.toLocalTime());
                seance.setIdModule(resultSet.getInt("ID_MODULE"));
                seance.setIdClasse(resultSet.getInt("ID_CLASSE"));
                seance.setId_user(resultSet.getInt("ID_USER"));
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
