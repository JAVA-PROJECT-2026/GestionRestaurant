
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.model.entite.LigneCommande;
import main.util.DatabaseConnection;

/**
 * DAO pour la gestion des lignes de commande
 * @author isaac
 */
public class LigneCommandeDAO {
    
    /**
     * Insérer une nouvelle ligne de commande
     * @param ligne La ligne de commande à insérer
     * @return true si l'insertion a réussi
     */
    public boolean insert(LigneCommande ligne) {
        String sql = "INSERT INTO LigneCommande (idCom, idProd, quantite, prixUnitaire) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, ligne.getIdCom());
            pst.setString(2, ligne.getIdProd());
            pst.setInt(3, ligne.getQuantite());
            pst.setDouble(4, ligne.getPrixUnitaire());
           
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la ligne de commande: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Mettre à jour une ligne de commande (quantité)
     * @param ligne La ligne de commande à mettre à jour
     * @return true si la mise à jour a réussi
     */
    public boolean update(LigneCommande ligne) {
        String sql = "UPDATE LigneCommande SET quantite = ?, prixUnitaire = ? WHERE idCom = ? AND idProd = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, ligne.getQuantite());
            pst.setDouble(2, ligne.getPrixUnitaire());
            pst.setString(3, ligne.getIdCom());
            pst.setString(4, ligne.getIdProd());
           
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de la ligne: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Supprimer une ligne de commande
     * @param idCom L'ID de la commande
     * @param idProd L'ID du produit
     * @return true si la suppression a réussi
     */
    public boolean delete(String idCom, String idProd) {
        String sql = "DELETE FROM LigneCommande WHERE idCom = ? AND idProd = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, idCom);
            pst.setString(2, idProd);
           
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la ligne: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Supprimer toutes les lignes d'une commande
     * @param idCom L'ID de la commande
     * @return true si la suppression a réussi
     */
    public boolean deleteAllByCommande(String idCom) {
        String sql = "DELETE FROM LigneCommande WHERE idCom = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, idCom);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression des lignes: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Récupérer toutes les lignes d'une commande
     * @param idCom L'ID de la commande
     * @return Liste des lignes de cette commande
     */
    public List<LigneCommande> findByCommande(String idCom) {
        List<LigneCommande> lignes = new ArrayList<>();
        String sql = "SELECT * FROM LigneCommande WHERE idCom = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, idCom);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                LigneCommande ligne = new LigneCommande();
                ligne.setIdCom(rs.getString("idCom"));
                ligne.setIdProd(rs.getString("idProd"));
                ligne.setQuantite(rs.getInt("quantite"));
                ligne.setPrixUnitaire(rs.getDouble("prixUnitaire"));
                
                lignes.add(ligne);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des lignes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lignes;
    }
    
    /**
     * Récupérer les lignes avec détails des produits (jointure)
     * @param idCom L'ID de la commande
     * @return Liste des lignes avec noms de produits
     */
    public List<Object[]> findByCommandeWithDetails(String idCom) {
        List<Object[]> lignes = new ArrayList<>();
        String sql = "SELECT lc.*, p.nom, p.stockActuel " +
                     "FROM LigneCommande lc " +
                     "JOIN Produit p ON lc.idProd = p.idProd " +
                     "WHERE lc.idCom = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, idCom);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Object[] ligne = new Object[6];
                ligne[0] = rs.getString("idProd");
                ligne[1] = rs.getString("nom");
                ligne[2] = rs.getInt("quantite");
                ligne[3] = rs.getDouble("prixUnitaire");
                ligne[4] = rs.getDouble("montantLigne");
                ligne[5] = rs.getInt("stockActuel");
                
                lignes.add(ligne);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des détails: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lignes;
    }
    
    /**
     * Calculer le total d'une commande
     * @param idCom L'ID de la commande
     * @return Le montant total
     */
    public double calculateTotal(String idCom) {
        String sql = "SELECT SUM(montantLigne) as total FROM LigneCommande WHERE idCom = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, idCom);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors du calcul du total: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0.0;
    }
    
    /**
     * Vérifier si un produit existe dans une commande
     * @param idCom L'ID de la commande
     * @param idProd L'ID du produit
     * @return true si le produit est déjà dans la commande
     */
    public boolean exists(String idCom, String idProd) {
        String sql = "SELECT COUNT(*) as nb FROM LigneCommande WHERE idCom = ? AND idProd = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, idCom);
            pst.setString(2, idProd);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("nb") > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Obtenir les produits les plus vendus
     * @param limite Nombre de produits à retourner
     * @return Liste des produits avec leur quantité vendue
     */
    public List<Object[]> getTopProduits(int limite) {
        List<Object[]> top = new ArrayList<>();
        String sql = "SELECT p.nom, SUM(lc.quantite) as totalVendu " +
                     "FROM LigneCommande lc " +
                     "JOIN Produit p ON lc.idProd = p.idProd " +
                     "JOIN Commande c ON lc.idCom = c.idCom " +
                     "WHERE c.etat = 'VALIDEE' " +
                     "GROUP BY lc.idProd, p.nom " +
                     "ORDER BY totalVendu DESC " +
                     "LIMIT ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, limite);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Object[] item = new Object[2];
                item[0] = rs.getString("nom");
                item[1] = rs.getInt("totalVendu");
                top.add(item);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du top produits: " + e.getMessage());
            e.printStackTrace();
        }
        
        return top;
    }
}