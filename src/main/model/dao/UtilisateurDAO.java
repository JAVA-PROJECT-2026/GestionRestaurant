/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.model.entite.Utilisateur;
import main.util.DatabaseConnection;
/**
 *
 * @author Isaac
 */
public class UtilisateurDAO {

    // Vérifier si l'email + password correspondent à un utilisateur en base
    public Utilisateur findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM Utilisateur WHERE email = ? AND password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getString("id"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                return u;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // aucun utilisateur trouvé
    }

    // Vérifier si un email existe déjà
    public boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM Utilisateur WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Insérer un nouvel utilisateur
    public boolean insert(Utilisateur utilisateur) {
        String sql = "INSERT INTO Utilisateur (id, email, password) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, utilisateur.getId());
            ps.setString(2, utilisateur.getEmail());
            ps.setString(3, utilisateur.getPassword());
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}