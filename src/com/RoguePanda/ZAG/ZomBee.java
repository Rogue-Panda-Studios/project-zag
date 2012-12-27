/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import com.RoguePanda.Library.ImageManipulator;
import java.awt.Point;

/**
 *
 * @author Oa10712
 */
public class ZomBee extends Entity {

    ZomBee(String name, int health, Point location, Block l) {
        super(name, health, location, l);
        falling = false;
        setSpriteSheet(level.zombee);
        setSprite(ImageManipulator.selectFromSheet(spriteSheet, 0, 64, 64));
        canFall = false;
        deathSprites = new int[]{1};
        walkSprites = new int[]{0};
        chaseSprites = new int[]{0};
        spritenumber = 1;
        tasks.add(new AIFlyingWander(this));
        tasks.add(new AIDeathAnimation(this));
    }
}