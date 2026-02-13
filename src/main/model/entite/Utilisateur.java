/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.model.entite;
import java.util.UUID;

/**
 *
 * @author isaac
 */
public class Utilisateur {
    private String id;
    private String email;
    private String password;
    
    public Utilisateur(String email, String password){
        this.id = UUID.randomUUID().toString();
<<<<<<< HEAD
        this.email = email;
=======
        this.email = login;
>>>>>>> 2b901d4 (feat/ali: Ajout des nouveaux frames)
        this.password = password;
    }
    
    public Utilisateur() {}    
    
    public String getId(){
        return this.id;
    }
    
    public void setId(String id){
        this.id = id;
    }
<<<<<<< HEAD
    public String getEmail(){
        return this.email;
    }
    
    public void setEmail(String email){
        this.email = email;   
=======
    public String getLogin(){
        return this.email;
    }
    
    public void setLogin(String login){
        this.email = login;   
>>>>>>> 2b901d4 (feat/ali: Ajout des nouveaux frames)
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
}
