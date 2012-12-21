/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import com.RoguePanda.Library.ImageManipulator;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * An item that is held in an Inventory
 *
 * @author Oa10712
 */
class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    public String name;
    private int id;
    private int health;
    private int damage;
    private int maxstacksize;
    private double weight;
    private double size;
    private BufferedImage sprite, spriteSheet;

    Item(int i) {
        try {
            int modid = id;
            int modsheet = 0;
            while (modid > 63) {
                modid -= 64;
                modsheet++;
            }
            id = i;
            if (id != 63) {
                spriteSheet = ImageIO.read(getClass().getResource("/com/RoguePanda/ZAG/Images/itemSheet0.png"));
                sprite = ImageManipulator.selectFromSheet(spriteSheet, id, 64, 64);
            } else {
                spriteSheet = null;
                sprite = null;
            }
            damage = 3;
            //<editor-fold defaultstate="collapsed" desc="data setup">
            switch (id) {
                case 0:
                    damage = 2;
                    weight = 0.1264;
                    health = 0;
                    maxstacksize = 1;
                    size = 540;
                    name = "Bell Pepper";
                    break;
                default:
                    damage = 0;
                    weight = 0;
                    health = 0;
                    maxstacksize = 1;
                    size = 0;
                    name = "";
                    break;
            }
            //</editor-fold>
        } catch (IOException ex) {
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public int getID() {
        return id;
    }

    double getDamage() {

        return damage;
    }

    double getWeight() {
        return weight;
    }

    double getSize() {
        return size;
    }

    @Override
    public String toString() {
        String out = "ID:" + id + ";Weight:" + weight + ";Size:" + size;
        return out;
    }
}
