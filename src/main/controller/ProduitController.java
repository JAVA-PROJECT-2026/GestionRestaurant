/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import java.util.List;
import main.model.dao.ProduitDAO;
import main.model.dao.LigneCommandeDAO;
import main.model.dao.CommandeDAO;
import main.model.dao.CategorieDAO;
import main.model.entite.Categorie;
import main.model.entite.Produit;
import main.model.entite.Commande;
import main.model.entite.enums.EtatCommande;

/**
 * Controller pour la gestion des produits
 * @author isaac
 */
public class ProduitController {
    // Les DAO dont on a besoin
    private ProduitDAO produitDAO;
    private CategorieDAO categorieDAO;
    private LigneCommandeDAO ligneCommandeDAO;
    private CommandeDAO commandeDAO;
    
    // Constructeur
    public ProduitController() {
        this.categorieDAO = new CategorieDAO();
        this.produitDAO = new ProduitDAO();
        this.ligneCommandeDAO=new LigneCommandeDAO();
        this.commandeDAO = new  CommandeDAO();
    }
    
    /**
     * Ajouter un nouveau produit avec validations
     * @param nom Nom du produit
     * @param idCategorie ID de la catégorie
     * @param prix Prix de vente
     * @param stock Stock actuel
     * @param seuil Seuil d'alerte
     * @return "OK" si succès, sinon message d'erreur
     */
    public String ajouterProduit(String nom, String idCategorie, double prix, int stock, int seuil) {
        // VALIDATIONS
        if (nom == null || nom.trim().isEmpty()) {
            return "Le nom du produit est obligatoire";
        }
        
        if (nom.length() > 150) {
            return "Le nom est trop long (max 150 caractères)";
        }
        
        if (prix <= 0) {
            return "Le prix doit être strictement positif";
        }
        
        if (stock < 0) {
            return "Le stock ne peut pas être négatif";
        }
        
        if (seuil < 0) {
            return "Le seuil d'alerte ne peut pas être négatif";
        }
        
        // Vérifier que la catégorie existe
        Categorie cat = categorieDAO.findById(idCategorie);
        if (cat == null) {
            return "Erreur : Catégorie inexistante";
        }
        
        // CRÉATION ET INSERTION
        try {
            Produit produit = new Produit(nom, prix, stock, seuil, idCategorie);
            boolean success = produitDAO.insertProduit(produit);
            
            if (success) {
                return "OK";
            } else {
                return "Erreur lors de l'enregistrement";
            }
        } catch (Exception e) {
            return "Erreur : " + e.getMessage();
        }
    }
    
    /**
     * Modifier un produit existant
     * @param idProduit ID du produit à modifier
     * @param nom Nouveau nom
     * @param idCategorie Nouvelle catégorie
     * @param prix Nouveau prix
     * @param stock Nouveau stock
     * @param seuil Nouveau seuil
     * @return "OK" si succès, sinon message d'erreur
     */
    public String modifierProduit(String idProduit, String nom, String idCategorie, 
                                   double prix, int stock, int seuil) { 
        
        if (nom == null || nom.trim().isEmpty()) {
            return "Le nom du produit est obligatoire";
        }
        
        if (nom.length() > 150) {
            return "Le nom est trop long (max 150 caractères)";
        }
        
        if (prix <= 0) {
            return "Le prix doit être strictement positif";
        }
        
        if (stock < 0) {
            return "Le stock ne peut pas être négatif";
        }
        
        if (seuil < 0) {
            return "Le seuil d'alerte ne peut pas être négatif";
        }
        
        // Vérifier que la catégorie existe
        Categorie cat = categorieDAO.findById(idCategorie);
        if (cat == null) {
            return "Erreur : Catégorie inexistante";
        }
        
        // Vérifier que le produit existe
        Produit produit = produitDAO.findById(idProduit);
        if (produit == null) {
            return "Erreur : Produit inexistant";
        }
        
        produit.setNom(nom);
        produit.setPrixVente(prix);
        produit.setStockActuel(stock);
        produit.setSeuilAlerte(seuil);
        
        
        Categorie categorie = new Categorie();
        categorie.setIdCat(idCategorie);
        produit.setCategorie(categorie);
        
        boolean success = produitDAO.updateProduit(produit);
        return success ? "OK" : "Erreur lors de la modification";
    }
    
    /**
     * Supprimer un produit
     * @param idProduit ID du produit à supprimer
     * @return "OK" si succès, sinon message d'erreur
     */
    public String supprimerProduit(String idProduit) {
        // Vérifier que le produit existe
        Produit produit = produitDAO.findById(idProduit);
        if (produit == null) {
            return "Erreur : Produit inexistant";
        }
        
        if (isProduitDansCommandesEnCours(idProduit)) {
            return "Impossible de supprimer : Ce produit est utilisé dans des commandes en cours ou validées";
        }
        
        
        boolean success = produitDAO.deleteProduit(produit);
        return success ? "OK" : "Erreur lors de la suppression";
    }
    /**
     * Vérifier si un produit est utilisé dans des commandes
     * @param idProduit L'ID du produit à vérifier
     * @return true si le produit est utilisé
     */
    private boolean isProduitDansCommandesEnCours(String idProduit) {
        // Récupérer toutes les commandes EN_COURS et VALIDEE
        List<Commande> commandesEnCours = commandeDAO.findByEtat(EtatCommande.EN_COURS);
        List<Commande> commandesValidees = commandeDAO.findByEtat(EtatCommande.VALIDEE);
        
        // Vérifier dans les commandes EN_COURS
        for (Commande cmd : commandesEnCours) {
            if (ligneCommandeDAO.exists(cmd.getIdCom(), idProduit)) {
                return true;  // le produit est utilisé !
            }
        }
        
        // Vérifier dans les commandes VALIDEE (historique)
        for (Commande cmd : commandesValidees) {
           if (ligneCommandeDAO.exists(cmd.getIdCom(), idProduit)) {
                return true;  // Le produit est dans l'historique !
            }
        }
        
        return false;  // Le produit peut être supprimé
    }
    
    /**
     * Récupérer tous les produits
     * @return Liste de tous les produits
     */
    public List<Produit> obtenirTousProduits() {
        return produitDAO.findAllProduits();
    }
    
    /**
     * Récupérer les produits en alerte (stock <= seuil)
     * @return Liste des produits en alerte
     */
    public List<Produit> obtenirProduitsEnAlerte() {
        return produitDAO.findProduitsEnAlerte();
    }
    
    /**
     * Récupérer toutes les catégories (pour les combobox)
     * @return Liste de toutes les catégories
     */
    public List<Categorie> obtenirToutesCategories() {
        return categorieDAO.findAll();
    }
    
    /**
     * Récupérer un produit par son ID
     * @param idProduit L'ID du produit
     * @return Le produit ou null si non trouvé
     */
    public Produit obtenirProduitParId(String idProduit) {
        return produitDAO.findById(idProduit);
    }
    
    /**
     * Récupérer les produits d'une catégorie
     * @param idCategorie L'ID de la catégorie
     * @return Liste des produits de cette catégorie
     */
    public List<Produit> obtenirProduitsParCategorie(String idCategorie) {
        return produitDAO.findByCategorie(idCategorie);
    }
}