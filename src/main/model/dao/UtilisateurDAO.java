package main.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.util.DatabaseConnection;
import main.model.entite.Utilisateur;

/**
 * DAO pour la gestion des utilisateurs
 * @author isaac
 */
public class UtilisateurDAO {

    /**
     * Insérer un nouvel utilisateur
     * @param utilisateur L'utilisateur à insérer
     * @return true si l'insertion a réussi
     */
    public boolean insert(Utilisateur utilisateur) {
        String sql = "INSERT INTO Utilisateur (id, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, utilisateur.getId());
            ps.setString(2, utilisateur.getEmail());  // ✅ CORRIGÉ: email
            ps.setString(3, utilisateur.getPassword());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de l'utilisateur: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mettre à jour un utilisateur
     * @param utilisateur L'utilisateur à mettre à jour
     * @return true si la mise à jour a réussi
     */
    public boolean update(Utilisateur utilisateur) {
        String sql = "UPDATE Utilisateur SET email = ?, password = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, utilisateur.getEmail());  // ✅ CORRIGÉ: email
            ps.setString(2, utilisateur.getPassword());
            ps.setString(3, utilisateur.getId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'utilisateur: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

<<<<<<< HEAD
    /**
     * Supprimer un utilisateur par ID
     * @param id L'ID de l'utilisateur à supprimer
     * @return true si la suppression a réussi
     */
    public boolean delete(String id) {
=======
    public void delete(Utilisateur utilisateur) {
>>>>>>> 3579b84 (correction de categoriedao)
        String sql = "DELETE FROM Utilisateur WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

<<<<<<< HEAD
            ps.setString(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
=======
            ps.setString(1, utilisateur.getId());
            ps.executeUpdate();
>>>>>>> 3579b84 (correction de categoriedao)

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'utilisateur: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Trouver un utilisateur par ID
     * @param id L'ID de l'utilisateur
     * @return L'utilisateur trouvé ou null
     */
    public Utilisateur findById(String id) {
        String sql = "SELECT * FROM Utilisateur WHERE id = ?";
        Utilisateur utilisateur = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getString("id"));
                utilisateur.setEmail(rs.getString("email"));  // ✅ CORRIGÉ: email
                utilisateur.setPassword(rs.getString("password"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'utilisateur: " + e.getMessage());
            e.printStackTrace();
        }

        return utilisateur;
    }

    /**
     * Trouver un utilisateur par email (pour la connexion)
     * @param email L'email de l'utilisateur
     * @return L'utilisateur trouvé ou null
     */
    public Utilisateur findByEmail(String email) {
        String sql = "SELECT * FROM Utilisateur WHERE email = ?";
        Utilisateur utilisateur = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getString("id"));
                utilisateur.setEmail(rs.getString("email"));  // ✅ CORRIGÉ: email
                utilisateur.setPassword(rs.getString("password"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par email: " + e.getMessage());
            e.printStackTrace();
        }

        return utilisateur;
    }

    /**
     * Récupérer tous les utilisateurs
     * @return Liste de tous les utilisateurs
     */
    public List<Utilisateur> findAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM Utilisateur";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(rs.getString("id"));
                utilisateur.setEmail(rs.getString("email"));  // ✅ CORRIGÉ: email
                utilisateur.setPassword(rs.getString("password"));

                utilisateurs.add(utilisateur);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des utilisateurs: " + e.getMessage());
            e.printStackTrace();
        }

        return utilisateurs;
    }
    
    /**
     * Vérifier si un email existe déjà
     * @param email L'email à vérifier
     * @return true si l'email existe
     */
    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM Utilisateur WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de l'email: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
}