/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import com.RoguePanda.Library.ImageManipulator;
import java.awt.Point;

/**
 * An object, like a tv or a fountain
 *
 * @author Oa10712
 */
public class GameObject extends Entity {

    int objectID;
    int waiter = 0;

    /**
     *
     * @param n The name of the Object
     * @param h The health of the Object
     * @param l The location of the Object
     * @param le The Level that this object is a part of
     * @param id The ID for this Object
     */
    public GameObject(String n, int h, Point l, Block le, int id) {
        super(n, h, l, le);
        setSpriteSheet(level.itemsheet);
        setSprite(ImageManipulator.selectFromSheet(spriteSheet, id, 64, 64));
        objectID = id;
        monster = false;
        tasks.add(new AIObject(this));
    }
}
