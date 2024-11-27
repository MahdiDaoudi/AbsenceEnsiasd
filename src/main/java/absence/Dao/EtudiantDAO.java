package absence.Dao;

import absence.Modele.Etudiant;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class EtudiantDAO {

    // Méthode pour obtenir un étudiant par son ID
    public Etudiant getEtudiantById(String id) {
        String sql = "SELECT * FROM ETUDIANT WHERE ID_ETUDIANT = ?";
        Etudiant etudiant = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                etudiant = new Etudiant(
                        rs.getString("cne"),
                        rs.getString("NOM_ETUDIANT"),
                        rs.getString("PRENOM_ETUDIANT"),
                        rs.getString("EMAIL"),
                        rs.getString("TELEPHONE"),
                        rs.getString("SEXE"),
                        rs.getInt("ID_CLASSE")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiant;
    }

    // Méthode pour modifier un étudiant
    public boolean modifierEtudiant(Etudiant etudiant) {
        String sql = "UPDATE ETUDIANT SET NOM_ETUDIANT = ?, PRENOM_ETUDIANT = ?, EMAIL = ?, TELEPHONE = ?, SEXE = ?, ID_CLASSE = ? WHERE ID_ETUDIANT = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, etudiant.getNomEtudiant());
            pstmt.setString(2, etudiant.getPrenomEtudiant());
            pstmt.setString(3, etudiant.getEmail());
            pstmt.setString(4, etudiant.getTelephone());
            pstmt.setString(5, etudiant.getSexe());
            pstmt.setInt(6, etudiant.getIdClasse());
            pstmt.setString(7, etudiant.getIdEtudiant());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour supprimer un étudiant
    public boolean supprimerEtudiant(String idEtudiant) {
        String sql = "DELETE FROM ETUDIANT WHERE ID_ETUDIANT = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idEtudiant);

            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour ajouter un étudiant avec l'ID
    public boolean ajouterEtudiant(Etudiant etudiant) {
        String sql = "INSERT INTO ETUDIANT (ID_ETUDIANT, NOM_ETUDIANT, PRENOM_ETUDIANT, EMAIL, TELEPHONE, SEXE, ID_CLASSE) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, etudiant.getIdEtudiant());
            pstmt.setString(2, etudiant.getNomEtudiant());
            pstmt.setString(3, etudiant.getPrenomEtudiant());
            pstmt.setString(4, etudiant.getEmail());
            pstmt.setString(5, etudiant.getTelephone());
            pstmt.setString(6, etudiant.getSexe());
            pstmt.setInt(7, etudiant.getIdClasse());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}