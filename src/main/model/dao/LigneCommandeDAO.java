package main.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.model.entite.LigneCommande;
import main.util.DatabaseConnection;

/
public class LigneCommandeDAO {

  
    public void insert(LigneCommande lc) {
        String sql = "INSERT INTO LigneCommande(idCom, idProd, quantite, prixUnitaire, montantLigne) VALUES(?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Calcul du montant avant insertion
            double montant = lc.getQuantite() * lc.getPrixUnitaire();
            
            ps.setString(1, lc.getIdCom());
            ps.setString(2, lc.getIdProd());
            ps.setInt(3, lc.getQuantite());
            ps.setDouble(4, lc.getPrixUnitaire());
            ps.setDouble(5, montant);

            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void update(LigneCommande lc) {
        String sql = "UPDATE LigneCommande SET quantite = ?, prixUnitaire = ?, montantLigne = ? WHERE idCom = ? AND idProd = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            double montant = lc.getQuantite() * lc.getPrixUnitaire();

            ps.setInt(1, lc.getQuantite());
            ps.setDouble(2, lc.getPrixUnitaire());
            ps.setDouble(3, montant);
            ps.setString(4, lc.getIdCom());
            ps.setString(5, lc.getIdProd());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void delete(String idCom, String idProd) {
        String sql = "DELETE FROM LigneCommande WHERE idCom = ? AND idProd = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idCom);
            ps.setString(2, idProd);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  
    public List<LigneCommande> findByCommande(String idCom) {
        List<LigneCommande> lignes = new ArrayList<>();
        String sql = "SELECT * FROM LigneCommande WHERE idCom = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idCom);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LigneCommande lc = new LigneCommande(
                    rs.getString("idCom"),
                    rs.getString("idProd"),
                    rs.getInt("quantite"),
                    rs.getDouble("prixUnitaire")
                );
                
                lignes.add(lc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lignes;
    }
}