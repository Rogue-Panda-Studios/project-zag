/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Oa10712
 */
public class Player extends Entity {

    public double hunger;
    public double thirst;
    private double regerateRate;
    private int infectionStatus;
    public int score = 0;
    private BufferedImage hatsSheet;
    private BufferedImage headSheet;
    private BufferedImage armsSheet;
    private BufferedImage bodySheet;
    private BufferedImage legsSheet;
    private int hatsSprite;
    private int headSprite;
    private int armsSprite;
    private int bodySprite;
    private int legsSprite;
    public int inventorySpace;
    /**
     * The Entities current Inventory
     */
    protected Inventory items;

    Player() {
        super("Player", 100, new Point(500, 420), null);
        try {
            velocity = new Point(0, 0);
            hunger = 100;
            thirst = 100;
            regerateRate = .007;
            infectionStatus = 0;
            inventorySpace = 6;
            items = new Inventory();
            items.addItemStack(new ItemStack(0, 1));
            setSpriteSheet(ImageIO.read(getClass().getResource("/com/RoguePanda/ZAG/Images/simpleplayer.png")));
            setSprite(ImageIO.read(getClass().getResource("/com/RoguePanda/ZAG/Images/simpleplayer.png")));
            isPlayer = true;
            tasks.add(new AIPlayer(this));
            monster = false;

        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double getRegenRate() {
        return regerateRate;
    }

    public void setRegenRate(double i) {
        regerateRate = i;
    }

    public void setLevel(Game game) {
        level = game.currentLevel;
    }

    @Override
    public void onDeath() {
        super.onDeath();
        System.out.println("Score: " + score);
    }

    public void setInventorySize(int size) {
        inventorySpace = size;
    }

    public int getInventorySize() {
        return inventorySpace;
    }

    /**
     *
     * @return the entities items
     */
    public Inventory getItems() {
        return items;
    }
}
