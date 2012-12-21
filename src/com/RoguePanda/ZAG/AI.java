/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

/**
 * This is the base class for all AI that entities can have
 *
 * @author Oa10712
 */
public abstract class AI {

    /**
     * The entity that has this AI
     */
    Entity entity;

    /**
     * The behavior to execute when this AI is running. Returns true if the
     * behavior is applicable to the current situation
     * @return true if the situation for the AI to occur is true
     */
    public abstract boolean execute();

    /**
     *
     * @param e The entity that owns this AI
     */
    AI(Entity e) {
        entity = e;
    }
}
