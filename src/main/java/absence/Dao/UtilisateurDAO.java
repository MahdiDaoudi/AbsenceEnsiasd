package absence.Dao;

import absence.Modeles.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

    private Connection connection;

    // Constructeur pour initialiser la connexion à la base de données
    public UtilisateurDAO() {
       connection=DatabaseConnection.getConnection();
    }

    // Vérification des informations de connexion (email et mot de passe)
    public Utilisateur verifierLogin(String email, String password) {
        String sql = "SELECT * FROM utilisateur WHERE EMAIL = ? AND PASSWORD = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int idUser = resultSet.getInt("ID_User");
                String nomUser = resultSet.getString("NOM_USER");
                String prenomUser = resultSet.getString("PRENOM_USER");
                String telephone = resultSet.getString("TELEPHONE");
                String role = resultSet.getString("ROLE");

                return new Utilisateur(idUser, nomUser, prenomUser, email, password, telephone, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Retourne null si l'utilisateur n'est pas trouvé
    }

    // Création d'un utilisateur
    public void creerUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateur (NOM_USER, PRENOM_USER, EMAIL, PASSWORD, TELEPHONE, ROLE) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, utilisateur.getNOM_USER());
            preparedStatement.setString(2, utilisateur.getPRENOM_USER());
            preparedStatement.setString(3, utilisateur.getEMAIL());
            preparedStatement.setString(4, utilisateur.getPASSWORD());
            preparedStatement.setString(5, utilisateur.getTELEPHONE());
            preparedStatement.setString(6, utilisateur.getROLE());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lecture de tous les utilisateurs
    public List<Utilisateur> lireUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idUser = resultSet.getInt("ID_User");
                String nomUser = resultSet.getString("NOM_USER");
                String prenomUser = resultSet.getString("PRENOM_USER");
                String email = resultSet.getString("EMAIL");
                String password = resultSet.getString("PASSWORD");
                String telephone = resultSet.getString("TELEPHONE");
                String role = resultSet.getString("ROLE");
                utilisateurs.add(new Utilisateur(idUser, nomUser, prenomUser, email, password, telephone, role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    // Mise à jour d'un utilisateur
    public void mettreAJourUtilisateur(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateur SET NOM_USER = ?, PRENOM_USER = ?, EMAIL = ?, PASSWORD = ?, TELEPHONE = ?, ROLE = ? WHERE ID_User = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, utilisateur.getNOM_USER());
            preparedStatement.setString(2, utilisateur.getPRENOM_USER());
            preparedStatement.setString(3, utilisateur.getEMAIL());
            preparedStatement.setString(4, utilisateur.getPASSWORD());
            preparedStatement.setString(5, utilisateur.getTELEPHONE());
            preparedStatement.setString(6, utilisateur.getROLE());
            preparedStatement.setInt(7, utilisateur.getID_User());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Suppression d'un utilisateur
    public void supprimerUtilisateur(int idUser) {
        String sql = "DELETE FROM utilisateur WHERE ID_User = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idUser);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
