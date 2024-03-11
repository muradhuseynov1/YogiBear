/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rx15mw_assignment3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Murad Hüseynov
 */
public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USER = "root";
    private static final String PASSWORD = "murad2002";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public void addOrUpdateHighScore(String name, int score) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            String selectQuery = "SELECT score FROM high_scores WHERE name = ?";
            try (PreparedStatement selectPs = conn.prepareStatement(selectQuery)) {
                selectPs.setString(1, name);
                ResultSet rs = selectPs.executeQuery();

                if (rs.next()) {
                    int existingScore = rs.getInt("score");
                    System.out.println("Existing score for " + name + ": " + existingScore);

                    if (score > existingScore) {
                        String updateQuery = "UPDATE high_scores SET score = ? WHERE name = ?";
                        try (PreparedStatement updatePs = conn.prepareStatement(updateQuery)) {
                            updatePs.setInt(1, score);
                            updatePs.setString(2, name);
                            int rowsAffected = updatePs.executeUpdate();
                            System.out.println("Updated score for " + name + " to " + score + ". Rows affected: " + rowsAffected);
                        }
                    } else {
                        System.out.println("New score for " + name + " is not higher. No update performed.");
                    }
                } else {
                    String insertQuery = "INSERT INTO high_scores (name, score) VALUES (?, ?)";
                    try (PreparedStatement insertPs = conn.prepareStatement(insertQuery)) {
                        insertPs.setString(1, name);
                        insertPs.setInt(2, score);
                        int rowsAffected = insertPs.executeUpdate();
                        System.out.println("Inserted new score for " + name + ": " + score + ". Rows affected: " + rowsAffected);
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> getTopTenHighScores() {
        List<String[]> highScores = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "SELECT name, score FROM high_scores ORDER BY score DESC LIMIT 10";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("name");
                    int score = rs.getInt("score");
                    highScores.add(new String[]{name, String.valueOf(score)});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highScores;
    }
}
