/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rx15mw_assignment3;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Murad Hüseynov
 */
public class Level {
    private List<Obstacle> obstacles = new ArrayList<>();
    private List<Ranger> rangers = new ArrayList<>();
    private List<Basket> baskets = new ArrayList<>();
    private YogiBear yogiBear;

    public void loadLevel(String filePath, YogiBear existingYogi) {
        obstacles.clear();
        rangers.clear();
        baskets.clear();
        
        yogiBear = new YogiBear(400,520,40,40, existingYogi);
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                String type = parts[0];
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);

                switch (type) {
                    case "tree":
                    case "rock":
                        String obstacleSpritePath = determineObstacleSpritePath(type);
                        obstacles.add(new Obstacle(x, y, obstacleSpritePath, 40, 40));
                        break;
                    case "rangerH":
                    case "rangerV":
                        char movementDirection = parts[3].charAt(0);
                        rangers.add(new Ranger(x, y, "data/media/ranger.png", movementDirection, 40, 40));
                        break;
                    case "basket":
                        baskets.add(new Basket(x, y, "data/media/basket.png", 40, 40));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String determineObstacleSpritePath(String type) {
        switch (type) {
            case "tree":
                return "data/media/wall.png";
            case "rock":
                return "data/media/wall.png";
            default:
                return "data/media/wall.png";
        }
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }
    
    public List<Ranger> getRangers() {
        return rangers;
    }
    
    public List<Basket> getBaskets() {
        return baskets;
    }

    public YogiBear getYogiBear() {
        return yogiBear;
    }
}
