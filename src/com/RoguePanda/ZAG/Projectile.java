/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import com.RoguePanda.Library.ImageManipulator;
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * This is a simple projectile with no gravity applied to it
 *
 * @author Oa10712
 */
public class Projectile extends Entity {

    int damage;
    int range;
    int projectileType;
    double distance;

    /**
     *
     * @param name The name of the Entity
     * @param type The type of Projectile
     * @param health The health of the Entity
     * @param location The location of the Entity
     * @param l The Level that this Entity is a part of
     * @param dam The damage that this Projectile will deal
     * @param ran The range for the Projectile to travel
     * @param speed The speed for the projectile to travel, in both the x and y
     * axis
     * @see Entity
     */
    public Projectile(String name, int type, int health, Point2D location, Block l, int dam, int ran, Point speed) {
        super(name, health, location, l);
        damage = dam;
        range = ran;
        velocity = speed;
        projectileType = type;
        setSpriteSheet(level.itemsheet);
        switch (projectileType) {
            case 0:
                setSprite(ImageManipulator.scaleImage(ImageManipulator.selectFromSheet(spriteSheet, 1, 64, 64), 16, 16));
                break;
        }
        monster = false;
        tasks.add(new AIProjectile(this));
        canFall = false;
    }
}
