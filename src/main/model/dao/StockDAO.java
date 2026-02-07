/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.dao;

import main.model.entite.Produit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.model.entite.MouvementStock;
import main.model.entite.enums.TypeMouvement;

/**
 *
 * @author isaac
 */

// DAO pour insert/ajouter un user dans la base de donnée
public class StockDAO {
    public void insertStock(MouvementStock stock) {
        String sql = "INSERT INTO Stock (typeMouv, idProd, quantite, dateMouv, motif";
        
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) {
            
            pst.setString(1, stock.getTypeMouv());
            pst.setString(2, stock.getIdProd());
            pst.setInt(3, stock.getQuantite());
            pst.setDate(4, (Date) stock.getDateMouv());
            pst.setString(5, stock.getMotif());
            
            pst.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    // DAO pour récuper tous les stocks dans la db 
    public List<MouvementStock> getAllStock() {
        List<MouvementStock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM MouvementStock";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                
             ) {
            
            while (rs.next()) {
                MouvementStock stock = new MouvementStock();
                stock.setDateMouv(rs.getDate("dateMouv"));
                stock.setIdProd(rs.getString("idprod"));
                stock.setIdMouv(rs.getString("idMouv"));
                stock.setTypeMouv(rs.getString("typeMouv"));
                stock.setQuantite(rs.getInt("quantite"));
                stock.setMotif(rs.getString("motif"));
                
                stocks.add(stock);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return stocks;
    }
}



