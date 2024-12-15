//package absence.Dao;
//
//import absence.Modeles.EnvoiMail;
//
//import javax.mail.*;
//import javax.mail.internet.*;
//import java.sql.*;
//import java.util.ArrayList;
////import java.util.List;
//import java.util.Properties;
//
//public class EnvoiMailDAO {
//    private Connection connection;
//    private final String fromEmail = "Ensiasdil@gmail.com"; // L'email de l'expéditeur
//    private final String password = "ayujkoaxtfuhjxhl"; // Le mot de passe de l'expéditeur
//
//    // Constructeur pour initialiser la connexion à la base de données
//    public EnvoiMailDAO() {
//        this.connection = DatabaseConnection.getConnection();
//    }
//
//    // Méthode pour envoyer un e-mail à un seul étudiant
//    public void envoyerEmail(String emailDestinataire, String message,String nom) {
//        String subject = "Notification d'absence"; // Sujet de l'email
//
//        // Configuration des propriétés pour la connexion SMTP (exemple avec Gmail)
//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//
//        // Authentification de l'expéditeur
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(fromEmail, password);
//            }
//        });
//
//        try {
//            // Création du message
//            MimeMessage messageEmail = new MimeMessage(session);
//            messageEmail.setFrom(new InternetAddress(fromEmail));
//            messageEmail.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDestinataire));
//            messageEmail.setSubject(subject);
//            messageEmail.setText("Bonjour "+nom +",\n" +
//                    "\n" +
//                    "Nous souhaitons vous informer que vous avez dépassé le nombre d'heures d'absence pour vos cours. et vous avez "+message +" Cette situation peut avoir un impact sur votre suivi académique et votre participation aux évaluations.\n" +
//                    "\n" +
//                    "Nous vous encourageons à prendre contact avec les responsables pédagogiques afin de régulariser votre situation et d'explorer les solutions possibles.\n" +
//                    "\n" +
//                    "Merci de votre compréhension.\n" +
//                    "\n" +
//                    "Cordialement, ");
//
//
//            // Envoi du message
//            Transport.send(messageEmail);
//            System.out.println("E-mail envoyé avec succès à: " + emailDestinataire);
//
//            // Enregistrement de l'envoi dans la base de données
//            enregistrerEnvoi(emailDestinataire, message);
//
//        } catch (MessagingException e) {
//            System.out.println("Erreur lors de l'envoi de l'e-mail: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    // Méthode pour enregistrer un envoi d'e-mail dans la base de données
//    public void enregistrerEnvoi(String emailDestinataire, String message) {
//        String sql = "INSERT INTO envoi_mail (email_destinataire, message) VALUES (?, ?)";
//
//        try (PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, emailDestinataire);
//            statement.setString(2, message);
//
//            int rowsAffected = statement.executeUpdate();
//            if (rowsAffected > 0) {
//                System.out.println("E-mail enregistré avec succès pour: " + emailDestinataire);
//            }
//        } catch (SQLException e) {
//            System.out.println("Erreur lors de l'enregistrement de l'envoi: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    public List<EnvoiMail> recupererEnvois() {
//        List<EnvoiMail> envois = new ArrayList<>();
//
//        String query = "SELECT * FROM envoi_mail"; // Assurez-vous que le nom de la table correspond à celui de votre base de données
//
//        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(query)) {
//            while (rs.next()) {
//                EnvoiMail envoi = new EnvoiMail(
//                        rs.getInt("id_mail"),
//                        rs.getString("email_destinataire"),
//                        rs.getString("message"),
//                        rs.getTimestamp("date_envoi")
//                );
//                envois.add(envoi);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // Gérer l'exception
//        }
//        return envois;
//    }
//}
//
