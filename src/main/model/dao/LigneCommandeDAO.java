/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.dao;
import java.sql.*;

/**
 *
 * @author isaac
 */
import main.model.entite.LigneCommande;
import main.util.DatabaseConnection;
public class LigneCommandeDAO {
    public void insert(LigneCommande lignecommande) {
        String sql = "INSERT INTO LigneCommande(idCom, idProd, quantite, prixUnitaire, montantLigne) VALUES(?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
        
            
        
        }catch(SQLException e){
            
        }
        
    
    
    
    
    }
    
    
}
