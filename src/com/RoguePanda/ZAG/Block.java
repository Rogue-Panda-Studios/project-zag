/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * This is a level in game, it hold lists for entities and buildings
 *
 * @author Oa10712
 */
public class Block {

    ArrayList<Entity> entities;
    Entity[] ets;
    ArrayList<Building> buildings;
    Point playerLocation;
    Game game;
    Dimension size;
    /**
     * The image that holds sprites for items on the level
     */
    BufferedImage itemsheet;
    /**
     * The image that holds sprites for a simple zombie
     */
    BufferedImage simplezombie;

    /**
     *
     * @param aThis This is the GUI that the level is attached to
     */
    Block(Game aThis) {
        game = aThis;
        playerLocation = new Point(0, 0);
        entities = new ArrayList<>();
        try {
            itemsheet = ImageIO.read(getClass().getResource("/com/RoguePanda/ZAG/Images/itemSheet0.png"));
            simplezombie = ImageIO.read(getClass().getResource("/com/RoguePanda/ZAG/Images/BasicZombie1.png"));
        } catch (IOException ex) {
            Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
        }
        buildings = new ArrayList<>();
        size = new Dimension(1600, 600);
    }
}
