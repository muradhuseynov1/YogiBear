/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rx15mw_assignment3;
import javax.swing.JFrame;
/**
 *
 * @author Murad Hüseynov
 */
public class GameLauncher {

    public static void main(String[] args) {
        JFrame window = new JFrame("Yogi Bear Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel(); 
        UIDialogManager uiDialogManager = new UIDialogManager(window, gamePanel); 

        gamePanel.setUIDialogManager(uiDialogManager); 
        uiDialogManager.setupUI();

        window.add(gamePanel);
        window.pack(); 
        window.setLocationRelativeTo(null); 
        window.setVisible(true);

        gamePanel.startGameLoop(); 
    }
}
