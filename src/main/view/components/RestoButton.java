/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.view.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author Ali Barry
 */
public class RestoButton extends JButton {
    
    private Color defaultColor = new Color(61,81, 181);
    private Color colorOver = new Color(61,81, 181);
    
    public RestoButton() {
        setText("test");
        setContentAreaFilled(true);
        setBorderPainted(false);
        setFocusPainted(false);
        
        // Style du texte
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setForeground(Color.WHITE);
        setBackground(defaultColor);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Gestion de l'effet de survol (Hover)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(colorOver);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultColor);
            }
        });
    }
}
