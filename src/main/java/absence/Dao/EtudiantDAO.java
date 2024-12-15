package absence.Dao;

import absence.Modeles.Etudiant;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {

    // Méthode pour obtenir un étudiant par son ID
    public Etudiant getEtudiantById(String id) {
        String sql = "SELECT * FROM ETUDIANT WHERE cne = ?";
        Etudiant etudiant = null;

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                etudiant = new Etudiant(rs.getString("cne"), rs.getString("NOM_ETUDIANT"), rs.getString("PRENOM_ETUDIANT"), rs.getString("EMAIL"), rs.getString("TELEPHONE"), rs.getString("SEXE"), rs.getInt("ID_CLASSE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiant;
    }

    // Méthode pour modifier un étudiant
    public boolean modifierEtudiant(String ancienCne, Etudiant etudiant) {
        String sql = "UPDATE ETUDIANT SET CNE= ?, NOM_ETUDIANT = ?, PRENOM_ETUDIANT = ?, EMAIL = ?, TELEPHONE = ?, SEXE = ?, ID_CLASSE = ? WHERE cne = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, etudiant.getCne());
            pstmt.setString(2, etudiant.getNomEtudiant());
            pstmt.setString(3, etudiant.getPrenomEtudiant());
            pstmt.setString(4, etudiant.getEmail());
            pstmt.setString(5, etudiant.getTelephone());
            pstmt.setString(6, etudiant.getSexe());
            pstmt.setInt(7, etudiant.getIdClasse());
            pstmt.setString(8, ancienCne);

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void supprimerEtudiant(String cne) {
        String sql = "DELETE FROM ETUDIANT WHERE cne = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cne);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Méthode pour ajouter un étudiant avec l'ID
    public boolean ajouterEtudiant(Etudiant etudiant) {
        String sql = "INSERT INTO ETUDIANT (cne, NOM_ETUDIANT, PRENOM_ETUDIANT, EMAIL, TELEPHONE, SEXE, ID_CLASSE) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, etudiant.getCne());
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

    public List<Etudiant> getEtudiantsParClasse(int id_classe) throws SQLException {
        String sql = "Select * from Etudiant where id_classe = ?";
        List<Etudiant> etudiants = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id_classe);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            etudiants.add(new Etudiant(rs.getString("cne"), rs.getString("NOM_ETUDIANT"), rs.getString("PRENOM_ETUDIANT"), rs.getString("EMAIL"), rs.getString("TELEPHONE"), rs.getString("SEXE"), rs.getInt("ID_CLASSE")));
        }
        return etudiants;
    }

    public List<Etudiant> getEtudiants() throws SQLException {
        String sql = "SELECT e.*, c.NOM_CLASSE, f.NOM_FILIERE FROM ETUDIANT e " + "INNER JOIN CLASSE c ON c.ID_CLASSE = e.ID_CLASSE " + "INNER JOIN FILIERE f ON c.ID_FILIERE = f.ID_FILIERE";
        List<Etudiant> etudiants = new ArrayList<>();

        try{
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                etudiants.add(new Etudiant(rs.getString("cne"), rs.getString("NOM_ETUDIANT"), rs.getString("PRENOM_ETUDIANT"), rs.getString("EMAIL"), rs.getString("TELEPHONE"), rs.getString("SEXE"), rs.getInt("ID_CLASSE"), rs.getString("NOM_FILIERE"), rs.getString("NOM_CLASSE")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return etudiants;
    }
}