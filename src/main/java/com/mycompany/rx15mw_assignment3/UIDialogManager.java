/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rx15mw_assignment3;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Murad Hüseynov
 */
public class UIDialogManager {
    private JFrame frame;
    private JLabel livesLabel;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JButton optionsButton;
    private JPopupMenu optionsMenu;
    private GameActions gameActions;

    public UIDialogManager(JFrame frame, GameActions gameActions) {
        this.frame = frame;
        this.gameActions = gameActions;
        setupUI();
    }
    
    public void setupUI() {
        livesLabel = new JLabel("Lives: 3");
        scoreLabel = new JLabel("Score: 0");
        timeLabel = new JLabel("Time: 0s");
        
        optionsButton = new JButton("Options");
        setupOptionsMenu();
        optionsButton.setFocusable(false);

        JPanel panel = new JPanel();
        panel.add(livesLabel);
        panel.add(scoreLabel);
        panel.add(timeLabel);
        panel.add(optionsButton);
        frame.add(panel, BorderLayout.NORTH); 
    }
    
    private void setupOptionsMenu() {
        optionsMenu = new JPopupMenu();
        
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(e -> gameActions.startNewGame());
        optionsMenu.add(newGameItem);

        JMenuItem restartItem = new JMenuItem("Restart");
        restartItem.addActionListener(e -> restartGame());
        optionsMenu.add(restartItem);

        JMenuItem highScoresItem = new JMenuItem("High Scores");
        highScoresItem.addActionListener(e -> showHighScores());
        optionsMenu.add(highScoresItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> exitGame());
        optionsMenu.add(exitItem);

        optionsButton.addActionListener(e -> optionsMenu.show(optionsButton, 0, optionsButton.getHeight()));
    }

    private void restartGame() {
        gameActions.restartCurrentLevel();
    }

    private void exitGame() {
        int choice = JOptionPane.showConfirmDialog(frame, 
                "Are you sure you want to exit the game?", 
                "Exit Game", 
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void updateLivesDisplay(int lives) {
        livesLabel.setText("Lives: " + lives);
        frame.revalidate(); 
    }

    public void updateScoreDisplay(int score) {
        scoreLabel.setText("Score: " + score);
        frame.revalidate(); 
    }
    
    public void updateTimeDisplay(long timeInSeconds) {
        timeLabel.setText("Time: " + timeInSeconds + "s");
    }
    
    public void showHighScores() {
        DatabaseManager dbManager = new DatabaseManager();
        List<String[]> highScores = dbManager.getTopTenHighScores();

        String[] columnNames = {"Name", "Score"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (String[] scoreEntry : highScores) {
            model.addRow(scoreEntry);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JDialog dialog = new JDialog();
        dialog.add(scrollPane);
        dialog.setTitle("High Scores");
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(null); 
        dialog.setVisible(true);
    }
}
