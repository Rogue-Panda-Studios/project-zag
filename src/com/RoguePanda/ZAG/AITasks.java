/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import com.RoguePanda.Library.ImageManipulator;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * The list of tasks for a specified Entity to preform
 *
 * @author Oa10712
 */
public class AITasks {

    /**
     * The list of AI attached to this AI List
     */
    ArrayList<AI> taskList = new ArrayList<>();
    /**
     * The Entity that owns this List
     */
    Entity entity;

    /**
     *
     * @param e The Entity that owns this
     */
    AITasks(Entity e) {
        entity = e;
    }

    /**
     * Adds an AI function to the AI list
     *
     * @param ai The AI to add
     */
    public void add(AI ai) {
        taskList.add(ai);
    }

    /**
     * Executes the actions starting from the first one defined using add()
     */
    public void action() {
        BoundBox r;
        r = new BoundBox(
                entity.boundingBox.x,
                entity.boundingBox.y,
                entity.sprite.getWidth(),
                entity.sprite.getHeight());
        entity.setBounds(r);
        entity.location = new Point2D.Double(r.getCenterX(), r.getCenterY());
        for (AI ai : taskList) {
            if (ai.execute()) {
                break;
            }
        }
        if (!entity.isPlayer) {
            if (entity.direction > 0) {
                entity.setSprite(ImageManipulator.selectFromSheet(
                        entity.spriteSheet,
                        entity.spritenumber,
                        64,
                        64));
            } else if (entity.direction < 0) {
                entity.setSprite(ImageManipulator.flipImage(
                        ImageManipulator.selectFromSheet(
                        entity.spriteSheet,
                        entity.spritenumber,
                        64,
                        64)));
            }
        }
    }
}
