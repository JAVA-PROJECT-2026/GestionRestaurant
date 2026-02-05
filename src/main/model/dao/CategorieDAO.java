/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.model.entite.Categorie;

/**
 *
 * @author isaac
 */
public class CategorieDAO {
    public void insert(Categorie categorie) {
        String sql = "INSERT INTO Categorie (idCat, libelle) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, categorie.getIdCat());
            ps.setString(2, categorie.getLibelle());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(Categorie categorie) {
        String sql = "UPDATE Categorie SET libelle = ? WHERE idCat = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, categorie.getLibelle());
            ps.setString(2, categorie.getIdCat());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void delete(String idCat) {
        String sql = "DELETE FROM Categorie WHERE idCat = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idCat);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // FIND BY ID
    public Categorie findById(String idCat) {
        String sql = "SELECT * FROM Categorie WHERE idCat = ?";
        Categorie categorie = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idCat);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                categorie = new Categorie();
                categorie.setIdCat(rs.getString("idCat"));
                categorie.setLibelle(rs.getString("libelle"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorie;
    }

    // FIND ALL
    public List<Categorie> findAll() {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categorie";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setIdCat(rs.getString("idCat"));
                categorie.setLibelle(rs.getString("libelle"));

                categories.add(categorie);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
    
}
