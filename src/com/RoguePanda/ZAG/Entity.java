/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Oa10712
 */
public abstract class Entity {

    /**
     * The name of the Entity
     */
    protected String name;
    /**
     * The Entity's current health
     */
    protected double health;
    /**
     * The Entity's maximum health
     */
    protected int maxHeath;
    /**
     * The cause of death for the Entity. 0 until death.
     */
    protected int deathCause = 0;
    /**
     * The Entity's armour. This amount will be taken off of every attack made
     * on the Entity.
     */
    protected int armour;
    /**
     * The current action state; e.g. attack, idle, panic
     */
    protected int actionState;
    /**
     * The list of sprite IDs for death
     */
    protected int[] deathSprites;
    /**
     * The list of sprite IDs for walking
     */
    protected int[] walkSprites;
    /**
     * The list of sprite IDs for chasing
     */
    protected int[] chaseSprites;
    //public Building inside;
    /**
     * The current sprite number, with 0 being the sprite in the upper left
     */
    protected int spritenumber;
    /**
     * The direction that the Entity is facing, -1 is left, 1 is right
     */
    protected int direction;
    /**
     * The current location of the Entity, based on the centre of the sprite
     */
    protected Point2D location;
    /**
     * The state of life
     */
    protected boolean dead;
    /**
     * The list of tasks for the Entity to execute
     */
    protected AITasks tasks;
    //Point spriteSize;
    /**
     * The sprite sheet used for the Entity.
     */
    protected BufferedImage spriteSheet;
    /**
     * The current sprite for the Entity.
     */
    protected BufferedImage sprite;
    //protected Block level;
    /**
     * Boolean representing if the entity can fall or not.
     */
    boolean canFall = true;
    /**
     * Boolean representing if the entity is falling.
     */
    boolean falling;
    Point2D velocity;

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

    private void onDeath() {
        dropItems();
    }

    private void dropItems() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
