/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.model.entite.Utilisateur;

/**
 *
 * @author isaac
 */
public class UtilisateurDAO {

    public void insert(Utilisateur utilisateur) {
        String sql = "INSERT INTO Utilisateur (id, login, password) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, utilisateur.getId());
            ps.setString(2, utilisateur.getLogin());
            ps.setString(3, utilisateur.getPassword());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Utilisateur utilisateur) {
        String sql = "UPDATE Utilisateur SET login = ?, password = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, utilisateur.getLogin());
            ps.setString(2, utilisateur.getPassword());
            ps.setString(3, utilisateur.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Utilisateur utilisateur) {
        String sql = "DELETE FROM Utilisateur WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, utilisateur.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                utilisateur.setLogin(rs.getString("login"));
                utilisateur.setPassword(rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }

    public Utilisateur findByLogin(String login) {
        String sql = "SELECT * FROM Utilisateur WHERE login = ?";
        Utilisateur utilisateur = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getString("id"));
                utilisateur.setLogin(rs.getString("login"));
                utilisateur.setPassword(rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }

    public List<Utilisateur> findAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM Utilisateur";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(rs.getString("id"));
                utilisateur.setLogin(rs.getString("login"));
                utilisateur.setPassword(rs.getString("password"));

                utilisateurs.add(utilisateur);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateurs;
    }
}