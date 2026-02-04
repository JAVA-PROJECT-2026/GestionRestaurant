/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.entite;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author isaac
 */
public class Commande {
    private String idCom;
    private LocalDateTime dateCommande;
    private String etat;

    public Commande(LocalDateTime dateCommande, String etat) {
        this.idCom = UUID.randomUUID().toString();
        this.dateCommande = dateCommande;
        this.etat = etat;
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

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
    
    
}
