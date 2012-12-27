/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import com.RoguePanda.Library.ImageManipulator;
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * A zombie that can shoot fireballs
 *
 * @author Oa10712
 */
public class FireZombie extends Entity {

    /**
     *
     * @param name The name of the Entity
     * @param health The starting health of the ENtity
     * @param location The location of this Entity
     * @param l The Block that this Entity is a part of
     */
    public FireZombie(String name, int health, Point location, Block l) {
        super(name, health, location, l);
        falling = true;
        setSpriteSheet(level.simplezombie);
        setSprite(ImageManipulator.selectFromSheet(spriteSheet, 0, 64, 64));
        deathSprites = new int[]{4, 5, 6, 7};
        walkSprites = new int[]{2, 3, 4, 3, 2, 1, 0, 1};
        chaseSprites = new int[]{7, 8, 9, 8, 7, 6, 5, 6};
        spritenumber = 2;
        tasks.add(new AIChasePlayer(this, 2, 100, 200));
        tasks.add(new AIShootProjectileAtPlayer(this, 60, 200, 5, 0));
        tasks.add(new AIWander(this, 1));
        tasks.add(new AIDeathAnimation(this));
        inside = null;
    }

    public FireZombie(String name, int health, Point location, Block l, Building b) {
        super(name, health, location, l, b);
        falling = true;
        setSpriteSheet(level.simplezombie);
        setSprite(ImageManipulator.selectFromSheet(spriteSheet, 0, 64, 64));
        deathSprites = new int[]{4, 5, 6, 7};
        walkSprites = new int[]{2, 3, 4, 3, 2, 1, 0, 1};
        chaseSprites = new int[]{7, 8, 9, 8, 7, 6, 5, 6};
        spritenumber = 2;
        tasks.add(new AIChasePlayer(this, 2, 100, 200));
        tasks.add(new AIShootProjectileAtPlayer(this, 60, 200, 5, 0));
        tasks.add(new AIWander(this, 1));
        tasks.add(new AIDeathAnimation(this));
    }

    @Override
    public void dropItems() {
        switch ((int) (Math.random() * 2) + 1) {
            case 1:
                DroppedItem d = new DroppedItem("Item", 100, new Point2D.Double(location.getX(), location.getY()), level, new ItemStack(0, 1));
                d.velocity.setLocation((int) (Math.random() * 7) - 3, -((int) (Math.random() * 5) + 1));
                d.inside = inside;
                level.entities.add(d);
                break;
        }

    }
}
