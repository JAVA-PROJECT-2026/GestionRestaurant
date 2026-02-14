/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import main.model.dao.CommandeDAO;
import main.model.dao.LigneCommandeDAO;
import main.model.dao.StockDAO;
import main.model.entite.Commande;
import main.model.entite.LigneCommande;

import java.sql.SQLException;
import java.util.List;
import main.model.entite.enums.EtatCommande;
/**
 *Controller pour gérer la logique métier des commandes
 * @author isaac
 */
public class CommandeController {
    
    private CommandeDAO commandeDAO;
    private LigneCommandeDAO ligneCommandeDAO;
    private StockDAO stockDAO;
    
    public CommandeController(){
        this.commandeDAO = new CommandeDAO();
        this.ligneCommandeDAO = new LigneCommandeDAO();
        this.stockDAO = new StockDAO();
    }
    
    /**
     * Recupérer toutes les commandes
     */
    public List<Commande> getAllCommandes() throws SQLException{
        return commandeDAO.findAll();
    }
    
    /**
     * Recupérer une commande par son ID
     * @param id
     * @return 
     * @throws java.sql.SQLException
     */
    public Commande getCommandeById(String id)throws SQLException{
        return commandeDAO.findById(id);
    }
    
    public String creerCommande() throws SQLException{
        Commande commande = new Commande();
        commande.setTotalCommande(0.0);
        commande.setEtat(EtatCommande.EN_COURS);
        commandeDAO.insert(commande);
        return commande.getIdCom();
    }
    
    public void ajouterProduitCommande(String Idcom, String Idprod, int quantite, double prix) throws SQLException, IllegalArgumentException{
        if (!stockDAO.hasEnoughStock(Idprod,quantite)){
            throw new IllegalArgumentException("Stock Insuffisant pour ce produit");
        }
        
        LigneCommande ligne = new LigneCommande();
        ligne.setIdCom(Idcom);
        ligne.setIdProd(Idprod);
        ligne.setQuantite(quantite);
        ligne.setPrixUnitaire(prix);
        
        ligneCommandeDAO.insert(ligne);
        recalculerMontantTotal(Idcom);
    }
    
    /**
     * Supprimer un produit d'une commande
     * @param idCom
     * @throws java.sql.SQLException
     */
    public void supprimerProduitDeCommande(String IdCom, String Idprod) throws SQLException {
        LigneCommande ligne = (LigneCommande) ligneCommandeDAO.findByCommande(idCom);
        if (ligne != null) {
            String Idcom = ligne.getIdCom();
            ligneCommandeDAO.delete(Idcom,Idprod);
            recalculerMontantTotal(Idcom);
        }
    }
    private void recalculerMontantTotal(String commandeId) throws SQLException {
        List<LigneCommande> lignes = ligneCommandeDAO.findByCommande(commandeId);
        
        double total = 0.0;
        for (LigneCommande ligne : lignes) {
            total += ligne.getQuantite() * ligne.getPrixUnitaire();
        }
        
        Commande commande = commandeDAO.findById(commandeId);
        commande.setTotalCommande(total);
        commandeDAO.update(commande);
    }
    public void validerCommande(String commandeId) throws SQLException, IllegalArgumentException {
        Commande commande = commandeDAO.findById(commandeId);
        
        if (commande == null) {
            throw new IllegalArgumentException("Commande introuvable");
        }
        
        if (commande.getEtat() != EtatCommande.EN_COURS) {
            throw new IllegalArgumentException(
                "Seules les commandes en attente peuvent être validées"
            );
        }
        
        // Vérifier et diminuer le stock pour chaque ligne
        List<LigneCommande> lignes = ligneCommandeDAO.findByCommande(commandeId);
        
        // D'abord vérifier que tout le stock est disponible
        for (LigneCommande ligne : lignes) {
            if (!stockDAO.hasEnoughStock(ligne.getIdProd(), ligne.getQuantite())) {
                throw new IllegalArgumentException(
                    "Stock insuffisant pour le produit ID: " + ligne.getProduitId()
                );
            }
        }
        
        // Ensuite diminuer le stock
        for (LigneCommande ligne : lignes) {
            stockDAO.updateStock(ligne);
        }
        
        // Changer le statut
        commande.setEtat(EtatCommande.VALIDEE);
        commandeDAO.update(commande);
    }
    
}
