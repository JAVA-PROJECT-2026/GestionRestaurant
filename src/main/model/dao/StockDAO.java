/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.dao;

import main.model.entite.Produit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author isaac
 */
public class StockDAO {
    public void insertStock(Stock stock) {
        String sql = "INSERT INTO Stock (typeMouv, idProd, quantite, dateMouv, motif");
        
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement pst = PrepareStaement(sql);) {
            
            pst.setString(1, st);
        }
    }
}
