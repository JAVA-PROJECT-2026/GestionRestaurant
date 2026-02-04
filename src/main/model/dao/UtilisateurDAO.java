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
    public void insert(Utilisateur utilisateur) throws SQLException{
        String sql = "INSERT INTO Utilisateur (id, login, password) VALUES (?, ?, ?)";
        
        try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, utilisateur.getId());
            ps.setString(2, utilisateur.getLogin());
            ps.setString(3, utilisateur.getPassword());
            
            ps.executeUpdate();
           
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void update(Utilisateur utilisateur) throws SQLException{
        String sql = "UPDATE Utilisateur SET login =? ,password = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            
            ps.setString(1, utilisateur.getLogin());
            ps.setString(2, utilisateur.getPassword());
            ps.setString(1, utilisateur.getId());
            
            ps.executeUpdate();
            
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public void delete(String id){
        String sql = "DELETE FROM Utilisateur WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setString(1, id);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Utilisateur findBy(String id){
        String sql = "SELECT * FROM Utilisateur WHERE id = ?";
        Utilisateur utilisateur = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getString("id"));
                utilisateur.setLogin(rs.getString("login"));
                utilisateur.setPassword(rs.getString("password"));
            }
        
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return utilisateur;
    }
    
    public Utilisateur findByLogin(String login){
        String sql ="SELECT * FROM UTILISATEUR  WHERE login = ?";
        Utilisateur utilisateur = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()){
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getString("id"));
                utilisateur.setLogin(rs.getString("login"));
                utilisateur.setPassword(rs.getString("password"));
            }
            
        }catch (SQLException e){
            e.printStackTrace();
        }
        return utilisateur;
    }
    
    public List<Utilisateur> findAll(){}
}
