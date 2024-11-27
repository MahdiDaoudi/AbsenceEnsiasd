package absence.Dao;



import absence.Modele.Absence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbsenceDAO {
    private Connection connection;

    public AbsenceDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Créer une nouvelle absence
    public boolean ajouterAbsence(Absence absence) {
        String sql = "INSERT INTO absence (JUSTIFIE, MOTIF, ID_ETUDIANT, ID_SEANCE) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, absence.getJustifie()); // 0 ou 1 pour JUSTIFIE
            statement.setString(2, absence.getMotif());
            statement.setString(3, absence.getIdEtudiant());
            statement.setInt(4, absence.getIdSeance());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'absence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Lire toutes les absences d'un étudiant
    public List<Absence> getAbsencesByEtudiant(String idEtudiant) {
        List<Absence> absences = new ArrayList<>();
        String sql = "SELECT * FROM absence WHERE ID_ETUDIANT = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, idEtudiant);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Absence absence = new Absence(
                        rs.getInt("ID_ABSENCE"),
                        rs.getInt("JUSTIFIE"),
                        rs.getString("MOTIF"),
                        rs.getString("ID_ETUDIANT"),
                        rs.getInt("ID_SEANCE")
                );
                absences.add(absence);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des absences: " + e.getMessage());
            e.printStackTrace();
        }
        return absences;
    }

    // Mettre à jour une absence
    public boolean mettreAJourAbsence(Absence absence) {
        String sql = "UPDATE absence SET JUSTIFIE = ?, MOTIF = ?, ID_SEANCE = ? WHERE ID_ABSENCE = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, absence.getJustifie());
            statement.setString(2, absence.getMotif());
            statement.setInt(3, absence.getIdSeance());
            statement.setInt(4, absence.getIdAbsence());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'absence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Supprimer une absence
    public boolean supprimerAbsence(int idAbsence) {
        String sql = "DELETE FROM absence WHERE ID_ABSENCE = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idAbsence);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'absence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
