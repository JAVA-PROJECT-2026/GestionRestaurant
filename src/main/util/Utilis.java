/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util;

/**
 *
 * @author Ali Barry
 */
public class Utilis {
    
    public static javax.swing.ImageIcon resizeIcon(String path, int width, int height) {
        java.net.URL imgURL = Utilis.class.getResource(path);
        if (imgURL != null) {
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imgURL);
            java.awt.Image img = icon.getImage();
            java.awt.Image newImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            return new javax.swing.ImageIcon(newImg);
        }
        return null;
    }
}
