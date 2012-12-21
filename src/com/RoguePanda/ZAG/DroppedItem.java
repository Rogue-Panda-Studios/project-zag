/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import com.RoguePanda.Library.ImageManipulator;
import java.awt.geom.Point2D;

/**
 * The object to represent an item dropped from an inventory
 *
 * @author Oa10712
 */
public class DroppedItem extends Entity {

    /**
     * The ItemStack that this represents
     */
    ItemStack itemst;

    /**
     *
     * @param n The name of this Entity
     * @param h The health of this Entity
     * @param l The location of this Entity
     * @param le The Block that this entity is a part of
     * @param is The ItemStack that this represents
     */
    public DroppedItem(String n, int h, Point2D l, Block le, ItemStack is) {
        super(n, h, l, le);
        falling = true;
        itemst = is;
        setSpriteSheet(level.itemsheet);
        setSprite(ImageManipulator.scaleImage(ImageManipulator.selectFromSheet(spriteSheet, is.item.getID(), 64, 64), 16, 16));
        tasks.add(new AIDroppedItem(this));
        monster = false;
    }
}
