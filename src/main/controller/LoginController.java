/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import main.model.dao.UtilisateurDAO;
import main.model.entite.Utilisateur;
/**
 *
 * @author isaac
 */

public class LoginController {

    private UtilisateurDAO utilisateurDAO;

    public LoginController() {
        this.utilisateurDAO = new UtilisateurDAO();
    }

    public Utilisateur connecter(String email, String password) {
        if (email == null || email.trim().isEmpty()) return null;
        if (password == null || password.trim().isEmpty()) return null;

        return utilisateurDAO.findByEmailAndPassword(email.trim(), password.trim());
    }

    public String inscrire(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            return "L'email est obligatoire";
        }
        if (password == null || password.trim().isEmpty()) {
            return "Le mot de passe est obligatoire";
        }
        if (password.trim().length() < 6) {
            return "Le mot de passe doit contenir au moins 6 caractères";
        }
        if (utilisateurDAO.emailExiste(email.trim())) {
            return "Cet email est déjà utilisé";
        }

        Utilisateur u = new Utilisateur(email.trim(), password.trim());
        boolean ok = utilisateurDAO.insert(u);
        return ok ? "OK" : "Erreur lors de l'inscription";
    }
}