/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.dao;
import java.sql.*;
import main.model.entite.Commande;
import main.model.entite.enums.EtatCommande;


/**
 *
 * @author isaac
 */
public class CommandeDAO {
    public void insert (Commande commande){
        String sql = "INSERT INTO Commande (idCom, dateCommande, etat , total) VALUES (?, ?, ?, ?)";
        
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql))
        {
            pst.setString(1, commande.getIdCom());
            pst.setDate(2, (Date) commande.getDateCommande());
            pst.setString(3, commande.getEtat().name());
            pst.setDouble(4, commande.getTotalCommande());
           
            pst.executeUpdate();
            
        }catch (SQLException e){
            e.printStackTrace();
        }
        
    }
 
    //A revoir .
    
    public void update(Commande commande){
        String sql = "UPDATE Commande  SET etat = ? WHERE idCom = ?";
        
        
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql))
        {
            pst.setString(1, commande.getIdCom());
            pst.setDate(2, (Date) commande.getDateCommande());
            pst.setString(3, commande.getEtat().name());
            pst.setDouble(4, commande.getTotalCommande());
           
            pst.executeUpdate();
            
        }catch (SQLException e){
            e.printStackTrace();
        }
    
    }
    
      // DELETE
    public void delete(Commande commande) {
        String sql = "DELETE FROM Commande WHERE idCom = ? ";

           
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql))
        {
            pst.setString(1, commande.getIdCom());
           
           
            pst.executeUpdate();
            
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
