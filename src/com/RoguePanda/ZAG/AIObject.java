/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * This handles the code for GameObjects, such as animations and particles
 *
 * @author Oa10712
 */
public class AIObject extends AI {

    /**
     * The Entity that owns this AI
     */
    GameObject go;

    /**
     *
     * @param e The Entity that owns this AI
     */
    public AIObject(GameObject e) {
        super(e);
        go = e;
    }

    @Override
    public boolean execute() {
        boolean ret = true;
        switch (go.objectID) {
            case 1:
                if (go.waiter > 10) {
                    DroppedItem d = new DroppedItem("Item", 10, new Point2D.Double(go.location.getX(), go.location.getY()), go.level, new ItemStack(0, 1));
                    d.velocity = new Point((int) (Math.random() * 7) - 3, -((int) (Math.random() * 5) + 3));
                    go.level.entities.add(d);
                    go.waiter = 0;
                } else {
                    go.waiter++;
                }
                break;
            default:
                break;
        }
        return ret;
    }
}
