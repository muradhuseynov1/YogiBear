/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rx15mw_assignment3;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
/**
 *
 * @author Murad Hüseynov
 */
public class Ranger {
    private BufferedImage sprite;
    private int x, y; 
    private int SPEED = 2; 
    private char movementDirection; // 'H' for horizontal, 'V' for vertical

    public Ranger(int x, int y, String spritePath, char movementDirection, int width, int height) {
        this.x = x;
        this.y = y;
        this.movementDirection = movementDirection;
        BufferedImage originalSprite = SpriteLoader.loadSprite(spritePath);
        this.sprite = SpriteLoader.resizeSprite(originalSprite, width, height);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
    }

    public void update(List<Obstacle> obstacles) {
        int oldX = x, oldY = y;

        if (movementDirection == 'H') {
            x += SPEED;
        } else if (movementDirection == 'V') {
            y += SPEED;
        }

        for (Obstacle obstacle : obstacles) {
            if (getBounds().intersects(obstacle.getBounds())) {
                x = oldX;
                y = oldY;

                if (movementDirection == 'H') {
                    SPEED = -SPEED; 
                } else if (movementDirection == 'V') {
                    SPEED = -SPEED; 
                }
                break; 
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, null);
    }
}
