package absence.Dao;



import absence.Modeles.Absence;
import absence.Modeles.AbsenceTableView;
import absence.Modeles.Etudiant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbsenceDAO {
    private Connection connection;

    public AbsenceDAO() {
        this.connection = DatabaseConnection.getConnection();
    }


    // Créer une nouvelle absence
    public void ajouterAbsence(String cne,int id_seance) {
        String sql = "INSERT INTO absence (CNE, ID_SEANCE) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cne);
            statement.setInt(2, id_seance);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'absence: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void ajouterListeAbsence(List<Etudiant> etudiants,int id_seance) {
        for (Etudiant etudiant : etudiants) {
            ajouterAbsence(etudiant.getCne(),id_seance);
        }
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
    public void mettreAJourAbsence(int id_absence ,int justifie, String motif) {
        String sql = "UPDATE absence SET JUSTIFIE = ?, MOTIF = ? WHERE ID_ABSENCE = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, justifie);
            statement.setString(2, motif);
            statement.setInt(3, id_absence);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'absence: " + e.getMessage());
            e.printStackTrace();
        }
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

    public ArrayList<String> getAbsencesAujourdHui(String filier, String seance) {
        ArrayList<String> absencesAujourdHui = new ArrayList<>();
        String sql = "SELECT ETUDIANT.CNE, ETUDIANT.NOM_ETUDIANT, ETUDIANT.PRENOM_ETUDIANT, MODULE.NOM_MODULE, "
                + "SEANCE.TYPE_SEANCE, SEANCE.HEURE_DEBUT, SEANCE.HEURE_FIN,"
                + "TIMESTAMPDIFF(MINUTE, SEANCE.HEURE_DEBUT, SEANCE.HEURE_FIN) /60 AS DUREE "
                + "FROM ETUDIANT "
                + "INNER JOIN ABSENCE ON ETUDIANT.cne = ABSENCE.cne "
                + "INNER JOIN SEANCE ON ABSENCE.ID_SEANCE = SEANCE.ID_SEANCE "
                + "INNER JOIN MODULE ON SEANCE.ID_MODULE = MODULE.ID_MODULE "
                + "INNER JOIN CLASSE ON ETUDIANT.ID_CLASSE = CLASSE.ID_CLASSE "
                + "INNER JOIN FILIERE ON CLASSE.ID_FILIERE = FILIERE.ID_FILIERE "
                + "WHERE SEANCE.DATE_SEANCE = CURDATE() "
                + "AND FILIERE.NOM_FILIERE = ? AND CLASSE.NOM_CLASSE = ? ";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, filier);
            statement.setString(2, seance);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                absencesAujourdHui.add(rs.getString("cne") +
                        "\t\t\t" + rs.getString("NOM_ETUDIANT") + "\t\t\t" +
                        rs.getString("PRENOM_ETUDIANT") + "\t\t\t" +
                        rs.getString("NOM_MODULE") + "\t\t\t" +
                        rs.getString("TYPE_SEANCE") + "\t\t\t" +
                        rs.getTime("HEURE_DEBUT") + "\t\t\t" +
                        rs.getTime("HEURE_FIN") + "\t\t\t" +
                        rs.getInt("DUREE")+"h");
            }
            return absencesAujourdHui;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des absences de cette semaine: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    public ArrayList<AbsenceTableView> getTousAbsences() { // Henaya B9it Tal4eda inchaellah
        ArrayList<AbsenceTableView> absences = new ArrayList<>();
        String sql = "SELECT ABSENCE.ID_ABSENCE, ETUDIANT.CNE, ETUDIANT.NOM_ETUDIANT, ETUDIANT.PRENOM_ETUDIANT, MODULE.NOM_MODULE, "
                + "SEANCE.TYPE_SEANCE,SEANCE.DATE_SEANCE,"
                + "SEANCE.HEURE_DEBUT, SEANCE.HEURE_FIN , ABSENCE.JUSTIFIE, ABSENCE.MOTIF "
                + "FROM ETUDIANT "
                + "INNER JOIN ABSENCE ON ETUDIANT.cne = ABSENCE.cne "
                + "INNER JOIN SEANCE ON ABSENCE.ID_SEANCE = SEANCE.ID_SEANCE "
                + "INNER JOIN MODULE ON SEANCE.ID_MODULE = MODULE.ID_MODULE;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                absences.add(new AbsenceTableView(
                        rs.getInt("ID_ABSENCE"),
                        rs.getString("cne"),
                        rs.getString("NOM_ETUDIANT")+" "+
                        rs.getString("PRENOM_ETUDIANT"),
                        rs.getString("NOM_MODULE"),
                        rs.getString("TYPE_SEANCE"),
                        rs.getDate("DATE_SEANCE").toLocalDate(),
                        rs.getTime("HEURE_DEBUT").toLocalTime()+"-"+
                        rs.getTime("HEURE_FIN").toLocalTime(),
                        rs.getInt("JUSTIFIE") == 0 ? "Non" : "Oui",
                        rs.getString("MOTIF")
                ));
            }
            return absences;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des absences de cette semaine: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }
}

