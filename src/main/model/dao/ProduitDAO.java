/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.dao;

import main.model.entite.Produit;

/**
 *
 * @author isaac
 */
public class ProduitDAO {
    
    // DAO pour inserer un produit
    public void insert(Produit produit){
        String sql = "INSERT INTO Produit (nom, idCategorie, prixDeVente, stockActuel, seuilAlerte) VALUES(?, ?, ?, ?, ?)";
        
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sql);
        } catch (){

        }
}
