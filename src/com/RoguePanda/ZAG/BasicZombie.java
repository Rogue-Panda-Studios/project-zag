/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import com.RoguePanda.Library.ImageManipulator;
import java.awt.Point;

/**
 * A basic Zombie, has a melee attack and wanders
 *
 * @author Oa10712
 */
public class BasicZombie extends Entity {

    /**
     * Creates a new BasicZombie
     *
     * @param name The name of the Entity, usually the type of Entity plus the
     * number of Entities at the time of creation
     * @param health The maximum health of the Entity
     * @param location The current location of the Entity
     * @param l The level that the Entity is a part of
     */
    public BasicZombie(String name, int health, Point location, Block l) {
        super(name, health, location, l);
        falling = true;
        switch ((int) (Math.random() * 2) + 1) {
            case 1:
                items.addItemStack(new ItemStack(0, 1));
                break;
        }
        setSpriteSheet(level.simplezombie);
        setSprite(ImageManipulator.selectFromSheet(spriteSheet, 2, 64, 64));
        deathSprites = new int[]{4, 5, 6, 7};
        walkSprites = new int[]{2, 3, 4, 3, 2, 1, 0, 1};
        chaseSprites = new int[]{7, 8, 9, 8, 7, 6, 5, 6};
        spritenumber = 2;
        tasks.add(new AIChasePlayer(this, 1, 5, 100));
        tasks.add(new AIAttackPlayerMelee(this, 15, 5));
        double rand = Math.random();
        if (rand > .5) {
            tasks.add(new AIWander(this, 1));
        } else {
            tasks.add(new AIWander(this, .5));
        }
        tasks.add(new AIDeathAnimation(this));
    }

    @Override
    public void onDeath() {
        super.onDeath();
        level.game.player.score += 5;
    }
}
