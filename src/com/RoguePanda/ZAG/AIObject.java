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
    int counter;

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
            case 0:
                //go.velocity.setLocation((int) (Math.random() * 7) - 3, -((int) (Math.random() * 7) - 3));
                if (counter > 20) {
                    go.objectID = 1;
                    go.spritenumber=1;
                    counter = 0;
                } else {
                    counter++;
                }
                break;
            case 1:
                if (counter > 20) {
                    counter = 0;
                    go.objectID = 0;
                    go.spritenumber=0;
                } else {
                    counter++;
                }
                break;
            case 3:
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
