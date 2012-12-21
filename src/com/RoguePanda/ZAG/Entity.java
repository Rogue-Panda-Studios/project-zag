/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import com.RoguePanda.Library.ImageManipulator;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A basic Entity, from which all other painted items extend, other than
 * buildings and items
 *
 * @author Oa10712
 */
public abstract class Entity {

    /**
     * The name of the Entity
     */
    protected String name;
    /**
     * The health of the Entity
     */
    protected double health;
    /**
     * The maximum health for the Entity
     */
    protected int maxHealth;
    /**
     * The cause of death for the Entity
     */
    protected int deathCause;
    /**
     * The amount of damage removed from each attack on the entity
     */
    protected int armour;
    /**
     * The current action state; e.g. attack, idle, panic
     */
    protected int actionState;
    protected int[] deathSprites;
    protected int[] walkSprites;
    protected int[] chaseSprites;
    protected int lightLevel;
    protected boolean clippable;
    protected boolean phazing;
    /**
     * The current sprite number, with 0 being the sprite in the upper left
     */
    protected int spritenumber;
    /**
     * The direction that the Entity is facing, -1 is left, 1 is right
     */
    protected int direction;
    /**
     * The current location of the Entity, based on the center of the sprite
     */
    protected Point2D location;
    /**
     * The state of life
     */
    protected boolean dead;
    /**
     * The Entities current Inventory
     */
    protected Inventory items;
    /**
     * The list of tasks for the Entity to execute
     */
    protected AITasks tasks;
    protected BufferedImage spriteSheet;
    protected BufferedImage sprite;
    protected Block level;
    protected boolean monster;
    /**
     * A rectangle representing the Entity, used in collision detection
     */
    protected BoundBox boundingBox;
    boolean falling;
    boolean isPlayer;
    Point2D velocity;

    Entity(String n, int h, Point2D l, Block le) {
        try {
            tasks = new AITasks(this);
            direction = 1;
            location = l;
            health = h;
            maxHealth = h;
            armour = 0;
            deathCause = 0;
            dead = false;
            name = n;
            actionState = 0;
            items = new Inventory();
            level = le;
            spritenumber = 0;
            isPlayer = false;
            velocity = new Point(0, 0);
            monster = true;
            clippable = false;
            location = l;
            boundingBox = new BoundBox(l.getX(), l.getY(), 0, 0);
        } catch (Exception ex) {
            Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return If other Entities can pass through this Entity
     */
    public boolean canClip() {
        return clippable;
    }

    /**
     * Returns the state of the entity as monster, used for game updates
     *
     * @return true if monster
     */
    public boolean isMonster() {
        return monster;
    }

    /**
     * This is called once the Entity dies
     */
    public void onDeath() {
        dropItems();
        dead = true;
        clippable = true;
        System.out.println(name + " died");
    }

    /**
     * Creates DroppedItems for each ItemStack in the Entities Inventory
     */
    public void dropItems() {
        for (ItemStack item : items) {
            DroppedItem d = new DroppedItem("Item", 100, new Point2D.Double(location.getX(), location.getY()), level, item);
            d.velocity.setLocation((int) (Math.random() * 7) - 3, -((int) (Math.random() * 5) + 1));
            level.entities.add(d);
        }
    }

    /**
     * Injures the entity
     *
     * @param val the amount of damage caused
     * @param ds the cause of the damage
     * @return true if the entity dies
     */
    public boolean injure(double val, int ds) {
        if (!dead) {
            health -= val;
            System.out.println(name + " took " + val + " damage");
            if (health <= 0) {
                dead = true;
                deathCause = ds;
                onDeath();
                /**
                 * 1 = fall damage 2 = player melee 3 = player range 4 = trap 5
                 * = splash damage
                 */
            }
        }
        return dead;
    }

    /**
     * Gets if the Entity is dead
     *
     * @return Death status
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Gets the cause of death for the Entity
     *
     * @return Death cause, or 0 if not dead
     */
    public int getDeathCause() {
        if (dead) {
            return deathCause;
        } else {
            return 0;
        }
    }

    /**
     * Gets the Entities health
     *
     * @return the entities health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Gets the location of the Entity
     *
     * @return the entities location as a point relative to the centre of the
     * sprite
     */
    public Point2D getLocation() {
        return location;
    }

    /**
     *
     * @return the entities items
     */
    public Inventory getItems() {
        return items;
    }

    /**
     * Sets the spriteSheet for the entity
     *
     * @param bi The BufferedImage that is being set
     */
    public void setSpriteSheet(BufferedImage bi) {
        spriteSheet = bi;
    }

    /**
     * Gets the spriteSheet
     *
     * @return The spriteSheet as a BufferedImage
     */
    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }

    /**
     * Sets the current sprite for the Entity
     *
     * @param p the current sprite
     */
    public void setSprite(BufferedImage p) {
        p = ImageManipulator.cropImage(p, 0);
        if (falling) {
            switch ((int) (Math.random() * 6)) {
                case 0:
                    p = ImageManipulator.adjustRGB(p, .5, 1, 1);
                    break;
                case 1:
                    p = ImageManipulator.adjustRGB(p, 1, .5, 1);
                    break;
                case 2:
                    p = ImageManipulator.adjustRGB(p, 1, 1, .5);
                    break;
                case 3:
                    p = ImageManipulator.adjustHSB(p, 1.5, 1, 1);
                    break;
                case 4:
                    p = ImageManipulator.adjustHSB(p, 1, 1.5, 1);
                    break;
                case 5:
                    p = ImageManipulator.adjustHSB(p, 1, 1, 1.5);
                    break;
            }
        }
        sprite = p;
    }

    /**
     * Gets the current sprite
     *
     * @return The current Sprite as a BufferedImage
     */
    public BufferedImage getSprite() {
        return sprite;
    }

    /**
     * Heals the Entity by the specified amount
     *
     * @param h The amount to heal by
     */
    public boolean heal(double h) {
        if (!dead) {
            health += h;
            if (health > maxHealth) {
                health = maxHealth;
            }
            if (health <= 0) {
                dead = true;
                deathCause = 6;
                onDeath();
                /**
                 * 1 = fall damage 2 = player melee 3 = player range 4 = trap 5
                 * = splash damage 6 = starvation/dehydration
                 */
            }
        }
        return dead;
    }

    /**
     *
     * @return The Entities position and space data
     */
    public BoundBox getBounds() {
        return boundingBox;
    }

    public void setBounds(BoundBox r) {
        boundingBox = r;
    }
}
