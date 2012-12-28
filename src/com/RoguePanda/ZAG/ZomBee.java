/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import com.RoguePanda.Library.ImageManipulator;
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 *
 * @author Oa10712
 */
public class ZomBee extends Entity {

    ZomBee(String name, int health, Point location, Block l) {
        super(name, health, location, l);
        falling = false;
        setSpriteSheet(level.zombee);
        setSprite(ImageManipulator.selectFromSheet(spriteSheet, 0, 25, 16));
        canFall = false;
        deathSprites = new int[]{4};
        walkSprites = new int[]{0};
        chaseSprites = new int[]{0};
        spritenumber = 0;
        spriteSize = new Point(25, 16);
        tasks.add(new AIFlyingWander(this));
        tasks.add(new AIDeathAnimation(this));
    }

    public void onDeath() {
        super.onDeath();
        canFall = true;
        falling = true;
        velocity = new Point2D.Double(velocity.getX(), 3);
    }
}