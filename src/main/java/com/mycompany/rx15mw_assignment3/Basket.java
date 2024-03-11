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
public class Basket {
    private BufferedImage sprite;
    private int x, y; 

    public Basket(int x, int y, String spritePath, int width, int height) {
        this.x = x;
        this.y = y;
        BufferedImage originalSprite = SpriteLoader.loadSprite(spritePath);
        this.sprite = SpriteLoader.resizeSprite(originalSprite, width, height);
    }

    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, null);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
    }
}
