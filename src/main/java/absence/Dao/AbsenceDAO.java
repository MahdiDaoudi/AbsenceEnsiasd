package absence.Dao;



import absence.Modele.Absence;
import absence.Modele.Etudiant;
import absence.Modele.EtudiantAVertissement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbsenceDAO {
    private  Connection connection;

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
    public int getNombreAbsencesAujourdHui() {
        String sql = "SELECT COUNT(*) AS total FROM absence a " +
                "JOIN seance s ON a.ID_SEANCE = s.ID_SEANCE " +
                "WHERE DATE(s.DATE_SEANCE) = CURDATE()";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des absences d'aujourd'hui: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    public int getNombreAbsencesCetteSemaine() {
        String sql = "SELECT COUNT(*) AS total FROM absence a " +
                "JOIN seance s ON a.ID_SEANCE = s.ID_SEANCE " +
                "WHERE WEEK(s.DATE_SEANCE, 1) = WEEK(CURDATE(), 1) " +
                "AND YEAR(s.DATE_SEANCE) = YEAR(CURDATE())";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des absences de cette semaine: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    public int getNombreAbsencesCeMois() {
        String sql = "SELECT COUNT(*) AS total FROM absence a " +
                "JOIN seance s ON a.ID_SEANCE = s.ID_SEANCE " +
                "WHERE MONTH(s.DATE_SEANCE) = MONTH(CURDATE()) " +
                "AND YEAR(s.DATE_SEANCE) = YEAR(CURDATE())";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des absences de ce mois: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    public List<EtudiantAVertissement> getAbsencesParEtudiant() {
        List<EtudiantAVertissement> absencesList = new ArrayList<>();
        String query = "SELECT e.cne, e.NOM_ETUDIANT, e.PRENOM_ETUDIANT, e.EMAIL, e.TELEPHONE, e.SEXE, e.ID_CLASSE, " +
                "COUNT(a.ID_ABSENCE) AS nombre_absences, MAX(s.DATE_SEANCE) AS derniere_absence " +  // Récupération de la dernière date d'absence
                "FROM absence a " +
                "JOIN etudiant e ON a.ID_ETUDIANT = e.cne " +
                "JOIN seance s ON a.ID_SEANCE = s.ID_SEANCE " +  // Join avec la table seance pour obtenir la date
                "WHERE a.JUSTIFIE = 0 " +  // Filtrer les absences non justifiées
                "GROUP BY e.cne " +
                "ORDER BY MAX(s.DATE_SEANCE) DESC";  // Tri par la date la plus récente d'absence

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Parcourir les résultats et les ajouter à la liste
            while (resultSet.next()) {
                String cne = resultSet.getString("cne");
                String nomEtudiant = resultSet.getString("NOM_ETUDIANT");
                String prenomEtudiant = resultSet.getString("PRENOM_ETUDIANT");
                String email = resultSet.getString("EMAIL");
                String telephone = resultSet.getString("TELEPHONE");
                String sexe = resultSet.getString("SEXE");
                int idClasse = resultSet.getInt("ID_CLASSE");
                int nombreAbsences = resultSet.getInt("nombre_absences"); // Nombre d'absences

                // Multiplier le nombre d'absences par 2
                int nombreAbsencesMultiplié = nombreAbsences * 2;

                // Créer un objet Etudiant avec les informations récupérées
                Etudiant etudiant = new Etudiant(
                        cne, nomEtudiant, prenomEtudiant, email, telephone, sexe, idClasse);
                EtudiantAVertissement etudiantAbsence = new EtudiantAVertissement(etudiant, nombreAbsencesMultiplié);

                absencesList.add(etudiantAbsence);
                System.out.println(etudiantAbsence);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return absencesList;
    }





}
