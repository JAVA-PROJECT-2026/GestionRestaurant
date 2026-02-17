/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

/**
 *
 * @author isaac
 */

import main.model.dao.CategorieDAO;
import main.model.entite.Categorie;
import java.util.List;
import java.util.UUID;

public class CategorieController {
    
    private CategorieDAO categorieDAO;
    
    public CategorieController() {
        this.categorieDAO = new CategorieDAO();
    }
    
    /**
     * Ajouter une nouvelle catégorie
     * @param libelle
     * @return 
     */
    public String ajouterCategorie(String libelle) {
        try {
            // Validation
            if (libelle == null || libelle.trim().isEmpty()) {
                return "Le libellé est obligatoire";
            }
            
            // Créer la catégorie
            Categorie categorie = new Categorie();
            categorie.setIdCat(genererIdCategorie());
            categorie.setLibelle(libelle.trim());
            
            // Insérer en base
            boolean resultat;
            resultat = categorieDAO.insert(categorie);
            
            if (resultat) {
                return "OK";
            } else {
                return "Erreur lors de l'insertion";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur : " + e.getMessage();
        }
    }
    
    /**
     * Lister toutes les catégories
     */
    public List<Categorie> listerToutesLesCategories() {
        return categorieDAO.findAll();
    }
    
    /**
     * Supprimer une catégorie
     */
    public String supprimerCategorie(String idCat) {
        try {
            Categorie categorie = new Categorie();
            categorie.setIdCat(idCat);
            
            boolean resultat;
            resultat = categorieDAO.delete(categorie);
            
            if (resultat) {
                return "OK";
            } else {
                return "Erreur lors de la suppression";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur : " + e.getMessage();
        }
    }
    
    /**
     * Générer un ID unique
     */
    private String genererIdCategorie() {
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "CAT-" + uuid;
    }
    
    /**
    * Ajouter une nouvelle catégorie
    */
    public String ajouterCategorie(String libelle, String description) {  // ✅ Ajout paramètre description
        try {
        // Validation
            if (libelle == null || libelle.trim().isEmpty()) {
                return "Le libellé est obligatoire";
            }
        
        // Créer la catégorie
            Categorie categorie = new Categorie();
            categorie.setIdCat(genererIdCategorie());
            categorie.setLibelle(libelle.trim());
            categorie.setDescription(description != null ? description.trim() : "");  // ✅ NOUVEAU
        
        // Insérer en base
            boolean resultat = categorieDAO.insert(categorie);
        
            if (resultat) {
                return "OK";
            } else {
                return "Erreur lors de l'insertion";
            }
        
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur : " + e.getMessage();
        }
    }
}
