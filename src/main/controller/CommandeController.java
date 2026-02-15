/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import main.model.dao.CommandeDAO;
import main.model.dao.LigneCommandeDAO;
import main.model.dao.StockDAO;
import main.model.dao.ProduitDAO;
import main.model.entite.Commande;
import main.model.entite.LigneCommande;
import main.model.entite.Produit;
import main.model.entite.MouvementStock;
import main.model.entite.enums.TypeMouvement;

import java.sql.SQLException;
import java.util.List;
import java.util.Date;
import main.model.entite.enums.EtatCommande;

/**
 * Controller pour gérer la logique métier des commandes
 * @author isaac
 */
public class CommandeController {
    
    private CommandeDAO commandeDAO;
    private LigneCommandeDAO ligneCommandeDAO;
    private StockDAO stockDAO;
    private ProduitDAO produitDAO;
    
    public CommandeController(){
        this.commandeDAO = new CommandeDAO();
        this.ligneCommandeDAO = new LigneCommandeDAO();
        this.stockDAO = new StockDAO();
        this.produitDAO = new ProduitDAO();
    }
    
    /**
     * Recupérer toutes les commandes
     * @return 
     * @throws java.sql.SQLException
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
    public Commande getCommandeById(String id) throws SQLException{
        return commandeDAO.findById(id);
    }
    
    /**
     * Créer une nouvelle commande
     * @return L'ID de la commande créée
     * @throws java.sql.SQLException
     */
    public String creerCommande() throws SQLException{
        Commande commande = new Commande();
        commande.setTotalCommande(0.0);
        commande.setEtat(EtatCommande.EN_COURS);
        commandeDAO.insert(commande);
        return commande.getIdCom();
    }
    
    /**
     * Ajouter un produit à une commande
     * @param idCom ID de la commande
     * @param idProd ID du produit
     * @param quantite Quantité à ajouter
     * @param prix Prix unitaire
     * @throws java.sql.SQLException
     * @throws java.lang.IllegalArgumentException
     */
    public void ajouterProduitCommande(String idCom, String idProd, int quantite, double prix) throws SQLException, IllegalArgumentException{
        if (!stockDAO.hasEnoughStock(idProd, quantite)){
            throw new IllegalArgumentException("Stock Insuffisant pour ce produit");
        }
        
        LigneCommande ligne = new LigneCommande();
        ligne.setIdCom(idCom);
        ligne.setIdProd(idProd);
        ligne.setQuantite(quantite);
        ligne.setPrixUnitaire(prix);
        
        ligneCommandeDAO.insert(ligne);
        recalculerMontantTotal(idCom);
    }
    
    /**
     * Supprimer un produit d'une commande
     * @param idCom ID de la commande
     * @param idProd ID du produit
     * @throws java.sql.SQLException
     */
    public void supprimerProduitDeCommande(String idCom, String idProd) throws SQLException {
        List<LigneCommande> lignes = ligneCommandeDAO.findByCommande(idCom);
        
        boolean found = false;
        for (LigneCommande ligne : lignes) {
            if (ligne.getIdProd().equals(idProd)) {
                ligneCommandeDAO.delete(idCom, idProd);
                found = true;
                break;
            }
        }
        
        if (found) {
            recalculerMontantTotal(idCom);
        }
    }
    
    /**
     * Recalculer le montant total d'une commande
     * @param commandeId ID de la commande
     * @throws java.sql.SQLException
     */
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
    
    /**
     * Valider une commande et mettre à jour le stock
     * @param commandeId ID de la commande
     * @throws java.sql.SQLException
     * @throws java.lang.IllegalArgumentException
     */
    public void validerCommande(String commandeId) throws SQLException, IllegalArgumentException {
        Commande commande = commandeDAO.findById(commandeId);
        
        if (commande == null) {
            throw new IllegalArgumentException("Commande introuvable");
        }
        
        if (commande.getEtat() != EtatCommande.EN_COURS) {
            throw new IllegalArgumentException(
                "Seules les commandes en cours peuvent être validées"
            );
        }
        
        // Vérifier et diminuer le stock pour chaque ligne
        List<LigneCommande> lignes = ligneCommandeDAO.findByCommande(commandeId);
        
        // D'abord vérifier que tout le stock est disponible
        for (LigneCommande ligne : lignes) {
            if (!stockDAO.hasEnoughStock(ligne.getIdProd(), ligne.getQuantite())) {
                throw new IllegalArgumentException(
                    "Stock insuffisant pour le produit ID: " + ligne.getIdProd()
                );
            }
        }
        
        // Ensuite diminuer le stock et créer les mouvements
        for (LigneCommande ligne : lignes) {
            // Récupérer le produit
            Produit produit = produitDAO.findById(ligne.getIdProd());
            
            // Diminuer la quantité en stock
            int nouvelleQuantite = produit.getStockActuel()- ligne.getQuantite();
            produit.setStockActuel(nouvelleQuantite);
            produitDAO.updateProduit(produit);
            
            // Créer un mouvement de sortie
            MouvementStock mouvement = new MouvementStock();
            mouvement.setTypeMouv(TypeMouvement.SORTIE.name());
            mouvement.setIdProd(ligne.getIdProd());
            mouvement.setQuantite(ligne.getQuantite());
            mouvement.setDateMouv(new Date());
            mouvement.setMotif("Validation commande " + commandeId);
            
            stockDAO.insertStock(mouvement);
        }
        
        // Changer le statut
        commande.setEtat(EtatCommande.VALIDEE);
        commandeDAO.update(commande);
    }
}