/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.entite;
import java.time.LocalDateTime;
import java.util.UUID;
import main.model.entite.enums.EtatCommande;

/**
 *
 * @author isaac
 */


public class Commande {
    private String idCom;
    private LocalDateTime dateCommande;
    private EtatCommande etat;
    private double total;

    public Commande(LocalDateTime dateCommande, EtatCommande etat, double total) {
        this.idCom = UUID.randomUUID().toString();
        this.dateCommande = dateCommande;
        this.etat = etat;
        this.total = total;
        
    }
    public Commande(){}

    public String getIdCom() {
        return idCom;
    }

    public void setIdCom(String idCom) {
        this.idCom = idCom;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public EtatCommande getEtat() {
        return etat;
    }

    public void setEtat(EtatCommande etat) {
        this.etat = etat;
    }
    
    public double getTotalCommande(){
        return total;
    }
    
}
