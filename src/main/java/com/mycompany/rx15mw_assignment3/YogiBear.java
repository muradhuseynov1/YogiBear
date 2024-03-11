/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rx15mw_assignment3;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
/**
 *
 * @author Murad Hüseynov
 */
public class YogiBear {
    private BufferedImage sprite;
    private int x, y; 
    private final int SPEED = 5; 
    private int startX, startY; 
    private int lives; 
    private final int initialLives = 3;

    public YogiBear(int startX, int startY, int width, int height, YogiBear existingYogi) {
        this.startX = startX;
        this.startY = startY;
        this.x = startX;
        this.y = startY;
        this.lives = (existingYogi != null) ? existingYogi.getLives() : initialLives;
        BufferedImage originalSprite = SpriteLoader.loadSprite("data/media/yogi.png");
        this.sprite = SpriteLoader.resizeSprite(originalSprite, width, height);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
    }

    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, null);
    }
    
    public void move(char direction, GamePanel panel) {
        int oldX = x, oldY = y;

        switch (direction) {
            case 'W': y -= SPEED; break;
            case 'A': x -= SPEED; break;
            case 'S': y += SPEED; break;
            case 'D': x += SPEED; break;
        }

        if (panel.checkCollision()) {
            x = oldX;
            y = oldY;
        }
    }
    
    public void resetPosition() {
        this.x = startX;
        this.y = startY;
    }
    
    public void resetLives() {
        this.lives = initialLives;
    }
    
    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
        resetPosition();
    }

    public int getLives() {
        return lives;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
}
