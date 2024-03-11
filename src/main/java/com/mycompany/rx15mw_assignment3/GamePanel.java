/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rx15mw_assignment3;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author Murad Hüseynov
 */
public class GamePanel extends JPanel implements Runnable, GameActions {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private Thread gameThread;
    private BufferedImage backgroundImage;
    private int currentLevelNumber = 1;
    private Level currentLevel;
    private int score = 0;
    
    private UIDialogManager uiDialogManager;
    private boolean isGameRunning;
    private long startTime;
    private long elapsedTime;
    private boolean restartRequested = false;

    
    private YogiBear yogiBear;

    public GamePanel() {
        this.uiDialogManager = uiDialogManager;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        startTime = System.currentTimeMillis();
        isGameRunning = true;
        backgroundImage = SpriteLoader.loadSprite("data/media/background.jpg");
        //yogiBear = new YogiBear(400, 520, 40, 40); 
        currentLevel = new Level();
        //currentLevel.loadLevel("data/levels/level1.txt");
        loadNextLevel();
        yogiBear = currentLevel.getYogiBear();
        
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W: yogiBear.move('W', GamePanel.this); break;
                    case KeyEvent.VK_S: yogiBear.move('S', GamePanel.this); break;
                    case KeyEvent.VK_A: yogiBear.move('A', GamePanel.this); break;
                    case KeyEvent.VK_D: yogiBear.move('D', GamePanel.this); break;
                }
            }
        });
    }

    public void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (true) {
            update(); 
            repaint(); 

            try {
                Thread.sleep(16); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        if (!isGameRunning) {
            return;
        }

        for (Ranger ranger : currentLevel.getRangers()) {
            ranger.update(currentLevel.getObstacles());
        }

        for (Ranger ranger : currentLevel.getRangers()) {
            if (yogiBear.getBounds().intersects(ranger.getBounds())) {
                yogiBear.loseLife();
                uiDialogManager.updateLivesDisplay(yogiBear.getLives());

                if (yogiBear.getLives() == 0) {
                    endGame();
                    return;
                }
            }
        }

        Iterator<Basket> basketIterator = currentLevel.getBaskets().iterator();
        while (basketIterator.hasNext()) {
            Basket basket = basketIterator.next();
            if (yogiBear.getBounds().intersects(basket.getBounds())) {
                basketIterator.remove(); 
                score++; 
                uiDialogManager.updateScoreDisplay(score); 
            }
        }

        elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        uiDialogManager.updateTimeDisplay(elapsedTime);

        if (restartRequested) {
            restartRequested = true; 
            return; 
        }

        if (currentLevel.getBaskets().isEmpty()) {
            goToNextLevel();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        drawBackground(g);
        yogiBear.draw(g);
        
        for (Obstacle obstacle : currentLevel.getObstacles()) {
            obstacle.draw(g);
        }
        
        for (Ranger ranger : currentLevel.getRangers()) {
            ranger.draw(g);
        }
        
        for (Basket basket : currentLevel.getBaskets()) {
            basket.draw(g);
        }
    }
    
    private void drawBackground(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
    
    public boolean checkCollision() {
        Rectangle yogiBounds = yogiBear.getBounds();
        for (Obstacle obstacle : currentLevel.getObstacles()) {
            if (yogiBounds.intersects(obstacle.getBounds())) {
                return true; 
            }
        }
        return false; 
    }
    
    public void endGame() {
        isGameRunning = false;
        
        String playerName = JOptionPane.showInputDialog(this, "Enter your name for the high score:");
        if(playerName != null && !playerName.trim().isEmpty()) {
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.addOrUpdateHighScore(playerName, score);
        }
        
        //displayHighScores();
    }
    
    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    public void setCurrentLevelNumber(int currentLevelNumber) {
        this.currentLevelNumber = currentLevelNumber;
    }

    public int getScore() {
        return score;
    }

//    private void displayHighScores() {
    public void setScore(int score) {
        this.score = score;
    }

//        DatabaseManager dbManager = new DatabaseManager();
//        List<String> highScores = dbManager.getTopTenHighScores();
//        JOptionPane.showMessageDialog(this, String.join("\n", highScores), "High Scores: ", JOptionPane.INFORMATION_MESSAGE);
//    }
    private void loadNextLevel() {
        if (currentLevelNumber > 10) {
            JOptionPane.showMessageDialog(this, "You have finished the game!");
            return;
        }

        String levelFile = "data/levels/level" + currentLevelNumber + ".txt";
        currentLevel = new Level();
        currentLevel.loadLevel(levelFile, yogiBear);
        yogiBear = currentLevel.getYogiBear();
    }
    
    public void loadLevel(int levelNumber) {
        String levelFile = "data/levels/level" + levelNumber + ".txt";
        currentLevel = new Level();
        currentLevel.loadLevel(levelFile, yogiBear);
        yogiBear = currentLevel.getYogiBear();
    }
    
    private void checkLevelCompletion() {
        if (currentLevel.getBaskets().isEmpty()) {
            goToNextLevel();
        }
    }
    
    private void goToNextLevel() {
        currentLevelNumber++;
        if (currentLevelNumber <= 10) {
            loadLevel(currentLevelNumber); 
        } else {
            JOptionPane.showMessageDialog(this, "You have finished the game!");
            endGame(); 
        }
    }
    
    @Override
    public void restartCurrentLevel() {
        restartRequested = true;
        loadLevel(currentLevelNumber);
        yogiBear.resetPosition();
        startTime = System.currentTimeMillis();
        uiDialogManager.updateTimeDisplay(0);
        score = 0; 
        yogiBear.resetPosition(); 
        uiDialogManager.updateScoreDisplay(score);
        yogiBear.resetLives(); 
        uiDialogManager.updateLivesDisplay(yogiBear.getLives());
    }
    
    public void setUIDialogManager(UIDialogManager uiDialogManager) {
        this.uiDialogManager = uiDialogManager;
    }
    
    @Override
    public void startNewGame() {
        System.out.println("Starting new game. Current level before reset: " + currentLevelNumber);
        currentLevelNumber = 1;
        System.out.println("Current level after reset: " + currentLevelNumber);
        score = 0; 
        yogiBear.resetPosition(); 
        yogiBear.resetLives(); 
        startTime = System.currentTimeMillis();
        
        loadLevel(currentLevelNumber); 
        isGameRunning = true; 
        
        //refreshGameDisplay();
        repaint();
    }
    
    private void refreshGameDisplay() {
        uiDialogManager.updateScoreDisplay(score);
        uiDialogManager.updateLivesDisplay(yogiBear.getLives());
        uiDialogManager.updateTimeDisplay(0);
    }
}